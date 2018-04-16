package app.desktop.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PropChooserButtonRenderer extends JButton implements TableCellRenderer {

    public PropChooserButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());

        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }

        return this;
    }
}
