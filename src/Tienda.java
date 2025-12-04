import java.util.List;

public class Tienda {
    private String nombre;
    private ArbolProductos inventario;
    private ColaClientes colaClientes;

    public Tienda(String nombre) {
        this.nombre = nombre;
        this.inventario = new ArbolProductos();
        this.colaClientes = new ColaClientes();
    }

    // Getters
    public String getNombre() { return nombre; }
    public ArbolProductos getInventario() { return inventario; }
    public ColaClientes getColaClientes() { return colaClientes; }

    // Agregar producto al inventario
    public void agregarProductoInventario(Producto producto) {
        inventario.insertar(producto);
        System.out.println("✅ Producto '" + producto.getNombre() + "' agregado al inventario.");
    }

    // Buscar producto en inventario
    public Producto buscarProductoInventario(String nombre) {
        return inventario.buscar(nombre);
    }

    // Mostrar inventario completo
    public void mostrarInventario() {
        inventario.mostrarInventario();
    }

    // Listar productos disponibles (nombre y precio)
    public void listarProductosDisponibles() {
        inventario.listarProductosDisponibles();
    }

    // Agregar cliente a la cola
    public void agregarCliente(Cliente cliente) {
        colaClientes.encolar(cliente);
    }

    // Método actualizado para atender cliente con grafo
    public void atenderSiguienteCliente(Grafo grafo) {
        Cliente cliente = colaClientes.atenderSiguiente(grafo);
        if (cliente != null) {
            System.out.println("\n" + "⭐".repeat(60));
            System.out.println("🎉 ATENDIENDO A CLIENTE: " + cliente.getNombre().toUpperCase());
            System.out.println("⭐".repeat(60));

            // Mostrar factura detallada con ruta
            cliente.mostrarFactura(grafo);

            // Actualizar inventario después de la compra
            actualizarInventario(cliente);

            System.out.println("✅ Cliente atendido exitosamente.");
        }
    }

    // Método para actualizar inventario después de una compra
    private void actualizarInventario(Cliente cliente) {
        List<CartItem> items = cliente.getCarrito().getItems();
        for (CartItem item : items) {
            Producto productoInventario = inventario.buscar(item.getProduct().getNombre());

            if (productoInventario != null) {
                // Reducir el inventario
                boolean exito = productoInventario.reducirInventario(item.getQuantity());
                if (exito) {
                    System.out.println("✅ Inventario actualizado: " + item.getProduct().getNombre() +
                            " -" + item.getQuantity() + " unidades");
                } else {
                    System.out.println("❌ Error al actualizar inventario para: " + item.getProduct().getNombre());
                }
            }
        }

        // Vaciar carrito del cliente después de la compra
        cliente.vaciarCarrito();
    }

    // Mostrar estado de la tienda
    public void mostrarEstadoTienda() {
        System.out.println("\n" + "🏪".repeat(60));
        System.out.println("📊 ESTADO DE LA TIENDA: " + nombre.toUpperCase());
        System.out.println("🏪".repeat(60));

        // Estado del inventario
        System.out.println("\n📦 INVENTARIO:");
        if (inventario.estaVacio()) {
            System.out.println("   No hay productos en el inventario.");
        } else {
            System.out.println("   Productos registrados: " + (contarProductosInventario(inventario.getRaiz())) + " productos");
        }

        // Estado de la cola
        colaClientes.mostrarEstadoCola();
    }

    // Método auxiliar para contar productos en el inventario
    private int contarProductosInventario(Producto nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarProductosInventario(nodo.getIzquierdo()) + contarProductosInventario(nodo.getDerecho());
    }

    // Verificar disponibilidad de producto
    public boolean verificarDisponibilidad(String nombreProducto, int cantidad) {
        Producto producto = inventario.buscar(nombreProducto);
        return producto != null && producto.haySuficienteInventario(cantidad);
    }

    // Método para crear cliente con grafo
    public void crearClienteConCarrito(Grafo grafo) {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(System.in));

            System.out.println("\n👥 CREAR NUEVO CLIENTE");
            System.out.print("👤 Nombre del cliente: ");
            String nombreCliente = reader.readLine();

            System.out.print("📍 Ubicación del cliente: ");
            String ubicacionCliente = reader.readLine();

            System.out.println("\n🎯 Tipo de cliente:");
            System.out.println("   1 - 🟢 Básico");
            System.out.println("   2 - 🟡 Afiliado");
            System.out.println("   3 - 🔴 Premium");
            System.out.print("   Seleccione (1-3): ");

            int prioridad = Integer.parseInt(reader.readLine());
            if (prioridad < 1 || prioridad > 3) {
                System.out.println("⚠️  Prioridad no válida. Se asignará BÁSICO.");
                prioridad = 1;
            }

            Cliente cliente = new Cliente(nombreCliente, prioridad, ubicacionCliente);

            // Gestión interactiva del carrito
            cliente.gestionarCarritoInteractivo(this);

            // Agregar cliente a la cola
            agregarCliente(cliente);

            // Si la ubicación no está conectada, ofrecer conectar
            if (!grafo.existeVertice(ubicacionCliente)) {
                System.out.println("\nℹ️  La ubicación '" + ubicacionCliente + "' es nueva en el sistema.");
                System.out.print("¿Desea conectarla a San José para entregas? (s/n): ");
                String respuesta = reader.readLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    System.out.print("📏 Distancia desde San José (km): ");
                    int distancia = Integer.parseInt(reader.readLine());
                    grafo.agregarArista("San José", ubicacionCliente, distancia);
                    System.out.println("✅ Ubicación conectada al sistema de entregas.");
                } else {
                    System.out.println("⚠️  El cliente podrá tener problemas de entrega si no está conectado.");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Error al crear cliente: " + e.getMessage());
        }
    }
}