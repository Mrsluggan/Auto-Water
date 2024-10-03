package com.sluggan.waterauto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "plants")
public class Plant {

    @Id
    private String id;
    private String name;
    private int value;

    public Plant() {

    }

    public Plant(String id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Plant(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
