import java.util.ArrayList;

public class Producto {
    // Atributos principales
    private String nombre;
    private double precio;
    private String categoria;
    private String fechaVencimiento; // puede ser null si no aplica
    private int cantidad;            // cantidad que se maneja en el carrito
    private int inventario;          // cantidad en inventario de la tienda
    private ArrayList<String> listaImagenes; // rutas de imágenes del producto

    // Atributos para funcionalidad de nodo (reemplaza NodoProducto)
    private Producto siguiente;      // referencia al siguiente producto en la lista
    private Producto izquierdo;      // referencia izquierda para árbol
    private Producto derecho;        // referencia derecha para árbol

    // Constructor básico
    public Producto(String nombre, double precio, String categoria, String fechaVencimiento, int cantidad, int inventario) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.inventario = inventario;
        this.listaImagenes = new ArrayList<>();
        this.siguiente = null;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Constructor de copia (útil para carrito de compras)
    public Producto(Producto original, int cantidadCarrito) {
        this.nombre = original.nombre;
        this.precio = original.precio;
        this.categoria = original.categoria;
        this.fechaVencimiento = original.fechaVencimiento;
        this.cantidad = cantidadCarrito;
        this.inventario = original.inventario;
        this.listaImagenes = new ArrayList<>(original.listaImagenes);
        this.siguiente = null;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters y Setters básicos
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getInventario() { return inventario; }
    public void setInventario(int inventario) { this.inventario = inventario; }

    public ArrayList<String> getListaImagenes() { return listaImagenes; }

    // Getters y Setters para funcionalidad de nodo
    public Producto getSiguiente() { return siguiente; }
    public void setSiguiente(Producto siguiente) { this.siguiente = siguiente; }

    public Producto getIzquierdo() { return izquierdo; }
    public void setIzquierdo(Producto izquierdo) { this.izquierdo = izquierdo; }

    public Producto getDerecho() { return derecho; }
    public void setDerecho(Producto derecho) { this.derecho = derecho; }

    // Método para añadir imágenes
    public void agregarImagen(String rutaImagen) {
        listaImagenes.add(rutaImagen);
    }

    // Método para calcular el costo total de este producto según la cantidad
    public double calcularCostoTotal() {
        return cantidad * precio;
    }

    // Método para verificar disponibilidad en inventario
    public boolean haySuficienteInventario(int cantidadRequerida) {
        return inventario >= cantidadRequerida;
    }

    // Método para reducir inventario
    public boolean reducirInventario(int cantidad) {
        if (haySuficienteInventario(cantidad)) {
            this.inventario -= cantidad;
            return true;
        }
        return false;
    }

    // Método para aumentar inventario
    public void aumentarInventario(int cantidad) {
        this.inventario += cantidad;
    }

    // Método para mostrar información básica (para listados)
    public void mostrarInfoBasica() {
        System.out.println("- " + nombre + " | $" + precio + " | Stock: " + inventario + " | Categoría: " + categoria);
    }

    // Método para mostrar información de carrito
    public void mostrarInfoCarrito() {
        System.out.println("- " + nombre + " | $" + precio + " | Cantidad: " + cantidad + " | Subtotal: $" + calcularCostoTotal());
    }

    // Método para mostrar información completa del producto
    public void mostrarProducto() {
        System.out.println("=== Producto ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Precio: $" + precio);
        System.out.println("Categoría: " + categoria);
        if (fechaVencimiento != null) {
            System.out.println("Fecha de Vencimiento: " + fechaVencimiento);
        } else {
            System.out.println("Fecha de Vencimiento: No aplica");
        }
        System.out.println("Cantidad (carrito): " + cantidad);
        System.out.println("Inventario disponible: " + inventario);
        System.out.println("Imágenes: " + listaImagenes);
        System.out.println("Costo total del producto: $" + calcularCostoTotal());
        System.out.println("===================");
    }

    // Método para comparar productos (útil para árbol binario)
    public int compararCon(Producto otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    // Método para verificar si es hoja en el árbol
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    // Método toString para representación básica
    @Override
    public String toString() {
        return nombre + " - $" + precio + " (Stock: " + inventario + ")";
    }

    // Método para crear copia para carrito
    public Producto crearCopiaParaCarrito(int cantidadCarrito) {
        return new Producto(this, cantidadCarrito);
    }
}