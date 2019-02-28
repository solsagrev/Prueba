/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

import java.util.Arrays;


/**
 *
 * @author Marco
 */
public class Grafo {
    
    private int [][] adyacencia;    //matriz de adyacencia
    private int nodos;  //número de nodos
    /**
     * Matriz que registra si un nodo está visitado, el nodo anterior, el peso
     * acumulado y las iteraciones necesarias para llegar a ese nodo
     */
    private int[][] tablaD;
    /**
     * Matriz que revela el árbol resultante del método de Prim
     */
    private int[][] prim;
    /**
     * Arreglo para saber qué nodos se consideran en el método de Prim
     */
    private int[] auxP;
    
    /**
     * Constructor que inicializa el tamaño
     * @param num 
     */
    
    private Aristas[] lista;
    
    int [] Raiz = new int[this.nodos];
    
    private int noaristas=0;
    
    Grafo(int num)
    {
        nodos = num;    //número de nodos del grafo
        adyacencia = new int[num][num]; //matriz de adyacencia
        tablaD = new int[num][4];   //tabla para el método de dijkstra
        for(int i=0; i<nodos; i++)      //inicializar la tabla de dijktra
        {
            tablaD[i][0]=0; //pone 0 en visitado
            tablaD[i][1]=0; // y en origen
            tablaD[i][2]=Integer.MAX_VALUE; //pone el valor máximo en el peso
            tablaD[i][3]=Integer.MAX_VALUE; //y las iteraciones
        }
        auxP = new int[nodos];
        prim = new int[nodos][nodos];
        
    }
    /**
     * Método que borra lo guardado en la tabla de Dijkstra para
     * poder usar el método de nuevo con otro nodo de inicio
     */
    protected void ReiniciarDijkstra()
    {
        for(int i=0; i<nodos; i++)//recorre todos los campos
        {
            tablaD[i][0]=0; //pone 0 en visitado
            tablaD[i][1]=0; //pone 0 en origen
            tablaD[i][2]=Integer.MAX_VALUE; //pone el número máximo en el peso
            tablaD[i][3]=Integer.MAX_VALUE; //pone el número máximo en las iteraciones
        }
    }
    
    /**
     * Método para poner un peso en las coordenadas x,y
     * @param x
     * @param y
     * @param peso 
     */
    protected void setMatriz(int x, int y, int peso)
    {
        adyacencia[x][y]=peso;  //pone el peso en la posición x, y de la tabla
        if(x!=y)    //si x es diferente de y
        {  
            adyacencia[y][x]=peso;  //pone el mismo valor en la posición y,x
        }
        noaristas++;
    }
    
    /**
     * Método para obtener el valor guardado en una posición de la matriz
     * @param x
     * @param y
     * @return 
     */
    protected int getMatriz(int x, int y)
    {
        if(x<nodos && y<nodos)
        {
            return adyacencia[x][y];
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * Método para obtener el camino más corto desde el nodo inicio
     * @param inicio
     * @param origen
     * @param peso
     * @param iteracion 
     */
    protected void Dijkstra(int inicio, int origen,int peso, int iteracion)
    {
        this.tablaD[inicio][0]=1;   //marcar el nodo visitado
        //Si el peso actual es menor que el que está en la tabla o
        //si el peso es igual pero las iteraciones son menores, hacer
        if(peso<this.tablaD[inicio][2] || (peso==this.tablaD[inicio][2] && iteracion<this.tablaD[inicio][3]))
        {
            this.tablaD[inicio][1]=origen;  //poner el origen actual
            this.tablaD[inicio][2]=peso;    //poner el peso actual
            this.tablaD[inicio][3]=iteracion;   //poner las iteraciones acutales
        }
        for(int i=0; i<this.nodos; i++) //recorrer todos los nodos
        {
            if(this.tablaD[i][0]!=1 && this.adyacencia[inicio][i]!=0)   //si una conexión entre ambos nodos y el segundo nodo no está visitado aún
            {
                Dijkstra(i, inicio, peso+this.adyacencia[inicio][i], iteracion+1);//Hacer recursión enviando el nuevo nodo, el actual,
                //el peso acumulado más el de la conexión con el nodo nuevo, e incrementar las iteraciones en 1
            }
        }
        this.tablaD[inicio][0]=0;//desmarcar como visitado por si acaso
    }
    
    /**
     * Método para marcar todos los nodos visitados luego de hacer Dijkstra o
     * por cualquier otra razón
     */
    protected void visitarTodo()
    {
        for(int i=0; i<this.nodos; i++) //recorrer todas las posiciones
        {
            this.tablaD[i][0]=1;    //poner en 1 el valor (visitado)
        }
    }
    
    /**
     * Método para marcar todos los nodos como no visitados
     */
    protected void desvisitarTodo()
    {
        for(int i=0; i<this.nodos; i++) //recorrer todas las posiciones
        {
            this.tablaD[i][0]=1;    //poner en 0 el valor (no visitado)
        }
    }
    
    /**
     * Método para obtener si está visitado o no en Dijkstra
     * @param num
     * @return 
     */
    protected int getVis(int num)
    {
        return this.tablaD[num][0];
    }
    
    /**
     * Método para obtener el origen de Dijkstra
     * @param num
     * @return 
     */
    protected int getOri(int num)
    {
        return this.tablaD[num][1];
    }
    
    /**
     * Método para obtener el peso de Dijkstra
     * @param num
     * @return 
     */
    protected int getPeso(int num)
    {
        return this.tablaD[num][2];
    }
    
    /**
     * Método para obtener las iteraciones de Dijkstra
     * @param num
     * @return 
     */
    protected int getIte(int num)
    {
        return this.tablaD[num][3];
    }
    
    /**
     * Método para obtener el camino más corto desde un nodo inicio hasta
     * el nodo final aquí obtenido
     * @param fin
     * @return 
     */
    protected String getCamino(int fin)
    {
        int nuevo=fin;    //el nuevo nodo que se agrega al camino
        String regresar=""; //cadena con el camino final   
        if(tablaD[fin][2]!=Integer.MAX_VALUE)
        {
            do//hacer
            {
                nuevo=tablaD[nuevo][1];  //obtener el nodo origen del nodo final
                if(nuevo!=-1)//si no es el nodo inicial, hacer
                    regresar=((char)(nuevo+65))+", "+regresar;  //guardar el nodo final
                //en la cadena con el camino, convirtiéndolo en un caracter y con
                //una coma al final
            }while(nuevo!=-1);//mientras el nodo nuevo no sea el nodo de inicio
        }
        else
            regresar="No hay camino";
       
        return regresar;    //regresar la cadena con el camino final
    }
    
    /**
     * Método para saber si todos los nodos han sido visitados durante
     * el algoritmo de Prim
     * @return 
     */
    private boolean TodoVisitado()
    {
        for(int i=0; i<nodos; i++)
        {
            if(this.auxP[i]==0)
                return false;
        }
        return true;
    }
    
    /**
     * Método para borrar los datos guardados en la matriz de Prim y en el
     * vector auxiliar 
     */
    protected void reiniciarPrim()
    {
        for(int i=0; i<nodos; i++)
        {
            this.auxP[i]=0;
            for(int j=0; j<nodos; j++)
                this.prim[i][j]=0;
        }
    }
    
    /**
     * Método para saber si el nodo pos ya fue visitado en el algoritmo
     * de Prim
     * @param pos
     * @return 
     */
    private boolean Visitado(int pos)
    {
        return this.auxP[pos]==1;
    }
    
    /**
     * Método que crea el árbol de Prim a partir del nodo inicial
     * @param inicio 
     * @return regresar
     */
    protected String Prim(int inicio)
    {
        String regresar="";
        this.auxP[inicio]=1;
        int menor, xmenor, ymenor, peso=0;
        while(!TodoVisitado())//mientras aún haya nodos por visitar
        {
            menor=Integer.MAX_VALUE;//inicializar con un valor máximo
            xmenor=inicio;  //poner el valor de las coordenadas...
            ymenor=inicio;  //del nodo de inicio
            for(int i=0; i<nodos; i++)//recorre todos los nodos
            {
                if(Visitado(i)) //si el nodo se está considerando para Prim, hacer
                {
                    for(int j=0; j<nodos; j++)  //recorrer todos los nodos
                    {
                        //Si hay arista entre dos nodos, es la arista más corta a considerar
                        //y el nodo no ha sido visitado, hacer
                        if(adyacencia[i][j]!=0 && adyacencia[i][j]<menor && !Visitado(j))  
                        {
                            menor=this.adyacencia[i][j];  //guardar el valor de la arista
                            xmenor=i;   //guardar la posición x
                            ymenor=j;   //guardar la posición y
                        }
                    }//fin del recorrido de todos los arcos de cada nodo
                    //para cada nodo considerado en Prim
                }//fin de condición
            }//fin del recorrido de todos los nodos considerados para obtener el
            //arco más corto para armar el árbol
            this.auxP[ymenor]=1;//marcar el nodo visitado
            this.prim[xmenor][ymenor]=menor; //poner en la tabla de prim, en las
            //coordenadas de donde obtuve el arista menor, el valor de la arista
            this.prim[ymenor][xmenor]=menor;//y también en la posición y,x
            //Guardar en una cadena una nueva línea con el peso menor entre
            //dos nodos y su peso
            regresar=regresar+"El camino más corto entre "+(char)(xmenor+65)+" y "+(char)(ymenor+65)+" es: "+menor+"\n";
            peso=peso+menor;    //acumular el peso del árbol    
        }//fin de while
        regresar = regresar + "El peso del árbol de Prim es: "+peso;//agregar el peso al final de la cadena
        return regresar;    //regresar la cadena
    }
    
    /**
     * Obtener el valor del árbol de Prim
     * @param x
     * @param y
     * @return 
     */
    protected int getArbol(int x, int y)
    {
        return this.prim[x][y];
    }
    
    private boolean RaicesIguales()
    {
        for(int i=1; i<this.nodos; i++)
        {
            if(Raiz[i]!=Raiz[i-1])
                return false;
        }
        return true;
    }
    
    private void CambiarRaiz(int cambiar, int poresta)
    {
        for(int i=0; i<this.nodos; i++)
        {
            if(Raiz[i]==cambiar)
                Raiz[i]=poresta;
        }
    }
    
    protected String Kruskal()
    {
        lista = new Aristas[nodos*nodos];
        for(int i=0; i< this. nodos; i++)
        {
            Raiz[i]=i;
        }
        int aristas=0;  //número de arista donde se guarda
        for(int i=1; i<this.nodos; i++) //recorrer los nodos
        {
            for(int j=(i+1); j<this.nodos; j++) //recorrer todos los nodos con los que se conecta
            {
                this.lista[aristas].CrearArista(i, j, this.adyacencia[i][j]);   //crea un arista con origen i, destino j, de peso adyacencia
                aristas++;  //aumenta el índice del arreglo de aristas
            }
        }//fin de creación de aristas
        
        Arrays.sort(lista, 0, lista.length);    //Ordenar las aristas por peso
        
        int i=0;    //índice para recorrer la lista de aristas
        int peso=0;
        while(!RaicesIguales()) //repetir mientras haya raíces diferentes
        {
            if(Raiz[lista[i].getOrigen()] != Raiz[lista[i].getDestino()])//si tienen raíces diferentes
            {
                CambiarRaiz(Raiz[lista[i].getOrigen()], Raiz[lista[i].getDestino()]);       //Cambiar la raíz del origen por la raíz del destino
                this.prim[lista[i].getOrigen()][lista[i].getDestino()]=lista[i].getPeso();  //guardar la arista en la matriz
                this.prim[lista[i].getDestino()][lista[i].getOrigen()]=lista[i].getPeso();  //en ambas posiciones (espejo)
                peso=peso+lista[i].getPeso();
            }
            i++;    //pasar a la siguiente arista
        }
        
        
        
        
        return "";
    }
    
}
