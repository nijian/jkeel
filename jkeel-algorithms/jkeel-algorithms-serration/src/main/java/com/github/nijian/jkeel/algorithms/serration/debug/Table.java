package com.github.nijian.jkeel.algorithms.serration.debug;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Table {

    private List<Row> rows;

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public static class Row {
        private List<Cell> cells;

        public List<Cell> getCells() {
            return cells;
        }

        public void setCells(List<Cell> cells) {
            this.cells = cells;
        }
    }

    public static class Cell {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
