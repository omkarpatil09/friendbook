package com.omkar.crud.FriendBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.omkar.crud.FriendBook.entities.User;
import com.omkar.crud.FriendBook.repositories.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider 
{
	@Autowired
	private UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException 
	{
		List<GrantedAuthority> authorities = new ArrayList<>();
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent())
        {
	        if(user.get().getUsername().equals(username) && user.get().getPassword().equals(password))
	        {
	        	authorities.add(new SimpleGrantedAuthority("USER")); // description is a string
	        	return new UsernamePasswordAuthenticationToken(username, password, authorities);
	        }
	        else
	        {
	        	System.out.println("Unauthorised!");
	        	return null;
	        }
        }
        else
        {
        	System.out.println("User not present!");
        	return null;
        }

        //return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
