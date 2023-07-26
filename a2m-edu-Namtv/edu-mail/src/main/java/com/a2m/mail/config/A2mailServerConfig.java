package com.a2m.mail.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableResourceServer
public class A2mailServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	/**
	 * Instead of using a TokenStore in our Resource Server, we can use
	 * RemoteTokeServices:
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Primary
	@Bean
	public RemoteTokenServices tokenServices() {
		final RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl(env.getRequiredProperty("a2m.token.endpoint"));
		tokenService.setClientId(env.getRequiredProperty("a2m.client.id"));
		tokenService.setClientSecret(env.getRequiredProperty("a2m.client.secret"));
		return tokenService;
	}

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		
		// For production.
//		http.headers().contentSecurityPolicy("frame-ancestors " + env.getRequiredProperty("a2m.web.host") + ":"
//				+ env.getRequiredProperty("a2m.web.port"));
//
//		http.headers().frameOptions().disable().addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM "
//				+ env.getRequiredProperty("a2m.web.host") + ":" + env.getRequiredProperty("a2m.web.port")));

        http
                .headers().frameOptions().disable()
                .addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM http://localhost:4200")).
                and()
                //		.csrf()
                //		.csrfTokenRepository(cookieCsrfTokenRepository())
                //		.ignoringAntMatchers("/public/**")
                //		.and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().authorizeRequests()
                // Allow public access
                .antMatchers("/public/**").permitAll()
                .antMatchers("/nero/**").permitAll()
                .antMatchers("/common/file-service/**").permitAll()
                .antMatchers("/api/homepage/**").permitAll()
                .antMatchers("/api/news").permitAll()
                .antMatchers("/wschat/common/file/**").permitAll()
                .and().authorizeRequests().antMatchers("/public/**").permitAll()
                .and().authorizeRequests().antMatchers("/static/**").permitAll()
                .antMatchers("/api/**").permitAll()

//				.anyRequest().permitAll();

				.antMatchers("/api/**").authenticated();

	}

	public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
		CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();
		repository.setCookieHttpOnly(false);
		repository.setCookiePath("/");
		return repository;
	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer config) throws SQLException {
		config.tokenServices(tokenServices());
	}

}
