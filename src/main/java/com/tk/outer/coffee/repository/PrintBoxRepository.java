package com.tk.outer.coffee.repository;

import com.tk.outer.coffee.domain.PrintBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrintBoxRepository extends JpaRepository<PrintBox, Integer>, JpaSpecificationExecutor {
  PrintBox findOneByCode(String code);
}
