/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;
import configuracion.DBHelper;
import entidades.Producto;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lopez
 */
public class ProductoDAOIMP implements CRUD<Producto> {
 DBHelper bd = new DBHelper();
   String TABLA = "producto";
     @Override
    public boolean agregar(Producto producto) {
     boolean resultado = false;
        if (bd.connect()) {
            try {
                StringBuilder sql = new StringBuilder();

                sql.append("INSERT INTO producto")
                        .append("(nombreProducto,cantidadProducto,precioProducto) VALUES")
                        .append("('").append(producto.getNombreProducto()).append("',")
                        .append("'").append(producto.getCantidadProducto()).append("',")
                        .append("'").append(producto.getPrecioProducto()).append("')");
                       
                resultado = (boolean) bd.execute(sql.toString(), true);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                bd.disconnect();
            }

        }

        return resultado;   
    
    
    }

    @Override
    public boolean editar(Producto producto) {
     boolean resultado = false;
        try {
            if (bd.connect()) {
                String query = "UPDATE producto SET  "
                        + "`nombreProducto` = '" + producto.getNombreProducto()+ "',  "
                        + "`cantidadProducto` = '" + producto.getCantidadProducto()+ "',  "
                        + " `precioProducto` = '" + producto.getPrecioProducto()+ "' "
                        + " WHERE (`idProducto` = '" + producto.getIdProducto()+ "') ";
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
                String query = "DELETE FROM empleado WHERE (`idProducto` = '" + id + "');";
                resultado = (boolean) bd.execute(query, true);
            }
        } catch (Exception e) {
        } finally {
            bd.disconnect();
        }
        return resultado;
   
    
    
    }

    @Override
    public List<Producto> buscarTodos() {
     List<Producto> producto = new ArrayList();

        try {
            if (bd.connect()) {
                String query = "SELECT * FROM " + TABLA;
                ResultSet rs = (ResultSet) bd.execute(query, false);
                while (rs.next()) {
                    Producto productos = new Producto();
                    productos.setIdProducto(rs.getInt("idProducto"));
                    productos.setNombreProducto(rs.getString("nombreProducto"));
                    productos.setCantidadProducto(rs.getInt("cantidadProducto"));
                    productos.setPrecioProducto(rs.getDouble("precioProducto"));
                    producto.add(productos);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            bd.disconnect();
        }

        return producto;
    
    }

    @Override
    public Producto buscarId(int id) {
    
     Producto producto = new Producto();
        try {
            if (bd.connect()) {
                String query = "SELECT * FROM " + TABLA + " WHERE idProducto = " + id;
                ResultSet rs = (ResultSet) bd.execute(query, false);
                if (rs.next()) {
                    producto.setIdProducto(rs.getInt("idProducto"));
                    producto.setNombreProducto(rs.getString("nombreProducto"));
                    producto.setCantidadProducto(rs.getInt("cantidadProducto"));
                    producto.setPrecioProducto(rs.getDouble("precioProducto"));
                }
            }
        } catch (Exception e) {
        } finally {
            bd.disconnect();
        }
        return producto;
    
    
    }
    
    
    
}
