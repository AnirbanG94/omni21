/*
 * package com.omnichannel.app.security;
 * 
 * import java.io.IOException;
 * 
 * import javax.servlet.FilterChain; import javax.servlet.ServletException;
 * import javax.servlet.ServletRequest; import javax.servlet.ServletResponse;
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.authentication.dao.DaoAuthenticationProvider;
 * import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * WebSecurityCustomizer; import
 * org.springframework.security.core.context.SecurityContextHolder; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.web.SecurityFilterChain; import
 * org.springframework.security.web.access.AccessDeniedHandler; import
 * org.springframework.security.web.authentication.AuthenticationSuccessHandler;
 * import org.springframework.security.web.authentication.ui.
 * DefaultLoginPageGeneratingFilter; import
 * org.springframework.web.filter.GenericFilterBean;
 * 
 * import com.omnichannel.app.service.serviceImpl.UserDetailsServiceImpl;
 * 
 * @Configuration public class SecurityConfiguration {
 * 
 * class LoginPageFilter extends GenericFilterBean {
 * 
 * @Override public void doFilter(ServletRequest request, ServletResponse
 * response, FilterChain chain) throws IOException, ServletException { if
 * (SecurityContextHolder.getContext().getAuthentication() != null &&
 * SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
 * ((HttpServletRequest)request).getRequestURI().equals("/login")){ System.out.
 * println("user is authenticated but trying to access login page, redirecting to /login"
 * ); ((HttpServletResponse)response).sendRedirect("/dashboard"); } else
 * if(SecurityContextHolder.getContext().getAuthentication() != null &&
 * SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
 * ((HttpServletRequest)request).getRequestURI().equals("/")){ System.out.
 * println("user is authenticated but trying to access login page, redirecting to /"
 * ); ((HttpServletResponse)response).sendRedirect("/dashboard"); }
 * chain.doFilter(request, response); }
 * 
 * }
 * 
 * @Autowired AuthenticationSuccessHandler successHandler;
 * 
 * @Autowired private AccessDeniedHandler accessDeniedHandler;
 * 
 * @Bean public UserDetailsService userDetailsService() { return new
 * UserDetailsServiceImpl(); }
 * 
 * @Bean public BCryptPasswordEncoder passwordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * @Bean public DaoAuthenticationProvider authenticationProvider() {
 * DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 * authProvider.setUserDetailsService(userDetailsService());
 * authProvider.setPasswordEncoder(passwordEncoder()); return authProvider; }
 * 
 * 
 * 
 * 
 * @Bean public WebSecurityCustomizer webSecurityCustomizer() { return (web) ->
 * web.ignoring() .antMatchers("/assets/**","/forgetpassword","/newpassword");
 * 
 * }
 * 
 * 
 * 
 * @Bean public SecurityFilterChain configuration(HttpSecurity http) throws
 * Exception {
 * 
 * http.addFilterBefore( new LoginPageFilter(),
 * DefaultLoginPageGeneratingFilter.class);
 * 
 * return http.authorizeRequests()
 * .antMatchers("/v3/api-docs/**","/swagger-resources",
 * "/swagger-ui.html","/getuser").permitAll()
 * .antMatchers("/private/**","/addUser").permitAll()
 * .antMatchers("/dashboard").hasAuthority("DASHBOARD")
 * .antMatchers("/company-setup").hasAuthority("COMPANYSETUP")
 * .antMatchers("/application-setup").hasAuthority("APPLICATIONSETUP")
 * .antMatchers("/year-setup").hasAuthority("YEARSETUP")
 * .antMatchers("/document-setup").hasAuthority("DOCUMENTSETUP")
 * .antMatchers("/email-setup").hasAuthority("EMAILSETUP")
 * .antMatchers("/approval-setup").hasAuthority("APPROVALSETUP")
 * .antMatchers("/login-history").hasAuthority("LOGINHISTORY")
 * .antMatchers("/transaction-log").hasAuthority("TRANSACTIONLOG")
 * .antMatchers("/user-registration").hasAuthority("USERREGISTRATION")
 * .antMatchers("/users").hasAuthority("USERS")
 * .antMatchers("/roles").hasAuthority("ROLES")
 * .antMatchers("/location-master").hasAuthority("LOCATIONMASTER")
 * .antMatchers("/outlet-master").hasAuthority("OUTLETMASTER")
 * .antMatchers("/region-master").hasAuthority("REGIONMASTER")
 * .antMatchers("/payment-mode-master").hasAuthority("PAYMENTMODEMASTER")
 * 
 * .antMatchers("/login").not().authenticated() .anyRequest() .authenticated()
 * .and() .formLogin() .loginPage("/login") .successHandler(successHandler)
 * .permitAll() .and()
 * .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789").
 * tokenValiditySeconds(3600) .and() .logout().deleteCookies("JSESSIONID")
 * .logoutSuccessUrl("/") .permitAll() .and()
 * .exceptionHandling().accessDeniedHandler(accessDeniedHandler) .and()
 * .build();
 * 
 * }
 * 
 * 
 * 
 * }
 */