package com.sluggan.waterauto.Controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sluggan.waterauto.Modell.Plant;
import com.sluggan.waterauto.Service.PlantService;

@Controller
public class PlantController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping("/")
    String getHome(Model model) {
        List<Plant> plants = plantService.getAllPlants();
        model.addAttribute("plants", plants);
        return "home";
    }

}
