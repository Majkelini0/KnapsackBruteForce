/*
Napisz implementację algorytmu brute-force dla problemu plecakowego.

Twój program powinien wygenerować wszystkie możliwe wektory charakterystyczne reprezentujące
potencjalne rozwiązania (ale nie powinien przechowywać ich w pamięci ani wypisywać wszystkich).

Należy od podstaw zaimplementować metodę generowania wektorów charakterystycznych.
Nie można do tego użyć predefiniowanych metod, takich jak Set,characteristicVector, toBinaryString lub inne.
Jako wynik wypisz wektor charakterystyczny całkowitą wagę i całkowitą wartość najlepszego rozwiązania.
Twój program powinien znaleźć rozwiązanie w ciągu kilku minut. Implementacje, których czas wykonania
przekracza 20 minut, nie będą akceptowane.
Wskazówka: Czas wykonania zależy od wybranego języka programowania.
Opcjonalnie: Wyświetl czas wykonania

Format każdego pliku w załączniku:
pierwsza linia: całkowita pojemność
każda następna linia: waga oraz wartość przedmiotu

Każdy student ma dedykowany plik wejściowy: s27799 -> plik nr 16
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // ANSI escape codes
    // Normal Colors
    public static final String RESET = "\u001B[0m";

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String YELLOW_BRIGHT = "\033[0;93m";

    // 2^30 (1.073.741.824) kombinacji
    // ok 160 sec = 2,6 min

    public static void main(String[] args) {
        String data = "src/Data/16";
        //String data = "src/Data/fast";

        Service service = new Service(data);

        service.readInput();
        System.out.println("Total capacity: " + service.capacity);
        System.out.println("Total items: " + service.items.size());
        System.out.println(YELLOW_BRIGHT + "Total combinations: " + (long) Math.pow(2, service.items.size()) + RESET);

        // START
        long startTime = System.currentTimeMillis();
        Thread timerThread = new Thread(() -> {
            while (true) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                System.out.println(BLACK_BRIGHT + "Elapsed time: " + elapsedTime / 1000.0 + " seconds" + RESET);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println(GREEN_BRIGHT + "Elapsed time: " + elapsedTime / 1000.0 + " seconds" + RESET);
                    break;
                }
            }
        });

        timerThread.start();

        service.generateCombinations(new byte[30], 0);

        timerThread.interrupt();
        // STOP

        service.printBestSolution();
    }
}