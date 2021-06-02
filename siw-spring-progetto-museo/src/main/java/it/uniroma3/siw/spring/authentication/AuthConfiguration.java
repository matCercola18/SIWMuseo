package it.uniroma3.siw.spring.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static it.uniroma3.siw.spring.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{

	/*is used to access the DB to get the 
	 * Credentials to perform authentiation and authorization*/
	@Autowired
	DataSource datasource;
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http
			//authorization paragraph: qui definiamo chi può accedere a cosa
			.authorizeRequests()
			
			// solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
			.antMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
			.antMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(ADMIN_ROLE)
			
			//Tutti gli altri utenti non autenticati possono accere alle pagine rimanenti 
			.anyRequest().permitAll()
			
			// login paragraph: qui definiamo come è gestita l'autenticazione
            // usiamo il protocollo formlogin 
			.and().formLogin()
			
			 // la pagina di login si trova a /login
            // NOTA: Spring gestisce il post di login automaticamente
			.loginPage("/login")
			
			// se il login ha successo, si viene rediretti al path /admin/collezioni
			.defaultSuccessUrl("/admin/collezioni")
			
			// logout paragraph: qui definiamo il logout
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
			// il logout è attivato con una richiesta GET a "/logout"
			
			// in caso di successo, si viene reindirizzati alla / page  (home page)
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.clearAuthentication(true).permitAll();
			
	}
	
	/*Metodo che offre le query sql per ottenere username e password*/
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
		//Utilizza datasource (autowired) per avere accesso alle credenziali salvate
		.dataSource(this.datasource)
		//Prende username e ruolo
		.authoritiesByUsernameQuery("SELECT username,role FROM credentials WHERE username=?")
		//Prende usernamen, password e un booleano che specifica se l'utente è abilitato o no (per noi sempre abilitato)
		.usersByUsernameQuery("SELECT username,password,1 as enabled FROM credentials WHERE username=?");
		
	}
	
	/*
	 * Questo metodo definisce un Password encoder bean
	 * Esso viene usato per codificare e decodificare le password
	 */
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
