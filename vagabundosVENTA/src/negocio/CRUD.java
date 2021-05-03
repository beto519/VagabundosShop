
package negocio;

import java.util.List;


public interface CRUD<T> {
    public boolean agregar(T t);
    public boolean editar(T t);
    public boolean eliminar(int id);
    public List<T> buscarTodos();
    public T buscarId(int id);
}
