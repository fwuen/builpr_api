package com.builpr.restapi.controller;


import com.builpr.database.db.builpr.model.Model;
import com.builpr.restapi.utils.ModelService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ModelController {

    private ModelService modelService;

    public ModelController() {
        modelService = new ModelService();
    }

    /**
     * @param modelId int
     * @return Model
     * @throws Exception Exception
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/model/{modelId}", method = RequestMethod.GET)
    public Model getModel(@RequestParam(
            value = "modelId"
    ) @PathVariable int modelId) throws Exception {
        return modelService.getModelById(modelId);
    }

    /**
     * @param changedModel Model
     * @return Model
     * @throws Exception ModelNotFoundException
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/model/{modelId}/change", method = RequestMethod.PUT)
    public Model changeModel(@RequestParam(

    ) Model changedModel) throws Exception {
        return modelService.changeModel(changedModel);
    }

    /**
     * @param newModel Model
     * @return Model
     * @throws Exception ModelNotFoundException
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/model/new", method = RequestMethod.POST)
    public Model createModel(@RequestParam(
            value = "model"
    ) Model newModel) throws Exception {
        return modelService.createModel(newModel);
    }
}
