
package Persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PersistenciaBinaria {
    private static final String RUTA_ARCHIVO = "src/datos/datos_inventario.dat";
    
    public static <T extends Serializable> void guardar(List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(lista);
            System.out.println("LOG: Datos persistidos correctamente en " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println("ERROR al guardar: " + e.getMessage());
        }
    }
    
    public static <T extends Serializable> List<T> cargar() {
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists()) return new ArrayList<>(); // Si no existe, devolvemos lista vacía

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR al cargar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
}
