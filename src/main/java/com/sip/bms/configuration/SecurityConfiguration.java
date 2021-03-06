package com.sip.bms.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/").permitAll() // accès pour tous users
				.antMatchers("/login").permitAll() // accès pour tous users
				.antMatchers("/registration").permitAll() // accès pour tous users
				
				.antMatchers("/users/**").permitAll()
				.antMatchers("/categories/**").permitAll() //.hasAuthority("ADMIN") // autre option .hasAnyAuthority("SUPER ADMIN", "ADMIN", "USER")
				.antMatchers("/books/list2").permitAll() //.hasAnyAuthority("SUPER ADMIN", "ADMIN", "USER")
				.antMatchers("/books/detail/**").permitAll() //.hasAnyAuthority("SUPER ADMIN", "ADMIN", "USER")
				.antMatchers("/books/add").permitAll() //.hasAnyAuthority("SUPER ADMIN", "ADMIN")
				.antMatchers("/books/list").permitAll() //.hasAnyAuthority("SUPER ADMIN", "ADMIN")
				.antMatchers("/roles/**").permitAll() //.hasAuthority("ADMIN")
				.antMatchers("/account/add/**").permitAll() //.hasAnyAuthority("SUPER ADMIN", "ADMIN", "USER")
				.antMatchers("/account/list2").permitAll() //.hasAnyAuthority("SUPER ADMIN", "ADMIN")
				
				.anyRequest().authenticated().and().csrf().disable().formLogin() // l'accès de fait via un formulaire    
				.loginPage("/login").failureUrl("/login?error=true") // fixer la page login
			
				.defaultSuccessUrl("/home") // page d'accueil après login avec succès
				.usernameParameter("email") // paramètres d'authentifications login et password
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // route de deconnexion ici /logut
				.logoutSuccessUrl("/login").and().exceptionHandling() // une fois deconnecté redirection vers login
				.accessDeniedPage("/403");
	}

	// redirect toute requette demandant l'accès aux ressources vers la sortie
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**"); // a remettre entre parantheses  
	} 
}
