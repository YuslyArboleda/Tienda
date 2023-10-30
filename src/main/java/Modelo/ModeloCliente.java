/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Controlador.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 *
 * @author HP
 */
public class ModeloCliente {
        public void mostrarTablaCliente(JTable tabla, String valor, String nomPesta) {
        Conexion conect = new Conexion();
        Connection cn = conect.iniciarConexion();

        //Personalizar Encabezado
        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setDefaultRenderer(new GestionEncabezado());
        tabla.setTableHeader(encabezado);

        tabla.setDefaultRenderer(Object.class, new GestionCeldas());

        JButton editar = new JButton();
        JButton eliminar = new JButton();
        JButton agregar = new JButton();

        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar.png")));
        eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png")));
        agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/agregar_archivo.png")));
        
        String[] titulo = {"Tipo de Documento", "Documento", "Nombre", "Dirección", "Celular", "Género", "Correo", "Fecha de Nacimiento"};
        int total = titulo.length;
        
        if(nomPesta.equals("cliente")){
            
            titulo= Arrays.copyOf(titulo,titulo.length+2);
            titulo[titulo.length-2]="";
            titulo[titulo.length-1]="";
            
        }else{
            titulo= Arrays.copyOf(titulo,titulo.length+1);
            titulo[titulo.length-1]="";
        }

        DefaultTableModel tablaCliente = new DefaultTableModel(null, titulo) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sqlCliente=valor.isEmpty()?"SELECT * FROM mostrar_cliente ":"call cliente_cons('" + valor + "')";
   
        try {
            String[] dato = new String[titulo.length];
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlCliente);
            while (rs.next()) {
                for (int i = 0; i < total; i++) {
                    dato[i] = rs.getString(i + 1);
                }
                Object[] fila={dato[0], dato[1], dato[2], dato[6], dato[3], dato[4], dato[5], dato[7]};
                if(nomPesta.equals("cliente")){
                    fila= Arrays.copyOf(fila, fila.length+2);
                    fila[fila.length-2]=editar;
                    fila[fila.length-1]=eliminar;
                }else{
                    fila= Arrays.copyOf(fila, fila.length+1);
                    fila[fila.length-1]=agregar;
                }
                tablaCliente.addRow(fila);
            }
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tabla.setModel(tablaCliente);
        //Darle tamaño a cada columna
        int numColumnas = tabla.getColumnCount();
        int[] tamanos = {150, 80, 150, 150, 100, 100, 200, 150, 130, 30, 30};
        for (int i = 0; i < numColumnas; i++) {
            TableColumn columna = tabla.getColumnModel().getColumn(i);
            columna.setPreferredWidth(tamanos[i]);

        }
        conect.cerrarConexion();

    }
    
}
