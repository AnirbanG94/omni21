package com.omnichannel.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.omnichannel.app.service.serviceImpl.UserDetailsServiceImpl;

/**
 * @author Rajsekhar Acharya
 *
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationSuccessHandler successHandler;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers("/assets/**", "/forgetpassword", "/newpassword", "/test",
						"/vendor-registration-onboarding", "/api/vendor/postVendorRegistrationOnboarding",
						"/private/onboarding/**", "/forgetPass", "/api/master/getLocation",
						"/api/master/getLocationById", "/api/master/getLocationByName", "/api/master/getCountry",
						"/api/master/getStateByCountryCode", "/api/master/getPaymentmode", "/CheckUsername")
				.permitAll().antMatchers("/v3/api-docs/**", "/swagger-resources", "/swagger-ui.html", "/getuser")
				.permitAll().antMatchers("/private/**", "/addUser", "/api/master/**").authenticated()
				.antMatchers("/home").hasAuthority("HOME")

				.antMatchers("/company-setup").hasAuthority("COMPANYSETUP").antMatchers("/document-setup")
				.hasAuthority("DOCUMENTSETUP").antMatchers("/application-setup").hasAuthority("APPLICATIONSETUP")
				.antMatchers("/year-setup").hasAuthority("YEARSETUP").antMatchers("/identification-setup")
				.hasAuthority("IDENTIFICATIONSETUP").antMatchers("/menu-setup").hasAuthority("MENUSETUP")
				.antMatchers("/email-setup").hasAuthority("EMAILSETUP").antMatchers("/approval-setup")
				.hasAuthority("APPROVALSETUP").antMatchers("/login-history").hasAuthority("LOGINHISTORY")
				.antMatchers("/transaction-log").hasAuthority("TRANSACTIONLOG")

				.antMatchers("/users").hasAuthority("USERS").antMatchers("/roles").hasAuthority("ROLES")

				.antMatchers("/location-master").hasAuthority("LOCATIONMASTER").antMatchers("/outlet-master")
				.hasAuthority("OUTLETMASTER").antMatchers("/region-master").hasAuthority("REGIONMASTER")
				.antMatchers("/payment-mode-master").hasAuthority("PAYMENTMODEMASTER").antMatchers("/assetstype-master")
				.hasAuthority("ASSETSTYPE").antMatchers("/transportation-master").hasAuthority("TRANSPORTATION")
				.antMatchers("/reason-master").hasAuthority("REASON").antMatchers("/bay-master").hasAuthority("BAY")
				.antMatchers("/schedule-master").hasAuthority("SCHEDULE").antMatchers("/cluster-master")
				.hasAuthority("CLUSTER").antMatchers("/cluster-mapping-master").hasAuthority("CLUSTER")
				.antMatchers("/tot-master").hasAuthority("TOTMASTER")
                .antMatchers("/tot-creation").hasAuthority("TOTCREATION")
                
				.antMatchers("/item-product").hasAuthority("ITEM").antMatchers("/brand-product").hasAuthority("BRAND")
				.antMatchers("/manufacture-product").hasAuthority("MANUFACTURE").antMatchers("/pack-type-product")
				.hasAuthority("PACKTYPE").antMatchers("/purchase-group-product").hasAuthority("PURCHASEGROUP")
				.antMatchers("/uom-product").hasAuthority("UOM").antMatchers("/article-product").hasAuthority("ARTICLE")
				.antMatchers("/article-status-product").hasAuthority("ARTICLESTATUS")

				.antMatchers("/invite-vendor").hasAuthority("INVITEVENDOR").antMatchers("/vendor-approval")
				.hasAuthority("VENDORAPPROVAL").antMatchers("/vendor-product").hasAuthority("VENDORPRODUCT")
				.antMatchers("/vendor-product-approval").hasAuthority("PRODUCTAPPROVAL")
				.antMatchers("/vendor-registration").hasAuthority("VENDORREGISTRATION")
				.antMatchers("/vendor-shipping-notification").hasAuthority("SHIPPINGNOTIFICATION")
				.antMatchers("/vendor-manufacture").hasAuthority("VENDORMANUFACTURE").antMatchers("/vendor-replacement")
				.hasAuthority("VENDORREPLACEMENT")

				.antMatchers("/vendor-promo").hasAuthority("VENDORPROMO").antMatchers("/buyer-promo")
				.hasAuthority("BUYERPROMO").antMatchers("/general-promo").hasAuthority("GENERALPROMO")

				.antMatchers("/vendor-promo-approval").hasAuthority("VENDORPROMOAPPROVAL")
				.antMatchers("/buyer-promo-approval").hasAuthority("BUYERPROMOAPPROVAL")

				.antMatchers("/gate-keeper-margin").hasAuthority("GKMARGIN")

				.antMatchers("/purchase-order").hasAuthority("PURCHASEORDER").antMatchers("/purchase-bill")
				.hasAuthority("PURCHASEBILL").antMatchers("/purchase-order-approval")
				.hasAuthority("PURCHASEORDERAPPROVAL")

				.antMatchers("/asset-registration").hasAuthority("ASSETREGISTRATION").antMatchers("/asset-booking")
				.hasAuthority("ASSETBOOKING").antMatchers("/asset-booking-approval")
				.hasAuthority("ASSETBOOKINGAPPROVAL").antMatchers("/asset-execution").hasAuthority("ASSETEXECUTION")

				.antMatchers("/store-gdn").hasAuthority("SGDN").antMatchers("/store-grn").hasAuthority("SGRN").antMatchers("/store-mrn").hasAuthority("SMRN")
				.antMatchers("/store-pick-list").hasAuthority("SPICKLIST").antMatchers("/store-stock")
				.hasAuthority("SSTOCK").antMatchers("/store-stock-transfer").hasAuthority("SSTOCKTRANSFER").antMatchers("/store-openingstock").hasAuthority("SOPENINGSTOCK")

				.antMatchers("/warehouse-gdn").hasAuthority("WGDN").antMatchers("/warehouse-grn").hasAuthority("WGRN").antMatchers("/warehouse-mrn").hasAuthority("WMRN")
				.antMatchers("/warehouse-pick-list").hasAuthority("WPICKLIST").antMatchers("/warehouse-stock")
				.hasAuthority("WSTOCK").antMatchers("/warehouse-stock-transfer").hasAuthority("WSTOCKTRANSFER").antMatchers("/warehouse-openingstock").hasAuthority("WOPENINGSTOCK")

				.antMatchers("/login").not().authenticated().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").successHandler(successHandler).permitAll().and().rememberMe()
				.key("AbcdEfghIjklmNopQrsTuvXyz_0123456789").tokenValiditySeconds(3600).and().logout()
				.deleteCookies("JSESSIONID").logoutSuccessUrl("/").permitAll().and().exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
	}

	/*
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * web.ignoring() .antMatchers("/assets/**", "/forgetpassword", "/newpassword",
	 * "/test", "/vendor-registration-onboarding",
	 * "/api/vendor/postVendorRegistrationOnboarding", "/private/onboarding/**",
	 * "/forgetPass", "/api/master/getLocation", "/api/master/getLocationById",
	 * "/api/master/getLocationByName", "/api/master/getCountry",
	 * "/api/master/getStateByCountryCode", "/api/master/getPaymentmode",
	 * "/CheckUsername"); }
	 */

}
