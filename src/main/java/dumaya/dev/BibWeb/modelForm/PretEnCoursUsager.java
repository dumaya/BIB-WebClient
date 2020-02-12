package dumaya.dev.BibWeb.modelForm;

public class PretEnCoursUsager {
    private int id;
    private int idOuvrage;
    private int idUsager;
    private String dateFin;
    private String dateRetour;
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

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(String dateRetour) {
        this.dateRetour = dateRetour;
    }
}
