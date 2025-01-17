package com.example.QuanLiDonHang.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	 @Bean
	    public RestTemplate restTemplate() {
	        RestTemplate restTemplate = new RestTemplate();
	        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
	        messageConverters.add(new MappingJackson2HttpMessageConverter());
	        return restTemplate;
	    }
}
