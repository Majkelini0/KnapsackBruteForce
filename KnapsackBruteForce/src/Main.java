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
    static int totalCapacity;
    static List<Item> items = new ArrayList<>();
    static List<Item> bestItems = new ArrayList<>();
    static int bestValue = 0;

    public static void main(String[] args) throws FileNotFoundException {
        readInput("src/Data/16");
        generateCombinations(new ArrayList<>(), 0);
        printBestSolution();
    }

    static void readInput(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        totalCapacity = scanner.nextInt();
        while (scanner.hasNextInt()) {
            items.add(new Item(scanner.nextInt(), scanner.nextInt()));
        }
        scanner.close();
    }

    static void generateCombinations(List<Item> currentItems, int currentIndex) {
        if (currentIndex == items.size()) {
            int totalWeight = currentItems.stream().mapToInt(item -> item.weight).sum();
            int totalValue = currentItems.stream().mapToInt(item -> item.value).sum();
            if (totalWeight <= totalCapacity && totalValue > bestValue) {
                bestItems = new ArrayList<>(currentItems);
                bestValue = totalValue;
            }
        } else {
            generateCombinations(new ArrayList<>(currentItems), currentIndex + 1);
            currentItems.add(items.get(currentIndex));
            generateCombinations(currentItems, currentIndex + 1);
        }
    }

    static void printBestSolution() {
        System.out.println("Best solution:");
        bestItems.forEach(item -> System.out.println("Weight: " + item.weight + ", Value: " + item.value));
        System.out.println("Total weight: " + bestItems.stream().mapToInt(item -> item.weight).sum());
        System.out.println("Total value: " + bestValue);
    }
}