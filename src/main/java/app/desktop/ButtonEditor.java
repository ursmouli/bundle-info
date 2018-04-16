package app.desktop;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @version 1.0 11/09/98
 */
public class ButtonEditor extends DefaultCellEditor implements ActionListener {
    protected JButton button;
    private String label;
    private boolean isPushed;

    private SimpleTable simpleTable;
    private JTable jTable;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        jTable = jTable;
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            JOptionPane.showMessageDialog(button, label + ": Ouch!");

            /*TreeDemo treeDemo = new TreeDemo(simpleTable);
            JDialog jDialog = new JDialog();
            jDialog.setContentPane(treeDemo);
            jDialog.setSize(new Dimension(200,200));
            jDialog.setTitle("Select value");
            jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jDialog.setVisible(true);

            TableModel model = jTable.getModel();
            model.setValueAt(simpleTable.getDialogData(), jTable.getEditingRow(), jTable.getEditingColumn() -1);*/
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
        fireEditingStopped();
    }

    public SimpleTable getSimpleTable() {
        return simpleTable;
    }

    public void setSimpleTable(SimpleTable simpleTable) {
        this.simpleTable = simpleTable;
    }
}
