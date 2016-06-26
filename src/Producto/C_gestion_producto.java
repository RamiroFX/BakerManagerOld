/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Producto;

import DB_manager.DB_Producto;
import DB_manager.DB_manager;
import Entities.M_producto;
import Entities.M_proveedor;
import Main.C_inicio;
import Proveedor.Seleccionar_proveedor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramiro Ferreira
 */
public class C_gestion_producto extends MouseAdapter implements ActionListener, KeyListener {

    private M_producto m_producto;
    private M_proveedor proveedor;
    V_gestion_producto vista;
    public C_inicio c_inicio;

    public C_gestion_producto(C_inicio c_inicio) {
        this.m_producto = new M_producto();
        this.proveedor = new M_proveedor();
        this.vista = new V_gestion_producto();
        this.c_inicio = c_inicio;
        this.vista.setLocation(c_inicio.centrarPantalla(this.vista));
        inicializarVista();
        agregarListeners();
    }

    /**
     * @return the producto
     */
    public M_producto getProducto() {
        return m_producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(M_producto producto) {
        this.m_producto = producto;
    }

    private void inicializarVista() {
        Vector marca = DB_manager.obtenerMarca();
        this.vista.jcbMarca.addItem("Todos");
        for (int i = 0; i < marca.size(); i++) {
            this.vista.jcbMarca.addItem(marca.get(i));
        }
        Vector rubro = DB_manager.obtenerRubro();
        this.vista.jcbRubro.addItem("Todos");
        for (int i = 0; i < rubro.size(); i++) {
            this.vista.jcbRubro.addItem(rubro.get(i));
        }
        Vector impuesto = DB_manager.obtenerImpuesto();
        this.vista.jcbImpuesto.addItem("Todos");
        for (int i = 0; i < impuesto.size(); i++) {
            this.vista.jcbImpuesto.addItem(impuesto.get(i));
        }
        Vector estado = DB_manager.obtenerEstado();
        this.vista.jcbEstado.addItem("Todos");
        for (int i = 0; i < estado.size(); i++) {
            this.vista.jcbEstado.addItem(estado.get(i));
        }
    }

    public void mostrarVista() {
        this.c_inicio.agregarVentana(vista);
    }

    private void agregarListeners() {
        this.vista.jtfBuscar.addKeyListener(this);
        this.vista.jbBuscar.addActionListener(this);
        this.vista.jbBorrar.addActionListener(this);
        this.vista.jbModificar.addActionListener(this);
        this.vista.jbAgregar.addActionListener(this);
        this.vista.jbEliminar.addActionListener(this);
        this.vista.jbAsigProdProv.addActionListener(this);
        this.vista.jbProveedor.addActionListener(this);
        this.vista.jbParametros.addActionListener(this);
        this.vista.jtProducto.addMouseListener(this);
        this.vista.jtProducto.addKeyListener(this);
    }

    public void displayQueryResults() {
        /*
         * Para permitir que los mensajes puedan ser desplegados, no se ejecuta
         * el query directamente, sino que se lo coloca en una cola de eventos
         * para que se ejecute luego de los eventos pendientes.
         */

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                String desc = vista.jtfBuscar.getText();
                String marca = vista.jcbMarca.getSelectedItem().toString();
                String rubro = vista.jcbRubro.getSelectedItem().toString();
                String impuesto = vista.jcbImpuesto.getSelectedItem().toString();
                String estado = vista.jcbEstado.getSelectedItem().toString();
                String proveedor = proveedor();
                /*
                 * Se utiliza el objeto factory para obtener un TableModel
                 * para los resultados del query.
                 */

                vista.jtProducto.setModel(DB_Producto.consultaSimpleProducto(desc.toLowerCase(), proveedor, marca, rubro, impuesto, estado));
            }
        });
    }

    private void completarCampos() {
        Integer idProducto = Integer.valueOf(String.valueOf(this.vista.jtProducto.getValueAt(this.vista.jtProducto.getSelectedRow(), 0)));
        //setProducto(DBmanagerProducto.mostrarProducto(idProducto));
        setProducto(DB_Producto.obtenerDatosProductoID(idProducto));
        this.vista.jbModificar.setEnabled(true);
        this.vista.jbEliminar.setEnabled(true);
        this.vista.jtfProducto.setText(getProducto().getDescripcion());
        this.vista.jtfCodigo.setText(String.valueOf(getProducto().getId()));
        this.vista.jtfPrecioCosto.setText(String.valueOf(getProducto().getPrecioCosto()));
        this.vista.jtfPrecioMayorista.setText(String.valueOf(getProducto().getPrecioMayorista()));
        this.vista.jtfPrecioVta.setText(String.valueOf(getProducto().getPrecioVenta()));
        this.vista.jtfRubro.setText(getProducto().getRubro());
        this.vista.jtfImpuesto.setText(String.valueOf(getProducto().getImpuesto()));
        this.vista.jtfMarca.setText(getProducto().getMarca());
        this.vista.jtfSuspendido.setText(getProducto().getEstado());
        this.vista.jtfCantActual.setText(String.valueOf(getProducto().getCantActual()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.jtfBuscar) {
            //producto= DBmanager.mostrarProducto(jtfBuscar.getText());
            displayQueryResults();
        } else if (e.getSource() == this.vista.jbBuscar) {
            //producto= DBmanager.mostrarProducto(jtfBuscar.getText());
            displayQueryResults();
        } else if (e.getSource() == this.vista.jbBorrar) {
            borrarParametros();;
        } else if (e.getSource() == this.vista.jbModificar) {
            C_modificar_producto c_modProd = new C_modificar_producto(this, getProducto());
            c_modProd.mostrarVista();
        } else if (e.getSource() == this.vista.jbAgregar) {
            C_crear_producto c_creaProd = new C_crear_producto(this);
            c_creaProd.mostrarVista();
        } else if (e.getSource().equals(this.vista.jbParametros)) {
            Parametros param = new Parametros(c_inicio, this);
            param.setVisible(true);
        } else if (e.getSource() == this.vista.jbEliminar) {
            //DBmanagerProducto.eliminarProducto(producto);
            JOptionPane.showMessageDialog(vista, "Funcion no implementada", "Atencion", JOptionPane.INFORMATION_MESSAGE, null);
        } else if (e.getSource() == this.vista.jbAsigProdProv) {
            Crear_producto_proveedor cpp = new Crear_producto_proveedor(this);
            cpp.setVisible(true);
        } else if (e.getSource() == this.vista.jbProveedor) {
            Seleccionar_proveedor sp = new Seleccionar_proveedor(this);
            sp.mostrarVista();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.vista.jtProducto.rowAtPoint(e.getPoint());
        int columna = this.vista.jtProducto.columnAtPoint(e.getPoint());
        Integer idProducto = Integer.valueOf(String.valueOf(this.vista.jtProducto.getValueAt(fila, 0)));
        //setProducto(DBmanagerProducto.mostrarProducto(idProducto));
        setProducto(DB_Producto.obtenerDatosProductoID(idProducto));
        if ((fila > -1) && (columna > -1)) {
            this.vista.jbModificar.setEnabled(true);
            this.vista.jbEliminar.setEnabled(true);
            this.vista.jtfProducto.setText(getProducto().getDescripcion());
            this.vista.jtfCodigo.setText(String.valueOf(getProducto().getId()));
            this.vista.jtfPrecioCosto.setText(String.valueOf(getProducto().getPrecioCosto()));
            this.vista.jtfPrecioMayorista.setText(String.valueOf(getProducto().getPrecioMayorista()));
            this.vista.jtfPrecioVta.setText(String.valueOf(getProducto().getPrecioVenta()));
            this.vista.jtfRubro.setText(getProducto().getRubro());
            this.vista.jtfImpuesto.setText(String.valueOf(getProducto().getImpuesto()));
            this.vista.jtfMarca.setText(getProducto().getMarca());
            this.vista.jtfSuspendido.setText(getProducto().getEstado());
            this.vista.jtfCantActual.setText(String.valueOf(getProducto().getCantActual()));
        } else {
            this.vista.jbModificar.setEnabled(false);
            this.vista.jbEliminar.setEnabled(false);
        }
        if (e.getClickCount() == 2) {
            //new jdModificarProducto(jif).setVisible(true);
        }
    }

    public void keyTyped(KeyEvent e) {
        displayQueryResults();
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (this.vista.jtProducto.hasFocus()) {
            completarCampos();
        }
    }

    public void actualizarVista() {
        Vector marca = DB_manager.obtenerMarca();
        this.vista.jcbMarca.removeAllItems();
        this.vista.jcbMarca.addItem("Todos");
        for (int i = 0; i < marca.size(); i++) {
            this.vista.jcbMarca.addItem(marca.get(i));
        }
        Vector rubro = DB_manager.obtenerRubro();
        this.vista.jcbRubro.removeAllItems();
        this.vista.jcbRubro.addItem("Todos");
        for (int i = 0; i < rubro.size(); i++) {
            this.vista.jcbRubro.addItem(rubro.get(i));
        }
        Vector impuesto = DB_manager.obtenerImpuesto();
        this.vista.jcbImpuesto.removeAllItems();
        this.vista.jcbImpuesto.addItem("Todos");
        for (int i = 0; i < impuesto.size(); i++) {
            this.vista.jcbImpuesto.addItem(impuesto.get(i));
        }
        Vector estado = DB_manager.obtenerEstado();
        this.vista.jcbEstado.removeAllItems();
        this.vista.jcbEstado.addItem("Todos");
        for (int i = 0; i < estado.size(); i++) {
            this.vista.jcbEstado.addItem(estado.get(i));
        }
    }

    private String proveedor() {
        if (this.vista.jtfProveedor.getText().isEmpty()) {
            return "Todos";
        }
        return this.proveedor.getEntidad();
    }

    public void recibirProveedor(M_proveedor proveedor) {
        this.proveedor = proveedor;
        String nombre = this.proveedor.getNombre();
        String entidad = this.proveedor.getEntidad();
        this.vista.jtfProveedor.setText(nombre + " (" + entidad + ")");
    }

    private void borrarParametros() {
        this.proveedor = new M_proveedor();
        this.vista.jtfBuscar.setText("");
        this.vista.jtfBuscar.requestFocusInWindow();
        this.vista.jtfProveedor.setText("");
    }
}
