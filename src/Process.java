package src;
import java.awt.*;
import javax.swing.*;

public class Process {
    public static Heiarchy h;
    public static Disease d;
    public static void main(String[] args) {
        h = new Heiarchy();
        HeiarchyCreator h0 = new HeiarchyCreator();
       d = new Disease((int)(Math.random()*6) + 1);
        Person p0 = new Person(Math.random()>=0.5, Math.random()>=0.5, true, d);
        Person p1;
        if(d.getDiseaseType()!=DiseaseType.YL)
        p1 = new Person(Math.random()>=0.5, Math.random()>=0.5, false, d);
        else
        p1 = new Person(false,false, false, d);
            Couple couple = new Couple(p0, p1);
        Gen g = new Gen(couple, d);

       	h0.createHeiarchy(couple, 0, g);

         while(!h0.checkAscertainment()){
            h0.clearHeiarchy();
            p0 = new Person(Math.random()>=0.5, Math.random()>=0.5, true, d);
            if(d.getDiseaseType()!=DiseaseType.YL)
                p1 = new Person(Math.random()>=0.5, Math.random()>=0.5, false, d);
            else
                p1 = new Person(false,false, false, d);
            couple = new Couple(p0, p1);
            g = new Gen(couple, d);
            h0.createHeiarchy(couple, 0, g);
        }

        h = h0.getHeiarchy();
        Pedigree p = new Pedigree(h);
        
        p.evaluateDiseases();

        JFrame frame = new JFrame("Traitle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel inputBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Enter your answer:");
        JTextField inputField = new JTextField(15);
        JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		JButton resetButton = new JButton("Restart");
        inputBar.add(label);
        inputBar.add(inputField);
        inputBar.add(submitButton);
		inputBar.add(resetButton);
		inputBar.add(cancelButton);
        //System.out.println(h.getDiseases());
        MyPanel drawingPanel = new MyPanel(h);
        JScrollPane scrollPane = new JScrollPane(drawingPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane, BorderLayout.CENTER);
        drawingPanel.revalidate();
        drawingPanel.repaint();
        frame.setLayout(new BorderLayout());
        frame.add(inputBar, BorderLayout.SOUTH);
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.pack();               
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.add(inputBar, BorderLayout.SOUTH);
        frame.add(drawingPanel, BorderLayout.CENTER);
		inputBar.setBorder(BorderFactory.createEmptyBorder(0, 100, 100, 0)); //get rid off eventually
		inputField.addActionListener(e -> submitButton.doClick());
        submitButton.addActionListener(e -> {
            String answer = inputField.getText().trim();
            boolean correct = Answer.checkAnswer(answer, h.getDiseases());
            if (correct)
                JOptionPane.showMessageDialog(frame, "Correct! Intended Inheritance Pattern: " +d);
            else
                JOptionPane.showMessageDialog(frame, "Incorrect! Correct Answer: " + d);
        });
		resetButton.addActionListener(e -> {       
    	Main.main(null);    
		frame.dispose();        
		});
		cancelButton.addActionListener(e -> System.exit(0));
        frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
