
package com.tikape.keskustelupalsta.dao;

import com.tikape.keskustelupalsta.domain.Alue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.tikape.keskustelupalsta.domain.Ketju;
import java.util.Objects;


public class KetjuDao implements Dao<Ketju, String> {
    private Database database;
    
    public KetjuDao(Database database) {
        this.database = database;
    }
    @Override
    public Ketju create(Ketju t) throws SQLException {
        this.database.getConnection().createStatement().execute("INSERT INTO Ketju (alue_id, name) VALUES('" + t.getAlue().getId() + "','"+t.getName()+"')");       
        return new Ketju(t.getId(),t.getAlue(),t.getName());
    }

    @Override
    public Ketju findOne(String key) throws SQLException {
        for (Ketju ketju : this.findAll()) {
            if (ketju.getId() == Integer.parseInt(key)) {
                return ketju;
            }
        }
        return null;
    }
    
    public Ketju findOne(Alue alue, String nimi) throws SQLException {
        for (Ketju ketju : this.findAll()) {
            if (Objects.equals(ketju.getAlue().getId(), alue.getId())) {
                if (ketju.getName().equals(nimi)) {
                    return ketju;
                }
            }
        }
        return null;
    }

    @Override
    public List<Ketju> findAll() throws SQLException {
        List<Ketju> kaikkiLista = new ArrayList();
        ResultSet kaikki = this.database.getConnection().createStatement().executeQuery("SELECT * FROM Ketju");
        AlueDao alueDao = new AlueDao(database);
        while (kaikki.next()) {                                             
            Ketju a = new Ketju(kaikki.getInt("id"), alueDao.findOne(kaikki.getString("alue_id")),kaikki.getString("name"));
            kaikkiLista.add(a);
        }
        return kaikkiLista;
    }

    @Override
    public void update(String key, Ketju t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
