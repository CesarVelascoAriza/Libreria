package com.example.service.factura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClients
public class ServiceFacturaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFacturaApplication.class, args);
    }

}
