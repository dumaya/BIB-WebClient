package dumaya.dev.BibWeb.repository;

import dumaya.dev.BibWeb.modelForm.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	 Utilisateur findByEmail(String email);
}
