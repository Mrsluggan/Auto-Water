package com.sluggan.waterauto.Service;

import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.sluggan.waterauto.Modell.Moist;

@Service
public class MoistService {

    private final MongoOperations mongoOperations;

    public MoistService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void deleteAll() {
        mongoOperations.remove(new Query(), Moist.class);
    }

    public Moist addData(Moist plant) {

        mongoOperations.save(plant);
        return plant;
    }

    public List<Moist> getAllPlants() {
        return mongoOperations.findAll(Moist.class);
    }

    public Moist getPlantsById(String id) {
        return mongoOperations.findById(id, Moist.class);
    }
}
