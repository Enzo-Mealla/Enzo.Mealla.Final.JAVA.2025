
package Persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import proyectoalmacen.Buloneria;

/**
 *
 * @author Enzo
 */
public class PersistenciaTXT {
    
    
    public static void exportarReporteTXT(List<Buloneria> lista, String titulo) {
        // Definimos el ancho de la columna Nombre (punto de referencia)
    int anchoNombre = 30; 
    
    try (PrintWriter pw = new PrintWriter(new FileWriter("src/datos/reporte_final.txt"))) {
        pw.println("======================================================================");
        pw.println("           REPORTE DE INVENTARIO: " + titulo.toUpperCase());
        pw.println("======================================================================");
        
        // El formato usa %-ANCHO.ANCHO s para forzar que NUNCA se pase de ahí
        // %-5s      -> ID (5 espacios)
        // %-30.30s  -> NOMBRE (Exactamente 30 espacios, si es más largo, lo corta)
        // %-10s     -> STOCK (10 espacios)
        // %-10s     -> PRECIO (10 espacios)
        String formato = "%-5s %-40.40s %-10s %-10s%n";
        String formatoDatos = "%-5d %-40.40s %-10d $%-9.2f%n";

        // Encabezado
        pw.printf(formato, "ID", "NOMBRE", "STOCK", "PRECIO");
        pw.println("----------------------------------------------------------------------");
        
        for (Buloneria b : lista) {
            pw.printf(formatoDatos, 
                      b.getId(), 
                      b.getNombre(), 
                      b.getStock(), 
                      b.getPrecioUtil());
        }
        
        pw.println("======================================================================");
        pw.println("Total de artículos: " + lista.size());
        
    } catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
    }
    }
    
}
