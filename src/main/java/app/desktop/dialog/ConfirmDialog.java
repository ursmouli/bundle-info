package app.desktop.dialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ConfirmDialog extends JDialog {

    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;

    private int result = -1;

    JPanel content;

    public ConfirmDialog(Frame parent) {
        super(parent, true);

        JPanel gui = new JPanel(new BorderLayout(3, 3));
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        content = new JPanel(new BorderLayout());
        gui.add(content, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(4));
        gui.add(buttons, BorderLayout.SOUTH);

        JButton ok = new JButton("OK");
        buttons.add(ok);
        ok.addActionListener(e->{
            result = OK_OPTION;
            setVisible(false);
        });

        JButton cancel = new JButton("Cancel");
        buttons.add(cancel);
        cancel.addActionListener(e->{
            result = CANCEL_OPTION;
            setVisible(false);
        });
        setContentPane(gui);
    }
    public int showConfirmDialog(JComponent child, String title) {
        setTitle(title);
        content.removeAll();
        content.add(child, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
        return result;
    }
}
