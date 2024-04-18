
function findTheCity(numberOfCities: number, edges: number[][], distanceThreshold: number): number {
    this.NO_EDGE_BETWEEN_NODES = Number.MAX_SAFE_INTEGER;
    const minDistance = initializeMinDistanceArray(edges, numberOfCities);
    fillMinDistanceByFloydWarshallAlgorithm(minDistance, numberOfCities);

    return findCityWithMinNumberOfThresholdCities(minDistance, distanceThreshold, numberOfCities);
};


function findCityWithMinNumberOfThresholdCities
    (minDistance: number[][], distanceThreshold: number, numberOfCities: number): number {

    let city = 0;
    let minNumberOfThresholdCities = Number.MAX_SAFE_INTEGER;

    for (let from = 0; from < numberOfCities; ++from) {
        let currentNumberOfThresholdCities = 0;

        for (let to = 0; to < numberOfCities; ++to) {
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

function fillMinDistanceByFloydWarshallAlgorithm(minDistance: number[][], numberOfCities: number): void {

    for (let middle = 0; middle < numberOfCities; ++middle) {

        for (let from = 0; from < numberOfCities; ++from) {
            if (minDistance[from][middle] === this.NO_EDGE_BETWEEN_NODES || from === middle) {
                continue;
            }

            for (let to = from + 1; to < numberOfCities; ++to) {
                if (minDistance[middle][to] === this.NO_EDGE_BETWEEN_NODES || to === middle) {
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

function initializeMinDistanceArray(edges: number[][], numberOfCities: number): number[][] {
    const minDistance = Array.from(new Array(numberOfCities), () => new Array(numberOfCities).fill(this.NO_EDGE_BETWEEN_NODES));

    for (let [from, to, weight] of edges) {
        minDistance[from][to] = weight;
        minDistance[to][from] = weight;

        minDistance[from][from] = 0;
        minDistance[to][to] = 0;
    }

    return minDistance;
}
