
package proyectoalmacen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class Utilidades {
    
    public static void ordenarPorPrecio(List<? extends Buloneria> lista) {
        lista.sort((b1, b2) -> Double.compare(b1.getPrecioUtil(), b2.getPrecioUtil()));
    }
    
    public static void ordenarPorStock(List<? extends Buloneria> lista) {
        lista.sort((b1, b2) -> Integer.compare(b2.getStock(), b1.getStock()));
    }
    
    public static List<Buloneria> filtrar(List<? extends Buloneria> lista, Predicate<Buloneria> criterio) {
        List<Buloneria> filtrados = new ArrayList<>();
        for (Buloneria b : lista) {
            if (criterio.test(b)) { // Aquí se ejecuta la lambda que vos mandes
                filtrados.add(b);
            }
        }
        return filtrados;
}
    
    
    public static <T extends Buloneria> void aplicarDescuento(List<T> lista, double porcentaje) {
        Consumer<T> descuentoEfectuado = b -> {
            double nuevoPrecio = b.getPrecioUtil() * (1 - porcentaje / 100);
            b.setPrecioUtil(nuevoPrecio);
        };
        lista.forEach(descuentoEfectuado);
    }
    
}
