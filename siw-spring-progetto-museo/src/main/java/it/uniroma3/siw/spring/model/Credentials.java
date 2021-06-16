package it.uniroma3.siw.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Credentials {

	public static final String ADMIN_ROLE = "ADMIN";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;
	
	@Column(nullable = false,unique=true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String role;

	public Credentials() {
		
	}
	
	public Credentials(String username,String password) {
		this.username=username;
		this.password=password;
	}

	
	
	
	
	
}
