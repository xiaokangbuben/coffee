package com.tk.outer.coffee.repository;

import com.tk.outer.coffee.domain.CategoryElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryElementRepository extends JpaRepository<CategoryElement, Integer>, JpaSpecificationExecutor {
}
