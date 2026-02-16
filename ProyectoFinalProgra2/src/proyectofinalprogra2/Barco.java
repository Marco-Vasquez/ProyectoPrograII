/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalprogra2;

/**
 *
 * @author andres
 */
public class Barco {
    private String tipo;
    private String codigo;
    private int size;
    private int vidasRestantes;
    private int fila;
    private int columna;
    private boolean esVertical;
    public Barco(String tipo){
        this.tipo=tipo;
        this.codigo=tipo;
        switch(tipo){
            case "PA": 
                this.size=5; 
                break;
            case "AZ":
                this.size=4;
                break;
            case "SM":
                this.size=3;
                break;
            case "DT":
                this.size=2;
                break;
            default:
                this.size=2;
        }
        this.vidasRestantes=this.size;
        this.fila=-1;
        this.columna=-1;
        this.esVertical=false;
        
    }
    public String getTipo(){
        return tipo;
    }
    public String getCodigo(){
        return codigo;
    }
    public int getSize(){
        return size;
    }
    public int getVidas(){
        return vidasRestantes;
    }
    public int getFila(){
        return fila;
    }
    public int getColumna(){
        return columna;
    }
    public boolean getEsVertical(){
        return esVertical;
    }
    public void setFila(int fila){
        this.fila=fila;
    }
    public void setColumna(int columna){
        this.columna=columna;
    }
    public void setVertical(boolean vertical){
        this.esVertical=vertical;
    }
    public void recibirDaño(){
        if(vidasRestantes>0)
            vidasRestantes--;
    }
    public boolean barcoHundido(){
        return vidasRestantes<=0;
    }
    public String getNombreBarcos(){
        switch(tipo){
            case "PA": 
                return "Portaaviones";
            case "AZ":
                return "Acorazado";
            case "SM":
                return "Submarino";
            case "DT": 
                return "Destructor";
            default:
                return "Barco";
        }
    }
    public String toString(){
        return getNombreBarcos()+" ("+codigo+") - Vidas: "+vidasRestantes+"/"+size;
    }
}
