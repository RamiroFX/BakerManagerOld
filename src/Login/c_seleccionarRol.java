/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import DB_manager.DB_Funcionario;
import DB_manager.DB_manager;
import Entities.M_funcionario;
import Main.C_inicio;
import MenuPrincipal.MenuPrincipal;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Administrador
 */
public class c_seleccionarRol implements ActionListener, KeyListener {

    public v_jifSeleccionarRol vista;
    private M_funcionario funcionario;
    C_inicio c_main; //referencia a la ventana principal

    public c_seleccionarRol(v_jifSeleccionarRol jifSelRol, M_funcionario funcionario, C_inicio c_main) {
        this.c_main = c_main;
        this.funcionario = funcionario;
        this.vista = jifSelRol;
        inicializarVista();
        agregarListeners();
    }

    public void mostrarVista() {
        this.vista.setSize((int) (this.c_main.vista.getWidth() * 0.3), (int) (this.c_main.vista.getHeight() * 0.2));
        this.vista.setLocation(new Point((this.c_main.vista.getWidth() - this.vista.getWidth()) / 2, (this.c_main.vista.getHeight() - this.vista.getHeight() - 45) / 2));
        this.c_main.agregarVentana(vista);
    }

    private void inicializarVista() {
        Vector vRolUsuario = DB_Funcionario.obtenerRolFuncionario(this.c_main.getFuncionario());
        for (int i = 0; i < vRolUsuario.size(); i++) {
            this.vista.jcbRol.addItem(vRolUsuario.get(i));
        }
    }

    private void agregarListeners() {
        vista.jbAceptar.addActionListener(this);
        vista.jbAceptar.addKeyListener(this);
        vista.jbSalir.addActionListener(this);
        vista.jcbRol.addActionListener(this);
        vista.jcbRol.addKeyListener(this);
    }

    private void controlarAccesoBarraMenu(Vector v) {
        Iterator it = v.iterator();
        while (it.hasNext()) {
            String rolMenuItem = it.next().toString();
            System.out.println(rolMenuItem);
//            if (rolMenuItem.equals("jifGestionUsuario")) {
//                c_main.v_mainFrame.getJMbarraMenu().jmGestionUsuario.setEnabled(true);
//            } else if (rolMenuItem.equals("jifGestionProducto")) {
//                c_main.v_mainFrame.getJMbarraMenu().jmGestionProducto.setEnabled(true);
//            } else if (rolMenuItem.equals("jifGestionCompra")) {
//                c_main.v_mainFrame.getJMbarraMenu().jmGestionCompra.setEnabled(true);
//            } else if (rolMenuItem.equals("jifGestionVenta")) {
//                c_main.v_mainFrame.getJMbarraMenu().jmGestionVenta.setEnabled(true);
//            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jbSalir) {
            c_main.vista.setJtfUsuario("");
            //c_main.vista.getJMbarraMenu().jmiLogOut.setEnabled(false);
            //c_main.vista.getJMbarraMenu().jmiLogIn.setEnabled(true);
            vista.cerrar();
        } else if (e.getSource() == vista.jbAceptar) {
            try {
                vista.dispose();
                c_main.vista.setJtfUsuario(DB_manager.obtenerNombreUsuario());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            getFuncionario().setOcupacion((String) vista.jcbRol.getSelectedItem());
            //controlarAccesoBarraMenu(DB_manager.obtenerTablas(funcionario.getOcupacion()));
            MenuPrincipal c_menuPrincipal = new MenuPrincipal(c_main);
            c_menuPrincipal.mostrarVista();
        }
    }

    /**
     * @return the funcionario
     */
    public M_funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(M_funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (this.vista.jcbRol.hasFocus()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                this.vista.jbAceptar.requestFocusInWindow();
            }
        }
        if (this.vista.jbAceptar.hasFocus()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    vista.dispose();
                    c_main.vista.setJtfUsuario(DB_manager.obtenerNombreUsuario());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                getFuncionario().setOcupacion((String) vista.jcbRol.getSelectedItem());
                ///controlarAccesoBarraMenu(DB_manager.obtenerTablas(funcionario.getOcupacion()));
                MenuPrincipal c_menuPrincipal = new MenuPrincipal(c_main);
                c_menuPrincipal.mostrarVista();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}
