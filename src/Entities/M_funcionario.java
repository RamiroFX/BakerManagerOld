package Entities;

import DB_manager.DB_manager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class M_funcionario extends M_persona {

    private String ocupacion, observacion, estado, alias, email, nro_celular, nro_telefono,direccion;
    private Date fechaIngreso, fechaSalida;
    private Integer id_funcionario, salario,idEstado;

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    private ArrayList rol;

    public M_funcionario() {
        super();
    }

    public boolean validarUsuario(String url, int dbms, String pass) throws SQLException {
        return DB_manager.conectarBD(dbms, url, getAlias(), pass);
    }

    public boolean validarUsuario(String pass) throws SQLException {
        return DB_manager.conectarBD(getAlias(), pass);
    }

    /**
     * @return the ocupacion
     */
    public String getOcupacion() {
        return ocupacion;
    }

    /**
     * @param ocupacion the ocupacion to set
     */
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFecha_ingreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFecha_ingreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return the fechaSalida
     */
    public Date getFecha_salida() {
        return fechaSalida;
    }

    /**
     * @param fechaSalida the fechaSalida to set
     */
    public void setFecha_salida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * @return the idUsuario
     */
    public Integer getId_funcionario() {
        return id_funcionario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setId_funcionario(Integer id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    /**
     * @return the salario
     */
    public int getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(int salario) {
        this.salario = salario;
    }

    public String imprimirFuncionario() {
        String f = null;
        f = super.getNombre() + ", " + super.getApellido() + ", " + super.getCedula() + ", " + this.getSalario();
        return f;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the rol
     */
    public ArrayList getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(ArrayList rol) {
        this.rol = rol;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the nro_celular
     */
    public String getNro_celular() {
        return nro_celular;
    }

    /**
     * @param nro_celular the nro_celular to set
     */
    public void setNro_celular(String nro_celular) {
        this.nro_celular = nro_celular;
    }

    /**
     * @return the nro_telefono
     */
    public String getNro_telefono() {
        return nro_telefono;
    }

    /**
     * @param nro_telefono the nro_telefono to set
     */
    public void setNro_telefono(String nro_telefono) {
        this.nro_telefono = nro_telefono;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
