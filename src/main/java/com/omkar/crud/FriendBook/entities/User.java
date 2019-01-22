package com.omkar.crud.FriendBook.entities;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@JsonFilter("CustomFilter")
public class User 
{
	@Id
	@GeneratedValue
	private int userId;
	
	@Column(unique=true)
	private String username;
	private String password;
	
	private String name;
	private int age;
	private String country;
	
	@JsonIgnore
	@JoinTable(name = "friend_list", joinColumns = {
	 @JoinColumn(name = "host", referencedColumnName = "userId", nullable = false)}, inverseJoinColumns = {
	 @JoinColumn(name = "friend", referencedColumnName = "userId", nullable = false)})
	 @ManyToMany
	 private Set<User> usersFriends;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "usersFriends")
    private Set<User> friendOfUsers;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	public Set<User> getUsersFriends() {
		return usersFriends;
	}

	public void setUsersFriends(Set<User> usersFriends) {
		this.usersFriends = usersFriends;
	}

	public Collection<User> getFriendOfUsers() {
		return friendOfUsers;
	}

	public void setFriendOfUsers(Set<User> friendOfUsers) {
		this.friendOfUsers = friendOfUsers;
	}

	public User()
	{
		
	}
	
	public User(String username, String password, String name, int age, String country) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.age = age;
		this.country = country;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", name=" + name + ", age=" + age
				+ ", country=" + country + "]";
	}
	
}
