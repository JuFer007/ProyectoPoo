package APP_RESTAURANTE.CLASES;

public class Repartidor extends Empleado {

    public Repartidor(String nombreEmpleado, double salarioEmpleado, String dniEmpleado, String cargoEmpleado, int idEmpleado) {
        super(nombreEmpleado, salarioEmpleado, dniEmpleado, cargoEmpleado, idEmpleado);
    }

    public Repartidor() {

    }

    @Override
    public int getIdEmpleado() {
        return super.getIdEmpleado();
    }

    @Override
    public void setIdEmpleado(int idEmpleado) {
        super.setIdEmpleado(idEmpleado);
    }

    @Override
    public String getNombreEmpleado() {
        return super.getNombreEmpleado();
    }

    @Override
    public void setNombreEmpleado(String nombreEmpleado) {
        super.setNombreEmpleado(nombreEmpleado);
    }

    @Override
    public double getSalarioEmpleado() {
        return super.getSalarioEmpleado();
    }

    @Override
    public void setSalarioEmpleado(double salarioEmpleado) {
        super.setSalarioEmpleado(salarioEmpleado);
    }

    @Override
    public String getDniEmpleado() {
        return super.getDniEmpleado();
    }

    @Override
    public void setDniEmpleado(String dniEmpleado) {
        super.setDniEmpleado(dniEmpleado);
    }

    @Override
    public String getCargoEmpleado() {
        return super.getCargoEmpleado();
    }

    @Override
    public void setCargoEmpleado(String cargoEmpleado) {
        super.setCargoEmpleado(cargoEmpleado);
    }
}
