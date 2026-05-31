package Main;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import Servicio.ServicioMetro;
import Modelo.*;
import Estrucuturas.*;


public class MainInterfaz extends JFrame {
    
    private ServicioMetro servicio;
    private JPanel panelPrincipal;
    private JTextArea areaResultados;
    private JTextField txtCodigoOrigen;
    private JTextField txtCodigoDestino;
    private JTextField txtDocumento;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtSaldo;
    
    public MainInterfaz() {
        servicio = new ServicioMetro();
        servicio.cargarDatosEjemplo();
        
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Sistema de Metro - Proyecto Final E.D.");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(true);
    }
    
    private void inicializarComponentes() {

        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        

        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTION DE METRO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 102, 204));
        panelTitulo.add(lblTitulo);
        

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setPreferredSize(new Dimension(250, 0));
        panelMenu.setBorder(BorderFactory.createTitledBorder("Menu de Opciones"));
        

        JButton btnRuta = crearBoton("1. Buscar Ruta Optima", "Encuentra el camino mas corto");
        JButton btnBFS = crearBoton("2. Explorar Red (BFS)", "Busqueda en anchura");
        JButton btnDFS = crearBoton("3. Explorar Red (DFS)", "Busqueda en profundidad");
        JButton btnConectadas = crearBoton("4. Ver Conexiones", "Estaciones adyacentes");
        JButton btnRegistrar = crearBoton("5. Registrar Pasajero", "Nuevo usuario al sistema");
        JButton btnViaje = crearBoton("6. Simular Viaje", "Realizar viaje y cobro");
        JButton btnHistorial = crearBoton("7. Ver Historial", "Consultas recientes");
        JButton btnCola = crearBoton("8. Atender Pasajeros", "Cola de atencion");
        JButton btnEstadisticas = crearBoton("9. Estadisticas", "Informacion del sistema");
        JButton btnLimpiar = crearBoton("Limpiar", "Borrar resultados");
        

        panelMenu.add(btnRuta);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnBFS);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnDFS);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnConectadas);
        panelMenu.add(Box.createVerticalStrut(10));
        panelMenu.add(btnRegistrar);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnViaje);
        panelMenu.add(Box.createVerticalStrut(10));
        panelMenu.add(btnHistorial);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnCola);
        panelMenu.add(Box.createVerticalStrut(5));
        panelMenu.add(btnEstadisticas);
        panelMenu.add(Box.createVerticalStrut(10));
        panelMenu.add(btnLimpiar);
        

        JPanel panelCentral = new JPanel(new BorderLayout());
        
        JLabel lblResultados = new JLabel("Resultados:");
        lblResultados.setFont(new Font("Arial", Font.BOLD, 14));
        
        areaResultados = new JTextArea();
        areaResultados.setFont(new Font("Courier New", Font.PLAIN, 12));
        areaResultados.setEditable(false);
        areaResultados.setBackground(new Color(245, 245, 245));
        areaResultados.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            new EmptyBorder(5, 5, 5, 5)
        ));
        
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        panelCentral.add(lblResultados, BorderLayout.NORTH);
        panelCentral.add(scrollResultados, BorderLayout.CENTER);
        

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de Entrada"));
        panelFormulario.setPreferredSize(new Dimension(250, 0));
        

        panelFormulario.add(crearCampoConEtiqueta("Codigo Origen:", txtCodigoOrigen = new JTextField(15)));
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(crearCampoConEtiqueta("Codigo Destino:", txtCodigoDestino = new JTextField(15)));
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(crearCampoConEtiqueta("Documento:", txtDocumento = new JTextField(15)));
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(crearCampoConEtiqueta("Nombre:", txtNombre = new JTextField(15)));
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(crearCampoConEtiqueta("Apellido:", txtApellido = new JTextField(15)));
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(crearCampoConEtiqueta("Saldo Inicial:", txtSaldo = new JTextField(15)));
        panelFormulario.add(Box.createVerticalStrut(20));
        

        JButton btnAyuda = new JButton(" Ver Instrucciones");
        btnAyuda.addActionListener(e -> mostrarAyuda());
        panelFormulario.add(btnAyuda);
        

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelMenu, BorderLayout.WEST);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelFormulario, BorderLayout.EAST);
        

        btnRuta.addActionListener(e -> buscarRuta());
        btnBFS.addActionListener(e -> explorarBFS());
        btnDFS.addActionListener(e -> explorarDFS());
        btnConectadas.addActionListener(e -> verConexiones());
        btnRegistrar.addActionListener(e -> registrarPasajero());
        btnViaje.addActionListener(e -> simularViaje());
        btnHistorial.addActionListener(e -> verHistorial());
        btnCola.addActionListener(e -> atenderPasajeros());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
        btnLimpiar.addActionListener(e -> areaResultados.setText(""));
        
        
        add(panelPrincipal);
    }
    
    private JButton crearBoton(String texto, String tooltip) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setMaximumSize(new Dimension(230, 35));
        boton.setToolTipText(tooltip);
        boton.setFont(new Font("Arial", Font.PLAIN, 12));
        boton.setFocusPainted(false);
        return boton;
    }
    
    private JPanel crearCampoConEtiqueta(String etiqueta, JTextField campo) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel(etiqueta);
        lbl.setPreferredSize(new Dimension(120, 25));
        campo.setPreferredSize(new Dimension(120, 25));
        panel.add(lbl);
        panel.add(campo);
        return panel;
    }
    
    
    private void buscarRuta() {
        String origen = txtCodigoOrigen.getText().trim().toUpperCase();
        String destino = txtCodigoDestino.getText().trim().toUpperCase();
        
        if (origen.isEmpty() || destino.isEmpty()) {
            mostrarError("Por favor ingrese codigo de origen y destino");
            return;
        }
        
        ConsultaRuta ruta = servicio.encontrarRutaMasCorta(origen, destino);
        
        if (ruta != null) {
            areaResultados.setText(" RUTA OPTIMA ENCONTRADA \n\n");
            areaResultados.append(ruta.generarInstruccionesRuta());

        }
    }
    
    private void explorarBFS() {
        String codigo = txtCodigoOrigen.getText().trim().toUpperCase();
        if (codigo.isEmpty()) {
            mostrarError("Ingrese un codigo de estacion");
            return;
        }
        
        ListaEnlazada<Estacion> visitados = servicio.busquedaAnchura(codigo);
        
        StringBuilder sb = new StringBuilder(" BUSQUEDA EN ANCHURA (BFS) \n\n");
        sb.append("Estacion inicial: ").append(codigo).append("\n");
        sb.append("Estaciones alcanzables (por nivel de cercania):\n\n");
        
        for (int i = 0; i < visitados.getTamaño(); i++) {
            sb.append((i + 1) + ". " + visitados.obtener(i).getNombre())
              .append(" (").append(visitados.obtener(i).getLinea()).append(")\n");
        }
        
        areaResultados.setText(sb.toString());
    }
    
    private void explorarDFS() {
        String codigo = txtCodigoOrigen.getText().trim().toUpperCase();
        if (codigo.isEmpty()) {
            mostrarError("Ingrese un codigo de estacion");
            return;
        }
        
        ListaEnlazada<Estacion> visitados = servicio.busquedaProfundidad(codigo);
        
        StringBuilder sb = new StringBuilder(" BUSQUEDA EN PROFUNDIDAD (DFS) \n\n");
        sb.append("Estacion inicial: ").append(codigo).append("\n");
        sb.append("Estaciones visitadas (orden DFS):\n\n");
        
        for (int i = 0; i < visitados.getTamaño(); i++) {
            sb.append((i + 1) + ". " + visitados.obtener(i).getNombre()).append("\n");
        }
        
        areaResultados.setText(sb.toString());
    }
    
    private void verConexiones() {
        String codigo = txtCodigoOrigen.getText().trim().toUpperCase();
        if (codigo.isEmpty()) {
            mostrarError("Ingrese un codigo de estacion");
            return;
        }
        
        ListaEnlazada<Estacion> adyacentes = servicio.obtenerEstacionesAdyacentes(codigo);
        
        StringBuilder sb = new StringBuilder(" ESTACIONES CONECTADAS \n\n");
        sb.append("Estacion: ").append(codigo).append("\n");
        sb.append("Conexiones directas:\n\n");
        
        if (adyacentes.estaVacia()) {
            sb.append("No hay conexiones o estacion no existe");
        } else {
            for (int i = 0; i < adyacentes.getTamaño(); i++) {
                Estacion e = adyacentes.obtener(i);
                sb.append(" -> ").append(e.getNombre())
                  .append(" (Línea ").append(e.getLinea()).append(")\n");
            }
        }
        
        areaResultados.setText(sb.toString());
    }
    
    private void registrarPasajero() {
        String doc = txtDocumento.getText().trim();
        String nom = txtNombre.getText().trim();
        String ape = txtApellido.getText().trim();
        String saldoStr = txtSaldo.getText().trim();
        
        if (doc.isEmpty() || nom.isEmpty() || ape.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }
        
        double saldo = 0;
        try {
            saldo = Double.parseDouble(saldoStr);
        } catch (NumberFormatException e) {
            mostrarError("Saldo debe ser un numero valido");
            return;
        }
        
        Pasajero p = new Pasajero(doc, nom, ape);
        p.recargarTarjeta(saldo);
        
        if (servicio.registrarPasajero(p)) {
            servicio.agregarAColaAtencion(doc);
            areaResultados.setText("¡PASAJERO REGISTRADO EXITOSAMENTE!\n\n");
            areaResultados.append("Documento: " + doc);
            areaResultados.append("\nNombre: " + nom + " " + ape);
            areaResultados.append("\nSaldo: $" + saldo);
            areaResultados.append("\n Agregado automaticamente a cola de atencion");
            
            txtDocumento.setText("");
            txtNombre.setText("");
            txtApellido.setText("");
            txtSaldo.setText("");
        } else {
            mostrarError("El pasajero ya existe");
        }
    }
    
    private void simularViaje() {
    String doc = txtDocumento.getText().trim();
    String origen = txtCodigoOrigen.getText().trim().toUpperCase();
    String destino = txtCodigoDestino.getText().trim().toUpperCase();
    
    if (doc.isEmpty() || origen.isEmpty() || destino.isEmpty()) {
        mostrarError("Complete documento, origen y destino");
        return;
    }
    
    int rutaViaje = obtenerRutaDelViaje(origen, destino);
    int rutaOrigen = obtenerGrupoRutaPrincipal(origen);
    int rutaDestino = obtenerGrupoRutaPrincipal(destino);
    
    boolean permitido = (rutaOrigen == rutaDestino) || 
                        esEstacionTransbordo(origen) || 
                        esEstacionTransbordo(destino);
    
    if (!permitido || rutaViaje == 0) {
        mostrarError("Error: No hay estaciones que se vinculen.\n" +
                     "Ruta 1: E001-E006 | Ruta 2: E007-E011 | Ruta 3: E012-E015\n");
        return;
    }
    
    boolean exito = servicio.simularViaje(doc, origen, destino);
    
    if (exito) {
        areaResultados.append("\n\n VIAJE COMPLETADO CON EXITO");
        areaResultados.append("\n Ruta: " + rutaViaje);
        abrirMapaSegunRuta(rutaViaje); 
    } else {
        areaResultados.append("\n\n El viaje no pudo completarse");
    }
    }
    
    private void verHistorial() {
        Pila<String> historial = servicio.obtenerHistorialConsultas();
        
        StringBuilder sb = new StringBuilder(" HISTORIAL DE CONSULTAS \n\n");
        sb.append("Total consultas: ").append(historial.getTamaño()).append("\n\n");
        
        if (historial.estaVacia()) {
            sb.append("No hay consultas registradas");
        } else {
            sb.append("Ultima consulta:\n");
            try {
                sb.append("| ").append(historial.cima());
            } catch (Exception e) {
                sb.append("No se puede acceder");
            }
        }
        
        areaResultados.setText(sb.toString());
    }
    
    private void atenderPasajeros() {
        int espera = servicio.obtenerTamanioCola();
        
        StringBuilder sb = new StringBuilder(" COLA DE ATENCION \n\n");
        sb.append("Pasajeros en espera: ").append(espera).append("\n\n");
        
        if (espera > 0) {
            Pasajero siguiente = servicio.atenderSiguientePasajero();
            if (siguiente != null) {
                sb.append("   ATENDIENDO AHORA:\n");
                sb.append("   Nombre: ").append(siguiente.getNombreCompleto()).append("\n");
                sb.append("   Documento: ").append(siguiente.getDocumento()).append("\n");
                sb.append("   Saldo: $").append(siguiente.getSaldoTarjeta()).append("\n");
                sb.append("\n Pasajero removido de la cola");
            }
        } else {
            sb.append("No hay pasajeros en espera");
        }
        
        areaResultados.setText(sb.toString());
    }
    
    private void mostrarEstadisticas() {
        areaResultados.setText(servicio.generarEstadisticas());
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarAyuda() {
        String texto = "INSTRUCCIONES DE USO:\n\n" +
            "1. BUSCAR RUTA: Ingrese codigos de origen y destino (ej: E001, E015)\n" +
            "2. BFS/DFS: Explore la red desde una estacion\n" +
            "3. CONEXIONES: Vea estaciones adyacentes\n" +
            "4. REGISTRAR: Complete documento, nombre, apellido y saldo\n" +
            "5. VIAJE: Use documento registrado y codigos de estacion\n" +
            "6. HISTORIAL: Muestra ultima consulta\n" +
            "7. COLA: Atiende pasajeros en orden\n\n" +
            "ESTRUCTURAS USADAS:\n" +
            "- Grafo: Rutas y conexiones\n" +
            "- HashTable: Busqueda rapida\n" +
            "- ListaEnlazada: Almacenamiento\n" +
            "- Cola: Atencion FIFO\n" +
            "- Pila: Historial LIFO\n" +
            "- ArbolBinario: Busqueda de nombres";
        
        JOptionPane.showMessageDialog(this, texto, "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MainInterfaz ventana = new MainInterfaz();
            ventana.setVisible(true);
        });
    }


    private int obtenerGrupoRutaPrincipal(String codigoEstacion) {
        if (codigoEstacion == null || !codigoEstacion.startsWith("E")) return 0;
        try {
            int numero = Integer.parseInt(codigoEstacion.substring(1));
            if (numero >= 1 && numero <= 6) return 1;
            if (numero >= 7 && numero <= 11) return 2;
            if (numero >= 12 && numero <= 15) return 3;
        } catch (NumberFormatException e) {}
    return 0;
}

    private boolean esEstacionTransbordo(String codigoEstacion) {
        return codigoEstacion.equals("E002") || codigoEstacion.equals("E004") || codigoEstacion.equals("E005");
}

    private int obtenerRutaDelViaje(String origen, String destino) {
       int rutaOrigen = obtenerGrupoRutaPrincipal(origen);
       int rutaDestino = obtenerGrupoRutaPrincipal(destino);

    if (!esEstacionTransbordo(origen)) {
        return rutaOrigen;
    }
    
    return rutaDestino;
}

private void abrirMapaSegunRuta(int numeroRuta) {
    try {
        String url = "";
        switch (numeroRuta) {
            case 1: url = "https://www.google.com/maps/d/u/1/embed?mid=1yPtVzThf-dBJZd_CZJd8LMKRrRx6duU&ehbc=2E312F"; break;
            case 2: url = "https://www.google.com/maps/d/u/1/embed?mid=12KkiZAdpb5US342q1u69itWGAZivMIg&ehbc=2E312F"; break;
            case 3: url = "https://www.google.com/maps/d/u/1/embed?mid=1WYausi6VXQLrBf2iFTM-jHK700IQZm0&ehbc=2E312F"; break;
        }
        if (!url.isEmpty()) Desktop.getDesktop().browse(new java.net.URI(url));
    } catch (Exception e) {
        System.out.println("Error mapa: " + e.getMessage());
    }
}

}
