package com.omkar.crud.FriendBook.controller;

import java.util.Optional;
import java.util.Set;

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

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.omkar.crud.FriendBook.entities.User;
import com.omkar.crud.FriendBook.exceptions.UserNotFoundException;
import com.omkar.crud.FriendBook.repositories.UserRepository;
import com.omkar.crud.FriendBook.status.StatusHandler;


@RestController
public class FriendBookController 
{
	@Autowired
	UserRepository userRepository;
		
	@PostMapping("/friendbook/v1/signup")
	public ResponseEntity<StatusHandler> createUser(@RequestBody User user)
	{
		try
		{
			User savedUser = userRepository.save(user);
			StatusHandler status = new StatusHandler(savedUser.getUserId(), "User '"+savedUser.getUsername()+"' created successfully!");
			return ResponseEntity.status(201).body(status);
		}
		catch(Exception e)
		{
			System.out.println("Some exception : "+e.getMessage());
			if(e.getMessage().contains("constraint [username]"))
			{
				StatusHandler status = new StatusHandler("User '"+user.getUsername()+"' already exists!");
				return ResponseEntity.status(409).body(status);
			}
		}
		return null;
	}
	
	@GetMapping("/friendbook/v1/users")
	public MappingJacksonValue listUsers()
	{		
		MappingJacksonValue mapping = filterOutput(userRepository.findAll());
		return mapping;
	}
	
	@GetMapping("/friendbook/v1/login")
	public MappingJacksonValue listUser(Authentication authentication)
	{		
		Optional<User> user = userRepository.findByUsername(authentication.getName());
		if(user.isPresent())
		{
			MappingJacksonValue mapping = filterOutput(user.get());
			return mapping;	
		}
		else
		{
			throw new UserNotFoundException("Specified user not found : "+authentication.getName());
		}
	}
	
	@GetMapping("/friendbook/v1/users/{username}")
	public MappingJacksonValue listUser(@PathVariable String username)
	{		
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent())
		{
			MappingJacksonValue mapping = filterOutput(user.get());
			return mapping;
		}
		else
		{
			throw new UserNotFoundException("Specified user not found : "+username);
		}
	}
	
	@GetMapping("/friendbook/v1/users/{username}/friends")
	public MappingJacksonValue listUserFriends(@PathVariable String username)
	{		
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent())
		{
			User fetchedUser = user.get();
			MappingJacksonValue mapping = filterOutput(fetchedUser.getUsersFriends());
			return mapping;	
		}
		else
		{
			throw new UserNotFoundException("Specified user not found : "+username);
		}
	}
	
	@PatchMapping("/friendbook/v1/users/{username}/add-friend/{friend_username}")
	public MappingJacksonValue addFriend(@PathVariable String username, @PathVariable String friend_username)
	{
		Optional<User> user = userRepository.findByUsername(friend_username);
		Optional<User> friend = userRepository.findByUsername(username);
		
		if(user.isPresent() && friend.isPresent())
		{
			User fetchedUser = user.get();
			User fetchedFriend = friend.get();
			
			Set<User> usersFriends = fetchedFriend.getUsersFriends();
			usersFriends.add(fetchedUser);
			fetchedFriend.setUsersFriends(usersFriends);

			userRepository.save(fetchedFriend);
			MappingJacksonValue mapping = filterOutput(fetchedFriend);
			return mapping;
		}
		
		throw new UserNotFoundException("Specified user(s) not found : "+"("+username+") or ("+friend_username+")");		
	}
	
	
	@PatchMapping("/friendbook/v1/users/{username}/remove-friend/{friend_username}")
	public MappingJacksonValue removeFriend(@PathVariable String username, @PathVariable String friend_username)
	{
		Optional<User> user = userRepository.findByUsername(friend_username);
		Optional<User> friend = userRepository.findByUsername(username);
		
		if(user.isPresent() && friend.isPresent())
		{
			User fetchedUser = user.get();
			User fetchedFriend = friend.get();
			
			Set<User> usersFriends = fetchedFriend.getUsersFriends();
			usersFriends.remove(fetchedUser);
			fetchedFriend.setUsersFriends(usersFriends);

			userRepository.save(fetchedFriend);
			MappingJacksonValue mapping = filterOutput(fetchedFriend);
			return mapping;
		}
		
		throw new UserNotFoundException("Specified user(s) not found : "+"("+username+") or ("+friend_username+")");		
	}
	
	public MappingJacksonValue filterOutput(Object action) 
	{
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userId","username","name","age","country");
		FilterProvider filters = new SimpleFilterProvider().addFilter("CustomFilter", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(action);
		
		mapping.setFilters(filters);
		return mapping;
	}
	
}

