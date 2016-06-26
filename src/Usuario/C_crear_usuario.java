/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import DB_manager.DB_manager;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import Entities.M_funcionario;
import java.util.Date;

/**
 *
 * @author Ramiro
 */
public class C_crear_usuario extends MouseAdapter implements ActionListener, KeyListener {

    C_gestion_usuario c_jifGesUsu;
    private V_crear_usuario v_jdCreUsu;
    private M_crear_usuario modelo;

    public C_crear_usuario(C_gestion_usuario c_jifGesUsu) {
        this.modelo = new M_crear_usuario();
        this.c_jifGesUsu = c_jifGesUsu;
        this.v_jdCreUsu = new V_crear_usuario(c_jifGesUsu.c_main.vista, true);
        inicializarVista();
        agregarListeners();
    }

    /**
     * Establece el tamaño, posicion y visibilidad de la vista.
     */
    public void mostrarVista() {
        this.v_jdCreUsu.setSize(800, 650);
        this.v_jdCreUsu.setLocationRelativeTo(this.v_jdCreUsu.getOwner());
        this.v_jdCreUsu.setVisible(true);
    }

    /**
     * Elimina la vista.
     */
    private void cerrar() {
        this.v_jdCreUsu.dispose();
        System.runFinalization();
    }

    /**
     * Agrega ActionListeners los controles.
     */
    private void agregarListeners() {
        this.v_jdCreUsu.jbCancelar.addActionListener(this);
        this.v_jdCreUsu.jbAceptar.addActionListener(this);
        this.v_jdCreUsu.jbQuitarRol.addActionListener(this);
        this.v_jdCreUsu.jbAgregarRol.addActionListener(this);
        this.v_jdCreUsu.jbCambiarImagen.addActionListener(this);
        this.v_jdCreUsu.jftCedulaIdentidad.addKeyListener(this);
        this.v_jdCreUsu.jftSalario.addKeyListener(this);
        this.v_jdCreUsu.jtfNombre.addMouseListener(this);
        this.v_jdCreUsu.jtfApellido.addMouseListener(this);
        this.v_jdCreUsu.jftCedulaIdentidad.addMouseListener(this);
        this.v_jdCreUsu.dccFechaNacimiento.addMouseListener(this);
        this.v_jdCreUsu.dccFechaIngreso.addMouseListener(this);
        this.v_jdCreUsu.jpassword1.addMouseListener(this);
        this.v_jdCreUsu.jpassword2.addMouseListener(this);
        this.v_jdCreUsu.jtfAlias.addMouseListener(this);
    }

    /**
     * Agrega valores a los componentes.
     */
    private void inicializarVista() {
        this.v_jdCreUsu.jtfIdFuncionario.setText("");
        Vector genero = DB_manager.obtenerGenero();
        for (int i = 0; i < genero.size(); i++) {
            this.v_jdCreUsu.jcbGenero.addItem(genero.get(i));
        }
        Vector pais = DB_manager.obtenerPais();
        for (int i = 0; i < pais.size(); i++) {
            this.v_jdCreUsu.jcbNacionalidad.addItem(pais.get(i));
        }
        Vector ciudad = DB_manager.obtenerCiudad();
        for (int i = 0; i < ciudad.size(); i++) {
            this.v_jdCreUsu.jcbCiudad.addItem(ciudad.get(i));
        }
        Vector estadoCivil = DB_manager.obtenerEstadoCivil();
        for (int i = 0; i < estadoCivil.size(); i++) {
            this.v_jdCreUsu.jcbEstadoCivil.addItem(estadoCivil.get(i));
        }
        Vector roles = DB_manager.obtenerRoles();
        for (int i = 0; i < roles.size(); i++) {
            this.v_jdCreUsu.jcbRoles.addItem(roles.get(i));
        }
        this.v_jdCreUsu.jltRol.setModel(this.modelo.lmRol);
        this.v_jdCreUsu.dccFechaIngreso.setDate(Calendar.getInstance().getTime());
        this.v_jdCreUsu.jftCedulaIdentidad.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                        new javax.swing.text.NumberFormatter(
                                new java.text.DecimalFormat("#,##0"))));
        this.v_jdCreUsu.jftSalario.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                        new javax.swing.text.NumberFormatter(
                                new java.text.DecimalFormat("#,##0"))));
    }

    private M_funcionario isValidDataEntry() {
        /*
         * VALIDAR NOMBRE
         */
        String nombre;
        if (this.v_jdCreUsu.jtfNombre.getText().isEmpty()) {
            this.v_jdCreUsu.jtfNombre.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu,
                    "El campo nombre esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosPersonales1);
            return null;
        } else {
            nombre = this.v_jdCreUsu.jtfNombre.getText();

        }
        /*
         * VALIDAR APELLIDO
         */
        String apellido;
        if (this.v_jdCreUsu.jtfApellido.getText().isEmpty()) {
            this.v_jdCreUsu.jtfApellido.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu,
                    "El campo apellido esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosPersonales1);
            return null;
        } else {
            apellido = this.v_jdCreUsu.jtfApellido.getText();
        }
        /*
         *VALIDAR CI
         */
        Integer cedula;
        try {
            String LongToString = String.valueOf(this.v_jdCreUsu.jftCedulaIdentidad.getValue());
            cedula = Integer.valueOf(LongToString.replace(".", ""));
        } catch (Exception e) {
            this.v_jdCreUsu.jftCedulaIdentidad.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu, "Coloque un Numero de CI valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosPersonales1);
            return null;
        }
        /*
         * VALIDAR FECHA NACIMIENTO
         */
        Date fechaNacimiento;
        try {
            fechaNacimiento = this.v_jdCreUsu.dccFechaNacimiento.getDate();
        } catch (Exception e) {
            this.v_jdCreUsu.dccFechaNacimiento.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu,
                    "Ingrese una fecha valida en el campo Fecha nacimiento",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosPersonales1);
            return null;
        }
        /*
         * VALIDAR ALIAS
         */
        String alias;
        if (this.v_jdCreUsu.jtfAlias.getText().isEmpty()) {
            this.v_jdCreUsu.jtfAlias.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu,
                    "El campo alias esta vacio",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosEmpresariales);
            return null;
        } else {
            alias = this.v_jdCreUsu.jtfAlias.getText().toLowerCase();
        }
        /*
         *VALIDAR SALARIO
         */
        Integer salario;
        try {
            String valor = this.v_jdCreUsu.jftSalario.getText();
            if (valor.isEmpty()) {
                salario = 0;
            } else {
                salario = Integer.valueOf(this.v_jdCreUsu.jftSalario.getText());
            }
        } catch (NumberFormatException e) {
            this.v_jdCreUsu.jftSalario.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu,
                    "Coloque un numero salario valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosEmpresariales);

            return null;
        }
        /*
         * VALIDAR TELEFONO
         */
        String telefono;
        try {
            String valor = this.v_jdCreUsu.jtfNroTelefono.getText();
            if (valor.isEmpty()) {
                telefono = null;
            } else {
                telefono = this.v_jdCreUsu.jtfNroTelefono.getText();
            }
        } catch (NumberFormatException e) {
            this.v_jdCreUsu.jtfNroTelefono.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu,
                    "Coloque un numero de telefono valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosPersonales2);
            return null;
        }
        /*
         * VALIDAR CELULAR
         */
        String celular;
        try {
            String valor = this.v_jdCreUsu.jtfNroCelular.getText();
            if (valor.isEmpty()) {
                celular = null;
            } else {
                celular = this.v_jdCreUsu.jtfNroCelular.getText();
            }
        } catch (NumberFormatException e) {
            this.v_jdCreUsu.jtfNroCelular.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu,
                    "Coloque un numero de celular valido",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosPersonales2);
            return null;
        }
        /*
         * VALIDAR FECHA INGRESOO
         */
        Date fechaIngreso;
        try {
            //Inicio fechas
            if (this.v_jdCreUsu.dccFechaIngreso.getDate() == null) {
                throw new Exception(new NullPointerException());
            } else {
                fechaIngreso = this.v_jdCreUsu.dccFechaIngreso.getDate();
            }
        } catch (Exception e) {
            this.v_jdCreUsu.dccFechaIngreso.setBackground(Color.red);
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu,
                    "Ingrese una fecha valida en el campo Fecha ingreso",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosEmpresariales);
            return null;
        }
        if (this.v_jdCreUsu.jltRol.getModel().getSize() < 1) {
            javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu,
                    "Seleccione por lo menos un rol",
                    "Parametros incorrectos",
                    javax.swing.JOptionPane.OK_OPTION);
            this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpRol);
            return null;
        }
        M_funcionario funcionario = new M_funcionario();
        String email = this.v_jdCreUsu.jtfCorreoElectronico.getText();
        String direccion = this.v_jdCreUsu.jtfDireccion.getText();
        String observacion = this.v_jdCreUsu.jtaObservacion.getText();
        funcionario.setNombre(nombre);
        funcionario.setApellido(apellido);
        funcionario.setCedula(cedula);
        funcionario.setFecha_nacimiento(fechaNacimiento);
        funcionario.setAlias(alias);
        funcionario.setSalario(salario);
        funcionario.setNro_telefono(telefono);
        funcionario.setNro_celular(celular);
        funcionario.setFecha_ingreso(fechaIngreso);
        funcionario.setEstado_civil((String) this.v_jdCreUsu.jcbEstadoCivil.getSelectedItem());
        funcionario.setObservacion(observacion);
        funcionario.setDireccion(direccion);
        funcionario.setEmail(email);
        funcionario.setCiudad((String) this.v_jdCreUsu.jcbCiudad.getSelectedItem());
        funcionario.setEstado("Activo");
        funcionario.setRol(null);//se establece en el modelo
        funcionario.setSexo((String) this.v_jdCreUsu.jcbGenero.getSelectedItem());;
        funcionario.setPais((String) this.v_jdCreUsu.jcbNacionalidad.getSelectedItem());
        return funcionario;
    }

    private void checkJFTcedula() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                String valorIngresado = v_jdCreUsu.jftCedulaIdentidad.getText().replace(".", "");
                valorIngresado = valorIngresado.replace(",", "");
                Long StringToLong = null;
                try {
                    StringToLong = Long.valueOf(valorIngresado);
                } catch (NumberFormatException numberFormatException) {
                    javax.swing.JOptionPane.showMessageDialog(v_jdCreUsu, "Ingrese solo numeros",
                            "Parametros incorrectos",
                            javax.swing.JOptionPane.OK_OPTION);
                }
                v_jdCreUsu.jftCedulaIdentidad.setValue(StringToLong);
                String valorJFT = v_jdCreUsu.jftCedulaIdentidad.getText();
                v_jdCreUsu.jftCedulaIdentidad.select(valorJFT.length(), valorJFT.length());
            }
        });
    }

    private void checkJFTsalario() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                String valorIngresado = v_jdCreUsu.jftSalario.getText().replace(".", "");
                Long StringToLong = null;
                try {
                    StringToLong = Long.valueOf(valorIngresado);
                } catch (NumberFormatException numberFormatException) {
                    javax.swing.JOptionPane.showMessageDialog(v_jdCreUsu, "Ingrese solo numeros",
                            "Parametros incorrectos",
                            javax.swing.JOptionPane.OK_OPTION);
                }
                v_jdCreUsu.jftSalario.setValue(StringToLong);
                String valorJFT = v_jdCreUsu.jftSalario.getText();
                v_jdCreUsu.jftSalario.select(valorJFT.length(), valorJFT.length());
            }
        });
    }

    private void cambiarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
        fileChooser.setFileFilter(extensionFilter);
        int estado = fileChooser.showOpenDialog(this.v_jdCreUsu);
        if (estado == JFileChooser.APPROVE_OPTION) {
            modelo.fileImage = fileChooser.getSelectedFile();
            modelo.nombreImagen = modelo.fileImage.getName();
            modelo.imagen = new ImageIcon(modelo.fileImage.getPath());
            if (!modelo.isValidImage(modelo.fileImage)) {
                JOptionPane.showMessageDialog(fileChooser, "Seleccione una otra imagen de 200 x 200 pixeles o menos", "Atención", JOptionPane.INFORMATION_MESSAGE);
            } else {
                this.v_jdCreUsu.jlImagen.setIcon(modelo.imagen);
            }
        }
    }

    private void establecerImagen(File fileImage, String name) {
        try {
            String path = fileImage.getPath();
            BufferedImage src = ImageIO.read(new File(path));
            // Convert Image to BufferedImage if required.
            BufferedImage image = toBufferedImage(src);
            save(image, "png", name);  // png okay, j2se 1.4+
            save(image, "bmp", name);  // gif okay in j2se 1.6+
            // gif okay in j2se 1.6+
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void save(BufferedImage image, String ext, String name) {
        String fileName = name;
        File file = new File(fileName + "." + ext);
        try {
            ImageIO.write(image, ext, file);  // ignore returned boolean
        } catch (IOException e) {
            System.out.println("Write error for " + file.getPath()
                    + ": " + e.getMessage());
        }
    }

    private static BufferedImage toBufferedImage(Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_RGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }

    private void crearUsuario() {
        modelo.m_funcionario = isValidDataEntry();
        if (null != modelo.m_funcionario) {
            System.out.println("null !=");
            if (modelo.crearUsuario(v_jdCreUsu.jpassword1.getPassword(), v_jdCreUsu.jpassword2.getPassword(), isValidDataEntry())) {
                //JOptionPane.showMessageDialog(v_jdCreUsu, "Succes", "Attention", JOptionPane.INFORMATION_MESSAGE);
                cerrar();
            } else {
                JOptionPane.showMessageDialog(v_jdCreUsu, "Failed", "Attention", JOptionPane.INFORMATION_MESSAGE);

            }
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.v_jdCreUsu.jbCancelar) {
            cerrar();
        } else if (e.getSource() == this.v_jdCreUsu.jbAceptar) {
            //establecerImagen(this.modelo.fileImage, this.modelo.m_funcionario.getIdUsuario().toString() + "-" + this.modelo.m_funcionario.getNombre() + this.modelo.m_funcionario.getApellido());
            crearUsuario();
            System.out.println("no paso");
        } else if (e.getSource() == this.v_jdCreUsu.jbAgregarRol) {
            modelo.agregarRol(v_jdCreUsu.jcbRoles.getSelectedItem().toString());
        } else if (e.getSource() == this.v_jdCreUsu.jbQuitarRol) {
            modelo.quitarRol(v_jdCreUsu.jltRol.getSelectedIndex());
        } else if (e.getSource() == this.v_jdCreUsu.jbCambiarImagen) {
            cambiarImagen();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.v_jdCreUsu.jftCedulaIdentidad.hasFocus()) {
            this.v_jdCreUsu.jftCedulaIdentidad.setBackground(Color.white);
        } else if (this.v_jdCreUsu.jtfNombre.hasFocus()) {
            this.v_jdCreUsu.jtfNombre.setBackground(Color.white);
        } else if (this.v_jdCreUsu.jtfApellido.hasFocus()) {
            this.v_jdCreUsu.jtfApellido.setBackground(Color.white);
        } else if (this.v_jdCreUsu.jtfAlias.hasFocus()) {
            this.v_jdCreUsu.jtfAlias.setBackground(Color.white);
        } else if (this.v_jdCreUsu.dccFechaIngreso.hasFocus()) {
            this.v_jdCreUsu.dccFechaIngreso.setBackground(Color.white);
        } else if (this.v_jdCreUsu.dccFechaNacimiento.hasFocus()) {
            this.v_jdCreUsu.dccFechaNacimiento.setBackground(Color.white);
        } else if (this.v_jdCreUsu.jpassword1.hasFocus()) {
            this.v_jdCreUsu.jpassword1.setBackground(Color.white);
        } else if (this.v_jdCreUsu.jpassword2.hasFocus()) {
            this.v_jdCreUsu.jpassword2.setBackground(Color.white);
        }
    }

    public void keyTyped(KeyEvent e) {
        if (e.getSource() == this.v_jdCreUsu.jftCedulaIdentidad) {
            checkJFTcedula();
        } else if (e.getSource() == this.v_jdCreUsu.jftSalario) {
            checkJFTsalario();
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
