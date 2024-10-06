package com.sluggan.waterauto.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sluggan.waterauto.Modell.Moist;
import com.sluggan.waterauto.Modell.Plant;
import com.sluggan.waterauto.Modell.Temp;
import com.sluggan.waterauto.Service.MoistService;
import com.sluggan.waterauto.Service.PlantService;
import com.sluggan.waterauto.Service.TempService;

import java.util.Date;
import java.util.Map;

@RestController
public class PlantApi {

    private final PlantService plantService;
    private final MoistService moistService;
    private final TempService tempService;

    public PlantApi(PlantService plantService, MoistService moistService, TempService tempService) {
        this.plantService = plantService;
        this.moistService = moistService;
        this.tempService = tempService;
    }

    @PostMapping("/plant")
    public String addPlant(@RequestBody Map<String, Integer> plantData) {
        System.out.println("Nu har vi en planta: " + plantData);
        plantService.deleteAll();
        int id = 1;

        for (String key : plantData.keySet()) {
            String plantNr = key;
            int value = plantData.get(key);
            plantService.addData(new Plant(String.valueOf(id), plantNr, value));
            System.out.println("nu har planta lags till");
            id += 1;
            System.out.println(id);
        }

        return "ok";
    }

    @PostMapping("/updateplant")
    public String updatePlant(@RequestBody Map<String, Integer> plantData) {
        for (String key : plantData.keySet()) {
            String plantNr = key.replaceAll("\\D+", "");
            int value = plantData.get(key);
            plantService.updateData(plantNr, value);
            System.out.println(plantNr);
            //plantService.updateData(plantNr, value);
        }

        return "update went okeeeeeeey";
    }

    @PostMapping("/temp")
    public String addTemp(@RequestBody Map<String, Float> tempData) {
        System.out.println("Nu har vi en planta: " + tempData);
        for (String key : tempData.keySet()) {
            String typeOfData = key;
            float value = tempData.get(key);
            if (typeOfData.equals("fuktighet")) {
                moistService.addData(new Moist(new Date(), value));
            } else if (typeOfData.equals("tempratur")) {
                tempService.addData(new Temp(new Date(), value));
            }
        }

        System.out.println("Nu hände det något");
        return "ok";

    }
}
