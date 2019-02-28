/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

/**
 *
 * @author Marco
 */
public abstract class Aristas implements Comparable{
    
    private int peso;
    private int origen;
    private int destino;
    
    
    Aristas()
    {
        
    }
    
    protected void CrearArista(int o, int d, int p)
    {
        this.origen=o;
        this.destino=d;
        this.peso=p;
    }
    
    protected int getOrigen()
    {
        return this.origen;
    }
    
    protected int getDestino()
    {
        return this.destino;
    }
    
    protected int getPeso()
    {
        return this.peso;
    }
    
    @Override
    public int compareTo(Object o)
    {
        Aristas AristaTemporal = (Aristas) o;
        if(this.peso<AristaTemporal.getPeso())
            return -1;
        else if(this.peso>AristaTemporal.getPeso())
            return 1;
        return 0;
    }
    
}
