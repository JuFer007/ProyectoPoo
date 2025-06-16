package APP_RESTAURANTE.CLASES;

public class Plato {
    private String nombrePlato;
    private double precioPlato;
    private int idPlato;
    private int idMenu;
    private int numeroPlato;

    public Plato(String nombrePlato, double precioPlato, int idMenu) {
        this.nombrePlato = nombrePlato;
        this.precioPlato = precioPlato;
        this.idMenu = idMenu;
    }

    public Plato(String nombrePlato, double precioPlato, int idMenu, int numeroPlato) {
        this.nombrePlato = nombrePlato;
        this.precioPlato = precioPlato;
        this.idMenu = idMenu;
        this.numeroPlato = numeroPlato;
    }

    public Plato() {
    }

    public int getNumeroPlato() {
        return numeroPlato;
    }

    public void setNumeroPlato(int numeroPlato) {
        this.numeroPlato = numeroPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(double precioPlato) {
        this.precioPlato = precioPlato;
    }

    public int getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(int idPlato) {
        this.idPlato = idPlato;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public String toString() {
        return nombrePlato + " - S/." + precioPlato;
    }
}
