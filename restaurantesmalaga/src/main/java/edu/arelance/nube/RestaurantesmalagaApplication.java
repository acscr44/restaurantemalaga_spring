package edu.arelance.nube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@Controller
//@EnableEurekaClient  // Activamos el cliente eureka
public class RestaurantesmalagaApplication {
	/**
	 * Para configurar el cliente eureka
	 * 
	 * 1) Add starters, eureka client
	 * 2) Add Anotación @EnableEurekaClient en el main
	 * 3) configuramos properties relativas a eureka.
	 * 
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		SpringApplication.run(RestaurantesmalagaApplication.class, args);
	}

	@RequestMapping(value = "{path:[^\\.]*}", method = RequestMethod.GET)
    public String redirect() {
        return "forward:/";
    }
}




