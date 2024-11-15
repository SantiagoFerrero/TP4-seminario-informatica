import java.time.LocalDateTime;

public class SalidaProducto {
    private int idSalida;
    private String nombreProducto;
    private int cantidadSalida;
    private LocalDateTime fechaSalida;
    private String usuario;

    public SalidaProducto(int idSalida, String nombreProducto, int cantidadSalida, LocalDateTime fechaSalida, String usuario) {
        this.idSalida = idSalida;
        this.nombreProducto = nombreProducto;
        this.cantidadSalida = cantidadSalida;
        this.fechaSalida = fechaSalida;
        this.usuario = usuario;
    }

    // Getters y Setters
    public int getIdSalida() {
        return idSalida;
    }

    public void setIdSalida(int idSalida) {
        this.idSalida = idSalida;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(int cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    // Método para mostrar la información de la salida
    public void mostrarInfo() {
        System.out.println("Salida ID: " + idSalida + ", Producto: " + nombreProducto + ", Cantidad Salida: " + cantidadSalida +
                           ", Fecha de Salida: " + fechaSalida + ", Usuario: " + usuario);
    }
}
