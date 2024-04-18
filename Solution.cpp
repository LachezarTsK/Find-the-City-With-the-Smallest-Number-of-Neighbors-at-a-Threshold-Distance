
#include <span>
#include <vector>
#include <limits>
using namespace std;

class Solution {

    // alternatively: INT_MAX    
    inline static const int NO_EDGE_BETWEEN_NODES = numeric_limits<int>::max();

public:
    int findTheCity(int numberOfCities, const vector<vector<int>>& edges, int distanceThreshold) const {
        vector<vector<int>> minDistance = initializeMinDistanceArray(edges, numberOfCities);
        fillMinDistanceByFloydWarshallAlgorithm(minDistance, numberOfCities);

        return findCityWithMinNumberOfThresholdCities(minDistance, distanceThreshold, numberOfCities);
    }

private:
    int findCityWithMinNumberOfThresholdCities(span<const vector<int>> minDistance, int distanceThreshold, int numberOfCities) const {
        int city = 0;
        int minNumberOfThresholdCities = numeric_limits<int>::max();

        for (size_t from = 0; from < numberOfCities; ++from) {
            int currentNumberOfThresholdCities = 0;

            for (size_t to = 0; to < numberOfCities; ++to) {
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

    void fillMinDistanceByFloydWarshallAlgorithm(span<vector<int>> minDistance, int numberOfCities) const {

        for (size_t middle = 0; middle < numberOfCities; ++middle) {

            for (size_t from = 0; from < numberOfCities; ++from) {
                if (minDistance[from][middle] == NO_EDGE_BETWEEN_NODES || from == middle) {
                    continue;
                }

                for (size_t to = from + 1; to < numberOfCities; ++to) {
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

    vector<vector<int>> initializeMinDistanceArray(span<const vector<int>> edges, int numberOfCities) const {
        vector<vector<int>> minDistance(numberOfCities, vector<int>(numberOfCities, NO_EDGE_BETWEEN_NODES));

        for (const auto& edge : edges) {
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
};
