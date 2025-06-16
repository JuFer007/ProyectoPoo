package APP_RESTAURANTE.CLASES;
import java.sql.Time;
import java.sql.Date;

public class Pedido {
    private int idPedido;
    private String codigoPedido;
    private Date fechaPedido;
    private Time horaPedido;
    private double totalPedido;
    private int idCliente;

    public Pedido(String codigoPedido, Date fechaPedido, Time horaPedido, double totalPedido, int idCliente) {
        this.codigoPedido = codigoPedido;
        this.fechaPedido = fechaPedido;
        this.horaPedido = horaPedido;
        this.totalPedido = totalPedido;
        this.idCliente = idCliente;
    }

    public Pedido() {
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Time getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(Time horaPedido) {
        this.horaPedido = horaPedido;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
