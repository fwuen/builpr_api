package com.builpr.restapi.service;

import com.builpr.database.BuilprApplication;
import com.builpr.database.db.builpr.model.Model;
import com.builpr.database.db.builpr.model.ModelImpl;
import com.builpr.database.db.builpr.model.ModelManager;
import com.builpr.restapi.exception.InvalidModelIdException;
import com.builpr.restapi.exception.ModelNotFoundException;
import com.builpr.restapi.model.PublicModel;
import com.builpr.restapi.utils.Connector;
import lombok.NonNull;


import java.util.List;
import java.util.stream.Collectors;


public class ModelService {

    private ModelManager modelManager;

    public ModelService() {
        BuilprApplication builprApplication = Connector.getConnection();
        modelManager = builprApplication.getOrThrow(ModelManager.class);
    }

    /**
     * @param id int
     * @return Model
     * @throws ModelNotFoundException Exception
     */
    public Model getById(@NonNull int id) throws ModelNotFoundException {
        List<Model> modelList = modelManager.stream().filter(Model.MID.equal(id)).collect(Collectors.toList());

        if (modelList.isEmpty()) {
            throw new ModelNotFoundException("Model does not exist!");
        }

        if (modelList.size() > 1) {
            throw new ModelNotFoundException("Houston we got a Problem");
        }

        return modelList.get(0);
    }

    /**
     * @param changedModel Model
     * @return Model
     * @throws ModelNotFoundException  Exception
     * @throws InvalidModelIdException Exception
     */
    public Model changeModel(Model changedModel) throws InvalidModelIdException, ModelNotFoundException {

        if (changedModel.getMid() < 1) {
            throw new InvalidModelIdException();
        }
        modelManager.stream()
                .filter(Model.MID.equal(changedModel.getMid()))
                .map(Model.TITLE.setTo(changedModel.getTitle()))
                .map(Model.AGE_RESTRICTION.setTo(changedModel.getAgeRestriction()))
                .map(Model.FILE.setTo(changedModel.getFile()))
                .map(Model.UID.setTo(changedModel.getUid()))
                .forEach(modelManager.updater());

        if (changedModel.getDescription().isPresent()) {
            modelManager.stream()
                    .filter(Model.MID.equal(changedModel.getMid()))
                    .map(Model.DESCRIPTION.setTo(changedModel.getDescription().get()))
                    .forEach(modelManager.updater());
        }

        List<Model> modelList = modelManager.stream().filter(Model.MID.equal(changedModel.getMid())).collect(Collectors.toList());
        //TODO Passende Exception finden


        return modelList.get(0);
    }

    /**
     * @param newModel PublicModel
     * @return Model
     * @throws IllegalArgumentException Exception
     */
    public Model createModel(@NonNull PublicModel newModel) throws IllegalArgumentException {
        Model model = new ModelImpl();
        model.setTitle(newModel.getTitle());
        model.setDescription(newModel.getDescription());
        model.setFile(newModel.getFile());
        model.setAgeRestriction(newModel.isAge_restriction());
        model.setUid(newModel.getUid());
        try {
            modelManager.persist(model);
        } catch (Exception e) {
            // TODO Exception finden, welche zum nicht erfolgreichen Speichern in der Datenbank passt
        }
        return model;
    }
}
