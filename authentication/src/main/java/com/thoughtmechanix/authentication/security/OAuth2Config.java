package com.thoughtmechanix.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService userDetailsService;

  private final JwtAccessTokenConverter jwtAccessTokenConverter;

  private final TokenStore tokenStore;

  public OAuth2Config(
      AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
      JwtAccessTokenConverter jwtAccessTokenConverter, TokenStore tokenStore) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    this.tokenStore = tokenStore;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient("eagleeye")
        .secret("thisissecret")
        .authorizedGrantTypes("refresh_token", "password", "client_credentials")
        .scopes("webclient", "mobileclient");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
        .accessTokenConverter(jwtAccessTokenConverter)
        .tokenStore(tokenStore);
  }
}
