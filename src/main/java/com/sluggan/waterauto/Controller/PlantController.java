package com.sluggan.waterauto.Controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sluggan.waterauto.Modell.Moist;
import com.sluggan.waterauto.Modell.Plant;
import com.sluggan.waterauto.Modell.Temp;
import com.sluggan.waterauto.Service.MoistService;
import com.sluggan.waterauto.Service.PlantService;
import com.sluggan.waterauto.Service.TempService;

@Controller
public class PlantController {
    private final PlantService plantService;
    private final TempService tempService;
    private final MoistService moistService;

    public PlantController(PlantService plantService, TempService tempService, MoistService moistService) {
        this.plantService = plantService;
        this.tempService = tempService;
        this.moistService = moistService;

    }

    @GetMapping("/")
    String getHome(Model model) {
        List<Plant> plants = plantService.getAllPlants();
        List<Temp> temp = tempService.getAllTemps();
        List<Moist> moist = moistService.getAllMoist();

        model.addAttribute("plants", plants);
        model.addAttribute("temp", temp);

        model.addAttribute("moist", moist);

        return "home";
    }

}
