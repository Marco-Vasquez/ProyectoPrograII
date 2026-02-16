/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalprogra2;
import java.util.Scanner;
/**
 *
 * @author andres
 */
public class ProyectoFinalProgra2 {
    static Scanner ingreso=new Scanner(System.in);
    static Battleship juego=new Battleship();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        menuInicio();
    }
    public static int leerEntero(){
        while(true){
            try{
                int numero=Integer.parseInt(ingreso.nextLine());
                return numero;
            }
            catch(NumberFormatException e){
                System.out.println("Ingrese un número válido: ");
            }
        }
    }
    public static void pausar(){
        System.out.println("\nPresione ENTER para continuar");
        ingreso.nextLine();
    }
    public static void menuInicio(){
        while(true){
            System.out.println("\n---------BATTLESHIP DINAMICO---------");
            System.out.println("1. Login");
            System.out.println("2. Crear Player");
            System.out.println("3. Salir");
            System.out.println("Seleccione una opción: ");
            int opcion=leerEntero();
            switch(opcion){
                case 1: 
                    login();
                    break;
                case 2:
                    crearPlayer();
                    break;
                case 3: 
                    System.out.println("Gracias por jugar!");
                    System.exit(0);
                    break;
                default: 
                    System.out.println("Opción inválida");
            }
        }
    }
    public static void login(){
        String username, password;
        System.out.println("\n---LOGIN---");
        while(true){
            System.out.println("Username: ");
            username=ingreso.nextLine();
            if(username.trim().isEmpty()){
                System.out.println("El username no puede estar vacio");
            }
            else{
                break;
            }
        }
        while(true){
            System.out.println("Password: ");
            password=ingreso.nextLine();
            if(password.trim().isEmpty()){
                System.out.println("La password no puede estar vacía");
            }
            else{
                break;
            }
        }
        if(juego.login(username, password)){
            System.out.println("Login existoso, bienvenido "+username+"!");
            menuPrincipal();
        }
        else{
            System.out.println("Username o password incorrectos");
            pausar();
        }
        
    }
    public static void crearPlayer(){
        String username, password;
        System.out.println("\n---CREAR PLAYER---");
        while(true){
            System.out.println("Username: ");
            username=ingreso.nextLine();
            if(username.trim().isEmpty()){
                System.out.println("El username no puede estar vacio");
            }
            else{
                break;
            }
        }
        while(true){
            System.out.println("Password: ");
            password=ingreso.nextLine();
            if(password.trim().isEmpty()){
                System.out.println("La password no puede estar vacía");
            }
            else{
                break;
            }
        }    
        if(juego.crearPlayer(username, password)){
            System.out.println("Player creado existosamente!");
            juego.login(username, password);
            menuPrincipal();
        }
        else{
            System.out.println("No se pudo crear el player (username repetido)");
            pausar();
        }
    }
    public static void iniciarJuego(){
        while(juego.players.size()<2){
            System.out.println("\nSe necesitan al menos dos jugadores para jugar Battleship");
            System.out.println("Desea crear otro jugador? (SI/NO)");
            String respuesta=ingreso.nextLine().toUpperCase();
            if(respuesta.equals("SI")){
                String username, password;
                System.out.println("\n---CREAR JUGADOR---");
                while(true){
                    System.out.println("Username: ");
                    username=ingreso.nextLine();
                    if(username.trim().isEmpty()){
                        System.out.println("El username no puede estar vacio");
                        
                    }
                    else{
                        break;
                    }
                }
                while(true){
                    System.out.println("Password: ");
                    password=ingreso.nextLine();
                    if(password.trim().isEmpty()){
                        System.out.println("La password no puede estar vacía");
                    }
                    else{
                        break;
                    }
                }
                if(juego.crearPlayer(username, password)){
                    System.out.println("Jugador creado exitosamente!");
                }
                else{
                    System.out.println("No se pudo crear al jugador (username repetido)");
                }
                
            }
            else if(respuesta.equals("NO")){
                System.out.println("Regresando al menú principal");
                return;
            }
            else{
                System.out.println("Opción inválida, intente otra vez");
            }
        }
        String usernameJ2;
        System.out.println("\n---INICIAR JUEGO---");
        while(true){
            System.out.println("Ingrese el username del jugador 2 (o escriba EXIT para salir): ");
            usernameJ2=ingreso.nextLine();
            if(usernameJ2.trim().isEmpty()){
                System.out.println("El username no puede estar vacio");
            }
            else{
                break;
            }
        }    
        if(usernameJ2.equalsIgnoreCase("EXIT")){
            System.out.println("Juego cancelado");
            pausar();
            return;
        }
        Player player2=juego.buscarPlayer(usernameJ2);
        if(player2==null){
            System.out.println("El jugador ingresado no existe!");
            pausar();
            return;
        }
        if(player2.getUsername().equals(juego.getUsuarioActual().getUsername())){
            System.out.println("No puedes jugar contra vos mismo");
            pausar();
            return;
            
        }
        System.out.println("Oponente: "+player2.getUsername());
        juego.iniciarTableros();
        juego.colocarBarcos(juego.tablero1, juego.barcosJugador1, juego.getUsuarioActual().getUsername(), ingreso);
        System.out.println("\nCAMBIO DE JUGADOR");
        System.out.println("Turno de "+player2.getUsername());
        pausar();
        juego.colocarBarcos(juego.tablero2, juego.barcosJugador2, player2.getUsername(), ingreso);
        System.out.println("¡QUE COMIENZE EL JUEGO!");
        pausar();
        juego.jugarPartida(player2, ingreso);
    }
    public static void menuPrincipal(){
        while(true){
            if(juego.getUsuarioActual()==null){
                return;
            }
            System.out.println("\n--------------------");
            System.out.println("-  MENÚ PRINCIPAL  -");
            System.out.println("-  Bienvenido "+juego.getUsuarioActual().getUsername()+" -");
            System.out.println("--------------------");
            System.out.println("1. Jugar Battleship");
            System.out.println("2. Configuración");
            System.out.println("3. Reportes");
            System.out.println("4. Mi Perfil");
            System.out.println("5. Salir");
            int opcion=leerEntero();
            switch(opcion){
                case 1: 
                    iniciarJuego();
                    break;
                case 2: 
                    menuConfiguracion();
                    break;
                case 3: 
                    menuReportes();
                    break;
                case 4: 
                    menuPerfil();
                    break;
                case 5:
                    juego.logout();
                    System.out.println("Sesión cerrada!");
                    return;
                default:
                    System.out.println("Opción inválida");
                    
            }
            
        }
    }
    public static void menuConfiguracion(){
        while(true){
            System.out.println("\n---CONFIGURACIÓN---");
            System.out.println("Dificultad actual: "+juego.getDificultad());
            System.out.println("Modo de juego actual: "+juego.getMododeJuego());
            System.out.println("1. Dificultad");
            System.out.println("2. Modo de juego");
            System.out.println("3. Menú Principal");
            int opcion=leerEntero();
            switch(opcion){
                case 1:
                    cambiarDificultad();
                    break;
                case 2: 
                    cambiarModo();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
    public static void cambiarDificultad(){
        System.out.println("\n---DIFICULTAD---");
        System.out.println("1. EASY (5 barcos)");
        System.out.println("2. NORMAL (4 barcos)");
        System.out.println("3. EXPERT (2 barcos)");
        System.out.println("4. GENIUS (1 barco)");
        System.out.println("Seleccione la dificultad: ");
        int opcion=leerEntero();
        switch(opcion){
            case 1:
                juego.setDificultad("EASY"); 
                System.out.println("Dificultad cambiada a: "+juego.getDificultad());
                break;
            case 2:
                juego.setDificultad("NORMAL");
                System.out.println("Dificultad cambiada a: "+juego.getDificultad());
                break;
            case 3: 
                juego.setDificultad("EXPERT");
                System.out.println("Dificultad cambiada a: "+juego.getDificultad());
                break;
            case 4:
                juego.setDificultad("GENIUS");
                System.out.println("Dificultad cambiada a: "+juego.getDificultad());
                break;
            default: 
                System.out.println("Opción inválida");
                break;
        }
        
    }
    public static void cambiarModo(){
        System.out.println("\n---MODO DE JUEGO---");
        System.out.println("1. ARCADE (barcos ocultos)");
        System.out.println("2. TUTORIAL (barcos visibles)");
        System.out.println("Selecione el modo de juego: ");
        int opcion=leerEntero();
        switch(opcion){
            case 1:
                juego.setMododeJuego("ARCADE");
                System.out.println("Modo de juego cambia a: "+juego.getMododeJuego());
                break;
            case 2: 
                juego.setMododeJuego("TUTORIAL");
                System.out.println("Modo de juego cambia a: "+juego.getMododeJuego());
                break;
            default: 
                System.out.println("Opción inválida"); 
                break;
        }
        
    }
    public static void menuReportes(){
        while(true){
            System.out.println("\n---MENÚ REPORTES---");
            System.out.println("1. Mis últimos 10 juegos");
            System.out.println("2. Ranking");
            System.out.println("3. Menú principal");
            int opcion=leerEntero();
            switch(opcion){
                case 1: 
                    juego.getUsuarioActual().mostrarHistorial();
                    break;
                case 2:
                    juego.mostrarRanking();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opción inválida");
                    
            }
        }
    }
    public static void menuPerfil(){
        while(true){
            System.out.println("\n---MENU MI PERFIL---");
            System.out.println("1. Ver mis datos");
            System.out.println("2. Modificar mis datos");
            System.out.println("3. Eliminar mi cuenta");
            System.out.println("4. Menú Principal");
            int opcion=leerEntero();
            switch(opcion){
                case 1:
                    verDatos();
                    break;
                case 2:
                    modificarDatos();
                    break;
                case 3: 
                    if(eliminarCuenta()){
                        return;
                    }
                    break;
                case 4: 
                    return;
                default:
                    System.out.println("Opción inválida");
                    
            }
        }
    }
    public static void verDatos(){
        Player usuario=juego.getUsuarioActual();
        System.out.println("\n---MIS DATOS---");
        System.out.println("Username: "+usuario.getUsername());
        System.out.println("Password: "+usuario.getPassword());
        System.out.println("Puntos: "+usuario.getPuntos());
        System.out.println("Juegos registrados: "+usuario.contarJuegos());
    }
    public static void modificarDatos(){
        String nuevoUsername, nuevaPassword;
        System.out.println("\n---MODIFICAR MIS DATOS---");
        while(true){
            System.out.println("Nuevo username: ");
            nuevoUsername=ingreso.nextLine();
            if(nuevoUsername.trim().isEmpty()){
                System.out.println("El username no puede estar vacio");
            }
            else{
                break;
            }
        }
        while(true){
            System.out.println("Nueva password: ");
            nuevaPassword=ingreso.nextLine();
            if(nuevaPassword.trim().isEmpty()){
                System.out.println("La password no puede estar vacía");
            }
            else{
                break;
            }
        }    
        juego.getUsuarioActual().modificarDatos(nuevoUsername, nuevaPassword);
        System.out.println("Datos modificados correctamente!");
        
    }
    public static boolean eliminarCuenta(){
        System.out.println("\n    ADVERTENCIA    ");
        System.out.println("Estás seguro de querer eliminar tu cuenta? (si/no): ");
        String respuesta=ingreso.nextLine();
        if(respuesta.equalsIgnoreCase("si")){
            juego.eleminarCuentaActual();
            System.out.println("Cuenta eliminada exitosamente!");
            return true;
        }
        else{
            System.out.println("Cancelado");
            return false;
        }
    }
    
}
