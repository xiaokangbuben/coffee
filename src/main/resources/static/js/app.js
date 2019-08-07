/*********** common ***********/
function getQueryString(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null) {
    return unescape(r[2]);
  }
  return null;
}

/*********** index ***********/
function addCategoryElements(categoryId, pageData) {
  // 每次请求数量
  var size = 15;
  // 容器
  var container = $("#tab_" + categoryId + " .list-container");
  // 当前总个数
  var count = container.attr("count");
  if (count == null) {
    count = 0;
  } else {
    count = parseInt(count);
  }
  // 如果count不能整除size，表示已经加载完成
  if (count % size !== 0) {
    return
  }
  var url = ctx + "/get_category_element_list?categoryId=" + categoryId + "&start=" + count + "&size=" + size;
  $.getJSON(url, function (data) {
    var content = data.content;
    if (content.length < size) {
      // 删除加载提示符
      $("#tab_" + categoryId + " .infinite-scroll-preloader").hide();
    }
    // 个数
    count = count + content.length;
    container.attr("count", count);
    // 显示
    for (var i = 0; i < content.length; ++i) {
      var item = content[i];
      var div = '<div class="img-box-3">'
          + '<a href=' + ctx + '"/code/' + item.id + '" data-no-cache="true">'
          + '<img src="' + ctx + '/upload/small/' + item.id + '.png" width="150rem">'
          + '</a>'
          + '</div>';
      container.append(div);
      pageData.loading = false;
    }
  });
}

/*********** edit ***********/
var timers = {};

function moveBottom(id, from, to, time) {
  clearInterval(timers[id]);
  var o = document.getElementById(id);
  if (o.style.bottom !== to + "rem") {
    o.style.bottom = from + "rem";
    var step = (to - from) * 30.0 / time;
    timers[id] = setInterval(function () {
      from += step;
      if (step > 0) {
        if (from >= to) {
          from = to;
          clearInterval(timers[id]);
        }
      } else {
        if (from <= to) {
          from = to;
          clearInterval(timers[id]);
        }
      }
      o.style.bottom = from + "rem";
    }, 30.0);
  }
}

function setEditBtnDisplay(isShow) {
  if (isShow) {
    moveBottom("edit_btn", -2, 4.2, 400);
  } else {
    moveBottom("edit_btn", 4.2, -2, 400);
  }
}

function setBarTabDisplay(isShow) {
  if (isShow) {
    moveBottom("bar_tab", -3.6, 0, 400);
  } else {
    moveBottom("bar_tab", 0, -3.6, 400);
  }
}

function setCategoryTabsDisplay(isShow) {
  if (isShow) {
    moveBottom("category_tabs", -2, 0, 400);
  } else {
    moveBottom("category_tabs", 0, -2, 400);
  }
}

function setCategoryContentDisplay(isShow) {
  if (isShow) {
    moveBottom("category_content", -15, 2, 400);
  } else {
    moveBottom("category_content", 2, -15, 400);
  }
}

function setInputPannelDisplay(isShow) {
  if (isShow) {
    moveBottom("input_pannel", -13, 0, 400);
  } else {
    moveBottom("input_pannel", 0, -13, 400);
  }
}

function addPattern(url, pageData) {
  fabric.Image.fromURL(url, function (img) {
    img.set({
      "centeredScaling": true,
      "originX": "center",
      "originY": "center"
    });
    setDefaultScale(img, pageData);
    pageData.canvas.add(img);
    pageData.canvas.centerObject(img);
    pageData.canvas.setActiveObject(img);
    pageData.canvas.requestRenderAll();
  });
}

function initCategoryContent(categoryId) {
  // 每次请求数量
  var start = 0;
  var size = 30;
  var url = ctx + "/get_category_element_list?categoryId=" + categoryId + "&start=" + start + "&size=" + size;
  $.getJSON(url, function (data) {
    var content = data.content;
    var container = $("#tab_" + categoryId + " .list-container");
    // 清空
    container.empty();
    // 添加
    for (var i = 0; i < content.length; ++i) {
      var item = content[i];
      var div = '<div class="img-box-4">'
          + '<a class="edit-element" element-id="' + item.id + '">'
          + '<img src="' + ctx + '/upload/small/' + item.id + '.png" width="150rem">'
          + '</a>'
          + '</div>';
      container.append(div);
    }
  });
}

function setCanvasClipTo(pageData) {
  pageData.canvas.clipTo = function (ctx) {
    ctx.arc(pageData.canvasLength / 2, pageData.canvasLength / 2, pageData.canvasLength / 2, 0, Math.PI * 2, true);
  };
}

function setDefaultScale(o, pageData) {
  var width;
  var height;
  if (o.get("type") === "text") {
    width = o.calcTextWidth();
    height = o.calcTextHeight();
  } else {
    width = o.get("width");
    height = o.get("height");
  }
  var wScale = pageData.canvasLength / width;
  var hScale = pageData.canvasLength / height;
  var scale;
  if (wScale < hScale) {
    scale = wScale;
  } else {
    scale = hScale;
  }
  o.scale(scale);
}

function setTextColor(color) {
  $("#input_text").css("color", color);
  return false;
}

$(function () {
  'use strict';
  // index
  $(document).on("pageInit", "#page_index", function (e, id, page) {
    // 当前页数据
    var pageData = {};
    pageData.loading = false;
    // 初始化
    addCategoryElements($(".tabs div.active").attr("category-id"), pageData);
    // tab切换
    $(".buttons-tab > a").on('click', function () {
      var categoryId = $(this).attr("category-id");
      addCategoryElements(categoryId, pageData);
    });
    // 滚动到底部
    $(page).on('infinite', function () {
      // 如果正在加载，则退出
      if (pageData.loading) {
        return;
      }
      pageData.loading = true;
      var categoryId = $(this).find('.infinite-scroll.active').attr('category-id');
      addCategoryElements(categoryId, pageData);
      $.refreshScroller();
    });
  });

  // edit
  $(document).on("pageInit", "#page_edit", function (e, id, page) {
    // 当前页数据
    var pageData = {};
    // 初始化
    pageData.totalLength = $(window).width();
    pageData.canvasLength = pageData.totalLength * 0.7811;
    $("#background_image").css("width", pageData.totalLength).css("height", pageData.totalLength);
    $("#edit_container").css("left", pageData.totalLength * 0.115).css("top", pageData.totalLength * 0.056);
    $("#canvas_container").attr("width", pageData.canvasLength).attr("height", pageData.canvasLength);
    $("#edit_canvas").attr("width", pageData.canvasLength).attr("height", pageData.canvasLength);
    $("#edit_content").css("display", "");
    // canvas初始化
    var canvas = new fabric.Canvas("edit_canvas");
    pageData.canvas = canvas;
    // 圆形遮罩
    setCanvasClipTo(pageData);
    canvas.on("selection:created", function (opt) {
      setEditBtnDisplay(true);
    });
    canvas.on("selection:cleared", function (opt) {
      setEditBtnDisplay(false);
    });
    canvas.on("object:added", function (opt) {
      $("#submit_div").css("display", "");
    });
    canvas.on("object:removed", function (opt) {
      var os = canvas.getObjects();
      if (os.length === 0) {
        setBarTabDisplay(true);
        $("#submit_div").css("display", "none");
      }
    });
    // 是否编辑已有的素材
    var paramId = getQueryString("id");
    if(paramId != null) {
      setCategoryTabsDisplay(false);
      setCategoryContentDisplay(false);
      addPattern(ctx + "/upload/big/" + paramId + ".png", pageData);
    }
    // 素材tab切换
    $(".buttons-tab > a").on('click', function () {
      var categoryId = $(this).attr("category-id");
      initCategoryContent(categoryId);
    });
    // 素材点击
    $(".list-container").on('click', '.edit-element', function () {
      var elementId = $(this).attr("element-id");
      setCategoryTabsDisplay(false);
      setCategoryContentDisplay(false);
      setBarTabDisplay(true);
      addPattern(ctx + "/upload/big/" + elementId + ".png", pageData);
      return false;
    });
    // 点击背景图
    $("#background_image").click(function () {
      setCategoryTabsDisplay(false);
      setCategoryContentDisplay(false);
      setInputPannelDisplay(false);
      setBarTabDisplay(true);
      // 取消选择
      canvas.discardActiveObject();
    });
    // 显示素材选择
    $("#btn_show_category").click(function () {
      setCategoryTabsDisplay(true);
      setCategoryContentDisplay(true);
      setBarTabDisplay(false);
      var categoryId = $(".buttons-tab .active").attr("category-id");
      initCategoryContent(categoryId);
    });
    // 显示文字输入
    $("#btn_show_input").click(function () {
      setInputPannelDisplay(true);
      setBarTabDisplay(false);
    });
    // 文字输入取消
    $("#btn_input_cancel").click(function () {
      setInputPannelDisplay(false);
      setBarTabDisplay(true);
    });
    // 文字输入确定
    $("#btn_input_ok").click(function () {
      var content = $("#input_text").val();
      if (content !== "") {
        var text = new fabric.Text(content, {
          "fill": $("#input_text").css("color"),
          "textAlign": 'center',
          "centeredScaling": true,
          "originX": "center",
          "originY": "center"
        });
        setDefaultScale(text, pageData);
        canvas.add(text);
        canvas.centerObject(text);
        canvas.setActiveObject(text);
        canvas.requestRenderAll();
      }
      setInputPannelDisplay(false);
    });
    // 编辑区-旋转
    $("#btn_rotate").click(function () {
      var o = canvas.getActiveObject();
      if (o != null) {
        var angle = o.get('angle');
        o.rotate(angle + 15);
        canvas.requestRenderAll();
      }
      return false;
    });
    // 编辑区-删除
    $("#btn_delete").click(function () {
      var o = canvas.getActiveObject();
      if (o != null) {
        canvas.remove(o);
        var os = canvas.getObjects();
        if (os.length > 0) {
          canvas.setActiveObject(os[os.length - 1]);
        }
        canvas.requestRenderAll();
      }
      return false;
    });
    // 编辑区-放大
    $("#btn_zoom_out").click(function () {
      var o = canvas.getActiveObject();
      var scaleX = o.get("scaleX") / 0.8;
      var scaleY = o.get("scaleY") / 0.8;
      o.set({
        "scaleX": scaleX,
        "scaleY": scaleY
      });
      canvas.requestRenderAll();
    });
    // 编辑区-缩小
    $("#btn_zoom_in").click(function () {
      var o = canvas.getActiveObject();
      var scaleX = o.get("scaleX") * 0.8;
      var scaleY = o.get("scaleY") * 0.8;
      o.set({
        "scaleX": scaleX,
        "scaleY": scaleY
      });
      canvas.requestRenderAll();
    });
    // 编辑区-初始化大小
    $("#btn_align").click(function () {
      var o = canvas.getActiveObject();
      setDefaultScale(o, pageData);
      canvas.requestRenderAll();
    });
    // 本地上传编辑图片
    document.getElementById('btn_upload_image').onchange = function handleImage(e) {
      if($("#btn_upload_image").val() === "") {
        return false;
      }
      var reader = new FileReader();
      reader.onload = function (event) {
        var obj = new Image();
        obj.src = event.target.result;
        obj.onload = function () {
          var img = new fabric.Image(obj);
          img.set({
            "centeredScaling": true,
            "originX": "center",
            "originY": "center"
          });
          setDefaultScale(img, pageData);
          canvas.add(img);
          canvas.centerObject(img);
          canvas.setActiveObject(img);
          canvas.requestRenderAll();
        }
      };
      console.log(e);
      reader.readAsDataURL(e.target.files[0]);
      setBarTabDisplay(true);
      return false;
    };
    // 上传图片到服务器
    $("#submit_div").click(function () {
      // 去掉遮罩
      canvas.clipTo = null;
      var data = canvas.toDataURL({multiplier: 2});
      // 开启遮罩
      setCanvasClipTo(pageData);
      canvas.requestRenderAll();
      $.ajax({
        url: ctx + '/upload',
        data: {data: data},
        type: 'post',
        dataType: 'json',
        timeout: 10000,
        async: false,
        error: function () {
          $.alert('上传失败');
        },
        success: function (result) {
          if (result.ret === 0) {
            window.location.href = ctx + "/code/" + result.id;
          } else {
            $.alert('上传失败');
          }
        }
      });
    });
  });

  // code
  $(document).on("pageInit", "#page_code", function (e, id, page) {
  });

  // 框架初始化
  $.init();
});