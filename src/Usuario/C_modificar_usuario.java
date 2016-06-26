/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import DB_manager.DB_manager;
import DB_manager.DB_Funcionario;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import Entities.M_funcionario;
import Entities.M_rol;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramiro Ferreira
 */
public class C_modificar_usuario extends MouseAdapter implements ActionListener, KeyListener {

    C_gestion_usuario c_jifGesUsu;
    private M_funcionario m_funcionario;
    V_modificar_usuario vista;
    DefaultListModel lmRol;
    String password;
    String nombreImagen;
    ImageIcon imagen;
    File fileImage;
    ArrayList roles = new ArrayList();

    public C_modificar_usuario(C_gestion_usuario c_jifGesUsu, M_funcionario m_funcionario) {
        this.c_jifGesUsu = c_jifGesUsu;
        this.m_funcionario = m_funcionario;
        this.vista = new V_modificar_usuario(c_jifGesUsu.c_main.vista, true);
        inicializarVista();
        agregarListeners();
    }

    /**
     * Establece el tamaño, posicion y visibilidad de la vista.
     */
    public void mostrarVista() {
        this.vista.setSize(this.c_jifGesUsu.c_main.establecerTamañoPanel());
        this.vista.setLocationRelativeTo(this.vista.getOwner());
        this.vista.setVisible(true);
    }

    /**
     * Elimina la vista.
     */
    private void cerrar() {
        this.vista.dispose();
        System.runFinalization();
    }

    /**
     * Agrega ActionListeners los controles.
     */
    private void agregarListeners() {
        this.vista.jbCancelar.addActionListener(this);
        this.vista.jbAceptar.addActionListener(this);
        this.vista.jbQuitarRol.addActionListener(this);
        this.vista.jbAgregarRol.addActionListener(this);
        this.vista.jbCambiarImagen.addActionListener(this);
        this.vista.jbCambiarPass.addActionListener(this);
        this.vista.jftCedulaIdentidad.addKeyListener(this);
        this.vista.jftSalario.addKeyListener(this);
        this.vista.jtfNombre.addMouseListener(this);
        this.vista.jtfApellido.addMouseListener(this);
        this.vista.jftCedulaIdentidad.addMouseListener(this);
        this.vista.dccFechaNacimiento.addMouseListener(this);
        this.vista.dccFechaIngreso.addMouseListener(this);
        this.vista.jtfAlias.addMouseListener(this);
    }

    /**
     * Agrega valores a los componentes.
     */
    private void inicializarVista() {
        this.vista.jtfIdFuncionario.setText("");
        this.vista.jftCedulaIdentidad.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                new java.text.DecimalFormat("#,##0"))));
        Vector genero = DB_manager.obtenerGenero();
        for (int i = 0; i < genero.size(); i++) {
            this.vista.jcbGenero.addItem(genero.get(i));
        }
        Vector pais = DB_manager.obtenerPais();
        for (int i = 0; i < pais.size(); i++) {
            this.vista.jcbNacionalidad.addItem(pais.get(i));
        }
        Vector ciudad = DB_manager.obtenerCiudad();
        for (int i = 0; i < ciudad.size(); i++) {
            this.vista.jcbCiudad.addItem(ciudad.get(i));
        }
        Vector estadoCivil = DB_manager.obtenerEstadoCivil();
        for (int i = 0; i < estadoCivil.size(); i++) {
            this.vista.jcbEstadoCivil.addItem(estadoCivil.get(i));
        }
        Vector roles = DB_manager.obtenerRoles();
        for (int i = 0; i < roles.size(); i++) {
            this.vista.jcbRoles.addItem(roles.get(i));
        }
        Vector rolesUsuario = DB_Funcionario.obtenerRolFuncionario(m_funcionario);
        lmRol = new DefaultListModel();
        for (int i = 0; i < rolesUsuario.size(); i++) {
            lmRol.addElement(rolesUsuario.get(i));
        }
        this.vista.jltRol.setModel(lmRol);
        completarCampos(m_funcionario);
        this.vista.jcbGenero.setSelectedItem(this.m_funcionario.getSexo());
        this.vista.jcbNacionalidad.setSelectedItem(this.m_funcionario.getPais());
        this.vista.jcbEstadoCivil.setSelectedItem(this.m_funcionario.getEstado_civil());
        this.vista.jcbCiudad.setSelectedItem(this.m_funcionario.getCiudad());
        //this.v_jdModUsu.jcbEstadoCivil=new JComboBox(DBmanager.obtenerEstadoCivil());
        this.vista.jftSalario.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                new java.text.DecimalFormat("#,##0"))));
    }

    private void completarCampos(M_funcionario funcionario) {
        this.vista.jftCedulaIdentidad.setValue(funcionario.getCedula());
        this.vista.jtfNombre.setText(funcionario.getNombre());
        this.vista.jtfApellido.setText(funcionario.getApellido());
        this.vista.jcbGenero.setSelectedItem(funcionario.getSexo());
        //this.v_jdModUsu.jtfIdUsuario.setText(String.valueOf(funcionario.getIdUsuario()));
        //this.v_jdModUsu.jtfIdPersona.setText(String.valueOf(funcionario.getIdPersona()));
        this.vista.jcbNacionalidad.setSelectedItem(funcionario.getPais());
        this.vista.jftSalario.setValue(funcionario.getSalario());
        this.vista.jtfNroTelefono.setText(funcionario.getNro_telefono());
        this.vista.jtfNroCelular.setText(funcionario.getNro_celular());
        this.vista.jtfDireccion.setText(funcionario.getDireccion());
        this.vista.jtfCorreoElectronico.setText(funcionario.getEmail());
        this.vista.jcbCiudad.setSelectedItem(funcionario.getCiudad());
        //funcionario.getIdCiudad(jcbCiudad.setSelectedIndex());
        //revisar
        //this.v_jdModUsu.jcbEstado.setSelectedItem(funcionario.getEstado());
        this.vista.jtfAlias.setText(funcionario.getAlias());
        /* String fechaNacimiento, fechaIngreso = null;
         SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
         Date dIngreso = null;
         try { 
         dIngreso= df.parse(fechaIngreso);
         } catch (ParseException ex) {
         System.err.println("ParseException: "+ex.toString());
         }
         fechaIngreso= this.m_funcionario.getFechaIngreso();*/
        this.vista.dccFechaIngreso.setDate(funcionario.getFecha_ingreso());
        this.vista.dccFechaNacimiento.setDate(funcionario.getFecha_nacimiento());
        this.vista.jcbEstadoCivil.setSelectedItem(funcionario.getEstado_civil());
        this.vista.jtaObservacion.setText(funcionario.getObservacion());
    }

    private void agregarRol() {
        String rolActual = this.vista.jcbRoles.getSelectedItem().toString();
        String rolLista[] = new String[this.vista.jltRol.getModel().getSize()];
        for (int i = 0; i < rolLista.length; i++) {
            rolLista[i] = this.vista.jltRol.getModel().getElementAt(i).toString();
        }
        boolean existe = false;
        for (int i = 0; i < rolLista.length; i++) {
            if (rolLista[i].equals(rolActual)) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            int option = JOptionPane.showConfirmDialog(vista, "¿Desea confirmar esta operación?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    M_rol rol = DB_Funcionario.obtenerRol(rolActual);
                    DB_Funcionario.insertarRolUsuario(m_funcionario, rol);
                    lmRol.addElement(rolActual);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void quitarRol() {
        if (this.vista.jltRol.getSelectedIndex() > -1) {
            int option = JOptionPane.showConfirmDialog(vista, "¿Desea confirmar esta operación?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                String rolActual = this.vista.jltRol.getSelectedValue().toString();
                lmRol.removeElement(rolActual);
                try {
                    M_rol rol = DB_Funcionario.obtenerRol(rolActual);
                    DB_Funcionario.eliminarRolFuncionario(m_funcionario, rol);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean crearFuncionario(M_funcionario funcionario) {
        /*
         * VALIDAR NOMBRE
         */
        String nombre;
        if (this.vista.jtfNombre.getText().isEmpty()) {
            this.vista.jtfNombre.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo nombre esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales1);
            return false;
        } else {
            nombre = this.vista.jtfNombre.getText();

        }
        /*
         * VALIDAR APELLIDO
         */
        String apellido;
        if (this.vista.jtfApellido.getText().isEmpty()) {
            this.vista.jtfApellido.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo apellido esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales1);
            return false;
        } else {
            apellido = this.vista.jtfApellido.getText();
        }
        /*
         *VALIDAR CI
         */
        Integer cedula;
        try {
            String LongToString = String.valueOf(this.vista.jftCedulaIdentidad.getValue());
            cedula = Integer.valueOf(LongToString.replace(".", ""));
        } catch (Exception e) {
            this.vista.jftCedulaIdentidad.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista, "Coloque un Numero de CI valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales1);
            return false;
        }
        /*
         * VALIDAR FECHA NACIMIENTO
         */
        Date fechaNacimiento;
        try {
            fechaNacimiento = this.vista.dccFechaNacimiento.getDate();
        } catch (Exception e) {
            this.vista.dccFechaNacimiento.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Ingrese una fecha valida en el campo Fecha nacimiento",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales1);
            return false;
        }
        /*
         * VALIDAR ALIAS
         */
        String alias;
        if (this.vista.jtfAlias.getText().isEmpty()) {
            this.vista.jtfAlias.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "El campo alias esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosEmpresariales);
            return false;
        } else {
            alias = this.vista.jtfAlias.getText();
        }
        /*
         *VALIDAR SALARIO
         */
        Integer salario;
        try {
            String valor = this.vista.jftSalario.getText();
            if (valor.isEmpty()) {
                salario = 0;
            } else {
                salario = Integer.valueOf(this.vista.jftSalario.getText());
            }
        } catch (NumberFormatException e) {
            this.vista.jftSalario.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Coloque un numero salario valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosEmpresariales);

            return false;
        }
        /*
         * VALIDAR TELEFONO
         */
        String telefono;
        try {
            String valor = this.vista.jtfNroTelefono.getText();
            if (valor.isEmpty()) {
                telefono = null;
            } else {
                telefono = this.vista.jtfNroTelefono.getText();
            }
        } catch (NumberFormatException e) {
            this.vista.jtfNroTelefono.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Coloque un numero de telefono valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales2);
            return false;
        }
        /*
         * VALIDAR CELULAR
         */
        String celular;
        try {
            String valor = this.vista.jtfNroCelular.getText();
            if (valor.isEmpty()) {
                celular = null;
            } else {
                celular = this.vista.jtfNroCelular.getText();
            }
        } catch (NumberFormatException e) {
            this.vista.jtfNroCelular.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Coloque un numero de celular valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosPersonales2);
            return false;
        }
        /*
         * VALIDAR FECHA INGRESOO
         */
        Date fechaIngreso;
        try {
            //Inicio fechas
            if (this.vista.dccFechaIngreso.getDate() == null) {
                throw new Exception(new NullPointerException());
            } else {
                fechaIngreso = this.vista.dccFechaIngreso.getDate();
            }
        } catch (Exception e) {
            this.vista.dccFechaIngreso.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Ingrese una fecha valida en el campo Fecha ingreso",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpDatosEmpresariales);
            return false;
        }
        if (this.vista.jltRol.getModel().getSize() < 1) {
            javax.swing.JOptionPane.showMessageDialog(this.vista,
                    "Seleccione por lo menos un rol",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.vista.jtpCenter.setSelectedComponent(vista.jpRol);
            return false;
        }
        String email = this.vista.jtfCorreoElectronico.getText();
        String direccion = this.vista.jtfDireccion.getText();
        String observacion = this.vista.jtaObservacion.getText();
        funcionario.setNombre(nombre);
        funcionario.setApellido(apellido);
        funcionario.setCedula(cedula);
        funcionario.setFecha_nacimiento(fechaNacimiento);
        funcionario.setAlias(alias);
        funcionario.setSalario(salario);
        funcionario.setNro_telefono(telefono);
        funcionario.setNro_celular(celular);
        funcionario.setFecha_ingreso(fechaIngreso);
        funcionario.setEstado_civil((String) this.vista.jcbEstadoCivil.getSelectedItem());
        funcionario.setObservacion(observacion);
        funcionario.setDireccion(direccion);
        funcionario.setEmail(email);
        funcionario.setCiudad((String) this.vista.jcbCiudad.getSelectedItem());
        funcionario.setEstado("Activo");
        funcionario.setRol(null);//se establece en el modelo
        funcionario.setSexo((String) this.vista.jcbGenero.getSelectedItem());;
        funcionario.setPais((String) this.vista.jcbNacionalidad.getSelectedItem());
        return true;
    }

    private boolean establecerRoles() {
        int listSize = this.vista.jltRol.getModel().getSize();
        if (listSize < 1) {
            JOptionPane.showMessageDialog(vista, "Escoja un rol", "Parametros incompletos", JOptionPane.ERROR_MESSAGE, null);
            this.vista.jtpCenter.setSelectedComponent(vista.jpRol);
            return false;
        }
        String nombreRol = "";
        roles.clear();
        for (int i = 0; i < listSize; i++) {
            nombreRol = (String) this.vista.jltRol.getModel().getElementAt(i);
            roles.add(nombreRol);
        }
        return true;
    }

    private void actualizarUsuario() {
        if (establecerRoles() && crearFuncionario(m_funcionario)) {
            DB_Funcionario.actualizarFuncionario(m_funcionario);
            this.c_jifGesUsu.vista.jtUsuario.setModel(DB_Funcionario.consultarFuncionario(" ", false, true, true));
            cerrar();
        }
    }

    private void cambiarImagen() {
        JFileChooser selector = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
        selector.setFileFilter(filter);
        int estado = selector.showOpenDialog(this.vista);
        if (estado == JFileChooser.APPROVE_OPTION) {
            fileImage = selector.getSelectedFile();
            nombreImagen = fileImage.getName();
            imagen = new ImageIcon(fileImage.getPath());
            if (imagen.getIconHeight() > 200 && imagen.getIconWidth() > 200) {
                JOptionPane.showMessageDialog(selector, "Seleccione una imagen de 200 x 200 pixeles", "Atencion", JOptionPane.INFORMATION_MESSAGE);
            } else {
                this.vista.jlImagen.setIcon(imagen);
            }
        }
    }

    private void checkJFTcedula() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                String valorIngresado = vista.jftCedulaIdentidad.getText().replace(".", "");
                Long StringToLong = null;
                try {
                    StringToLong = Long.valueOf(valorIngresado);
                } catch (NumberFormatException numberFormatException) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Ingrese solo numeros",
                            "Parametros incorrectos",
                            javax.swing.JOptionPane.OK_OPTION);
                    StringToLong = (Long) m_funcionario.getCedula();
                }
                vista.jftCedulaIdentidad.setValue(StringToLong);
                String valorJFT = vista.jftCedulaIdentidad.getText();
                vista.jftCedulaIdentidad.select(valorJFT.length(), valorJFT.length());
            }
        });
    }

    private void checkJFTsalario() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                String valorIngresado = vista.jftSalario.getText().replace(".", "");
                Long StringToLong = null;
                try {
                    StringToLong = Long.valueOf(valorIngresado);
                } catch (NumberFormatException numberFormatException) {
                    javax.swing.JOptionPane.showMessageDialog(vista, "Ingrese solo numeros",
                            "Parametros incorrectos",
                            javax.swing.JOptionPane.OK_OPTION);
                }
                vista.jftSalario.setValue(StringToLong);
                String valorJFT = vista.jftSalario.getText();
                vista.jftSalario.select(valorJFT.length(), valorJFT.length());
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.jbCancelar) {
            cerrar();
        } else if (e.getSource() == this.vista.jbAceptar) {
            actualizarUsuario();
        } else if (e.getSource() == this.vista.jbAgregarRol) {
            agregarRol();
        } else if (e.getSource() == this.vista.jbQuitarRol) {
            quitarRol();
        } else if (e.getSource() == this.vista.jbCambiarPass) {
            cambiarPass();
        } else if (e.getSource() == this.vista.jbCambiarImagen) {
            cambiarImagen();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.vista.jftCedulaIdentidad.hasFocus()) {
            this.vista.jftCedulaIdentidad.setBackground(Color.white);
        } else if (this.vista.jtfNombre.hasFocus()) {
            this.vista.jtfNombre.setBackground(Color.white);
        } else if (this.vista.jtfApellido.hasFocus()) {
            this.vista.jtfApellido.setBackground(Color.white);
        } else if (this.vista.jtfAlias.hasFocus()) {
            this.vista.jtfAlias.setBackground(Color.white);
        } else if (this.vista.dccFechaIngreso.hasFocus()) {
            this.vista.dccFechaIngreso.setBackground(Color.white);
        } else if (this.vista.dccFechaNacimiento.hasFocus()) {
            this.vista.dccFechaNacimiento.setBackground(Color.white);
        } else if (this.vista.jbCambiarPass.hasFocus()) {
            this.vista.jbCambiarPass.setBackground(Color.white);
        }
    }

    public void keyTyped(KeyEvent e) {
        if (e.getSource() == this.vista.jftCedulaIdentidad) {
            checkJFTcedula();
        } else if (e.getSource() == this.vista.jftSalario) {
            checkJFTsalario();
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    private void cambiarPass() {
        int opcion = JOptionPane.showConfirmDialog(vista, "¿Esta seguro que desea modificar la contraseña?", "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            String password = JOptionPane.showInputDialog(vista, "Inserte su nueva contraseña", "Cambio de contraseña", JOptionPane.PLAIN_MESSAGE);
            DB_Funcionario.cambiarPassword(m_funcionario.getAlias(), password);
        }
    }
}
