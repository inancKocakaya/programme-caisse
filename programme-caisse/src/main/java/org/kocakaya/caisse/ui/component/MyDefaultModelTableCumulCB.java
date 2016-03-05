package org.kocakaya.caisse.ui.component;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class MyDefaultModelTableCumulCB extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    protected List<String[]> data;
    protected String[] columnsName;

    @Override
    public int getColumnCount() {
	return columnsName.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
	return data.get(row)[column];
    }

    public int getRowCount() {
	return data.size();
    }

    @Override
    public String getColumnName(int col) {
	return columnsName[col].toString();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	if (columnIndex == 2) {
	    return true;
	}
	return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
	data.get(rowIndex)[columnIndex] = String.valueOf(value);
	fireTableCellUpdated(rowIndex, columnIndex);
    }

    public List<String[]> getData() {
	return data;
    }

    public void setData(List<String[]> data) {
	this.data = data;
	fireTableDataChanged();
    }

    public String[] getColumnsName() {
	return columnsName;
    }

    public void setColumnsName(String[] columnsName) {
	this.columnsName = columnsName;
    }
}
