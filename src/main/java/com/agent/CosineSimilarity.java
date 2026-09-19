package com.agent;

import java.util.List;

public class CosineSimilarity {

    public static double calculate(
            List<Double> a,
            List<Double> b) {

        if (a.size() != b.size()) {
            throw new IllegalArgumentException(
                    "Vector dimensions must be the same"
            );
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < a.size(); i++) {

            double valueA = a.get(i);
            double valueB = b.get(i);

            dotProduct += valueA * valueB;

            normA += valueA * valueA;
            normB += valueB * valueB;
        }

        if (normA == 0 || normB == 0) {
            return 0.0;
        }

        return dotProduct /
                (Math.sqrt(normA) * Math.sqrt(normB));
    }
}