import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

class FileCluster {
    private List<File> files;

    public FileCluster() {
        this.files = new ArrayList<>();
    }

    public void addFile(File file) {
        files.add(file);
    }

    public List<File> getFiles() {
        return files;
    }
}

public class SinglePassClustering {

    // Calculate cosine similarity between two vectors
    private static double cosineSimilarity(Map<String, Integer> vectorA, Map<String, Integer> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (String term : vectorA.keySet()) {
            int a = vectorA.get(term);
            dotProduct += a * vectorB.getOrDefault(term, 0);
            normA += a * a;
        }

        for (int b : vectorB.values()) {
            normB += b * b;
        }

        if (normA == 0 || normB == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    // Read the file content and create a term frequency map
    private static Map<String, Integer> getTermFrequency(String content) {
        Map<String, Integer> termFrequency = new HashMap<>();
        String[] terms = content.split("\\W+"); // Split by non-word characters

        for (String term : terms) {
            term = term.toLowerCase();
            termFrequency.put(term, termFrequency.getOrDefault(term, 0) + 1);
        }

        return termFrequency;
    }

    public static List<FileCluster> clusterFiles(File[] files) throws IOException {
        List<FileCluster> clusters = new ArrayList<>();

        for (File file : files) {
            String content = new String(Files.readAllBytes(file.toPath()));
            Map<String, Integer> termFrequency = getTermFrequency(content);

            boolean added = false;
            for (FileCluster cluster : clusters) {
                // Calculate the centroid as the average term frequency of the cluster
                Map<String, Integer> centroidTF = new HashMap<>();
                for (File clusteredFile : cluster.getFiles()) {
                    String clusteredContent = new String(Files.readAllBytes(clusteredFile.toPath()));
                    Map<String, Integer> clusteredTF = getTermFrequency(clusteredContent);
                    clusteredTF.forEach((k, v) -> centroidTF.merge(k, v, Integer::sum));
                }

                // Average term frequency
                for (String term : centroidTF.keySet()) {
                    centroidTF.put(term, centroidTF.get(term) / cluster.getFiles().size());
                }

                // Calculate similarity
                if (cosineSimilarity(termFrequency, centroidTF) > 0.5) { // Threshold for similarity
                    cluster.addFile(file);
                    added = true;
                    break;
                }
            }

            if (!added) {
                // Create a new cluster if not similar
                FileCluster newCluster = new FileCluster();
                newCluster.addFile(file);
                clusters.add(newCluster);
            }
        }

        return clusters;
    }

    public static void main(String[] args) {
        try {
            // Manually specify the paths of each file
            File[] files = new File[]{
                new File("C:/Users/tmbha/OneDrive/Desktop/IR/Exp_2 _SinglePass/file1.txt"),
                new File("C:/Users/tmbha/OneDrive/Desktop/IR/Exp_2 _SinglePass/file2.txt"),
                new File("C:/Users/tmbha/OneDrive/Desktop/IR/Exp_2 _SinglePass/file3.txt"),
                new File("C:/Users/tmbha/OneDrive/Desktop/IR/Exp_2 _SinglePass/file4.txt"),
                new File("C:/Users/tmbha/OneDrive/Desktop/IR/Exp_2 _SinglePass/file5.txt"),
                // Add more files as needed
            };

            List<FileCluster> clusters = clusterFiles(files);

            // Print out the clusters
            for (int i = 0; i < clusters.size(); i++) {
                System.out.println("Cluster " + (i + 1) + ":");
                for (File file : clusters.get(i).getFiles()) {
                    System.out.println(" - " + file.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
