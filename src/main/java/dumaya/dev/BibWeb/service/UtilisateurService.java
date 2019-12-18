package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.modelForm.Utilisateur;

public interface UtilisateurService {
	public Utilisateur findUtilisateurByEmail(String email);
	public void saveUtilisateur(Utilisateur utilisateur);
}
