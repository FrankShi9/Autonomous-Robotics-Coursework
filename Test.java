package com.company;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Motion f1 = new Motion();
        // motion disable normalization
        f1.output();
        for(int i=0; i<9; i++) {
            f1.forward();
        }
        for(int i=0; i<5; i++) {
            f1.backward();
        }
        System.out.println("-------------------------------------------------");

        Sensor f2 = new Sensor();
        // sensor enable normalization
        f2.output();
        double eta = 0.0;

        for(int i=0; i<19; i++) {
            double q = 1.0;
            double dexp = 0.0;
            double p = 0.0;
            double df = f2.forwardSense();
            double bel = 0.0;

            if(df < f2.RANGE_MAX){
                dexp = f2.getSize()-i;
                p = normal(df-dexp, f2.SENSOR_NOISE_SD);
                q *= p;
            }
            double db = f2.backwardSense();
            if(db<f2.RANGE_MAX){
                dexp = i;
                p = normal(db-dexp, f2.SENSOR_NOISE_SD);
                q *= p;
            }

            bel = q * f2.getBel(i);
            eta += bel;
            f2.setBel(i, bel);
        }

        for (int i=0; i<f2.getSize(); i++){
            f2.setBel(i, f2.getBel(i)/eta);
        }
    }

    public static double normal(double a, double b){
        Random rand = new Random();
        double t = b*2;
        // latent gaussian sample on demand
        for(int i=0; i<12; i++) {
            a += (rand.nextDouble()*t-b);
        }
        return (a/2.0);
    }
}
