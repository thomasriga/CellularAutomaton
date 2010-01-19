package com.thomasriga.ai.cellular.automaton;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenA extends Applet
{
    class ButtonHandler
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            String s = actionevent.getActionCommand();
            if(s.equals("Start"))
                canvas.start();
            if(s.equals("Stop"))
                canvas.stop();
            if(s.equals("Reset"))
            {
                mutationRateField.setText(String.valueOf(canvas.defaultMutationRate));
                crossoverParameterField.setText(String.valueOf(canvas.defaultCrossoverParameter));
                populationSizeField.setText(String.valueOf(canvas.defaultPopulationSize));
                tournamentSizeField.setText(String.valueOf(canvas.defaultTournamentSize));
                delayField.setText(String.valueOf(canvas.defaultDelay));
                canvas.reset(canvas.defaultPopulationSize, canvas.defaultTournamentSize, canvas.defaultMutationRate, canvas.defaultCrossoverParameter, canvas.defaultDelay);
            }
            if(s.equals("Submit"))
            {
                boolean flag = false;
                double d;
                if(checkForDouble(mutationRateField))
                {
                    String s1 = mutationRateField.getText();
                    d = (new Double(s1)).doubleValue();
                } else
                {
                    d = canvas.defaultMutationRate;
                    mutationRateField.setText(String.valueOf(canvas.defaultMutationRate));
                }
                double d1;
                if(checkForDouble(crossoverParameterField))
                {
                    String s2 = crossoverParameterField.getText();
                    d1 = (new Double(s2)).doubleValue();
                } else
                {
                    d1 = canvas.defaultCrossoverParameter;
                    crossoverParameterField.setText(String.valueOf(canvas.defaultCrossoverParameter));
                }
                int k;
                if(checkForInteger(delayField))
                {
                    String s3 = delayField.getText();
                    k = Integer.parseInt(s3);
                } else
                {
                    k = canvas.defaultDelay;
                    delayField.setText(String.valueOf(canvas.defaultDelay));
                }
                int i;
                if(checkForInteger(populationSizeField))
                {
                    String s4 = populationSizeField.getText();
                    if(Integer.parseInt(s4) != canvas.genetic.populationSize)
                        flag = true;
                    i = Integer.parseInt(s4);
                } else
                {
                    i = canvas.defaultPopulationSize;
                    populationSizeField.setText(String.valueOf(canvas.defaultPopulationSize));
                    flag = true;
                }
                int j;
                if(checkForInteger(tournamentSizeField))
                {
                    String s5 = tournamentSizeField.getText();
                    if(Integer.parseInt(s5) != canvas.genetic.tournamentSize)
                        flag = true;
                    j = Integer.parseInt(s5);
                } else
                {
                    j = canvas.defaultTournamentSize;
                    tournamentSizeField.setText(String.valueOf(canvas.defaultTournamentSize));
                    flag = true;
                }
                if(flag || canvas.jobFinished)
                {
                    canvas.reset(i, j, d, d1, k);
                } else
                {
                    canvas.genetic.mutationProb = d;
                    canvas.genetic.crossoverParameter = d1;
                    canvas.delay = k;
                    if(canvas.paused)
                        canvas.start();
                }
            }
            if(s.equals("AND"))
                canvas.operator = 1;
            if(s.equals("OR"))
                canvas.operator = 2;
            if(s.equals("XOR"))
                canvas.operator = 3;
            if(s.equals("IF..THEN"))
                canvas.operator = 4;
        }

        ButtonHandler()
        {
        }
    }


    public void init()
    {
        setBackground(Color.white);
        setLayout(new FlowLayout());
        canvas.setBounds(0, 0, 520, 310);
        add(canvas);
        setupButtons();
        resize(screenWidth, screenHeight);
    }

    void setupButtons()
    {
        startButton.addActionListener(new ButtonHandler());
        stopButton.addActionListener(new ButtonHandler());
        resetButton.addActionListener(new ButtonHandler());
        valuesButton.addActionListener(new ButtonHandler());
        ANDButton.addActionListener(new ButtonHandler());
        ORButton.addActionListener(new ButtonHandler());
        XORButton.addActionListener(new ButtonHandler());
        IFTHENButton.addActionListener(new ButtonHandler());
        Panel panel = new Panel();
        panel.setLayout(new GridLayout(2, 5));
        Panel panel1 = new Panel();
        panel1.add(stopButton);
        panel1.add(startButton);
        panel1.add(resetButton);
        panel1.add(valuesButton);
        panel1.add(ANDButton);
        panel1.add(ORButton);
        panel1.add(XORButton);
        panel1.add(IFTHENButton);
        panel.add(mutationRateField);
        panel.add(crossoverParameterField);
        panel.add(populationSizeField);
        panel.add(tournamentSizeField);
        panel.add(delayField);
        panel.add(mutationRateLabel);
        panel.add(crossoverParameterLabel);
        panel.add(populationSizeLabel);
        panel.add(tournamentSizeLabel);
        panel.add(delayLabel);
        add(panel);
        add(panel1);
    }

    public boolean checkForDouble(TextField textfield)
    {
        String s = textfield.getText();
        boolean flag = false;
        int j = 0;
        if(s.length() == 0)
            return false;
        String s1 = ".";
        for(int i = 0; i < s.length(); i++)
            if(!Character.isDigit(s.charAt(i)))
                if(s.charAt(i) == s1.charAt(0))
                    j++;
                else
                    flag = true;

        return !flag && j <= 1 && (j != 1 || s.length() != 1);
    }

    public boolean checkForInteger(TextField textfield)
    {
        String s = textfield.getText();
        if(s.length() == 0)
            return false;
        for(int i = 0; i < s.length(); i++)
            if(!Character.isDigit(s.charAt(i)))
                return false;

        return true;
    }

    public GenA()
    {
        canvas = new MyCanvas();
        startButton = new Button("Start");
        stopButton = new Button("Stop");
        resetButton = new Button("Reset");
        valuesButton = new Button("Submit");
        ANDButton = new Button("AND");
        ORButton = new Button("OR");
        XORButton = new Button("XOR");
        IFTHENButton = new Button("IF..THEN");
        mutationRateField = new TextField(String.valueOf(canvas.defaultMutationRate), 8);
        mutationRateLabel = new Label("Mutation rate");
        crossoverParameterField = new TextField(String.valueOf(canvas.defaultCrossoverParameter), 8);
        crossoverParameterLabel = new Label("Crossover param.   ");
        populationSizeField = new TextField(String.valueOf(canvas.defaultPopulationSize), 8);
        populationSizeLabel = new Label("Population size");
        tournamentSizeField = new TextField(String.valueOf(canvas.defaultTournamentSize), 8);
        tournamentSizeLabel = new Label("Tournament size");
        delayField = new TextField(String.valueOf(canvas.defaultDelay), 8);
        delayLabel = new Label("Delay");
        screenWidth = 520;
        screenHeight = 400;
    }

    MyCanvas canvas;
    Button startButton;
    Button stopButton;
    Button resetButton;
    Button valuesButton;
    Button ANDButton;
    Button ORButton;
    Button XORButton;
    Button IFTHENButton;
    TextField mutationRateField;
    Label mutationRateLabel;
    TextField crossoverParameterField;
    Label crossoverParameterLabel;
    TextField populationSizeField;
    Label populationSizeLabel;
    TextField tournamentSizeField;
    Label tournamentSizeLabel;
    TextField delayField;
    Label delayLabel;
    int screenWidth;
    int screenHeight;
}
