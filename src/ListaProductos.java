
public class ListaProductos {
    private Producto primero;

    public ListaProductos() {
        primero = null;
    }

    public Producto getPrimero() {
        return primero;
    }

    public void setPrimero(Producto nuevoPrimero) {
        primero = nuevoPrimero;
    }

    // Insertar al inicio
    public void insertarInicio(Producto producto) {
        producto.setSiguiente(primero);
        setPrimero(producto);
    }

    // Insertar al final
    public void insertarFinal(Producto producto) {
        if (primero == null) {
            setPrimero(producto);
            return;
        }

        Producto productoTemp = primero;
        while (productoTemp.getSiguiente() != null) {
            productoTemp = productoTemp.getSiguiente();
        }
        productoTemp.setSiguiente(producto);
    }

    // Buscar producto por nombre
    public Producto buscarProducto(String nombreBuscar) {
        if (primero == null) {
            System.out.println("El catálogo global se encuentra vacío.");
            return null;
        }

        Producto productoTemp = primero;
        while (productoTemp != null) {
            if (productoTemp.getNombre().equalsIgnoreCase(nombreBuscar)) {
                System.out.println("✅ Producto encontrado en el catálogo global.");
                return productoTemp;
            }
            productoTemp = productoTemp.getSiguiente();
        }

        System.out.println("❌ Producto no encontrado en el catálogo global.");
        return null;
    }

    // Eliminar producto por nombre
    public boolean eliminarProducto(String nombreEliminar) {
        if (primero == null) {
            System.out.println("El catálogo está vacío.");
            return false;
        }

        // Caso especial: eliminar el primer nodo
        if (primero.getNombre().equalsIgnoreCase(nombreEliminar)) {
            primero = primero.getSiguiente();
            System.out.println("✅ Producto eliminado del catálogo global.");
            return true;
        }

        // Buscar el nodo a eliminar
        Producto productoTemp = primero;
        Producto anterior = null;

        while (productoTemp != null && !productoTemp.getNombre().equalsIgnoreCase(nombreEliminar)) {
            anterior = productoTemp;
            productoTemp = productoTemp.getSiguiente();
        }

        if (productoTemp == null) {
            System.out.println("❌ Producto no encontrado en el catálogo.");
            return false;
        }

        anterior.setSiguiente(productoTemp.getSiguiente());
        System.out.println("✅ Producto eliminado del catálogo global.");
        return true;
    }

    // Mostrar todos los productos
    public void mostrarTodosProductos() {
        if (primero == null) {
            System.out.println("📭 El catálogo global está vacío.");
            return;
        }

        Producto productoTemp = primero;
        int contador = 1;

        System.out.println("\n📋 CATÁLOGO GLOBAL DE PRODUCTOS");
        System.out.println("═".repeat(50));

        while (productoTemp != null) {
            System.out.println("\nProducto #" + contador + ":");
            productoTemp.mostrarProducto();
            productoTemp = productoTemp.getSiguiente();
            contador++;
        }

        System.out.println("═".repeat(50));
        System.out.println("📊 Total de productos en catálogo: " + (contador-1));
    }

    // Reporte de costos totales
    public void imprimirReporteCostos() {
        if (primero == null) {
            System.out.println("📭 No hay productos en el catálogo.");
            return;
        }

        Producto productoTemp = primero;
        double costoTotalAcumulado = 0;
        int contador = 1;

        System.out.println("\n📊 REPORTE DE COSTOS - CATÁLOGO GLOBAL");
        System.out.println("═".repeat(60));
        while (productoTemp != null) {
            double costoProducto = productoTemp.calcularCostoTotal();
            costoTotalAcumulado += costoProducto;

            System.out.printf("%2d. %-35s | Cant: %-3d | Unit: $%-6.2f | Total: $%-8.2f\n",
                    contador,
                    productoTemp.getNombre(),
                    productoTemp.getCantidad(),
                    productoTemp.getPrecio(),
                    costoProducto);

            productoTemp = productoTemp.getSiguiente();
            contador++;
        }

        System.out.println("─".repeat(60));
        System.out.printf("💰 COSTO TOTAL ACUMULADO: $%.2f\n", costoTotalAcumulado);
        System.out.println("═".repeat(60));
    }

    // Método para calcular total del carrito
    public double calcularTotalCarrito() {
        double total = 0;
        Producto actual = primero;
        while (actual != null) {
            total += actual.calcularCostoTotal();
            actual = actual.getSiguiente();
        }
        return total;
    }

    // Método para verificar si la lista está vacía
    public boolean estaVacia() {
        return primero == null;
    }

    // Método para obtener el tamaño de la lista
    public int obtenerTamano() {
        int contador = 0;
        Producto actual = primero;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    /**
     * Busca un producto por nombre y retorna una copia para el inventario.
     * Útil para importar productos del catálogo al inventario.
     */
    public Producto obtenerCopiaParaInventario(String nombre) {
        Producto original = buscarProducto(nombre);
        if (original != null) {
            return new Producto(
                    original.getNombre(),
                    original.getPrecio(),
                    original.getCategoria(),
                    original.getFechaVencimiento(),
                    0, // cantidad en carrito comienza en 0
                    original.getInventario()
            );
        }
        return null;
    }
}