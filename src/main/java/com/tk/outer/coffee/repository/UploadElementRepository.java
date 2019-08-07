package com.tk.outer.coffee.repository;

import com.tk.outer.coffee.domain.UploadElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UploadElementRepository extends JpaRepository<UploadElement, Integer>, JpaSpecificationExecutor {

  @Query(value = "SELECT MAX(id) AS id FROM upload_element", nativeQuery = true)
  Integer getMaxId();
}
