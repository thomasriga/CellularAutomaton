/*
CellularAutomaton Copyright (C) 2010 Thomas Riga

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

Contact the author at thomasriga@gmail.com (http://www.thomasriga.com)
*/


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
