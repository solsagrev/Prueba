/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

import java.util.Scanner;

/**
 *
 * @author Marco Antonio Ornelas Sandoval y Ricardo Fernandez Martínez
 */
public class Grafos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int nodos;
        do
        {
            System.out.println("Escribe el número de nodos:  ");    //pedir la cantidad de nodos
            nodos = entradaInt();
            if(nodos<2)
                System.out.println("Debe haber por lo menos dos nodos");
        }while(nodos<2);
        
        Grafo este = new Grafo(nodos);
        System.out.println("Llenado de la matriz de adyacencia:");
        char nodo1 = 'A';
        char nodo2 = 'B';
        int rel=0, mayor=0;
        for(int i=0; i<nodos; i++)
        {
            for(int j = i+1; j<nodos; j++)
            {
                do
                {
                    System.out.println("¿Hay relación entre el nodo "+nodo1+" y el nodo "+nodo2+" ? 1: Sí 2: No  ");
                    rel = entradaInt();
                    if(rel!=1 && rel!=2)
                        System.out.println("Opción no válida. Vuelve a intentar");
                }while(rel!=1 && rel!=2);
                
                if(rel==1)
                {
                    int peso;
                    do
                    {
                        System.out.println("Escribe el peso: ");
                        peso=entradaInt();
                        if(peso==0)
                            System.out.println("El peso no puede ser 0");
                    }while(peso==0);
                    if(peso>mayor)
                        mayor=peso;
                    este.setMatriz(i, j, peso);
                }
                else
                {
                    este.setMatriz(i, j, 0);
                }
                nodo2++;
            }//fin del for interno
            nodo1++;
            nodo2=(char) (nodo1+1);
        }//fin de for
        
        //Imprimir la matriz de adyacencia
        System.out.println("Matriz de adyacencia:");
        nodo1 = 'A';//guarda el caracter A
        System.out.print("\t");
        for(int i=0; i<nodos; i++)
        {
            System.out.print("\t "+nodo1);//imprime todos los nodos desde la A
            nodo1++;    //aumenta al siguiente nodo
        }
        System.out.println("");
        nodo1='A';  //guarda el caracter A
        for(int i = 0; i<nodos; i++)//imprimir la matriz de adyacencia, recorre las columnas
        {
            System.out.print("\t"+nodo1);    //imprime la letra
            
            for(int j = 0; j<nodos; j++)    //recorre una fila
            {
                System.out.print("\t["+este.getMatriz(i, j)+"]");//imprime el valor en la posicion i,j
                
            }
            
            System.out.println("");//salto de linea
            nodo1++;    //aumenta el nodo
        }//fin de for
        
        
        
        int opcion=0;
        do
        {
            System.out.println("--M E N Ú--");
            System.out.println("1. Dijkstra");
            System.out.println("2. Prim");
            System.out.println("5. Salir");
            System.out.println("Elige una opcion -> ");
            opcion=entradaInt();
            switch(opcion)//elegir una opcion del menú
            {
                case 1://opcion 1: dijkstra
                    
                    int inicio;
                    String aux;
                    do
                    {
                        inicio=0;
                        System.out.println("Escribe el nodo de inicio para el recorrido de Dijkstra");
                        for(int i=0; i<nodos; i++)
                            System.out.print(" "+(char)(i+65));//muestra la lista de nodos disponibles
                        System.out.println("");
                        aux = EntradaCharM(nodos);
                        for(int j=0; j<aux.length(); j++)
                            inicio = inicio+aux.charAt(j);   //guarda el nodo de inicio
                        if(inicio>=65 && inicio<(65+nodos))  //si el nodo es una mayúscula, hacer
                            este.Dijkstra(inicio-65, -1, 0, 0); //método de dijkstra desde A
                        else    //si no
                            System.out.println("El nodo no está en la lista");
                    }while(!(inicio>=65 && inicio<(65+nodos)));
                    
                    int fin;
                    do
                    {
                        System.out.println("Elige el nodo de fin para el recorrido de Dijkstra");
                        fin=0;
                        for(int i=0; i<nodos; i++)
                        {
                            if((i+65)!=inicio)
                                System.out.print(" "+(char)(i+65));//muestra la lista de nodos disponibles
                        }
                        System.out.println("");
                            
                        aux = EntradaCharM(nodos);   //guarda la cadena con el nodo de inicio
                        for(int j=0; j<aux.length(); j++)
                            fin = fin+aux.charAt(j);   //guarda el nodo de inicio
                        
                        if(fin>=65 && fin<(65+nodos) && fin!=inicio)  //si el nodo es una mayúscula, hacer
                        {
                            String camino=este.getCamino(fin-65);
                            if(camino.equals("No hay camino"))
                            {
                                System.out.println(camino);
                            }
                            else
                            {
                                System.out.println("\nCamino más corto entre "+(char)inicio+" y "+(char)fin+": "+este.getCamino(fin-65)+" "+(char)fin);
                                System.out.println("Con un peso de: "+este.getPeso(fin-65));
                            }
                        }
                        else    //si no
                            System.out.println("El nodo no está en la lista");
                        
                        if(inicio==fin)//si es igual al de inicio
                            System.out.println("El nodo de inicio no puede ser igual al de fin");
                    }while(!(fin>=65 && fin<(65+nodos) && fin!=inicio));
                    /*Imprimir la matriz de Dijkstra
                    System.out.println("Matriz de dijkstra:");
                    este.visitarTodo();
                    //visitado, origen, peso e iteraciones
                    System.out.println("     V     O     P     I");

                    System.out.println("");
                    nodo1='A';
                    for(int r = 0; r<este.nodos; r++)//imprimir la matriz de Dijkstra, recorre las columnas
                    {
                        System.out.print(" "+nodo1+" ");    //imprime la letra

                        System.out.println(" [ "
                                +este.getVis(r) 
                                +" ] [ "+(char)(este.getOri(r)+65)+" ] [ "
                                +este.getPeso(r)+" ] [ "
                                +este.getIte(r)+" ]");
                        //imprime si está visitado, el nodo origen, el peso y las iteraciones para llegar
                        System.out.println("");
                        nodo1++;    //aumentar la letra
                    }//fin de for
                    */
                    este.ReiniciarDijkstra();
                    break;//fin del caso 1
                case 2:
                    do
                    {
                        System.out.println("Elige el nodo de inicio para el árbol de Prim");
                        for(int i=0; i<nodos; i++)
                            System.out.print(" "+(char)(i+65));//muestra la lista de nodos
                        System.out.println("");
                        inicio=0;
                        aux = EntradaCharM(nodos);   //guarda el nodo de inicio
                        for(int j=0; j<aux.length(); j++)
                            inicio = inicio+aux.charAt(j);   //guarda el nodo de inicio
                        if(inicio>=65 && inicio<(65+nodos))  //si el nodo es una mayúscula, hacer
                            System.out.println(este.Prim(inicio-65));
                        else    //si no
                            System.out.println("El nodo no está en la lista");
                    }while(!(inicio>=65 && inicio<(65+nodos)));
                    //Imprimir la matriz de adyacencia
                    System.out.println("Árbol de Prim:");
                    nodo1 = 'A';//guarda el caracter A
                    for(int i=0; i<nodos; i++)
                    {
                        System.out.print("\t "+nodo1);//imprime todos los nodos desde la A
                        nodo1++;    //aumenta al siguiente nodo
                    }
                    System.out.println("");
                    nodo1='A';  //guarda el caracter A
                    for(int i = 0; i<nodos; i++)//imprimir la matriz de adyacencia, recorre las columnas
                    {
                        System.out.print(nodo1);    //imprime la letra
                        for(int j = 0; j<nodos; j++)    //recorre una fila
                        {
                            System.out.print("\t["+este.getArbol(i, j)+"]");//imprime el valor en la posicion i,j
                        }
                        System.out.println("");//salto de linea
                        nodo1++;    //aumenta el nodo
                    }//fin de for
                    este.reiniciarPrim();
                    break;//fin del caso de Prim
                case 5:
                    System.out.println("Saliendo");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }while(opcion!=5);
        System.out.println("\n\n          Hecho por:");
        System.out.println("  Marco Antonio Ornelas Sandoval");
        System.out.println("    Ricardo Fernández Martínez");
        System.out.println("Seminario de solución de problemas\n   de estructura de datos II");
        System.out.println("          19/02/2019");
        
    }
 
    /**
     * Método para la entrada de números enteros
     * @return 
     */
    private static int entradaInt()
    {
        Scanner entrada = new Scanner(System.in);//objeto escaner
        int regresar;   //numero a regresar
        do
        {
            try //intentar
            {
                regresar = entrada.nextInt();   //leer un entero
            }
            catch(java.util.InputMismatchException e)  //detener una excepcion y
            {
                entrada.next();   //poner el valor en 0
                regresar=0;
            }
            if(regresar<1)
                System.out.println("Número incorrecto. Vuelve a intentar");
            
                
        }while(regresar<1);
        
        return regresar;    //regresar el número obtenido
    }
    
    /**
     * Método para la entrada de una letra Mayúscula
     * @param max
     * @return 
     */
    private static String EntradaCharM(int max)
    {
        Scanner entrada = new Scanner (System.in); //objeto escáner
        String regresar=""; //cadena para guardar la entrada
        do
        {
            try //intentar
            {
                regresar = entrada.next();  //leer un text
            }
            catch(Exception e)  //detener una excepción
            {
                entrada.next();
                regresar="";
                System.out.println("Vuelve a intentar");
            }
        }while(regresar.equals(""));
        
        return regresar;
    }
    
}
