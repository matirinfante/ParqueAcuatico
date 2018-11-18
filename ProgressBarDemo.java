/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TPOF;

/**
 *
 * @author Kurito
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ProgressBarDemo extends JFrame {

    public final static int ONE_SECOND = 1000;

    private JProgressBar progressBar;
    private Timer timer;
    private JButton startButton;
    private LongTask task;
    private JTextArea taskOutput;
    private String newline;

    public ProgressBarDemo() {
        super("ProgressBarDemo");
        newline = System.getProperty("line.separator");

        task = new LongTask();

        //create the demo's UI
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(new ButtonListener());

        progressBar = new JProgressBar(0, task.getLengthOfTask());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5, 5, 5, 5));
        taskOutput.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(progressBar);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        //create a timer and a task
        timer = new Timer(ONE_SECOND, new TimerListener());
    }

    //the actionPerformed method in this class
    //is called each time the Timer "goes off"
    class TimerListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            progressBar.setValue(task.getCurrent());
            taskOutput.append(task.getMessage() + newline);
            taskOutput.setCaretPosition(taskOutput.getDocument().getLength());
            if (task.done()) {
                Toolkit.getDefaultToolkit().beep();
                timer.stop();
                startButton.setEnabled(true);
                progressBar.setValue(progressBar.getMinimum());
            }
        }
    }

    //the actionPerformed method in this class
    //when the user presses the start button
    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            startButton.setEnabled(false);
            task.go();
            timer.start();
        }
    }

    public static void main(String[] args) {

        JFrame frame = new ProgressBarDemo();

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(l);

        frame.pack();
        frame.setVisible(true);
    }
}
