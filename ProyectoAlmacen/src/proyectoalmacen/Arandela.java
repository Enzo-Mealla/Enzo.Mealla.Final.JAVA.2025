
package proyectoalmacen;


public class Arandela extends Buloneria {
    private int diametroInterior;
    private TipoArandela tipoArandela;

    public Arandela(int diametroInterior, TipoArandela tipoArandela, int id, String nombre, int stock, TipoTratamiento tipoTratamiento, double precioUtil) {
        super(id, nombre, stock, tipoTratamiento, precioUtil);
        this.diametroInterior = diametroInterior;
        this.tipoArandela = tipoArandela;
    }

    public Arandela(int diametroInterior, int id, String nombre, int stock, TipoTratamiento tipoTratamiento, double precioUtil) {
        super(id, nombre, stock, tipoTratamiento, precioUtil);
        this.diametroInterior = diametroInterior;
    }

    public Arandela(int id, String nombre, int stock) {
        super(id, nombre, stock);
    }

    @Override
    public String obtenerMedidas() {
        return "Arandela " + tipoArandela + " | Diámetro Int: " + diametroInterior + "mm";
    }

    public int getDiametroInterior() {
        return diametroInterior;
    }

    public TipoArandela getTipoArandela() {
        return tipoArandela;
    }

    public void setDiametroInterior(int diametroInterior) {
        this.diametroInterior = diametroInterior;
    }

    public void setTipoArandela(TipoArandela tipoArandela) {
        this.tipoArandela = tipoArandela;
    }

    
    
    
}
