package app.desktop.tree;

import javax.swing.*;

public class TreeMain {

    public TreeMain() {
        JFrame frame = new JFrame();
        frame.setTitle("Tree Frame");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        SampleTree sampleTree = new SampleTree();
        sampleTree.setOpaque(true);
        frame.setContentPane(sampleTree);

        frame.pack();
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public static void createAndShowGUI() {
        new TreeMain();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
