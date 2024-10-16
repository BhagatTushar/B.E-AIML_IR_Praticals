public class HarmonicAndEMeasureCalculator {
    
    public static void main(String[] args) {
        // Sample precision and recall values
        double precision = 0.75; // Example precision value
        double recall = 0.60;    // Example recall value
        double beta = 1.0;       // Typically 1 for F1 measure
        
        // Calculate F-measure (F1 Score)
        double fMeasure = calculateFMeasure(precision, recall);
        
        // Calculate E-measure
        double eMeasure = calculateEMeasure(precision, recall, beta);
        
        // Output results
        System.out.printf("F-measure (F1 Score): %.2f\n", fMeasure);
        System.out.printf("E-measure: %.2f\n", eMeasure);
    }

    public static double calculateFMeasure(double precision, double recall) {
        if (precision + recall == 0) {
            return 0.0; // Avoid division by zero
        }
        return 2 * (precision * recall) / (precision + recall);
    }

    public static double calculateEMeasure(double precision, double recall, double beta) {
        if (precision == 0 && recall == 0) {
            return 0.0; // Avoid division by zero
        }
        double betaSquared = beta * beta;
        return (1 + betaSquared) * (precision * recall) / (betaSquared * precision + recall);
    }
}
