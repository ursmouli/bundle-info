package app.desktop.tree;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SampleTree extends JPanel {

    public SampleTree() {
        super(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));
        DefaultMutableTreeNode style = new DefaultMutableTreeNode("Style");
        DefaultMutableTreeNode color = new DefaultMutableTreeNode("Color");

        style.add(color);

        DefaultMutableTreeNode red = new DefaultMutableTreeNode("Red");
        DefaultMutableTreeNode green = new DefaultMutableTreeNode("Green");
        DefaultMutableTreeNode blue = new DefaultMutableTreeNode("Blue");
        DefaultMutableTreeNode black = new DefaultMutableTreeNode("Black");

        color.add(red);
        color.add(green);
        color.add(blue);
        color.add(black);

        JTree tree = new JTree(style);
        tree.setOpaque(true);
        tree.setSize(200, 200);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode == null) {
                    return;
                }

                if (selectedNode.isLeaf()) {
                    System.out.println(selectedNode.toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree);
        //scrollPane.add(BorderLayout.CENTER, scrollPane);

        setSize(200, 200);

        add(BorderLayout.CENTER, scrollPane);
        setVisible(true);
    }
}
