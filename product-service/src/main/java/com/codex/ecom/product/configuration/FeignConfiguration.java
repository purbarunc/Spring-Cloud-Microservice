package com.codex.ecom.product.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {com.codex.ecom.product.client.InventoryFeignClient.class})
public class FeignConfiguration {
}
