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
public class Player {
        
    private String username;
    private String password;
    private int puntos;
    private String[] historialJuegos;

    public Player(String username, String password){
        this.username=username;
        this.password=password;
        this.puntos=0;
        this.historialJuegos=new String[10];
    }
    public String getUsername(){
        return username;
       
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public int getPuntos(){
        return puntos;
    }
    public void setPuntos(int puntos){
        this.puntos=puntos;
    }
    public String[] getHistorialJuegos(){
        return historialJuegos;
    }
    public void agregarHistorialJuegos(String resultado){
        String[] nuevoHistorial=new String[10];
        nuevoHistorial[0]=resultado;
        int control=1;
        for(String juego:historialJuegos){
            if(juego!=null && control<10){
                nuevoHistorial[control]=juego;
                control++;
            }
        }
        historialJuegos=nuevoHistorial;
    }
    public void agregarPuntos(int puntos){
        this.puntos+=puntos;
    }
    public void mostrarHistorial(){
        System.out.println("\n---HISTORIAL DE JUEGOS---\n");
        int control=1;
        for (String juego:historialJuegos){
            if(juego!=null){
                System.out.println(control+"- "+juego);
            }
            else{
                System.out.println(control+"-");
            }
            control++;
        }
    }
    public int contarJuegos(){
        int contador=0;
        for(String juego:historialJuegos){
            if(juego!=null){
                contador++;
            }
            
        }
        return contador;
    }
    public String toString(){
        return "Player: "+username+" | Puntos: "+puntos;
    }
    public boolean validarPassword(String intentoPassword){
        return this.password.equals(intentoPassword);
    }
    public void modificarDatos(String nuevoUsername, String nuevoPassword){
        this.username=nuevoUsername;
        this.password=nuevoPassword;
    }
    
    
}
