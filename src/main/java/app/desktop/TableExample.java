package app.desktop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TableExample extends JFrame {

    public TableExample() {
        super("Table Example");

        DefaultTableModel dm = new DefaultTableModel();

        Object[][] data = new Object[][]{
                {"...", "foo"}, {"...", "bar"}};

        Object[] columns = new Object[]{"", "String"};

        dm.setDataVector(data, columns);

        JTable table = new JTable(dm);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn column = table.getColumn("");
        column.setCellRenderer(new ButtonRenderer());
        column.setCellEditor(new ButtonEditor(new JCheckBox()));

        column.setWidth(3);

        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add(scroll);
        setSize(400, 100);
        setVisible(true);
    }

    public static void main(String[] args) {
        TableExample table = new TableExample();
        table.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}
