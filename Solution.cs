
using System;

public class Solution
{
    private static readonly int NO_EDGE_BETWEEN_NODES = int.MaxValue;

    public int FindTheCity(int numberOfCities, int[][] edges, int distanceThreshold)
    {
        int[][] minDistance = InitializeMinDistanceArray(edges, numberOfCities);
        FillMinDistanceByFloydWarshallAlgorithm(minDistance, numberOfCities);

        return FindCityWithMinNumberOfThresholdCities(minDistance, distanceThreshold, numberOfCities);
    }

    private int FindCityWithMinNumberOfThresholdCities(int[][] minDistance, int distanceThreshold, int numberOfCities)
    {
        int city = 0;
        int minNumberOfThresholdCities = int.MaxValue;

        for (int from = 0; from < numberOfCities; ++from)
        {
            int currentNumberOfThresholdCities = 0;

            for (int to = 0; to < numberOfCities; ++to)
            {
                currentNumberOfThresholdCities += (minDistance[from][to] <= distanceThreshold) ? 1 : 0;
            }
            // '<=' instead of '<' since when the numbers are equal, 
            // we pick up the city with the higher index
            if (currentNumberOfThresholdCities <= minNumberOfThresholdCities)
            {
                minNumberOfThresholdCities = currentNumberOfThresholdCities;
                city = from;
            }
        }

        return city;
    }

    private void FillMinDistanceByFloydWarshallAlgorithm(int[][] minDistance, int numberOfCities)
    {
        for (int middle = 0; middle < numberOfCities; ++middle)
        {
            for (int from = 0; from < numberOfCities; ++from)
            {
                if (minDistance[from][middle] == NO_EDGE_BETWEEN_NODES || from == middle)
                {
                    continue;
                }

                for (int to = from + 1; to < numberOfCities; ++to)
                {
                    if (minDistance[middle][to] == NO_EDGE_BETWEEN_NODES || to == middle)
                    {
                        continue;
                    }

                    if (minDistance[from][to] > minDistance[from][middle] + minDistance[middle][to])
                    {
                        minDistance[from][to] = minDistance[from][middle] + minDistance[middle][to];
                        minDistance[to][from] = minDistance[from][middle] + minDistance[middle][to];
                    }
                }
            }
        }
    }

    private int[][] InitializeMinDistanceArray(int[][] edges, int numberOfCities)
    {
        int[][] minDistance = new int[numberOfCities][];
        for (int i = 0; i < numberOfCities; ++i)
        {
            minDistance[i] = new int[numberOfCities];
            Array.Fill(minDistance[i], NO_EDGE_BETWEEN_NODES);
        }

        foreach (int[] edge in edges)
        {
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
