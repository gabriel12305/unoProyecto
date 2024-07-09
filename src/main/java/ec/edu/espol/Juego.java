package ec.edu.espol;
import java.util.ArrayList;
import java.util.Scanner;
public class Juego {
    private static Carta ultimaCarta;
    
    public static void iniciarJuego() {
        Baraja baraja = new Baraja();
        Jugador jugador = new Jugador("Jugador", baraja.crearMano());
        Maquina maquina = new Maquina("Máquina", baraja.crearMano());
        ultimaCarta = baraja.robarCarta();

        while (ultimaCarta != null && (ultimaCarta.getTipo().equals("^") || ultimaCarta.getTipo().equals("%N")  || ultimaCarta.getTipo().equals("+2") || ultimaCarta.getTipo().equals("+4") || ultimaCarta.getTipo().equals("&"))) {
            baraja.getBaraja().add(ultimaCarta);
            ultimaCarta = baraja.robarCarta();
        }

        if (ultimaCarta == null) {
            System.out.println("No hay suficientes cartas para iniciar el juego.");
            return;
        }

        int turno = 1;
        Scanner sc = new Scanner(System.in);
        while (jugador.getMano().size() != 0 && maquina.getMano().size() != 0 && (baraja.getBaraja().size() != 0 || (baraja.getBaraja().size() == 0 && jugador.verificarExistenciaCarta(ultimaCarta) && maquina.verificarExistenciaCarta(ultimaCarta)))) {

            Utilitaria.mostrarEstadoDelJuego(jugador, maquina, ultimaCarta);

            if (turno == 1) {
                if (jugador.verificarExistenciaCarta(ultimaCarta)) {
                    Carta cartaRemovida = null;
                    while (cartaRemovida == null) {
                        int indice;
                        do {
                            System.out.println("Ingrese el índice de la carta que desea jugar: ");
                            indice = sc.nextInt();
                            sc.nextLine();
                            if (indice < 0 || indice >= jugador.getMano().size()) {
                                System.out.println("Índice fuera de rango. Intente nuevamente.");
                            }
                        } while (indice < 0 || indice >= jugador.getMano().size());
                        cartaRemovida = jugador.removerCartaMano(indice, ultimaCarta);
                        if (cartaRemovida == null) {
                            System.out.println("No se puede tirar esta carta, intente con otra.");
                        }
                    }

                    // Efectos de la carta
                    if (cartaRemovida.getTipo().equals("+2")) {
                        ArrayList<Carta> cartas = new ArrayList<>();
                        for(int i = 0; i < 2; i++){
                            Carta carta1 = baraja.robarCarta();
                            if(carta1 != null){
                                maquina.getMano().add(carta1);
                                cartas.add(carta1);
                                System.out.println("Se agrego esta carta a máquina: "+ carta1);
                                turno = 2;
                            }
                        }
                        if(cartas.size() == 2 ){
                            ultimaCarta = cartaRemovida;
                            System.out.println("Carta en juego: " + ultimaCarta);
                            //Se manda un mensaje fuera del while de "La baraja se quedo sin cartas"(profe si se puede hacer esto)
                        }
                    }                    
                    else if (cartaRemovida.getTipo().equals("+4")) {
                        ArrayList<Carta> cartas = new ArrayList<>();
                        for (int i = 0; i < 4; i++) {
                            Carta carta = baraja.robarCarta();
                            if(carta != null){
                                maquina.getMano().add(carta);
                                cartas.add(carta);
                                System.out.println("Se agrego esta carta a maquina: " + carta);
                                turno = 2;
                            }
                        }
                        if(cartas.size() == 4){
                            ultimaCarta = cartaRemovida;
                            System.out.println("Carta en juego: " + ultimaCarta);
                            //Se manda un mensaje fuera del while de "La baraja se quedo sin cartas"(profe si se puede hacer esto)
                        }
                    } else if (cartaRemovida.getTipo().equals("^") || cartaRemovida.getTipo().equals("&")) {
                        // Turno permanece con el jugador actual
                        ultimaCarta = cartaRemovida;
                        System.out.println("Carta en juego: " + ultimaCarta);
                    } else {
                        turno = 2;
                        ultimaCarta = cartaRemovida;
                        System.out.println("Carta en juego: " + ultimaCarta);
                    }
                    if (jugador.getMano().size() == 1) {
                        System.out.println("Jugador: ¡UNOOOOOOOOOOOOO!");
                    }
                } else {
                    Carta carta = baraja.robarCarta();
                    if (carta != null) {
                        jugador.getMano().add(carta);
                        System.out.println("Carta añadida a tu mano: " + carta);
                        turno = 2;
                        //Se manda un mensaje fuera del while de "La baraja se quedo sin cartas"(profe si se puede hacer esto)
                    }
                }
            } 
            else {
                if (maquina.verificarExistenciaCarta(ultimaCarta)) {
                    Carta cartaRemovida = maquina.jugarCarta(ultimaCarta);
                    if (cartaRemovida != null) {
                        if (cartaRemovida.getTipo().equals("+2")) {
                            ArrayList<Carta> cartas = new ArrayList<>();
                            for(int i = 0; i < 2; i++){
                                Carta carta1 = baraja.robarCarta();
                                if(carta1 != null){
                                    jugador.getMano().add(carta1);
                                    cartas.add(carta1);
                                    System.out.println("Se agrego esta carta a jugador: "+ carta1);
                                    turno = 1;
                                }
                            }
                            if(cartas.size() == 2 ){
                                ultimaCarta = cartaRemovida;
                                System.out.println("Carta en juego: " + ultimaCarta);
                            }
                        } else if (cartaRemovida.getTipo().equals("+4")) {
                            ArrayList<Carta> cartas = new ArrayList<>();
                            for (int i = 0; i < 4; i++) {
                                Carta carta = baraja.robarCarta();
                                if(carta != null){
                                    jugador.getMano().add(carta);
                                    cartas.add(carta);
                                    System.out.println("Se agrego esta carta a jugador: " + carta);
                                    turno = 1;
                                }
                            }
                            if(cartas.size() == 4){
                                ultimaCarta = cartaRemovida;
                                System.out.println("Carta en juego: " + ultimaCarta);
                            }
                        } else if (cartaRemovida.getTipo().equals("^") || cartaRemovida.getTipo().equals("&")) {
                            // Turno permanece con la máquina
                            ultimaCarta = cartaRemovida;
                            System.out.println("Carta en juego: " + ultimaCarta);
                        } else {
                            turno = 1;
                            ultimaCarta = cartaRemovida;
                            System.out.println("Carta en juego: " + ultimaCarta);
                        }
                        if (maquina.getMano().size() == 1) {
                            System.out.println("Máquina: ¡UNOOOOOOO!");
                        }
                    } else {
                        Carta carta = baraja.robarCarta();
                        if (carta != null) {
                            maquina.getMano().add(carta);
                            System.out.println("Carta añadida a la mano de la máquina: " + carta);
                            turno = 1;
                        }
                    }
                } else {
                    Carta carta = baraja.robarCarta();
                    if (carta != null) {
                        maquina.getMano().add(carta);
                        System.out.println("Carta añadida a la mano de la máquina: " + carta);
                        turno = 1;
                    }
                }
            }
            if (jugador.getMano().size() == 0) {
                System.out.println("¡Jugador ha ganado! 🥳🏆");
            } else if (maquina.getMano().size() == 0) {
                System.out.println("¡Máquina ha ganado! 🥳🏆");
            }
        }
        if(baraja.getBaraja().size() == 0){
            System.out.println("No hay más cartas en la baraja. El juego termina en empate.");
        }
        sc.close();
    }
}
