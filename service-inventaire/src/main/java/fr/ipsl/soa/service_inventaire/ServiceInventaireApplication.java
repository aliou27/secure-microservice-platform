package fr.ipsl.soa.service_inventaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceInventaireApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceInventaireApplication.class, args);
	}
}
