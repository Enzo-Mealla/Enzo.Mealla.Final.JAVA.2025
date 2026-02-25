
package proyectoalmacen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Almacen<T extends Buloneria> implements IGestionable<T> {
    
    
    private List<T> listaElementos = new ArrayList<>();
    
    
    
    public Almacen(){
        this.listaElementos = new ArrayList<>();
    };

    @Override
    public void agregar(T elemento) throws DatoInvalidoException { // <--- Avisamos que puede fallar
    // Validamos si el ID ya existe
    if (buscarPorId(elemento.getId()) != null) {
        throw new DatoInvalidoException("El ID " + elemento.getId() + " ya está registrado en el sistema.");
    }
    
    // Validamos que el stock no sea negativo
    if (elemento.getStock() < 0) {
        throw new DatoInvalidoException("No se puede ingresar un stock negativo.");
    }

    listaElementos.add(elemento);
}


    @Override
    public boolean eliminar(int id) {
        Iterator<T> elemento = listaElementos.iterator();
        while (elemento.hasNext()){
            T actual = elemento.next();
            if(actual.getId() == id){
                elemento.remove();
                return true;
            }
        }
        return false;
    };

    @Override
    public T buscarPorId(int id) {
        for(T elemento : listaElementos){
            if(elemento.getId() == id){
                return elemento;
            }
        }
        return null;

    };

    @Override
    public void listarTodo() {
        if(listaElementos.isEmpty()){
            System.out.println("Almacen vacio");
        }else{
            for(T elemento : listaElementos){
                System.out.println(elemento.toString());
            }
        }

    };
    
    public List<T> getLista(){
        return listaElementos;
    }
    
}
