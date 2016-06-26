/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proveedor;

import Contacto.AgregarContacto;
import DB_manager.DB_Proveedor;
import DB_manager.DB_manager;
import Entities.M_contacto;
import Entities.M_sucursal;
import Entities.M_telefono;
import Entities.M_proveedor;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ramiro Ferreira
 */
public class C_crear_proveedor extends MouseAdapter implements ActionListener, KeyListener {

    public V_crear_proveedor vista;
    C_gestion_proveedores padre;
    DefaultTableModel dtmSucursal, dtmTelefono, dtmContacto;
    M_proveedor proveedor;
    ArrayList<M_contacto> contactos;

    public C_crear_proveedor(C_gestion_proveedores padre) {
        this.padre = padre;
        this.vista = new V_crear_proveedor(this.padre.c_inicio.vista, true, true);
        inicializarVista();
        agregarListeners();
    }

    public void mostrarVista() {
        this.vista.setLocationRelativeTo(this.vista);
        this.vista.setVisible(true);
    }

    private void inicializarVista() {
        dtmContacto = new DefaultTableModel();
        dtmContacto.addColumn("Nombre");
        dtmContacto.addColumn("Apellido");
        dtmContacto.addColumn("Nro. telefono");;
        this.vista.jtContacto.setModel(dtmContacto);

        dtmSucursal = new DefaultTableModel();
        dtmSucursal.addColumn("Direccion");
        dtmSucursal.addColumn("Telefono");
        this.vista.jtSucursal.setModel(dtmSucursal);

        dtmTelefono = new DefaultTableModel();
        dtmTelefono.addColumn("Telefono");
        dtmTelefono.addColumn("Tipo Telefono");
        dtmTelefono.addColumn("Observacion");
        this.vista.jtTelefono.setModel(dtmTelefono);
        this.vista.jbQuitarSucursal.setEnabled(false);
        this.vista.jbQuitarTelefono.setEnabled(false);
        this.vista.jbModTelefono.setEnabled(false);
        this.vista.jbModSucursal.setEnabled(false);
        this.vista.jbModContacto.setEnabled(false);
        this.vista.jbQuitarContacto.setEnabled(false);
        contactos = new ArrayList<>();
    }

    private void agregarListeners() {
        this.vista.jbAgregarSucursal.addActionListener(this);
        this.vista.jbAgregarTelefono.addActionListener(this);
        this.vista.jbAceptar.addActionListener(this);
        this.vista.jbQuitarSucursal.addActionListener(this);
        this.vista.jbQuitarTelefono.addActionListener(this);
        this.vista.jbModSucursal.addActionListener(this);
        this.vista.jbModTelefono.addActionListener(this);
        this.vista.jtSucursal.addMouseListener(this);
        this.vista.jtTelefono.addMouseListener(this);
        this.vista.jtContacto.addMouseListener(this);
        this.vista.jbAgregarContacto.addActionListener(this);
        this.vista.jbQuitarContacto.addActionListener(this);
    }

    public void recibirSucursal(String direccion, String telefono) {
        Object[] fila = {direccion, telefono};
        dtmSucursal.addRow(fila);
        this.vista.jtSucursal.updateUI();
        System.out.println("recibirSucursal");
    }

    public void modificarSucursal(String direccion, String telefono) {
        int row = this.vista.jtSucursal.getSelectedRow();
        dtmSucursal.setValueAt(direccion, row, 0);
        dtmSucursal.setValueAt(telefono, row, 1);
        this.vista.jbQuitarTelefono.setEnabled(false);
        this.vista.jbModTelefono.setEnabled(false);
    }

    private void quitarSucursal() {
        dtmSucursal.removeRow(this.vista.jtSucursal.getSelectedRow());
        this.vista.jbQuitarSucursal.setEnabled(false);
    }

    void recibirTelefono(String tipoTelefono, String nroTelefono, String observacion) {
        Object[] fila = {nroTelefono, tipoTelefono, observacion};
        dtmTelefono.addRow(fila);
        this.vista.jtTelefono.updateUI();
    }

    void modificarTelefono(String tipoTelefono, String nroTelefono, String observacion) {
        int row = this.vista.jtTelefono.getSelectedRow();
        dtmTelefono.setValueAt(nroTelefono, row, 0);
        dtmTelefono.setValueAt(tipoTelefono, row, 1);
        dtmTelefono.setValueAt(observacion, row, 2);
        this.vista.jbQuitarTelefono.setEnabled(false);
        this.vista.jbModTelefono.setEnabled(false);
    }

    private void quitarTelefono() {
        dtmTelefono.removeRow(this.vista.jtTelefono.getSelectedRow());
        this.vista.jbQuitarTelefono.setEnabled(false);
    }

    private void validarDatos() {
        int cantTel = dtmTelefono.getRowCount();
        M_telefono[] telefono = new M_telefono[cantTel];
        for (int i = 0; i < cantTel; i++) {
            telefono[i] = new M_telefono();
            telefono[i].setCategoria(dtmTelefono.getValueAt(i, 1).toString());
            telefono[i].setNumero(dtmTelefono.getValueAt(i, 0).toString());
            telefono[i].setObservacion(dtmTelefono.getValueAt(i, 2).toString());
        }
        int cantSuc = dtmSucursal.getRowCount();
        M_sucursal[] sucursal = new M_sucursal[cantSuc];
        for (int i = 0; i < cantSuc; i++) {
            sucursal[i] = new M_sucursal();
            sucursal[i].setDireccion(dtmSucursal.getValueAt(i, 0).toString());
            sucursal[i].setTelefono(dtmSucursal.getValueAt(i, 1).toString());
        }
        /*
         * VALIDAR RAZON SOCIAL
         */
        String entidad;
        if (this.vista.jtfRazonSocial.getText().isEmpty()) {
            this.vista.jtfRazonSocial.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo Razón social esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosGenerales);
            return;
        } else {
            entidad = this.vista.jtfRazonSocial.getText();

        }
        /*
         * VALIDAR NOMBRE FANTASIA
         */
        String nombreFantasia;
        if (this.vista.jtfNombreFantasia.getText().isEmpty()) {
            this.vista.jtfNombreFantasia.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo Nombre fantasía esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosGenerales);
            return;
        } else {
            nombreFantasia = this.vista.jtfNombreFantasia.getText();
        }
        /*
         * VALIDAR R.U.C.
         */
        String ruc;
        if (this.vista.jtfRUC.getText().isEmpty()) {
            this.vista.jtfRUC.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo R.U.C. esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosGenerales);
            return;
        } else {
            ruc = this.vista.jtfRUC.getText();
        }
        /*
         * VALIDAR R.U.C. ID
         */
        String rucId;
        if (this.vista.jtfRUC_ID.getText().isEmpty()) {
            this.vista.jtfRUC_ID.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo R.U.C. División esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosGenerales);
            return;
        } else {
            rucId = this.vista.jtfRUC_ID.getText();

        }        
        proveedor = new M_proveedor();
        proveedor.setEntidad(entidad);
        proveedor.setNombre(nombreFantasia);
        proveedor.setRuc(ruc);
        proveedor.setRuc_id(rucId);
        proveedor.setDescripcion(this.vista.jtfDescripcion.getText());
        proveedor.setDireccion(this.vista.jtfDireccion.getText());
        proveedor.setEmail(this.vista.jtfemail.getText());
        proveedor.setPagWeb(this.vista.jtfPagWeb.getText());
        proveedor.setObservacion(this.vista.jtaNota.getText());
        DB_Proveedor.insertarProveedor(proveedor, sucursal, telefono, contactos);
        actualizarTablaProveedores();
        cerrar();
    }

    public void recibirContacto(M_contacto contacto) {
        contactos.add(contacto);
        int cantContactos = dtmContacto.getRowCount();
        for (int i = 0; i < cantContactos; i++) {
            dtmContacto.removeRow(0);
        }
        for (int i = 0; i < contactos.size(); i++) {
            Object[] fila = {contactos.get(i).getNombre(), contactos.get(i).getApellido(), contactos.get(i).getTelefono()};
            dtmContacto.addRow(fila);
        }
    }

    private void modificarContacto(String tipoTelefono, String nroTelefono, String observacion) {
        /*int row = this.vista.jtTelefono.getSelectedRow();
         dtmTelefono.setValueAt(nroTelefono, row, 0);
         dtmTelefono.setValueAt(tipoTelefono, row, 1);
         dtmTelefono.setValueAt(observacion, row, 2);
         this.vista.jbQuitarTelefono.setEnabled(false);
         this.vista.jbModTelefono.setEnabled(false);*/
    }

    private void quitarContacto() {
        contactos.remove(this.vista.jtContacto.getSelectedRow());
        dtmContacto.removeRow(this.vista.jtContacto.getSelectedRow());
        this.vista.jbQuitarContacto.setEnabled(false);
        this.vista.jbModContacto.setEnabled(false);
    }

    private void actualizarTablaProveedores() {
        this.padre.vista.jtProveedor.setModel(DB_Proveedor.consultarProveedor(" ", true, true, false));
        Utilities.c_packColumn.packColumns(this.padre.vista.jtProveedor, 2);
    }

    private void cerrar() {
        this.vista.dispose();
        System.runFinalization();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vista.jbAgregarContacto)) {
            AgregarContacto crear_contacto = new AgregarContacto(this);
            crear_contacto.mostrarVista();
        }
        if (e.getSource().equals(this.vista.jbQuitarContacto)) {
            quitarContacto();
        }
        if (e.getSource().equals(this.vista.jbAgregarSucursal)) {
            C_crear_sucursal crear_sucursal = new C_crear_sucursal(this);
            crear_sucursal.mostrarVista();
        }
        if (e.getSource().equals(this.vista.jbQuitarSucursal)) {
            quitarSucursal();
        }
        if (e.getSource().equals(this.vista.jbAgregarTelefono)) {
            C_crear_telefono crear_telefono = new C_crear_telefono(this);
            crear_telefono.mostrarVista();
        }
        if (e.getSource().equals(this.vista.jbQuitarTelefono)) {
            quitarTelefono();
        }
        if (e.getSource().equals(this.vista.jbAceptar)) {
            validarDatos();
        }
        if (e.getSource().equals(this.vista.jbCancelar)) {
            cerrar();
        }
        if (e.getSource().equals(this.vista.jbModTelefono)) {
            C_modificar_telefono modificar_telefono = new C_modificar_telefono(this);
            modificar_telefono.mostrarVista();
        }
        if (e.getSource().equals(this.vista.jbModSucursal)) {
            C_modificar_sucursal modificar_sucursal = new C_modificar_sucursal(this);
            modificar_sucursal.mostrarVista();
        }        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.vista.jtfRazonSocial.hasFocus()) {
            this.vista.jtfRazonSocial.setBackground(Color.white);
        } else if (this.vista.jtfNombreFantasia.hasFocus()) {
            this.vista.jtfNombreFantasia.setBackground(Color.white);
        } else if (this.vista.jtfRUC.hasFocus()) {
            this.vista.jtfRUC.setBackground(Color.white);
        } else if (this.vista.jtfRUC_ID.hasFocus()) {
            this.vista.jtfRUC_ID.setBackground(Color.white);
        }

        if (this.vista.jtpCenter.getSelectedComponent().equals(this.vista.jpDatosGenerales)) {
            int fila = this.vista.jtTelefono.rowAtPoint(e.getPoint());
            int columna = this.vista.jtTelefono.columnAtPoint(e.getPoint());
            if ((fila > -1) && (columna > -1)) {
                this.vista.jbQuitarTelefono.setEnabled(true);
            } else {
                this.vista.jbQuitarTelefono.setEnabled(false);
            }
        }
        if (this.vista.jtpCenter.getSelectedComponent().equals(this.vista.jpDatosSucursal)) {
            int fila = this.vista.jtSucursal.rowAtPoint(e.getPoint());
            int columna = this.vista.jtSucursal.columnAtPoint(e.getPoint());
            if ((fila > -1) && (columna > -1)) {
                this.vista.jbQuitarSucursal.setEnabled(true);
            } else {
                this.vista.jbQuitarSucursal.setEnabled(false);
            }
        }
        if (this.vista.jtpCenter.getSelectedComponent().equals(this.vista.jpDatosContacto)) {
            int fila = this.vista.jtContacto.rowAtPoint(e.getPoint());
            int columna = this.vista.jtContacto.columnAtPoint(e.getPoint());
            if ((fila > -1) && (columna > -1)) {
                this.vista.jbQuitarContacto.setEnabled(true);
            } else {
                this.vista.jbQuitarContacto.setEnabled(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
