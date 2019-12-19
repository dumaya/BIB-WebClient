package dumaya.dev.BibWeb.controller;

import dumaya.dev.BibWeb.modelAPI.Pret;
import dumaya.dev.BibWeb.modelForm.OuvrageCherche;
import dumaya.dev.BibWeb.modelForm.PretEnCours;
import dumaya.dev.BibWeb.service.APIClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PretController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PretController.class);

    private final APIClientService clientService;

    public PretController(APIClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/mesprets")
    public String mesPrets(Model model) {
        LOGGER.debug("page consultation de mes prets en cours");
        List<PretEnCours> prets = clientService.getListePretEnCours();
        model.addAttribute("prets", prets);
        return "mesprets";
    }
}

