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
    <div class="page page-current" id="page_index">

      <div class="buttons-tab fixed-tab" style="overflow:scroll;">
        <a href="#tab_0" category-id="0" class="tab-link button active"><#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>ALL<#else>全部</#if></a>
        <#list categories as category>
          <a href="#tab_${category.id}" category-id="${category.id}" class="tab-link button"><#if Session.lang ?? && (Session.lang == 1 || Session.flag == 1)>${category.enName}<#else>${category.name}</#if></a>
        </#list>
      </div>

      <div class="content" style="top:2rem;bottom:3rem;">
        <div class="tabs">
          <div id="tab_0" category-id="0" class="tab infinite-scroll active" >
            <div class="list-container"></div>
            <div class="infinite-scroll-preloader" style="margin-top: 0rem">
              <div class="preloader">
              </div>
            </div>
          </div>
        <#list categories as category>
          <div id="tab_${category.id}" category-id="${category.id}" class="tab infinite-scroll">
            <div class="list-container"></div>
            <div class="infinite-scroll-preloader" style="margin-top: 0rem">
              <div class="preloader">
              </div>
            </div>
          </div>
        </#list>
        </div>
      </div>

      <div class="bar bar-tab" style="height:3rem;">
        <div class="row" style="overflow:visible;margin:0%;padding-right:4%;">
          <div class="col-100">
            <a class="button button-fill button-warning" href="${ctx}/edit" style="color:#FFFFFF;height:1.8rem;padding:0.3rem;" data-no-cache="true"><#if Session.lang ??  && (Session.lang == 1 || Session.flag == 1)>I WANT DIY<#else>我要DIY</#if></a>
          </div>
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