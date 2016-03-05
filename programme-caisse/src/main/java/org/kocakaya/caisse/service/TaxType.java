package org.kocakaya.caisse.service;

public enum TaxType {

    TVA_20(20.0), TVA_10(10.0);

    private Double value = null;

    private TaxType(Double value) {
	this.value = value;
    }

    public Double getValue() {
	return value;
    }

    public void setValue(Double value) {
	this.value = value;
    }

    public String toString() {
	return value.toString();
    }
}
