package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.modelAPI.Usager;

public interface UsagerService {
	public Usager findUsagerByEmail(String email);
	public void saveUsager(Usager usager);
}
