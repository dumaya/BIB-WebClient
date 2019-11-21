package dumaya.dev.BibWeb.modelForm;

public class OuvrageCherche {
    private int id;

    private int idReference;

    private String titre;

    private String auteur;

    private boolean dispoPret;

    private String emplacement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReference() {
        return idReference;
    }

    public void setIdReference(int idReference) {
        this.idReference = idReference;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
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

    public boolean isDispoPret() {
        return dispoPret;
    }

    public void setDispoPret(boolean dispoPret) {
        this.dispoPret = dispoPret;
    }
}

