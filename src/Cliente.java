import java.util.List;

public class Cliente {
    private String nombre;
    private int prioridad; // 1: básico, 2: afiliado, 3: premium
    private ShoppingCart carrito;
    private Cliente siguiente; // ✅ NUEVO: Para la cola de prioridad

    public Cliente(String nombre, int prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.carrito = new ShoppingCart();
        this.siguiente = null; // ✅ NUEVO
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) {
        if (prioridad >= 1 && prioridad <= 3) {
            this.prioridad = prioridad;
        }
    }

    public ShoppingCart getCarrito() { return carrito; }
    public void setCarrito(ShoppingCart carrito) { this.carrito = carrito; } // ✅ NUEVO

    public Cliente getSiguiente() { return siguiente; } // ✅ NUEVO
    public void setSiguiente(Cliente siguiente) { this.siguiente = siguiente; } // ✅ NUEVO

    public void agregarAlCarrito(Producto producto, int cantidad) {
        carrito.addProduct(producto, cantidad);
    }

    public double calcularTotalCarrito() {
        return carrito.subtotal();
    }

    public void mostrarFactura() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("🎫 FACTURA - " + nombre.toUpperCase());
        System.out.println("═".repeat(60));
        System.out.println("👤 Cliente: " + nombre);
        System.out.println("🎯 Tipo: " + getTipoCliente());
        System.out.println("📦 Productos en carrito: " + carrito.getItemCount());

        if (carrito.isEmpty()) {
            System.out.println("\nEl carrito está vacío.");
        } else {
            System.out.println("\n--- DETALLE DE COMPRA ---");
            System.out.printf("%-2s %-30s %-5s %-10s %-10s\n",
                    "#", "PRODUCTO", "CANT", "PRECIO", "SUBTOTAL");
            System.out.println("─".repeat(60));

            List<CartItem> items = carrito.getItems();
            for (int i = 0; i < items.size(); i++) {
                CartItem item = items.get(i);
                System.out.printf("%2d. %-30s %3d   $%-8.2f  $%-8.2f\n",
                        i + 1,
                        item.getProduct().getNombre(),
                        item.getQuantity(),
                        item.getProduct().getPrecio(),
                        item.lineTotal());
            }

            // Cálculos de impuestos y total
            double subtotal = carrito.subtotal();
            double iva = carrito.taxes(0.13); // 13% de IVA
            double envio = getCostoEnvio();
            double total = subtotal + iva + envio;

            System.out.println("─".repeat(60));
            System.out.printf("%45s: $%8.2f\n", "SUBTOTAL", subtotal);
            System.out.printf("%45s: $%8.2f\n", "IVA (13%)", iva);
            System.out.printf("%45s: $%8.2f\n", "ENVÍO", envio);
            System.out.println("═".repeat(60));
            System.out.printf("%45s: $%8.2f\n", "TOTAL A PAGAR", total);
            System.out.println("═".repeat(60));
        }
    }

    private double getCostoEnvio() {
        switch (prioridad) {
            case 3: return 0.0;    // Premium: envío gratis
            case 2: return 2.0;    // Afiliado: envío con descuento
            case 1: return 5.0;    // Básico: envío estándar
            default: return 5.0;
        }
    }

    public String getTipoCliente() {
        switch (prioridad) {
            case 1: return "🟢 Básico";
            case 2: return "🟡 Afiliado";
            case 3: return "🔴 Premium";
            default: return "Desconocido";
        }
    }

    public void vaciarCarrito() {
        this.carrito.clear();
    }

    public void gestionarCarritoInteractivo(Tienda tienda) {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(System.in));

            boolean gestionando = true;
            while (gestionando) {
                System.out.println("\n" + "🛒".repeat(30));
                System.out.println("GESTIÓN DE CARRITO - " + nombre.toUpperCase());
                System.out.println("🛒".repeat(30));
                carrito.mostrarResumen();

                System.out.println("\nOpciones:");
                System.out.println("1. ➕ Agregar producto");
                System.out.println("2. ✏️  Modificar cantidad");
                System.out.println("3. 🗑️  Eliminar producto");
                System.out.println("4. 👀 Ver productos disponibles");
                System.out.println("5. ✅ Finalizar y guardar carrito");
                System.out.print("Seleccione: ");

                String opcion = reader.readLine();

                switch (opcion) {
                    case "1":
                        agregarProductoInteractivo(tienda, reader);
                        break;
                    case "2":
                        modificarCantidadInteractivo(reader);
                        break;
                    case "3":
                        eliminarProductoInteractivo(reader);
                        break;
                    case "4":
                        tienda.listarProductosDisponibles();
                        break;
                    case "5":
                        gestionando = false;
                        System.out.println("✅ Carrito guardado para " + nombre);
                        break;
                    default:
                        System.out.println("❌ Opción no válida.");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error en la gestión del carrito: " + e.getMessage());
        }
    }

    private void agregarProductoInteractivo(Tienda tienda, java.io.BufferedReader reader) throws Exception {
        System.out.print("🔍 Nombre del producto a agregar: ");
        String nombreProducto = reader.readLine();

        Producto producto = tienda.buscarProductoInventario(nombreProducto);
        if (producto != null) {
            System.out.print("🔢 Cantidad (disponible: " + producto.getInventario() + "): ");
            int cantidad = Integer.parseInt(reader.readLine());

            if (cantidad > 0 && cantidad <= producto.getInventario()) {
                int cantidadExistente = carrito.getProductQuantity(nombreProducto);
                int totalRequerido = cantidadExistente + cantidad;

                if (totalRequerido <= producto.getInventario()) {
                    carrito.addProduct(producto, cantidad);
                    System.out.println("✅ Producto agregado al carrito.");
                } else {
                    System.out.println("❌ No hay suficiente inventario. Ya tienes " + cantidadExistente + " en el carrito.");
                }
            } else {
                System.out.println("❌ Cantidad no válida.");
            }
        } else {
            System.out.println("❌ Producto no encontrado.");
        }
    }

    private void modificarCantidadInteractivo(java.io.BufferedReader reader) throws Exception {
        if (carrito.isEmpty()) {
            System.out.println("❌ El carrito está vacío.");
            return;
        }

        System.out.print("🔍 Nombre del producto a modificar: ");
        String nombreProducto = reader.readLine();

        if (carrito.containsProduct(nombreProducto)) {
            System.out.print("🔢 Nueva cantidad: ");
            int nuevaCantidad = Integer.parseInt(reader.readLine());

            if (carrito.updateQuantity(nombreProducto, nuevaCantidad)) {
                System.out.println("✅ Cantidad actualizada.");
            } else {
                System.out.println("❌ Error al actualizar cantidad.");
            }
        } else {
            System.out.println("❌ Producto no encontrado en el carrito.");
        }
    }

    private void eliminarProductoInteractivo(java.io.BufferedReader reader) throws Exception {
        if (carrito.isEmpty()) {
            System.out.println("❌ El carrito está vacío.");
            return;
        }

        System.out.print("🔍 Nombre del producto a eliminar: ");
        String nombreProducto = reader.readLine();

        if (carrito.removeProductByName(nombreProducto)) {
            System.out.println("✅ Producto eliminado del carrito.");
        } else {
            System.out.println("❌ Producto no encontrado en el carrito.");
        }
    }
}