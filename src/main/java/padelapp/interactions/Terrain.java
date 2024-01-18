package padelapp.interactions;

public class Terrain {
    private int idTerrain;
    private int numero;

    public Terrain (){}

    public Terrain(int idT, int num, boolean estReserve){
        this.idTerrain = idT;
        this.numero = num;
    }

    public int getIdTerrain() {
        return idTerrain;
    }

    public void setIdTerrain(int idT) {
        this.idTerrain = idT;
    }

    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }

}
