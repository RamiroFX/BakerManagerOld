/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import Main.V_inicio;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Ramiro Ferreira
 */
public class V_modificar_usuario extends javax.swing.JDialog {

    private javax.swing.JPanel  jpImagen, jpNorth;
    private javax.swing.JScrollPane jspObservacion, jspRol, jspAyuda;
    public javax.swing.JButton jbAceptar, jbAgregarRol, jbCambiarImagen, jbCancelar, jbQuitarRol,
            jbCambiarPass;
    public javax.swing.JComboBox jcbCiudad, jcbEstadoCivil, jcbGenero, jcbNacionalidad, jcbRoles;
    public javax.swing.JLabel jlAlias, jlApellido, jlCedulaIdentidad, jlCiudad, jlEstadoCivil,
            jlFechaNacimiento,jlFechaSalida, jlGenero, jlIdEmpleado, jlImagen, jlNacionalidad, jlNombre,
            jlNroCelular, jlNroTelefono, jlPassword, jlFechaIngreso, jlSalario, jlCorreoElectronico, jlDireccion;
    public javax.swing.JList jltRol;
    public javax.swing.JPanel jpDatosEmpresariales, jpSouth,jpDatosPersonales1, jpRol, jpDatosPersonales2;
    public javax.swing.JTextArea jtaObservacion, jtaAyuda;
    public javax.swing.JFormattedTextField jftCedulaIdentidad,jftSalario ;
    public javax.swing.JTextField jtfAlias, jtfApellido, jtfCorreoElectronico,
            jtfDireccion, jtfIdFuncionario, jtfNombre, jtfNroCelular, jtfNroTelefono;
    public JDateChooser dccFechaNacimiento, dccFechaIngreso, dccFechaSalida;
    public javax.swing.JTabbedPane jtpCenter;

    public V_modificar_usuario(V_inicio v_jfMain, boolean modal) {
        super(v_jfMain, modal);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Actualizar usuario");
        setAlwaysOnTop(false);
        setName("jdActualizarUsuario");
        setPreferredSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        initPanelDatosPersonales1();
        initPanelDatosPersonales2();
        initPanelDatosEmpresariales();
        initPaneRol();
        initPaneNorth();
        initPanelSouth();
        jtpCenter = new JTabbedPane();
        jtpCenter.add("Datos Generales", jpDatosPersonales1);
        jtpCenter.add("Datos Personales", jpDatosPersonales2);
        jtpCenter.add("Datos Empresariales", jpDatosEmpresariales);
        jtpCenter.add("Roles", jpRol);
        getContentPane().add(jpNorth, java.awt.BorderLayout.NORTH);
        getContentPane().add(jtpCenter, java.awt.BorderLayout.CENTER);
        getContentPane().add(jpSouth, java.awt.BorderLayout.SOUTH);
        pack();
    }

    private void initPanelDatosPersonales1() {
        //Panel
        jpDatosPersonales1 = new javax.swing.JPanel();
        //jpDatosPersonales1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos personales 1"));
        jpDatosPersonales1.setLayout(new java.awt.GridLayout(7, 4));
        int swingConstant = javax.swing.SwingConstants.CENTER;
        //Labels, textfields, combobox
        jlNombre = new javax.swing.JLabel("Nombre (*)");
        jlNombre.setHorizontalAlignment(swingConstant);
        jtfNombre = new javax.swing.JTextField();
        jlApellido = new javax.swing.JLabel("Apellido (*)");
        jtfApellido = new javax.swing.JTextField();
        jlApellido.setHorizontalAlignment(swingConstant);
        jlFechaNacimiento = new javax.swing.JLabel("Fecha de nacimiento");
        jlFechaNacimiento.setHorizontalAlignment(swingConstant);
        dccFechaNacimiento = new JDateChooser();
        dccFechaNacimiento.setDateFormatString("dd/MM/yyyy");
        jlCedulaIdentidad = new javax.swing.JLabel("Cedula de identidad (*)");
        jlCedulaIdentidad.setHorizontalAlignment(swingConstant);
        jftCedulaIdentidad = new javax.swing.JFormattedTextField();
        jlNacionalidad = new javax.swing.JLabel("Nacionalidad");
        jlNacionalidad.setHorizontalAlignment(swingConstant);
        jcbNacionalidad = new javax.swing.JComboBox();
        jlCiudad = new javax.swing.JLabel("Ciudad");
        jlCiudad.setHorizontalAlignment(swingConstant);
        jcbCiudad = new javax.swing.JComboBox();
        jlGenero = new javax.swing.JLabel("Género");
        jlGenero.setHorizontalAlignment(swingConstant);
        jcbGenero = new javax.swing.JComboBox();
        jlEstadoCivil = new javax.swing.JLabel("Estado civil");
        jlEstadoCivil.setHorizontalAlignment(swingConstant);
        jcbEstadoCivil = new javax.swing.JComboBox();

        jpDatosPersonales1.add(jlNombre);
        jpDatosPersonales1.add(jtfNombre);
        jpDatosPersonales1.add(jlApellido);
        jpDatosPersonales1.add(jtfApellido);
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(jlFechaNacimiento);
        jpDatosPersonales1.add(dccFechaNacimiento);
        jpDatosPersonales1.add(jlCedulaIdentidad);
        jpDatosPersonales1.add(jftCedulaIdentidad);
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(jlNacionalidad);
        jpDatosPersonales1.add(jcbNacionalidad);
        jpDatosPersonales1.add(jlCiudad);
        jpDatosPersonales1.add(jcbCiudad);
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(new Component() {
        });
        jpDatosPersonales1.add(jlGenero);
        jpDatosPersonales1.add(jcbGenero);
        jpDatosPersonales1.add(jlEstadoCivil);
        jpDatosPersonales1.add(jcbEstadoCivil);
    }

    private void initPanelDatosPersonales2() {
        //Panel
        jpDatosPersonales2 = new javax.swing.JPanel(new java.awt.GridLayout(6, 2));
        int swingConstant = javax.swing.SwingConstants.CENTER;
        //Labels, Textfields & textarea
        jlNroTelefono = new javax.swing.JLabel("Número de telefono");
        jlNroTelefono.setHorizontalAlignment(swingConstant);
        jtfNroTelefono = new javax.swing.JTextField();
        jlNroCelular = new javax.swing.JLabel("Número de celular");
        jlNroCelular.setHorizontalAlignment(swingConstant);
        jtfNroCelular = new javax.swing.JTextField();
        jtaObservacion = new javax.swing.JTextArea();
        jspObservacion = new javax.swing.JScrollPane(jtaObservacion);
        jspObservacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        jlDireccion = new javax.swing.JLabel("Dirección");
        jlDireccion.setHorizontalAlignment(swingConstant);
        jtfDireccion = new javax.swing.JTextField();
        //Adding components into panel
        jpDatosPersonales2.add(jlDireccion);
        jpDatosPersonales2.add(jtfDireccion);
        jpDatosPersonales2.add(new Component() {
        });
        jpDatosPersonales2.add(new Component() {
        });
        jpDatosPersonales2.add(jlNroTelefono);
        jpDatosPersonales2.add(jtfNroTelefono);
        jpDatosPersonales2.add(new Component() {
        });
        jpDatosPersonales2.add(new Component() {
        });
        jpDatosPersonales2.add(jlNroCelular);
        jpDatosPersonales2.add(jtfNroCelular);
        jpDatosPersonales2.add(new Component() {
        });
        jpDatosPersonales2.add(new Component() {
        });
    }

    private void initPanelDatosEmpresariales() {
        //Panel
        jpDatosEmpresariales = new javax.swing.JPanel(new java.awt.GridLayout(8, 4));
        int swingConstant = javax.swing.SwingConstants.CENTER;
        //jpDatosEmpresariales.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos empresariales"));
        //Labels, textfields, buttons, combobox, list & passwordfields
        jlAlias = new javax.swing.JLabel();
        jlAlias.setHorizontalAlignment(swingConstant);
        jlAlias.setText("Alias (*)");
        jtfAlias = new javax.swing.JTextField();
        jlFechaIngreso = new javax.swing.JLabel();
        jlFechaIngreso.setHorizontalAlignment(swingConstant);
        jlFechaIngreso.setText("Fecha de ingreso");
        jlFechaSalida = new javax.swing.JLabel("Fecha de salida");
        jlFechaSalida.setHorizontalAlignment(swingConstant);
        dccFechaIngreso = new JDateChooser();
        dccFechaIngreso.setDateFormatString("dd/MM/yyyy");
        dccFechaSalida = new JDateChooser();
        dccFechaSalida.setDateFormatString("dd/MM/yyyy");
        jlSalario = new javax.swing.JLabel();
        jlSalario.setHorizontalAlignment(swingConstant);
        jlSalario.setText("Salario");
        jftSalario = new javax.swing.JFormattedTextField();
        jlIdEmpleado = new javax.swing.JLabel();
        jlIdEmpleado.setHorizontalAlignment(swingConstant);
        jlIdEmpleado.setText("Id. funcionario");
        jtfIdFuncionario = new javax.swing.JTextField();
        jtfIdFuncionario.setEditable(false);
        jlPassword = new javax.swing.JLabel();
        jlPassword.setHorizontalAlignment(swingConstant);
        jlPassword.setText("Contraseña");
        jbCambiarPass = new javax.swing.JButton("Cambiar contraseña");
        jlCorreoElectronico = new javax.swing.JLabel();
        jlCorreoElectronico.setHorizontalAlignment(swingConstant);
        jlCorreoElectronico.setText("Correo electrónico");
        jtfCorreoElectronico = new javax.swing.JTextField();


        jpDatosEmpresariales.add(jlIdEmpleado);
        jpDatosEmpresariales.add(jtfIdFuncionario);
        jpDatosEmpresariales.add(jlAlias);
        jpDatosEmpresariales.add(jtfAlias);
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });

        jpDatosEmpresariales.add(jlFechaIngreso);
        jpDatosEmpresariales.add(dccFechaIngreso);
        jpDatosEmpresariales.add(jlPassword);
        jpDatosEmpresariales.add(jbCambiarPass);
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });


        jpDatosEmpresariales.add(jlCorreoElectronico);
        jpDatosEmpresariales.add(jtfCorreoElectronico);
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });

        jpDatosEmpresariales.add(jlSalario);
        jpDatosEmpresariales.add(jftSalario);
        jpDatosEmpresariales.add(jlFechaSalida);
        jpDatosEmpresariales.add(dccFechaSalida);
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });
        jpDatosEmpresariales.add(new Component() {
        });

    }

    private void initPaneNorth() {
        //Panel
        jpImagen = new javax.swing.JPanel(new java.awt.BorderLayout());
        jpImagen.setBorder(javax.swing.BorderFactory.createTitledBorder("Imagen"));
        //Label & Button
        jlImagen = new javax.swing.JLabel();
        jlImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlImagen.setPreferredSize(new java.awt.Dimension(200, 200));
        jbCambiarImagen = new javax.swing.JButton("Cambiar imagen");
        //Adding components into panel
        jpImagen.add(jlImagen, java.awt.BorderLayout.CENTER);
        jpImagen.add(jbCambiarImagen, java.awt.BorderLayout.SOUTH);
        jpNorth = new javax.swing.JPanel(new java.awt.GridLayout(1, 2));
        javax.swing.JPanel panel2 = new javax.swing.JPanel(new java.awt.GridLayout(1, 2));
        jtaAyuda = new javax.swing.JTextArea();
        jtaAyuda.setEditable(false);
        jtaAyuda.setEnabled(false);
        jtaAyuda.setText("Los campos marcados con un asterisco(*) son obligatorios.");
        jspAyuda = new javax.swing.JScrollPane(jtaAyuda);
        jpNorth.add(jpImagen);
        jpNorth.add(new JLabel("Los campos marcados con un asterisco(*) son obligatorios."));
    }

    private void initPaneRol() {
        //Panel
        jpRol = new javax.swing.JPanel(new java.awt.GridLayout(1, 2));
        javax.swing.JPanel left = new javax.swing.JPanel(new java.awt.GridLayout(2, 1));
        javax.swing.JPanel right = new javax.swing.JPanel(new java.awt.BorderLayout());
        javax.swing.JPanel botones = new javax.swing.JPanel();
        javax.swing.JPanel comobox = new javax.swing.JPanel(new java.awt.BorderLayout());

        jcbRoles = new javax.swing.JComboBox();
        jltRol = new javax.swing.JList();
        jltRol.setBorder(javax.swing.BorderFactory.createTitledBorder("Roles (*)"));
        jspRol = new javax.swing.JScrollPane(jltRol);
        jbAgregarRol = new javax.swing.JButton();
        jbAgregarRol.setText("Agregar");
        jbQuitarRol = new javax.swing.JButton();
        jbQuitarRol.setText("Quitar");
        comobox.add(jcbRoles, java.awt.BorderLayout.SOUTH);
        botones.add(jbAgregarRol);
        botones.add(jbQuitarRol);
        left.add(comobox);
        left.add(botones);
        right.add(jspRol);
        jpRol.add(left);
        jpRol.add(right);
    }

    private void initPanelSouth() {
        jpSouth = new javax.swing.JPanel();
        jpSouth.setBorder(new javax.swing.border.EtchedBorder());
        jbAceptar = new javax.swing.JButton("Aceptar");
        jbCancelar = new javax.swing.JButton("Cancelar");
        jpSouth.add(jbAceptar);
        jpSouth.add(jbCancelar);
    }
}
