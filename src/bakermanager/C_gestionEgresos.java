/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bakermanager;

import Charts.MenuDiagramas;
import DB_manager.DB_Egreso;
import Entities.M_egreso_cabecera;
import Entities.M_funcionario;
import Entities.M_proveedor;
import Main.C_inicio;
import Proveedor.Seleccionar_proveedor;
import Usuario.Seleccionar_funcionario;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Ramiro Ferreira
 */
public class C_gestionEgresos extends MouseAdapter implements ActionListener, KeyListener {

    private M_egreso_cabecera m_egreso_cabecera;
    V_gestion_egresos vista;
    public C_inicio c_inicio;

    public C_gestionEgresos(C_inicio c_inicio) {
        this.m_egreso_cabecera = new M_egreso_cabecera();
        this.vista = new V_gestion_egresos();
        this.c_inicio = c_inicio;
        this.vista.setLocation(c_inicio.centrarPantalla(this.vista));
        inicializarVista();
        agregarListeners();
    }

    /**
     * @return the producto
     */
    public M_egreso_cabecera getEgreso_cabecera() {
        return m_egreso_cabecera;
    }

    /**
     * @param producto the producto to set
     */
    public void setEgreso_cabecera(M_egreso_cabecera m_egreso_cabecera) {
        this.m_egreso_cabecera = m_egreso_cabecera;
    }

    private void inicializarVista() {
        this.vista.jbDetalle.setEnabled(false);
        this.vista.jbResumen.setEnabled(false);
        Vector condCompra = DB_Egreso.obtenerTipoOperacion();
        this.vista.jcbCondCompra.addItem("Todos");
        for (int i = 0; i < condCompra.size(); i++) {
            this.vista.jcbCondCompra.addItem(condCompra.get(i));
        }
        Date today = Calendar.getInstance().getTime();
        this.vista.jddInicio.setDate(today);
        this.vista.jddFinal.setDate(today);
    }

    public void mostrarVista() {
        this.c_inicio.agregarVentana(vista);
    }

    private void agregarListeners() {
        this.vista.jbProveedor.addActionListener(this);
        this.vista.jbFuncionario.addActionListener(this);
        this.vista.jbBuscar.addActionListener(this);
        this.vista.jbBorrar.addActionListener(this);
        this.vista.jbAgregar.addActionListener(this);
        this.vista.jbDetalle.addActionListener(this);
        this.vista.jbBuscarDetalle.addActionListener(this);
        this.vista.jtEgresoCabecera.addMouseListener(this);
        this.vista.jbResumen.addActionListener(this);
        this.vista.jbGraficos.addActionListener(this);
        this.vista.jtEgresoCabecera.addKeyListener(this);
    }

    private String empleado() {
        if (vista.jtfFuncionario.getText().isEmpty()) {
            return "Todos";
        }
        return m_egreso_cabecera.getId_empleado().toString();
    }

    private String proveedor() {
        if (vista.jtfProveedor.getText().isEmpty()) {
            return "Todos";
        }
        return m_egreso_cabecera.getProveedor().getEntidad();
    }

    private String tipoOperacion() {
        String idEmpleado = "";
        String datosTipoOperacion = vista.jcbCondCompra.getSelectedItem().toString();
        if (vista.jcbCondCompra.getSelectedItem().toString().equals("Todos")) {
            return "Todos";//1kg*26000// 10kg*210000
        } else {
            idEmpleado = DB_Egreso.obtenerTipoOperacion(datosTipoOperacion).toString();
        }
        return idEmpleado;
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
                if (vista.jddInicio.getDate() != null && vista.jddFinal.getDate() != null) {
                    int dateValue = vista.jddInicio.getDate().compareTo(vista.jddFinal.getDate());
                    if (dateValue > 0) {
                        vista.jddFinal.setDate(vista.jddInicio.getDate());
                        vista.jddFinal.updateUI();
                        JOptionPane.showMessageDialog(vista, "La fecha inicio debe ser menor que fecha final", "Atención", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                String fechaInicio = "";
                String fechaFinal = "";
                Integer nro_factura = null;
                try {
                    java.util.Date dateInicio = vista.jddInicio.getDate();
                    fechaInicio = new Timestamp(dateInicio.getTime()).toString().substring(0, 11);
                    fechaInicio = fechaInicio + "00:00:00.000";
                } catch (Exception e) {
                    fechaInicio = "Todos";
                }
                try {
                    java.util.Date dateFinal = vista.jddFinal.getDate();
                    fechaFinal = new Timestamp(dateFinal.getTime()).toString().substring(0, 11);
                    fechaFinal = fechaFinal + "23:59:59.000";
                } catch (Exception e) {
                    fechaFinal = "Todos";
                }
                try {
                    if (!vista.jtfNroFactura.getText().isEmpty()) {
                        nro_factura = Integer.valueOf(String.valueOf(vista.jtfNroFactura.getText()));
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(vista, "El numero de factura debe ser solo numérico", "Atención", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String empleado = empleado();
                String proveedor = proveedor();
                String tiop = tipoOperacion();
                /*
                 * Se utiliza el objeto factory para obtener un TableModel
                 * para los resultados del query.
                 */
                vista.jtEgresoCabecera.setModel(DB_Egreso.obtenerEgreso(proveedor, nro_factura, empleado, fechaInicio, fechaFinal, tiop));
                Utilities.c_packColumn.packColumns(vista.jtEgresoCabecera, 1);
                controlarTablaEgreso();
            }
        });
    }

    private void completarCampos() {
        int fila = vista.jtEgresoCabecera.getSelectedRow();
        int idEgrsoCabecera = Integer.valueOf(String.valueOf(vista.jtEgresoCabecera.getValueAt(fila, 0).toString()));
        this.vista.jtEgresoDetalle.setModel(DB_Egreso.obtenerEgresoDetalle(idEgrsoCabecera));
        Utilities.c_packColumn.packColumns(vista.jtEgresoDetalle, 1);
    }

    private void borrarParametros() {
        this.m_egreso_cabecera.setId_empleado(null);
        this.m_egreso_cabecera.setFuncionario(null);
        this.m_egreso_cabecera.setId_proveedor(null);
        this.m_egreso_cabecera.setProveedor(null);
        this.m_egreso_cabecera.setNro_factura(null);
        this.vista.jtfProveedor.setText("");
        this.vista.jtfFuncionario.setText("");
        this.vista.jtfNroFactura.setText("");
        this.vista.jcbCondCompra.setSelectedItem("Todos");
    }

    private void crearResumen() {
        if (vista.jddInicio.getDate() != null && vista.jddFinal.getDate() != null) {
            int dateValue = vista.jddInicio.getDate().compareTo(vista.jddFinal.getDate());
            if (dateValue > 0) {
                vista.jddFinal.setDate(vista.jddInicio.getDate());
                vista.jddFinal.updateUI();
                JOptionPane.showMessageDialog(vista, "La fecha inicio debe ser menor que fecha final", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        Integer nro_factura = null;
        try {
            if (!vista.jtfNroFactura.getText().isEmpty()) {
                nro_factura = Integer.valueOf(String.valueOf(vista.jtfNroFactura.getText()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "El numero de factura debe ser solo numérico", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String empleado = empleado();
        String proveedor = proveedor();
        String tiop = tipoOperacion();
        Resumen_egreso re = new Resumen_egreso(c_inicio, this.vista.jtEgresoCabecera.getModel(), proveedor, nro_factura, empleado, vista.jddInicio.getDate(), vista.jddFinal.getDate(), tiop);
        re.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.jbBuscar) {
            this.vista.jbDetalle.setEnabled(false);
            displayQueryResults();
        } else if (e.getSource() == this.vista.jbBuscarDetalle) {
            C_buscar_detalle buscar_detalle = new C_buscar_detalle(c_inicio);
            buscar_detalle.mostrarVista();
        } else if (e.getSource() == this.vista.jbProveedor) {
            Seleccionar_proveedor sp = new Seleccionar_proveedor(this);
            sp.mostrarVista();
        } else if (e.getSource() == this.vista.jbFuncionario) {
            Seleccionar_funcionario sf = new Seleccionar_funcionario(this);
            sf.mostrarVista();
        } else if (e.getSource() == this.vista.jbBorrar) {
            borrarParametros();
        } else if (e.getSource() == this.vista.jbAgregar) {
            Egresos crearEgreso = new Egresos(c_inicio);
            crearEgreso.mostrarVista();
        } else if (e.getSource().equals(this.vista.jbResumen)) {
            crearResumen();
        } else if (e.getSource().equals(this.vista.jbGraficos)) {
            verGraficos();
        } else if (e.getSource() == this.vista.jbDetalle) {
            Integer idEgresoCabecera = Integer.valueOf(String.valueOf(this.vista.jtEgresoCabecera.getValueAt(this.vista.jtEgresoCabecera.getSelectedRow(), 0)));
            Ver_Egresos ver_egreso = new Ver_Egresos(c_inicio, idEgresoCabecera);
            ver_egreso.mostrarVista();
            this.vista.jbDetalle.setEnabled(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(this.vista.jtEgresoCabecera)) {
            int fila = this.vista.jtEgresoCabecera.rowAtPoint(e.getPoint());
            int columna = this.vista.jtEgresoCabecera.columnAtPoint(e.getPoint());
            Integer idEgresoCabecera = Integer.valueOf(String.valueOf(this.vista.jtEgresoCabecera.getValueAt(fila, 0)));
            //setProducto(DBmanagerProducto.mostrarProducto(idProducto));
            setEgreso_cabecera(DB_Egreso.obtenerEgresoCabeceraID(idEgresoCabecera));
            if ((fila > -1) && (columna > -1)) {
                this.vista.jbDetalle.setEnabled(true);
                this.vista.jtEgresoDetalle.setModel(DB_Egreso.obtenerEgresoDetalle(idEgresoCabecera));
                Utilities.c_packColumn.packColumns(vista.jtEgresoDetalle, 1);
            } else {
                this.vista.jbDetalle.setEnabled(false);
            }
            if (e.getClickCount() == 2) {
                Ver_Egresos ver_egreso = new Ver_Egresos(c_inicio, idEgresoCabecera);
                ver_egreso.mostrarVista();
                this.vista.jbDetalle.setEnabled(false);
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        displayQueryResults();
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (this.vista.jtEgresoCabecera.hasFocus()) {
            completarCampos();
        }
    }

    public void recibirProveedor(M_proveedor proveedor) {
        this.m_egreso_cabecera.setProveedor(proveedor);
        this.m_egreso_cabecera.setId_proveedor(proveedor.getId());
        String entidad = this.m_egreso_cabecera.getProveedor().getEntidad();
        String nombre = this.m_egreso_cabecera.getProveedor().getNombre();
        this.vista.jtfProveedor.setText(nombre + " - " + entidad);
    }

    public void recibirFuncionario(M_funcionario funcionario) {
        this.m_egreso_cabecera.setFuncionario(funcionario);
        this.m_egreso_cabecera.setId_empleado(funcionario.getId_funcionario());
        String alias = this.m_egreso_cabecera.getFuncionario().getAlias();
        String apellido = this.m_egreso_cabecera.getFuncionario().getApellido();
        String nombre = this.m_egreso_cabecera.getFuncionario().getNombre();
        this.vista.jtfFuncionario.setText(nombre + " " + apellido + " (" + alias + ")");
    }

    private void controlarTablaEgreso() {
        if (this.vista.jtEgresoCabecera.getRowCount() > 0) {
            this.vista.jbResumen.setEnabled(true);
        } else {
            this.vista.jbResumen.setEnabled(false);
        }
    }

    private void verGraficos() {
        MenuDiagramas ge = new MenuDiagramas(this);
        ge.setVisible(true);
    }
}
