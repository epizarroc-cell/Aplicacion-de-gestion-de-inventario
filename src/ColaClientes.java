/**
 * Los clientes se almacenan en orden de prioridad (3 > 2 > 1).
 */
public class ColaClientes {
    private Cliente primero;
    private Cliente ultimo;
    private int tamaño;

    public ColaClientes() {
        primero = null;
        ultimo = null;
        tamaño = 0;
    }

    /**
     * Encolar un cliente manteniendo el orden por prioridad.
     * Prioridad 3 (Premium) tiene la mayor prioridad, 1 (Básico) la menor.
     */
    public void encolar(Cliente cliente) {
        Cliente nuevo = new Cliente(cliente.getNombre(), cliente.getPrioridad());
        nuevo.setCarrito(cliente.getCarrito());

        if (primero == null) {
            // Cola vacía
            primero = nuevo;
            ultimo = nuevo;
        } else {
            // Insertar según prioridad (de mayor a menor: 3, 2, 1)
            if (cliente.getPrioridad() == 3) {
                // Premium va al frente si es necesario
                if (primero.getPrioridad() < 3) {
                    nuevo.setSiguiente(primero);
                    primero = nuevo;
                } else {
                    // Buscar posición correcta para premium
                    insertarOrdenadoPorPrioridad(nuevo);
                }
            } else if (cliente.getPrioridad() == 2) {
                insertarOrdenadoPorPrioridad(nuevo);
            } else {
                // Prioridad 1 va al final
                ultimo.setSiguiente(nuevo);
                ultimo = nuevo;
            }
        }
        tamaño++;
        System.out.println("✅ Cliente " + cliente.getNombre() + " agregado a la cola (" + cliente.getTipoCliente() + ")");
    }

    /**
     * Inserta un cliente en la posición correcta según su prioridad.
     * Los clientes con mayor prioridad van primero.
     */
    private void insertarOrdenadoPorPrioridad(Cliente nuevo) {
        Cliente actual = primero;
        Cliente anterior = null;

        while (actual != null && actual.getPrioridad() >= nuevo.getPrioridad()) {
            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (anterior == null) {
            // Insertar al inicio
            nuevo.setSiguiente(primero);
            primero = nuevo;
        } else {
            // Insertar en medio
            anterior.setSiguiente(nuevo);
            nuevo.setSiguiente(actual);

            if (actual == null) {
                ultimo = nuevo;
            }
        }
    }

    /**
     * Atiende al siguiente cliente (siempre el primero en la cola).
     * Retorna null si la cola está vacía.
     */
    public Cliente atenderSiguiente() {
        if (primero == null) {
            System.out.println("❌ No hay clientes en la cola");
            return null;
        }

        Cliente atendido = primero;
        primero = primero.getSiguiente();

        if (primero == null) {
            ultimo = null;
        }

        tamaño--;
        System.out.println("🎯 Atendiendo a: " + atendido.getNombre() + " (" + atendido.getTipoCliente() + ")");
        return atendido;
    }

    /**
     * Verifica si la cola está vacía.
     */
    public boolean estaVacia() {
        return primero == null;
    }

    /**
     * Muestra el estado actual de la cola.
     */
    public void mostrarEstadoCola() {
        System.out.println("\n=== ESTADO DE LA COLA ===");

        if (primero == null) {
            System.out.println("La cola está vacía.");
            return;
        }

        System.out.println("📊 Total de clientes en espera: " + tamaño);
        System.out.println("\n👉 Orden de atención (de primero a último):");

        Cliente actual = primero;
        int contador = 1;
        int premium = 0, afiliado = 0, basico = 0;

        while (actual != null) {
            System.out.printf("%d. %-20s - %s\n",
                    contador,
                    actual.getNombre(),
                    actual.getTipoCliente());

            // Contar por tipo
            switch (actual.getPrioridad()) {
                case 3: premium++; break;
                case 2: afiliado++; break;
                case 1: basico++; break;
            }

            actual = actual.getSiguiente();
            contador++;
        }

        System.out.println("\n📈 Distribución por tipo:");
        System.out.println("🔴 Clientes Premium (Prioridad 3): " + premium);
        System.out.println("🟡 Clientes Afiliados (Prioridad 2): " + afiliado);
        System.out.println("🟢 Clientes Básicos (Prioridad 1): " + basico);

        if (primero != null) {
            System.out.println("\n⏭️  Próximo cliente: " + primero.getNombre() + " (" + primero.getTipoCliente() + ")");
        }
    }

    /**
     * Retorna el tamaño total de la cola.
     */
    public int obtenerTamanoTotal() {
        return tamaño;
    }

    /**
     * Retorna el primer cliente sin atenderlo.
     */
    public Cliente getPrimero() {
        return primero;
    }

    /**
     * Retorna el último cliente en la cola.
     */
    public Cliente getUltimo() {
        return ultimo;
    }
}