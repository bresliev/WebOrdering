<%@page contentType="text/html"%>
<%@page pageEncoding="Windows-1250"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css">
    <c:choose>
        <c:when test="${oj == 40}"><link rel="stylesheet" type="text/css" href="<c:url value="/css/wo_itison.css"/>"/></c:when>
        <c:otherwise><link rel="stylesheet" type="text/css" href="<c:url value="/css/wo_darex.css"/>"/></c:otherwise>
    </c:choose>

    <script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.9.2.js"></script>
    <!--<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>-->
    <script type="text/javascript" src="js/web_ordering.js"></script>
</head>
	<!--[if gte IE 9]>
	   <style type="text/css">
	        .dark_gray_gradient {filter: none;}
			.light_gray_gradient {filter: none;}
       </style>
    <![endif]-->
<body>
<div id="header">
    <tiles:insertAttribute name="header"/>
</div>

<div class="container">
    <tiles:insertAttribute name="menu_G4"/>
    <tiles:insertAttribute name="menu"/>
    <tiles:insertAttribute name="body"/>
    <tiles:insertAttribute name="right"/>
</div>
<div class="footer">
    <tiles:insertAttribute name="footer"/>
</div>
<input type="hidden" id="formAction" value=""/>
<input type="hidden" id="formActionLabelTemp" value=""/>
<input type="hidden" id="brandId" value=""/>
<input type="hidden" id="perPage" value="${perPage}"/>
<div id="dialog-message" title="GREŠKA">
    <img src="images/error.png" width="20px" height="20px" style="float: left;margin: 10px;" alt=""/>
    <div class="error-box"></div>
</div>
<div id="dialog-message-w" title="UPOZORENJE">
    <img src="images/warning.png" width="20px" height="20px" style="float: left;margin: 10px;" alt=""/>
    <div class="error-box"></div>
</div>
<div id="dialog-message-i" title="INFO">
    <img src="images/info.png" width="20px" height="20px" style="float: left;margin: 10px;" alt=""/>
    <div class="error-box"></div>
</div>
<div id="toTop"><img src="images/back_to_top.png" alt=""/></div>

</body>
</html>
