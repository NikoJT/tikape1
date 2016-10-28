
package com.tikape.keskustelupalsta.dao;

import com.tikape.keskustelupalsta.domain.Alue;
import com.tikape.keskustelupalsta.domain.Ketju;
import com.tikape.keskustelupalsta.domain.Viesti;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ViestiDao implements Dao<Viesti, String> {
    private Database database;
    
    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti create(Viesti t) throws SQLException {
        this.database.getConnection().createStatement().execute("INSERT INTO Viesti (alue_id, ketju_id, sisalto, nimimerkki, aika) VALUES('" + t.getAlue().getId() + "','" + t.getKetju().getId() + "','" + t.getSisalto() + "','" + t.getNimimerkki() + "','" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime()) +"')");
        return new Viesti(t.getKetju(),t.getSisalto(), t.getNimimerkki());
    }

    @Override
    public Viesti findOne(String key) throws SQLException {
        for (Viesti viesti : this.findAll()) {
            if (viesti.getId() == Integer.parseInt(key)) {
                return viesti;
            }
        }
        return null;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        List<Viesti> kaikkiViestit = new ArrayList();
        ResultSet kaikki = this.database.getConnection().createStatement().executeQuery("SELECT * FROM Viesti");
        KetjuDao ketjudao = new KetjuDao(database);
        AlueDao aluedao = new AlueDao(database);
        while (kaikki.next()) {
            Viesti a = new Viesti(kaikki.getInt("id"), aluedao.findOne(kaikki.getString("alue_id")), ketjudao.findOne(kaikki.getString("ketju_id")), kaikki.getString("sisalto"), kaikki.getString("nimimerkki"), kaikki.getString("aika"));
            kaikkiViestit.add(a);
        }
        return kaikkiViestit;
    }
    
    public List<Integer> findAllViestit() throws SQLException {
        List<Integer> alueenViestit = new ArrayList();
        AlueDao aluedao = new AlueDao(database);
        for (Alue alue : aluedao.findAll()) {
            List<Viesti> kaikkiViestitAlueella = new ArrayList();
            for (Viesti viesti : this.findAll()) {
                if (alue.getId().equals(viesti.getAlue().getId())) {
                    kaikkiViestitAlueella.add(viesti);
                }
            }
            alueenViestit.add(kaikkiViestitAlueella.size());
        }
        return alueenViestit;
    }
    
    public List<Integer> findAllKetjunViestit(String alue) throws SQLException {
        List<Integer> ketjunViestit = new ArrayList();
        KetjuDao ketjudao = new KetjuDao(database);           
        for (Ketju ketju : ketjudao.findAll()) {
            List<Viesti> kaikkiViestitKetjussa = new ArrayList();
            for (Viesti viesti : this.findAll()) {
                if (ketju.getId().equals(viesti.getKetju().getId()) && alue.equals(viesti.getAlue().getId().toString())) {
                    kaikkiViestitKetjussa.add(viesti);
                }
            }
            if (!kaikkiViestitKetjussa.isEmpty()) {
                ketjunViestit.add(kaikkiViestitKetjussa.size()); 
            }       
        }
        return ketjunViestit;
    }
    
    @Override
    public void update(String key, Viesti t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
