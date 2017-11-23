package fgv.Model;

/**
 * Created by Fernando on 14/10/2017.
 */

public class MPassageiroDistancia {

    private int IdPassageiroInicio;
    private int IdPassageiroFim;
    private int IdDestino;
    private double Distancia;

    public int getIdPassageiroInicio() {
        return IdPassageiroInicio;
    }

    public void setIdPassageiroInicio(int idPassageiroInicio) {
        this.IdPassageiroInicio = idPassageiroInicio;
    }

    public int getIdPassageiroFim() {
        return IdPassageiroFim;
    }

    public void setIdPassageiroFim(int idPassageiroFim) {
        this.IdPassageiroFim = idPassageiroFim;
    }

    public int getIdDestino() {
        return IdDestino;
    }

    public void setIdDestino(int idDestino) {
        this.IdDestino = idDestino;
    }

    public double getDistancia() {
        return Distancia;
    }

    public void setDistancia(double distancia) {
        this.Distancia = distancia;
    }
}
