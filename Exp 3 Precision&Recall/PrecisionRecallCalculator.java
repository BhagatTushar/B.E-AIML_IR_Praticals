import java.util.HashSet;
import java.util.Set;

public class PrecisionRecallCalculator {

    public static void main(String[] args) {
        // Sample inputs
        Set<String> answerSetA = new HashSet<>();
        answerSetA.add("doc1");
        answerSetA.add("doc2");
        answerSetA.add("doc3");
        answerSetA.add("doc4");
        answerSetA.add("doc5");

        Set<String> relevantDocumentsRq1 = new HashSet<>();
        relevantDocumentsRq1.add("doc2");
        relevantDocumentsRq1.add("doc3");
        relevantDocumentsRq1.add("doc6");

        Set<String> retrievedDocumentsQ1 = new HashSet<>();
        retrievedDocumentsQ1.add("doc1");
        retrievedDocumentsQ1.add("doc2");

        // Calculate precision and recall
        double precision = calculatePrecision(retrievedDocumentsQ1, relevantDocumentsRq1);
        double recall = calculateRecall(retrievedDocumentsQ1, relevantDocumentsRq1);

        // Output the results
        System.out.printf("Precision: %.2f%n", precision);
        System.out.printf("Recall: %.2f%n", recall);
    }

    // Method to calculate precision
    private static double calculatePrecision(Set<String> retrieved, Set<String> relevant) {
        if (retrieved.isEmpty()) {
            return 0.0; // Avoid division by zero
        }
        Set<String> relevantRetrieved = new HashSet<>(retrieved);
        relevantRetrieved.retainAll(relevant); // Keep only relevant documents
        return (double) relevantRetrieved.size() / retrieved.size();
    }

    // Method to calculate recall
    private static double calculateRecall(Set<String> retrieved, Set<String> relevant) {
        if (relevant.isEmpty()) {
            return 0.0; // Avoid division by zero
        }
        Set<String> relevantRetrieved = new HashSet<>(retrieved);
        relevantRetrieved.retainAll(relevant); // Keep only relevant documents
        return (double) relevantRetrieved.size() / relevant.size();
    }
}
