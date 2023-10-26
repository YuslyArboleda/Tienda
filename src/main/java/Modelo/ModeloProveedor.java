package Modelo;

import Controlador.Conexion;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class ModeloProveedor {

    /* Creamos los atributos o variables  que necesita para realizar los procesos de base de datos*/
    private int doc, sex;
    private String nit,pers,nom, dir, tel, cor;
    private Date fec;

    public ModeloProveedor() {
    }
    
    

    /*Creamos los getter y setter*/

    public int getDoc() {
        return doc;
    }

    public void setDoc(int doc) {
        this.doc = doc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getPers() {
        return pers;
    }

    public void setPers(String pers) {
        this.pers = pers;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Date getFec() {
        return fec;
    }

    public void setFec(Date fec) {
        this.fec = fec;
    }
   

    /*Creamos el metodo para llenar los combos a través de Map o diccionario*/
    public Map<String, Integer> llenarCombo() {
        //Llamamos a la clase conexión
        Conexion conect = new Conexion();
        Connection cn = conect.iniciarConexion();//Instanciamos la conexion
        String sql = "Select * from mostrar_sexo" ;

        Map<String, Integer> llenar_combo = new HashMap<>();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                llenar_combo.put(rs.getString(2), rs.getInt(1));
            }
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return llenar_combo;
    }

    public void insertarUsuario() {
        //Llamamos a la clase conexión
        Conexion conect = new Conexion();
        Connection cn = conect.iniciarConexion();//Instanciamos la conexion
        
        String sql="Call ins_usuario(?,?,?,?,?,?,?,?,?,?)";//Consulta a realizar a la base de datos
        try {
         PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, getDoc());
            ps.setString(2, getNit());
            ps.setString(3, getNom());
            ps.setString(4, getPers());
            ps.setDate(5, getFec());
            ps.setInt(5, getSex());
            ps.setString(7, getTel());
            ps.setString(8, getCor());
            ps.setString(9, getDir());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Almacenado");
            cn.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar", "Error", JOptionPane.ERROR_MESSAGE);
        }
        conect.cerrarConexion();
    }
    public void limpiar(Component[] panel){
        for(Object control: panel){
            if(control instanceof JTextField){
                ((JTextField)control).setText("");
            }
            if(control instanceof JComboBox){
                ((JComboBox)control).setSelectedItem("Seleccione...");
            }
            if(control instanceof JDateChooser){
                ((JDateChooser)control).setDate(null);
            }
        }
        
    }
        public void mostrarTablaProveedor(JTable tabla, String valor) {
        Conexion conect = new Conexion();
        Connection cn = conect.iniciarConexion();
       
        //Personalizar Encabezado
        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setDefaultRenderer(new GestionEncabezado());
        tabla.setTableHeader(encabezado);
        
        tabla.setDefaultRenderer(Object.class, new GestionCeldas());
        

        JButton editar = new JButton();
        JButton eliminar = new JButton();

        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar.png")));
       eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png")));

        String[] titulo = {"Tipo de Documento", "Documento", "Nombre" , "Tipo Persona", "Dirección", "Celular", "Género", "Correo", "Fecha de Nacimiento", "", ""};

        DefaultTableModel tablaUsuario = new DefaultTableModel(null, titulo){
            public boolean  isCellEditable(int row,int column){
                return false;
            }
        };

        String sqlProveedor;
        if (valor.equals("")) {
            sqlProveedor = " SELECT * FROM mostrar_proveedor ";
        } else {
            sqlProveedor = "call cons_proveedor('" + valor + "')";
        }
        try {
            String[] dato = new String[titulo.length];
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlProveedor);
            while (rs.next()) {
                for (int i = 0; i < titulo.length - 2; i++) {
                    dato[i] = rs.getString(i + 1);
                }
                tablaUsuario.addRow(new Object[]{dato[0],dato[1],dato[2],dato[6],dato[3],dato[4],dato[5],dato[7],dato[8],editar,eliminar});
            }
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tabla.setModel(tablaUsuario);
        //Darle tamaño a cada columna
        int numColumnas = tabla.getColumnCount();
        int[] tamanos={150,80,150,150,100,100,200,150,130,30,30};
        for( int i= 0;i<numColumnas;i++){
            TableColumn columna= tabla.getColumnModel().getColumn(i);
            columna.setPreferredWidth(tamanos[i]);
            
        }
        conect.cerrarConexion();

    }

}
