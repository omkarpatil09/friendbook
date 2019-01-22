package com.omkar.crud.FriendBook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.omkar.crud.FriendBook.entities.User;
import com.omkar.crud.FriendBook.services.UserService;
import com.omkar.crud.FriendBook.status.StatusHandler;


@RestController
public class FriendBookController 
{
		
	@Autowired
	UserService userService;
		
	@PostMapping("/friendbook/v1/signup")
	public ResponseEntity<StatusHandler> createUser(@RequestBody User user)
	{
		return userService.createUser(user);
	}
	
	@GetMapping("/friendbook/v1/users")
	public MappingJacksonValue listUsers()
	{		
		return userService.listUsers();
	}

	@GetMapping("/friendbook/v1/login")
	public MappingJacksonValue listUser(Authentication authentication)
	{		
		return userService.listUser(authentication);
	}
	
	@GetMapping("/friendbook/v1/users/{username}")
	public MappingJacksonValue listUser(@PathVariable String username)
	{		
		return userService.listUser(username);
	}
	
	@GetMapping("/friendbook/v1/users/{username}/friends")
	public MappingJacksonValue listUserFriends(@PathVariable String username)
	{		
		return userService.listUserFriends(username);
	}

	@PatchMapping("/friendbook/v1/users/{username}/add-friend/{friend_username}")
	public MappingJacksonValue addFriend(@PathVariable String username, @PathVariable String friend_username)
	{
		return userService.addFriend(username, friend_username);		
	}
	
	@PatchMapping("/friendbook/v1/users/{username}/remove-friend/{friend_username}")
	public MappingJacksonValue removeFriend(@PathVariable String username, @PathVariable String friend_username)
	{
		return userService.removeFriend(username, friend_username);		
	}

}

