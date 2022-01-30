package com.company.Estruturas;


public class MinCostPair implements Comparable<MinCostPair>
{
    /**
     * Index of the current element.
     */
    private int index;
    /**
     * Cost from the start element to the current element.
     */
    private double cost;
    /**
     * Path from the start element to the current element.
     */
    public ArrayUnorderedList<Integer> path = new ArrayUnorderedList<Integer>();

    /**
     * Creates a new MinCostPair with a given index and cost.
     *
     * @param index index of the current element
     * @param cost  cost of the current path
     */
    public MinCostPair(int index, double cost) {
        this.index = index;
        this.cost = cost;
    }

    /**
     * Returns the index of this MinCostPair.
     *
     * @return index of this MinCostPair
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index for this MinCostPair.
     *
     * @param index index of this MinCostPair
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Returns the cost of the path of this MinCostPair.
     *
     * @return cost of the path of this MinCostPair
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cost of the path of this MinCostPair.
     *
     * @param cost of the path of this MinCostPair
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Returns an int value that indicates if the given MinCostPair is bigger, equal or
     * smaller to this MinCostPair.
     *
     * @param minCostPair to be compared to this MinCostPair
     * @return 1 if the current MinCostPair is bigger than the given one, 0 if its equal
     * or -1 if it is smaller
     */
    @Override
    public int compareTo(MinCostPair minCostPair) {
        if (this.cost < minCostPair.cost)
            return -1;
        else if (this.cost > minCostPair.cost)
            return 1;
        else
            return 0;
    }
}
