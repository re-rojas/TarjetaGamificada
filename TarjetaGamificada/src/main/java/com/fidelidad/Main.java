package com.fidelidad;

import java.util.Scanner;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public class Main {

    private static ClienteRepository clienteRepo;
    private static CompraRepository compraRepo;
    private static FidelidadService fidelidadService;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        clienteRepo = new ClienteRepository();
        compraRepo = new CompraRepository();
        fidelidadService = new FidelidadService(clienteRepo, compraRepo);

        System.out.println("¡Bienvenido al Sistema de Fidelidad Gamificada!");

        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    gestionarClientes();
                    break;
                case 2:
                    gestionarCompras();
                    break;
                case 3:
                    mostrarPuntosNivelCliente();
                    break;
                case 4:
                    System.out.println("¡Gracias por usar el sistema. Adiós!");
                    return; // salir
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Gestión de Clientes");
        System.out.println("2. Gestión de Compras");
        System.out.println("3. Mostrar Puntos / Nivel de un Cliente");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // valor invalido si no es un número
        }
    }

    private static void gestionarClientes() {
        while (true) {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Agregar nuevo cliente");
            System.out.println("2. Listar todos los clientes");
            System.out.println("3. Actualizar cliente existente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    agregarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    actualizarCliente();
                    break;
                case 4:
                    eliminarCliente();
                    break;
                case 5:
                    return; // Sale de este método y vuelve al menú principal
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // En Main.java
    private static void gestionarCompras() {
        while (true) {
            System.out.println("\n--- GESTIÓN DE COMPRAS ---");
            System.out.println("1. Registrar nueva compra");
            System.out.println("2. Ver historial de un cliente");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    registrarNuevaCompra();
                    break;
                case 2:
                    verHistorialCliente();
                    break;
                case 3:
                    return; // volver a menu
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void mostrarPuntosNivelCliente() {
        System.out.println("\n--- Consultar Puntos y Nivel ---");
        System.out.print("Ingrese el ID del cliente: ");
        String idCliente = scanner.nextLine();

        Cliente cliente = clienteRepo.buscarPorId(idCliente);

        if (cliente == null) {
            System.out.println("Error: No se encontró un cliente con ese ID.");
            return;
        }

        System.out.println("\n--- Estado del Cliente ---");
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Puntos acumulados: " + cliente.getPuntos());
        System.out.println("Nivel de fidelidad: " + cliente.getNivel().name());
        System.out.println("--------------------------");
    }

    private static void agregarCliente() {
        System.out.println("\n--- Agregar Nuevo Cliente ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();

        // generar id
        String id = UUID.randomUUID().toString();

        try {
            Cliente nuevoCliente = new Cliente(id, nombre, correo);
            clienteRepo.guardar(nuevoCliente);
            System.out.println("¡Cliente agregado con éxito! ID: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        System.out.println("\n--- Listado de Clientes ---");
        List<Cliente> clientes = clienteRepo.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.printf("ID: %s | Nombre: %s | Correo: %s | Puntos: %d | Nivel: %s%n",
                cliente.getId(),
                cliente.getNombre(),
                cliente.getCorreo(),
                cliente.getPuntos(),
                cliente.getNivel().name()
            );
        }
    }

    // En Main.java

    private static void actualizarCliente() {
        System.out.println("\n--- Actualizar Cliente Existente ---");
        System.out.print("Ingrese el ID del cliente a actualizar: ");
        String id = scanner.nextLine();

        Cliente clienteAActualizar = clienteRepo.buscarPorId(id);

        if (clienteAActualizar == null) {
            System.out.println("Error: No se encontró un cliente con ese ID.");
            return;
        }

        System.out.print("Nuevo nombre (dejar en blanco para no cambiar): ");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isBlank()) {
            clienteAActualizar.setNombre(nuevoNombre);
        }

        System.out.print("Nuevo correo (dejar en blanco para no cambiar): ");
        String nuevoCorreo = scanner.nextLine();
        if (!nuevoCorreo.isBlank()) {
            clienteAActualizar.setCorreo(nuevoCorreo);
        }

        try {
            clienteRepo.actualizar(clienteAActualizar);
            System.out.println("¡Cliente actualizado con éxito!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    // En Main.java

    private static void eliminarCliente() {
        System.out.println("\n--- Eliminar Cliente ---");
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        String id = scanner.nextLine();

        // Verificamos primero si el cliente existe para dar un mejor feedback
        if (clienteRepo.buscarPorId(id) == null) {
            System.out.println("Error: No se encontró un cliente con ese ID.");
            return;
        }

        clienteRepo.eliminar(id);
        System.out.println("¡Cliente eliminado con éxito!");
    }


    private static void registrarNuevaCompra() {
        System.out.println("\n--- Registrar Nueva Compra ---");
        System.out.print("Ingrese el ID del cliente: ");
        String idCliente = scanner.nextLine();

        Cliente cliente = clienteRepo.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Error: No se encontró un cliente con ese ID.");
            return;
        }

        System.out.print("Ingrese el monto de la compra: ");
        double monto;
        try {
            monto = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Monto no válido.");
            return;
        }

        // Llamamos al servicio para que aplique toda la lógica de negocio
        fidelidadService.registrarCompra(idCliente, monto, LocalDate.now());

        // Mostramos un feedback útil al usuario
        Cliente clienteActualizado = clienteRepo.buscarPorId(idCliente);
        System.out.println("¡Compra registrada con éxito!");
        System.out.printf("Nuevos datos del cliente -> Puntos: %d | Nivel: %s%n",
            clienteActualizado.getPuntos(),
            clienteActualizado.getNivel().name()
        );
    }

    // En Main.java

    private static void verHistorialCliente() {
        System.out.println("\n--- Ver Historial de Compras de un Cliente ---");
        System.out.print("Ingrese el ID del cliente: ");
        String idCliente = scanner.nextLine();

        if (clienteRepo.buscarPorId(idCliente) == null) {
            System.out.println("Error: No se encontró un cliente con ese ID.");
            return;
        }

        List<Compra> historial = fidelidadService.obtenerComprasCliente(idCliente);

        if (historial.isEmpty()) {
            System.out.println("Este cliente no tiene compras registradas.");
            return;
        }

        System.out.println("--- Historial de: " + clienteRepo.buscarPorId(idCliente).getNombre() + " ---");
        for (Compra compra : historial) {
            System.out.printf("ID Compra: %s | Fecha: %s | Monto: $%,.2f%n",
                compra.getIdCompra(),
                compra.getFecha().toString(),
                compra.getMonto()
            );
        }
    }

}