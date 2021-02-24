package com.koreait.mango.security.model;

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
	private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    
    public UserPrincipal(UserEntity user) {
    	this.setUserPk(user.getUserPk());
    	this.setUid(user.getUid());
    	this.setUpw(user.getUpw());
    	this.setEmail(user.getEmail());
    	this.setAuth(user.getAuth());
    	
    	authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getAuth()));
    }
    
    public static UserPrincipal create(UserEntity user,  Map<String, Object> attributes) {
    	 UserPrincipal userPrincipal = UserPrincipal.create(user);
         userPrincipal.setAttributes(attributes);
         return userPrincipal;
    }
   
    public static UserPrincipal create(UserEntity user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority(user.getAuth()));
        return new UserPrincipal(user);
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(this.getAuth()));
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
