/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Entities.M_funcionario;
import Login.Login;

/**
 *
 * @author Ramiro Ferreira
 */
public class Inicio {

    M_funcionario m_funcionario;
    V_inicio v_mainframe;
    C_inicio c_main;

    public Inicio() {
        v_mainframe = new V_inicio();
        c_main = new C_inicio(v_mainframe, m_funcionario);
        c_main.mostrarVista();
    }

    public void mostrarLogin() {
        Login login = new Login(c_main);
        login.mostrarVista();
    }
}
