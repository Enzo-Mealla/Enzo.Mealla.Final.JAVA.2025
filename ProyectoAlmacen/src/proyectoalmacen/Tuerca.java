
package proyectoalmacen;


public class Tuerca extends Buloneria implements Rectificable {
    private int diametroMM;
    private TipoRosca tipoRosca;

    public Tuerca(int diametroMM, TipoRosca tipoRosca, int id, String nombre, int stock, TipoTratamiento tipoTratamiento, double precioUtil) {
        super(id, nombre, stock, tipoTratamiento, precioUtil);
        this.diametroMM = diametroMM;
        this.tipoRosca = tipoRosca;
    }

    public Tuerca(int diametroMM, int id, String nombre, int stock, TipoTratamiento tipoTratamiento, double precioUtil) {
        super(id, nombre, stock, tipoTratamiento, precioUtil);
        this.diametroMM = diametroMM;
    }

    public Tuerca(int id, String nombre, int stock) {
        super(id, nombre, stock);
    }

    @Override
    public String obtenerMedidas() {
        return "Tuerca - Ø: " + diametroMM + "mm, Rosca: " + tipoRosca;
    }

    @Override
    public void rectificarRosca(TipoRosca nuevaRosca) {
        this.tipoRosca = nuevaRosca;
        System.out.println("Rosca de la tuerca rectificada a: " + nuevaRosca);
    }

    public int getDiametroMM() {
        return diametroMM;
    }

    public TipoRosca getTipoRosca() {
        return tipoRosca;
    }

    public void setDiametroMM(int diametroMM) {
        this.diametroMM = diametroMM;
    }

    public void setTipoRosca(TipoRosca tipoRosca) {
        this.tipoRosca = tipoRosca;
    }

    
    
    
    
    
}
