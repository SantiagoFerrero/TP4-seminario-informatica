public class ProductoAlimento extends Producto {
    private String fechaCaducidad;

    public ProductoAlimento(String nombre, double precio, int cantidad, String fechaCaducidad) {
        super(nombre, precio, cantidad);
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Producto: " + getNombre() + ", Precio: $" + getPrecio() +
                ", Cantidad: " + getCantidad() + ", Fecha de Caducidad: " + fechaCaducidad);
    }
}