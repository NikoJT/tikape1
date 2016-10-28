package com.tikape.keskustelupalsta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import com.tikape.keskustelupalsta.dao.Database;
import com.tikape.keskustelupalsta.dao.AlueDao;
import com.tikape.keskustelupalsta.dao.KetjuDao;
import com.tikape.keskustelupalsta.dao.ViestiDao;
import com.tikape.keskustelupalsta.domain.Alue;
import com.tikape.keskustelupalsta.domain.Ketju;
import com.tikape.keskustelupalsta.domain.Viesti;
import java.util.Objects;
import spark.Spark;
import static spark.Spark.get;
import static spark.Spark.post;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:h2:./database");
        AlueDao alueDao = new AlueDao(database); 
        KetjuDao ketjuDao = new KetjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);
        Spark.staticFileLocation("public");
        get("/", (req, res) -> {
            res.redirect("/alue");
            return "";
        });
        
        get("/die", (req, res) -> {
        System.exit(0);
        return "";
        });
        
        get("/alue", (req, res) -> {
            HashMap<String, Object> data = new HashMap<>();
            data.put("alueet", alueDao.findAll());
            data.put("viestimaara", viestiDao.findAllViestit());
            return new ModelAndView(data, "alueet");
        }, new ThymeleafTemplateEngine());
        
        post("/alue", (req, res) -> {
            String nimi = req.queryParams("name");            
            if (!nimi.isEmpty()) {
                Alue uusi = new Alue(nimi);
                alueDao.create(uusi);
            }          
            res.redirect("/alue");          
            return "";
        });
        
        post("/alue/:id/delete", (req, res) -> {
            alueDao.delete(req.params(":id"));
            res.redirect("/alue");
            return "";
        });
        
        get("/alue/:id", (req, res) -> {
            HashMap<String, Object> data = new HashMap<>();
            data.put("alue", alueDao.findOne(req.params(":id")));
            List<Ketju> alueenKetjut = new ArrayList();
            for (Ketju ketju : ketjuDao.findAll()) {
                if (Objects.equals(ketju.getAlue().getId(), alueDao.findOne(req.params(":id")).getId())) {
                    alueenKetjut.add(ketju);
                }
            }
            data.put("ketjut", alueenKetjut);
            data.put("viestimaara", viestiDao.findAllKetjunViestit(req.params(":id")));
            return new ModelAndView(data, "ketjut");
        },new ThymeleafTemplateEngine());
        
        post("/alue/:id", (req,res) -> {
            String nimi = req.queryParams("name");
            String sisalto = req.queryParams("sisalto");
            String nimimerkki = req.queryParams("nimimerkki");
            if (!nimi.isEmpty() && !sisalto.isEmpty() && !nimimerkki.isEmpty()) {
                Ketju uusiKetju = new Ketju(nimi);
                uusiKetju.setAlue(alueDao.findOne(req.params(":id")));               
                ketjuDao.create(uusiKetju);
                Viesti ekaViesti = new Viesti(sisalto, nimimerkki);
                ekaViesti.setAlue(alueDao.findOne(req.params(":id")));
                ekaViesti.setKetju(ketjuDao.findOne(uusiKetju.getAlue(), uusiKetju.getName()));
                viestiDao.create(ekaViesti);
            }
            res.redirect("/alue/" + req.params(":id"));           
            return "";
        });
        
        get("/alue/:id/:ketjuid", (req, res) -> {
            HashMap<String, Object> data = new HashMap<>();
            data.put("alue", alueDao.findOne(req.params(":id")));
            data.put("ketju", ketjuDao.findOne(req.params(":ketjuid")));
            List<Viesti> ketjunViestit = new ArrayList();
            for (Viesti viesti : viestiDao.findAll()) {
                if (Objects.equals(viesti.getKetju().getId(), ketjuDao.findOne(req.params(":ketjuid")).getId())) {
                    ketjunViestit.add(viesti);
                }
            }
            data.put("viestit", ketjunViestit);
            return new ModelAndView(data, "ketju"); 
        },new ThymeleafTemplateEngine());
        
        post("/alue/:id/:ketjuid", (req,res) -> {
            String sisalto = req.queryParams("sisalto");
            String nimimerkki = req.queryParams("nimimerkki");
            if (!sisalto.isEmpty() && !nimimerkki.isEmpty()) {
                Viesti uusiViesti = new Viesti(sisalto,nimimerkki);
                uusiViesti.setAlue(alueDao.findOne(req.params(":id")));
                uusiViesti.setKetju(ketjuDao.findOne(req.params(":ketjuid")));
                viestiDao.create(uusiViesti);
            }
            res.redirect("/alue/" + req.params(":id") + "/" + req.params(":ketjuid"));
            return "";
        });
    }
}
