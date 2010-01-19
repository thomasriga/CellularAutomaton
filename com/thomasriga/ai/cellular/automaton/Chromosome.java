package com.thomasriga.ai.cellular.automaton;

public class Chromosome
{

    Chromosome(int i)
    {
        numOfGenes = i;
        gene = new boolean[i];
        randomGenes();
    }

    Chromosome(int i, boolean aflag[])
    {
        numOfGenes = i;
        gene = new boolean[i];
        copyGenes(aflag, gene);
    }

    Chromosome(int i, boolean aflag[], double d)
    {
        numOfGenes = i;
        gene = new boolean[i];
        copyGenes(aflag, gene);
        fitnessValue = d;
    }

    void randomGenes()
    {
        for(int i = 0; i < numOfGenes; i++)
            if(Math.random() < 0.5D)
                gene[i] = false;
            else
                gene[i] = true;

    }

    void copyGenes(boolean aflag[], boolean aflag1[])
    {
        for(int i = 0; i < numOfGenes; i++)
            if(aflag[i])
                aflag1[i] = true;
            else
                aflag1[i] = false;

    }

    int numOfGenes;
    public boolean gene[];
    public double fitnessValue;
}
