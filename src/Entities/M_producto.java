/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

/**
 *
 * @author Ramiro
 */
public class M_producto {
    private String descripcion,marca,rubro,estado;
    private Integer id,codigo,impuesto,precioCosto,precioMinorista,precioMayorista;
    private Double cantActual;

    public M_producto() {
    }

    public M_producto(String descripcion, String marca, String rubro, String estado, Integer id, Integer codigo, Integer impuesto, int precioCosto, Integer precioMinorista, Integer precioMayorista, Double cantActual) {
        this.descripcion = descripcion;
        this.marca = marca;
        this.rubro = rubro;
        this.estado = estado;
        this.id = id;
        this.codigo = codigo;
        this.impuesto = impuesto;
        this.precioCosto = precioCosto;
        this.precioMinorista = precioMinorista;
        this.precioMayorista = precioMayorista;
        this.cantActual = cantActual;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the rubro
     */
    public String getRubro() {
        return rubro;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the codBarra
     */
    public Integer getCodBarra() {
        return codigo;
    }

    /**
     * @param codBarra the codBarra to set
     */
    public void setCodBarra(Integer codBarra) {
        this.codigo = codBarra;
    }

    /**
     * @return the impuesto
     */
    public Integer getImpuesto() {
        return impuesto;
    }

    /**
     * @param impuesto the impuesto to set
     */
    public void setImpuesto(Integer impuesto) {
        this.impuesto = impuesto;
    }

    /**
     * @return the precioCosto
     */
    public Integer getPrecioCosto() {
        return precioCosto;
    }

    /**
     * @param precioCosto the precioCosto to set
     */
    public void setPrecioCosto(Integer precioCosto) {
        this.precioCosto = precioCosto;
    }

    /**
     * @return the precioVenta
     */
    public Integer getPrecioVenta() {
        return precioMinorista;
    }

    /**
     * @param precioVenta the precioVenta to set
     */
    public void setPrecioVenta(Integer precioVenta) {
        this.precioMinorista = precioVenta;
    }

    /**
     * @return the precioMayorista
     */
    public Integer getPrecioMayorista() {
        return precioMayorista;
    }

    /**
     * @param precioMayorista the precioMayorista to set
     */
    public void setPrecioMayorista(Integer precioMayorista) {
        this.precioMayorista = precioMayorista;
    }

    /**
     * @return the cantActual
     */
    public Double getCantActual() {
        return cantActual;
    }

    /**
     * @param cantActual the cantActual to set
     */
    public void setCantActual(Double cantActual) {
        this.cantActual = cantActual;
    }

}
