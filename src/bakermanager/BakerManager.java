/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bakermanager;

import Main.C_inicio;
import MenuPrincipal.C_MenuPrincipal;

/**
 *
 * @author Usuario
 */
public class BakerManager {
    
    M_Egresos modelo;
    V_crear_egreso vista;
    C_crear_egreso controlador;

    public BakerManager(C_inicio c_inicio) {
        modelo = new M_Egresos();
        vista = new V_crear_egreso(c_inicio.vista, true);
        controlador = new C_crear_egreso(vista, modelo);
    }

    public BakerManager(C_MenuPrincipal menuPrincipal) {
        modelo = new M_Egresos();
        vista = new V_crear_egreso(null, true);
        controlador = new C_crear_egreso(menuPrincipal,vista, modelo);
    }
    
    public void mostrarVista(){
        this.controlador.mostrarVista();
    }
}
