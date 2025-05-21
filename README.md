# Sistema de Gestión de Productos y Pedidos

Este es un proyecto simple en Java que permite administrar productos y realizar pedidos desde consola.

## Funcionalidades

1. Agregar producto
2. Listar productos
3. Buscar y actualizar producto
4. Eliminar producto
5. Crear un pedido
6. Listar pedidos
7. Salir

## Estructura del proyecto

- `modelo/`: contiene las clases `Producto` y `Pedido`.
- `excepciones/`: contiene la excepción personalizada `StockInsuficienteException`.
- `app/`: contiene la clase `Main` con el menú principal y la lógica del sistema.

## Requisitos técnicos

- Java 8 o superior.
- Uso de try/catch para manejo de errores.
- Excepción personalizada para manejar stock insuficiente.
- Persistencia de datos en memoria.

## Ejecución

Compilar y ejecutar el proyecto con:

```bash
javac app/Main.java
java app.Main
