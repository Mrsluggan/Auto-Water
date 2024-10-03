package com.sluggan.waterauto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class PlantApi {

    private final PlantService plantService;

    public PlantApi(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping("/plant")
    public void addPlant(@RequestBody Map<String, Integer> plantData) {
        System.out.println("Nu har vi en planta: " + plantData);
        plantService.deleteAll();
        for (String key : plantData.keySet()) {
            String plantName = key;
            int value = plantData.get(key);
            System.out.println("Plantan: " + plantName + ", Värde: " + value);
            plantService.addData(new Plant(plantName, value));
        }

        System.out.println("Nu hände det något");
    }
}
