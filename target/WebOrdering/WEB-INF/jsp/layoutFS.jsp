<%@page contentType="text/html"%>
<%@page pageEncoding="Windows-1250"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/wo_darex.css"/>"/>
</head>
<body>
    <tiles:insertAttribute name="content"/>
</body>
</html>
