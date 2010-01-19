/*
CellularAutomaton Copyright (C) 2010 Thomas Riga

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

Contact the author at thomasriga@gmail.com (http://www.thomasriga.com)
*/

package com.thomasriga.ai.cellular.automaton;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Panel;

public class CA_Button extends Applet
{

    public CA_Button()
    {
    }

    public void init()
    {
        setBackground(Color.white);
        prepareCanvas();
        panelE = new Panel();
        panelE.add(canvas);
        add(panelE);
        resize(165, 165);
    }

    public void prepareCanvas()
    {
        canvas = new Can_Button();
        canvas.setSize(160, 160);
    }

    Can_Button canvas;
    Panel panelE;
}
