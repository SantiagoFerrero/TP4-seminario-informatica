public class ProductoElectrodomestico extends Producto {
    private int garantiaMeses;

    public ProductoElectrodomestico(String nombre, double precio, int cantidad, int garantiaMeses) {
        super(nombre, precio, cantidad);
        this.garantiaMeses = garantiaMeses;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Producto: " + getNombre() + ", Precio: $" + getPrecio() +
                ", Cantidad: " + getCantidad() + ", Garantía: " + garantiaMeses + " meses");
    }
}
