import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Service {
    // ANSI escape codes

    // Normal Colors
    public static final String RESET = "\u001B[0m";

    // High Intensity
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String WHITE_BRIGHT = "\033[0;97m";

    String data;

    int capacity;
    List<Item> items = new ArrayList<>(); //
    byte[] bestSolVector; // aktualnie najlepsze rozwiazanie
    List<Item> bestSolItems = new ArrayList<>(); // aktualnie najlepsze rozwiazanie
    byte[] bestSolIndexes;
    int bestValue = 0;

    public Service(String data) {
        this.data = data;
    }

    void readInput() {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(data));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert scanner != null;
        capacity = scanner.nextInt();
        while (scanner.hasNextInt()) {
            items.add(new Item(scanner.nextInt(), scanner.nextInt()));
        }
        scanner.close();

        this.bestSolVector = new byte[items.size()];
    }

    public void generateCombinations(byte[] currentIndexes, int currentIndex) {
        if (currentIndex == items.size()) {
            int totalWeight = 0;
            int totalValue = 0;
            //List<Integer> currentIndexesL = new ArrayList<>();
            for (int i = 0; i < currentIndexes.length; i++) {
                if (currentIndexes[i] == 1) {
                    Item item = items.get(i);
                    totalWeight += item.weight;
                    totalValue += item.value;
                    //currentIndexesL.add(i);
                }
            }
            if (totalWeight <= capacity && totalValue > bestValue) {
                bestSolIndexes = currentIndexes;
                bestValue = totalValue;
            }
        } else {
            currentIndexes[currentIndex] = 0;
            generateCombinations(currentIndexes, currentIndex + 1);
            currentIndexes[currentIndex] = 1;
            generateCombinations(currentIndexes, currentIndex + 1);
        }
    }


//    public void generateCombinations(List<Item> currentItems, int currentIndex) { // Java STREAMs bo szybkosc
//        if (currentIndex == items.size()) { // item.size() == 30 2^30
//            int totalWeight = currentItems.stream().mapToInt(item -> item.weight).sum();
//            int totalValue = currentItems.stream().mapToInt(item -> item.value).sum();
//            if (totalWeight <= capacity && totalValue > bestValue) {
//                bestSolItems = new ArrayList<>(currentItems);
//                bestValue = totalValue;
//            }
//        } else {
//            generateCombinations(new ArrayList<>(currentItems), currentIndex + 1);
//            currentItems.add(items.get(currentIndex));
//            generateCombinations(currentItems, currentIndex + 1);
//        }
//    }

    public void printBestSolution() {
        System.out.println(WHITE_BRIGHT + "Best solution:" + RESET);
        bestSolItems
                .forEach(item -> System.out.println("Weight: " + item.weight + ", Value: " + item.value));

        System.out.println(GREEN_BRIGHT + "Total weight: " + bestSolItems.stream().mapToInt(item -> item.weight).sum() + RESET);
        System.out.println(GREEN_BRIGHT + "Total value: " + bestValue + RESET);
    }
}
