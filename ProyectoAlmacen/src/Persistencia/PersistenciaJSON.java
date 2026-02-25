
package Persistencia;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import proyectoalmacen.Buloneria;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import proyectoalmacen.Arandela;
import proyectoalmacen.Bulon;
import proyectoalmacen.Tuerca;

public class PersistenciaJSON {
    
    public static void guardarJSON(List<Buloneria> lista, String nombreArchivo) {
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    try (FileWriter writer = new FileWriter(nombreArchivo)) {
        gson.toJson(lista, writer);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    public static List<Buloneria> cargarJSON() {
    
    List<Buloneria> lista = new ArrayList<>();
    try (FileReader reader = new FileReader("src/datos/datos.json")) {
        JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
        for (JsonElement elemento : jsonArray) {
            JsonObject obj = elemento.getAsJsonObject();
            // Aquí chequeamos un campo único de cada hijo para saber qué es
            if (obj.has("longitudVastagoR")) {
                lista.add(new Gson().fromJson(obj, Bulon.class));
            } else if (obj.has("diametroInterior")) {
                lista.add(new Gson().fromJson(obj, Arandela.class));
            } else {
                lista.add(new Gson().fromJson(obj, Tuerca.class));
            }
        }
    } catch (Exception e) {
        System.out.println("Error al parsear: " + e.getMessage());
    }
    return lista;
}
}
