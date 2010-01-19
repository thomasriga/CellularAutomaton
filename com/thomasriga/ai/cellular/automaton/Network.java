/*
CellularAutomaton Copyright (C) 2010 Thomas Riga

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

Contact the author at thomasriga@gmail.com (http://www.thomasriga.com)
*/


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
