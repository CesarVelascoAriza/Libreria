/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cava.examples.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 *
 * @author cesar
 */
public interface CommonService<E> {

    public Iterable<E> findAll();

    public Page<E> findAll(Pageable pageable);

    public Optional<E> findById(Long id);

    public E save(E entity);

    public void deleteById(Long id);
}
