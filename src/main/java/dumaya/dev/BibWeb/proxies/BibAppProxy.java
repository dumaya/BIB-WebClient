package dumaya.dev.BibWeb.proxies;

import dumaya.dev.BibWeb.modelAPI.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "BibApp", url = "localhost:8001")
public interface BibAppProxy {

    @GetMapping(value = "/ouvrages")
    List<Ouvrage> listeDesOuvrages();

    @GetMapping(value = "/ouvrages/{id}")
    Ouvrage recupererUnOuvrage (@PathVariable("id") int id);

    @GetMapping(value = "/usagers/{id}")
    Usager recupererUnUsager (@PathVariable("id") int id);

    @GetMapping(value = "/usagers/email/{email}")
    Usager recupererUnUsagerParEmail (@PathVariable("email") String email);

    @PostMapping(value = "/usagers/")
    Usager creerUnUsager (@RequestBody Usager usager);

    @GetMapping(value = "/references")
    List<Reference> listeDesReferences();

    @GetMapping(value = "/references/{id}")
    Reference recupererUneReference (@PathVariable("id") int id);

    @GetMapping(value = "/prets")
    List<Pret> listeDesPrets();

    @GetMapping(value = "/prets/{id}")
    Pret recupererUnPret(@PathVariable("id") int id);

    @GetMapping(value = "/prets/ouvrages/{id}")
    Pret pretEnCoursOuvrage(@PathVariable("id") int id);

    @GetMapping(value = "/prets/usagers/{id}")
    List <Pret> pretEnCoursUsager(@PathVariable("id") int id);

    @GetMapping(value = "/roles/role/{role}")
    Role roleParRole(@PathVariable("role") String role);
}
