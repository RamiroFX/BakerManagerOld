/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Entities.M_funcionario;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;

/**
 *
 * @author Ramiro
 */
/*
 * Esta clase gerencia la comunicacion entre la barra de menu, el logIn y la
 * pantalla principal.
 */
public class C_inicio implements ActionListener {

    public V_inicio vista = null;//referencia a la ventana principal.
    private M_funcionario m_funcionario;
    private escuchadorVentana wa;

    public C_inicio(V_inicio mainFrame, M_funcionario funcionario) {
        this.m_funcionario = funcionario;
        this.vista = mainFrame;
        this.vista.setSize(obtenerDimensionDePantalla());
        wa = new escuchadorVentana();
        agregarListeners();
        //c_ventanaTamaño.mainFrame = this.v_mainFrame;
    }
    /*
     public void mostrarIngreso() {
     Login login = new Login(this);
     }
     */

    public void mostrarVista() {
        this.vista.setVisible(true);
    }

    private void agregarListeners() {
        vista.addWindowListener(wa);
    }

    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();
        //Object enter = ActionEvent.KEY_EVENT_MASK;
       /* if (fuente == v_mainFrame.getJMbarraMenu().jmiLogIn) {
         //se verifica que el panel login no se encuentre creado.
         mostrarIngreso();
         v_mainFrame.getJMbarraMenu().jmiLogIn.setEnabled(false);
         v_mainFrame.getJMbarraMenu().jmiLogOut.setEnabled(true);
         } else if (fuente == v_mainFrame.getJMbarraMenu().jmiLogOut) {
         //frame.getDesktop().getDesktopManager().closeFrame(frame.getCurrentJIF());
         v_mainFrame.getDesktop().removeAll();
         v_mainFrame.getDesktop().updateUI();
         v_mainFrame.setJtfUsuario("");
         v_mainFrame.getJMbarraMenu().jmiLogIn.setEnabled(true);
         v_mainFrame.getJMbarraMenu().jmiLogOut.setEnabled(false);
         } else if (fuente == v_mainFrame.getJMbarraMenu().jmiMenuPrincipal) {
         c_jifMenuPrincipal c_menuPrincipal = new c_jifMenuPrincipal(this);
         c_menuPrincipal.mostrarVista();
         }
         /*if(fuente == jmiPersonalizar)new jdPanelPersonalizar(frame);
         else if (fuente == jmiAcerca)getAcercade();
         else if (fuente == jmiBuscarUsuario)new jdCrearUsuario(null);*/
        /*if (fuente == v_mainFrame.getJMbarraMenu().jmiCerrar) {
         System.exit(0);
         }*/
    }

    public void agregarVentana(JInternalFrame mdi) {
        vista.getDesktop().add(mdi);
        vista.setCurrentJIF(mdi);
        //mdi.setLocation(centrarPantalla(mdi));
        mdi.setVisible(true);
        mdi.moveToFront();
        try {
            mdi.setSelected(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
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
        pantalla.height = pantalla.height - 25;
        return pantalla;
    }

    /**
     * Para poder agregar una ventana cualquiera que sea JInternalFrame o
     * heredados de ella sirve este metodo, solo tienes que crear el
     * JIternalFrame como si fuese un JFrame o Frame normal y lo agregas aquí.
     *
     * @param mdi JInternalFrame
     */
    public Point centrarPantalla(JInternalFrame i) {
        /*con este codigo centramos el panel en el centro del contenedor
         la anchura del contenedor menos la anchura de nuestro componente divido a 2
         lo mismo con la altura.*/
        return new Point((vista.getWidth() - i.getWidth()) / 2, (vista.getHeight() - i.getHeight() - 45) / 2);
    }

    public Dimension establecerTamañoPanel() {
        return new Dimension((int) (vista.getWidth() * 0.8), (int) (vista.getHeight() * 0.8));
    }

    /**
     * @return the funcionario
     */
    public M_funcionario getFuncionario() {
        return m_funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(M_funcionario funcionario) {
        this.m_funcionario = funcionario;
    }
    /*-
     private void inicializarVista() {
     String alias = this.m_funcionario.getAlias();
     this.vista.getJtfUsuario().setText("Conectado como: " + alias);
     }
     */

    private class escuchadorVentana extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
            /*v_mainFrame.trayBar.IconTray();
             if (Config.getTraybar().equals("0")) {
             String[] a = {"Si", "No"};
             Config.setTraybar(String.valueOf(JOptionPane.showOptionDialog(v_mainFrame, "Minimizado al traybar. \n¿Desea volver a ver el mensaje?.\nPuede modificar esto en el Panel Herramientas/Personalizar/Preferencias.", "Alerta", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, a, 0)));
             }*/
        }
    }
}
