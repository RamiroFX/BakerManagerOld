/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventas;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Ramiro Ferreira
 */
public class V_crearVentaRapida extends JDialog {
    //NORTE
    JPanel jpNorth;
    public JTextField jtfCliente, jtfFuncionario, jtfClieDireccion, jtfClieTelefono, jtfClieRuc;
    public JButton jbCliente;
    public JLabel jlFuncionario;
    public JRadioButton jrbContado, jrbCredito;
    //CENTRO
    JPanel jpCenter;
    public JTable jtFacturaDetalle;
    public JScrollPane jspFacturaDetalle;
    public JButton jbAgregarProducto, jbModificarDetalle, jbEliminarDetalle;
    public JLabel jlIva5, jlIva10, jlExenta, jlTotal;
    public JFormattedTextField jftIva5, jftIva10, jftExenta, jftTotal;
    //SUR
    JPanel jpSouth;
    public JButton jbAceptar, jbSalir;

    public V_crearVentaRapida(JFrame frame) {
        super(frame, "Crear venta", JDialog.ModalityType.APPLICATION_MODAL);
        setSize(900, 700);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(frame);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jpNorth, BorderLayout.NORTH);
        getContentPane().add(jpCenter, BorderLayout.CENTER);
        getContentPane().add(jpSouth, BorderLayout.SOUTH);
    }

    private void initComponents() {
        initNorth();
        initCenter();
        initSouth();
    }

    private void initNorth() {
        jpNorth = new JPanel(new MigLayout());
        jbCliente = new JButton("Agregar cliente");
        jtfCliente = new JTextField(30);
        jtfCliente.setEditable(false);
        jlFuncionario = new JLabel("Funcionario");
        jtfFuncionario = new JTextField(30);
        jtfFuncionario.setEditable(false);
        jrbContado = new JRadioButton("Contado");
        jrbCredito = new JRadioButton("Crédito");
        jtfClieRuc = new JTextField(30);
        jtfClieRuc.setEditable(false);
        jtfClieDireccion = new JTextField(30);
        jtfClieDireccion.setEditable(false);
        jtfClieTelefono = new JTextField(30);
        jtfClieTelefono.setEditable(false);
        javax.swing.ButtonGroup bg1 = new javax.swing.ButtonGroup();
        bg1.add(jrbContado);
        bg1.add(jrbCredito);
        jpNorth.add(jbCliente);
        jpNorth.add(jtfCliente);
        jpNorth.add(jlFuncionario);
        jpNorth.add(jtfFuncionario);
        jpNorth.add(jrbContado);
        jpNorth.add(jrbCredito, "wrap");
        jpNorth.add(new JLabel("R.U.C.:"));
        jpNorth.add(jtfClieRuc);
        jpNorth.add(new JLabel("Direccion:"));
        jpNorth.add(jtfClieDireccion);
        jpNorth.add(new JLabel("Telefono:"));
        jpNorth.add(jtfClieTelefono);
    }

    private void initCenter() {
        jpCenter = new JPanel(new BorderLayout());
        jpCenter.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        jtFacturaDetalle = new JTable();
        jtFacturaDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jspFacturaDetalle = new JScrollPane(jtFacturaDetalle);
        jbAgregarProducto = new JButton("Agregar producto");
        jbModificarDetalle = new JButton("Modificar detalle");
        jbEliminarDetalle = new JButton("Eliminar detalle");
        JPanel jpSouthAux = new JPanel(new MigLayout());
        jpSouthAux.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        jpSouthAux.add(jbAgregarProducto, "wrap");
        jpSouthAux.add(jbModificarDetalle, "growx, wrap");
        jpSouthAux.add(jbEliminarDetalle, "growx");

        jlIva5 = new JLabel("I.V.A. 5%");
        jlIva10 = new JLabel("I.V.A. 10%");
        jlExenta = new JLabel("Exentas");
        jlTotal = new JLabel("Total");
        jftExenta = new JFormattedTextField();
        jftExenta.setEditable(false);
        jftIva5 = new JFormattedTextField();
        jftIva5.setEditable(false);
        jftIva10 = new JFormattedTextField();
        jftIva10.setEditable(false);
        jftTotal = new JFormattedTextField();
        jftTotal.setEditable(false);
        JPanel jpDetalleAux = new JPanel(new MigLayout());
        jpDetalleAux.add(jpSouthAux, "wrap");
        jpDetalleAux.add(jlExenta, "wrap");
        jpDetalleAux.add(jftExenta, "growx, wrap");
        jpDetalleAux.add(jlIva5, "wrap");
        jpDetalleAux.add(jftIva5, "growx, wrap");
        jpDetalleAux.add(jlIva10, "wrap");
        jpDetalleAux.add(jftIva10, "growx, wrap");
        jpDetalleAux.add(jlTotal, "wrap");
        jpDetalleAux.add(jftTotal, "growx, wrap");
        jpCenter.add(jspFacturaDetalle, BorderLayout.CENTER);
        jpCenter.add(jpDetalleAux, BorderLayout.EAST);
    }

    private void initSouth() {
        jpSouth = new JPanel();
        jbAceptar = new JButton("Aceptar");

        jbSalir = new JButton("Salir");
        jpSouth.add(jbAceptar);
        jpSouth.add(jbSalir);
    }
}
