/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import configuracion.DBHelper;
import entidades.Empleado;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Carlos Soto Pacheco
 */
public class EmpleadoDAOIMP implements CRUD<Empleado> {

    DBHelper bd = new DBHelper();

    @Override
    public boolean agregar(Empleado empleado) {
        boolean resultado = false;
        if (bd.connect()) {
            try {
                StringBuilder sql = new StringBuilder();

                ///sql.append("INSERT INTO `vagabundosws`.`empleados` (`int`, `nombre`, `codigo`, `rol`, `contraseña`) VALUES ('1', 'Carlos Alberto Soto Pacheco', '1450842', 'Admin', 'a231195d67aa0224b886ddd8fbdcfa92')");
                sql.append("INSERT INTO empleados")
                        .append("(nombre, codigo,rol,contraseña) VALUES")
                        .append("('").append(empleado.getNombre()).append("',")
                        .append("'").append(empleado.getCodigoEmpleado()).append("',")
                        .append("'").append(empleado.getRol()).append("',")
                        .append("MD5('").append(empleado.getContraseña()).append("'))");

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
                if(rs.next()){
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
    public boolean editar(Empleado t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Empleado> buscarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Empleado buscarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
