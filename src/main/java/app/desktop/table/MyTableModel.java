package app.desktop.table;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

    String CUST_PROP_VAL_BTN = "...";

    String[] columnNames = {"First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian", ""};

    Object[][] data = {
            {"Kathy", "Smith",
                    "Snowboarding", new Integer(5), new Boolean(false), CUST_PROP_VAL_BTN},
            {"John", "Doe",
                    "Rowing", new Integer(3), new Boolean(true), CUST_PROP_VAL_BTN},
            {"Sue", "Black",
                    "Knitting", new Integer(2), new Boolean(false), CUST_PROP_VAL_BTN},
            {"Jane", "White",
                    "Speed reading", new Integer(20), new Boolean(true), CUST_PROP_VAL_BTN},
            {"Joe", "Brown",
                    "Pool", new Integer(10), new Boolean(false), CUST_PROP_VAL_BTN}
    };

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    /**
     * The cell editable value should be true for custom CellEditor to work.
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
