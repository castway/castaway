package com.tomankiewicz.rafal.castawayspringboot.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomankiewicz.rafal.castawayspringboot.dao.AuthorityDao;
import com.tomankiewicz.rafal.castawayspringboot.dao.UserDao;
import com.tomankiewicz.rafal.castawayspringboot.entity.Authority;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@Service
public class UserServiceImpl implements UserService {

	
	private UserDao userRepository;
	private AuthorityDao authorityDao;
	
	@Autowired
	public UserServiceImpl(UserDao userRepository, AuthorityDao authorityDao) {
		this.userRepository = userRepository;
		this.authorityDao = authorityDao;
	}
	
	@Override
	@Transactional(noRollbackFor = Exception.class)
	public User findByUsername(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	@Transactional(noRollbackFor = Exception.class)
	public void save(User user) {
		user.setEnabled(1);
		
		if (user.getPassword() != null) {
			user.setPassword(bCrypt(user.getPassword()));
		}
		
		userRepository.save(user);
		
		authorityDao.setAuthority(user);
	}

	@Override
	@Transactional
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				mapRolesToAuthorities(user.getAuthorities()));
		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Authority> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
	}
	
	// Explicit BCryptPasswordEncoder creation, autowiring it would not work
	
    public static String bCrypt(String data) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return passwordEncoder.encode(data);
    }

	@Override
	public Authentication getAuthenticationToken() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public String getUsernameOfTheLoggedInUserFromDB(Authentication auth) {

		if (auth instanceof OAuth2AuthenticationToken) {

			User user = getUserObjectFromDBusing(auth);
			return user.getUsername();
			
		}
		
		return auth.getName();
	}

	public User getUserObjectFromDBusing(Authentication auth) {

		OAuth2User loggedInUser = (OAuth2User) auth.getPrincipal();
		String email = loggedInUser.getAttribute("email");
		
		return findByEmail(email);
	}


}
