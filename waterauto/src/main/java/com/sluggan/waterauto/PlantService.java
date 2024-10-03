package com.sluggan.waterauto;

import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class PlantService {

    private final MongoOperations mongoOperations;

    public PlantService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void deleteAll() {
        mongoOperations.remove(new Query(), Plant.class);
    }

    public Plant addData(Plant plant) {
        
        mongoOperations.save(plant);
        return plant;
    }

    public List<Plant> getAllPlants() {
        return mongoOperations.findAll(Plant.class);
    }

    public Plant getPlantsById(String id) {
        return mongoOperations.findById(id, Plant.class);
    }
}
