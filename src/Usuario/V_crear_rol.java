/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Usuario;

import Main.V_inicio;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 *
 * @author Ramiro
 */
public class V_crear_rol extends JDialog{
    private JPanel jpMid, jpMid1,jpMid2, jpMid3,
                    jpPrincipal, jpBot;
    private JLabel jlNombre,jlCodigo,jlApellido,jlCedula,jlSexo,jlNacionalidad,
            jlCiudad,jlFechaNacimiento,jlEstadoCivil,jlOcupacion,jlFechaIngreso,
            jlSalario,jlObservaciones;
    public JTextField jtfNombre,jtfApellido,jtfCedula,jtfCodigo,jtfFechaNacimiento,
            jtfFechaIngreso,jtfSalario;
    public JTextArea jtaObservaciones;
    public JComboBox jcbSexo,jcbNacionalidad,jcbCiudad,jcbEstadoCivil,jcbOcupacion,jcbEstado;
    public JButton jbAceptar, jbCancelar;
    private JScrollPane jspObservaciones;
    public V_crear_rol(V_inicio v_main) {
        super(v_main, "Modificar Producto", DEFAULT_MODALITY_TYPE);
        setName("ModificarProducto");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jpPrincipal);
    }
}
