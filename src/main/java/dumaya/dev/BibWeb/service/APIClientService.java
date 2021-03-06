package dumaya.dev.BibWeb.service;

import dumaya.dev.BibWeb.exceptions.APIException;
import dumaya.dev.BibWeb.exceptions.NotFoundException;
import dumaya.dev.BibWeb.modelAPI.*;
import dumaya.dev.BibWeb.modelForm.OuvrageCherche;
import dumaya.dev.BibWeb.modelForm.PretEnCoursUsager;
import dumaya.dev.BibWeb.proxies.BibAppProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class APIClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIClientService.class);

    @Autowired
    private BibAppProxy bibAppProxy;

    /**
     * @param usager
     * @return usager créé
     */
    public Usager creerUsager (@Valid Usager usager) {
        LOGGER.debug("creer usager");
        try {
            LOGGER.debug("Appel API usager");
            Usager usagerExistant = bibAppProxy.recupererUnUsager(usager.getId());
            LOGGER.info("Usager déjà existant pour cet id Web");
            return usagerExistant;
        } catch (NotFoundException e) {
            try {
                Usager usagerACreer = new Usager();
                usagerACreer.setEmail(usager.getEmail());
                usagerACreer.setNom(usager.getNom());
                usagerACreer.setPrenom(usager.getPrenom());
                usagerACreer.setId(usager.getId());
                usagerACreer.setPassword(usager.getPassword());
                usagerACreer.setActive(usager.isActive());
                usagerACreer.setRoles(usager.getRoles());
                Usager usagerCree = bibAppProxy.creerUnUsager(usagerACreer);
                return  usagerCree;
            } catch (RuntimeException ex) {
                throw new APIException("Post Usager" ,ex.getMessage(),ex.getStackTrace().toString());
            }
        } catch (RuntimeException e) {
            throw new APIException("Post Usager" ,e.getMessage(),e.getStackTrace().toString());
        }
    }

    /**
     * @param email
     * @return usager trouvé
     */
    public Usager recupererUnUsagerParEmail(String email) {
        try {
            LOGGER.debug("Appel API usager par mail");
            Usager usager = bibAppProxy.recupererUnUsagerParEmail(email);
            return usager;
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get usager par email" ,e.getMessage(),e.getStackTrace().toString());
        }
    }
    private Ouvrage recupererUnOuvrageService (int id) {
        try {
            LOGGER.debug("Appel API ouvrage");
            Ouvrage ouvrage = bibAppProxy.recupererUnOuvrage(id);
            return ouvrage;
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Ouvrage par id" ,e.getMessage(),e.getStackTrace().toString());
        }
    }
    private Reference recupererUneReferenceService (int id) {
        try {
            LOGGER.debug("Appel API reference");
            Reference reference = bibAppProxy.recupererUneReference(id);
            return reference;
        } catch (NotFoundException e) {
            Reference referenceNonTrouvée = new Reference();
            referenceNonTrouvée.setAuteur("Auteur non trouvé");
            referenceNonTrouvée.setTitre("Titre non trouvé");
            return referenceNonTrouvée;
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
            LOGGER.debug("Appel API pret  ouvrage");
            Pret pretEnCours = bibAppProxy.pretEnCoursOuvrage(id);
            return pretEnCours;
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Pret par id" ,e.getMessage(),e.getStackTrace().toString());
        }
    }

    /**
     * @param id id du pret
     */
    public void prolongerUnPretService (int id) {
        try {
            LOGGER.debug("Appel API prolonger");
            bibAppProxy.prolongerPret(id);
        } catch (NotFoundException e) {
            LOGGER.warn("Prolongation sur pret non trouvé");
        } catch (RuntimeException e) {
            throw new APIException("Put prolongation Pret par id" ,e.getMessage(),e.getStackTrace().toString());
        }
    }

    private List<OuvrageCherche> getListeOuvrages() {

        List<OuvrageCherche> ouvrageChercheListe = new ArrayList<>();
        try {
            LOGGER.debug("Appel API usager");
            List<Ouvrage> ouvrages = bibAppProxy.listeDesOuvrages();

        for (Ouvrage ouvrage: ouvrages) {
            Reference reference = recupererUneReferenceService(ouvrage.getIdReference());
            Pret pret = recupererUnPretService(ouvrage.getId());
            OuvrageCherche ouvrageCherche = new OuvrageCherche();
            ouvrageCherche.setId(ouvrage.getId());
            ouvrageCherche.setIdReference(ouvrage.getIdReference());
            ouvrageCherche.setAuteur(reference.getAuteur());
            ouvrageCherche.setTitre(reference.getTitre());

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

    /**
     * @param ouvrageCherche
     * @return liste des ouvrages (au format formulaire) filtrée
     */
    public List<OuvrageCherche> getListeOuvragesFiltree(OuvrageCherche ouvrageCherche) {
        LOGGER.debug("liste ouvrage filtree");

        List<OuvrageCherche> ouvrageChercheListe = getListeOuvrages();
        List<OuvrageCherche> ouvrageChercheListeFiltree = new ArrayList<>();

        for (OuvrageCherche ouvrage: ouvrageChercheListe) {
            if (null != ouvrageCherche.getAuteur() && !ouvrageCherche.getAuteur().isEmpty()
                && null != ouvrageCherche.getTitre() && !ouvrageCherche.getTitre().isEmpty() ) {
                if (ouvrage.getAuteur().toLowerCase().contains(ouvrageCherche.getAuteur().toLowerCase())
                && ouvrage.getTitre().toLowerCase().contains(ouvrageCherche.getTitre().toLowerCase())
                && ouvrage.isDispoPret() == ouvrageCherche.isDispoPret()) {
                    ouvrageChercheListeFiltree.add(ouvrage);
                }
            }
            if (null != ouvrageCherche.getAuteur() && !ouvrageCherche.getAuteur().isEmpty()
                    && (null == ouvrageCherche.getTitre() || ouvrageCherche.getTitre().isEmpty()) ) {
                if (ouvrage.getAuteur().toLowerCase().contains(ouvrageCherche.getAuteur().toLowerCase())
                        && ouvrage.isDispoPret() == ouvrageCherche.isDispoPret()) {
                    ouvrageChercheListeFiltree.add(ouvrage);
                }
            }
            if ((null == ouvrageCherche.getAuteur() || ouvrageCherche.getAuteur().isEmpty())
                    && null != ouvrageCherche.getTitre() && !ouvrageCherche.getTitre().isEmpty() ) {
                if (ouvrage.getTitre().toLowerCase().contains(ouvrageCherche.getTitre().toLowerCase())
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

    /**
     * @param idUsager
     * @return tous les prets en cours de cet usager
     */
    public List<PretEnCoursUsager> getListePretEnCours(int idUsager) {
        LOGGER.debug("liste pret en cours");
        List<PretEnCoursUsager> pretEnCoursListe = new ArrayList<>();
        try {
            LOGGER.debug("Appel API pret en cours");
            List<Pret> prets = bibAppProxy.pretEnCoursUsager(idUsager);
            for (Pret pret: prets) {
                Ouvrage ouvrage = recupererUnOuvrageService(pret.getIdOuvrage());
                Reference reference = recupererUneReferenceService(ouvrage.getIdReference());
                PretEnCoursUsager pretEnCoursUsager = new PretEnCoursUsager();
                pretEnCoursUsager.setId(pret.getId());
                pretEnCoursUsager.setIdOuvrage(pret.getIdOuvrage());
                pretEnCoursUsager.setIdUsager(pret.getIdUsager());
                pretEnCoursUsager.setAuteur(reference.getAuteur());
                pretEnCoursUsager.setTitre(reference.getTitre());
                SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy 'à' hh:mm:ss");
                if (null != pret.getDateFin()) {
                    pretEnCoursUsager.setDateFin(formater.format(pret.getDateFin()));
                }
                if (null != pret.getDateRetour()) {
                    pretEnCoursUsager.setDateRetour(formater.format(pret.getDateRetour()));
                }
                pretEnCoursUsager.setTopProlongation(pret.getTopProlongation());

                pretEnCoursListe.add(pretEnCoursUsager);
            }
            return pretEnCoursListe;
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Liste des prets en cours d'un usager" ,e.getMessage(),e.getStackTrace().toString());
        }
    }

    /**
     * @param role
     * @return role correspondant au code role voulu
     */
    public Role recupererUnRoleParRole(String role) {
        try {
            LOGGER.debug("Appel API role");
            Role roleTrouve = bibAppProxy.roleParRole(role);
            return roleTrouve;
        } catch (NotFoundException e) {
            return null;
        } catch (RuntimeException e) {
            throw new APIException("Get Role par role" ,e.getMessage(),e.getStackTrace().toString());
        }
    }
}
