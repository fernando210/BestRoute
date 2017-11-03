package fgv.Controller.AgPmx;
/**
 * @author Peter Tran
 */
import java.util.Random;

public class Cromossomo
{
    private int cromossomoId;                           // uniquely identify a chromosome //
    private double weight;                              // weight of circuit //
    private double fitness;                             // chromosome fitness //
    private double fitnessPrcent;                      // chromosome fitness Percent//
    private double[] faixaRoleta = { 0, 0 };            // Piece of the pizza which it can be selected //
    e
    public Cromossomo(int cromossomoId){//, double weight
        this.cromossomoId = cromossomoId;
        //this.weight = weight;
    }

    public int getCromossomoId(){
        return cromossomoId;
    }

    public double getWeight(){
        return weight;
    }

    public double getFitness(){
        return fitness;
    }

    public void setFitness_PerGen(double fitness){
        this.fitness = fitness;
    }

    public double getFitnessPercent() {
        return fitnessPercent;
    }

    public void setFitnessPercent(double fitnessPercent) {
        this.fitnessPercent = fitnessPercent;
    }

    public void setRangeRoleta(double inicio, double fim)
    {
        faixaRoleta[0] = inicio;
        faixaRoleta[1] = fim;
    }

    public double[] getRangeRoleta()
    {
        return this.faixaRoleta;
    }}
