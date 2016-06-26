/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Resumen;

import Entities.M_egreso_cabecera;
import Main.C_inicio;
import Pedido.C_gestionPedido;
import java.util.Date;

/**
 *
 * @author Ramiro Ferreira
 */
public class Resumen {

    public static final int PEDIDO = 1;
    public static final int EGRESO = 2;
    M_resumen modelo;
    V_resumen vista;
    C_resumen controlador;

    public Resumen(C_inicio c_inicio, M_egreso_cabecera egresoCabecera, Date fechaInicio, Date fechaFin) {
        this.modelo = new M_resumen(egresoCabecera, fechaInicio, fechaFin);
        this.vista = new V_resumen(c_inicio.vista);
        this.controlador = new C_resumen(this.modelo, this.vista, c_inicio);
    }

    public Resumen(C_gestionPedido controlador) {
        this.modelo = new M_resumen(controlador);
        this.vista = new V_resumen(controlador.c_inicio.vista, PEDIDO);
        this.controlador = new C_resumen(this.modelo, this.vista, controlador.c_inicio);
    }

    public void mostrarVista() {
        this.controlador.mostraVista();
    }
}
