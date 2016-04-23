/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kocakaya.caisse.ui.component;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.kocakaya.caisse.business.MoneyType;

public class MyDefaultModelTable extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<String[]> data;
    protected String[] columnsName;
    protected MoneyType moneyType;
    protected boolean isAllLocked;

    public MyDefaultModelTable() {
	super();
    }

    public MyDefaultModelTable(List<String[]> data, String[] columnsName, MoneyType moneyType, boolean isAllLocked) {
	super();
	this.data = data;
	this.columnsName = columnsName;
	this.moneyType = moneyType;
	this.isAllLocked = isAllLocked;
    }

    public MoneyType getMoneyType() {
	return moneyType;
    }

    public void setMoneyType(MoneyType moneyType) {
	this.moneyType = moneyType;
    }

    public boolean isAllLocked() {
	return isAllLocked;
    }

    public void setAllLocked(boolean isAllLocked) {
	this.isAllLocked = isAllLocked;
    }

    @Override
    public int getColumnCount() {
	return columnsName.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
	return data.get(row)[column];
    }

    @Override
    public int getRowCount() {
	return data.size();
    }

    @Override
    public String getColumnName(int col) {
	return columnsName[col];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	if (isAllLocked) {
	    return false;
	}
	if ((MoneyType.ESPECES.equals(moneyType) && (columnIndex == 2 || columnIndex == 3)) || (MoneyType.TICKET_RESTAURANT.equals(moneyType) && (columnIndex == 3))) {
	    return false;
	}
	return true;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
	data.get(rowIndex)[columnIndex] = String.valueOf(value);
	fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void addRow(String[] row) {
	data.add(row);
	fireTableRowsInserted(0, 0);
    }

    public void removeRow(int index) {
	data.remove(index);
	fireTableRowsDeleted(0, 0);
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
