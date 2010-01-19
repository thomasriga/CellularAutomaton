/*
CellularAutomaton Copyright (C) 2010 Thomas Riga

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

Contact the author at thomasriga@gmail.com (http://www.thomasriga.com)
*/

package com.thomasriga.ai.cellular.automaton;

import java.awt.*;
import java.util.Vector;

class Can_Button extends Canvas
    implements Runnable
{
    public class Scan
    {

        int NO;
        int NE;
        int EA;
        int SE;
        int SO;
        int SW;
        int WE;
        int NW;

        Scan(int firstField[][], int columns, int rows, int x, int y)
        {
            int tx = x - 1;
            if(tx < 0)
                tx = columns - 1;
            int ty = y - 1;
            if(ty < 0)
                ty = rows - 1;
            NW = firstField[tx][ty];
            ty = y - 1;
            if(ty < 0)
                ty = rows - 1;
            NO = firstField[x][ty];
            tx = x + 1;
            if(tx == columns)
                tx = 0;
            ty = y - 1;
            if(ty < 0)
                ty = rows - 1;
            NE = firstField[tx][ty];
            tx = x + 1;
            if(tx == rows)
                tx = 0;
            EA = firstField[tx][y];
            tx = x + 1;
            if(tx == columns)
                tx = 0;
            ty = y + 1;
            if(ty == rows)
                ty = 0;
            SE = firstField[tx][ty];
            ty = y + 1;
            if(ty == rows)
                ty = 0;
            SO = firstField[x][ty];
            tx = x - 1;
            if(tx < 0)
                tx = columns - 1;
            ty = y + 1;
            if(ty == rows)
                ty = 0;
            SW = firstField[tx][ty];
            tx = x - 1;
            if(tx < 0)
                tx = columns - 1;
            WE = firstField[tx][y];
        }
    }


    public Can_Button()
    {
        frameDelay = 300;
        lastDisplay = 0L;
        parA = new Vector();
        parB = new Vector();
        parA.addElement("3");
        parB.addElement("1");
        parB.addElement("4");
        parB.addElement("5");
        parB.addElement("6");
        parB.addElement("7");
        parB.addElement("8");
        initializeField(firstField, 40, 40);
        copyFields(secondField, firstField, 40, 40);
        repaint();
        animation = new Thread(this);
        animation.start();
    }

    public void paint(Graphics g)
    {
        g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
        for(int x = 0; x < 40; x++)
        {
            for(int y = 0; y < 40; y++)
                if(secondField[x][y] == 1)
                    g.fillOval(x * 2 * 2, y * 2 * 2, 4, 4);

        }

    }

    public void run()
    {
        initializeField(firstField, 40, 40);
        copyFields(secondField, firstField, 40, 40);
        repaint();
        do
        {
            long time;
            do
            {
                move(firstField, secondField);
                copyFields(firstField, secondField, 40, 40);
                time = System.currentTimeMillis();
            } while(time - lastDisplay <= (long)frameDelay);
            repaint();
            try
            {
                Thread.sleep(frameDelay);
            }
            catch(InterruptedException ex) { }
            lastDisplay = time;
        } while(true);
    }

    public void move(int firstField[][], int secondField[][])
    {
        int temp = 0;
        for(int x = 0; x < 40; x++)
        {
            for(int y = 0; y < 40; y++)
            {
                Scan neighbours = new Scan(firstField, 40, 40, x, y);
                temp = neighbours.NW + neighbours.NO + neighbours.NE + neighbours.EA + neighbours.SE + neighbours.SO + neighbours.SW + neighbours.WE;
                for(int z = 0; z < parA.size(); z++)
                    if(temp == (new Integer((String)parA.elementAt(z))).intValue() && firstField[x][y] == 0)
                        secondField[x][y] = 1;

                for(int z = 0; z < parB.size(); z++)
                    if(temp == (new Integer((String)parB.elementAt(z))).intValue() && firstField[x][y] == 1)
                        secondField[x][y] = 0;

            }

        }

    }

    public void copyFields(int firstField[][], int secondField[][], int columns, int rows)
    {
        for(int x = 0; x < columns; x++)
        {
            for(int y = 0; y < rows; y++)
                firstField[x][y] = secondField[x][y];

        }

    }

    public void initializeField(int firstField[][], int columns, int rows)
    {
        for(int x = 0; x < columns; x++)
        {
            for(int y = 0; y < rows; y++)
                if(Math.random() < 0.90000000000000002D)
                    firstField[x][y] = 0;
                else
                    firstField[x][y] = 1;

        }

    }

    public void setParA(String par[])
    {
        int x = 0;
        parA.removeAllElements();
        try
        {
            for(; par[x] != null; x++)
                parA.addElement(par[x]);

        }
        catch(ArrayIndexOutOfBoundsException ev) { }
    }

    public void setParB(String par[])
    {
        int x = 0;
        parB.removeAllElements();
        try
        {
            for(; par[x] != null; x++)
                parB.addElement(par[x]);

        }
        catch(ArrayIndexOutOfBoundsException ev) { }
    }

    public void setDelay(int par)
    {
        frameDelay = par;
    }

    public void reset()
    {
        initializeField(firstField, 40, 40);
        copyFields(secondField, firstField, 40, 40);
    }

    private static final int RADIUS = 2;
    private static final int rows = 40;
    private static final int columns = 40;
    private static int firstField[][] = new int[40][40];
    private static int secondField[][] = new int[40][40];
    Thread animation;
    int frameDelay;
    long lastDisplay;
    Vector parA;
    Vector parB;

}
