package Reservas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reserva {
    private static int contador = 1;
    private int id;
    private Cliente cliente;
    private String fecha;
    private String tipo;
    private int cantidadPersonas;
    private int cantidadHoras;
    private String horaRegistro;
    private String estado;
    private String horaFinalizacion;
    private String horaInicio;

    
    public Reserva(Cliente cliente, String fecha, String tipo, int cantidadPersonas, int cantidadHoras, String horaRegistro, String horaInicio) {
        this.id = contador++;
        this.cliente = cliente;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cantidadPersonas = cantidadPersonas;
        this.cantidadHoras = cantidadHoras;
        this.horaRegistro = horaRegistro;
        this.horaInicio = horaInicio; 
        this.estado = "Activa";
        this.horaFinalizacion = calcularHoraFinalizacion();
    }

    
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public int getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(int cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public String getEstado() {
        return estado;
    }

    public String getHoraFinalizacion() {
        return horaFinalizacion;
    }

    
    private String calcularHoraFinalizacion() {
        if (horaRegistro == null || horaRegistro.isEmpty()) {
            return "";
        }
        LocalTime horaInicio = LocalTime.parse(horaRegistro, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime horaFin = horaInicio.plusHours(cantidadHoras);
        return horaFin.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    
    public void finalizarReserva() {
        this.estado = "Finalizada";
        this.horaFinalizacion = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    
    public String toCSV() {
        return id + "," + cliente.getNombre() + "," + cliente.getDni() + "," + tipo + "," + fecha + ","
                + cantidadPersonas + "," + cantidadHoras + "," + estado + "," + horaRegistro + "," + horaFinalizacion;
    }

   
    public static Reserva fromCSV(String csv) {
        String[] data = csv.split(",");

        if (data.length >= 10 && data[0].trim().matches("\\d+")) {
            try {
                int id = Integer.parseInt(data[0].trim());
                Cliente cliente = new Cliente(data[1].trim(), data[2].trim());
                String tipo = data[3].trim();
                String fecha = data[4].trim();
                int cantidadPersonas = Integer.parseInt(data[5].trim());
                int cantidadHoras = Integer.parseInt(data[6].trim());
                String estado = data[7].trim();
                String horaRegistro = data[8].trim();
                String horaFinalizacion = data[9].trim();
                

                String horaInicio = horaRegistro.isEmpty() ? "00:00:00" : horaRegistro;

                Reserva reserva = new Reserva(cliente, fecha, tipo, cantidadPersonas, cantidadHoras, horaRegistro, horaInicio);
                reserva.id = id;
                reserva.estado = estado;
                reserva.horaFinalizacion = horaFinalizacion;
                return reserva;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error al procesar CSV (Formato nuevo): " + e.getMessage());
                return null;
            }
        } else {
            try {
                String fecha = data.length > 0 ? data[0].trim() : "01-01"; 
                String nombre = data.length > 1 ? data[1].trim() : "Desconocido";
                String dni = data.length > 2 ? data[2].trim() : "00000000";
                String tipo = data.length > 3 ? data[3].trim() : "Hotel";

                Cliente cliente = new Cliente(nombre, dni);
                int cantidadPersonas = 0; 
                int cantidadHoras = 0;  
                String horaRegistro = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                String horaInicio = horaRegistro; 

                return new Reserva(cliente, fecha, tipo, cantidadPersonas, cantidadHoras, horaRegistro, horaInicio);
            } catch (Exception e) {
                System.err.println("Error al procesar CSV (Formato antiguo): " + e.getMessage());
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Cliente: " + cliente.getNombre() + " | DNI: " + cliente.getDni() +
                " | Tipo: " + tipo + " | Fecha: " + fecha + " | Personas: " + cantidadPersonas +
                " | Horas: " + cantidadHoras + " | Estado: " + estado +
                (estado.equals("Finalizada") ? " | Hora de Finalización: " + horaFinalizacion : "");
    }
}
