package com.koreait.mango.model.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.koreait.mango.model.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrincipal extends UserEntity implements OAuth2User, UserDetails {
	
	private static final long serialVersionUID = 1L;
	private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(authorities == null) {
			authorities = Collections.singletonList(new SimpleGrantedAuthority(getAuth()));
		}
		return authorities;
	}
	
	@Override
	public String getPassword() {		
		return this.getUpw();
	}
	@Override
	public String getUsername() {
		return this.getUid();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() { 
		return true;
	}
	
	@Override
	public Map<String, Object> getAttributes() {		
		return attributes;
	}

	@Override
	public String getName() {
		return getUid();
	}

}
