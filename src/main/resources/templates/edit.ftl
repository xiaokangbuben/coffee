<!DOCTYPE html>
<#include "base/base_func.ftl">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
<#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)><title>Coffee Drawing Machine</title><#else><title>咖奶糕点拉花</title></#if>
    <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.css">
  <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.css">
  <link rel="stylesheet" href="${ctx}/css/app.css">
  <!-- 全局变量 -->
  <script>
    var ctx = "${ctx}";
  </script>
</head>

<body style="margin:0;">

<div class="bodybox" style="max-width:1334px; min-width:320px; margin:0 auto; position:relative;height:100%;">
  <div class="page-group">
    <div class="page page-current" id="page_edit">

      <div id="making">

        <div id="edit_content" style="display: none;">
          <!--款式背景图。-->
          <img id="background_image" src="${ctx}/img/coffee.jpg">
          <!--设计区-->
          <div id="edit_container" style="position: absolute;">
            <div id="canvas_container" style="position: relative; user-select: none;">
              <canvas id="edit_canvas" style="border: 1px solid rgb(170, 170, 170);"></canvas>
            </div>
          </div>
        </div>

        <!--工具栏-->
        <div class="bar bar-tab" id="bar_tab" style="bottom: 0rem">
          <a class="tab-item" id="btn_show_category">
            <img src="${ctx}/img/btn_select_image.png" class="icon">
            <span class="tab-label"><#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>MATERIAL<#else>素材</#if></span>
          </a>
          <a class="tab-item" id="btn_show_input">
            <img src="${ctx}/img/btn_input_text.png" class="icon">
            <span class="tab-label"><#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>TEXT<#else>文字</#if></span>
          </a>
          <div class="tab-item" id="btn_add_photo">
            <input type="file" id="btn_upload_image">
            <img src="${ctx}/img/btn_upload_image.png" class="icon">
            <span class="tab-label"><#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>PHOTO<#else>照片</#if></span>
          </div>
        </div>

        <!--素材类别-->
        <div class="buttons-tab" id="category_tabs" style="bottom: -2rem;overflow:scroll;">
          <#list categories as category>
          <a href="#tab_${category.id}" category-id="${category.id}" class="tab-link button <#if category?index == 0>active</#if>"><#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>${category.enName}<#else>${category.name}</#if></a>
          </#list>
        </div>

        <!--素材内容-->
        <div class="tabs" id="category_content" style="bottom: -15rem;">
          <#list categories as category>
            <div id="tab_${category.id}" category-id="${category.id}" class="tab <#if category?index == 0>active</#if>">
              <div class="list-container"></div>
            </div>
          </#list>
        </div>

        <!--文字输入-->
        <div id="input_pannel" style="bottom: -13rem;">
          <div class="content-block" style="margin: 0 0;">
            <div class="button-row">
              <#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>
                <textarea style="color: #933500;" id="input_text" placeholder="Please enter the content" rows="4" cols="20"></textarea>
              <#else>
                <textarea style="color: #933500;" id="input_text" placeholder="请输入文字内容，将手机设置为什么字体，DIY系统就使用什么字体。" rows="4" cols="20"></textarea>
              </#if>
            </div>
            <p class="buttons-row">
              <a href="javascript:setTextColor('#000000');" class="button" style="background-color:#000000"></a>
              <a href="javascript:setTextColor('#933500');" class="button" style="background-color:#933500"></a>
              <a href="javascript:setTextColor('#FF0000');" class="button" style="background-color:#FF0000"></a>
              <a href="javascript:setTextColor('#666666');" class="button" style="background-color:#666666"></a>
              <a href="javascript:setTextColor('#FF00FF');" class="button" style="background-color:#FF00FF"></a>
              <a href="javascript:setTextColor('#66FF66');" class="button" style="background-color:#66FF66"></a>
              <a href="javascript:setTextColor('#0000FF');" class="button" style="background-color:#0000FF"></a>
              <a href="javascript:setTextColor('#FFFF00');" class="button" style="background-color:#FFFF00"></a>
              <a href="javascript:setTextColor('#FF9933');" class="button" style="background-color:#FF9933"></a>
            </p>
            <div class="row">
              <div class="col-50"><a id="btn_input_ok" class="button button-fill button-success"><#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>OK<#else>确定</#if></a></div>
              <div class="col-50"><a id="btn_input_cancel" class="button button-fill button-success"><#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>CANCEL<#else>取消</#if></a></div>
            </div>
          </div>
        </div>

        <!--编辑区-->
        <div id="edit_btn" style="bottom: -2rem;">
          <div class="button-row">
            <a id="btn_rotate"><img src="${ctx}/img/btn_rotate.png"></a>
            <a id="btn_delete"><img src="${ctx}/img/btn_delete.png"></a>
            <a id="btn_zoom_out"><img src="${ctx}/img/btn_zoom_out.png"></a>
            <a id="btn_zoom_in"><img src="${ctx}/img/btn_zoom_in.png"></a>
            <a id="btn_align"><img src="${ctx}/img/btn_align.png"></a>
          </div>
        </div>

        <!--提交按钮-->
        <div id="submit_div" style="display: none;">
          <#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>
            <img src="${ctx}/img/submit_en.png"/>
          <#else>
            <img src="${ctx}/img/submit.png"/>
          </#if>
        </div>
      </div>

    </div>
  </div>

</div>

<script type="text/javascript" src="//g.alicdn.com/sj/lib/zepto/zepto.js" charset="utf-8"></script>
<script>
  $.config = {router: false};
</script>
<script type="text/javascript" src="//g.alicdn.com/msui/sm/0.6.2/js/sm.js" charset="utf-8"></script>
<script type="text/javascript" src="//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/webjars/fabric/2.2.4/dist/fabric.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/js/app.js" charset="utf-8"></script>
</body>
</html>