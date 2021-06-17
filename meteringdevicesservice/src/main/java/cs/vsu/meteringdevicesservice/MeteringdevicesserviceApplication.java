package cs.vsu.meteringdevicesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeteringdevicesserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeteringdevicesserviceApplication.class, args);
	}

}
