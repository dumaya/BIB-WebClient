package dumaya.dev.BibWeb.controller;

import dumaya.dev.BibWeb.modelAPI.Usager;
import dumaya.dev.BibWeb.modelForm.OuvrageCherche;
import dumaya.dev.BibWeb.service.APIClientService;
import dumaya.dev.BibWeb.service.UsagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OuvrageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OuvrageController.class);
    private final UsagerService usagerService;
    private final APIClientService clientService;

    public OuvrageController(UsagerService usagerService, APIClientService clientService) {
        this.usagerService = usagerService;
        this.clientService = clientService;
    }
    @RequestMapping("/")
    public String racine() {
        return "index";
    }
    @RequestMapping("/index")
    public String index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usager usager = usagerService.findUsagerByEmail(auth.getName());
        model.addAttribute("usager", usager);
        return "index";
    }

    @GetMapping("/rechercheouvrage")
    public String rechercheouvrage(Model model) {
        LOGGER.debug("page rechercher les ouvrages");
        OuvrageCherche ouvrageCherche = new OuvrageCherche();
        List<OuvrageCherche> ouvrages= new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usager usager = usagerService.findUsagerByEmail(auth.getName());
        model.addAttribute("ouvrageCherche", ouvrageCherche);
        model.addAttribute("ouvrages", ouvrages);
        model.addAttribute("usager", usager);
        return "rechercheouvrage";
    }

    @PostMapping(value = "/rechercheouvrage/recherche")
    public String rechercheouvrageRecherche (Model model, @ModelAttribute("ouvrageCherche") OuvrageCherche ouvrageCherche) {
        LOGGER.debug("lancement d'une recherche");
        List<OuvrageCherche> ouvrages= clientService.getListeOuvragesFiltree(ouvrageCherche);
        model.addAttribute("ouvrageCherche", ouvrageCherche);
        model.addAttribute("ouvrages", ouvrages);
        return "rechercheouvrage";
    }

}

