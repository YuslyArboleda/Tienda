package Controlador;

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

public class ControladorPrincipal implements ActionListener, ChangeListener {

    Principal prin = new Principal();//Instancia(Llama) la ventana principal
//    Nuevo_Usuario nuevo = new Nuevo_Usuario();//Instanca (Llama) la ventana(vista) Nuevo usuario
    ControladorUsuario contUsua= new ControladorUsuario();

    
    public ControladorPrincipal() {
        prin.getBtnNuevo().addActionListener(this);//Agrega el boton nuevo para que se escuche cuando se de clic
        prin.getJtPrincipal().addChangeListener(this);
    }

    public void iniciar() {
        prin.setLocationRelativeTo(null);//Centra la ventana
        prin.setTitle("Principal");//Le da titulo a la ventana
        prin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        prin.setVisible(true);//Hace visible la ventana
        gestionPestanas();
    }
    
    public void gestionPestanas(){
        if(prin.getJtPrincipal().getSelectedIndex()==0){
            
        }
        
//        prin.getJtPrincipal().addChangeListener(new ChangeListener(){
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//        
//        });
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
        int seleccion= prin.getJtPrincipal().getSelectedIndex();
        if(seleccion==1){
            ModeloUsuario modUsu = new ModeloUsuario();//Instancia el modelo de 
            modUsu.mostrarTablaUsuario(prin.getTbUsuario(), "");
            
            prin.getTxtBuscar().addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    prin.getTxtBuscar().setText("");
                    prin.getTxtBuscar().setForeground(Color.BLACK);
                }
            
            });
        }
    }

}
