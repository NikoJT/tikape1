package com.tikape.keskustelupalsta.domain;

public class Alue {

    private Integer id;
    private String name;


    public Alue(Integer id, String name) {
        this.id = id;
        this.name = name;        
    }

    public Alue(String name) {
        this(null, name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   
}
