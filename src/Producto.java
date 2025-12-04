import java.util.ArrayList;

public class Producto {
    private String nombre;
    private double precio;
    private String categoria;
    private String fechaVencimiento;
    private int cantidad;
    private int inventario;
    private String ubicacion;
    private ArrayList<String> listaImagenes;
    private Producto siguiente;
    private Producto izquierdo;
    private Producto derecho;

    // Constructor actualizado con ubicación
    public Producto(String nombre, double precio, String categoria, String fechaVencimiento, int cantidad, int inventario, String ubicacion) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.inventario = inventario;
        this.ubicacion = ubicacion;
        this.listaImagenes = new ArrayList<>();
        this.siguiente = null;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Constructor sobrecargado para compatibilidad (sin ubicación)
    public Producto(String nombre, double precio, String categoria, String fechaVencimiento, int cantidad, int inventario) {
        this(nombre, precio, categoria, fechaVencimiento, cantidad, inventario, "Sin ubicación");
    }

    // Constructor de copia
    public Producto(Producto original, int cantidadCarrito) {
        this.nombre = original.nombre;
        this.precio = original.precio;
        this.categoria = original.categoria;
        this.fechaVencimiento = original.fechaVencimiento;
        this.cantidad = cantidadCarrito;
        this.inventario = original.inventario;
        this.ubicacion = original.ubicacion;
        this.listaImagenes = new ArrayList<>(original.listaImagenes);
        this.siguiente = null;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters y Setters
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

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public ArrayList<String> getListaImagenes() { return listaImagenes; }
    public Producto getSiguiente() { return siguiente; }
    public void setSiguiente(Producto siguiente) { this.siguiente = siguiente; }
    public Producto getIzquierdo() { return izquierdo; }
    public void setIzquierdo(Producto izquierdo) { this.izquierdo = izquierdo; }
    public Producto getDerecho() { return derecho; }
    public void setDerecho(Producto derecho) { this.derecho = derecho; }

    // Resto de los métodos se mantienen igual...
    public void agregarImagen(String rutaImagen) {
        listaImagenes.add(rutaImagen);
    }

    public double calcularCostoTotal() {
        return cantidad * precio;
    }

    public boolean haySuficienteInventario(int cantidadRequerida) {
        return inventario >= cantidadRequerida;
    }

    public boolean reducirInventario(int cantidad) {
        if (haySuficienteInventario(cantidad)) {
            this.inventario -= cantidad;
            return true;
        }
        return false;
    }

    public void aumentarInventario(int cantidad) {
        this.inventario += cantidad;
    }

    public void mostrarInfoBasica() {
        System.out.println("- " + nombre + " | $" + precio + " | Stock: " + inventario + " | Categoría: " + categoria + " | Ubicación: " + ubicacion);
    }

    public void mostrarInfoCarrito() {
        System.out.println("- " + nombre + " | $" + precio + " | Cantidad: " + cantidad + " | Subtotal: $" + calcularCostoTotal());
    }

    public void mostrarProducto() {
        System.out.println("=== Producto ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Precio: $" + precio);
        System.out.println("Categoría: " + categoria);
        System.out.println("Ubicación: " + ubicacion);
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

    public int compararCon(Producto otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio + " (Stock: " + inventario + ") Ubicación: " + ubicacion;
    }

    public Producto crearCopiaParaCarrito(int cantidadCarrito) {
        return new Producto(this, cantidadCarrito);
    }
}