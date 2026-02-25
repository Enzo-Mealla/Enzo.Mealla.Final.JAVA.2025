
package proyectoalmacen;


public interface IGestionable<T> {
    
    void agregar(T elemento)throws DatoInvalidoException;;
    
    boolean eliminar(int id);
    
    T buscarPorId(int id);
    
    void listarTodo();
}
