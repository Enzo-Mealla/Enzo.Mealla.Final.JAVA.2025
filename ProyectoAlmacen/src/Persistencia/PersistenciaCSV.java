
package Persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import proyectoalmacen.Bulon;
import proyectoalmacen.Buloneria;
import proyectoalmacen.TipoRosca;
import proyectoalmacen.TipoTratamiento;

/**
 *
 * @author Enzo
 */
public class PersistenciaCSV {
    
    
    public static void guardarCSV(List<? extends Buloneria> lista) {
    try (PrintWriter writer = new PrintWriter(new FileWriter("src/datos/datos.csv"))) {
        for (Buloneria b : lista) {
            // id;nombre;stock;precio;tipo
            writer.println(b.getId() + ";" + b.getNombre() + ";" + b.getStock() + ";" + b.getPrecioUtil());
        }
    } catch (IOException e) {
        System.err.println("Error en CSV: " + e.getMessage());
    }
    }
    
    
    public static List<Buloneria> cargarCSV() {
        List<Buloneria> lista = new ArrayList<>();
   
        File archivo = new File("src/datos/datos.csv");

        if (!archivo.exists()) {
            System.err.println("ERROR: El archivo no existe en " + archivo.getAbsolutePath());
            return lista;
        }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), "UTF-8"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
             

                String[] v = linea.split(";");

                
                if (v.length >= 4) {
                    try {
                        int id = Integer.parseInt(v[0].trim());
                        String nombre = v[1].trim();
                        int stock = Integer.parseInt(v[2].trim());
                        double precio = Double.parseDouble(v[3].trim());

                        
                        lista.add(new Bulon(0, 0, TipoRosca.METRICA, 0, id, nombre, stock, TipoTratamiento.SIN_TRATAMIENTO, precio));
                    } catch (NumberFormatException e) {
                        // Si falla el parseo, probablemente sea la cabecera (ID;Nombre...)
                        // No hacemos nada y seguimos con la siguiente línea
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
        }
        return lista;
    }
}
