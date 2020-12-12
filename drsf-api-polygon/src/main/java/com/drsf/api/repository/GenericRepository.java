package com.drsf.api.repository;

import com.drsf.api.entities.Dividend;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class JPARepository<Dividend, Long>
        //extends SimpleJpaRepository<Dividend, Long>
{

}
