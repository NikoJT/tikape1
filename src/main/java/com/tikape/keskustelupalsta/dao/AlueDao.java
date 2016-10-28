
package com.tikape.keskustelupalsta.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.tikape.keskustelupalsta.domain.Alue;

public class AlueDao implements Dao<Alue, String> {
    private Database database;
    
    public AlueDao(Database database) {
        this.database = database;
    }
    
    @Override
    public Alue create(Alue t) throws SQLException {
        this.database.getConnection().createStatement().execute("INSERT INTO Alue (name) VALUES('" + t.getName() + "')");
        return new Alue(t.getId(),t.getName());
        
    }

    @Override
    public Alue findOne(String key) throws SQLException {
        for (Alue alue : this.findAll()) {
            if (alue.getId() == Integer.parseInt(key)) {
                return alue;
            }
        }
        return null;    
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        List<Alue> kaikkiLista = new ArrayList<>();
        ResultSet kaikki = this.database.getConnection().createStatement().executeQuery("SELECT * FROM Alue");
        while (kaikki.next()) {
            Alue a = new Alue(kaikki.getInt("id"),kaikki.getString("name"));
            kaikkiLista.add(a);
        }
        return kaikkiLista;       
    }
        
    @Override
    public void update(String key, Alue t) throws SQLException {
        this.database.getConnection().createStatement().execute("UPDATE Alue SET Alue.name='" + t.getName() + "' WHERE Alue.id='" + Integer.parseInt(key) + "'");    
    }

    @Override
    public void delete(String key) throws SQLException {
        this.database.getConnection().createStatement().execute("DELETE FROM Alue WHERE Alue.id='"+Integer.parseInt(key)+"'");    
    }   
}
