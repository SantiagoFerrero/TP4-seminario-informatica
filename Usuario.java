public class Usuario {
    private int idUsuario;
    private String nombre;
    private String rol;

    public Usuario(int idUsuario, String nombre, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public void mostrarInfo() {
        System.out.println("Usuario ID: " + idUsuario + ", Nombre: " + nombre + ", Rol: " + rol);
    }
}

