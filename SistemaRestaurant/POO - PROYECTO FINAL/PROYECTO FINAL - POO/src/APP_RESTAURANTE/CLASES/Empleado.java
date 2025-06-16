package APP_RESTAURANTE.CLASES;

public class Empleado {
    private String nombreEmpleado;
    private double salarioEmpleado;
    private String dniEmpleado;
    private String cargoEmpleado;
    private int idEmpleado;

    public Empleado(String nombreEmpleado, double salarioEmpleado, String dniEmpleado, String cargoEmpleado, int idEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
        this.salarioEmpleado = salarioEmpleado;
        this.dniEmpleado = dniEmpleado;
        this.cargoEmpleado = cargoEmpleado;
        this.idEmpleado = idEmpleado;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Empleado() {
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public double getSalarioEmpleado() {
        return salarioEmpleado;
    }

    public void setSalarioEmpleado(double salarioEmpleado) {
        this.salarioEmpleado = salarioEmpleado;
    }

    public String getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(String dniEmpleado) {
        this.dniEmpleado = dniEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }
}