package APP_RESTAURANTE.CLASES;

public class DetallePedido {
    private int idDetallePedido;
    private int idPedido;
    private int numeroPlato;
    private int cantidad;
    private double precioUnitario;
    private String nombrePlato;
    private double subTotal;

    public DetallePedido(int idPedido, int numeroPlato, int cantidad, double precioUnitario, String nombrePlato, double subTotal) {
        this.idPedido = idPedido;
        this.numeroPlato = numeroPlato;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.nombrePlato = nombrePlato;
        this.subTotal = subTotal;
    }

    public int getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(int idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getNumeroPlato() {
        return numeroPlato;
    }

    public void setNumeroPlato(int numeroPlato) {
        this.numeroPlato = numeroPlato;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
