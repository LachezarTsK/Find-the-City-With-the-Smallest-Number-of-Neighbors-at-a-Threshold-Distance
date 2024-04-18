
class Solution {

    companion object {
        const val NO_EDGE_BETWEEN_NODES = Int.MAX_VALUE
    }

    fun findTheCity(numberOfCities: Int, edges: Array<IntArray>, distanceThreshold: Int): Int {
        val minDistance = initializeMinDistanceArray(edges, numberOfCities);
        fillMinDistanceByFloydWarshallAlgorithm(minDistance, numberOfCities);

        return findCityWithMinNumberOfThresholdCities(minDistance, distanceThreshold, numberOfCities);
    }

    private fun findCityWithMinNumberOfThresholdCities(
        minDistance: Array<IntArray>,
        distanceThreshold: Int,
        numberOfCities: Int
    ): Int {
        var city = 0;
        var minNumberOfThresholdCities = Int.MAX_VALUE;

        for (from in 0..<numberOfCities) {
            var currentNumberOfThresholdCities = 0;

            for (to in 0..<numberOfCities) {
                currentNumberOfThresholdCities += if (minDistance[from][to] <= distanceThreshold) 1 else 0;
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

    private fun fillMinDistanceByFloydWarshallAlgorithm(minDistance: Array<IntArray>, numberOfCities: Int) {

        for (middle in 0..<numberOfCities) {

            for (from in 0..<numberOfCities) {
                if (minDistance[from][middle] == NO_EDGE_BETWEEN_NODES || from == middle) {
                    continue;
                }

                for (to in (from + 1)..<numberOfCities) {
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

    private fun initializeMinDistanceArray(edges: Array<IntArray>, numberOfCities: Int): Array<IntArray> {
        val minDistance = Array<IntArray>(numberOfCities) { IntArray(numberOfCities) { NO_EDGE_BETWEEN_NODES } }

        for ((from, to, weight) in edges) {
            minDistance[from][to] = weight;
            minDistance[to][from] = weight;

            minDistance[from][from] = 0;
            minDistance[to][to] = 0;
        }

        return minDistance;
    }
}
