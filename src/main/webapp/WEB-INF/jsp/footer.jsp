<%@page pageEncoding="Windows-1250"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:choose>
    <c:when test="${oj == 30}">
        <b>Copyright &copy; Darexmax d.o.o. Podgorica</b>&nbsp;&nbsp;&nbsp;Ilije Plamenca bb, Zabjelo , 81000 Podgorica, tel: + 382 (0)20 225 289, fax: + 382 (0)20 225 289, e-mail: <b><a style="color:#4c4b4b;" href="mailto:info@darexmax.me">info@darexmax.me</a></b>
    </c:when>
    <c:otherwise>
        <b>Copyright &copy; Darex doo</b>&nbsp;&nbsp;&nbsp;Autoput Beograd - Zagreb bb, 11077 Novi Beograd, tel: 011 2091 600, fax: 011 2091 699, e-mail: <b><a style="color:#4c4b4b;" href="mailto:info@darex.rs">info@darex.rs</a></b>
    </c:otherwise>
</c:choose>

