package dumaya.dev.BibWeb.modelAPI;

import java.util.Date;

public class Pret {

    private int id;

    private int idOuvrage;
    private int idUsager;
    private Date dateFin;
    private Boolean topProlongation;

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

    public Boolean getTopProlongation() {
        return topProlongation;
    }

    public void setTopProlongation(Boolean topProlongation) {
        this.topProlongation = topProlongation;
    }
}
