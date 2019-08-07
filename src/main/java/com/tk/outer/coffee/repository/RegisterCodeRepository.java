package com.tk.outer.coffee.repository;

import com.tk.outer.coffee.domain.RegisterCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegisterCodeRepository extends JpaRepository<RegisterCode, Integer>, JpaSpecificationExecutor {

  RegisterCode findOneByCode(String code);

}
