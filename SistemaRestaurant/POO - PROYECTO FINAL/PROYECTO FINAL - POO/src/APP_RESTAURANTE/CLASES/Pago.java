package APP_RESTAURANTE.CLASES;
import java.sql.Date;

public class Pago {
    int idPago;
    double montoPago;
    String metodoPago;
    Date fechaPago;
    String estadoPago;
    int idPedido;

    public Pago(int idPago, double montoPago, String metodoPago, Date fechaPago, String estadoPago, int idPedido) {
        this.idPago = idPago;
        this.montoPago = montoPago;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
        this.estadoPago = estadoPago;
        this.idPedido = idPedido;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public double getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(double montoPago) {
        this.montoPago = montoPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
}