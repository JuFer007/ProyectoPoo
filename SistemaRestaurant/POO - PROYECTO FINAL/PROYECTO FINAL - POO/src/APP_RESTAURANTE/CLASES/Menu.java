package APP_RESTAURANTE.CLASES;

public class Menu {
    private int idMenu;
    private String nombreMenu;
    private int cantidadPlatos;
    private String categoriaMenu;

    public Menu(int idMenu, String nombreMenu, int cantidadPlatos, String categoriaMenu) {
        this.idMenu = idMenu;
        this.nombreMenu = nombreMenu;
        this.cantidadPlatos = cantidadPlatos;
        this.categoriaMenu = categoriaMenu;
    }

    public Menu() {
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    public int getCantidadPlatos() {
        return cantidadPlatos;
    }

    public void setCantidadPlatos(int cantidadPlatos) {
        this.cantidadPlatos = cantidadPlatos;
    }

    public String getCategoriaMenu() {
        return categoriaMenu;
    }

    public void setCategoriaMenu(String categoriaMenu) {
        this.categoriaMenu = categoriaMenu;
    }
}
