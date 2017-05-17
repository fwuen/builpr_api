package com.builpr.restapi.utils;

import com.builpr.database.BuilprApplicationBuilder;
import com.builpr.database.db.builpr.model.Model;
import com.builpr.database.db.builpr.model.ModelImpl;
import com.builpr.database.db.builpr.model.ModelManager;
import com.builpr.restapi.Exception.ModelNotFoundException;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;


public class ModelService {

    private ModelManager modelManager;

    public ModelService() {
        modelManager = new BuilprApplicationBuilder().withPassword("builpr123").build().getOrThrow(ModelManager.class);
    }


    public Model getModelById(int id) throws ModelNotFoundException {
        List<Model> modelList = modelManager.stream().filter(Model.MID.equal(id)).collect(Collectors.toList());

        if (modelList.isEmpty()) {
            throw new ModelNotFoundException("Model does not exist!");
        }
        if (modelList.size() > 1) {
            throw new ModelNotFoundException("Houston we got a Problem");
        }
        return modelList.get(0);
    }

    public Model createModel(Model newModel){
return newModel;
    }


    public Model changeModel(Model changedModel) throws ModelNotFoundException {
        if (changedModel.getMid() < 1) {
            throw new ModelNotFoundException();
        }
        int model_id = changedModel.getMid();
        try {


        } catch (Exception e) {

        }
        return changedModel;
    }

}
