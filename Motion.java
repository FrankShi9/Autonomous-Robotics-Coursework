package com.company;

public class Motion {
    protected double pTotal = 0.0;
    protected int truePos = 9;
    protected int likelyPos = -1;

    protected static double p_fn = .25;
    protected static double p_fm = .5;
    protected static double p_lfm = .75;
    protected static double p_fm2 = .25;

    protected double[] next = new double[20];
    protected double[] state = new double[20];
    protected double[] stateP = new double[20];

    public Motion(){
        for(int i=0; i<20; i++){
            if (i == 9){
                this.state[i] = 1.0;
                this.stateP[i] = 1.0;
            }else {
                this.state[i] = 0;
                this.stateP[i] = 0;
            }
            this.next[i] = 0;
        }

    }

//    public void forward(){
//        //this.truePos++;
//        for(int i=2; i<20; i++){
//            if(i < 17){
//                this.stateP[i] = p_fn * this.state[i] + p_fm * this.state[i - 1] + p_fm2 * this.state[i - 2];
//            }else if(i == 18){
//                this.stateP[i] = p_fn * this.state[i] + p_lfm * this.state[i - 1];
//            }else{
//                this.stateP[i] = this.state[i-1];
//            }
//        }
//        this.state = this.stateP;
//        this.stateP = this.next;
//        this.output();
//    }
    public void refresh(){
        for(int i=0; i<20; i++){
            this.next[i] = 0;
        }
    }

    public void forward(){
        this.truePos++;
        double bel0=0.0, bel1=0.0, bel2=0.0;
        for(int i=0; i<20; i++){
            if(i>=2){
                bel2 = state[i-2];
            }
            if(i>=1){
                bel1 = state[i-1];
            }
            bel0 = state[i];
            if(i == state.length-1){
                this.stateP[i] = p_fm2 * bel2 + p_lfm * bel1 + bel0;
            }else{
                this.stateP[i] = p_fn * bel0 + p_fm * bel1 + p_fm2 * bel2;
            }
        }
        this.output();
        this.state = this.stateP;
        this.stateP = this.next;

    }

    public void backward(){
        this.truePos--;
        double bel0=0.0, bel1=0.0, bel2=0.0;
        for(int i=0; i<20; i++){
            if(i<state.length-2){
                bel2 = state[i+2];
            }
            if(i<state.length-1){
                bel1 = state[i+1];
            }
            bel0 = state[i];
            if(i == 0){
                this.stateP[i] = p_fm2 * bel2 + 0.75 * bel1 + bel0;
            }else{
                this.stateP[i] = p_fn * bel0 + p_fm * bel1 + p_fm2 * bel2;
            }
        }
        this.output();
        this.state = this.stateP;
        this.stateP = this.next;

    }

//    public void backward(){
//        //this.truePos--;
//        for(int i=2; i<17; i++){
//            this.stateP[i] = p_fn * this.state[i] + p_fm * this.state[i - 1] + p_fm2 * this.state[i - 2];
//        }
//        this.state = this.stateP;
//        this.stateP = this.next;
//        this.output();
//    }

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

