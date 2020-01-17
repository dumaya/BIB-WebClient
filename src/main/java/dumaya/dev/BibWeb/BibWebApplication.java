package dumaya.dev.BibWeb;


import dumaya.dev.BibWeb.modelAPI.Usager;
import dumaya.dev.BibWeb.modelAPI.Role;
import dumaya.dev.BibWeb.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.HashSet;

@EnableFeignClients("dumaya.dev.BibWeb")
@SpringBootApplication
public class BibWebApplication {

	@Autowired
	public UsagerService usagerService;

	public static void main(String[] args) {
		SpringApplication.run(BibWebApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Usager usager = new Usager();
		usager.setNom("ADMIN");
		usager.setPrenom("ADMIN");
		usager.setEmail("admin@cnss.ne");
		usager.setPassword("$2a$10$fE7BKQcc.tesDzaptjL8luXZB6MV5rvUJ13ub5aVYKqnoPmMqYd8m");
		usager.setActive(true);
		//Role
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("ADMIN");
		roles.add(role);
		usager.setRoles(roles);
		usagerService.saveUsager(usager);
	}

}
