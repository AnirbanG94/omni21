package com.omnichannel.app.MvcConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/logout").setViewName("login");
		registry.addViewController("/forgetpassword").setViewName("forgetpassowrd");
		registry.addViewController("/newpassword").setViewName("new-password");
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/company-setup").setViewName("admin/company-setup");
		registry.addViewController("/application-setup").setViewName("admin/application-setup");
		registry.addViewController("/year-setup").setViewName("admin/year-setup");
		registry.addViewController("/identification-setup").setViewName("admin/identification-setup");
		registry.addViewController("/email-setup").setViewName("admin/email-setup");
		registry.addViewController("/approval-setup").setViewName("admin/approval-setup");
		registry.addViewController("/login-history").setViewName("admin/login-history");
		registry.addViewController("/transaction-log").setViewName("admin/transaction-log");
		registry.addViewController("/document-setup").setViewName("admin/document-setup");
		registry.addViewController("/menu-setup").setViewName("admin/menu-setup");

		registry.addViewController("/user-registration").setViewName("usermanagment/user-registration");
		registry.addViewController("/users").setViewName("usermanagment/users");
		registry.addViewController("/roles").setViewName("usermanagment/roles");
		registry.addViewController("/location-master").setViewName("master/location-master");
		registry.addViewController("/outlet-master").setViewName("master/outlet-master");
		registry.addViewController("/region-master").setViewName("master/region-master");
		registry.addViewController("/payment-mode-master").setViewName("master/payment-mode-master");
		registry.addViewController("/assetstype-master").setViewName("master/assetstype-master");
		registry.addViewController("/transportation-master").setViewName("master/transportation-master");
		registry.addViewController("/bay-master").setViewName("master/bay-master");
		registry.addViewController("/schedule-master").setViewName("master/schedule-master");
		registry.addViewController("/reason-master").setViewName("master/reason-master");
		registry.addViewController("/cluster-master").setViewName("master/cluster-master");
		registry.addViewController("/cluster-mapping-master").setViewName("master/cluster-mapping-master");
		registry.addViewController("/tot-master").setViewName("master/tot-master");
		registry.addViewController("/tot-creation").setViewName("master/tot-creation");

		registry.addViewController("/item-product").setViewName("product/item-product");
		registry.addViewController("/brand-product").setViewName("product/brand-product");
		registry.addViewController("/manufacture-product").setViewName("product/manufacture-product");
		registry.addViewController("/pack-type-product").setViewName("product/pack-type-product");
		registry.addViewController("/purchase-group-product").setViewName("product/purchase-group-product");
		registry.addViewController("/uom-product").setViewName("product/uom-product");
		registry.addViewController("/article-product").setViewName("product/article-product");
		registry.addViewController("/article-status-product").setViewName("product/article-status-product");

		registry.addViewController("/invite-vendor").setViewName("vendor/invite-vendor");
		registry.addViewController("/vendor-approval").setViewName("vendor/vendor-approval");
		registry.addViewController("/vendor-product").setViewName("vendor/vendor-product");
		registry.addViewController("/vendor-product-approval").setViewName("vendor/vendor-product-approval");
		registry.addViewController("/vendor-registration").setViewName("vendor/vendor-registration");
		registry.addViewController("/vendor-shipping-notification").setViewName("vendor/vendor-shipping-notification");
		registry.addViewController("/vendor-registration-onboarding")
				.setViewName("vendor/vendor-registration-onboarding");
		registry.addViewController("/vendor-promo").setViewName("vendor/vendor-promo");
		registry.addViewController("/vendor-manufacture").setViewName("vendor/vendor-manufacture");
		registry.addViewController("/vendor-replacement").setViewName("vendor/vendor-replacement");

		registry.addViewController("/general-promo").setViewName("promo/general-promo");
		registry.addViewController("/buyer-promo").setViewName("promo/buyer-promo");
		registry.addViewController("/vendor-promo-approval").setViewName("promo/vendor-promo-approval");
		registry.addViewController("/buyer-promo-approval").setViewName("promo/buyer-promo-approval");

		registry.addViewController("/gate-keeper-margin").setViewName("gkm/gate-keeper-margin");

		registry.addViewController("/purchase-order").setViewName("purchase/purchase-order");
		registry.addViewController("/purchase-bill").setViewName("purchase/purchase-bill");
		registry.addViewController("/purchase-order-approval").setViewName("purchase/purchase-order-approval");

		registry.addViewController("/asset-booking").setViewName("asset/asset-booking");
		registry.addViewController("/asset-booking-approval").setViewName("asset/asset-booking-approval");
		registry.addViewController("/asset-execution").setViewName("asset/asset-execution");
		registry.addViewController("/asset-registration").setViewName("asset/asset-registration");

		registry.addViewController("/store-gdn").setViewName("store/store-gdn");
		registry.addViewController("/store-grn").setViewName("store/store-grn");
		registry.addViewController("/store-mrn").setViewName("store/store-mrn");
		registry.addViewController("/store-pick-list").setViewName("store/store-pick-list");
		registry.addViewController("/store-stock").setViewName("store/store-stock");
		registry.addViewController("/store-stock-transfer").setViewName("store/store-stock-transfer");
		registry.addViewController("/store-openingstock").setViewName("store/store-openingstock");

		registry.addViewController("/warehouse-gdn").setViewName("warehouse/warehouse-gdn");
		registry.addViewController("/warehouse-grn").setViewName("warehouse/warehouse-grn");
		registry.addViewController("/warehouse-mrn").setViewName("warehouse/warehouse-mrn");
		registry.addViewController("/warehouse-pick-list").setViewName("warehouse/warehouse-pick-list");
		registry.addViewController("/warehouse-stock").setViewName("warehouse/warehouse-stock");
		registry.addViewController("/warehouse-stock-transfer").setViewName("warehouse/warehouse-stock-transfer");
		registry.addViewController("/warehouse-openingstock").setViewName("warehouse/warehouse-openingstock");

		
		registry.addViewController("/403").setViewName("403");
		registry.addViewController("/error").setViewName("error");

		registry.addViewController("/test").setViewName("test");
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
	}
	

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginPageInterceptor());
    }

}
