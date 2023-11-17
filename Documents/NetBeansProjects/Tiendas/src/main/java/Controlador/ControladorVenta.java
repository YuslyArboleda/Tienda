
package Controlador;

import Modelo.ModeloUsuario;
import Vista.BuscarUsuario;
import Vista.Principal;
import Controlador.ControladorFactura;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author HP
 */
public class ControladorVenta implements ActionListener,DocumentListener{
    BuscarUsuario busUsu = new BuscarUsuario();
    Principal prin = new Principal();
    ModeloUsuario modUsu = new ModeloUsuario();
    ControladorFactura contFact = new ControladorFactura();
    

    public ControladorVenta() {
        prin.getBtnVen_Usu().addActionListener(this);
        busUsu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        busUsu.getTxtBuscar().getDocument().addDocumentListener(this);
    }
      public void buscar(String valor,String usuario,String cliente) {
        if (valor.equals("usuario")) {
            modUsu.mostrarTablaUsuario(busUsu.getJtUsuario(), "", "compra");
            busUsu.setVisible(true);
            contFact.gestion_Usuario(cliente,5);
        }
        if (valor.equals("cliente")) {

        }
     
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
    
}
