package com.builpr.restapi.controller;


import com.builpr.database.db.builpr.model.Model;
import com.builpr.restapi.exception.InvalidModelIdException;
import com.builpr.restapi.exception.ModelNotFoundException;
import com.builpr.restapi.model.PublicModel;
import com.builpr.restapi.service.ModelService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ModelController {

    private ModelService modelService;

    public ModelController() {
        modelService = new ModelService();
    }

    /**
     * Getting the data from a model due to the id
     *
     * @param modelId int
     * @return Model
     * @throws ModelNotFoundException Exception
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/model/{modelId}", method = RequestMethod.GET)
    public Model getModel(@RequestParam(
            value = "modelId"
    ) @PathVariable int modelId) throws ModelNotFoundException {
        return modelService.getById(modelId);
    }

    /**
     * Changing an existing model
     *
     * @param changedModel Model
     * @return Model
     * @throws InvalidModelIdException Exception
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/model/{modelId}/change", method = RequestMethod.PUT)
    public Model changeModel(@RequestParam(
    ) Model changedModel) throws InvalidModelIdException, ModelNotFoundException {
        return modelService.changeModel(changedModel);
    }

    /**
     * Creating a new model
     *
     * @param newModel PublicModel
     * @return Model
     * @throws InvalidModelIdException Exception
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/model/new", method = RequestMethod.POST)
    public Model createModel(@RequestParam(
            value = "model"
    ) PublicModel newModel) throws InvalidModelIdException {
        return modelService.createModel(newModel);
    }
}
