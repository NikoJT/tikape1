
package com.tikape.keskustelupalsta.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Viesti {
    private Integer id;
    private Alue alue;
    private Ketju ketju;
    private String sisalto;
    private String nimimerkki;
    private String aika;
    
    public Viesti(Integer id, Alue alue, Ketju ketju, String sisalto, String nimimerkki, String aika) {
        this.alue = alue;
        this.ketju = ketju;
        this.sisalto = sisalto;
        this.nimimerkki = nimimerkki;
        this.aika = aika;
    }
    
    public Viesti(Alue alue, Ketju ketju, String sisalto, String nimimerkki) {
        this(null, alue, ketju, sisalto, nimimerkki, null);
    }
    
    public Viesti(Ketju ketju, String sisalto, String nimimerkki) {
        this(null, null, ketju, sisalto, nimimerkki, null);
    }
    
    public Viesti(String sisalto, String nimimerkki) {
        this(null, null, null, sisalto, nimimerkki, null);
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika =  new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());

    }
    
    public Alue getAlue() {
        return alue;
    }

    public void setAlue(Alue alue) {
        this.alue = alue;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ketju getKetju() {
        return ketju;
    }

    public void setKetju(Ketju ketju) {
        this.ketju = ketju;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }   
}
