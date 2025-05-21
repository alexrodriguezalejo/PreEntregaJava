package com.techlab.app;

import com.techlab.modelo.Producto;
import com.techlab.modelo.Pedido;
import com.techlab.excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static ArrayList<Producto> productos = new ArrayList<>();
    private static ArrayList<Pedido> pedidos = new ArrayList<>();
    private static int contadorProducto = 1;
    private static int contadorPedido = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                sc.next();
            }
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    agregarProducto(sc);
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    buscarActualizarProducto(sc);
                    break;
                case 4:
                    eliminarProducto(sc);
                    break;
                case 5:
                    crearPedido(sc);
                    break;
                case 6:
                    listarPedidos();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 7);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n------------------------");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
        System.out.print("\nElija una opción: ");
    }

    private static void agregarProducto(Scanner sc) {
        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine();

        System.out.print("Precio del producto: ");
        double precio = leerDouble(sc);

        System.out.print("Stock inicial: ");
        int stock = leerEntero(sc);

        Producto nuevo = new Producto(contadorProducto++, nombre, precio, stock);
        productos.add(nuevo);

        System.out.println("Producto agregado con éxito.");
    }

    private static void listarProductos() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        for (Producto p : productos) {
            System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() +
                               " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock());
        }
    }

    private static void buscarActualizarProducto(Scanner sc) {
        System.out.print("Ingrese el ID del producto a buscar: ");
        int id = leerEntero(sc);
        Producto encontrado = buscarProductoPorId(id);

        if (encontrado == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.println("Producto encontrado: " + encontrado.getNombre());
        sc.nextLine();
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nuevoNombre = sc.nextLine();
        if (!nuevoNombre.isBlank()) {
            encontrado.setNombre(nuevoNombre);
        }

        System.out.print("Nuevo precio (-1 para mantener): ");
        double nuevoPrecio = leerDouble(sc);
        if (nuevoPrecio >= 0) {
            encontrado.setPrecio(nuevoPrecio);
        }

        System.out.print("Nuevo stock (-1 para mantener): ");
        int nuevoStock = leerEntero(sc);
        if (nuevoStock >= 0) {
            encontrado.setStock(nuevoStock);
        }

        System.out.println("Producto actualizado.");
    }

    private static void eliminarProducto(Scanner sc) {
        System.out.print("Ingrese el ID del producto a eliminar: ");
        int id = leerEntero(sc);
        Producto producto = buscarProductoPorId(id);

        if (producto != null) {
            productos.remove(producto);
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void crearPedido(Scanner sc) {
        Pedido pedido = new Pedido(contadorPedido++);

        while (true) {
            listarProductos();
            System.out.print("Ingrese el ID del producto a agregar (0 para terminar): ");
            int id = leerEntero(sc);
            if (id == 0) break;

            Producto producto = buscarProductoPorId(id);
            if (producto == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }

            System.out.print("Cantidad: ");
            int cantidad = leerEntero(sc);

            try {
                pedido.agregarProducto(producto, cantidad);
                System.out.println("Producto agregado al pedido.");
            } catch (StockInsuficienteException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (pedido.getProductos().isEmpty()) {
            System.out.println("No se agregaron productos al pedido.");
            return;
        }

        pedidos.add(pedido);
        System.out.println("Pedido creado con ID: " + pedido.getId() + " | Total: $" + pedido.calcularTotal());
    }

    private static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos realizados.");
            return;
        }

        for (Pedido p : pedidos) {
            System.out.println("\nPedido ID: " + p.getId());
            for (Producto prod : p.getProductos()) {
                System.out.println(" - " + prod.getNombre() + " | $" + prod.getPrecio());
            }
            System.out.println("Total: $" + p.calcularTotal());
        }
    }

    private static Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private static double leerDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        return sc.nextDouble();
    }

    private static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número entero válido: ");
            sc.next();
        }
        return sc.nextInt();
    }
}
