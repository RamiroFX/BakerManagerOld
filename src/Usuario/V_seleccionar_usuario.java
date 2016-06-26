/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import Main.V_inicio;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author RamiroFX
 */
class V_seleccionar_usuario extends JDialog {

    JButton jbAceptar, jbCancelar;
    JScrollPane jspUsuarios;
    JTable jtUsuarios;
    JPanel jpBotones;

    public V_seleccionar_usuario(V_inicio main) {
        super(main, "Seleccionar chofer disponible", true);
        setSize(600, 400);
        setLocationRelativeTo(main);
        initComp();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jspUsuarios,BorderLayout.CENTER);
        getContentPane().add(jpBotones,BorderLayout.SOUTH);
    }
    
    private void initComp() {
        jtUsuarios = new JTable();
        jspUsuarios = new JScrollPane(jtUsuarios);
        jbAceptar = new  JButton("Aceptar");
        jbCancelar = new  JButton("Cancelar");
        jpBotones = new JPanel();
        jpBotones.add(jbAceptar);
        jpBotones.add(jbCancelar);
    }
}
