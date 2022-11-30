package com.company;
import java.lang.Math;
import java.util.Random;

public class Sensor extends Motion{

    private Random rand;
    private int size = 20;
    final double RANGE_MAX = 5.0;
    final double SENSOR_NOISE_SD = 0.4;

    public Sensor(){
        super();
        this.truePos = 9;
        this.rand = new Random();
        this.update();
    }

//    public void forward(){
//        this.truePos++;
//        double x = 0.0;
//        for(int i=0; i<20; i++){
//            if(this.truePos<=3 || this.truePos>=14){
//                x = Math.abs(this.query(0.4)+(double)(20-this.truePos));
//                x = x + state[i];
//            }else{
//                x = 5.0;
//            }
//            this.stateP[i] = x;
//        }
//        this.output();
//        this.state = this.stateP;
//        this.stateP = this.next;
//    }
//
//    public void backward(){
//        this.truePos--;
//        double x = 0.0;
//        for(int i=0; i<20; i++) {
//            if(this.truePos<=3 || this.truePos>=14){
//                x = Math.abs(this.query(0.4)+(double)(20-this.truePos));
//                x = x + state[i];
//            }else{
//                x = 5.0;
//            }
//            this.stateP[i] = x;
//        }
//        this.output();
//        this.state = this.stateP;
//        this.stateP = this.next;
//    }

    public int getSize(){
        return this.size;
    }

    public double query(double b){
        double a = 0;
        double t = b*2;
        // latent gaussian sample on demand
        for(int i=0; i<12; i++) {
            a += (this.rand.nextDouble()*t-b);
        }
        return (a/2.0);
    }

    public void forward(){
        if(this.truePos<this.size-1) this.truePos++;
    }

    public void backward(){
        if(this.truePos>0) this.truePos--;
    }

    public double forwardSense(){
        int proximity = this.size - this.truePos;

        if (proximity < this.RANGE_MAX) return Math.abs(this.query(this.SENSOR_NOISE_SD)+(double) proximity);
        else return this.RANGE_MAX;
    }

    public double backwardSense(){
        int proximity = this.truePos;

        if (proximity < this.RANGE_MAX) return Math.abs(this.query(this.SENSOR_NOISE_SD)+(double) proximity);
        else return this.RANGE_MAX;
    }

    public void output(){
        double max = 0.0;
        int idx=0;
        this.pTotal = 0;
        for(int i=0; i<20; i++){
            this.pTotal += this.stateP[i];
        }
        for(int i=0; i<20; i++){
            this.stateP[i] /= this.pTotal;
            System.out.printf("| %.3f", this.stateP[i]);
        }

        this.pTotal = 1.0;
        for(int i=0; i<20; i++){
            if (this.stateP[i] > max) {
                max = this.stateP[i];
                idx=i;
            }
        }
        this.likelyPos = idx;
        System.out.print("|");
        System.out.printf(" pTotal = %.3f",this.pTotal);
        System.out.print(" likelyPos = "+this.likelyPos);
        System.out.print(" truePos = "+this.truePos);
        System.out.println();
    }

}
