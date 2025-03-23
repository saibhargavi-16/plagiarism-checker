import java.util.HashMap;
import java.util.Map;

public class SimilarityChecker {

    public static double calculateCosineSimilarity(Map<String, Integer> inputMap1, Map<String, Integer> inputMap2) {
        // Create mutable maps
        Map<String, Integer> freqMap1 = new HashMap<>(inputMap1);
        Map<String, Integer> freqMap2 = new HashMap<>(inputMap2);

        int dotProduct = 0;
        double normA = 0;
        double normB = 0;

        for (String key : freqMap1.keySet()) {
            int value1 = freqMap1.getOrDefault(key, 0);
            int value2 = freqMap2.getOrDefault(key, 0);

            dotProduct += value1 * value2;
            normA += Math.pow(value1, 2);
        }

        for (int value : freqMap2.values()) {
            normB += Math.pow(value, 2);
        }

        if (normA == 0 || normB == 0) {
            return 0;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
