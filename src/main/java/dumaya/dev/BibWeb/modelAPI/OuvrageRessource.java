package dumaya.dev.BibWeb.modelAPI;

public class OuvrageRessource {

    private Ouvrage ouvrage;

    public OuvrageRessource(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }
}
