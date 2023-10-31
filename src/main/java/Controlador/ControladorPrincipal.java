package Controlador;

import Modelo.ModeloProveedor;
import Modelo.ModeloUsuario;
import Vista.Principal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ControladorPrincipal implements ActionListener, ChangeListener,DocumentListener {

    Principal prin = new Principal();//Instancia(Llama) la ventana principal
//    Nuevo_Usuario nuevo = new Nuevo_Usuario();//Instanca (Llama) la ventana(vista) Nuevo usuario
    ControladorUsuario contUsua = new ControladorUsuario();
    ModeloUsuario modUsu = new ModeloUsuario();//Instancia el modelo de 

    public ControladorPrincipal() {
        prin.getBtnNuevo().addActionListener(this);//Agrega el boton nuevo para que se escuche cuando se de clic
        prin.getJtPrincipal().addChangeListener(this);
    }

    public void iniciar(int valor) {
        prin.setLocationRelativeTo(null);//Centra la ventana
        prin.setTitle("Principal");//Le da titulo a la ventana
        prin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        prin.getJtPrincipal().setSelectedIndex(valor);
        prin.setVisible(true);//Hace visible la ventana
        gestionPestanas();

    }

    public void gestionPestanas() {
//        if (prin.getJtPrincipal().getSelectedIndex() == 0) {
//
//        }
    }
    public void gestionUsuario(){
        
            modUsu.mostrarTablaUsuario(prin.getTbUsuario(), "","usuario");

            prin.getTxtBuscar().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    prin.getTxtBuscar().setText("");
                    prin.getTxtBuscar().setForeground(Color.BLACK);
                }

            });
         
            prin.getTbUsuario().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int fila = prin.getTbUsuario().rowAtPoint(e.getPoint());
                    int columna = prin.getTbUsuario().columnAtPoint(e.getPoint());
                    modUsu.setDoc(Integer.parseInt(prin.getTbUsuario().getValueAt(fila, 1).toString()));

                    if (columna == 9) {
                        prin.setVisible(false);
                        contUsua.actualizarUsuario(modUsu.getDoc());
                    }
                    if(columna ==10){
                        contUsua.eliminarUsuario(modUsu.getDoc());
                        modUsu.mostrarTablaUsuario(prin.getTbUsuario(), "", "usuario");
                        
                    }
                }

            });
        
    }
    void gestionCliente(){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) { //Valida los eventos
        if (e.getSource().equals(prin.getBtnNuevo())) {//Se crea al acción cuando le damos clic en el boton nuevo de la vista princial

            prin.setVisible(false);
            contUsua.control();
        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int seleccion = prin.getJtPrincipal().getSelectedIndex();
        if (seleccion == 0) {
            gestionPestanas();
        }
        if (seleccion == 1) {
            gestionUsuario();
        }
        if (seleccion == 3) {
            ModeloProveedor modPro = new ModeloProveedor();
            modPro.mostrarTablaProveedor(prin.getJtProveedor(), "");
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        modUsu.mostrarTablaUsuario(prin.getTbUsuario(), prin.getTxtBuscar().getText(),"usuario");
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        modUsu.mostrarTablaUsuario(prin.getTbUsuario(), prin.getTxtBuscar().getText(),"usuario");
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        modUsu.mostrarTablaUsuario(prin.getTbUsuario(), prin.getTxtBuscar().getText(),"usuario");
    }

}
