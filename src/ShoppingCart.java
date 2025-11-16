import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * ShoppingCart: almacenamiento en memoria de CartItem.
 * Adaptado para trabajar con nuestra clase Producto y el sistema de tienda.
 */
public class ShoppingCart {
    private final List<CartItem> items = new ArrayList<>();

    public ShoppingCart() {}

    /**
     * Añade una cantidad del producto al carrito. Si el producto ya existe (por nombre),
     * suma la cantidad.
     */
    public void addProduct(Producto product, int quantity) {
        if (product == null) throw new IllegalArgumentException("product no puede ser null");
        if (quantity <= 0) throw new IllegalArgumentException("quantity debe ser >= 1");

        Optional<CartItem> existing = items.stream()
                .filter(ci -> ci.getProduct().getNombre().equals(product.getNombre()))
                .findFirst();

        if (existing.isPresent()) {
            CartItem ci = existing.get();
            ci.setQuantity(ci.getQuantity() + quantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    /**
     * Remueve un producto completamente del carrito por su nombre. Retorna true si se removió.
     */
    public boolean removeProductByName(String productName) {
        return items.removeIf(ci -> ci.getProduct().getNombre().equalsIgnoreCase(productName));
    }

    /**
     * Actualiza la cantidad de un producto existente. Si la cantidad es 0, remueve el item.
     */
    public boolean updateQuantity(String productName, int newQuantity) {
        for (CartItem ci : items) {
            if (ci.getProduct().getNombre().equalsIgnoreCase(productName)) {
                if (newQuantity <= 0) {
                    // Remover item si la nueva cantidad no es positiva
                    return removeProductByName(productName);
                } else {
                    ci.setQuantity(newQuantity);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retorna una copia inmutable de los items para que la capa de IU no manipule la lista interna.
     */
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    /**
     * Calcula el subtotal (suma de lineTotal).
     */
    public double subtotal() {
        return items.stream().mapToDouble(CartItem::lineTotal).sum();
    }

    /**
     * Calcula impuestos usando una tasa. Método separado para facilitar pruebas.
     */
    public double taxes(double taxRate) {
        if (taxRate < 0) throw new IllegalArgumentException("taxRate no puede ser negativo");
        return subtotal() * taxRate;
    }

    /**
     * Calcula total final: subtotal + taxes + shipping (si aplica)
     */
    public double total(double taxRate, double shipping) {
        return subtotal() + taxes(taxRate) + Math.max(0, shipping);
    }

    /**
     * Limpia el carrito.
     */
    public void clear() { items.clear(); }

    public boolean isEmpty() { return items.isEmpty(); }

    public int getItemCount() { return items.size(); }

    // ✅ NUEVO: Método para verificar si un producto ya está en el carrito
    public boolean containsProduct(String productName) {
        return items.stream()
                .anyMatch(ci -> ci.getProduct().getNombre().equalsIgnoreCase(productName));
    }

    // ✅ NUEVO: Método para obtener la cantidad de un producto en el carrito
    public int getProductQuantity(String productName) {
        return items.stream()
                .filter(ci -> ci.getProduct().getNombre().equalsIgnoreCase(productName))
                .findFirst()
                .map(CartItem::getQuantity)
                .orElse(0);
    }

    // ✅ NUEVO: Método para mostrar resumen del carrito
    public void mostrarResumen() {
        if (isEmpty()) {
            System.out.println("🛒 El carrito está vacío.");
            return;
        }

        System.out.println("\n📦 CONTENIDO DEL CARRITO:");
        System.out.println("─".repeat(50));
        int contador = 1;
        for (CartItem item : items) {
            System.out.printf("%d. %-25s x%2d = $%-8.2f\n",
                    contador,
                    item.getProduct().getNombre(),
                    item.getQuantity(),
                    item.lineTotal());
            contador++;
        }
        System.out.println("─".repeat(50));
        System.out.printf("💰 SUBTOTAL: $%.2f\n", subtotal());
    }
}