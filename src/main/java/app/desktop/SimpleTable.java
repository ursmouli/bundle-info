package app.desktop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleTable extends JFrame implements ActionListener {

    private String dialogData;

    private JTable table;

    private DefaultTableModel tableModel = new DefaultTableModel();

    private Object[][] data = new Object[][]{
            {"foo1", "bar1", "..."}, {"foo2", "bar2", "..."}};

    private Object[] columns = new Object[]{"Foo Col", "Bar Col", ""};

    private JPopupMenu popupMenu;
    private JMenuItem addItem;
    private JMenuItem deleteItem;

    public SimpleTable() {
        super("Table Example");

        popupMenu = new JPopupMenu();
        addItem = new JMenuItem("Add");
        addItem.addActionListener(this);
        deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(this);
        popupMenu.add(addItem);
        popupMenu.add(deleteItem);

        tableModel.setDataVector(data, columns);

        table = new JTable(tableModel);
        table.getTableHeader().setComponentPopupMenu(popupMenu);
        table.setComponentPopupMenu(popupMenu);
        table.setInheritsPopupMenu(true);

        TableColumn column = table.getColumn("");
        column.setCellRenderer(new ButtonRenderer());

        ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox());
        buttonEditor.setSimpleTable(this);
        column.setCellEditor(buttonEditor);
        column.setPreferredWidth(-1);
        column.setMaxWidth(3);

        tableModel.addTableModelListener(e -> {
            // updateUI below throws AWT NullPointerException
            // table.updateUI();
        });

        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add(scroll, BorderLayout.CENTER);
        setSize(300, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        SimpleTable table = new SimpleTable();
        table.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JMenuItem actionItem = (JMenuItem) actionEvent.getSource();
        if (actionItem == addItem) {
            System.out.println("add sr:" + table.getSelectedRow());
            int selectedRow = table.getRowCount() + 1;
            tableModel.addRow(new Object[] { "foo" + selectedRow, "bar" + selectedRow, "..." });
        }
        if (actionItem == deleteItem) {
            System.out.println("del sr:" + table.getSelectedRow());
            tableModel.removeRow(table.getSelectedRow());
        }
    }

    public String getDialogData() {
        return dialogData;
    }

    public void setDialogData(String dialogData) {
        this.dialogData = dialogData;
    }
}
