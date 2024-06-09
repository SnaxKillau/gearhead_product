package ite.product.gearheadproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@RefreshScope
@EnableFeignClients
@SpringBootApplication
public class GearheadproductApplication {

	public static void main(String[] args) {
		SpringApplication.run(GearheadproductApplication.class, args);
	}

}
