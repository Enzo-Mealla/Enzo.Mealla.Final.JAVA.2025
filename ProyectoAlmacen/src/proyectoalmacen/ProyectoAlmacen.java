
package proyectoalmacen;

import Persistencia.PersistenciaBinaria;
import Persistencia.PersistenciaCSV;
import Persistencia.PersistenciaJSON;
import Persistencia.PersistenciaTXT;
import java.util.ArrayList;
import java.util.List;



public class ProyectoAlmacen {

    
    public static void main(String[] args) {
        
            // 1. CARGA INICIAL (Simulamos entrada de datos)
        List<Buloneria> lista = new ArrayList<>();
        lista.add(new Bulon(10, 50.0, TipoRosca.METRICA, 20.0, 1, "Bulon Cabeza Hexagonal 10mmx50mm ", 100, TipoTratamiento.ZINCADO, 150.0));
        lista.add(new Bulon(12, 60.0, TipoRosca.WHITWORTH, 30.0, 2, "Bulon Cabeza Redonda 12mmx60mm", 50, TipoTratamiento.CROMADO, 200.0));

        System.out.println("--- PRUEBA 1: Ordenamiento ---");
        // Probamos el ordenamiento por nombre (Punto 4)
        lista.sort((b1, b2) -> b1.getNombre().compareToIgnoreCase(b2.getNombre()));
        System.out.println("Lista ordenada: " + lista);

        System.out.println("\n--- PRUEBA 2: Filtrado ---");
        // Probamos filtrar solo Bulones (Punto 4)
        List<Buloneria> soloBulones = Utilidades.filtrar(lista, b -> b instanceof Bulon);
        System.out.println("Se encontraron " + soloBulones.size() + " bulones.");



        System.out.println("\n--- PRUEBA 3: Interfaz Funcional (Descuento) ---");
        // Aplicamos 10% de descuento a toda la lista usando el forEach (Punto 4)
        Utilidades.aplicarDescuento(lista, 10.0);
        System.out.println("Precio del primer item con descuento: " + lista.get(0).getPrecioUtil());

        System.out.println("\n--- PRUEBA 4: Persistencia ---");

        //guardamos binario
        PersistenciaBinaria.guardar(lista);
        List<Buloneria> recuperada = PersistenciaBinaria.cargar();
        System.out.println("Recuperados binarios: " + recuperada.size());

        // Guardar CSV (Excel)
        PersistenciaCSV.guardarCSV(lista);
        List<Buloneria> listaCSV = PersistenciaCSV.cargarCSV();
        imprimirListaConsola(listaCSV);

        System.out.println("Items recuperados del CSV: " + listaCSV.size());
        // Guardamos con Gsons
        PersistenciaJSON.guardarJSON(lista,"src/datos/datos.json");
        // Recuperamos para ver si se mantiene la integridad
        List<Buloneria> listaJSON = PersistenciaJSON.cargarJSON();
        imprimirListaConsola(listaJSON);
        System.out.println("Items recuperados del JSON: " + listaJSON.size());

        System.out.println("\n--- PRUEBA 5: Exportación de Reporte TXT ---");
        // Exportamos el listado filtrado de bulones (Punto 5)
        PersistenciaTXT.exportarReporteTXT(soloBulones, "Reporte de Bulones Filtrados");

        System.out.println("\n--- TODO OK: Sistema verificado ---");
        
    }
    
    // MÉTODO AUXILIAR PARA NO REPETIR CÓDIGO
    public static void imprimirListaConsola(List<Buloneria> lista) {
        if (lista.isEmpty()) {
            System.out.println("La lista está vacía o el archivo no existe.");
        } else {
            // Usamos un forEach con una lambda para mostrar los datos clave
            lista.forEach(b -> System.out.printf("ID: %-3d | Nombre: %-20s | Stock: %-5d | Precio: $%-7.2f%n", 
                    b.getId(), b.getNombre(), b.getStock(), b.getPrecioUtil()));
        }
    }
    
}
