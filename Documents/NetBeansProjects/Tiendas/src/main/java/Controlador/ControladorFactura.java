/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ModeloProveedor;
import Modelo.ModeloUsuario;
import Vista.Buscar;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class ControladorFactura implements ActionListener {

    Buscar bus = new Buscar();
    ModeloUsuario modUsu = new ModeloUsuario();
    ModeloProveedor modPro = new ModeloProveedor();

    public ControladorFactura() {
        bus.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bus.getTxtBuscar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bus.getTxtBuscar().setText("");
                bus.getTxtBuscar().setForeground(Color.BLACK);
            }
        });

    }

    public void buscar(String buscar, String usua, String pro,String nomUsu,String nomPro) {

        bus.getLblbuscar().setText(buscar);
        System.out.println(usua);
        if (buscar.equals("Usuario")) {
            modUsu.mostrarTablaUsuario(bus.getJtBuscar(), "", "factura");
        } else {
            modPro.mostrarTablaProveedor(bus.getJtBuscar(), "", "factura");
        }
        bus.setLocationRelativeTo(null);
        bus.setVisible(true);
        bus.getJtBuscar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = bus.getJtBuscar().rowAtPoint(e.getPoint());
                int columna = bus.getJtBuscar().columnAtPoint(e.getPoint());
                int docum = Integer.parseInt(bus.getJtBuscar().getValueAt(fila, 1).toString());
                String nom = bus.getJtBuscar().getValueAt(fila, 2).toString();

                if (columna == 9) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas agregar al " + buscar + "?\n"
                            + docum + " " + nom, "Aceptar", JOptionPane.YES_OPTION);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        ControladorPrincipal contPrin = new ControladorPrincipal();
                        if (buscar.equals("Usuario")) {
                            contPrin.CompraVenta(4, String.valueOf(docum), pro,nom,nomPro);
                        }else{
                            contPrin.CompraVenta(4, usua, String.valueOf(docum),nomUsu,nom);
                        }
                        bus.dispose();
                    }

                }

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if(e.getSource().equals("po"))
    }

}
