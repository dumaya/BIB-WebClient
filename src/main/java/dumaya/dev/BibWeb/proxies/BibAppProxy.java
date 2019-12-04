package dumaya.dev.BibWeb.proxies;

import dumaya.dev.BibWeb.modelAPI.Ouvrage;
import dumaya.dev.BibWeb.modelAPI.Pret;
import dumaya.dev.BibWeb.modelAPI.Reference;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "BibApp", url = "localhost:8001")
public interface BibAppProxy {

    @GetMapping(value = "/ouvrages")
    List<Ouvrage> listeDesOuvrages();

    @GetMapping(value = "/ouvrages/{id}")
    Ouvrage recupererUnOuvrage (@PathVariable("id") int id);

    @GetMapping(value = "/references")
    List<Reference> listeDesReferences();

    @GetMapping(value = "/references/{id}")
    Reference recupererUneReference (@PathVariable("id") int id);

    @GetMapping(value = "/prets")
    List<Pret> listeDesPrets();

    @GetMapping(value = "/prets/{id}")
    Pret recupererUnPret(@PathVariable("id") int id);

    @GetMapping(value = "/prets/ouvrage/{id}")
    List<Pret> listeDesPretsPourOuvrage(@PathVariable("id") int id);
}
