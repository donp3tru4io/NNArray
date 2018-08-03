/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nntest;

/**
 *
 * @author donp3tru4io
 */
public class NNTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        double[][] input = new double[][]
        {
            {0,0,0},
            {0,0,1},
            {0,1,0},
            {0,1,1},
            {1,0,0},
            {1,0,1},
            {1,1,0},
            {1,1,1}
        };
        
        double[] expectation = new double[]{0,1,0,0,1,1,0,1};
        
        NeuralNetwork nn = new NeuralNetwork(3,2,0.08);
        nn.train(input, expectation, 5000);
    }
    
}
