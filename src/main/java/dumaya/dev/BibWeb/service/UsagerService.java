package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.modelAPI.Usager;

/**
 * Interface d'accés aux données usager (pour l'authent)
 */
public interface UsagerService {
	public Usager findUsagerByEmail(String email);
	public void saveUsager(Usager usager);
}
