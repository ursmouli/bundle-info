package app.desktop.table;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class MyTable extends JPanel {

    protected MyTableModel myTableModel;
    protected JTable table;
    protected TableColumn chooserCol;

    public MyTable() {
        super(new GridLayout(1,0));

        myTableModel = new MyTableModel();
        table = new JTable(myTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem addMenuItem = new JMenuItem("Add");
        JMenuItem delMenuItem = new JMenuItem("Delete");

        popupMenu.add(addMenuItem);
        popupMenu.add(delMenuItem);

        table.setComponentPopupMenu(popupMenu);
        table.getTableHeader().setComponentPopupMenu(popupMenu);

        chooserCol = table.getColumn("");
        chooserCol.setCellEditor(new PropChooserButtonEditor(new JCheckBox()));
        chooserCol.setCellRenderer(new PropChooserButtonRenderer());
        chooserCol.setPreferredWidth(-1);
        chooserCol.setMaxWidth(3);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
}
