
package proyectoalmacen;

import java.io.Serializable;


public abstract class Buloneria implements Serializable, Comparable<Buloneria> {
    private int id;
    private String nombre;
    private int stock;
    private TipoTratamiento tipoTratamiento;
    private double precioUtil;

    public Buloneria(int id, String nombre, int stock, TipoTratamiento tipoTratamiento, double precioUtil) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.tipoTratamiento = tipoTratamiento;
        this.precioUtil = precioUtil;
    }

    public Buloneria(int id, String nombre, int stock, TipoTratamiento tipoTratamiento) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.tipoTratamiento = tipoTratamiento;
    }

    public Buloneria(int id, String nombre, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
    }
    
    
    public void actualizarStock(int cantidad) throws StockInsuficienteException {
    if (cantidad < 0) {
        throw new StockInsuficienteException("El stock no puede ser negativo (" + cantidad + ")");
    }
    this.stock = cantidad;
}
    
    public double obtenerValorInventario(){
        return this.stock * this.precioUtil;
    };
    
    public abstract String obtenerMedidas();

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public TipoTratamiento getTipoTratamiento() {
        return tipoTratamiento;
    }

    public void setPrecioUtil(double precioUtil) {
        this.precioUtil = precioUtil;
    }
    
    

    public double getPrecioUtil() {
        return precioUtil;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setTipoTratamiento(TipoTratamiento tipoTratamiento) {
        this.tipoTratamiento = tipoTratamiento;
    }

    
    
    @Override
    public int compareTo(Buloneria otroId){
        return Integer.compare(this.id, otroId.id);
    }
    
    
    
    
}
