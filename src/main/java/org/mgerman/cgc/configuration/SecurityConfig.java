package org.mgerman.cgc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    //http.authorizeRequests().anyRequest().permitAll();
    http
        .authorizeRequests()
        .antMatchers("/graphql").permitAll()
        .and()
/*        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(jwtFilter, RequestHeaderAuthenticationFilter.class) // Filter*/
        .cors().disable()
        .csrf().disable();
  }
}
