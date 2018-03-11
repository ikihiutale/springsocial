
package com.foo.login.model;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity(name = "user")
@Table(name = "user")
public class UserBean implements Serializable {
	 private static final long serialVersionUID = 1L;
	 
	 @NotNull(message = "Email  cannot be empty")
	 @Email(message = "Email Format is not valid")
	 @Size(min = 3, max = 30, message = "Email can not be empty")
	 @Id
	 private String email;
	 
	 @NotNull(message = "First Name cannot be empty")
	 @Size(min = 3, max = 30, message = "First Name cannot be less than 3 characters")
	 private String firstName;
	 
	 @NotNull(message = "Last Name cannot be empty")
	 @Size(min = 3, max = 30, message = "Last Name cannot be less than 3 characters")
	 private String lastName;
	 
	private String title;
	 private String country;
	 private String password;
	 private String passwordConfirm; 
	 private String provider;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "UserBean [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", title=" + title
				+ ", country=" + country + ", password=" + password + ", provider=" + provider + "]";
	}
	
}
