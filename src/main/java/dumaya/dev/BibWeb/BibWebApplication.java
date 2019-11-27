package dumaya.dev.BibWeb;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("dumaya.dev.BibWeb")
@SpringBootApplication
public class BibWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibWebApplication.class, args);
	}

}
