package com.sambuja.deliverylink.config;

import com.sambuja.deliverylink.DeliveryLinkApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DeliveryLinkApplication.class);
    }
}
