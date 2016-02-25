<%@page contentType="text/html" %>
<%@page pageEncoding="Windows-1250" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="content">
    <div>
        <div class="navigation_bar_left dark_gray_gradient">
            <h3 id="formActionLabel"></h3>
        </div>
        <div class="navigation_bar_right" style="display: inline">
            <p>Ovde bi trebalo da dojde neka slika!!!</p>
        </div>
    </div>
    <div style="width:736px;">
        <c:forEach items="${groupList}" var="item">
            <div class="product_item">

            </div>
        </c:forEach>
    </div>
</div>
