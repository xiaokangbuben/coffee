package com.tk.outer.coffee.repository;

import com.tk.outer.coffee.domain.StarElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StarElementRepository extends JpaRepository<StarElement, Integer>, JpaSpecificationExecutor {
}
