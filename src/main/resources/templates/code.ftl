<!DOCTYPE html>
<#include "base/base_func.ftl">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <title>咖奶糕点拉花</title>
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
    <div id="page_code" class="page">
      <div class="content" style="bottom:3rem;">
        <!--目标图片-->
        <div id="coffee_result_rec">
          <img src="${ctx}/img/coffee.jpg" id="element_background" />
          <img src="${ctx}/upload/big/${id}.png" id="element_image" />
        </div>
        <!--打印码-->
        <div id="code_number">
          <div><#if Session.lang ?? && Session.lang == 1>PRINT CODE<#else>打印码</#if>：<span>${id}</span></div>
        </div>
        <!--其他说明-->
        <div id="code_remark"><#if Session.lang ?? && Session.lang == 1>This is my design, is it beautiful?<#else>这是我设计的咖啡拉花，漂亮吧？</#if>	</div>
      </div>
      <div class="bar bar-tab" style="padding-left:2rem;padding-right:2rem;padding-top:0.3rem;height:3rem;">
        <a href="${ctx}/edit?id=${id}" class="button button-round" style="color:#FFFFFF;background:#51bf87;" data-no-cache="true"><#if Session.lang ?? && Session.lang == 1>GO ON DIY<#else>我要继续DIY</#if></a>
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