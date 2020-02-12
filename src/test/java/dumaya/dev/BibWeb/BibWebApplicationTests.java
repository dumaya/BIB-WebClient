package dumaya.dev.BibWeb;

import dumaya.dev.BibWeb.exceptions.APIException;
import dumaya.dev.BibWeb.modelAPI.Ouvrage;
import dumaya.dev.BibWeb.modelAPI.Pret;
import dumaya.dev.BibWeb.proxies.BibAppProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BibWebApplicationTests {

	private static Logger log = LoggerFactory.getLogger(BibWebApplicationTests.class);
	@Autowired
	private BibAppProxy bibAppProxy;
	@Test
	public void listeOuvrageNonVide() throws Exception {
		log.info("debut test");
		List<Ouvrage> ouvrages = bibAppProxy.listeDesOuvrages();
		assertNotNull(ouvrages);
		assertTrue(ouvrages.size() > 0);

	}
	@Test
	public void listePretsParUser() throws Exception {
		log.info("debut test listePretsParUser");
		try {
		List<Pret> prets = bibAppProxy.pretEnCoursUsager(300);

		assertTrue(prets.size() > 0);
		} catch (RuntimeException e) {
			throw new APIException("Get Liste des prets en cours d'un usager" ,e.getMessage(),e.getStackTrace().toString());
		}
	}
	@Test
	public void accederUnOuvrageNonTrouvé() throws Exception {
		log.info("debut test");
		//try {
			Ouvrage ouvrage = bibAppProxy.recupererUnOuvrage(300);
		//} catch (Exception e) {
		//	assertEquals("Non trouvé ",e.getMessage());
		//}
	}
}
