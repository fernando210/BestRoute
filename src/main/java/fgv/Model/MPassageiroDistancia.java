package fgv.Model;

/**
 * Created by Fernando on 14/10/2017.
 */

public class MPassageiroDistancia {

    private int idPassageiroInicio;
    private int idPassageiroFim;
    private int idDestino;
    private double distancia;

    public int getIdPassageiroInicio() {
        return idPassageiroInicio;
    }

    public void setIdPassageiroInicio(int idPassageiroInicio) {
        this.idPassageiroInicio = idPassageiroInicio;
    }

    public int getIdPassageiroFim() {
        return idPassageiroFim;
    }

    public void setIdPassageiroFim(int idPassageiroFim) {
        this.idPassageiroFim = idPassageiroFim;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
