package com.thomasriga.ai.cellular.automaton;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Alife extends Applet
{
    class ButtonHandler
        implements ActionListener
    {

        public void actionPerformed(ActionEvent ev)
        {
            String s = ev.getActionCommand();
            if(s.equals("Reset"))
                canvas.reset();
        }

        ButtonHandler()
        {
        }
    }

    class ListBHandler
        implements ItemListener
    {

        public void itemStateChanged(ItemEvent ev)
        {
            canvas.setParB(listB.getSelectedItems());
        }

        ListBHandler()
        {
        }
    }

    class ListAHandler
        implements ItemListener
    {

        public void itemStateChanged(ItemEvent ev)
        {
            canvas.setParA(listA.getSelectedItems());
        }

        ListAHandler()
        {
        }
    }


    public Alife()
    {
    }

    public void init()
    {
        setBackground(Color.white);
        prepareLists();
        prepareCanvas();
        prepareButton();
        panelE = new Panel();
        panelE.add(canvas);
        panelD = new Panel();
        panelD.add(reset);
        panelF = new Panel();
        panelF.setLayout(new BorderLayout());
        panelF.add(panelE);
        panelF.add(panelD, "South");
        panelA = new Panel();
        panelA.setLayout(new BorderLayout());
        textAA = new Label("birth");
        panelA.add(textAA, "North");
        panelA.add(listA);
        panelB = new Panel();
        panelB.setLayout(new BorderLayout());
        textBA = new Label("death");
        panelB.add(textBA, "North");
        panelB.add(listB);
        panelC = new Panel();
        panelC.setLayout(new GridLayout(1, 2));
        panelC.add(panelA);
        panelC.add(panelB);
        setLayout(new GridLayout(1, 2));
        add(panelF);
        add(panelC);
        resize(450, 200);
    }

    public void prepareLists()
    {
        listA = new List(8, true);
        listA.add("1");
        listA.add("2");
        listA.add("3");
        listA.add("4");
        listA.add("5");
        listA.add("6");
        listA.add("7");
        listA.add("8");
        listA.select(2);
        listB = new List(8, true);
        listB.add("1");
        listB.add("2");
        listB.add("3");
        listB.add("4");
        listB.add("5");
        listB.add("6");
        listB.add("7");
        listB.add("8");
        listB.select(0);
        listB.select(3);
        listB.select(4);
        listB.select(5);
        listB.select(6);
        listB.select(7);
        listA.addItemListener(new ListAHandler());
        listB.addItemListener(new ListBHandler());
    }

    public void prepareCanvas()
    {
        canvas = new Can();
        canvas.setSize(160, 160);
    }

    public void prepareButton()
    {
        reset = new Button("Reset");
        reset.addActionListener(new ButtonHandler());
    }

    Can canvas;
    Button reset;
    List listA;
    List listB;
    Label textAA;
    Label textBA;
    Panel panelA;
    Panel panelB;
    Panel panelC;
    Panel panelD;
    Panel panelE;
    Panel panelF;
}
