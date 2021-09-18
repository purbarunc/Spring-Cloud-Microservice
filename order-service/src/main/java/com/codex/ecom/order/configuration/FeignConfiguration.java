package com.codex.ecom.order.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = { com.codex.ecom.order.client.ProductFeignClient.class,
		com.codex.ecom.order.client.InventoryFeignClient.class })
public class FeignConfiguration {
}
