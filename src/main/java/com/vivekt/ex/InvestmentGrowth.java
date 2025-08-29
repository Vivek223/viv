package com.vivekt.ex;

public class InvestmentGrowth {

    public static void main(String[] args) {
        double principal = 5_00_00_000; // 5 Crores
        int years = 10;

        System.out.printf("%-10s %-20s %-20s %-20s%n", 
                "Return%", "Final Value (₹ Cr)", "Total Gain (₹ Cr)", "Multiple");

        for (int rate = 5; rate <= 15; rate += 1) {
            double finalValue = calculateFutureValue(principal, rate / 100.0, years);
            double gain = finalValue - principal;
            double multiple = finalValue / principal;

            System.out.printf("%-10d %-20.2f %-20.2f %-20.2f%n",
                    rate,
                    finalValue / 1_00_00_000, // convert to Crore
                    gain / 1_00_00_000,      // convert to Crore
                    multiple);
        }
    }

    private static double calculateFutureValue(double principal, double rate, int years) {
        return principal * Math.pow(1 + rate, years);
    }
}
