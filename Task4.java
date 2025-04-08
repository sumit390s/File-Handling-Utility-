import java.util.*;

public class RecommendationEngine {

    // Sample data: Products with their features/categories
    private static final Map<String, Set<String>> products = new HashMap<>();

    // Sample data: User preferences (what features/categories they like)
    private static final Map<String, Set<String>> userPreferences = new HashMap<>();

    public static void main(String[] args) {
        // Initialize sample product data
        products.put("Laptop X", new HashSet<>(Arrays.asList("Electronics", "Laptop", "High Performance", "16GB RAM", "512GB SSD")));
        products.put("Laptop Y", new HashSet<>(Arrays.asList("Electronics", "Laptop", "Budget", "8GB RAM", "256GB SSD")));
        products.put("Smartphone A", new HashSet<>(Arrays.asList("Electronics", "Smartphone", "Camera Focused", "128GB Storage", "5G")));
        products.put("Smartphone B", new HashSet<>(Arrays.asList("Electronics", "Smartphone", "Long Battery", "64GB Storage", "4G")));
        products.put("Book 1", new HashSet<>(Arrays.asList("Books", "Fiction", "Mystery", "Thriller")));
        products.put("Book 2", new HashSet<>(Arrays.asList("Books", "Science Fiction", "Space Opera")));
        products.put("T-Shirt Red", new HashSet<>(Arrays.asList("Apparel", "T-Shirt", "Casual", "Red", "Cotton")));
        products.put("T-Shirt Blue", new HashSet<>(Arrays.asList("Apparel", "T-Shirt", "Casual", "Blue", "Polyester")));

        // Initialize sample user preferences
        userPreferences.put("User1", new HashSet<>(Arrays.asList("Electronics", "Laptop", "High Performance")));
        userPreferences.put("User2", new HashSet<>(Arrays.asList("Books", "Mystery")));
        userPreferences.put("User3", new HashSet<>(Arrays.asList("Electronics", "Smartphone", "Camera Focused", "5G")));
        userPreferences.put("User4", new HashSet<>(Arrays.asList("Apparel", "T-Shirt", "Cotton")));

        // Get recommendations for a specific user
        String userId = "User1";
        List<String> recommendations = getRecommendations(userId);

        System.out.println("Recommendations for " + userId + ":");
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations found.");
        } else {
            for (String product : recommendations) {
                System.out.println("- " + product);
            }
        }

        userId = "User2";
        recommendations = getRecommendations(userId);
        System.out.println("\nRecommendations for " + userId + ":");
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations found.");
        } else {
            for (String product : recommendations) {
                System.out.println("- " + product);
            }
        }
    }

    public static List<String> getRecommendations(String userId) {
        if (!userPreferences.containsKey(userId)) {
            System.out.println("User not found: " + userId);
            return new ArrayList<>();
        }

        Set<String> userPrefs = userPreferences.get(userId);
        List<ProductScore> productScores = new ArrayList<>();

        for (Map.Entry<String, Set<String>> productEntry : products.entrySet()) {
            String productName = productEntry.getKey();
            Set<String> productFeatures = productEntry.getValue();

            // Calculate a simple score based on the number of matching features
            int score = 0;
            for (String feature : productFeatures) {
                if (userPrefs.contains(feature)) {
                    score++;
                }
            }

            // Store the product and its score
            productScores.add(new ProductScore(productName, score));
        }

        // Sort products by score in descending order
        Collections.sort(productScores, (p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

        // Extract the recommended product names (excluding items the user might have already interacted with - not implemented here for simplicity)
        List<String> recommendations = new ArrayList<>();
        for (ProductScore productScore : productScores) {
            if (productScore.getScore() > 0) { // Only recommend products with at least one matching feature
                recommendations.add(productScore.getProductName());
            }
        }

        return recommendations;
