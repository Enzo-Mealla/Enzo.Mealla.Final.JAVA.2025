
package proyectoalmacen;


public class Bulon extends Buloneria implements Rectificable {
    private int diametroMM;
    private double longitud;
    private TipoRosca tipoRosca;
    private double longitudVastagoR;

    public Bulon(int diametroMM, double longitud, TipoRosca tipoRosca, double longitudVastagoR, int id, String nombre, int stock, TipoTratamiento tipoTratamiento, double precioUtil) {
        super(id, nombre, stock, tipoTratamiento, precioUtil);
        this.diametroMM = diametroMM;
        this.longitud = longitud;
        this.tipoRosca = tipoRosca;
        this.longitudVastagoR = longitudVastagoR;
    }

    public Bulon(int diametroMM, double longitud, TipoRosca tipoRosca, int id, String nombre, int stock, TipoTratamiento tipoTratamiento, double precioUtil) {
        super(id, nombre, stock, tipoTratamiento, precioUtil);
        this.diametroMM = diametroMM;
        this.longitud = longitud;
        this.tipoRosca = tipoRosca;
    }

    public Bulon(int id, String nombre, int stock) {
        super(id, nombre, stock);
    };

    
    
    @Override
    public String obtenerMedidas() {
        return "Bulón - Ø: " + diametroMM + "mm, Largo: " + longitud + "mm, Rosca: " + tipoRosca;
    };
    
    
    
    @Override
    public void rectificarRosca(TipoRosca nuevaRosca) {
        this.tipoRosca = nuevaRosca;
        System.out.println("Rosca del bulón rectificada a: " + nuevaRosca);
    };

    public int getDiametroMM() {
        return diametroMM;
    }

    public double getLongitud() {
        return longitud;
    }

    public TipoRosca getTipoRosca() {
        return tipoRosca;
    }

    public double getLongitudVastagoR() {
        return longitudVastagoR;
    }

    public void setDiametroMM(int diametroMM) {
        this.diametroMM = diametroMM;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setTipoRosca(TipoRosca tipoRosca) {
        this.tipoRosca = tipoRosca;
    }

    public void setLongitudVastagoR(double longitudVastagoR) {
        this.longitudVastagoR = longitudVastagoR;
    }

    

    
    
    
    
}
