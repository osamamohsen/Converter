package com.app.models;

public class QtySubmit {
    private String type = "";
    private int position = 0;
    private Double qty = 0.0;

    public QtySubmit(String type , int position, Double qty) {
        this.type = type;
        this.position = position;
        this.qty = qty;
    }

    public int getPosition() {
        return position;
    }

    public Double getQty() {
        return qty;
    }

    public String getType() {
        return type;
    }
}
