package com.tzz.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;


@Entity
@Table(name = "stocks", schema = "financial_system", catalog = "")
@Data
public class StocksEntity {
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "stock_code")
    private String stockCode;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "open")
    private Double open;
    @Basic
    @Column(name = "high")
    private Double high;
    @Basic
    @Column(name = "low")
    private Double low;
    @Basic
    @Column(name = "close")
    private Double close;
    @Basic
    @Column(name = "adj_close")
    private Double adjClose;
    @Basic
    @Column(name = "volume")
    private Double volume;
    @Basic
    @Column(name = "roi")
    private Double roi;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "datedate")
    private String datedate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(Double adjClose) {
        this.adjClose = adjClose;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatedate() {
        return datedate;
    }

    public void setDatedate(String datedate) {
        this.datedate = datedate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StocksEntity that = (StocksEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(stockCode, that.stockCode) && Objects.equals(date, that.date) && Objects.equals(open, that.open) && Objects.equals(high, that.high) && Objects.equals(low, that.low) && Objects.equals(close, that.close) && Objects.equals(adjClose, that.adjClose) && Objects.equals(volume, that.volume) && Objects.equals(roi, that.roi) && Objects.equals(datedate, that.datedate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, stockCode, date, open, high, low, close, adjClose, volume, roi, id, datedate);
    }
}
