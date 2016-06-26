/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import DB_manager.DB_Funcionario;
import Utilities.c_packColumn;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import Entities.M_funcionario;
import Interface.Gestion;
import Main.C_inicio;
import java.awt.event.KeyEvent;

/**
 *
 * @author Ramiro Ferreira
 */
public class C_gestion_usuario implements Gestion {

    public C_inicio c_main;
    private M_funcionario m_funcionario;
    private ImageIcon foto;
    int idFuncionario;
    V_gestion_usuario vista;
    String imagePath;

    public C_gestion_usuario(C_inicio c_main) {
        this.c_main = c_main;
        this.m_funcionario = new M_funcionario();
        this.vista = new V_gestion_usuario();
        inicializarVista();
        agregarListeners();
    }

    /**
     * @return the m_funcionario
     */
    public M_funcionario getFuncionario() {
        return m_funcionario;
    }

    /**
     * @param m_funcionario the m_funcionario to set
     */
    public void setFuncionario(M_funcionario m_funcionario) {
        this.m_funcionario = m_funcionario;
    }

    @Override
    public final void inicializarVista() {
        this.vista.jtUsuario.setModel(DB_Funcionario.consultarFuncionario("", false, true, true));
        c_packColumn.packColumns(this.vista.jtUsuario, 2);
        this.vista.jbBuscarUsuario.setEnabled(false);
        this.vista.jbActualizarUsuario.setEnabled(false);
        this.vista.jbImagenUsuario.setEnabled(false);
        this.vista.jftCedulaIdentidad.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                new java.text.DecimalFormat("#,##0"))));
        this.vista.jftSalario.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.NumberFormatter(
                new java.text.DecimalFormat("#,##0"))));
    }

    /**
     * Establece el tamaño, posicion y visibilidad de la vista.
     */
    public void mostrarVista() {
        this.vista.setSize(this.c_main.establecerTamañoPanel());
        this.vista.setLocation(this.c_main.centrarPantalla(this.vista));
        this.c_main.agregarVentana(this.vista);
    }

    /**
     * Elimina la vista.
     */
    @Override
    public final void cerrar() {
        this.vista.dispose();
        System.runFinalization();
    }

    /**
     * Agrega ActionListeners los controles.
     */
    @Override
    public final void agregarListeners() {
        //this.v_jifGesUsu.jbBorrar.addActionListener(this);
        //this.v_jifGesUsu.jtfBuscar.addActionListener(this);
        this.vista.jbCrearUsuario.addActionListener(this);
        this.vista.jbActualizarUsuario.addActionListener(this);
        this.vista.jbImagenUsuario.addActionListener(this);
        this.vista.jtUsuario.addMouseListener(this);
        this.vista.jtfBuscar.addKeyListener(this);
        this.vista.jckbCedula.addActionListener(this);
        this.vista.jckbNombreApellido.addActionListener(this);
        this.vista.jrbExclusivo.addActionListener(this);
        this.vista.jrbInclusivo.addActionListener(this);
    }

    public void displayQueryResults() {
        /*
         * Para permitir que los mensajes puedan ser desplegados, no se ejecuta
         * el query directamente, sino que se lo coloca en una cola de eventos
         * para que se ejecute luego de los eventos pendientes.
         */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*
                 * Se utiliza el objeto factory para obtener un TableModel
                 * para los resultados del query.
                 */
                String busqueda = vista.jtfBuscar.getText().toLowerCase();
                boolean isExclusivo = false;
                boolean entidad = false;
                boolean ruc = false;
                if (vista.jrbExclusivo.isSelected()) {
                    isExclusivo = true;
                }
                if (vista.jckbNombreApellido.isSelected()) {
                    entidad = true;
                }
                if (vista.jckbCedula.isSelected()) {
                    ruc = true;
                }
                vista.jtUsuario.setModel(DB_Funcionario.consultarFuncionario(busqueda, isExclusivo, entidad, ruc));
                c_packColumn.packColumns(vista.jtUsuario, 2);
                vista.jbActualizarUsuario.setEnabled(false);
            }
        });
    }

    /*public void itemStateChanged(ItemEvent e) {
     if(e.getItem().equals(this.v_jifGesUsu.jrbBuscar[0])){
     System.out.println("jrbBuscar[0])");
     }else if(e.getItem().equals(this.v_jifGesUsu.jrbBuscar[1])){
     System.out.println("jrbBuscar[1])");
     }else if(e.getItem().equals(this.v_jifGesUsu.jrbBuscar[2])){
     System.out.println("jrbBuscar[2])");
     }
     }*/
    public void actionPerformed(ActionEvent e) {
        /*if(e.getSource()==this.v_jifGesUsu.jtfBuscar){
         //producto= c_DBmanager.mostrarProducto(jtfBuscar.getText());
         displayQueryResults(this.v_jifGesUsu.jtfBuscar.getText());
         }
         if(e.getSource()==this.v_jifGesUsu.jbBuscar){
         //producto= c_DBmanager.mostrarProducto(jtfBuscar.getText());
         displayQueryResults(this.v_jifGesUsu.jtfBuscar.getText());
         }
         else if(e.getSource()==this.v_jifGesUsu.jbBorrar){
         this.v_jifGesUsu.jtfBuscar.setText("");
         this.v_jifGesUsu.jtfBuscar.requestFocusInWindow();
         }*/
        if (e.getSource() == this.vista.jbCrearUsuario) {
            C_crear_usuario crearUsuario = new C_crear_usuario(this);
            crearUsuario.mostrarVista();
            this.vista.jtUsuario.setModel(DB_Funcionario.consultarFuncionario("", false, true, true));
        } else if (e.getSource() == this.vista.jbActualizarUsuario) {
            C_modificar_usuario actualizarUsuario = new C_modificar_usuario(this, m_funcionario);
            actualizarUsuario.mostrarVista();
        } else if (e.getSource() == this.vista.jbImagenUsuario) {
            //cambiarImagen();
        } else if (e.getSource() == this.vista.jrbExclusivo) {
            displayQueryResults();
        } else if (e.getSource() == this.vista.jrbInclusivo) {
            displayQueryResults();
        } else if (e.getSource() == this.vista.jckbCedula) {
            displayQueryResults();
        } else if (e.getSource() == this.vista.jckbNombreApellido) {
            displayQueryResults();
        }
    }
    /*
     private void cambiarImagen() {
     JFileChooser selector = new JFileChooser();
     FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
     selector.setFileFilter(filter);
     int estado = selector.showOpenDialog(this.vista);
     if (estado == JFileChooser.APPROVE_OPTION) {
     File archivoelegido = selector.getSelectedFile();
     imagePath = archivoelegido.getPath();
     ImageIcon imagen = new ImageIcon(imagePath);
     if (imagen.getIconHeight() > 200 && imagen.getIconWidth() > 200) {
     JOptionPane.showMessageDialog(selector, "Seleccione una imagen de 200 x 200 pixeles", "Atencion", JOptionPane.INFORMATION_MESSAGE);
     } else {
     try {
     byte[] byteImage = Utilities.ImageToByte(archivoelegido);
     DB_Funcionario.actualizarImagen(byteImage, idFuncionario, archivoelegido.getName());
     this.vista.jlFoto.setIcon(imagen);
     } catch (FileNotFoundException ex) {
     Logger.getLogger(C_gestion_usuario.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     }
     }*/

    @Override
    public void mouseClicked(MouseEvent e) {
        int fila = this.vista.jtUsuario.rowAtPoint(e.getPoint());
        int columna = this.vista.jtUsuario.columnAtPoint(e.getPoint());
        idFuncionario = (Integer.valueOf((String) this.vista.jtUsuario.getValueAt(fila, 0)));
        setFuncionario(DB_Funcionario.obtenerDatosFuncionarioID(idFuncionario));
        if ((fila > -1) && (columna > -1)) {
            this.vista.jbActualizarUsuario.setEnabled(true);
            this.vista.jbImagenUsuario.setEnabled(true);
            //this.v_jifGesUsu.jbEliminar.setEnabled(true);
            this.vista.jtfAlias.setText(getFuncionario().getAlias());
            this.vista.jtfDireccion.setText(getFuncionario().getDireccion());
            this.vista.jtfCorreoElectronico.setText(getFuncionario().getEmail());
            this.vista.jtfEstado.setText(getFuncionario().getEstado());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                this.vista.jtfFechaIngreso.setText(sdf.format(getFuncionario().getFecha_ingreso()));
            } catch (java.lang.NullPointerException ex) {
                this.vista.jtfFechaIngreso.setText("");
            }
            try {
                this.vista.jtfFechaNacimiento.setText(sdf.format(getFuncionario().getFecha_nacimiento()));
            } catch (java.lang.NullPointerException ex) {
                this.vista.jtfFechaNacimiento.setText("");
            }
            try {
                this.vista.jtfFechaSalida.setText(sdf.format(getFuncionario().getFecha_salida()));
            } catch (java.lang.NullPointerException ex) {
                this.vista.jtfFechaSalida.setText("");
            }
            this.vista.jtaObservacion.setText(getFuncionario().getObservacion());
            this.vista.jftSalario.setValue(getFuncionario().getSalario());
            this.vista.jtfNroCelular.setText(getFuncionario().getNro_celular());
            this.vista.jtfNroTelefono.setText(getFuncionario().getNro_telefono());
            //Datos personales
            //this.v_jifGesUsu.jtfIdPersona.setText(String.valueOf(getFuncionario().getIdPersona()));
            this.vista.jtfNombre.setText(getFuncionario().getNombre());
            this.vista.jtfApellido.setText(getFuncionario().getApellido());
            this.vista.jftCedulaIdentidad.setValue(getFuncionario().getCedula());
            this.vista.jtfGenero.setText(getFuncionario().getSexo());
            this.vista.jtfEstadoCivil.setText(getFuncionario().getEstado_civil());
            this.vista.jtfNacionalidad.setText(getFuncionario().getPais());
            this.vista.jtfCiudad.setText(getFuncionario().getCiudad());
            this.vista.jcbRol.removeAllItems();
            Vector vRolUsuario = DB_Funcionario.obtenerRolFuncionario(getFuncionario());
            for (int i = 0; i < vRolUsuario.size(); i++) {
                this.vista.jcbRol.addItem(vRolUsuario.get(i));
            }
            //this.vista.jlFoto.setIcon((Icon) DB_Funcionario.obtenerImagenFuncionario(idFuncionario));
        } else {
            this.vista.jbActualizarUsuario.setEnabled(false);
            this.vista.jbImagenUsuario.setEnabled(false);
        }
        if (e.getClickCount() == 2) {
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        displayQueryResults();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
