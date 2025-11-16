/**
 * CartItem representa una línea en el carrito: un producto y su cantidad.
 * Adaptada para trabajar con nuestra clase Producto unificada.
 */
public class CartItem {
    private final Producto product;
    private int quantity;

    public CartItem(Producto product, int quantity) {
        if (product == null) throw new IllegalArgumentException("product no puede ser null");
        if (quantity <= 0) throw new IllegalArgumentException("quantity debe ser >= 1");
        this.product = product;
        this.quantity = quantity;
    }

    public Producto getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity debe ser >= 1");
        this.quantity = quantity;
    }

    public double lineTotal() {
        return product.getPrecio() * quantity;
    }

    @Override
    public String toString() {
        return String.format("🛒 %s | Cantidad: %d | Subtotal: $%.2f",
                product.getNombre(), quantity, lineTotal());
    }

    // ✅ NUEVO: Método para mostrar información en factura
    public void mostrarEnFactura(int numero) {
        System.out.printf("%2d. %-30s %3d x $%-6.2f = $%-8.2f\n",
                numero, product.getNombre(), quantity,
                product.getPrecio(), lineTotal());
    }
}