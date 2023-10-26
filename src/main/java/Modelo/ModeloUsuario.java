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

public class ModeloUsuario {

    /* Creamos los atributos o variables  que necesita para realizar los procesos de base de datos*/
    private int doc, sex, rol, tip;
    private String nom, dir, tel, cor, lo, cl;

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }
    private Date fec;

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

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
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

    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = lo;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public Date getFec() {
        return fec;
    }

    public void setFec(Date fec) {
        this.fec = fec;
    }

    /*Creamos el metodo para llenar los combos a través de Map o diccionario*/
    public Map<String, Integer> llenarCombo(String valor) {
        //Llamamos a la clase conexión
        Conexion conect = new Conexion();
        Connection cn = conect.iniciarConexion();//Instanciamos la conexion
        String sql = "Select * from mostrar_" + valor;

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

        String sql = "Call ins_usuario(?,?,?,?,?,?,?,?,?,?,?)";//Consulta a realizar a la base de datos
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, getDoc());
            ps.setInt(2, getTip());
            ps.setString(3, getNom());
            ps.setString(4, getTel());
            ps.setString(5, getCor());
            ps.setString(6, getDir());
            ps.setDate(7, getFec());
            ps.setInt(8, getSex());
            ps.setInt(9, getRol());
            ps.setString(10, getLo());
            ps.setString(11, getCl());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Almacenado");
            cn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            conect.cerrarConexion();
        }

    }

    public void limpiar(Component[] panel) {
        for (Object control : panel) {
            if (control instanceof JTextField) {
                ((JTextField) control).setText("");
            }
            if (control instanceof JComboBox) {
                ((JComboBox) control).setSelectedItem("Seleccione...");
            }
            if (control instanceof JDateChooser) {
                ((JDateChooser) control).setDate(null);
            }
        }

    }

    public void mostrarTablaUsuario(JTable tabla, String valor) {
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

        String[] titulo = {"Tipo de Documento", "Documento", "Nombre", "Dirección", "Celular", "Género", "Correo", "Fecha de Nacimiento", "Rol", "", ""};

        DefaultTableModel tablaUsuario = new DefaultTableModel(null, titulo){
            public boolean  isCellEditable(int row,int column){
                return false;
            }
        };

        String sqlUsuario;
        if (valor.equals("")) {
            sqlUsuario = " SELECT * FROM mostrar_usuario ";
        } else {
            sqlUsuario = "call cons_usuario('" + valor + "')";
        }
        try {
            String[] dato = new String[titulo.length];
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlUsuario);
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
    public void buscarUsuario(int valor){
        Conexion cone= new Conexion();
        Connection cn = cone.iniciarConexion();
        String sql="call bus_usuario("+valor+")";
        try {
            Statement st = cn.createStatement();
            ResultSet rs= st.executeQuery(sql);
            
            while(rs.next()){
                setDoc(rs.getInt(1));
                setTip(rs.getInt(2));
                setNom(rs.getString(3));
                setTel(rs.getString(4));
                setCor(rs.getString(5));
                setDir(rs.getString(6));
                setFec(rs.getDate(7));
                setSex(rs.getInt(8));
                setRol(rs.getInt(9));
                setLo(rs.getString(10));
                setCl(rs.getString(11));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String obtenerSeleccion(Map<String,Integer>dato,int valor){
        for(Map.Entry<String,Integer> seleccion:dato.entrySet()){
            if(seleccion.getValue()==valor){
                return seleccion.getKey();
            }
        }
        return null;        
    }

}
