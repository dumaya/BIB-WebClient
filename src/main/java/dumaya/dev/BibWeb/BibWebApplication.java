package dumaya.dev.BibWeb;


import dumaya.dev.BibWeb.modelForm.Role;
import dumaya.dev.BibWeb.modelForm.Utilisateur;
import dumaya.dev.BibWeb.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.HashSet;

@EnableFeignClients("dumaya.dev.BibWeb")
@SpringBootApplication
public class BibWebApplication {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	public static void main(String[] args) {
		SpringApplication.run(BibWebApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setName("ADMIN");
		utilisateur.setLastName("ADMIN");
		utilisateur.setEmail("admin@cnss.ne");
		utilisateur.setPassword("$2a$10$fE7BKQcc.tesDzaptjL8luXZB6MV5rvUJ13ub5aVYKqnoPmMqYd8m");
		utilisateur.setActive(true);
		//Role
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("ADMIN");
		roles.add(role);
		utilisateur.setRoles(roles);
		utilisateurRepository.save(utilisateur);
	}

}
