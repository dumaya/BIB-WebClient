package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.modelAPI.Ouvrage;
import dumaya.dev.BibWeb.modelAPI.Pret;
import dumaya.dev.BibWeb.modelAPI.Reference;
import dumaya.dev.BibWeb.modelForm.OuvrageCherche;
import dumaya.dev.BibWeb.proxies.BibAppProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private BibAppProxy bibAppProxy;

    private List<OuvrageCherche> getListeOuvrages() {

        List<OuvrageCherche> ouvrageChercheListe = new ArrayList<>();
        List<Ouvrage> ouvrages = bibAppProxy.listeDesOuvrages();

        for (Ouvrage ouvrage: ouvrages) {
            Reference reference = bibAppProxy.recupererUneReference(ouvrage.getIdReference());
            Pret pret = bibAppProxy.recupererUnPret(ouvrage.getId());
            OuvrageCherche ouvrageCherche = new OuvrageCherche();
            ouvrageCherche.setId(ouvrage.getId());
            ouvrageCherche.setIdReference(ouvrage.getIdReference());
            ouvrageCherche.setAuteur(reference.getAuteur());
            ouvrageCherche.setTitre(reference.getTitre());
            if (null != pret && pret.getDateFin().after(new Date())){
                ouvrageCherche.setDispoPret(true);
            } else {
                ouvrageCherche.setDispoPret(false);
            }
            ouvrageCherche.setEmplacement(ouvrage.getEmplacement());
            ouvrageChercheListe.add(ouvrageCherche);
        }

        return ouvrageChercheListe;
    }

    public List<OuvrageCherche> getListeOuvragesFiltree(OuvrageCherche ouvrageCherche) {

        List<OuvrageCherche> ouvrageChercheListe = getListeOuvrages();
        List<OuvrageCherche> ouvrageChercheListeFiltree = new ArrayList<>();

        for (OuvrageCherche ouvrage: ouvrageChercheListe) {
            if (null != ouvrageCherche.getAuteur() && !ouvrageCherche.getAuteur().isEmpty()
                && null != ouvrageCherche.getTitre() && !ouvrageCherche.getTitre().isEmpty() ) {
                if (ouvrage.getAuteur().contains(ouvrageCherche.getAuteur())
                && ouvrage.getTitre().contains(ouvrageCherche.getTitre())
                && ouvrage.isDispoPret() == ouvrageCherche.isDispoPret()) {
                    ouvrageChercheListeFiltree.add(ouvrage);
                }
            }
            if (null != ouvrageCherche.getAuteur() && !ouvrageCherche.getAuteur().isEmpty()
                    && (null == ouvrageCherche.getTitre() || ouvrageCherche.getTitre().isEmpty()) ) {
                if (ouvrage.getAuteur().contains(ouvrageCherche.getAuteur())
                        && ouvrage.isDispoPret() == ouvrageCherche.isDispoPret()) {
                    ouvrageChercheListeFiltree.add(ouvrage);
                }
            }
            if ((null == ouvrageCherche.getAuteur() || ouvrageCherche.getAuteur().isEmpty())
                    && null != ouvrageCherche.getTitre() && !ouvrageCherche.getTitre().isEmpty() ) {
                if (ouvrage.getTitre().contains(ouvrageCherche.getTitre())
                        && ouvrage.isDispoPret() == ouvrageCherche.isDispoPret()) {
                    ouvrageChercheListeFiltree.add(ouvrage);
                }
            }
            if ((null == ouvrageCherche.getAuteur() || ouvrageCherche.getAuteur().isEmpty())
                    && (null == ouvrageCherche.getTitre() || ouvrageCherche.getTitre().isEmpty()) ) {
                if (ouvrage.isDispoPret() == ouvrageCherche.isDispoPret()) {
                    ouvrageChercheListeFiltree.add(ouvrage);
                }
            }
        }

        return ouvrageChercheListeFiltree;
    }
}
