/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package universidadejemplo;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author pablo
 */
public class UniversidadEjemplo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            
        try{
        // Establezco el driver
            Class.forName("org.mariadb.jdbc.Driver");
            String bd = "jdbc:mysql://localhost/universidadejemplo";
            String usuario = "root";
            String password = "";

            Connection con = DriverManager.getConnection(bd,usuario,password);
            PreparedStatement ps;
            
            // Llamo a los metodos insertar y le envio los datos 
            
            insertarAlumnoDB("111","Callegaro","Martina","2003-04-18","1",con);
            insertarAlumnoDB("222","Lucero","Joaquin","2003-05-25","1",con);
            insertarAlumnoDB("333","Maidana","Pablo","2002-07-14","1",con);

            insertarMateriaDB("Estrucutra de datos y algoritmos","2024","1",con);
            insertarMateriaDB("Laboratorio","1","1",con);
            insertarMateriaDB("Web","2","1",con);
            insertarMateriaDB("Ingles","1","1",con);

            inscribirAlumnoMateriaDB("8","1","2",con);
            inscribirAlumnoMateriaDB("7","1","1",con);
            inscribirAlumnoMateriaDB("9","2","4",con);
            inscribirAlumnoMateriaDB("8","2","3",con);
            inscribirAlumnoMateriaDB("9","3","3",con);
            inscribirAlumnoMateriaDB("6","3","2",con);
            
            // Preparo la consulta 
            ps = con.prepareStatement("SELECT alumno.* FROM `alumno` JOIN inscripcion ON inscripcion.idAlumno = alumno.idAlumno WHERE inscripcion.nota > 8");
            
            // Ejecuto la consulta
            ResultSet datos = ps.executeQuery();
            
            // Imprimo los datos mientras haya una siguiente fila
            while (datos.next()) {
                System.out.println("Alumno ID: " + datos.getString("idAlumno"));
                System.out.println("Apellido: " + datos.getString("apellido"));
                System.out.println("Nombre: " + datos.getString("nombre"));
                System.out.println("DNI: " + datos.getString("dni"));
                System.out.println("FechaNac: " + datos.getString("fechaNac"));
                System.out.println("");
            }
            
            // Elimino una inscripcion de un alumno en una materia
            ps = con.prepareStatement("DELETE FROM `inscripcion` WHERE idInscripcion = 1");
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Se ha eliminado un alumno");
            }
            
        }catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar el driver");
        } catch (SQLException ex) {
            int codigoE=ex.getErrorCode();
            if(codigoE==1062){
                  JOptionPane.showMessageDialog(null, "Entrada Duplicada");
            } else if(codigoE==1049){
            JOptionPane.showMessageDialog(null, "BD Desconocida");
            }else{
                  JOptionPane.showMessageDialog(null, "Error ");
            }
            
            ex.printStackTrace();
            System.out.println("codigo de error "+ex.getErrorCode());
        }
    }
    
    public static void insertarAlumnoDB(String dni,String apellido,String nombre,String fecha,String estado,Connection con) throws SQLException{
        // Indico que el metodo puede tirar una excepcion SQLException y que la maneje el main
        // Establezco como va a ser la consulta con los datos recibidos por parametro
        String sql = "INSERT INTO "
                   + "alumno(dni, apellido, nombre, fechaNac, estado) "
                   + "VALUES ("+ dni +",'"+apellido+"','"+nombre+"','"+fecha+"',"+estado+")"; 
        
        // Preparo la consulta
        PreparedStatement ps = con.prepareStatement(sql);
        
        // La ejecuto con executeUpdate por que es un INSERT
        int filas = ps.executeUpdate();
        
        if (filas > 0) {
            System.out.println("Se ha agregado el alumno: " + nombre + " " + apellido);
        }
    }
    
    public static void insertarMateriaDB(String nombre,String anio,String estado,Connection con) throws SQLException{
        // Indico que el metodo puede tirar una excepcion SQLException y que la maneje el main
        // Establezco como va a ser la consulta con los datos recibidos por parametro
        String sql = "INSERT INTO "
                   + "materia(nombre, año, estado) "
                   + "VALUES ('"+nombre+"',"+anio+","+estado+")"; 
        
        // Preparo la consulta
        PreparedStatement ps = con.prepareStatement(sql);
        
        // La ejecuto con executeUpdate por que es un INSERT
        int filas = ps.executeUpdate();
        
        if (filas > 0) {
            System.out.println("Se ha agregado la materia: " + nombre);
        }
    }
    
    public static void inscribirAlumnoMateriaDB(String nota,String idAlumno,String idMateria,Connection con) throws SQLException{
        // Indico que el metodo puede tirar una excepcion SQLException y que la maneje el main
        // Establezco como va a ser la consulta con los datos recibidos por parametro
        String sql = "INSERT INTO "
                   + "inscripcion(nota, idAlumno, idMateria) "
                   + "VALUES ("+ nota +","+idAlumno+","+idMateria+")"; 
        
        // Preparo la consulta
        PreparedStatement ps = con.prepareStatement(sql);
        
        // La ejecuto con executeUpdate por que es un INSERT
        int filas = ps.executeUpdate();
        
        if (filas > 0) {
            System.out.println("Se ha inscripto el alumno");
        }
    }
}
