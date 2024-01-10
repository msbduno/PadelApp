package padelapp.interactions;

public class Terrain {
    private int numero;
    private boolean estReserve;

    public Terrain (){}

    public Terrain(int num, boolean estReserve){
        this.numero = num;
        this.estReserve = estReserve;
    }

    public int getNumero() {
        return numero;
    }
    
    public boolean getEstReserve() {
        return estReserve;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setEstReserve(boolean estReserve) {
        this.estReserve = estReserve;
    }
}
