import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConflationProgram {

    // List of common suffixes to strip
    private static final String[] SUFFIXES = {"ing", "ed", "ly", "s", "es"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter text for conflation:");

        // Read input text
        String input = scanner.nextLine();
        scanner.close();

        // Process the input text
        Map<String, Integer> wordCountMap = new HashMap<>();
        String[] words = input.split("\\W+"); // Split by non-word characters

        for (String word : words) {
            // Strip suffix and convert to lowercase
            String strippedWord = stripSuffix(word.toLowerCase());
            if (!strippedWord.isEmpty()) {
                wordCountMap.put(strippedWord, wordCountMap.getOrDefault(strippedWord, 0) + 1);
            }
        }

        // Print the word counts
        System.out.println("Word counts after suffix stripping:");
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Method to strip suffixes from a word
    private static String stripSuffix(String word) {
        for (String suffix : SUFFIXES) {
            if (word.endsWith(suffix)) {
                return word.substring(0, word.length() - suffix.length());
            }
        }
        return word; // Return the original word if no suffix was found
    }
}
