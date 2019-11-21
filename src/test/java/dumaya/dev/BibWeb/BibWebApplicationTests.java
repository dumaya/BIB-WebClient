package dumaya.dev.BibWeb;

import dumaya.dev.BibWeb.config.OuvrageControllerFeignClientBuilder;
import dumaya.dev.BibWeb.modelAPI.Ouvrage;
import dumaya.dev.BibWeb.modelAPI.OuvrageRessource;
import dumaya.dev.BibWeb.service.OuvrageClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(JUnit4.class)
public class BibWebApplicationTests {

	private OuvrageClient ouvrageClient;
	private static Logger log = LoggerFactory.getLogger(BibWebApplicationTests.class);


	@Before
	public void setup() {
		OuvrageControllerFeignClientBuilder feignClientBuilder = new OuvrageControllerFeignClientBuilder();
		ouvrageClient = feignClientBuilder.getOuvrageClient();
	}

	@Test
	public void givenOuvrageClient_shouldRunSuccessfully() throws Exception {
		log.info("debut test");
		List<Ouvrage> ouvrages = ouvrageClient.findAll()
				.stream()
				.map(OuvrageRessource::getOuvrage)
				.collect(Collectors.toList());
		assertTrue(ouvrages.size() > 0);
		log.info("taille ouvrage", ouvrages.size());
		log.info("{}", ouvrages);
	}
}
