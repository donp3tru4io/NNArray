/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nntest;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author donp3tru4io
 */
public class NeuralNetwork {
    
    private int input,hidden;
    double learningRate;
    private double[] sensor,neuron; 
    private double[][] wItoH;
    private double[] wHtoO;
    
    public NeuralNetwork(int _input,int _hidden,double _learningRate)
    {
        input = _input;
        hidden = _hidden;
        learningRate = _learningRate;
        //sensor = new double[input];
        neuron = new double[hidden];
        wItoH = new double[input][hidden];
        wHtoO = new double[hidden];
        randWeight();
    }
    
    private void randWeight()
    {
        for(int i = 0;i<input;i++)
        {
            for(int j = 0;j<hidden;j++)
            {
                wItoH[i][j] = ThreadLocalRandom.current().nextDouble(0.5);
            }
        }
        for(int i = 0; i<hidden;i++)
        {
            wHtoO[i] = ThreadLocalRandom.current().nextDouble(1);
        }
    }
    
    private double f(double x)
    {
        return 1.0/(1+Math.exp(-x));
    }
    
    private double fdx(double x)
    {
        return f(x)*(1 - f(x));
    }
    
    private double sumHidden(int n)
    {
        double sum = 0;
        for(int i = 0;i<input;i++)
        {
            sum+= sensor[i]*wItoH[i][n];
        }
        return sum;
    }
    
    private void calculateHidden()
    {
        for(int i = 0;i<hidden;i++)
        {
            neuron[i]=f(sumHidden(i));
        }
    }
    
    private double sumOutput()
    {
        double sum = 0;
        for(int i = 0;i<hidden;i++)
        {
            sum+= neuron[i]*wHtoO[i];
        }
        return sum;
    }
    
    public double predict(double[] _input)
    {
        sensor = _input.clone();
        calculateHidden();
        return f(sumOutput());
    }
    
    private double dw(double error)
    {
        return error*fdx(error);
    }
    
    private double changewHtoO(double error)
    {
        double dW = dw(error);
        for(int i = 0;i<hidden;i++)
        {
            wHtoO[i]-=neuron[i]*dW*learningRate;
        }
        return dW;
    }
    
    private void changewItoH(double dW)
    {
        for(int i = 0;i<hidden;i++)
        {
            double error = wHtoO[i]*dW;
            double _dW=error*fdx(error);
            for(int j =0;j<input;j++)
            {
                wItoH[j][i]-=sensor[j]*_dW*learningRate;
            }
        }
    }
    
    public void train(double[][] _input,double[] expectation,int ages)
    {
        for(int i = 0; i < ages;i++)
        {
            double mse = 0;
            for(int j = 0; j < expectation.length;j++ )
            {
                double error = predict(_input[j])-expectation[j];
                mse+=error*error;
                double dW = changewHtoO(error);
                changewItoH(dW);
            }
            System.out.println(Math.sqrt(mse/8));
        }
        
        for(int i = 0;i<expectation.length;i++)
        {
            System.out.println("prediction = "+predict(_input[i])+" expectation = " + expectation[i]);
        }
        
        /*for(int i = 0;i<input;i++)
        {
            for(int j=0;j<hidden;j++)
            System.out.println("I"+i+"H"+j+" = "+wItoH[i][j]);
        }
        
        for(int i = 0;i<input;i++)
        {
            System.out.println(sensor[i]);
        }
        
        for(int i = 0; i<hidden;i++)
        {
            System.out.println("H"+i+"O = "+wHtoO[i]);
        }*/
    }
       
    
}