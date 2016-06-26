/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import DB_manager.DB_Funcionario;
import DB_manager.DB_manager;
import Entities.M_funcionario;
import Utilities.Config;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramiro Ferreira
 */
public class M_login {

    Config configuracion;
    M_funcionario funcionario;

    public M_login() {
        funcionario = new M_funcionario();
        configuracion = new Config();
    }
    

    public boolean conectar(String user, String password) {
        boolean b = false;
        try {
            b = DB_manager.conectarBD(user, password);
            if (b) {
                funcionario = DB_Funcionario.obtenerDatosFuncionario(user);
            }
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(M_login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    public String obtenerNombreUsuario() {
        try {
            return DB_manager.obtenerNombreUsuario();
        } catch (SQLException ex) {
            Logger.getLogger(M_login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
