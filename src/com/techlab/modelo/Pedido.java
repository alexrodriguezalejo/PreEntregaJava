package com.techlab.modelo;

import com.techlab.excepciones.StockInsuficienteException;
import java.util.ArrayList;

public class Pedido {
    private int id;
    private ArrayList<Producto> productos;

    public Pedido(int id) {
        this.id = id;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) throws StockInsuficienteException {
        if (cantidad > producto.getStock()) {
            throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.reducirStock(cantidad);
        for (int i = 0; i < cantidad; i++) {
            productos.add(producto);
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}
