import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Merge<T extends Comparable<T>> {

    // Method 1: Merge two ordered lists into a new ordered list
    public static <T extends Comparable<T>> OrderedList<T> merge(OrderedList<T> list1, OrderedList<T> list2) {
        OrderedList<T> result = new OrderedList<>();
        int i = 0, j = 0;

        // Use two-finger walking algorithm to merge the list
        while (i < list1.size() && j < list2.size()) {
            T item1 = list1.get(i);
            T item2 = list2.get(j);

            if (item1.compareTo(item2) < 0) {
                result.add(item1);
                i++;
            } else if (item1.compareTo(item2) > 0) {
                result.add(item2);
                j++;
            } else {
                result.add(item1); // Add common element once
                i++;
                j++;
            }
        }

        while (i < list1.size()) {
            result.add(list1.get(i++));
        }

        while (j < list2.size()) {
            result.add(list2.get(j++));
        }

        return result;
    }

    // Method 2: Find common elements between two ordered lists
    public static <T extends Comparable<T>> OrderedList<T> common(OrderedList<T> list1, OrderedList<T> list2) {
        OrderedList<T> result = new OrderedList<>();
        int i = 0, j = 0;

        while (i < list1.size() && j < list2.size()) {
            T item1 = list1.get(i);
            T item2 = list2.get(j);

            int comparison = item1.compareTo(item2);
            if (comparison == 0) {
                result.add(item1);
                i++;
                j++;
            } else if (comparison < 0) {
                i++;
            } else {
                j++;
            }
        }

        return result;
    }


    // Method 3: Find difference between two ordered lists (items in list1 but not in list2)
    public static <T extends Comparable<T>> OrderedList<T> difference(OrderedList<T> list1, OrderedList<T> list2) {
        OrderedList<T> result = new OrderedList<>();
        int i = 0, j = 0;

        while (i < list1.size() && j < list2.size()) {
            T item1 = list1.get(i);
            T item2 = list2.get(j);

            int comparison = item1.compareTo(item2);
            if (comparison == 0) {
                i++;
                j++;
            } else if (comparison < 0) {
                result.add(item1);
                i++;
            } else {
                j++;
            }
        }

        // Add remaining items from list1, if any
        while (i < list1.size()) {
            result.add(list1.get(i++));
        }

        return result;
    }


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ArrayList<String> names = new ArrayList<>();
            try (Scanner fileScanner = new Scanner(new File("RandomNames.txt"))) {
                while (fileScanner.hasNextLine()) {
                    names.add(fileScanner.nextLine().trim());
                }
            } catch (IOException e) {
                System.out.println("Error reading RandomNames.txt.");
                return;
            }

            int n1 = promptListSize(scanner, "list1");
            int n2 = promptListSize(scanner, "list2");

            OrderedList<String> list1 = createRandomOrderedList(names, n1);
            OrderedList<String> list2 = createRandomOrderedList(names, n2);

            OrderedList<String> mergedList = merge(list1, list2);
            saveToFile(mergedList, "merged.txt");

            OrderedList<String> commonList = common(list1, list2);
            saveToFile(commonList, "common.txt");

            OrderedList<String> differenceList = difference(list1, list2);
            saveToFile(differenceList, "difference.txt");

            System.out.println("Results saved to merged.txt, common.txt, and difference.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading or writing files.");
        }
    }

    /**
     * Helper Method, prompts the user to enter the size of a list within a specified range.
     * Repeats until the user provides a valid integer within the range 1000-1500.
     *
     * @param scanner The Scanner object to read user input.
     * @param listName The name of the list for display in the prompt.
     * @return The valid list size entered by the user.
     */
    private static int promptListSize(Scanner scanner, String listName) {
        int size;
        do {
            System.out.print("Enter the size for " + listName + " (between 1000 and 1500): ");
            size = scanner.nextInt();
        } while (size < 1000 || size > 1500);
        return size;
    }


    /**
     * Helper Method, creates an ordered list by randomly selecting a specified number of unique names
     * from a given list of names. Names are selected without repetition.
     *
     * @param names The ArrayList containing the names to choose from.
     * @param n The number of names to select.
     * @return An OrderedList containing the randomly selected names in sorted order.
     */
    private static OrderedList<String> createRandomOrderedList(ArrayList<String> names, int n) {
        ArrayList<String> selectedNames = new ArrayList<>(names); // Make a copy of names
        Random random = new Random();
        OrderedList<String> orderedList = new OrderedList<>();

        for (int i = 0; i < n; i++) {
            int randomIndex = random.nextInt(selectedNames.size());
            String name = selectedNames.remove(randomIndex); // Remove to avoid repetition
            orderedList.insert(name);
        }

        return orderedList;
    }


    /**
     * Helper Method, saves the contents of an ordered list to a text file.
     * Each item in the list is written on a new line in the file.
     *
     * @param list The OrderedList containing the items to save.
     * @param fileName The name of the file to save the list to.
     * @throws IOException If an I/O error occurs.
     */
    private static void saveToFile(OrderedList<String> list, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < list.size(); i++) {
                writer.write(list.get(i) + "\n");
            }
        }
    }
}