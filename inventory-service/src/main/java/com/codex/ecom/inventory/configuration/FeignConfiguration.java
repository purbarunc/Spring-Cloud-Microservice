package com.codex.ecom.inventory.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = com.codex.ecom.inventory.client.ProductFeignClient.class)
public class FeignConfiguration {
}
