package edu.arelance.nube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayalumnoApplication {

	/**
	 * Para hacer el Gateway
	 * 
	 * 1) Nuevo proyecto con Spring Starter Project con las
	 * dependencias de Gateway y Eureka Client
	 *  
	 *  2) Anotación con @EnableEurekaClient en el main
	 *  3) Properties / yml configutación  / enrutamiento
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(GatewayalumnoApplication.class, args);
	}

}
