package com.tk.outer.coffee.controller;

import com.google.gson.JsonObject;
import com.tk.outer.coffee.configuration.WebConfig;
import com.tk.outer.coffee.controller.base.BaseController;
import com.tk.outer.coffee.domain.Category;
import com.tk.outer.coffee.domain.CategoryElement;
import com.tk.outer.coffee.domain.PrintBox;
import com.tk.outer.coffee.domain.RandomElement;
import com.tk.outer.coffee.domain.StarElement;
import com.tk.outer.coffee.domain.UploadElement;
import com.tk.outer.coffee.service.CoffeeService;
import com.tk.outer.coffee.vo.CategoryVo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/", method = {RequestMethod.GET})
public class HomeController extends BaseController {

  @Value("${image.size}")
  private int imageSize;

  @Autowired
  private CoffeeService service;

  @RequestMapping("")
  public String index(@RequestParam(defaultValue = "zh") String language) {
    if(language.equals("zh") || language.equals("zh_CN") || language.equals("zh_TW") || language.equals("zh_HK")) {
      session.setAttribute("lang", 0);
    } else {
      session.setAttribute("lang", 1);
    }
    model.put("categories", service.getCategories());
    return "index";
  }

  @RequestMapping("get_category_element_list")
  @ResponseBody
  public Page<CategoryElement> getCategoryElementList(@RequestParam Integer categoryId, @RequestParam Integer start, @RequestParam(defaultValue = "15") Integer size) {
    PageRequest pageRequest = PageRequest.of(start / size, size, new Sort(Direction.ASC, "id"));
    return service.getCategoryElements(categoryId, pageRequest);
  }

  @RequestMapping("edit")
  public String edit(@RequestParam(defaultValue = "0") Integer id) {
    model.put("categories", service.getCategories());
    if (id != 0) {
      model.put("id", id);
    }
    return "edit";
  }

  @RequestMapping("code/{id}")
  public String code(@PathVariable Integer id) {
    model.put("id", id);
    return "code";
  }

  @RequestMapping(value = "upload", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> upload(@RequestParam String data) {
    Map<String, Object> result = new HashMap<>();
    Integer elementId = service.newUploadElementId();
    String path = WebConfig.getImageUploadPath();
    File imageFile = new File(path + elementId + ".png");
    try {
      byte[] decodedBytes = DatatypeConverter.parseBase64Binary(data.replaceAll("data:image/.+;base64,", ""));
      // 源图片
      BufferedImage srcImage = ImageIO.read(new ByteArrayInputStream(decodedBytes));
      // 目标图片
      BufferedImage dstImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
      Graphics2D graphics = dstImage.createGraphics();
      // 圆形遮罩
      graphics.setClip(new Ellipse2D.Double(0, 0, imageSize, imageSize));
      // 缩放
      Image resizeImage = srcImage.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
      // This code ensures that all the pixels in the image are loaded
      Image temp = new ImageIcon(resizeImage).getImage();
      // Create the buffered image
      BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
      // Copy image to buffered image
      Graphics g = bufferedImage.createGraphics();
      // Clear background and paint the image
      g.setColor(Color.white);
      g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
      g.drawImage(temp, 0, 0, null);
      g.dispose();
      // Soften
      float softenFactor = 0.05f;
      float[] softenArray = { 0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
      Kernel kernel = new Kernel(3, 3, softenArray);
      ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
      bufferedImage = cOp.filter(bufferedImage, null);
      // 保存图片
      graphics.drawImage(bufferedImage, 0, 0, null);
      graphics.dispose();
      ImageIO.write(dstImage, "png", imageFile);
      srcImage.flush();
      dstImage.flush();
      // 保存数据库
      UploadElement uploadElement = new UploadElement();
      uploadElement.setId(elementId);
      service.saveUploadElement(uploadElement);
      // 返回
      result.put("ret", 0);
      result.put("id", elementId);
    } catch (Exception e) {
      result.put("ret", -1);
      result.put("msg", "Cannot process due to the image processing error.");
    }
    return result;
  }

  @RequestMapping("register")
  @ResponseBody
  public String register(@RequestParam String code, @RequestParam String device) {
    JsonObject response = new JsonObject();
    response.addProperty("error", service.updateRegisterCode(code, device));
    return response.toString();
  }

  @RequestMapping("version")
  @ResponseBody
  public String version() {
    Integer version = service.getVersion();
    if (version == null) {
      version = 0;
    }
    JsonObject response = new JsonObject();
    response.addProperty("version", version);
    return response.toString();
  }
   //TODO
//  @RequestMapping("update_category")
//  @ResponseBody
//  public List<CategoryVo> updateCategory() {
//    List<CategoryVo> result = new ArrayList<>();
//    List<Category> list = service.getCategories();
//    for(Category category : list) {
//      CategoryVo vo = new CategoryVo();
//      vo.setId(category.getId());
//      vo.setName(category.getName());
//      result.add(vo);
//    }
//    return result;
//  }

  @RequestMapping("update_category")
  @ResponseBody
  public List<CategoryVo> updateCategory(
      @RequestParam(value = "device", required = false) String device,
      @RequestParam(value = "lang", required = false) String lang
  ) {
    return service.getCategories(device, lang);
  }

  @RequestMapping("update_category_element")
  @ResponseBody
  public List<CategoryElement> updateCategoryElement() {
    return service.getCategoryElements();
  }

  @RequestMapping("update_star_element")
  @ResponseBody
  public List<StarElement> updateStarElement() {
    return service.getStarElements();
  }

  @RequestMapping("update_random_element")
  @ResponseBody
  public List<RandomElement> updateRandomElement() {
    return service.getRandomElements();
  }

  @RequestMapping("get_print_box")
  @ResponseBody
  public PrintBox getPrintBox(@RequestParam String code) {
    return service.getPrintBox(code);
  }

  @RequestMapping("use_print_box")
  @ResponseBody
  public PrintBox usePrintBox(@RequestParam String code) {
    PrintBox printBox = service.getPrintBox(code);
    if(printBox == null) {
      return null;
    }
    if(printBox.getLeftTime() <= 0) {
      return null;
    }
    try {
      printBox.setLeftTime(printBox.getLeftTime() - 1);
      service.savePrintBox(printBox);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return printBox;
  }

}
