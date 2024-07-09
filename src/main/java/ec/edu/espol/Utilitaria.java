package ec.edu.espol;

public class Utilitaria {
    public static void mostrarMensajeBienvenida() {
        System.out.println("*****************************************");
        System.out.println("*                                       *");
        System.out.println("*            ¡Bienvenidos a             *");
        System.out.println("*                  UNO!                 *");
        System.out.println("*                                       *");
        System.out.println("*****************************************");
        System.out.println("        ¡Que comience el juego!");
        System.out.println("*****************************************");

    }
    
    public static void mostrarEstadoDelJuego(Jugador j, Jugador m, Carta ultimaCarta) {
        System.out.println();
        System.out.println("========================================");
        System.out.println(j);
        System.out.println(m);
        System.out.println("Carta en juego: " + ultimaCarta);
        System.out.println("========================================");
        System.out.println();
    }
}
