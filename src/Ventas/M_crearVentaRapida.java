/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventas;

import DB_manager.DB_Funcionario;
import DB_manager.DB_Ingreso;
import Entities.M_facturaCabecera;
import Entities.M_facturaDetalle;
import Entities.M_funcionario;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ramiro Ferreira
 */
public class M_crearVentaRapida {

    private M_funcionario funcionario;
    private M_facturaCabecera cabecera;
    private M_facturaDetalle detalle;
    private ArrayList<M_facturaDetalle> detalles;
    private DefaultTableModel dtm;

    public M_crearVentaRapida() {
        this.funcionario = DB_Funcionario.obtenerDatosFuncionarioID(1);
        this.cabecera = new M_facturaCabecera();
        this.cabecera.setFuncionario(funcionario);
        this.detalle = new M_facturaDetalle();
        this.detalles = new ArrayList<>();
        dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Cantidad");
        dtm.addColumn("Descripción");
        dtm.addColumn("Precio");
        dtm.addColumn("Descuento");
        dtm.addColumn("Exenta");
        dtm.addColumn("IVA 5%");
        dtm.addColumn("IVA 10%");
    }

    public M_funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(M_funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public M_facturaCabecera getCabecera() {
        return cabecera;
    }

    public void setCabecera(M_facturaCabecera cabecera) {
        this.cabecera = cabecera;
    }

    public M_facturaDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(M_facturaDetalle detalle) {
        this.detalle = detalle;
    }

    public ArrayList<M_facturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<M_facturaDetalle> detalles) {
        this.detalles = detalles;
    }

    public void validarDatos() {
    }

    public void insertarVenta() {
    }

    /**
     * @return the dtm
     */
    public DefaultTableModel getDtm() {
        return dtm;
    }

    /**
     * @param dtm the dtm to set
     */
    public void setDtm(DefaultTableModel dtm) {
        this.dtm = dtm;
    }

    public void guardarVenta() {
        DB_Ingreso.insertarIngreso(getCabecera(), getDetalles());
    }
}
