package com.tikape.keskustelupalsta.domain;

public class Ketju {

    private Integer id;
    private Alue alue;
    private String name;

    public Ketju(Integer id, Alue alue, String name) {
        this.id = id;
        this.alue = alue;
        this.name = name;
    }

    public Ketju(Alue alue, String name) {
        this(null, alue, name);
    }
    
    public Ketju(String name) {
        this(null, null, name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Alue getAlue() {
        return alue;
    }

    public void setAlue(Alue alue) {
        this.alue = alue;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
