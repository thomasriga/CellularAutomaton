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
