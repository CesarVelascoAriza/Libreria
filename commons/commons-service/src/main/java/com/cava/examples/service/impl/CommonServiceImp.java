/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cava.examples.service.impl;

import com.cava.examples.service.CommonService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cesar
 */
public class CommonServiceImp<E,R extends JpaRepository<E,Long>> implements CommonService<E>{

    @Autowired
    protected R repository;
    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
       return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }
    @Transactional
    public E save(E entity) {
        return repository.save(entity);
    }
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    
    
}
