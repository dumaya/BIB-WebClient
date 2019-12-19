package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.exceptions.APIException;
import dumaya.dev.BibWeb.exceptions.NotFoundException;
import dumaya.dev.BibWeb.modelAPI.Ouvrage;
import dumaya.dev.BibWeb.modelAPI.Pret;
import dumaya.dev.BibWeb.modelAPI.Reference;
import dumaya.dev.BibWeb.modelAPI.Usager;
import dumaya.dev.BibWeb.modelForm.OuvrageCherche;
import dumaya.dev.BibWeb.modelForm.PretEnCours;
import dumaya.dev.BibWeb.modelForm.Utilisateur;
import dumaya.dev.BibWeb.proxies.BibAppProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class APIClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIClientService.class);

    @Autowired
    private BibAppProxy bibAppProxy;

    public Usager creerUsager (Utilisateur utilisateur) {
        try {
            Usager usagerExistant = bibAppProxy.recupererUnUsager(utilisateur.getId());
            LOGGER.info("Usager déjà existant pour cet id Web");
            return usagerExistant;
        } catch (NotFoundException e) {
            try {
                Usager usagerACreer = new Usager();
                usagerACreer.setMail(utilisateur.getEmail());
                usagerACreer.setNom(utilisateur.getName());
                usagerACreer.setPrenom(utilisateur.getLastName());
                usagerACreer.setIdWeb(utilisateur.getId());
                Usager usagerCree = bibAppProxy.creerUnUsager(usagerACreer);
                return  usagerCree;
            } catch (RuntimeException ex) {
                throw new APIException("Post Usager" ,ex.getMessage(),ex.getStackTrace().toString());
            }
        } catch (RuntimeException e) {
            throw new APIException("Post Usager" ,e.getMessage(),e.getStackTrace().toString());
        }
    }

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

    /**
     * @param id id de l'ouvrage
     * @return Pret
     */
    private Pret recupererUnPretService (int id) {
        try {
            Pret pretEnCours = bibAppProxy.pretEnCours(id);
            return pretEnCours;
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Pret par id" ,e.getMessage(),e.getStackTrace().toString());
            //TODO logs
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

    public List<PretEnCours> getListePretEnCours() {
        //TODO aller chercher la liste des prets pour un usager
    }
}
