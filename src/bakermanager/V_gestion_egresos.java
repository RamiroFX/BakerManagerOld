/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bakermanager;

import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Ramiro Ferreira
 */
public class V_gestion_egresos extends JInternalFrame {

    public JButton jbBuscar, jbBuscarDetalle, jbBorrar, jbAgregar, jbDetalle,
            jbResumen, jbProveedor, jbFuncionario, jbGraficos;
    public JTextField jtfNroFactura, jtfProveedor, jtfFuncionario;
    public JComboBox jcbCondCompra;
    private JPanel jpTop, jpBotonesTop, jpBot;
    public JTable jtEgresoCabecera, jtEgresoDetalle;
    private JScrollPane jspEgresoCabecera, jspEgresoDetalle;
    private JSplitPane jspMid;
    public JDateChooser jddInicio, jddFinal;

    public V_gestion_egresos() {
        super("Egresos", true, true, true, true);
        setSize(950, 600);
        setName("jifGestionEgresos");
        initTop();
        initMid();
        initBot();
        getContentPane().add(jpTop, "North");
        getContentPane().add(jspMid, "Center");
        getContentPane().add(jpBot, "South");
    }

    private void initTop() {
        jpTop = new JPanel(new MigLayout("", "[fill][fill]", "[fill][]"));
        JPanel jpFiltros = new JPanel(new MigLayout());
        jbProveedor = new JButton("Proveedor");
        jbFuncionario = new JButton("Funcionario");
        jcbCondCompra = new JComboBox();
        jtfNroFactura = new JTextField();
        jtfProveedor = new JTextField();
        jtfProveedor.setEditable(false);
        jtfProveedor.setPreferredSize(new Dimension(250, 10));
        jtfFuncionario = new JTextField();
        jtfFuncionario.setEditable(false);
        jtfFuncionario.setPreferredSize(new Dimension(250, 10));
        jddInicio = new JDateChooser();
        jddInicio.setPreferredSize(new Dimension(150, 10));
        jddFinal = new JDateChooser();
        jddFinal.setPreferredSize(new Dimension(150, 10));
        jpFiltros.add(jbProveedor, "growx");
        jpFiltros.add(jtfProveedor);
        jpFiltros.add(new JLabel("Fecha inicio:"));
        jpFiltros.add(jddInicio, "growx");
        jpFiltros.add(new JLabel("Cond. compra:"));
        jpFiltros.add(jcbCondCompra, "wrap");
        jpFiltros.add(jbFuncionario);
        jpFiltros.add(jtfFuncionario);
        jpFiltros.add(new JLabel("Fecha final:"));
        jpFiltros.add(jddFinal, "growx");
        jpFiltros.add(new JLabel("Nro. factura:"));
        jpFiltros.add(jtfNroFactura, "growx");
        jpBotonesTop = new JPanel(new MigLayout());
        jpBotonesTop.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        jbBuscar = new JButton("Buscar");
        jbBorrar = new JButton("Borrar");
        jbBuscarDetalle = new JButton("Buscar por detalle");
        jpBotonesTop.add(jbBuscar);
        jpBotonesTop.add(jbBorrar, "wrap");
        jpBotonesTop.add(jbBuscarDetalle, "span, growx");
        jpTop.add(jpFiltros);
        jpTop.add(jpBotonesTop);
        jpTop.setBorder(new EtchedBorder(EtchedBorder.RAISED));
    }

    private void initMid() {
        //Panel medio izquierda
        jtEgresoCabecera = new JTable();
        jspEgresoCabecera = new JScrollPane(jtEgresoCabecera);

        //panel medio derecha
        jtEgresoDetalle = new JTable();
        jspEgresoDetalle = new JScrollPane(jtEgresoDetalle);
        //creamos nuestro splitpane y agregamos los dos paneles del medio
        jspMid = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jspEgresoCabecera, jspEgresoDetalle);
        jspMid.setDividerLocation(this.getWidth() / 2);
        jspMid.setOneTouchExpandable(true);
    }

    private void initBot() {
        jpBot = new JPanel();
        jbAgregar = new JButton("Crear egreso");
        jbDetalle = new JButton("Ver detalle");
        jbResumen = new JButton("Ver resumen");
        jbGraficos = new JButton("Ver gráficos");
        jpBot.add(jbAgregar);
        jpBot.add(jbDetalle);
        jpBot.add(jbResumen);
        jpBot.add(jbGraficos);
    }
}
