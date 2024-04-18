
import java.util.Arrays;

public class Solution {

    private static final int NO_EDGE_BETWEEN_NODES = Integer.MAX_VALUE;

    public int findTheCity(int numberOfCities, int[][] edges, int distanceThreshold) {
        int[][] minDistance = initializeMinDistanceArray(edges, numberOfCities);
        fillMinDistanceByFloydWarshallAlgorithm(minDistance, numberOfCities);

        return findCityWithMinNumberOfThresholdCities(minDistance, distanceThreshold, numberOfCities);
    }

    private int findCityWithMinNumberOfThresholdCities(int[][] minDistance, int distanceThreshold, int numberOfCities) {
        int city = 0;
        int minNumberOfThresholdCities = Integer.MAX_VALUE;

        for (int from = 0; from < numberOfCities; ++from) {
            int currentNumberOfThresholdCities = 0;

            for (int to = 0; to < numberOfCities; ++to) {
                currentNumberOfThresholdCities += (minDistance[from][to] <= distanceThreshold) ? 1 : 0;
            }
            // '<=' instead of '<' since when the numbers are equal, 
            // we pick up the city with the higher index
            if (currentNumberOfThresholdCities <= minNumberOfThresholdCities) {
                minNumberOfThresholdCities = currentNumberOfThresholdCities;
                city = from;
            }
        }

        return city;
    }

    private void fillMinDistanceByFloydWarshallAlgorithm(int[][] minDistance, int numberOfCities) {

        for (int middle = 0; middle < numberOfCities; ++middle) {

            for (int from = 0; from < numberOfCities; ++from) {
                if (minDistance[from][middle] == NO_EDGE_BETWEEN_NODES || from == middle) {
                    continue;
                }

                for (int to = from + 1; to < numberOfCities; ++to) {
                    if (minDistance[middle][to] == NO_EDGE_BETWEEN_NODES || to == middle) {
                        continue;
                    }

                    if (minDistance[from][to] > minDistance[from][middle] + minDistance[middle][to]) {
                        minDistance[from][to] = minDistance[from][middle] + minDistance[middle][to];
                        minDistance[to][from] = minDistance[from][middle] + minDistance[middle][to];
                    }
                }
            }
        }
    }

    private int[][] initializeMinDistanceArray(int[][] edges, int numberOfCities) {
        int[][] minDistance = new int[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; ++i) {
            Arrays.fill(minDistance[i], NO_EDGE_BETWEEN_NODES);
        }

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];

            minDistance[from][to] = weight;
            minDistance[to][from] = weight;

            minDistance[from][from] = 0;
            minDistance[to][to] = 0;
        }

        return minDistance;
    }
}
