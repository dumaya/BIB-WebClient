package dumaya.dev.BibWeb.modelForm;

import java.util.Date;

public class PretEnCoursUsager {
    private int id;
    private int idOuvrage;
    private int idUsager;
    private Date dateFin;
    private Date dateRetour;
    private Boolean topProlongation;
    private String titre;
    private String auteur;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOuvrage() {
        return idOuvrage;
    }

    public void setIdOuvrage(int idOuvrage) {
        this.idOuvrage = idOuvrage;
    }

    public int getIdUsager() {
        return idUsager;
    }

    public void setIdUsager(int idUsager) {
        this.idUsager = idUsager;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Boolean getTopProlongation() {
        return topProlongation;
    }

    public void setTopProlongation(Boolean topProlongation) {
        this.topProlongation = topProlongation;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}
