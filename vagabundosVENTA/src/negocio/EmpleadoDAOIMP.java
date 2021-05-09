/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import configuracion.DBHelper;
import entidades.Empleado;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Soto Pacheco
 */
public class EmpleadoDAOIMP implements CRUD<Empleado> {

    DBHelper bd = new DBHelper();
String TABLA ="empleados";
    @Override
    public boolean agregar(Empleado empleado) {
        boolean resultado = false;
        if (bd.connect()) {
            try {
                StringBuilder sql = new StringBuilder();

                ///sql.append("INSERT INTO `vagabundosws`.`empleados` (`int`, `nombre`, `codigo`, `rol`, `contraseña`) VALUES ('1', 'Carlos Alberto Soto Pacheco', '1450842', 'Admin', 'a231195d67aa0224b886ddd8fbdcfa92')");
                sql.append("INSERT INTO empleados")
                        .append("(nombre, codigo,rol,contraseña,) VALUES")
                        .append("('").append(empleado.getNombre()).append("',")
                        .append("'").append(empleado.getCodigoEmpleado()).append("',")
                        .append("'").append(empleado.getRol()).append("',")
                        .append("MD5('").append(empleado.getContraseña()).append("')")
                        .append("'").append(empleado.getPuesto()).append("')");

                resultado = (boolean) bd.execute(sql.toString(), true);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                bd.disconnect();
            }

        }

        return resultado;
    }

    public Empleado login(long codigo, String contraseña) {
        Empleado empleado = new Empleado();

        if (bd.connect()) {

            try {

                StringBuilder sql = new StringBuilder();

                sql.append("SELECT * FROM empleados WHERE codigo ='").
                        append(codigo).append("' AND contraseña = MD5('").append(contraseña).append("')");

                ResultSet rs = (ResultSet) bd.execute(sql.toString(), false);

                if (rs.next()) {
                    empleado.setCodigoEmpleado(rs.getLong("codigo"));
                  
                    empleado.setContraseña(rs.getString("contraseña"));
                    empleado.setIdEmpleado(rs.getInt("id"));
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setRol(rs.getString("rol"));
                     empleado.setPuesto(rs.getString("puesto"));
                }

            } catch (Exception e) {
            }
        }

        return empleado;
    }

    public long autorizacion(long codigo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM autorizacioncodigos WHERE codigo =").append(codigo);
        long codigoA = 0;
        try {
            if (bd.connect()) {

                ResultSet rs = (ResultSet) bd.execute(sql.toString(), false);
                if (rs.next()) {
                    codigoA = rs.getLong("codigo");

                }

            }

        } catch (Exception e) {
        } finally {
            bd.disconnect();
        }
        return codigoA;
    }

    @Override
    public boolean editar(Empleado empleado) {
    boolean resultado = false;
        try {
            if (bd.connect()) {
                String query = "UPDATE empleados SET  "
                        + "`nombre` = '" + empleado.getNombre() + "',  "
                        + "`codigo` = '" + empleado.getCodigoEmpleado()+ "',  "
                        + "`rol` = '" + empleado.getRol()+ "',  "
                        + "`contraseña` = '" + empleado.getContraseña()+ "', "
                        + " `puesto` = '" + empleado.getPuesto()+ "' "
                        + " WHERE (`id` = '" + empleado.getIdEmpleado()+ "') ";
                resultado = (boolean) bd.execute(query, true);
            }

        } catch (Exception e){
            System.out.println(e);
          
        } finally {
            bd.disconnect();
        }
        return resultado;
    
    
    }

    @Override
    public boolean eliminar(int id) {
    
      boolean resultado = false;

        try {
            if (bd.connect()) {
                String query = "DELETE FROM empleados WHERE (`id` = '" + id + "');";
                resultado = (boolean) bd.execute(query, true);
            }
        } catch (Exception e) {
        } finally {
            bd.disconnect();
        }
        return resultado;
        
    
    }

    @Override
    public List<Empleado> buscarTodos() {
   
     List<Empleado> empleado = new ArrayList();

        try {
            if (bd.connect()) {
                String query = "SELECT * FROM  empleados";
                ResultSet rs = (ResultSet) bd.execute(query, false);
                while (rs.next()) {
                    Empleado empleados = new Empleado();
                    empleados.setIdEmpleado(rs.getInt("id"));
                    empleados.setNombre(rs.getString("nombre"));
                    empleados.setCodigoEmpleado(rs.getLong("codigo"));
                    empleados.setRol(rs.getString("rol"));
                   // empleados.setContraseña(rs.getString("contraseña"));
                    empleados.setPuesto(rs.getString("puesto"));
                    empleado.add(empleados);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            bd.disconnect();
        }

        return empleado;
    }

    @Override
    public Empleado buscarId(int id) {
   
        
         Empleado empleado = new Empleado();
        try {
            if (bd.connect()) {
                String query = "SELECT * FROM " + TABLA + " WHERE id = " + id;
                ResultSet rs = (ResultSet) bd.execute(query, false);
                if (rs.next()) {
                    empleado.setIdEmpleado(rs.getInt("id"));
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setCodigoEmpleado(rs.getLong("codigo"));
                    empleado.setRol(rs.getString("rol"));
                   // empleado.setContraseña(rs.getString("contraseña"));
                    empleado.setPuesto(rs.getString("puesto"));
                }
            }
        } catch (Exception e) {
        } finally {
            bd.disconnect();
        }
        return empleado;
    
    
    }

}
