package dev.jbcu10.opendata.domain;

public class Source {

    private String id;
    private String src;
    private String csv;

    public Source() {

    }

    public Source(String id, String src, String csv) {
        this.id = id;
        this.src = src;
        this.csv = csv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }
}
