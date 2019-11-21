package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.modelAPI.Ouvrage;
import dumaya.dev.BibWeb.modelAPI.OuvrageRessource;
import dumaya.dev.BibWeb.modelForm.OuvrageCherche;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private OuvrageClient ouvrageClient;

    public List<OuvrageCherche> getOuvrageCherche() {

        List<OuvrageCherche> ouvrageChercheListe = new ArrayList<>();
        List<Ouvrage> ouvrages = ouvrageClient.findAll().stream()
                .map(OuvrageRessource::getOuvrage)
                .collect(Collectors.toList());

        for (Ouvrage ouvrage: ouvrages) {
            OuvrageCherche ouvrageCherche = new OuvrageCherche();
            ouvrageCherche.setId(ouvrage.getId());
            ouvrageCherche.setIdReference(ouvrage.getIdReference());
            ouvrageCherche.setAuteur("Alexis");
            ouvrageCherche.setTitre("La coccina");
            ouvrageCherche.setDispoPret(true);
            ouvrageCherche.setEmplacement(ouvrage.getEmplacement());
            ouvrageChercheListe.add(ouvrageCherche);
        }

        return ouvrageChercheListe;
    }
}
