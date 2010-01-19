package com.thomasriga.ai.cellular.automaton;

public class Network
{

    public Network(int i, int j, int k)
    {
        inputArraySize = i;
        hiddenArraySize = j;
        outputArraySize = k;
        biasArraySize = j + k;
        initializeArrays();
    }

    void initializeArrays()
    {
        input = new int[inputArraySize];
        hidden = new double[hiddenArraySize];
        output = new double[outputArraySize];
        target = new int[outputArraySize];
        bias = new double[biasArraySize];
        inputToHiddenWeights = new double[inputArraySize][hiddenArraySize];
        hiddenToOutputWeights = new double[hiddenArraySize][outputArraySize];
    }

    public void forwardPass()
    {
        double d = 0.0D;
        for(int k = 0; k < hiddenArraySize; k++)
        {
            for(int i = 0; i < inputArraySize; i++)
                d += (double)input[i] * inputToHiddenWeights[i][k];

            hidden[k] = 1.0D / (1.0D + Math.exp(-1D * (d + bias[k])));
            d = 0.0D;
        }

        for(int l = 0; l < outputArraySize; l++)
        {
            for(int j = 0; j < hiddenArraySize; j++)
                d += hidden[j] * hiddenToOutputWeights[j][l];

            output[l] = 1.0D / (1.0D + Math.exp(-1D * (d + bias[l + hiddenArraySize])));
            d = 0.0D;
        }

    }

    public int input[];
    public int target[];
    public int inputArraySize;
    public int hiddenArraySize;
    public int outputArraySize;
    public int biasArraySize;
    public double hidden[];
    public double output[];
    public double bias[];
    public double inputToHiddenWeights[][];
    public double hiddenToOutputWeights[][];
}
