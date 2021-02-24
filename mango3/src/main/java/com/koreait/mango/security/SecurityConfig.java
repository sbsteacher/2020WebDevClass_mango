package com.koreait.mango.security;

import static com.koreait.mango.security.SocialType.FACEBOOK;
import static com.koreait.mango.security.SocialType.GOOGLE;
import static com.koreait.mango.security.SocialType.KAKAO;
import static com.koreait.mango.security.SocialType.NAVER;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	final UserDetailsService memberService;
	final CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/res/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests()
				.antMatchers("/user/**").hasRole("USER")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/**").permitAll()
		 
				.antMatchers("/facebook").hasAuthority(FACEBOOK.getRoleType())
		        .antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
		        .antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
		        .antMatchers("/naver").hasAuthority(NAVER.getRoleType())
		        .anyRequest().authenticated()
		        	.and()
		    .oauth2Login()
		        .userInfoEndpoint()
		        	.userService(customOAuth2UserService)
		        		.and()
		        .defaultSuccessUrl("/home")
		        .failureUrl("/login")
		        	.and()
		    .exceptionHandling() 
		    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
		
		http.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/home");
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.invalidateHttpSession(true);
		
		http.exceptionHandling()
			.accessDeniedPage("/denied");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties,
			@Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
			@Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
			@Value("${custom.oauth2.naver.client-id}") String naverClientId,
			@Value("${custom.oauth2.naver.client-secret}") String naverClientSecret) {
		
		List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
				.map(client -> getRegistration(oAuth2ClientProperties, client))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
				.clientId(kakaoClientId)
				.clientSecret(kakaoClientSecret)				
				.build()
		);		
		registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
				.clientId(naverClientId)
				.clientSecret(naverClientSecret)				
				.build()
		);
		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
		if ("google".equals(client)) {
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
			return CommonOAuth2Provider.GOOGLE.getBuilder(client)
					.clientId(registration.getClientId())
					.clientSecret(registration.getClientSecret())
					.scope("email", "profile")
					.build();
		} else if ("facebook".equals(client)) {
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");
			return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
					.clientId(registration.getClientId())
					.clientSecret(registration.getClientSecret())
					.userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
					.scope("email")
					.build();
		}
		return null;
	}
}
