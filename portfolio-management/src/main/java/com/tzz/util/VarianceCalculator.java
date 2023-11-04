package com.tzz.util;

import java.util.List;

public class VarianceCalculator {

    public static Double calculateVariance(List<Double> data) {
        int n = data.size();
        Double mean = calculateMean(data);
        Double sum = 0.0;

        for (Double value : data) {
            Double diff = value - mean;
            sum += diff * diff;
        }


        return sum / n;
    }

    private static Double calculateMean(List<Double> data) {
        int n = data.size();
        Double sum = 0.0;
        for (Double value: data) {
            sum = sum + value;
        }
        return sum / n;
    }

}
