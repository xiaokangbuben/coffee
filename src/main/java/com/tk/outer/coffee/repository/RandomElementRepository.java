package com.tk.outer.coffee.repository;

import com.tk.outer.coffee.domain.RandomElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RandomElementRepository extends JpaRepository<RandomElement, Integer>, JpaSpecificationExecutor {

}
