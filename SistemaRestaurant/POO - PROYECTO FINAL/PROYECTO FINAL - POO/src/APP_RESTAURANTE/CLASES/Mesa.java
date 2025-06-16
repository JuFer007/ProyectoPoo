package APP_RESTAURANTE.CLASES;

public class Mesa {
    private int idMesa;
    private int numeroMesa;
    private int capacidadMesa;
    private String estadoMesa;
    private int idMesero;
    private String estadoReserva;
    private int idClienteReserva;

    public Mesa() {
    }

    public Mesa(int idMesa, int numeroMesa, int capacidadMesa, String estadoMesa, int idMesero, String estadoReserva, int idClienteReserva) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.capacidadMesa = capacidadMesa;
        this.estadoMesa = estadoMesa;
        this.idMesero = idMesero;
        this.estadoReserva = estadoReserva;
        this.idClienteReserva = idClienteReserva;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int getCapacidadMesa() {
        return capacidadMesa;
    }

    public void setCapacidadMesa(int capacidadMesa) {
        this.capacidadMesa = capacidadMesa;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

    public int getIdMesero() {
        return idMesero;
    }

    public void setIdMesero(int idMesero) {
        this.idMesero = idMesero;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public int getIdClienteReserva() {
        return idClienteReserva;
    }

    public void setIdClienteReserva(int idClienteReserva) {
        this.idClienteReserva = idClienteReserva;
    }
}