/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ModeloProveedor;
import Modelo.ModeloUsuario;
import Vista.BuscarProveedor;
import Vista.BuscarUsuario;
import Vista.Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author HP
 */
public class ControladorFactura implements ActionListener, DocumentListener {

    BuscarProveedor busProv = new BuscarProveedor();
    BuscarUsuario busUsu = new BuscarUsuario();
    Principal prin = new Principal();
    ModeloUsuario modUsu = new ModeloUsuario();
    ModeloProveedor modProv = new ModeloProveedor();

    public ControladorFactura() {
        prin.getBtnBuscarUsu().addActionListener(this);
        prin.getBtnFactProv().addActionListener(this);
        busUsu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        busProv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        busProv.getTxtBuscar().getDocument().addDocumentListener(this);
        busUsu.getTxtBuscar().getDocument().addDocumentListener(this);
        
    }

    public void buscar(String valor, String usuario, String proveedor) {
        if (valor.equals("usuario")) {
            modUsu.mostrarTablaUsuario(busUsu.getJtUsuario(), "", "compra");
            busUsu.setVisible(true);
            gestion_Usuario(proveedor, 4);
        }
        if (valor.equals("proveedor")) {

        }

    }
    

    public void gestion_Usuario(String dato, int pes) {
      
        busUsu.getJtUsuario().addMouseListener(new MouseAdapter() {
            @Override
             
            public void mouseClicked(MouseEvent e) { System.out.println(pes);
                int fila = busUsu.getJtUsuario().rowAtPoint(e.getPoint());
                int columna = busUsu.getJtUsuario().columnAtPoint(e.getPoint());
                modUsu.setDoc(Integer.parseInt(busUsu.getJtUsuario().getValueAt(fila, 1).toString()));
                modUsu.setNom(busUsu.getJtUsuario().getValueAt(fila, 2).toString());
                System.out.println(columna);
                if (columna == 9) {

                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea agregar al usuario? \n"
                            + modUsu.getDoc() + " " + modUsu.getNom(), "Aceptar", JOptionPane.YES_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        busUsu.setVisible(false);
                        ControladorPrincipal contPrin = new ControladorPrincipal();
//                        contPrin.CompraVenta(pes,  String.valueOf(modUsu.getDoc()), dato);

//                        busUsu.dispose();
                    }
                }

            }
        });
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
