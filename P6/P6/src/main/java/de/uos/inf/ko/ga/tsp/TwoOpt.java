package de.uos.inf.ko.ga.tsp;

public class TwoOpt {

	/**
	 * Perform a two-opt exchange step of the edges (v[pos1], v[pos1 + 1]) and (v[pos2], v[pos2 + 1]).
	 * The resulting tour visits
	 *   v[0], ..., v[pos1], v[pos2], v[pos2 - 1], ..., v[pos1 + 1], v[pos2 + 1], v[pos2 + 2], ..., v[n - 1].
	 * Special case: if the last edge (v[n - 1], v[0]) is used, then the following tour is created:
	 *   v[0], v[pos1 + 1], v[pos1 + 2], ..., v[n - 1], v[pos1], v[pos1 - 1], ..., v[0]
	 * @param tour Tour
	 * @param pos1 Index of the starting vertex of the first edge
	 * @param pos2 Index of the starting vertex of the second edge
	 * @return tour obtained by performing the edge exchange
	 */
	public static Tour twoOptExchange(Tour tour, int pos1, int pos2) {
		assert(tour != null);
		assert(pos1 >= 0);
		assert(pos2 > pos1 + 1);
		assert(pos2 < tour.getVertices().length);
		assert(pos1 != (pos2 + 1) % tour.getVertices().length);

		int[] newTour = new int[tour.getVertices().length];

		// special case: last edge used
        if (pos1 == tour.getVertices().length - 1 || pos2 == tour.getVertices().length - 1) {
            int idx = 0;
            newTour[idx++] = tour.getVertices()[0];
            for (int i = pos1 + 1; i < tour.getVertices().length; i++) {
                newTour[idx++] = tour.getVertices()[i];
            }
            for (int i = pos1; i >= 0; i--) {
                newTour[idx++] = tour.getVertices()[i];
            }
        } else {
            for (int i = 0; i <= pos1; i++) {
                newTour[i] = tour.getVertices()[i];
            }
            int idx = pos1 + 1;
            for (int i = pos2; i > pos1; i--) {
                newTour[idx++] = tour.getVertices()[i];
            }
            newTour[idx++] = tour.getVertices()[pos2 + 1];

            for (int i = pos2 + 2; i < tour.getVertices().length; i++) {
                newTour[i] = tour.getVertices()[i];
            }
        }
        return new Tour(tour.getGraph(), newTour);
	}

	/**
	 * Single step of the Two-Opt neighborhood for the TSP with either
	 * first-fit or best-fit selection of the neighbor.
	 * - First-fit returns the first neighbor that is found that has a
	 *   better objective value than the original tour.
	 * - Best-fit always searches through the whole neighborhood and
	 *   returns one of the tours with the best objective values.
	 * @param tour
	 * @param firstFit whether to use first-fit or best-fit for neighbor selection
	 * @return tour obtained by performing the first or the best improvement
	 */
	public static Tour twoOptNeighborhood(Tour tour, boolean firstFit) {

	    Tour bestTour = new Tour(tour);

	    for (int i = 0; i < tour.getVertices().length - 2; i++) {
	        for (int j = i + 2; j < tour.getVertices().length - 1; j++) {
	            Tour tmpTour = twoOptExchange(tour, i, j);
	            if (firstFit && tmpTour.getCosts() < tour.getCosts()) {
	                return tmpTour;
                } else {
	                if (tmpTour.getCosts() < bestTour.getCosts()) {
	                    bestTour = new Tour(tmpTour);
                    }
                }
            }
        }
        return bestTour;
	}

	/**
	 * Iterative Two-Opt neighborhood for the TSP.
	 * This method calls twoOptNeighborhood iteratively as long as the
	 * tour can be improved.
	 * @param tour Tour to be improved
	 * @param firstFit whether to use first-fit or best-fit for neighbor selection
	 * @return best tour obtained by iteratively applying the two-opt neighborhood
	 */
	public static Tour iterativeTwoOpt(Tour tour, boolean firstFit) {

	    boolean improvement = true;
	    Tour bestTour = new Tour(tour);

	    while (improvement) {
            improvement = false;
	        Tour tmpTour = twoOptNeighborhood(tour, firstFit);
	        if (tmpTour.getCosts() < bestTour.getCosts()) {
	            bestTour = new Tour(tmpTour);
	            improvement = true;
            }
        }
        return bestTour;
	}
}
