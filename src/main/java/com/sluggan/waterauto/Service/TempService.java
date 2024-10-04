package com.sluggan.waterauto.Service;

import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.sluggan.waterauto.Modell.Temp;

@Service
public class TempService {

    private final MongoOperations mongoOperations;

    public TempService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void deleteAll() {
        mongoOperations.remove(new Query(), Temp.class);
    }

    public Temp addData(Temp plant) {

        mongoOperations.save(plant);
        return plant;
    }

    public List<Temp> getAllTemps() {
        return mongoOperations.findAll(Temp.class);
    }

    public Temp getTempsById(String id) {
        return mongoOperations.findById(id, Temp.class);
    }
}
