<#--
需要配置：
spring.freemarker.expose-request-attributes: true
spring.freemarker.request-context-attribute: request
-->
<#if Request.request.contextPath == "/">
    <#assign ctx = "">
<#else >
    <#assign ctx = Request.request.contextPath>
</#if>

<#assign uri = request.getRequestUri()>
