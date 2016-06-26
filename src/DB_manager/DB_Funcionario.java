/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_manager;

import Entities.M_funcionario;
import Entities.M_rol;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class DB_Funcionario {

    private static Statement st = null;
    private static PreparedStatement pst = null;
    private static ResultSet rs = null;
    /*
     * CREATE
     */

    public static void insertarFuncionario(M_funcionario funcionario, ArrayList rol, String pass) {
        String idSexo = "(SELECT SEXO_ID_SEXO FROM SEXO WHERE SEXO_DESCRIPCION LIKE'" + funcionario.getSexo() + "%')";
        String idEstadoCivil = "(SELECT ESCI_ID_ESTADO_CIVIL FROM ESTADO_CIVIL WHERE ESCI_DESCRIPCION LIKE'" + funcionario.getEstado_civil() + "%')";
        String idPais = "(SELECT PAIS_ID_PAIS FROM PAIS WHERE PAIS_DESCRIPCION LIKE'" + funcionario.getPais() + "%')";
        String idCiudad = "(SELECT ciud_id_ciudad FROM CIUDAD WHERE CIUD_DESCRIPCION LIKE'" + funcionario.getCiudad() + "%')";
        String idEstado = "(SELECT ESTA_ID_ESTADO FROM ESTADO WHERE ESTA_DESCRIPCION LIKE'" + funcionario.getEstado() + "%')";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String insPersona = "INSERT INTO PERSONA "
                + "(PERS_CI,PERS_NOMBRE,PERS_APELLIDO,"
                + "PERS_SEXO,PERS_FECHA_NACIMIENTO,PERS_ESTADO_CIVIL,"
                + "PERS_ID_PAIS,PERS_ID_CIUDAD)"
                + "VALUES (" + funcionario.getCedula() + ",'" + funcionario.getNombre() + "','" + funcionario.getApellido() + "'," + idSexo + ",to_date('" + sdf.format(funcionario.getFecha_nacimiento()) + "','dd/mm/yyyy')," + idEstadoCivil + "," + idPais + "," + idCiudad + ")";
        String insFuncionario = "INSERT INTO FUNCIONARIO"
                + "( func_id_persona, func_alias,func_fecha_ingreso, func_estado,func_salario, func_nro_celular, func_nro_telefono,func_email, func_direccion, func_observacion)"
                + "VALUES(" + funcionario.getId_persona() + ",'" + funcionario.getAlias() + "',TO_DATE('" + sdf.format(funcionario.getFecha_ingreso()) + "','dd/mm/yyyy')," + idEstado + "," + funcionario.getSalario() + "," + funcionario.getNro_celular() + "," + funcionario.getNro_telefono() + ",'" + funcionario.getEmail() + "','" + funcionario.getDireccion() + "','" + funcionario.getObservacion() + "')";
        System.out.println("Insert persona: " + insPersona);
        System.out.println("Insert cliente: " + insFuncionario);
        String createUser = "CREATE USER " + funcionario.getAlias() + " "
                + " PASSWORD '" + pass + "'"
                + " NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN";

        try {
            System.out.println("insertarFuncionario(): Insertando datos personales: ");
            DB_manager.habilitarTransaccionManual();
            pst = DB_manager.getConection().prepareStatement(insPersona, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.executeUpdate();
            pst.close();
            System.out.println("insertarFuncionario(): Insertando datos funcionario: ");
            pst = DB_manager.getConection().prepareStatement(insFuncionario, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.executeUpdate();
            pst.close();
            System.out.println("insertarFuncionario(): Creando usuario: ");
            pst = DB_manager.getConection().prepareStatement(createUser, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.executeUpdate();
            pst.close();
            System.out.println("insertarFuncionario(): Asignando roles");
            for (int i = 0; i < rol.size(); i++) {
                M_rol _rol = (M_rol) rol.get(i);
                String as = "INSERT INTO ROL_USUARIO (ROUS_ID_ROL,ROUS_ID_FUNCIONARIO)VALUES(" + _rol.getId() + ",+" + funcionario.getId_funcionario() + ")";
                pst = DB_manager.getConection().prepareStatement(as, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                pst.executeUpdate();
                pst.close();
            }
            for (int i = 0; i < rol.size(); i++) {
                M_rol _rol = (M_rol) rol.get(i);
                System.out.println("_rol.getDescripcion() = " + _rol.getDescripcion());
                if (_rol.getDescripcion().equals("ADMINISTRADOR")) {
                    String select = "GRANT administrador TO " + funcionario.getAlias() + "";
                    System.out.println("GRANTING DBA: " + select);
                    pst = DB_manager.getConection().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    pst.executeUpdate();
                    pst.close();
                } else if (_rol.getDescripcion().equals("COMPRAS")) {
                    String select = "GRANT compras TO " + funcionario.getAlias() + "";
                    System.out.println("GRANTING compras: " + select);
                    pst = DB_manager.getConection().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    pst.executeUpdate();
                    pst.close();
                } else if (_rol.getDescripcion().equals("VENTAS")) {
                    String select = "GRANT ventas TO " + funcionario.getAlias() + "";
                    System.out.println("GRANTING ventas: " + select);
                    pst = DB_manager.getConection().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    pst.executeUpdate();
                    pst.close();
                } else if (_rol.getDescripcion().equals("PRODUCTOS")) {
                    String select = "GRANT productos TO " + funcionario.getAlias() + "";
                    System.out.println("GRANTING productos: " + select);
                    pst = DB_manager.getConection().prepareStatement(select, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    pst.executeUpdate();
                    pst.close();
                }
            }
            System.out.println("insertarFuncionario(): Se inserto exitosamente");
            JOptionPane.showMessageDialog(null, "Funcionario insertado", "Exito", JOptionPane.INFORMATION_MESSAGE);
            DB_manager.establecerTransaccion();
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    /*
     * Esta funcion solo inserta el funcionario en la tabla persona y tabla funcionario
     * sin crear un usuario de base de datos.
     */
    public static void insertarFuncionarioFX(M_funcionario funcionario, ArrayList rol, String pass) {
        long id_persona = -1L;
        long id_funcionario = -1L;
        String id_ciudad = "SELECT CIUD_ID_CIUDAD FROM CIUDAD WHERE CIUD_DESCRIPCION LIKE'" + funcionario.getCiudad() + "'";
        String id_pais = "SELECT PAIS_ID_PAIS FROM PAIS WHERE PAIS_DESCRIPCION LIKE'" + funcionario.getPais() + "'";
        String id_estado_civil = "SELECT ESCI_ID_ESTADO_CIVIL FROM ESTADO_CIVIL WHERE ESCI_DESCRIPCION LIKE'" + funcionario.getEstado_civil() + "'";
        String id_estado = "SELECT ESTA_ID_ESTADO FROM ESTADO WHERE ESTA_DESCRIPCION LIKE'" + funcionario.getEstado() + "'";
        String id_sexo = "SELECT SEXO_ID_SEXO FROM SEXO WHERE SEXO_DESCRIPCION LIKE'" + funcionario.getSexo() + "'";
        String INSERT_FUNCIONARIO = "INSERT INTO FUNCIONARIO(FUNC_ID_PERSONA, FUNC_ALIAS, FUNC_FECHA_INGRESO, FUNC_ID_ESTADO, FUNC_FECHA_SALIDA, FUNC_SALARIO, FUNC_NRO_CELULAR, FUNC_NRO_TELEFONO, FUNC_EMAIL, FUNC_DIRECCION, FUNC_OBSERVACION, FUNC_IMAGEN)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String INSERT_PERSONA = "INSERT INTO PERSONA(PERS_CI, PERS_NOMBRE, PERS_APELLIDO, PERS_ID_SEXO, PERS_FECHA_NACIMIENTO, PERS_ID_ESTADO_CIVIL, PERS_ID_PAIS, PERS_ID_CIUDAD)VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String createUser = "CREATE USER " + funcionario.getAlias() + " "
                + " PASSWORD '" + pass + "'"
                + " NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN";
        try {
            DB_manager.habilitarTransaccionManual();
            pst = DB_manager.getConection().prepareStatement(id_ciudad, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setId_ciudad(rs.getInt("CIUD_ID_CIUDAD"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(id_pais, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setId_pais(rs.getInt("PAIS_ID_PAIS"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(id_estado, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setIdEstado(rs.getInt("ESTA_ID_ESTADO"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(id_estado_civil, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setId_estado_civil(rs.getInt("ESCI_ID_ESTADO_CIVIL"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(id_sexo, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setId_sexo(rs.getInt("SEXO_ID_SEXO"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(INSERT_PERSONA, PreparedStatement.RETURN_GENERATED_KEYS);
            try {
                if (funcionario.getCedula() == null) {
                    pst.setNull(1, Types.INTEGER);
                } else {
                    pst.setInt(1, (int) funcionario.getCedula());
                }
            } catch (Exception e) {
                pst.setNull(1, Types.INTEGER);
            }
            pst.setString(2, funcionario.getNombre());
            pst.setString(3, funcionario.getApellido());
            pst.setInt(4, funcionario.getId_sexo());
            try {
                if (funcionario.getFecha_nacimiento() == null) {
                    pst.setNull(5, Types.DATE);
                } else {
                    pst.setDate(5, new java.sql.Date(funcionario.getFecha_nacimiento().getTime()));
                }
            } catch (Exception e) {
                pst.setNull(5, Types.DATE);
            }
            pst.setInt(6, funcionario.getId_estado_civil());
            pst.setInt(7, funcionario.getId_pais());
            pst.setInt(8, funcionario.getId_ciudad());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            while (rs != null && rs.next()) {
                id_persona = rs.getLong(1);
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(INSERT_FUNCIONARIO, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setInt(1, (int) id_persona);
            pst.setString(2, funcionario.getAlias());//NOT NULL
            try {
                if (funcionario.getFecha_ingreso() == null) {
                    pst.setNull(3, Types.DATE);
                } else {
                    pst.setDate(3, new java.sql.Date(funcionario.getFecha_ingreso().getTime()));
                }
            } catch (Exception e) {
                pst.setNull(3, Types.DATE);
            }
            pst.setInt(4, funcionario.getIdEstado());//NOT NULL
            try {
                if (funcionario.getFecha_salida() == null) {
                    pst.setNull(5, Types.DATE);
                } else {
                    pst.setDate(5, new java.sql.Date(funcionario.getFecha_salida().getTime()));
                }
            } catch (Exception e) {
                pst.setNull(5, Types.DATE);
            }
            pst.setInt(6, funcionario.getSalario());//NOT NULL
            try {
                if (funcionario.getNro_celular() == null) {
                    pst.setNull(7, Types.VARCHAR);
                } else {
                    pst.setString(7, funcionario.getNro_celular());
                }
            } catch (Exception e) {
                pst.setNull(7, Types.VARCHAR);
            }
            try {
                if (funcionario.getNro_telefono() == null) {
                    pst.setNull(8, Types.VARCHAR);
                } else {
                    pst.setString(8, funcionario.getNro_telefono());
                }
            } catch (Exception e) {
                pst.setNull(8, Types.VARCHAR);
            }
            try {
                if (funcionario.getEmail() == null) {
                    pst.setNull(9, Types.VARCHAR);
                } else {
                    pst.setString(9, funcionario.getEmail());
                }
            } catch (Exception e) {
                pst.setNull(9, Types.VARCHAR);
            }
            try {
                if (funcionario.getDireccion() == null) {
                    pst.setNull(10, Types.VARCHAR);
                } else {
                    pst.setString(10, funcionario.getDireccion());
                }
            } catch (Exception e) {
                pst.setNull(10, Types.VARCHAR);
            }
            try {
                if (funcionario.getObservacion() == null) {
                    pst.setNull(11, Types.VARCHAR);
                } else {
                    pst.setString(11, funcionario.getObservacion());
                }
            } catch (Exception e) {
                pst.setNull(11, Types.VARCHAR);
            }
            pst.setNull(12, Types.BINARY);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            while (rs != null && rs.next()) {
                id_funcionario = rs.getLong(1);
            }
            rs.close();
            pst.close();
            for (int i = 0; i < rol.size(); i++) {
                M_rol _rol = (M_rol) rol.get(i);
                String as = "INSERT INTO ROL_USUARIO (ROUS_ID_ROL,ROUS_ID_FUNCIONARIO)VALUES(" + _rol.getId() + "," + id_funcionario + ")";
                pst = DB_manager.getConection().prepareStatement(as);
                pst.executeUpdate();
                pst.close();
            }
            pst = DB_manager.getConection().prepareStatement(createUser);
            pst.executeUpdate();
            pst.close();
            DB_manager.establecerTransaccion();
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static void insertarRolUsuario(M_funcionario funcionario, M_rol rol) {
        String INSERT_PERSONA = "INSERT INTO ROL_USUARIO(ROUS_ID_ROL, ROUS_ID_FUNCIONARIO) VALUES (?, ?);";
        try {
            DB_manager.habilitarTransaccionManual();
            pst = DB_manager.getConection().prepareStatement(INSERT_PERSONA);
            pst.setInt(1, rol.getId());
            pst.setInt(2, funcionario.getId_funcionario());
            pst.executeUpdate();
            rs.close();
            pst.close();
            DB_manager.establecerTransaccion();
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /*
     * READ
     */
    public static M_funcionario obtenerDatosFuncionarioID(int idFuncionario) {
        M_funcionario f = null;
        String genero = "(SELECT SEXO_DESCRIPCION  FROM SEXO WHERE SEXO_ID_SEXO = PERS_ID_SEXO) \"pers_sexo\"";
        String pais = "(SELECT PAIS_DESCRIPCION FROM PAIS WHERE PERS_ID_PAIS=PAIS_ID_PAIS) \"PERS_NACIONALIDAD\"";
        String ciudad = " (SELECT CIUD_DESCRIPCION FROM CIUDAD WHERE PERS_ID_CIUDAD=CIUD_ID_CIUDAD)\"PERS_CIUDAD\"";
        String estadoCivil = " (SELECT ESCI_DESCRIPCION FROM ESTADO_CIVIL WHERE ESCI_ID_ESTADO_CIVIL=PERS_ID_ESTADO_CIVIL)\"pers_estado_civil\"";
        String estado = " (SELECT ESTA_DESCRIPCION FROM ESTADO WHERE ESTA_ID_ESTADO=FUNC_ID_ESTADO)\"func_estado\"";
        String queryFunc = "func_id_funcionario, func_id_persona, func_alias,func_fecha_ingreso, " + estado + ", func_fecha_salida,func_salario,func_id_estado, func_nro_celular, func_nro_telefono,func_email, func_direccion, func_observacion";
        String queryPers = "pers_id_persona, pers_ci, pers_nombre, pers_apellido," + genero + ", pers_fecha_nacimiento, " + pais + "," + ciudad + ", " + estadoCivil + "";
        String Query = "SELECT " + queryFunc + "," + queryPers + " FROM funcionario, persona " + "WHERE func_id_persona = pers_id_persona " + "AND func_id_funcionario =" + idFuncionario;

        try {
            st = DB_manager.getConection().createStatement();
            rs = st.executeQuery(Query);
            while (rs.next()) {
                f = new M_funcionario();
                f.setPais(rs.getString("PERS_NACIONALIDAD"));
                f.setSalario(rs.getInt("func_salario"));
                f.setCiudad(rs.getString("PERS_CIUDAD"));
                f.setFecha_nacimiento(rs.getDate("PERS_FECHA_NACIMIENTO"));
                f.setSexo(rs.getString("pers_sexo"));
                f.setNro_celular(rs.getString("func_nro_celular"));
                f.setNro_telefono(rs.getString("func_nro_telefono"));
                f.setEmail(rs.getString("func_email"));
                f.setDireccion(rs.getString("FUNC_DIRECCION"));
                f.setAlias(rs.getString("func_alias"));
                f.setNombre(rs.getString("pers_nombre"));
                f.setApellido(rs.getString("pers_apellido"));
                f.setFecha_ingreso(rs.getDate("FUNC_FECHA_INGRESO"));
                f.setId_persona(rs.getInt("pers_id_persona"));
                f.setCedula(rs.getInt("pers_ci"));
                f.setEstado(rs.getString("func_estado"));
                f.setIdEstado(rs.getInt("func_id_estado"));
                f.setEstado_civil(rs.getString("pers_estado_civil"));
                f.setId_funcionario(rs.getInt("func_id_funcionario"));
                f.setObservacion(rs.getString("FUNC_OBSERVACION"));
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return f;
    }

    public static M_funcionario obtenerDatosFuncionario(String alias) {
        M_funcionario f = null;
        String genero = "(SELECT SEXO_DESCRIPCION  FROM SEXO WHERE SEXO_ID_SEXO = PERS_ID_SEXO) \"pers_sexo\"";
        String pais = "(SELECT PAIS_DESCRIPCION FROM PAIS WHERE PERS_ID_PAIS=PAIS_ID_PAIS) \"PERS_NACIONALIDAD\"";
        String ciudad = " (SELECT CIUD_DESCRIPCION FROM CIUDAD WHERE PERS_ID_CIUDAD=CIUD_ID_CIUDAD)\"PERS_CIUDAD\"";
        String estadoCivil = " (SELECT ESCI_DESCRIPCION FROM ESTADO_CIVIL WHERE ESCI_ID_ESTADO_CIVIL=PERS_ID_ESTADO_CIVIL)\"pers_estado_civil\"";
        String estado = " (SELECT ESTA_DESCRIPCION FROM ESTADO WHERE ESTA_ID_ESTADO=FUNC_ID_ESTADO)\"func_estado\"";
        String queryFunc = "func_id_funcionario, func_id_persona, func_alias,func_fecha_ingreso, " + estado + ", func_fecha_salida,func_salario,func_id_estado, func_nro_celular, func_nro_telefono,func_email, func_direccion, func_observacion";
        String queryPers = "pers_id_persona, pers_ci, pers_nombre, pers_apellido," + genero + ", pers_fecha_nacimiento, " + pais + "," + ciudad + ", " + estadoCivil + "";
        String Query = "SELECT " + queryFunc + "," + queryPers + " FROM funcionario, persona " + "WHERE func_id_persona = pers_id_persona " + "AND func_alias like '" + alias + "'";

        try {
            st = DB_manager.getConection().createStatement();
            rs = st.executeQuery(Query);
            while (rs.next()) {
                f = new M_funcionario();
                f.setPais(rs.getString("PERS_NACIONALIDAD"));
                f.setSalario(rs.getInt("func_salario"));
                f.setCiudad(rs.getString("PERS_CIUDAD"));
                f.setFecha_nacimiento(rs.getDate("PERS_FECHA_NACIMIENTO"));
                f.setSexo(rs.getString("pers_sexo"));
                f.setNro_celular(rs.getString("func_nro_celular"));
                f.setNro_telefono(rs.getString("func_nro_telefono"));
                f.setEmail(rs.getString("func_email"));
                f.setDireccion(rs.getString("FUNC_DIRECCION"));
                f.setAlias(rs.getString("func_alias"));
                f.setNombre(rs.getString("pers_nombre"));
                f.setApellido(rs.getString("pers_apellido"));
                f.setFecha_ingreso(rs.getDate("FUNC_FECHA_INGRESO"));
                f.setId_persona(rs.getInt("pers_id_persona"));
                f.setCedula(rs.getInt("pers_ci"));
                f.setEstado(rs.getString("func_estado"));
                f.setIdEstado(rs.getInt("func_id_estado"));
                f.setEstado_civil(rs.getString("pers_estado_civil"));
                f.setId_funcionario(rs.getInt("func_id_funcionario"));
                f.setObservacion(rs.getString("FUNC_OBSERVACION"));
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return f;
    }

    public static Vector obtenerRolFuncionario(M_funcionario f) {
        Vector rol = null;
        String q = "SELECT rol_descripcion "
                + "FROM funcionario f, rol r, rol_usuario ru "
                + "WHERE ru.rous_id_funcionario = f.func_id_funcionario "
                + "AND ru.rous_id_rol = r.rol_id_rol "
                + "AND f.func_alias LIKE ('" + f.getAlias() + "')";
        try {
            st = DB_manager.getConection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            rol = new Vector();
            while (rs.next()) {
                rol.add(rs.getString("rol_descripcion"));
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return rol;
    }

    public static M_rol obtenerRolesFuncionario(M_funcionario f) {
        M_rol roles = null;
        String q = "SELECT rol_descripcion "
                + "FROM funcionario f, rol r, rol_usuario ru "
                + "WHERE ru.rous_id_funcionario = f.func_id_funcionario "
                + "AND ru.rous_id_rol = r.rol_id_rol "
                + "AND f.func_alias LIKE ('" + f.getAlias() + "')";
        try {
            st = DB_manager.getConection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            rs.last();
            roles = new M_rol();
            rs.beforeFirst();
            while (rs.next()) {
                //roles.getRol()[rs.getRow()-1]=(rs.getString("rol_descripcion"));
                roles = new M_rol(rs.getInt("rol_id_rol"), rs.getString("rol_descripcion"));
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return roles;
    }

    public static M_rol obtenerRol(String rolDescripcion) {
        M_rol roles = null;
        String q = "SELECT rol_id_rol,rol_descripcion "
                + "FROM rol "
                + "WHERE rol_descripcion  LIKE ('" + rolDescripcion + "')";
        try {
            st = DB_manager.getConection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            while (rs.next()) {
                roles = new M_rol(rs.getInt("rol_id_rol"), rs.getString("rol_descripcion"));
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return roles;
    }

    public static Vector obtenerChoferes() {
        Vector funcionario = null;
        String q = "SELECT "
                + "PERS_NOMBRE, "
                + "PERS_APELLIDO "
                + "FROM PERSONA P, FUNCIONARIO F, ROL_USUARIO, ROL "
                + "WHERE P.PERS_ID_PERSONA = F.FUNC_ID_PERSONA "
                + "AND ROUS_ID_FUNCIONARIO = FUNC_ID_FUNCIONARIO "
                + "AND ROUS_ID_ROL = ROL_ID_ROL "
                + "AND ROL_DESCRIPCION LIKE 'CHOFER'";
        try {
            st = DB_manager.getConection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(q);
            funcionario = new Vector();
            while (rs.next()) {
                funcionario.add(rs.getString("pers_nombre") + " " + rs.getString("pers_apellido"));
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return funcionario;
    }

    public static ResultSetTableModel consultarFuncionario(String busqueda, boolean isExclusivo, boolean entidad, boolean ci) {
        ResultSetTableModel rstm = null;
        try {
            if (DB_manager.getConection() == null) {
                throw new IllegalStateException("Connection already closed.");
            }
            /*String genero = "(SELECT SEXO_DESCRIPCION  FROM SEXO WHERE SEXO_ID_SEXO = PERS_SEXO) \"Genero\"";
             String pais = "(SELECT PAIS_DESCRIPCION FROM PAIS WHERE PERS_ID_PAIS=PAIS_ID_PAIS) \"Nacionalidad\"";
             String ciudad = " (SELECT CIUD_DESCRIPCION FROM CIUDAD WHERE PERS_ID_CIUDAD=CIUD_ID_CIUDAD)\"Ciudad\"";
             String estadoCivil = " (SELECT ESCI_DESCRIPCION FROM ESTADO_CIVIL WHERE ESCI_ID_ESTADO_CIVIL=PERS_ESTADO_CIVIL)\"Estado civil\"";
             String estado = " (SELECT ESTA_DESCRIPCION FROM ESTADO WHERE ESTA_ID_ESTADO=FUNC_ESTADO)\"Estado\"";
             String q2 = "SELECT FUNC_ID_FUNCIONARIO\"ID\",FUNC_ALIAS \"Alias\", PERS_CI\"CI\",PERS_NOMBRE\"Nombre\",PERS_APELLIDO\"Apellido\" "
             + "FROM funcionario, persona " + ""
             + "WHERE func_id_persona = pers_id_persona " + "AND pers_nombre LIKE '" + busqueda + "%'";
             */

            String SELECT = "SELECT FUNC_ID_FUNCIONARIO\"ID\",FUNC_ALIAS \"Alias\", PERS_CI\"CI\",PERS_NOMBRE\"Nombre\",PERS_APELLIDO\"Apellido\" ";
            String FROM = "FROM funcionario, persona ";
            String WHERE = "WHERE func_id_persona = pers_id_persona AND ";
            String ORDER_BY = " ORDER BY PERS_NOMBRE ";
            if (isExclusivo) {
                busqueda = busqueda + "%";
            } else {
                busqueda = "%" + busqueda + "%";
            }
            if (entidad && ci) {
                WHERE = WHERE + "(LOWER(pers_nombre) LIKE '" + busqueda + "' OR LOWER(PERS_APELLIDO) LIKE '" + busqueda + "' OR CAST(PERS_CI AS TEXT) LIKE '" + busqueda + "')";
            } else if (entidad) {
                WHERE = WHERE + "(LOWER(pers_nombre) LIKE '" + busqueda + "' OR LOWER(PERS_APELLIDO) LIKE '" + busqueda + "')";
            } else if (ci) {
                WHERE = WHERE + "CAST(PERS_CI AS TEXT) LIKE  LIKE '" + busqueda + "'";
            } else if (!entidad && !ci) {
                WHERE = WHERE + "(LOWER(pers_nombre) LIKE '" + busqueda + "' OR LOWER(PERS_APELLIDO) LIKE '" + busqueda + "' OR CAST(PERS_CI AS TEXT) LIKE '" + busqueda + "')";
            }
            String query = SELECT + FROM + WHERE + ORDER_BY;
            st = DB_manager.getConection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // se ejecuta el query y se obtienen los resultados en un ResultSet
            rs = st.executeQuery(query);
            rstm = new ResultSetTableModel(rs);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } /*finally {
         try {
         if (rs != null) {
         rs.close();
         }
         if (st != null) {
         st.close();
         }
         } catch (SQLException ex) {
         Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
         lgr.log(Level.WARNING, ex.getMessage(), ex);
         }
         }*/

        return rstm;
    }

    public static ResultSetTableModel consultarFuncionarioChofer() {
        ResultSetTableModel rstm = null;
        try {
            if (DB_manager.getConection() == null) {
                throw new IllegalStateException("Connection already closed.");
            }
            String query = "SELECT "
                    + "FUNC_ID_FUNCIONARIO \"ID\", "
                    + "PERS_NOMBRE \"Nombre\", "
                    + "PERS_APELLIDO \"Apellido\", "
                    + "PERS_CI \"Cedula de identidad\", "
                    + "PERS_FECHA_NACIMIENTO \"Fecha de nacimiento\" "
                    + "FROM "
                    + "FUNCIONARIO, "
                    + "PERSONA, "
                    + "ROL, "
                    + "ROL_USUARIO "
                    + "WHERE "
                    + "FUNC_ID_PERSONA = PERS_ID_PERSONA AND "
                    + "FUNC_ID_FUNCIONARIO = ROUS_ID_FUNCIONARIO AND "
                    + "ROUS_ID_ROL = ROL_ID_ROL AND "
                    + "ROL_DESCRIPCION LIKE 'CHOFER' AND "
                    + "FUNC_ESTADO = 2";
            st = DB_manager.getConection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // se ejecuta el query y se obtienen los resultados en un ResultSet
            rs = st.executeQuery(query);
            rstm = new ResultSetTableModel(rs);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }/* finally {
         try {
         if (rs != null) {
         rs.close();
         }
         if (st != null) {
         st.close();
         }
         } catch (SQLException ex) {
         Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
         lgr.log(Level.WARNING, ex.getMessage(), ex);
         }
         }*/

        return rstm;
    }

    /*
     * UPDATE
     */
    public static void actualizarFuncionario(M_funcionario funcionario) {
        System.out.println("actualizarFuncionario: " + funcionario.getId_funcionario());
        String id_ciudad = "SELECT CIUD_ID_CIUDAD FROM CIUDAD WHERE CIUD_DESCRIPCION LIKE'" + funcionario.getCiudad() + "'";
        String id_pais = "SELECT PAIS_ID_PAIS FROM PAIS WHERE PAIS_DESCRIPCION LIKE'" + funcionario.getPais() + "'";
        String id_estado_civil = "SELECT ESCI_ID_ESTADO_CIVIL FROM ESTADO_CIVIL WHERE ESCI_DESCRIPCION LIKE'" + funcionario.getEstado_civil() + "'";
        String id_estado = "SELECT ESTA_ID_ESTADO FROM ESTADO WHERE ESTA_DESCRIPCION LIKE'" + funcionario.getEstado() + "'";
        String id_sexo = "SELECT SEXO_ID_SEXO FROM SEXO WHERE SEXO_DESCRIPCION LIKE'" + funcionario.getSexo() + "'";
        String UPDATE_FUNCIONARIO = "UPDATE FUNCIONARIO SET FUNC_ALIAS=?, "
                + "FUNC_FECHA_INGRESO=?, FUNC_ID_ESTADO=?, FUNC_FECHA_SALIDA=?, "
                + "FUNC_SALARIO=?, FUNC_NRO_CELULAR=?, FUNC_NRO_TELEFONO=?, "
                + "FUNC_EMAIL=?, FUNC_DIRECCION=?, FUNC_OBSERVACION=?, "
                + "FUNC_IMAGEN=? WHERE FUNC_ID_FUNCIONARIO = ?;";
        String UPDATE_PERSONA = "UPDATE PERSONA SET PERS_CI=?, PERS_NOMBRE=?, "
                + "PERS_APELLIDO=?, PERS_ID_SEXO=?, PERS_FECHA_NACIMIENTO=?, "
                + "PERS_ID_ESTADO_CIVIL=?, PERS_ID_PAIS=?, PERS_ID_CIUDAD=? "
                + "WHERE PERS_ID_PERSONA = ?;";
        try {
            DB_manager.habilitarTransaccionManual();
            pst = DB_manager.getConection().prepareStatement(id_ciudad, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setId_ciudad(rs.getInt("CIUD_ID_CIUDAD"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(id_pais, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setId_pais(rs.getInt("PAIS_ID_PAIS"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(id_estado, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setIdEstado(rs.getInt("ESTA_ID_ESTADO"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(id_estado_civil, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setId_estado_civil(rs.getInt("ESCI_ID_ESTADO_CIVIL"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(id_sexo, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                funcionario.setId_sexo(rs.getInt("SEXO_ID_SEXO"));
            }
            rs.close();
            pst.close();
            pst = DB_manager.getConection().prepareStatement(UPDATE_FUNCIONARIO);
            pst.setString(1, funcionario.getAlias());//NOT NULL
            try {
                if (funcionario.getFecha_ingreso() == null) {
                    pst.setNull(2, Types.DATE);
                } else {
                    pst.setDate(2, new java.sql.Date(funcionario.getFecha_ingreso().getTime()));
                }
            } catch (Exception e) {
                pst.setNull(2, Types.DATE);
            }
            pst.setInt(3, funcionario.getIdEstado());//NOT NULL
            try {
                if (funcionario.getFecha_salida() == null) {
                    pst.setNull(4, Types.DATE);
                } else {
                    pst.setDate(4, new java.sql.Date(funcionario.getFecha_salida().getTime()));
                }
            } catch (Exception e) {
                pst.setNull(4, Types.DATE);
            }
            pst.setInt(5, funcionario.getSalario());//NOT NULL
            try {
                if (funcionario.getNro_celular() == null) {
                    pst.setNull(6, Types.VARCHAR);
                } else {
                    pst.setString(6, funcionario.getNro_celular());
                }
            } catch (Exception e) {
                pst.setNull(6, Types.VARCHAR);
            }
            try {
                if (funcionario.getNro_telefono() == null) {
                    pst.setNull(7, Types.VARCHAR);
                } else {
                    pst.setString(7, funcionario.getNro_telefono());
                }
            } catch (Exception e) {
                pst.setNull(7, Types.VARCHAR);
            }
            try {
                if (funcionario.getEmail() == null) {
                    pst.setNull(8, Types.VARCHAR);
                } else {
                    pst.setString(8, funcionario.getEmail());
                }
            } catch (Exception e) {
                pst.setNull(8, Types.VARCHAR);
            }
            try {
                if (funcionario.getDireccion() == null) {
                    pst.setNull(9, Types.VARCHAR);
                } else {
                    pst.setString(9, funcionario.getDireccion());
                }
            } catch (Exception e) {
                pst.setNull(9, Types.VARCHAR);
            }
            try {
                if (funcionario.getObservacion() == null) {
                    pst.setNull(10, Types.VARCHAR);
                } else {
                    pst.setString(10, funcionario.getObservacion());
                }
            } catch (Exception e) {
                pst.setNull(10, Types.VARCHAR);
            }
            pst.setNull(11, Types.BINARY);
            pst.setInt(12, funcionario.getId_funcionario());
            pst.executeUpdate();
            pst = DB_manager.getConection().prepareStatement(UPDATE_PERSONA);
            try {
                if (funcionario.getCedula() == null) {
                    pst.setNull(1, Types.INTEGER);
                } else {
                    pst.setInt(1, (int) funcionario.getCedula());
                }
            } catch (Exception e) {
                pst.setNull(1, Types.INTEGER);
            }
            pst.setString(2, funcionario.getNombre());
            pst.setString(3, funcionario.getApellido());
            pst.setInt(4, funcionario.getId_sexo());
            try {
                if (funcionario.getFecha_nacimiento() == null) {
                    pst.setNull(5, Types.DATE);
                } else {
                    pst.setDate(5, new java.sql.Date(funcionario.getFecha_nacimiento().getTime()));
                }
            } catch (Exception e) {
                pst.setNull(5, Types.DATE);
            }
            pst.setInt(6, funcionario.getId_estado_civil());
            pst.setInt(7, funcionario.getId_pais());
            pst.setInt(8, funcionario.getId_ciudad());
            pst.setInt(9, funcionario.getId_persona());
            pst.executeUpdate();
            DB_manager.establecerTransaccion();
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    /*
     * DELETE
     */
    public static void eliminarFuncionario(M_funcionario funcionario) {
        System.out.println("eliminarFuncionario: " + funcionario.getId_funcionario());
        String deleteFuncionario = "DELETE FROM  funcionario "
                + " WHERE func_id_funcionario = " + funcionario.getId_funcionario() + "";
        //String idPersona="(SELECT PERS_ID_PERSONA FROM PERSONA,FUNCIONARIO WHERE PERS_ID_PERSONA = FUNC_ID_PERSONA AND FUNC_ID_FUNCIONARIO ="+funcionario+")";
        String deletePersona = "DELETE FROM  persona "
                + " WHERE pers_id_persona =" + funcionario.getId_persona();
        String borrarPermisos = "DELETE FROM ROL_USUARIO WHERE ROUS_ID_FUNCIONARIO = " + funcionario.getId_funcionario();

        String borrarUSUARIO = "DROP USER " + funcionario.getAlias() + " CASCADE ";

        try {
            DB_manager.habilitarTransaccionManual();
            st = DB_manager.getConection().createStatement();
            System.out.println("Query(borrarPermisos): " + borrarPermisos);
            st.executeUpdate(borrarPermisos);
            st = DB_manager.getConection().createStatement();
            System.out.println("Query(Delete funcionario): " + deleteFuncionario);
            st.executeUpdate(deleteFuncionario);
            System.out.println("Query(Delete persona): " + deletePersona);
            st.executeUpdate(deletePersona);
            st = DB_manager.getConection().createStatement();
            System.out.println("Query(borrarUSUARIO): " + borrarUSUARIO);
            st.executeUpdate(borrarUSUARIO);
            System.out.println("DBmanagerFuncionario.eliminarFuncionario()");
            DB_manager.establecerTransaccion();
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    /*
     * miscelanea
     */
    public static void insertarImagenFuncionario(int idUsuario, String pathname) {
        FileInputStream fis = null;
        try {
            File file = new File(pathname);
            fis = new FileInputStream(file);
            PreparedStatement ps = DB_manager.getConection().prepareStatement("UPDATE funcionario "
                    + "SET func_nombre_imagen=?, func_imagen=? "
                    + "WHERE func_id_funcionario = ?");
            ps.setString(1, file.getName());
            ps.setBinaryStream(2, fis, file.length());
            ps.setInt(3, idUsuario);
            ps.executeUpdate();
            ps.close();
        } catch (IOException ex) {
            Logger.getLogger(DB_Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DB_Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(DB_Funcionario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /* Metodo que convierte una cadena de bytes en una imagen JPG
     * input:
     * bytes: array que contiene los binarios de la imagen
     * Output: la foto JPG en formato IMAGE
     */
    private static Image ConvertirImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis;
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        return reader.read(0, param);
    }

    public static void actualizarImagen(byte[] img, int id_funcionario, String imgName) {
        try {

            String sql = "UPDATE funcionario "
                    + "SET func_nombre_imagen=?, func_imagen=? "
                    + "WHERE func_id_funcionario = ?";
            pst = DB_manager.getConection().prepareStatement(sql);

            pst.setString(1, imgName);
            pst.setBytes(2, img);
            pst.setInt(3, id_funcionario);

            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    public static byte[] getImage(int id) {
        byte[] byteImg = null;
        try {
            // String byteImg="";
            pst = DB_manager.getConection().prepareStatement("SELECT image FROM image WHERE id = ?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                byteImg = rs.getBytes(1);
                // use the data in some way here
            }
            rs.close();
            pst.close();

            return byteImg;
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return byteImg;
    }

    public static M_funcionario consultarFuncionarioPorID(Integer id_chofer) {
        M_funcionario f = null;
        String q = "SELECT *  FROM PERSONA P, FUNCIONARIO F "
                + "WHERE p.pers_id_persona = f.func_id_persona "
                + "AND func_id_funcionario = " + id_chofer + "";
        try {
            st = DB_manager.getConection().createStatement();
            rs = st.executeQuery(q);
            while (rs.next()) {
                f = new M_funcionario();
                f.setFecha_nacimiento(rs.getDate("pers_fecha_nacimiento"));
                f.setSexo(rs.getString("pers_sexo"));
                f.setNro_celular(rs.getString("func_nro_celular"));
                f.setNro_telefono(rs.getString("func_nro_telefono"));
                f.setEmail(rs.getString("func_email"));
                f.setDireccion(rs.getString("FUNC_DIRECCION"));
                f.setAlias(rs.getString("func_alias"));
                f.setNombre(rs.getString("pers_nombre"));
                f.setApellido(rs.getString("pers_apellido"));
                f.setFecha_ingreso(rs.getDate("FUNC_FECHA_INGRESO"));
                f.setId_persona(rs.getInt("pers_id_persona"));
                f.setCedula(rs.getInt("pers_ci"));
                f.setEstado(rs.getString("func_estado"));
                f.setEstado_civil(rs.getString("pers_estado_civil"));
                f.setSalario(rs.getInt("func_salario"));
                f.setId_funcionario(rs.getInt("func_id_funcionario"));
                f.setObservacion(rs.getString("FUNC_OBSERVACION"));
                //f.setRol(obtenerRolesFuncionario(f));
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return f;
    }

    public static void eliminarRolFuncionario(M_funcionario m_funcionario, M_rol rol) {
        String DELETE_ROL_USUARIO = "DELETE FROM ROL_USUARIO WHERE ROUS_ID_ROL = ? AND ROUS_ID_FUNCIONARIO = ? ;";
        try {
            DB_manager.habilitarTransaccionManual();
            pst = DB_manager.getConection().prepareStatement(DELETE_ROL_USUARIO);
            pst.setInt(1, rol.getId());
            pst.setInt(2, m_funcionario.getId_funcionario());
            pst.executeUpdate();
            DB_manager.establecerTransaccion();
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void cambiarPassword(String alias, String newPass) {
        String DELETE_ROL_USUARIO = "ALTER USER " + alias + "  WITH PASSWORD '" + newPass + "';";
        try {
            DB_manager.habilitarTransaccionManual();
            pst = DB_manager.getConection().prepareStatement(DELETE_ROL_USUARIO);
            pst.executeUpdate();
            DB_manager.establecerTransaccion();
        } catch (SQLException ex) {
            System.out.println(ex.getNextException());
            if (DB_manager.getConection() != null) {
                try {
                    DB_manager.getConection().rollback();
                } catch (SQLException ex1) {
                    Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
                    lgr.log(Level.WARNING, ex1.getMessage(), ex1);
                }
            }
            Logger lgr = Logger.getLogger(DB_Funcionario.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
