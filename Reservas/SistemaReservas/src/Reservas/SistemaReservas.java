package Reservas;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SistemaReservas extends JFrame {
    private List<Reserva> reservas = new ArrayList<>();
    private static final String ARCHIVO = "reservas.txt";
    private JTextArea areaTexto;

    public SistemaReservas() {
        setTitle("Sistema de Reservas ");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 14));
        getContentPane().add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));
        JButton btnAgregar = new JButton("Agregar Reserva");
        JButton btnMostrar = new JButton("Mostrar Reservas");
        JButton btnModificar = new JButton("Modificar Reserva");
        JButton btnEliminar = new JButton("Eliminar Reserva");
        JButton btnFinalizar = new JButton("Finalizar Reserva");
        JButton btnSalir = new JButton("Salir");

        btnAgregar.addActionListener(e -> agregarReserva());
        btnMostrar.addActionListener(e -> mostrarReservas());
        btnModificar.addActionListener(e -> modificarReserva());
        btnEliminar.addActionListener(e -> eliminarReserva());
        btnFinalizar.addActionListener(e -> finalizarReserva());
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnFinalizar);
        panelBotones.add(btnSalir);

        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        cargarReservas();
    }
    
    /*Metodo para agregar una nueva reserva*/
    private void agregarReserva() {
        String nombre = JOptionPane.showInputDialog("Ingrese nombre del cliente:");
        String dni = solicitarDNI();
        String tipo = JOptionPane.showInputDialog("Ingrese tipo de reserva (Hotel/Vuelo):");
        if (nombre == null || dni == null || tipo == null) return;

        int cantidadPersonas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad de personas:"));
        int cantidadHoras = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad de horas:"));

        String fecha = solicitarFecha("Ingrese la fecha de la reserva (MM-DD):");
        String horaRegistro = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String horaInicio = ("Hotel".equalsIgnoreCase(tipo)) ? LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "";

        Cliente cliente = new Cliente(nombre, dni);
        Reserva reserva = new Reserva(cliente, fecha, tipo, cantidadPersonas, cantidadHoras, horaRegistro, horaInicio);
        reservas.add(reserva);

        guardarReservas();
        mostrarReservas();
    }
    
    /*Metodo para modificar la reserva*/
    private void modificarReserva() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID de la reserva a modificar:");
        if (idStr == null) return;
        int id = Integer.parseInt(idStr);

        for (Reserva r : reservas) {
            if (r.getId() == id) {
                String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre del cliente:", r.getCliente().getNombre());
                String nuevoDni = solicitarDNI();
                String nuevaFecha = JOptionPane.showInputDialog("Nueva fecha de la reserva (YYYY-MM-DD):");
                String nuevoTipo = JOptionPane.showInputDialog("Nuevo tipo de reserva:", r.getTipo());

                r.setCliente(new Cliente(nuevoNombre, nuevoDni));
                r.setFecha(nuevaFecha);
                r.setTipo(nuevoTipo);
                r.setCantidadPersonas(Integer.parseInt(JOptionPane.showInputDialog("Nueva cantidad de personas:")));
                r.setCantidadHoras(Integer.parseInt(JOptionPane.showInputDialog("Nueva cantidad de horas:")));

                guardarReservas();
                mostrarReservas();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Reserva no encontrada.");
    }
     
    /*Metodo para dar por finalizada la reserva*/
    private void finalizarReserva() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID de la reserva a finalizar:");
        if (idStr == null) return;
        int id = Integer.parseInt(idStr);

        for (Reserva r : reservas) {
            if (r.getId() == id) {
                r.finalizarReserva();
                guardarReservas();
                mostrarReservas();
                JOptionPane.showMessageDialog(this, "Reserva finalizada con éxito.");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Reserva no encontrada.");
    }
    
    /*Permite mostrar las reservas si existen*/
    private void mostrarReservas() {
        areaTexto.setText("");
        if (reservas.isEmpty()) {
            areaTexto.append("No hay reservas registradas.\n");
        } else {
            for (Reserva r : reservas) {
                areaTexto.append(r.toString() + "\n");
            }
        }
    }
 
    /*Metodo para eliminar un reserva especifica*/
    private void eliminarReserva() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID de la reserva a eliminar:");
        if (idStr == null) return;
        int id = Integer.parseInt(idStr);

        reservas.removeIf(reserva -> reserva.getId() == id);
        guardarReservas();
        mostrarReservas();
    }

    /*Metodo para guardar las reservas*/
    private void guardarReservas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Reserva r : reservas) {
                writer.write(r.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar las reservas.");
        }
    }

    /*Metodo para cargar las reservas*/
    private void cargarReservas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                reservas.add(Reserva.fromCSV(linea));
            }
        } catch (IOException e) {
            System.out.println("No se encontró archivo de reservas.");
        }
    }
    
    /*Metodo para solicitar y validar la fecha*/
    private String solicitarFecha(String mensaje) {
        while (true) {
            String fecha = JOptionPane.showInputDialog(mensaje);
            if (fecha == null) return null;
            try {
                String fechaCompleta = "2025-" + fecha;
                LocalDate.parse(fechaCompleta, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return fechaCompleta;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Formato incorrecto. Use MM-DD.");
            }
        }
    }

    /*Metodo para solicitar y validar el DNI*/
    private String solicitarDNI() {
        while (true) {
            String dni = JOptionPane.showInputDialog("Ingrese DNI del cliente (8 dígitos):");
            if (dni == null) return null;
            if (dni.matches("\\d{8}")) return dni;
            JOptionPane.showMessageDialog(this, "DNI inválido. Debe contener exactamente 8 números.");
        }
    }
    /*Falta corregir y implementar*/
    /*
    private void descargarReservas() {
        if (reservas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay reservas para guardar.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reservas");
        fileChooser.setSelectedFile(new File("reservas.txt"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                for (Reserva r : reservas) {
                    writer.write(r.getId() + "," + r.getCliente().getNombre() + "," + r.getCliente().getDni() + "," + r.getTipo() + "," + r.getFecha());
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(this, "Reservas guardadas correctamente en " + archivo.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo.");
            }
        }
    }
    */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaReservas().setVisible(true));
    }
}