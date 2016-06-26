/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import Entities.M_funcionario;
import Main.C_inicio;

/**
 *
 * @author Administrador
 */
public class seleccionarRol {

    private c_seleccionarRol controlador;
    private v_jifSeleccionarRol vista;
    private C_inicio c_main;

    public seleccionarRol(C_inicio c_main, M_funcionario funcionario) {
        this.c_main = c_main;
        vista = new v_jifSeleccionarRol();
        controlador = new c_seleccionarRol(vista, this.c_main.getFuncionario(), this.c_main);
    }

    /**
     * @return the selecRol
     */
    public c_seleccionarRol getSelecRol() {
        return controlador;
    }

    /**
     * @param selecRol the selecRol to set
     */
    public void setSelecRol(c_seleccionarRol selecRol) {
        this.controlador = selecRol;
    }

    /**
     * @return the jifSelRol
     */
    public v_jifSeleccionarRol getJifSelRol() {
        return vista;
    }

    /**
     * @param jifSelRol the jifSelRol to set
     */
    public void setJifSelRol(v_jifSeleccionarRol jifSelRol) {
        this.vista = jifSelRol;
    }

    public void mostrarVista() {
        controlador.mostrarVista();
    }
}
