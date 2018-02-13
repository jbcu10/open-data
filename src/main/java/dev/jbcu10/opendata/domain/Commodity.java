package dev.jbcu10.opendata.domain;

 import org.springframework.data.annotation.Id;
 import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "product")
public class Commodity implements Serializable {

    @Id
    private String id;
    private String market;
    private String date;
    private String name;
    private String price;



    public Commodity( String market, String date, String name, String price) {
         this.market = market;
        this.date = date;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Commodity{" +
                "id='" + id + '\'' +
                ", market='" + market + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
