/*
CellularAutomaton Copyright (C) 2010 Thomas Riga

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

Contact the author at thomasriga@gmail.com (http://www.thomasriga.com)
*/


package com.thomasriga.ai.cellular.automaton;

import java.awt.*;

class MyCanvas extends Canvas
    implements Runnable
{

    public MyCanvas()
    {
        defaultPopulationSize = 100;
        defaultTournamentSize = 10;
        numOfBits = 208;
        defaultDelay = 100;
        operator = 3;
        defaultMutationRate = 0.01D;
        defaultCrossoverParameter = 0.5D;
        paused = false;
        jobFinished = false;
        t = 0;
        delay = defaultDelay;
        genetic = new FastGA(defaultPopulationSize, defaultTournamentSize, numOfBits, defaultMutationRate, defaultCrossoverParameter);
        genetic.loadGenes();
        animation = new Thread(this);
        animation.start();
    }

    public void paint(Graphics g)
    {
        double ad[] = decode(FastGA.population[bestCandidate()]);
        g.clearRect(1, 1, 780, 400);
        g.drawString("status", 10, 10);
        g.drawRect(10, 12, 134, 92);
        g.drawString("cycles: " + t, 14, 26);
        if(operator == 1)
            g.drawString("operator: AND", 14, 38);
        else
        if(operator == 2)
            g.drawString("operator: OR", 14, 38);
        else
        if(operator == 3)
            g.drawString("operator: XOR", 14, 38);
        else
        if(operator == 4)
            g.drawString("operator: IF..THEN..", 14, 38);
        g.drawString("mutation: " + genetic.mutationProb, 14, 50);
        g.drawString("crossover: " + genetic.crossoverParameter, 14, 62);
        g.drawString("population: " + genetic.populationSize, 14, 74);
        g.drawString("tournament: " + genetic.tournamentSize, 14, 86);
        g.drawString("delay: " + delay, 14, 98);
        g.drawString("best network", 151, 10);
        g.drawRect(151, 12, 359, 176);
        g.drawOval(170, 75, 15, 15);
        g.drawOval(170, 135, 15, 15);
        g.drawOval(320, 45, 15, 15);
        g.drawOval(320, 105, 15, 15);
        g.drawOval(320, 165, 15, 15);
        g.drawOval(470, 105, 15, 15);
        g.drawLine(185, 82, 320, 52);
        g.drawLine(185, 142, 320, 52);
        g.drawLine(185, 82, 320, 112);
        g.drawLine(185, 142, 320, 112);
        g.drawLine(185, 82, 320, 172);
        g.drawLine(185, 142, 320, 172);
        g.drawLine(335, 52, 470, 112);
        g.drawLine(335, 112, 470, 112);
        g.drawLine(335, 172, 470, 112);
        String s = String.valueOf(ad[0]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 310, 40);
        g.drawRect(308, 29, 34, 13);
        s = String.valueOf(ad[1]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 310, 100);
        g.drawRect(308, 89, 34, 13);
        s = String.valueOf(ad[2]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 310, 160);
        g.drawRect(308, 149, 34, 13);
        s = String.valueOf(ad[3]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 460, 100);
        g.drawRect(458, 89, 34, 13);
        s = String.valueOf(ad[4]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 220, 67);
        s = String.valueOf(ad[5]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 223, 88);
        s = String.valueOf(ad[6]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 175, 107);
        s = String.valueOf(ad[7]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 175, 128);
        s = String.valueOf(ad[8]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 222, 145);
        s = String.valueOf(ad[9]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 220, 168);
        s = String.valueOf(ad[10]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 400, 78);
        s = String.valueOf(ad[11]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 395, 108);
        s = String.valueOf(ad[12]);
        if(s.length() > 5)
            s = s.substring(0, 5);
        g.drawString(s, 400, 158);
        g.drawString("fitness distribution", 10, 193);
        g.drawRect(10, 195, genetic.populationSize * 5, 105);
        for(int i = 0; i < genetic.populationSize; i++)
            g.drawRect(i * 5 + 10, 300 - Math.round((float)FastGA.population[i].fitnessValue * 50F), 5, Math.round((float)FastGA.population[i].fitnessValue * 50F));

    }

    public void fitnessFunction(int i)
    {
        int l1 = 0;
        double d1 = 0.0D;
        int ai[][] = new int[4][2];
        ai[0][0] = 1;
        ai[0][1] = 1;
        ai[1][0] = 1;
        ai[1][1] = 0;
        ai[2][0] = 0;
        ai[2][1] = 1;
        ai[3][0] = 0;
        ai[3][1] = 0;
        double ad1[] = new double[4];
        if(operator == 1)
        {
            ad1[0] = 1.0D;
            ad1[1] = 0.0D;
            ad1[2] = 0.0D;
            ad1[3] = 0.0D;
        }
        if(operator == 2)
        {
            ad1[0] = 1.0D;
            ad1[1] = 1.0D;
            ad1[2] = 1.0D;
            ad1[3] = 0.0D;
        }
        if(operator == 3)
        {
            ad1[0] = 0.0D;
            ad1[1] = 1.0D;
            ad1[2] = 1.0D;
            ad1[3] = 0.0D;
        }
        if(operator == 4)
        {
            ad1[0] = 1.0D;
            ad1[1] = 0.0D;
            ad1[2] = 1.0D;
            ad1[3] = 1.0D;
        }
        Network network = new Network(2, 3, 1);
        double ad[] = decode(FastGA.population[i]);
        for(int j = 0; j < network.biasArraySize; j++)
        {
            network.bias[j] = ad[l1];
            l1++;
        }

        for(int k = 0; k < network.inputArraySize; k++)
        {
            for(int j1 = 0; j1 < network.hiddenArraySize; j1++)
            {
                network.inputToHiddenWeights[k][j1] = ad[l1];
                l1++;
            }

        }

        for(int l = 0; l < network.hiddenArraySize; l++)
        {
            for(int k1 = 0; k1 < network.outputArraySize; k1++)
            {
                network.hiddenToOutputWeights[l][k1] = ad[l1];
                l1++;
            }

        }

        l1 = 0;
        for(int i1 = 0; i1 < 4; i1++)
        {
            network.input[0] = ai[i1][0];
            network.input[1] = ai[i1][1];
            network.forwardPass();
            double d = ad1[i1] - network.output[0];
            if(d < 0.0D)
                d = 0.0D - d;
            d1 += d;
        }

        FastGA.population[i].fitnessValue = 2D - d1;
        d1 = 0.0D;
    }

    public void run()
    {
        for(int i = 0; i < genetic.populationSize; i++)
            fitnessFunction(i);

        do
        {
            genetic.tournamentSelection();
            newCandidate = genetic.parametrizedUniformCrossover();
            genetic.mutation(FastGA.population[newCandidate]);
            fitnessFunction(newCandidate);
            if(FastGA.population[newCandidate].fitnessValue > 1.8999999999999999D)
                jobFinished = true;
            t++;
            long l = System.currentTimeMillis();
            if(l - lastDisplay > (long)delay)
            {
                repaint();
                try
                {
                    Thread.sleep(delay);
                }
                catch(InterruptedException _ex) { }
                lastDisplay = l;
            }
        } while(!jobFinished);
        repaint();
    }

    public double[] decode(Chromosome chromosome)
    {
        double ad[] = new double[13];
        int ai[] = new int[52];
        for(int i = 0; i < 52; i++)
        {
            ai[i] = toDecimal(i * 4, 4, chromosome);
            if(ai[i] > 9)
                ai[i] = 9;
        }

        for(int j = 0; j < 13; j++)
            ad[j] = ((double)ai[j * 4] * 10D + (double)ai[j * 4 + 1] + (double)ai[j * 4 + 2] * 0.10000000000000001D + (double)ai[j * 4 + 3] * 0.01D) - 45D;

        return ad;
    }

    public void start()
    {
        if(paused)
        {
            animation.resume();
            paused = false;
        }
    }

    public void stop()
    {
        if(!paused)
        {
            animation.suspend();
            paused = true;
        }
    }

    public void reset(int i, int j, double d, double d1, int k)
    {
        animation.stop();
        t = 0;
        jobFinished = false;
        paused = false;
        delay = k;
        genetic = new FastGA(i, j, numOfBits, d, d1);
        genetic.loadGenes();
        animation = new Thread(this);
        animation.start();
    }

    public int bestCandidate()
    {
        int j = 0;
        double d = 0.0D;
        for(int i = 0; i < genetic.populationSize; i++)
            if(FastGA.population[i].fitnessValue > d)
            {
                d = FastGA.population[i].fitnessValue;
                j = i;
            }

        return j;
    }

    public int toDecimal(int i, int j, Chromosome chromosome)
    {
        int k = 0;
        byte byte0 = 2;
        for(int i1 = i; i1 < i + j; i1++)
            if(chromosome.gene[i1])
            {
                int l = 2;
                switch(i1 - i)
                {
                case 0: // '\0'
                    l = 1;
                    break;

                case 1: // '\001'
                    l = 2;
                    break;

                default:
                    for(int j1 = 1; j1 < i1 - i; j1++)
                        l *= 2;

                    break;
                }
                k += l;
            }

        return k;
    }

    int defaultPopulationSize;
    int defaultTournamentSize;
    int numOfBits;
    int defaultDelay;
    int delay;
    int t;
    int newCandidate;
    int operator;
    double defaultMutationRate;
    double defaultCrossoverParameter;
    boolean paused;
    boolean jobFinished;
    FastGA genetic;
    Thread animation;
    long lastDisplay;
}
