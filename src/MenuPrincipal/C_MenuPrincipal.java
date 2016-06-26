/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuPrincipal;

import Cliente.Gestion_cliente;
import DB_manager.DB_Egreso;
import Main.C_inicio;
import Pedido.GestionPedidos;
import Producto.C_gestion_producto;
import Proveedor.Gestion_proveedores;
import Usuario.C_gestion_usuario;
import Ventas.Gestion_Ventas;
import bakermanager.C_gestionEgresos;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.swing.JInternalFrame;

/**
 *
 * @author Usuario
 */
public class C_MenuPrincipal implements ActionListener {

    V_MenuPrincipal vista;
    int idEgresoCabecera;
    public C_inicio inicio;

    public C_MenuPrincipal(V_MenuPrincipal vista, C_inicio inicio) {
        this.vista = vista;
        this.inicio = inicio;
        inicializarVista();
        agregarListeners();
    }

    private void inicializarVista() {
        java.util.Date date = new java.util.Date();
        String fechaInicio = new Timestamp(date.getTime()).toString().substring(0, 11);
        fechaInicio = fechaInicio + "00:00:00.000";
        String fechaFinal = new Timestamp(date.getTime()).toString().substring(0, 11);
        fechaFinal = fechaFinal + "23:59:59.000";
        Timestamp tsInicio = Timestamp.valueOf(fechaInicio);
        Timestamp tsFinal = Timestamp.valueOf(fechaFinal);
        this.vista.jftFecha.setValue(Calendar.getInstance().getTime());
        this.vista.jtEgresos.setModel(DB_Egreso.obtenerEgreso(tsInicio, tsFinal, 1));
    }

    private void agregarListeners() {
        this.vista.jbProveedores.addActionListener(this);
        this.vista.jbProducto.addActionListener(this);
        this.vista.jbEgreso.addActionListener(this);
        this.vista.jbSalir.addActionListener(this);
        //this.vista.jtEgresos.addMouseListener(this);
        this.vista.jbEmpleados.addActionListener(this);
        this.vista.jbClientes.addActionListener(this);
        this.vista.jbVentas.addActionListener(this);
        this.vista.jbPedidos.addActionListener(this);
    }

    void mostrarVista() {
        this.vista.setVisible(true);
        this.inicio.agregarVentana(vista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vista.jbProveedores)) {
            Gestion_proveedores proveedores = new Gestion_proveedores(this);
            proveedores.mostrarVista();
        } else if (e.getSource().equals(this.vista.jbProducto)) {
            C_gestion_producto productos = new C_gestion_producto(inicio);
            productos.mostrarVista();
        } else if (e.getSource().equals(this.vista.jbEgreso)) {
            C_gestionEgresos gestion_egresos = new C_gestionEgresos(inicio);
            gestion_egresos.mostrarVista();
        } else if (e.getSource().equals(this.vista.jbEmpleados)) {
            C_gestion_usuario ges_usuario = new C_gestion_usuario(inicio);
            ges_usuario.mostrarVista();
        } else if (e.getSource().equals(this.vista.jbClientes)) {
            Gestion_cliente ges_cliente = new Gestion_cliente(inicio);
            ges_cliente.mostrarVista();
        } else if (e.getSource().equals(this.vista.jbVentas)) {
            Gestion_Ventas ges_venta = new Gestion_Ventas(inicio);
            ges_venta.mostrarVista();
        } else if (e.getSource().equals(this.vista.jbPedidos)) {
            GestionPedidos gp = new GestionPedidos(inicio);
            gp.mostrarVista();
        } else if (e.getSource().equals(this.vista.jbSalir)) {
            System.exit(0);
        }
    }

    public void actualizarTablaEgreso() {
        java.util.Date date = new java.util.Date();
        String fechaInicio = new Timestamp(date.getTime()).toString().substring(0, 11);
        fechaInicio = fechaInicio + "00:00:00.000";
        String fechaFinal = new Timestamp(date.getTime()).toString().substring(0, 11);
        fechaFinal = fechaFinal + "23:59:59.000";
        Timestamp tsInicio = Timestamp.valueOf(fechaInicio);
        Timestamp tsFinal = Timestamp.valueOf(fechaFinal);
        this.vista.jftFecha.setValue(Calendar.getInstance().getTime());
        this.vista.jtEgresos.setModel(DB_Egreso.obtenerEgreso(tsInicio, tsFinal, 1));
    }
    /*
     @Override
     public void mouseClicked(MouseEvent e) {
     int fila = this.vista.jtEgresos.rowAtPoint(e.getPoint());
     int columna = this.vista.jtEgresos.columnAtPoint(e.getPoint());
     idEgresoCabecera = Integer.valueOf(String.valueOf(this.vista.jtEgresos.getValueAt(fila, 0)));
     //setProducto(DBmanagerProducto.mostrarProducto(idProducto));
     //itinerario = DBmanagerItinerario.consultarItinerarioPorID(idItinerario);
     if ((fila > -1) && (columna > -1)) {
     } else {
     }
     if (e.getClickCount() == 2) {
     Ver_Egresos ver_egreso = new Ver_Egresos(inicio, idEgresoCabecera);
     ver_egreso.mostrarVista();
     }
     }
     */

    public Point centrarPantalla(JInternalFrame i) {
        /*con este codigo centramos el panel en el centro del contenedor
         la anchura del contenedor menos la anchura de nuestro componente divido a 2
         lo mismo con la altura.*/
        return new Point((this.vista.getWidth() - i.getWidth()) / 2, (this.vista.getHeight() - i.getHeight() - 45) / 2);
    }

    public Dimension establecerTamañoPanel() {
        return new Dimension((int) (this.vista.getWidth() * 0.8), (int) (this.vista.getHeight() * 0.8));
    }
}
