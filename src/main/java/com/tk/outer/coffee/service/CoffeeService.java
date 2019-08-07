package com.tk.outer.coffee.service;

import com.mysql.jdbc.StringUtils;
import com.tk.outer.coffee.domain.Category;
import com.tk.outer.coffee.domain.CategoryElement;
import com.tk.outer.coffee.domain.PrintBox;
import com.tk.outer.coffee.domain.RandomElement;
import com.tk.outer.coffee.domain.RegisterCode;
import com.tk.outer.coffee.domain.StarElement;
import com.tk.outer.coffee.domain.UploadElement;
import com.tk.outer.coffee.repository.PrintBoxRepository;
import com.tk.outer.coffee.repository.RandomElementRepository;
import com.tk.outer.coffee.repository.StarElementRepository;
import com.tk.outer.coffee.repository.VersionRepository;
import com.tk.outer.coffee.repository.CategoryElementRepository;
import com.tk.outer.coffee.repository.CategoryRepository;
import com.tk.outer.coffee.repository.RegisterCodeRepository;
import com.tk.outer.coffee.repository.UploadElementRepository;
import com.tk.outer.coffee.util.ConfigUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import javax.persistence.criteria.Predicate;

import com.tk.outer.coffee.vo.CategoryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CoffeeService {

  private static final Logger log = LoggerFactory.getLogger(CoffeeService.class);

  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private CategoryElementRepository categoryElementRepository;
  @Autowired
  private StarElementRepository starElementRepository;
  @Autowired
  private RandomElementRepository randomElementRepository;
  @Autowired
  private UploadElementRepository uploadElementRepository;
  @Autowired
  private RegisterCodeRepository registerCodeRepository;
  @Autowired
  private VersionRepository versionRepository;
  @Autowired
  private PrintBoxRepository printBoxRepository;

  private AtomicInteger uploadElementIdGenerator;
  public static final String lang_us = "us";
  public static final String lang_cn = "cn";
  @PostConstruct
  private void init() {
    Integer id = uploadElementRepository.getMaxId();
    if(id == null || id < 10000) {
      uploadElementIdGenerator = new AtomicInteger(10000);
    } else {
      uploadElementIdGenerator = new AtomicInteger(id);
    }
  }

  public List<Category> getCategories() {
    return categoryRepository.findAll();
  }

  public List<CategoryVo> getCategories(String device, String lang) {
    Specification query = (Specification<Category>) (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (!StringUtils.isNullOrEmpty(device)) {
        predicates.add(criteriaBuilder.equal(root.get("device"), device));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    };
    List<Category> list = categoryRepository.findAll(query);
    List<CategoryVo> voList = new ArrayList<>();
    for(Category category : list){
      CategoryVo vo = new CategoryVo();
      vo.setId(category.getId());
      if(lang_us.equals(lang)){
        vo.setName(category.getEnName());
      }else{
        vo.setName(category.getName());
      }
      voList.add(vo);
    }
    return voList;
  }

  public Page<CategoryElement> getCategoryElements(int categoryId, Pageable pageable) {
    Specification query = (Specification<CategoryElement>) (root, criteriaQuery, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (categoryId != ConfigUtil.CATEGORY_ALL_ID) {
        predicates.add(criteriaBuilder.equal(root.get("categoryId"), categoryId));
      }
      return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    };
    return categoryElementRepository.findAll(query, pageable);
  }

  public List<CategoryElement> getCategoryElements() {
    return categoryElementRepository.findAll();
  }

  public List<StarElement> getStarElements() {
    return starElementRepository.findAll();
  }

  public List<RandomElement> getRandomElements() {
    return randomElementRepository.findAll();
  }

  public int newUploadElementId() {
    return uploadElementIdGenerator.incrementAndGet();
  }

  public void saveUploadElement(UploadElement uploadElement) {
    uploadElementRepository.save(uploadElement);
  }

  public String updateRegisterCode(String code, String device) {
    RegisterCode registerCode = registerCodeRepository.findOneByCode(code);
    if(registerCode == null) {
      return "注册码不存在";
    }
    // 相同设备
    if(registerCode.getDevice().equals(device)) {
      return "";
    }
    // 不同设备，检查更新时间，如果未超过24小时，则不可以修改
    Date today = new Date();
    if((registerCode.getDevice() != null && !registerCode.getDevice().isEmpty()) && today.getTime() - registerCode.getUpdateTime().getTime() < 1000L * 3600 * 24) {
      return "注册码已绑定设备，请在24小时后重试";
    }
    registerCode.setDevice(device);
    registerCode.setUpdateTime(new Date());
    registerCodeRepository.save(registerCode);
    return "";
  }

  public Integer getVersion() {
    return versionRepository.getMaxId();
  }

  public PrintBox getPrintBox(String code) {
    return printBoxRepository.findOneByCode(code);
  }

  public PrintBox savePrintBox(PrintBox printBox) {
    return printBoxRepository.save(printBox);
  }
}
