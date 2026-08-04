/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.conexion;
import Models.alquileres;
import Models.clientes;     // IMPORTANTE: Importamos el modelo
import Models.vehiculos;    // IMPORTANTE: Importamos el modelo
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class alquileresDAO {

    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    // =====================================================
    // LISTAR CLIENTES ACTIVOS PARA COMBOBOX (Lógica de Modelos)
    // =====================================================
    public List<clientes> listarClientesActivos() {
        List<clientes> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE estado = 'Activo'";

   
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                clientes c = new clientes();
                c.setId_cliente(rs.getInt("id_cliente"));
                
                // 👇 AGREGA ESTA LÍNEA 👇
                c.setCedula(rs.getString("cedula")); 
                
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                lista.add(c);
            }

        } catch (Exception e) {
            // Silenciado para el .exe
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {}
        }
        return lista;
    }


    // =====================================================
    // LISTAR VEHICULOS DISPONIBLES (Lógica de Modelos)
    // =====================================================
    public List<vehiculos> listarVehiculosDisponibles() {
        List<vehiculos> lista = new ArrayList<>();
        
        // Hacemos el JOIN para traer los nombres reales en lugar de los números (IDs)
        String sql = "SELECT v.id_vehiculo, v.placa, m.marca AS nombre_marca, mo.modelo AS nombre_modelo " +
                     "FROM vehiculos v " +
                     "INNER JOIN marcas_vehiculos m ON v.fk_id_marca = m.id_marca " +
                     "INNER JOIN modelos mo ON v.fk_id_modelo = mo.id_modelo " +
                     "WHERE v.estado = 'Activo'";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                vehiculos v = new vehiculos();
                v.setIdVehiculo(rs.getInt("id_vehiculo"));
                v.setPlaca(rs.getString("placa"));
                
                // Guardamos la marca y el modelo auxiliarmente
                v.setNombreMarca(rs.getString("nombre_marca"));
                v.setNombreModelo(rs.getString("nombre_modelo"));
                
                lista.add(v);
            }

        } catch (Exception e) {
            // Silenciado
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {}
        }
        return lista;
    }


    // =====================================================
    // INSERTAR ALQUILER
    // =====================================================
    public boolean insertarAlquiler(alquileres a){
        String sql="INSERT INTO alquileres "
                + "(fk_id_cliente,fk_id_vehiculo,fecha_alquiler,total,estado,dias)"
                + " VALUES(?,?,?,?,?,?)";

        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1,a.getFkIdCliente());
            ps.setInt(2,a.getFkIdVehiculo());
            ps.setString(3,a.getFechaAlquiler());
            ps.setDouble(4,a.getTotal());
            ps.setString(5,a.getEstado());
            ps.setInt(6,a.getDias());

            ps.executeUpdate();

            // cambiar estado del vehículo
            actualizarEstadoVehiculo(a.getFkIdVehiculo(), "Alquilado");

            return true;

        }catch(Exception e){
            return false;
        }finally{
            try{
                if(ps != null) ps.close();
                if(con != null) con.close();
            }catch(SQLException e){}
        }
    }


    // =====================================================
    // LISTAR ALQUILERES
    // =====================================================
    public List<alquileres> listarAlquileres(){
        List<alquileres> lista = new ArrayList<>();
        String sql="SELECT "
                +"a.id_alquiler,"
                +"a.fk_id_cliente,"
                +"CONCAT(c.nombre,' ',c.apellido) AS cliente,"
                +"a.fk_id_vehiculo,"
                +"v.placa,"
                +"a.fecha_alquiler,"
                +"a.total,"
                +"a.dias,"
                +"a.estado "
                +"FROM alquileres a "
                +"INNER JOIN clientes c "
                +"ON a.fk_id_cliente=c.id_cliente "
                +"INNER JOIN vehiculos v "
                +"ON a.fk_id_vehiculo=v.id_vehiculo "
                +"ORDER BY a.id_alquiler DESC";

        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();

            while(rs.next()){
                alquileres a = new alquileres();

                a.setIdAlquiler(rs.getInt("id_alquiler"));
                a.setFkIdCliente(rs.getInt("fk_id_cliente"));
                a.setFkIdVehiculo(rs.getInt("fk_id_vehiculo"));
                a.setFechaAlquiler(rs.getString("fecha_alquiler"));
                a.setTotal(rs.getDouble("total"));
                a.setDias(rs.getInt("dias"));
                a.setEstado(rs.getString("estado"));

                // datos auxiliares para tabla
                a.setNombreCliente(rs.getString("cliente"));
                a.setPlaca(rs.getString("placa"));

                lista.add(a);
            }

        }catch(Exception e){
        }finally{
            try{
                if(rs!=null)rs.close();
                if(ps!=null)ps.close();
                if(con!=null)con.close();
            }catch(SQLException e){}
        }
        return lista;
    }


    // =====================================================
    // EDITAR ALQUILER
    // =====================================================
    public boolean editarAlquiler(alquileres a){
        String sql="UPDATE alquileres SET "
                +"fk_id_cliente=?,"
                +"fk_id_vehiculo=?,"
                +"fecha_alquiler=?,"
                +"total=?,"
                +"estado=?,"
                +"dias=? "
                +"WHERE id_alquiler=?";

        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);

            ps.setInt(1,a.getFkIdCliente());
            ps.setInt(2,a.getFkIdVehiculo());
            ps.setString(3,a.getFechaAlquiler());
            ps.setDouble(4,a.getTotal());
            ps.setString(5,a.getEstado());
            ps.setInt(6,a.getDias());
            ps.setInt(7,a.getIdAlquiler());

            ps.executeUpdate();
            return true;

        }catch(SQLException e){
            return false;
        }finally{
            try{
                if(ps!=null)ps.close();
                if(con!=null)con.close();
            }catch(SQLException e){}
        }
    }


    // =====================================================
    // ELIMINAR ALQUILER
    // =====================================================
    public boolean eliminarAlquiler(int idAlquiler){
        String sql="DELETE FROM alquileres "
                 +"WHERE id_alquiler=?";

        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1,idAlquiler);
            ps.executeUpdate();

            return true;

        }catch(Exception e){
            return false;
        }finally{
            try{
                if(ps!=null)ps.close();
                if(con!=null)con.close();
            }catch(SQLException e){}
        }
    }


    // =====================================================
    // CAMBIAR ESTADO DEL VEHICULO
    // =====================================================
    private void actualizarEstadoVehiculo(int idVehiculo,String estado){
        String sql="UPDATE vehiculos "
                 +"SET estado=? "
                 +"WHERE id_vehiculo=?";
        try{
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1,estado);
            ps.setInt(2,idVehiculo);
            ps.executeUpdate();

        }catch(Exception e){
        } finally {
            try {
                if(ps!=null) ps.close();
                if(con!=null) con.close();
            } catch (SQLException e) {}
        }
    }
}