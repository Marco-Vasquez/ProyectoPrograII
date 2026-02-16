/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalprogra2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author andres
 */
public class Battleship {
    protected ArrayList<Player> players;
    protected Player usuarioActual;
    protected String dificultad;
    protected String modoJuego;
    public String tablero1[][];
    public String tablero2[][];
    public ArrayList<Barco> barcosJugador1;
    public ArrayList<Barco> barcosJugador2;
    
    public Battleship(){
        this.players=new ArrayList<>();
        this.usuarioActual=null;
        this.dificultad="NORMAL";
        this.modoJuego="TUTORIAL";
        this.tablero1=new String[8][8];
        this.tablero2=new String[8][8];
        this.barcosJugador1=new ArrayList<>();
        this.barcosJugador2=new ArrayList<>();
    }
    public Player getUsuarioActual(){
        return usuarioActual;
    }
    public void setDificultad(String dificultad){
        this.dificultad=dificultad;
        
    }
    public String getDificultad(){
        return dificultad;
    }
    public void setMododeJuego(String modo){
        this.modoJuego=modo;
    }
    public String getMododeJuego(){
        return modoJuego;
    }
    public boolean crearPlayer(String username, String password){
        for(Player control:players){
            if(control.getUsername().equalsIgnoreCase(username)){
                return false;
            }
            
        }
        if(username.trim().isEmpty() || password.trim().isEmpty()){
            return false;
        }
        Player nuevoPlayer=new Player(username, password);
        players.add(nuevoPlayer);
        return true;
    }
    public boolean login(String username, String password){
        for(Player control:players){
            if(control.getUsername().equalsIgnoreCase(username) && control.getPassword().equals(password)){
                usuarioActual=control;
                return true;
            }
        }
        return false;
    }
    public void logout(){
        usuarioActual=null;
        
    }
    public Player buscarPlayer(String username){
        for(Player control:players){
            if(control.getUsername().equalsIgnoreCase(username)){
                return control;
            }
        }
        return null;
    }
    public boolean eleminarCuentaActual(){
        if(usuarioActual!=null){
            players.remove(usuarioActual);
            usuarioActual=null;
            return true;
        }
        return false;
    }
    public int getCantidadBarcos(){
        switch(dificultad){
            case "EASY": return 5;
            case "NORMAL": return 4;
            case "EXPERT": return 2;
            case "GENIUS": return 1;
            default: return 4;
        }
    }
    public ArrayList<Player> getRanking(){
        ArrayList<Player> ranking=new ArrayList<>(players);
        for(int control=0;control<ranking.size()-1;control++){
            for(int controlador=0;controlador<ranking.size()-1-control;controlador++){
                if(ranking.get(controlador).getPuntos()<ranking.get(controlador+1).getPuntos()){
                    Player temporal=ranking.get(controlador);
                    ranking.set(controlador, ranking.get(controlador+1));
                    ranking.set(controlador+1, temporal);
                }
            }
        }
        return ranking;
    }
    public void mostrarRanking(){
        ArrayList<Player> ranking=getRanking();
        System.out.println("\n----RANKING DE LOS JUGADORES----");
        System.out.println("Posición   |   Username   |   Puntos");
        System.out.println("--------------------------------------");
        int posicion=1;
        for(Player jugador:ranking){
            System.out.println(posicion+"          |   "+jugador.getUsername()+"     |   "+jugador.getPuntos());
            posicion++;
        }
        System.out.println("--------------------------------------\n");
    }
    public boolean existeJugador(String username){
        return buscarPlayer(username)!=null;
    }
    public void iniciarTableros(){
        for(int filas=0;filas<8;filas++){
            for(int columnas=0;columnas<8;columnas++){
                tablero1[filas][columnas]="-";
                tablero2[filas][columnas]="-";
            }
        }
        barcosJugador1.clear();
        barcosJugador2.clear();
    }
    public void mostrarTablero(String[][] tablero, boolean ocultarBarcos){
        System.out.println("\n  0 1 2 3 4 5 6 7");
        for(int filas=0;filas<8;filas++){
            System.out.print(filas+" ");
            for(int columnas=0;columnas<8;columnas++){
                String casilla=tablero[filas][columnas];
                if(ocultarBarcos && esBarco(casilla)){
                    System.out.printf("%-2s","-");
                    
                }
                else{
                    System.out.printf("%-2s", casilla);
                }
                
            }
            System.out.println();
        }
        System.out.println();
    }
    private boolean esBarco(String casilla){
        return casilla.equals("PA") || casilla.equals("AZ") || casilla.equals("SM") || casilla.equals("DT");
    }    
    public boolean puedeColocar(String[][] tablero,int fila,int columna, int size, boolean vertical){
        if(vertical){
            if(fila+size>8)
                return false;
        }
        else{
            if(columna+size>8)
                return false;
        }
        for(int control=0;control<size;control++){
            int filas, columnas;
            filas=vertical ? fila+control:fila;
            columnas=vertical ? columna:columna+control;
            if(!tablero[filas][columnas].equals("-")){
                return false;
            }
        }
        return true;
    }
    public void colocarBarco(String[][] tablero, Barco barco, int fila, int columna, boolean vertical){
        barco.setFila(fila);
        barco.setColumna(columna);
        barco.setVertical(vertical);
        for(int control=0;control<barco.getSize();control++){
            int filas, columnas;
            filas=vertical ? fila+control:fila;
            columnas=vertical ? columna:columna+control;
            tablero[filas][columnas]=barco.getCodigo();
        }
    }
    public boolean existeBarco(ArrayList<Barco> barcos, String tipo){
        for(Barco barco:barcos){
            if(barco.getTipo().equals(tipo)){
                return true;
            }
        }
        return false;
    }
    public Barco buscarPosicionBarco(ArrayList<Barco> barcos, int fila, int columna){
        for (Barco barco:barcos){
            int size=barco.getSize();
            boolean vertical=barco.getEsVertical();
            for(int control=0;control<size;control++){
                int filas, columnas;
                filas=vertical ? barco.getFila()+control:barco.getFila();
                columnas=vertical ? barco.getColumna():barco.getColumna()+control;
                if(filas==fila && columnas==columna){
                    return barco;
                }
            }
        }
        return null;
    }
    public int contarBarcosVivos(ArrayList<Barco> barcos){
        int vivos=0;
        for(Barco control:barcos){
            if(!control.barcoHundido())
                vivos++;
        }
        return vivos;
    }
    public int leerEnteroValido(Scanner ingreso, int minimo, int maximo){
        while(true){
            try{
                int numero=Integer.parseInt(ingreso.nextLine());
                if(numero>=minimo && numero<=maximo){
                    return numero;
                }
                System.out.println("El valor debe estar entre "+minimo+" y "+maximo+": ");
            }
            catch(NumberFormatException e){
                System.out.println("Ingrese un número válido");
            }
        }
    }
    public void colocarBarcos(String[][] tablero, ArrayList<Barco> barcos, String nombrePlayer, Scanner ingreso){
        System.out.println("\n---COLOCACIÓN DE BARCOS---");
        System.out.println("--- TURNO DE "+nombrePlayer+" ---");
        int cantidadBarcos=getCantidadBarcos();
        System.out.println("Debes de colocar "+cantidadBarcos+" barcos");
        System.out.println("Los tipos de barcos y sus tamaños son: PA(5), AZ(4), SM(3), DT(2)");
        int barcosColocados=0;
        while(barcosColocados<cantidadBarcos){
            System.out.println("\nBarco "+(barcosColocados+1)+" de "+cantidadBarcos);
            mostrarTablero(tablero, false);
            String tipo;
            System.out.println("Ingresa el tipo de barco que quieres colocar (PA, AZ, SM, DT): ");
            tipo=ingreso.nextLine().toUpperCase();
            if(!tipo.equals("PA") && !tipo.equals("AZ") && !tipo.equals("SM") && !tipo.equals("DT")){
                System.out.println("Tipo de barco inválido");
                continue;
            }
            if(existeBarco(barcos, tipo)){
                if(!(dificultad.equals("EASY") && tipo.equals("DT"))){
                    System.out.println("Ya tienes un barco de ese tipo");
                    continue;
                }
            }
            Barco newBarco=new Barco(tipo);
            int fila, columna;
            System.out.println("Fila inicial (0-7): ");
            fila=leerEnteroValido(ingreso, 0, 7);
            System.out.println("Columna inicial (0-7): ");
            columna=leerEnteroValido(ingreso, 0, 7);
            String orientacion;
            boolean vertical;
            while(true){
                System.out.println("Desea colocarlo de forma vertical? (SI/NO): ");
                orientacion=ingreso.nextLine().toUpperCase();
                if(orientacion.equals("SI")){
                    vertical=true;
                    break;
                }
                else if(orientacion.equals("NO")){
                    vertical=false;
                    break;
                }
                else{
                    System.out.println("Respuesta inválida, escriba SI o NO");
                }
            }    
            if(!puedeColocar(tablero, fila, columna, newBarco.getSize(), vertical)){
                System.out.println("No puedes colocar el barco ahi!");
                continue;
            }
            colocarBarco(tablero, newBarco, fila, columna, vertical);
            barcos.add(newBarco);
            barcosColocados++;
            System.out.println("Barco colocado!");
        }
            
        System.out.println("Todos los barcos han sido colocados correctamente!");
        mostrarTablero(tablero, false);
    }
    public void regenerarTablero(String[][] tablero, ArrayList<Barco> barcos){
        for(int filas=0;filas<8;filas++){
            for(int columnas=0;columnas<8;columnas++){
                tablero[filas][columnas]="-";
            }
        }
        for(Barco barco:barcos){
            if(!barco.barcoHundido()){
                boolean colocado=false;
                int intentos=0;
                while(!colocado && intentos<100){
                    int fila=(int)(Math.random()*8);
                    int columna=(int)(Math.random()*8);
                    boolean vertical=Math.random()<0.5;
                    if(puedeColocar(tablero, fila, columna, barco.getSize(), vertical)){
                        colocarBarco(tablero, barco, fila, columna, vertical);
                        colocado=true;
                    }
                    intentos++;
                }
            }
        }
        System.out.println("Tablero regenerado correctamente!");
    }
    public void jugarPartida(Player player2, Scanner ingreso){
        boolean juegoActivo=true, turnoPlayer1=true;
        while(juegoActivo){
            String nombreActual;
            String[][] tableroEnemigo;
            ArrayList<Barco> barcosEnemigos;
            nombreActual=turnoPlayer1 ? usuarioActual.getUsername():player2.getUsername();
            tableroEnemigo=turnoPlayer1 ? tablero2:tablero1;
            barcosEnemigos=turnoPlayer1 ? barcosJugador2:barcosJugador1;
            System.out.println("\n-----TURNO DE "+nombreActual+" para bombardear-----");
            int barcosEnemigosVivos=contarBarcosVivos(barcosEnemigos);
            System.out.println("Barcos enemigos restantes: "+barcosEnemigosVivos);
            boolean ocultarBarcos=modoJuego.equals("ARCADE");
            mostrarTablero(tableroEnemigo, ocultarBarcos);
            System.out.println("Ingresa -1 en ambas fila y columna si deseas rendirte");
            System.out.println("Fila para bombardear (0-7): ");
            int fila=leerEnteroValido(ingreso, -1, 7);
            if(fila==-1){
                System.out.println("Columna para bombardear (0-7): ");
                int columna=leerEnteroValido(ingreso, -1, 7);
                if(columna==-1){
                    while(true){
                        String confirmacion;
                        System.out.println("Estas seguro de querer rendirte? (SI/NO): ");
                        confirmacion=ingreso.nextLine().toUpperCase();
                        if(confirmacion.equals("SI")){
                            String ganador, perdedor;
                            ganador=turnoPlayer1 ? player2.getUsername():usuarioActual.getUsername();
                            perdedor=turnoPlayer1 ? usuarioActual.getUsername():player2.getUsername();
                            System.out.println("El juador "+ganador+" gana por retiro del oponente!!!");
                            Player jugadorGanador;
                            jugadorGanador=turnoPlayer1 ? player2:usuarioActual;
                            jugadorGanador.agregarPuntos(3);
                            String resultado;
                            resultado=perdedor+" se retiro dejando a "+ganador+" como ganador en el modo "+dificultad+".";
                            usuarioActual.agregarHistorialJuegos(resultado);
                            player2.agregarHistorialJuegos(resultado);
                            return;
                        }
                        else if(confirmacion.equals("NO")){
                            break;
                        }
                        else{
                            System.out.println("Respuesta inválida, escriba SI o NO");
                        }
                    }    
                    continue;
                }    
            }
            System.out.println("Columna para bombardear (0-7): ");
            int columna=leerEnteroValido(ingreso, 0, 7);
            if(fila<0 || fila>7 || columna<0 || columna>7){
                System.out.println("Cordenadas inválidas");
                continue;
            }
            String celda;
            celda=tableroEnemigo[fila][columna];
            if(celda.equals("-")){
                System.out.println("Has fallado el tiro!");
                tableroEnemigo[fila][columna]="F";
                mostrarTablero(tableroEnemigo, ocultarBarcos);
                System.out.println("\nPresiona ENTER para continuar");
                ingreso.nextLine();
                tableroEnemigo[fila][columna]="-";
                
            }
            
            else{
                System.out.println("Has acertado! Le diste a un "+celda+"!");
                tableroEnemigo[fila][columna]="X";
                Barco barcoGolpeado;
                barcoGolpeado=buscarPosicionBarco(barcosEnemigos, fila, columna);
                if(barcoGolpeado!=null){
                    barcoGolpeado.recibirDaño();
                    tableroEnemigo[fila][columna]="X";
                    mostrarTablero(tableroEnemigo, ocultarBarcos);
                    if(barcoGolpeado.barcoHundido()){
                        System.out.println("El barco "+barcoGolpeado.getNombreBarcos().toUpperCase()+" ha sido hundido!!!");
                        
                    }
                    System.out.println("Presiona enter para continuar");
                    ingreso.nextLine();
                    if(contarBarcosVivos(barcosEnemigos)>0){
                        regenerarTablero(tableroEnemigo, barcosEnemigos);
                    }
                }
            }
            if(contarBarcosVivos(barcosEnemigos)==0){
                String ganador=nombreActual;
                String perdedor;
                perdedor=turnoPlayer1 ? player2.getUsername():usuarioActual.getUsername();
                System.out.println("--- "+ganador+" GANA ---");
                Player jugadorGanador;
                jugadorGanador=turnoPlayer1 ? usuarioActual:player2;
                jugadorGanador.agregarPuntos(3);
                String resultado=ganador+" hundió todos los barcos de "+perdedor+" en modo "+dificultad+".";
                usuarioActual.agregarHistorialJuegos(resultado);
                player2.agregarHistorialJuegos(resultado);
                return;
            }
            turnoPlayer1=!turnoPlayer1;
            
        }
    }
    
}
