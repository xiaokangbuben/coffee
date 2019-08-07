package com.tk.outer.coffee.controller.base;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

public class BaseController {

  protected Map<String, Object> model;
  protected HttpServletRequest request;
  protected HttpServletResponse response;
  protected HttpSession session;

  @ModelAttribute
  public void init(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
    this.model = model;
    this.request = request;
    this.response = response;
    this.session = request.getSession();
  }
}
