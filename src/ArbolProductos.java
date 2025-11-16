public class ArbolProductos {
    private Producto raiz;

    public ArbolProductos() {
        raiz = null;
    }

    // Insertar producto en el árbol
    public void insertar(Producto producto) {
        raiz = insertarRec(raiz, producto);
    }

    private Producto insertarRec(Producto nodo, Producto producto) {
        if (nodo == null) {
            return producto;
        }

        if (producto.compararCon(nodo) < 0) {
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), producto));
        } else if (producto.compararCon(nodo) > 0) {
            nodo.setDerecho(insertarRec(nodo.getDerecho(), producto));
        }

        return nodo;
    }

    // Buscar producto por nombre
    public Producto buscar(String nombre) {
        return buscarRec(raiz, nombre);
    }

    private Producto buscarRec(Producto nodo, String nombre) {
        if (nodo == null) {
            return null;
        }

        if (nombre.equalsIgnoreCase(nodo.getNombre())) {
            return nodo;
        } else if (nombre.compareToIgnoreCase(nodo.getNombre()) < 0) {
            return buscarRec(nodo.getIzquierdo(), nombre);
        } else {
            return buscarRec(nodo.getDerecho(), nombre);
        }
    }

    // Recorrido inorden para mostrar productos
    public void mostrarInventario() {
        if (raiz == null) {
            System.out.println("El inventario está vacío.");
            return;
        }
        System.out.println("\n=== INVENTARIO DE LA TIENDA ===");
        mostrarInorden(raiz);
    }

    private void mostrarInorden(Producto nodo) {
        if (nodo != null) {
            mostrarInorden(nodo.getIzquierdo());
            nodo.mostrarProducto();
            mostrarInorden(nodo.getDerecho());
        }
    }

    // Obtener todos los productos (para mostrar en menú)
    public void listarProductosDisponibles() {
        if (raiz == null) {
            System.out.println("No hay productos disponibles.");
            return;
        }
        System.out.println("\n=== PRODUCTOS DISPONIBLES ===");
        listarInorden(raiz);
    }

    private void listarInorden(Producto nodo) {
        if (nodo != null) {
            listarInorden(nodo.getIzquierdo());
            nodo.mostrarInfoBasica();
            listarInorden(nodo.getDerecho());
        }
    }

    // Verificar si el árbol está vacío
    public boolean estaVacio() {
        return raiz == null;
    }

    // Obtener la raíz del árbol
    public Producto getRaiz() {
        return raiz;
    }
}