package app.desktop.dialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        final ConfirmDialog dialog = new ConfirmDialog(frame);

        final JTree tree = new JTree();
        tree.setVisibleRowCount(5);

        final JScrollPane treeScroll = new JScrollPane(tree);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton button = new JButton("Choose Tree Item");
        button.addActionListener(e -> {
            int result = dialog.showConfirmDialog(treeScroll, "Choose an item");
            if (result == ConfirmDialog.OK_OPTION) {
                System.out.println(tree.getSelectionPath());
            } else {
                System.out.println("User cancelled");
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(button);
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
