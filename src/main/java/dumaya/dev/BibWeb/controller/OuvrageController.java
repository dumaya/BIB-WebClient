package dumaya.dev.BibWeb.controller;

import dumaya.dev.BibWeb.modelForm.OuvrageCherche;
import dumaya.dev.BibWeb.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OuvrageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OuvrageController.class);

    private final ClientService clientService;

    public OuvrageController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/ajoutouvrage")
    public String ajoutouvrage(Model model) {

        /* Formulaire de création d'un ouvrage */
        LOGGER.debug("Init formulaire ouvrage");
        OuvrageCherche ouvrage = new OuvrageCherche();
        model.addAttribute("ouvrage", ouvrage);

        return "ajoutouvrage";
    }


    @GetMapping("/rechercheouvrage")
    public String rechercheouvrage(Model model) {
        LOGGER.debug("page rechercher les ouvrages");
        OuvrageCherche ouvrageCherche = new OuvrageCherche();
        List<OuvrageCherche> ouvrages= new ArrayList<>();
        model.addAttribute("ouvrageCherche", ouvrageCherche);
        model.addAttribute("ouvrages", ouvrages);
        return "rechercheouvrage";
    }

    @PostMapping(value = "/rechercheouvrage/recherche")
    public String rechercheouvrageRecherche (Model model, @ModelAttribute("ouvrageCherche") OuvrageCherche ouvrageCherche) {
        LOGGER.debug("lancement d'une recherche");
        List<OuvrageCherche> ouvrages= clientService.getOuvrageCherche();
        model.addAttribute("ouvrageCherche", ouvrageCherche);
        model.addAttribute("ouvrages", ouvrages);
        return "rechercheouvrage";
    }

}

