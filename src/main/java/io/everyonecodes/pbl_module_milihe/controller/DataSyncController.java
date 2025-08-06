package io.everyonecodes.pbl_module_milihe.controller;

import io.everyonecodes.pbl_module_milihe.service.DataSyncService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
public class DataSyncController {

    private final DataSyncService dataSyncService;

    public DataSyncController(DataSyncService dataSyncService) {
        this.dataSyncService = dataSyncService;
    }

    /**
     * A manual trigger endpoint to start the data synchronization process.
     * @return A success message.
     */
    @PostMapping("/recipes")
    public String triggerSync() {
        dataSyncService.syncRecipes();
        return "Spoonacular data sync initiated successfully!";
    }
}
