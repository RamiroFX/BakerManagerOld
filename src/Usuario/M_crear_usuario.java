/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import DB_manager.DB_Funcionario;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import Entities.M_funcionario;
import Entities.M_rol;

/**
 *
 * @author Administrador
 */
public class M_crear_usuario {

    M_funcionario m_funcionario;
    DefaultListModel lmRol;
    String password;
    String nombreImagen;
    ImageIcon imagen;
    File fileImage;
    ArrayList roles;

    public M_crear_usuario() {
        m_funcionario = new M_funcionario();
        lmRol = new DefaultListModel();
        password = "";
        nombreImagen = "";
        imagen = new ImageIcon();
        fileImage = null;
        roles = new ArrayList();
    }

    public void agregarRol(String rol) {
        String rolActual = rol;
        String rolLista[] = new String[lmRol.getSize()];
        for (int i = 0; i < rolLista.length; i++) {
            rolLista[i] = lmRol.getElementAt(i).toString();
        }
        boolean existe = false;
        for (int i = 0; i < rolLista.length; i++) {
            if (rolLista[i].equals(rolActual)) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            lmRol.addElement(rolActual);
        }
    }

    public void quitarRol(int selectedIndex) {
        if (selectedIndex < 0 || selectedIndex >= lmRol.size()) {
            lmRol.remove(selectedIndex);
        }
    }

    public boolean establecerRoles() {
        if (lmRol.isEmpty()) {
            /*JOptionPane.showMessageDialog(v_jdCreUsu, "Escoja un rol", "Parametros incompletos", JOptionPane.ERROR_MESSAGE, null);
             this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpRol);*/
            return false;
        }
        String nombreRol = "";
        roles.clear();
        for (int i = 0; i < lmRol.size(); i++) {
            nombreRol = (String) lmRol.getElementAt(i);
            M_rol rol = DB_Funcionario.obtenerRol(nombreRol);
            roles.add(rol);
        }
        return true;
    }

    public boolean isPasswordCorrect(char[] charPasswordTemp, char[] charPasswordTemp2) {
        /*char[] charPasswordTemp = this.v_jdCreUsu.jpassword1.getPassword();
         char[] charpasswordTemp2 = this.v_jdCreUsu.jpassword2.getPassword();*/
        if (charPasswordTemp.length == 0 || charPasswordTemp2.length == 0) {
            /*this.v_jdCreUsu.jpassword1.setBackground(Color.red);
             this.v_jdCreUsu.jpassword2.setBackground(Color.red);*/
            /*javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu, "Password vacios",
             "Parametros incorrectos",
             javax.swing.JOptionPane.OK_OPTION);
             this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosEmpresariales);*/
            return false;
        }
        if (Arrays.equals(charPasswordTemp, charPasswordTemp2)) {
            password = String.copyValueOf(charPasswordTemp);
            return true;
        } else {
            /*this.v_jdCreUsu.jpassword1.setBackground(Color.red);
             this.v_jdCreUsu.jpassword2.setBackground(Color.red);
             javax.swing.JOptionPane.showMessageDialog(this.v_jdCreUsu, "Password incorrectos",
             "Parametros incorrectos",
             javax.swing.JOptionPane.OK_OPTION);
             this.v_jdCreUsu.jtpCenter.setSelectedComponent(v_jdCreUsu.jpDatosEmpresariales);*/
            return false;
        }
    }

    public boolean crearUsuario(char[] charPasswordTemp, char[] charPasswordTemp2, M_funcionario m_funcionario) {
        if (isPasswordCorrect(charPasswordTemp, charPasswordTemp2) && establecerRoles()) {
            // try {
            DB_Funcionario.insertarFuncionarioFX(m_funcionario, roles, password);
            //DBmanagerFuncionario.actualizarImagen(Utils.ImageToByte(fileImage), m_funcionario.getIdUsuario(), nombreImagen);
            //this.c_jifGesUsu.v_jifGesUsu.jtUsuario.setModel(DBmanagerFuncionario.consultarFuncionario(""));
            return true;
            // } catch (FileNotFoundException ex) {
            //  Logger.getLogger(C_CrearUsuario.class.getName()).log(Level.SEVERE, null, ex);
            // }
        }
        return false;
    }

    public boolean isValidImage(File fileImage) {
        this.fileImage = fileImage;
        this.nombreImagen = fileImage.getName();
        this.imagen = new ImageIcon(fileImage.getPath());
        if (imagen.getIconHeight() > 200 && imagen.getIconWidth() > 200) {
            try {
                imagen.setImage(createResizedCopy(this.imagen.getImage(), 200, 200, false));
                return true;
            } catch (Exception e) {
                this.fileImage = null;
                this.nombreImagen = null;
                this.imagen = null;
                return false;
            }
        } else {
            return true;
        }

    }

    BufferedImage createResizedCopy(Image originalImage,
            int scaledWidth, int scaledHeight,
            boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }
}
