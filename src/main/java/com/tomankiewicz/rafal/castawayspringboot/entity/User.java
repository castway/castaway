package com.tomankiewicz.rafal.castawayspringboot.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.tomankiewicz.rafal.castawayspringboot.validation.PasswordFieldsMatch;
import com.tomankiewicz.rafal.castawayspringboot.validation.ValidEmail;

// Custom validation for password fields matching

@PasswordFieldsMatch.List({
			@PasswordFieldsMatch(firstField = "password", secondField = "passwordConfirmation", message = "The passwords must match")
})

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "username")
	@NotBlank(message = "Username is required")
	private String username;

	@Column(name = "password")
	@NotBlank(message = "Password is required")
	@Size(min = 5, message = "Minimum 5 characters")
	private String password;

	@Transient
	private String passwordConfirmation;

	@Column(name = "email")
	@ValidEmail
	private String email;

	@Column(name = "enabled")
	private int enabled;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Catch> catches;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Authority> authorities;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(
	name="competition_user",
	joinColumns=@JoinColumn(name="username"),
	inverseJoinColumns=@JoinColumn(name="competition_id")
	)
	private List<Competition> competitions;

	public User() {

	}

	public User(String username, String password, String passwordConfirmation, String email, int enabled,
			List<Catch> catches) {
		this.username = username;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.email = email;
		this.enabled = enabled;
		this.catches = catches;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public List<Catch> getCatches() {
		return catches;
	}

	public void setCatches(List<Catch> catches) {
		this.catches = catches;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	
	public List<Competition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(List<Competition> competitions) {
		this.competitions = competitions;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", enabled=" + enabled + ", catches=" + catches
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
		result = prime * result + ((catches == null) ? 0 : catches.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + enabled;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordConfirmation == null) ? 0 : passwordConfirmation.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (authorities == null) {
			if (other.authorities != null)
				return false;
		} else if (!authorities.equals(other.authorities))
			return false;
		if (catches == null) {
			if (other.catches != null)
				return false;
		} else if (!catches.equals(other.catches))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled != other.enabled)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (passwordConfirmation == null) {
			if (other.passwordConfirmation != null)
				return false;
		} else if (!passwordConfirmation.equals(other.passwordConfirmation))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
