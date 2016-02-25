<%@page contentType="text/html" %>
<%@page pageEncoding="Windows-1250" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="G4">
    <div class="G4_L light_gray_gradient">  <!--style="min-width: 177px;"-->
        <input type="button" value="Korpa" class="btn ${url == '/basket' ? "class='selected'":""} light_gray_gradient"
               onClick="location.href='basket'"/>
        <c:choose>
            <c:when test="${loginUser.woUser.userType == 'INTERNI' ||  loginUser.woUserHasRights['PKP']}">
                <input type="button" value="Kartica" class="btn light_gray_gradient" id="karticaPKP"/>
            </c:when>
            <c:otherwise>
                <input type="button" value="Kartica" class="btn light_gray_gradient" id="kartica"/>
            </c:otherwise>
        </c:choose>
        <input type="hidden" id="karticaHidden"/>
        <c:if test="${oj == 19}">
        <input type="button" value="Kursna lista NBS" class="btn light_gray_gradient" onClick="window.open('http://www.nbs.rs/internet/latinica/scripts/ondate.html')"/>
        </c:if>
    </div>
    <div class="G4_D">
        <div class="ui-widgetT">
            Kategorije:
            <select class="filterPrviNivo" style="width: 170px;">
                <option value="-1">-- BREND --</option>
                <c:forEach items="${CompanySetting.filterPrviNivo[oj]}" var="item">
                    <option value="19/${item.id.klasifikacija}">${item.naziv}</option>
                </c:forEach>
            </select>
            <select class="filterDrugiNivo" style="width: 170px;">
                <option value="-1">-- GRUPA --</option>
            </select>
            <input type="text" id="productId" value="ŠIFRA" onfocus="javascript: this.value = ''"
                   onblur="javascript: if (this.value=='') this.value = 'ŠIFRA'"
                   style="text-align: center;width:60px;"/>
            <input type="text" id="namePattern" value="NAZIV ARTIKLA" onfocus="javascript: this.value = ''"
                   onblur="javascript: if (this.value=='') this.value = 'NAZIV ARTIKLA'"
                   style="text-align: center;width:240px;"/>
            <input type="submit" id="search_button" class="btn search" value="PRETRAŽI" style="margin-right:5px;"/>
        </div>
    </div>
</div>
<div style="clear:both"></div>