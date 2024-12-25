package com.dulfinne.taxi.promocodeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PromocodeServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(PromocodeServiceApplication.class, args);
  }
}
