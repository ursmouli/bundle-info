package app.desktop.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PropChooserButtonEditor extends DefaultCellEditor implements ActionListener {

    private String label;
    private boolean isPushed;
    private JButton button;

    private JColorChooser colorChooser;
    private JDialog dialog;

    private JTable table;
    private int row;
    private int col;

    public PropChooserButtonEditor(JCheckBox checkBox) {
        super(checkBox);

        colorChooser = new JColorChooser();
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(this);

        this.dialog = JColorChooser.createDialog(button, "Pick a color", true, colorChooser, this, null);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        this.row = row;
        this.col = column;

        this.label = value.toString();
        button.setText(value.toString());

        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }

        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // JOptionPane.showMessageDialog(button, "To call LDS chooser here!");
            // dialog.show();
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dialog.setVisible(true);
        this.dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                table.getModel().setValueAt("@selected", row, 1);
            }
        });
        fireEditingStopped();
    }
}
