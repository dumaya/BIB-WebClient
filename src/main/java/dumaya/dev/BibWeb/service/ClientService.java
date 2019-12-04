package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.exceptions.APIException;
import dumaya.dev.BibWeb.exceptions.NotFoundException;
import dumaya.dev.BibWeb.modelAPI.Ouvrage;
import dumaya.dev.BibWeb.modelAPI.Pret;
import dumaya.dev.BibWeb.modelAPI.Reference;
import dumaya.dev.BibWeb.modelForm.OuvrageCherche;
import dumaya.dev.BibWeb.proxies.BibAppProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private BibAppProxy bibAppProxy;

    private Reference recupererUneReferenceService (int id) {
        try {
            Reference reference = bibAppProxy.recupererUneReference(id);
            return reference;
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Reference par id" ,e.getMessage(),e.getStackTrace().toString());
        }
    }
    private Pret recupererUnPretService (int id) {
        try {
            List<Pret>  listePret = bibAppProxy.listeDesPretsPourOuvrage(id);
            return pret;
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Pret par id" ,e.getMessage(),e.getStackTrace().toString());
        }
    }
    private List<OuvrageCherche> getListeOuvrages() {

        List<OuvrageCherche> ouvrageChercheListe = new ArrayList<>();
        try {
            List<Ouvrage> ouvrages = bibAppProxy.listeDesOuvrages();

        for (Ouvrage ouvrage: ouvrages) {
            Reference reference = recupererUneReferenceService(ouvrage.getIdReference());
            Pret pret = recupererUnPretService(ouvrage.getId());
            OuvrageCherche ouvrageCherche = new OuvrageCherche();
            ouvrageCherche.setId(ouvrage.getId());
            ouvrageCherche.setIdReference(ouvrage.getIdReference());
            if (reference != null ) {
                ouvrageCherche.setAuteur(reference.getAuteur());
                ouvrageCherche.setTitre(reference.getTitre());
            } else {
                ouvrageCherche.setAuteur("Auteur non trouvé");
                ouvrageCherche.setTitre("Titre non trouvé");
            }
            if (null != pret) {
                if (pret.getDateFin().after(new Date())) {
                    ouvrageCherche.setDispoPret(false);
                } else {
                    ouvrageCherche.setDispoPret(true);
                }
            } else {
                ouvrageCherche.setDispoPret(true);
            }
            ouvrageCherche.setEmplacement(ouvrage.getEmplacement());
            ouvrageChercheListe.add(ouvrageCherche);
        }
        return ouvrageChercheListe;

        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Ouvrages" ,e.getMessage(),e.getStackTrace().toString());
        }
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
