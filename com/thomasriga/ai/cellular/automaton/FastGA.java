/*
CellularAutomaton Copyright (C) 2010 Thomas Riga

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

Contact the author at thomasriga@gmail.com (http://www.thomasriga.com)
*/


package com.thomasriga.ai.cellular.automaton;

import java.util.Random;

public class FastGA
{

    public FastGA(int i, int j, int k, double d, double d1)
    {
        populationSize = i;
        mutationProb = d;
        tournamentSize = j;
        numOfGenes = k;
        crossoverParameter = d1;
        population = new Chromosome[populationSize];
        matingPool = new int[2];
    }

    public void loadGenes()
    {
        for(int i = 0; i < populationSize; i++)
            population[i] = new Chromosome(numOfGenes);

    }

    public void tournamentSelection()
    {
        int ai[] = new int[tournamentSize];
        Random random = new Random();
        for(int i = 0; i < tournamentSize; i++)
        {
            int k = random.nextInt();
            if(k < 0)
                k = -k;
            k %= populationSize;
            ai[i] = k;
        }

        matingPool[0] = bestCandidate(ai);
        for(int j = 0; j < tournamentSize; j++)
        {
            int l = random.nextInt();
            if(l < 0)
                l = -l;
            l %= populationSize;
            ai[j] = l;
        }

        matingPool[1] = bestCandidate(ai);
    }

    public int parametrizedUniformCrossover()
    {
        int j = worstCandidate();
        int k;
        int l;
        if(population[matingPool[0]].fitnessValue >= population[matingPool[1]].fitnessValue)
        {
            k = matingPool[0];
            l = matingPool[1];
        } else
        {
            k = matingPool[1];
            l = matingPool[0];
        }
        for(int i = 0; i < numOfGenes; i++)
        {
            double d = Math.random();
            if(d <= crossoverParameter)
            {
                if(population[k].gene[i])
                    population[j].gene[i] = true;
                else
                    population[j].gene[i] = false;
            } else
            if(population[l].gene[i])
                population[j].gene[i] = true;
            else
                population[j].gene[i] = false;
        }

        return j;
    }

    public void mutation(Chromosome chromosome)
    {
        for(int i = 0; i < numOfGenes; i++)
        {
            double d = Math.random();
            if(d <= mutationProb)
                if(!chromosome.gene[i])
                    chromosome.gene[i] = true;
                else
                    chromosome.gene[i] = false;
        }

    }

    void copyGenes(boolean aflag[], boolean aflag1[])
    {
        for(int i = 0; i < numOfGenes; i++)
            if(aflag[i])
                aflag1[i] = true;
            else
                aflag1[i] = false;

    }

    int bestCandidate(int ai[])
    {
        int j = 0;
        double d = 0.0D;
        for(int i = 0; i < tournamentSize; i++)
            if(population[ai[i]].fitnessValue > d)
            {
                d = population[ai[i]].fitnessValue;
                j = ai[i];
            }

        return j;
    }

    int worstCandidate()
    {
        int j = 0;
        double d = 100D;
        for(int i = 0; i < populationSize; i++)
            if(population[i].fitnessValue < d)
            {
                d = population[i].fitnessValue;
                j = i;
            }

        return j;
    }

    public static Chromosome population[];
    public static int matingPool[];
    public int populationSize;
    public int tournamentSize;
    public int numOfGenes;
    public double mutationProb;
    public double crossoverParameter;
}
