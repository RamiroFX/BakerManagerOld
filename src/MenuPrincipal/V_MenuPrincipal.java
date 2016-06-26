/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuPrincipal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 *
 * @author Ramiro Ferreira
 */
class V_MenuPrincipal extends JInternalFrame {

    JPanel jpSouth, jpCenter, jpWest, jpNorth;
    JTable jtEgresos;
    JScrollPane jspEgresos;
    JButton jbSalir, jbProveedores, jbProducto, jbEgreso, jbEmpleados,
            jbClientes, jbVentas, jbPedidos;
    JFormattedTextField jftFecha;

    public V_MenuPrincipal() {
        super("Menú principal", true, true, true, true);
        setSize(obtenerDimensionDePantalla());
        setName("menuPrincipal");
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarVista();
        getContentPane().add(jpNorth, BorderLayout.NORTH);
        getContentPane().add(jpCenter, BorderLayout.CENTER);
        getContentPane().add(jpSouth, BorderLayout.SOUTH);
        getContentPane().add(jpWest, BorderLayout.WEST);
    }

    /**
     * Este metodo se encargará de obtener la dimension de la pantalla en
     * pixeles, para ello utilizamos la clase java.awt.Toolkit.
     *
     * Una vez obtenida la dimension de la pantalla, reducimos el alto de
     * nuestra aplicación puesto que la barra de tareas ocupa parte de la
     * pantalla, comúnmente el alto promedio es de 25 pixeles, por lo tanto a la
     * altura la reducimos 25
     *
     * @return Dimension
     */
    private Dimension obtenerDimensionDePantalla() {
        Dimension pantalla = null;
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        pantalla.height = pantalla.height - 80;
        return pantalla;
    }

    private void inicializarVista() {
        jpNorth = new JPanel();
        jpSouth = new JPanel();
        jpSouth.setBorder(new EtchedBorder());
        jpCenter = new JPanel(new BorderLayout());
        jpWest = new JPanel(new GridLayout(8, 1));
        jpWest.setBorder(new EtchedBorder());
        jtEgresos = new JTable();
        jspEgresos = new JScrollPane(jtEgresos);
        jbSalir = new JButton("Salir");
        jbProveedores = new JButton("Proveedores");
        jbProducto = new JButton("Productos");
        jbEgreso = new JButton("Egreso");
        jbEmpleados = new JButton("Empleados");
        jbClientes = new JButton("Clientes");
        jbVentas = new JButton("Ventas");
        jbPedidos = new JButton("Pedidos");
        jftFecha = new JFormattedTextField(
                new DefaultFormatterFactory(
                new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"))));
        jftFecha.setFont(new java.awt.Font("Monospaced", 1, 14));
        jftFecha.setEditable(false);

        jpNorth.add(jftFecha);
        jpSouth.add(jbSalir);
        jpWest.add(jbProveedores);
        jpWest.add(jbProducto);
        jpWest.add(jbEgreso);
        jpWest.add(jbEmpleados);
        jpWest.add(jbClientes);
        jpWest.add(jbVentas);
        jpWest.add(jbPedidos);
        jpCenter.add(jspEgresos, BorderLayout.CENTER);
    }
}
