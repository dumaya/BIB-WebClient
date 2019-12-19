package dumaya.dev.BibWeb.controller;

import dumaya.dev.BibWeb.modelAPI.Usager;
import dumaya.dev.BibWeb.modelForm.PretEnCoursUsager;
import dumaya.dev.BibWeb.modelForm.Utilisateur;
import dumaya.dev.BibWeb.service.APIClientService;
import dumaya.dev.BibWeb.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PretController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PretController.class);
    private final UtilisateurService utilisateurService;
    private final APIClientService clientService;

    public PretController(UtilisateurService utilisateurService, APIClientService clientService) {
        this.utilisateurService = utilisateurService;
        this.clientService = clientService;
    }

    @GetMapping("/mesprets")
    public String mesPrets(Model model) {
        LOGGER.debug("page consultation de mes prets en cours");
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurService.findUtilisateurByEmail(auth.getName());
        Usager usager = clientService.recupererUnUsagerService(utilisateur.getId());
        List<PretEnCoursUsager> prets = clientService.getListePretEnCours(usager.getId());
        model.addAttribute("prets", prets);
        return "mesprets";
    }
}

