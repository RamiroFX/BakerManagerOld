/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Entities.M_funcionario;
import Usuario.V_crear_rol;

/**
 *
 * @author Administrador
 */
public class C_crear_rol implements ActionListener{
    private M_funcionario m_funcionario;
    C_gestion_usuario jifUsuario;
    V_crear_rol v_jdCreRol;
    public C_crear_rol(M_funcionario m_funcionario, C_gestion_usuario jifUsuario) {
        this.m_funcionario = m_funcionario;
        this.jifUsuario = jifUsuario;
        this.v_jdCreRol= new V_crear_rol(jifUsuario.c_main.vista);
        agregarListeners();
    }




    /**
     * Establece el tamaño, posicion y visibilidad de la vista.
     */
    public void mostrarVista(){
        this.v_jdCreRol.setSize(this.jifUsuario.c_main.establecerTamañoPanel());
        this.v_jdCreRol.setLocationRelativeTo(this.v_jdCreRol.getOwner());
        this.v_jdCreRol.setVisible(true);
    }

    /**
     * Elimina la vista.
     */
    private void cerrar(){
        this.v_jdCreRol.dispose();
        System.runFinalization();
    }

    /**
     * Agrega ActionListeners los controles.
     */
    private void agregarListeners(){

    }

    /**
     * Agrega valores a los componentes.
     */
    private void inicializarVista(){

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.v_jdCreRol.jbCancelar)cerrar();
        if(e.getSource()==this.v_jdCreRol.jbAceptar){
        }
        if(e.getSource()==this.v_jdCreRol.jbCancelar)cerrar();
    }

}

