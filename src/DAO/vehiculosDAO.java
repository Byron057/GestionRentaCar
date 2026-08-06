package DAO;

import java.sql.*;

import Conexion.conexion;
import Models.vehiculos;
import java.util.ArrayList;
import java.util.List;

import Models.colores;
import Models.marcas;
import Models.modelos;
import Models.tipos;

public class vehiculosDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean insertarVehiculo(vehiculos v){
        String sql ="INSERT INTO vehiculos (placa, fk_id_marca, fk_id_modelo, fk_id_tipo, fk_id_color, estado) VALUES(?,?,?,?,?,?)";
        try{
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setString(1, v.getPlaca());
           ps.setInt(2, v.getIdMarca());
           ps.setInt(3, v.getIdModelo());
           ps.setInt(4, v.getIdTipo());
           ps.setInt(5, v.getIdColor());
           ps.setString(6, v.getEstado());
           
           ps.executeUpdate();
           return true;
             
        }catch(Exception e){
            return false;    
        }finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
            }
        }
    }
    
public List<vehiculos>listarVehiculo(){
    List<vehiculos> lista = new ArrayList<>();
    
    String sql = "SELECT v.id_vehiculo, " +
                "v.placa, " +
                "v.fk_id_marca, " +
                "m.marca AS nombre_marca, " + 
                "v.fk_id_modelo, " +
                "mo.modelo as nombre_modelo, " + 
                "v.fk_id_tipo, " +
                "t.tipo As nombre_tipo, " + 
                "v.fk_id_color, " +
                "c.color as nombre_color, " + 
                "v.estado " +
                "FROM vehiculos v " +
                "INNER JOIN marcas_vehiculos m ON v.fk_id_marca = m.id_marca " + 
                "INNER JOIN modelos mo ON v.fk_id_modelo = mo.id_modelo " + 
                "INNER JOIN tipos t ON v.fk_id_tipo = t.id_tipo " + 
                "INNER JOIN colores c ON v.fk_id_color = c.id_color " + 
                "ORDER BY v.id_vehiculo DESC";

    try{
     con = cn.getConnection();
     ps = con.prepareStatement(sql);
     rs = ps.executeQuery();
        while(rs.next()){
            vehiculos v = new vehiculos();
            
            v.setIdVehiculo(rs.getInt("id_vehiculo"));
            v.setPlaca(rs.getString("placa"));
            v.setIdMarca(rs.getInt("fk_id_marca"));
            v.setIdModelo(rs.getInt("fk_id_modelo"));
            v.setIdTipo(rs.getInt("fk_id_tipo"));
            v.setIdColor(rs.getInt("fk_id_color"));
            v.setEstado(rs.getString("estado"));
            v.setNombreMarca(rs.getString("nombre_marca"));
            v.setNombreModelo(rs.getString("nombre_modelo"));
            v.setNombreTipo(rs.getString("nombre_tipo"));
            v.setNombreColor(rs.getString("nombre_color"));

            lista.add(v);  
        }    
    }catch(Exception e){
    }finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}

public List<marcas> listarMarcasActivas() {
    List<marcas> lista = new ArrayList<>();
    String sql = "SELECT * FROM marcas_vehiculos WHERE estado = 'Activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            marcas m = new marcas();
            m.setId(rs.getInt("id_marca"));
            m.setNombreMarca(rs.getString("marca"));
            lista.add(m);
        }
    } catch (Exception e) {
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}

public List<modelos> listarModelosActivos() {
    List<modelos> lista = new ArrayList<>();
    String sql = "SELECT * FROM modelos WHERE estado = 'Activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            modelos mod = new modelos();
            mod.setId(rs.getInt("id_modelo"));
            mod.setNombreModelo(rs.getString("modelo"));
            lista.add(mod);
        }
    } catch (Exception e) {
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}

public List<tipos> listarTiposActivos() {
    List<tipos> lista = new ArrayList<>();
    String sql = "SELECT * FROM tipos WHERE estado = 'activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            tipos t = new tipos();
            t.setId(rs.getInt("id_tipo"));
            t.setNombreTipo(rs.getString("tipo"));
            lista.add(t);
        }
    } catch (Exception e) {
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}

public List<colores> listarColoresActivos() {
    List<colores> lista = new ArrayList<>();
    String sql = "SELECT * FROM colores WHERE estado = 'Activo'";
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            colores c = new colores();
            c.setId_color(rs.getInt("id_color"));
            c.setNombreColor(rs.getString("color"));
            lista.add(c);
        }
    } catch (Exception e) {
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
        }
    }
    return lista;
}

public List<modelos> listarModelosMarcas(int idMarca){
  List<modelos> lista = new ArrayList<>();
  String sql = "SELECT * FROM modelos WHERE fk_id_marca = ? AND estado = 'activo'";
  try{
      con = cn.getConnection();
      ps = con.prepareStatement(sql);
      ps.setInt(1, idMarca);
      rs = ps.executeQuery();
      while(rs.next()){
          modelos mod = new modelos();
          mod.setId(rs.getInt("id_modelo"));
          mod.setFk_id_marca(rs.getInt("fk_id_marca"));
          mod.setModelo(rs.getString("modelo"));
          mod.setEstado(rs.getString("estado"));
          mod.setNombreModelo(rs.getString("modelo"));
          lista.add(mod);
      }
  }catch(Exception e){
  } finally {
      try {
          if (rs != null) rs.close();
          if (ps != null) ps.close();
          if (con != null) con.close();
      } catch (SQLException e) {
      }
  }
  return lista;
}

public boolean existePlaca(String placa) {
    boolean existe = false;
    String sql = "SELECT * FROM vehiculos WHERE placa = ?"; 
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, placa);
        rs = ps.executeQuery();
        if (rs.next()) {
            existe = true; 
        }
    } catch (Exception e) {
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
        }
    }
    return existe;
}

public boolean existePlacaAlEditar(String placa, int idVehiculo) {
    boolean existe = false;
    String sql = "SELECT * FROM vehiculos WHERE placa = ? AND id_vehiculo != ?"; 
    try {
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, placa);
        ps.setInt(2, idVehiculo); 
        rs = ps.executeQuery();
        if (rs.next()) {
            existe = true; 
        }
    } catch (Exception e) {
        System.out.println("Error al verificar placa al editar: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) { }
    }
    return existe;
}

public boolean cambiarEstadoVehiculo(int idVehiculo, String nuevoEstado){
    String sql = "UPDATE vehiculos SET estado = ? WHERE id_vehiculo = ?";
    try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, nuevoEstado);
        ps.setInt(2, idVehiculo);
        ps.executeUpdate();
        return true;
    }catch(Exception e){
        return false;
    }finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
        }
    }
}

public boolean editarVehiculo(vehiculos v){
    String sql = "UPDATE vehiculos SET "
            +"placa=?,"
            +"fk_id_marca=?,"
            +"fk_id_modelo=?,"
            +"fk_id_tipo=?,"
            +"fk_id_color=?,"
            +"estado=? "
            +"WHERE id_vehiculo=?";
    try{
        con = cn.getConnection();
        ps = con.prepareStatement(sql);
        
        ps.setString(1,v.getPlaca());
        ps.setInt(2,v.getIdMarca());
        ps.setInt(3,v.getIdModelo());
        ps.setInt(4,v.getIdTipo());
        ps.setInt(5,v.getIdColor());
        ps.setString(6, v.getEstado());
        ps.setInt(7,v.getIdVehiculo());
        
        ps.executeUpdate();
        return true;

    }catch(SQLException e){
        return false;
    }finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
        }
    }
}

}