/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Producto;

import Pedido.C_crearPedido;
import Pedido.C_verPedido;
import Ventas.C_crearVentaRapida;
import Ventas.C_verMesa;
import bakermanager.C_crear_egreso;
import javax.swing.JDialog;

/**
 *
 * @author Ramiro Ferreira
 */
public class SeleccionarProducto {

    V_seleccionarProducto vista;
    C_seleccionarProducto controlador;

    public SeleccionarProducto(JDialog main, C_crear_egreso egresos) {
        vista = new V_seleccionarProducto(main);
        controlador = new C_seleccionarProducto(vista, egresos);
    }

    public SeleccionarProducto(C_crearVentaRapida crearVentaRapida) {
        vista = new V_seleccionarProducto(crearVentaRapida.vista);
        controlador = new C_seleccionarProducto(vista, crearVentaRapida);
    }

    public SeleccionarProducto(C_verMesa verMesa) {
        vista = new V_seleccionarProducto(verMesa.vista);
        controlador = new C_seleccionarProducto(vista, verMesa);
    }

    public SeleccionarProducto(C_crearPedido crearPedido) {
        vista = new V_seleccionarProducto(crearPedido.vista);
        controlador = new C_seleccionarProducto(vista, crearPedido);
    }

    public SeleccionarProducto(C_verPedido verPedido) {
        vista = new V_seleccionarProducto(verPedido.vista);
        controlador = new C_seleccionarProducto(vista, verPedido);
    }

    public void mostrarVista() {
        controlador.mostrarVista();
    }
}
