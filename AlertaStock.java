public class AlertaStock {
    private int idAlerta;
    private String nombreProducto;
    private int nivelStockActual;
    private int nivelStockMinimo;

    public AlertaStock(int idAlerta, String nombreProducto, int nivelStockActual, int nivelStockMinimo) {
        this.idAlerta = idAlerta;
        this.nombreProducto = nombreProducto;
        this.nivelStockActual = nivelStockActual;
        this.nivelStockMinimo = nivelStockMinimo;
    }

    public int getIdAlerta() {
        return idAlerta;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getNivelStockActual() {
        return nivelStockActual;
    }

    public int getNivelStockMinimo() {
        return nivelStockMinimo;
    }

    public void mostrarInfo() {
        System.out.println("Alerta ID: " + idAlerta + ", Producto: " + nombreProducto +
                           ", Stock Actual: " + nivelStockActual + ", Stock Mínimo: " + nivelStockMinimo);
    }
}
