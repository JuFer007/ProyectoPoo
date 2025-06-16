package APP_RESTAURANTE.CLASES;
import java.util.ArrayList;

public class Usuario {
    private String nombreUsuario;
    private String contraseña;
    private String rol;
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    public Usuario() {
        inicializarUsuariosDelSistema();
    }

    public Usuario(String nombreUsuario, String contraseña, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    //Array de la lista de usuarios
    private void inicializarUsuariosDelSistema () {
        if (usuarios.isEmpty()) {
            usuarios.add(new Usuario("AdmiPolsent", "polsent123", "Administrador"));
            usuarios.add(new Usuario("AdmiJunior", "Fjunior07!", "Administrador"));
            usuarios.add(new Usuario("Mesero01Piero", "mesero0001", "Mesero"));
            usuarios.add(new Usuario("Mesero02Mario", "marioMesero09", "Mesero"));
            usuarios.add(new Usuario("Repartidor2CarlosSa", "carlosSan45", "Repartidor"));
            usuarios.add(new Usuario("Repartidor1JuanPe", "juanPe123", "Repartidor"));
        }
    }

    //Verificar usuario y contraseña
    public String verificarUsuario (String nombreUsuario, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getContraseña().equals(contraseña)) {
                return usuario.getRol();
            }
        }
        return null;
    }
}
