package com.test.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
		endpoints.accessTokenConverter(tokenConverter()).tokenStore(tokenStore());
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient("admin")
		.secret("{noop}admin")
		.authorizedGrantTypes("authorization_code","client_credentials","password")
		.scopes("read","write").accessTokenValiditySeconds(9000).refreshTokenValiditySeconds(90000);
	}
	
	@Bean
	public DefaultTokenServices tokenService() {
		DefaultTokenServices service=new DefaultTokenServices();
		service.setTokenStore(tokenStore());
		service.setSupportRefreshToken(true);
		return service;
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		JwtTokenStore store=new JwtTokenStore(tokenConverter());
		return store;
	}
	
	@Bean
	public JwtAccessTokenConverter tokenConverter() {
		JwtAccessTokenConverter converter=new JwtAccessTokenConverter();
		converter.setSigningKey("012345678901234567890123456789AB");
		return converter;
	}
}
