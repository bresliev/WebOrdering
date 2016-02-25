<%@page contentType="text/html"%>
<%@page pageEncoding="Windows-1250"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="content">
    <c:if test="${not empty CompanySetting.downloadTip[oj]}">
    <div style="float:left;">
        <div class="navigation_bar_left dark_gray_gradient" style="width:622px;border-radius: 5px 0 0 0;">
            <h3 style="border-radius: 5px 5px;">DOWNLOAD</h3>
        </div>
        <div class="navigation_bar_right" style="width:64px;border-radius: 0 5px 0 0;"><img src="images/strelice.png" alt=""/></div>
        <div class="navigation_bar_content">
            <c:forEach items="${CompanySetting.downloadTip[oj]}" var="item">
            <div class="title_div dark_gray_gradient">
                <h3 class="dark_gray_gradient">${item.naziv}</h3>
                <img src="/${item.imageUrl}" alt=""/>
                <input type="button" class="btn light_gray_gradient" value="PREGLED" onClick="location.href='download?tipDownload=${item.id}'"/>
            </div>
            </c:forEach>
        </div>
    </div>
    </c:if>
    <c:if test="${not empty downloadFiles}">
        <div id="download" class="title_div dark_gray_gradient" style="width:716px;margin-top: 10px;">
            <h3 class="dark_gray_gradient" style="font-size: 12px;">${naziv}</h3>
            <c:forEach items="${downloadFiles}" var="brand">
                <div class="download_brand">${brand.key}</div>
                <c:forEach items="${brand.value}" var="item">
                    <div class="download">${item.naziv}</div>
                    <div style="float: right;"><input type="button" value="DOWNLOAD" class="btn light_gray_gradient" onclick="window.open('/${item.fajlUrl}')"/></div>
                    <div style="clear:both"></div>
                </c:forEach>
            </c:forEach>
        </div>
    </c:if>
</div>
