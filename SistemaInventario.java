import java.sql.*;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SistemaInventario {
    private Scanner scanner = new Scanner(System.in);
    private Connection conexion;

    public SistemaInventario() {
        conexion = DatabaseConnection.getConnection();
        if (conexion == null) {
            System.out.println("Error: no se pudo establecer la conexión a la base de datos.");
            System.exit(1);
        }
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Menú de Inventario ---");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Listar Productos");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Listar Usuarios");
            System.out.println("5. Listar Proveedores");
            System.out.println("6. Ver Productos con Alerta de Stock");
            System.out.println("7. Registrar Salida de Producto");
            System.out.println("8. Listar Salidas de Productos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();  // Limpiar buffer
                switch (opcion) {
                    case 1:
                        agregarProducto();
                        break;
                    case 2:
                        listarProductos();
                        break;
                    case 3:
                        eliminarProducto();
                        break;
                    case 4:
                        listarUsuarios();
                        break;
                    case 5:
                        listarProveedores();
                        break;
                    case 6:
                        verProductosConAlertaDeStock();
                        break;
                    case 7:
                        registrarSalidaProducto();
                        break;
                    case 8:
                        listarSalidasProducto();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.next(); // Limpiar entrada no válida
                opcion = -1;
            }
        } while (opcion != 0);

        DatabaseConnection.closeConnection();
    }
    public void agregarProducto() {
        try {
            System.out.println("Seleccione el tipo de producto:");
            System.out.println("1. Alimento");
            System.out.println("2. Electrodoméstico");
            int tipoProducto = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el precio del producto: ");
            double precio = scanner.nextDouble();

            System.out.print("Ingrese la cantidad en stock: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine();

            if (tipoProducto == 1) {
                System.out.print("Ingrese la fecha de caducidad (DD/MM/AAAA): ");
                String fechaCaducidad = scanner.nextLine();
                Producto nuevoProducto = new ProductoAlimento(nombre, precio, cantidad, fechaCaducidad);
                insertarProductoBD(nuevoProducto, "Alimento", fechaCaducidad, 0);
            } else if (tipoProducto == 2) {
                System.out.print("Ingrese los meses de garantía: ");
                int garantiaMeses = scanner.nextInt();
                Producto nuevoProducto = new ProductoElectrodomestico(nombre, precio, cantidad, garantiaMeses);
                insertarProductoBD(nuevoProducto, "Electrodoméstico", null, garantiaMeses);
            } else {
                System.out.println("Tipo de producto no válido.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un dato válido.");
            scanner.nextLine();
        }
    }

    private void insertarProductoBD(Producto producto, String tipo, String fechaCaducidad, int garantiaMeses) {
        try {
            String query = "INSERT INTO productos (nombre_producto, precio, cantidad_stock, tipo, fecha_caducidad, garantia_meses) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getCantidad());
            stmt.setString(4, tipo);
            stmt.setString(5, fechaCaducidad);
            stmt.setInt(6, garantiaMeses);
            stmt.executeUpdate();
            System.out.println("Producto agregado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar producto a la base de datos: " + e.getMessage());
        }
    }

    public void listarProductos() {
        try {
            String query = "SELECT * FROM productos";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (!rs.isBeforeFirst()) {
                System.out.println("El inventario está vacío.");
            } else {
                while (rs.next()) {
                    System.out.println("Producto: " + rs.getString("nombre_producto") +
                            ", Precio: $" + rs.getDouble("precio") +
                            ", Cantidad: " + rs.getInt("cantidad_stock") +
                            ", Tipo: " + rs.getString("tipo") +
                            (rs.getString("tipo").equals("Alimento") ? 
                            ", Fecha de caducidad: " + rs.getString("fecha_caducidad") :
                            ", Garantía: " + rs.getInt("garantia_meses") + " meses"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
    }

    public void eliminarProducto() {
        System.out.print("Ingrese el nombre del producto a eliminar: ");
        String nombreProducto = scanner.nextLine();

        try {
            String query = "DELETE FROM productos WHERE nombre_producto = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, nombreProducto);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto eliminado exitosamente.");
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }
public void listarUsuarios() {
    try {
        String query = "SELECT * FROM usuarios";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (!rs.isBeforeFirst()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("rol_usuario"));
                usuario.mostrarInfo();
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al listar usuarios: " + e.getMessage());
    }
}

public void listarProveedores() {
    try {
        String query = "SELECT * FROM proveedores";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (!rs.isBeforeFirst()) {
            System.out.println("No hay proveedores registrados.");
        } else {
            while (rs.next()) {
                Proveedor proveedor = new Proveedor(rs.getInt("id_proveedor"), rs.getString("nombre_proveedor"), rs.getString("email_proveedor"));
                proveedor.mostrarInfo();
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al listar proveedores: " + e.getMessage());
    }
}
public void verProductosConAlertaDeStock() {
    try {
        String query = "SELECT * FROM alertas_stock";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (!rs.isBeforeFirst()) {
            System.out.println("No hay alertas de stock.");
        } else {
            while (rs.next()) {
                AlertaStock alerta = new AlertaStock(rs.getInt("id_alerta"), rs.getString("nombre_producto"), rs.getInt("nivel_stock_actual"), rs.getInt("nivel_stock_minimo"));
                alerta.mostrarInfo();
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al mostrar productos con alerta de stock: " + e.getMessage());
    }
}
public void registrarSalidaProducto() {
    try {
        System.out.print("Ingrese el nombre del producto para registrar la salida: ");
        String nombreProducto = scanner.nextLine();

        System.out.print("Ingrese la cantidad de salida: ");
        int cantidadSalida = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        System.out.print("Ingrese el nombre del usuario que realiza la salida: ");
        String usuario = scanner.nextLine();

        LocalDateTime fechaSalida = LocalDateTime.now();

        // Insertar la salida en la base de datos
        String query = "INSERT INTO salidas_stock (nombre_producto, cantidad_salida, fecha_salida, usuario) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conexion.prepareStatement(query);
        stmt.setString(1, nombreProducto);
        stmt.setInt(2, cantidadSalida);
        stmt.setTimestamp(3, Timestamp.valueOf(fechaSalida));
        stmt.setString(4, usuario);
        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Salida de producto registrada exitosamente.");
        } else {
            System.out.println("Error al registrar la salida de producto.");
        }
    } catch (SQLException e) {
        System.out.println("Error al registrar salida de producto: " + e.getMessage());
    } catch (InputMismatchException e) {
        System.out.println("Error: Ingrese un número válido.");
        scanner.nextLine(); // Limpiar buffer
    }
}

public void listarSalidasProducto() {
    try {
        String query = "SELECT * FROM salidas_stock";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (!rs.isBeforeFirst()) {
            System.out.println("No hay salidas de productos registradas.");
        } else {
            while (rs.next()) {
                SalidaProducto salida = new SalidaProducto(
                        rs.getInt("id_salida"),
                        rs.getString("nombre_producto"),
                        rs.getInt("cantidad_salida"),
                        rs.getTimestamp("fecha_salida").toLocalDateTime(),
                        rs.getString("usuario")
                );
                salida.mostrarInfo();
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al listar salidas de producto: " + e.getMessage());
    }
}
}