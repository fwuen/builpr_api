package com.builpr.restapi.controller;

import com.builpr.restapi.interfaces.SolrModelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class SolrSearchRequestController implements SolrModelRepository {
    @Override
    public Page findByTitleAndDescription(String searchTerm, Pageable page) {
        return null;
    }
    
    @Override
    public Iterable findAll(Sort sort) {
        return null;
    }
    
    @Override
    public Page findAll(Pageable pageable) {
        return null;
    }
    
    @Override
    public Object save(Object entity) {
        return null;
    }
    
    @Override
    public Iterable save(Iterable entities) {
        return null;
    }
    
    @Override
    public Object findOne(Serializable serializable) {
        return null;
    }
    
    @Override
    public boolean exists(Serializable serializable) {
        return false;
    }
    
    @Override
    public Iterable findAll() {
        return null;
    }
    
    @Override
    public Iterable findAll(Iterable iterable) {
        return null;
    }
    
    @Override
    public void delete(Serializable serializable) {
    
    }
    
    @Override
    public void delete(Object entity) {
    
    }
    
    @Override
    public void delete(Iterable entities) {
    
    }
    
    @Override
    public void deleteAll() {
    
    }
    
    @Override
    public long count() {
        return 0;
    }
}
