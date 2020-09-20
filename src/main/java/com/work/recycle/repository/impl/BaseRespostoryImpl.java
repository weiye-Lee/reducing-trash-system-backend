package com.work.recycle.repository.impl;

import com.work.recycle.repository.BaseRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class BaseRespostoryImpl<T, ID> extends SimpleJpaRepository<T, ID>  implements BaseRepository<T, ID> {
    private EntityManager manager;
    public BaseRespostoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager); // entityInformation
        this.manager = entityManager;
    }

    @Override
    public void refresh(T t) {
        manager.refresh(t);
    }
}
