package dumaya.dev.BibWeb.modelAPI;


public class Ouvrage {

    private int id;

    private int idReference;

    private String emplacement;

    public Ouvrage(int id, int idReference, String emplacement) {
        this.id = id;
        this.idReference = idReference;
        this.emplacement = emplacement;
    }

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

    @Override
    public String toString() {
        return "Ouvrage{" +
                "id=" + id +
                ", idReference=" + idReference +
                ", emplacement='" + emplacement + '\'' +
                '}';
    }
}
