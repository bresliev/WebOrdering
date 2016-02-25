<%@page contentType="text/html" %>
<%@page pageEncoding="Windows-1250" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content">
    <c:choose>
        <c:when test="${not empty flashSize}">
            <object width="716px" height="${flashSize}" style="margin:0;padding:0;margin-left:10px;"
                    classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                    codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0">
                <param name="SRC" value="/WO/flash/${oj}/podrska.swf"/>
                <param name="scale" value="exactfit"/>
                <param name="wmode" value="transparent"/>
                <embed src="/WO/flash/${oj}/podrska.swf" width="716px" height="${flashSize}" wmode="transparent"/>
            </object>
        </c:when>
        <c:otherwise>
            <div style="float:left;">
                <div class="navigation_bar_left dark_gray_gradient" style="width:622px;">
                    <h3>PODRŠKA</h3>
                </div>
                <div class="navigation_bar_right" style="width:64px;"><img src="images/strelice.png" alt=""/></div>
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${oj == 30}">
            <div style="float:left;width:716px;margin-top:25px;height:100%;margin-left:10px;color:#58595b;font-size: 13px;line-height:16px;">
                <b>Poštovani Partneri,</b><br><br>
                Darex Partner Podrška je na raspolaganju za sva Vaša pitanja, sugestije i konsultacije, ukoliko
                naiðete<br>
                na bilo kakav problem u toku korišæenja naše aplikacije Web Ordering.<br><br>
                Odgovore na Vaša pitanja možete dobiti putem:<br>
                telefon: <b>+381 (0)65 31 95 298</b><br>
                e-mail: <b><a style="color:#58595b;" href="mailto:webpodrska@darexmax.me">webpodrska@darexmax.me</a></b> , na
                koji æemo odgovoriti u što kraæem vremenskom roku.<br><br>
                Radno vreme Darex Partner Podrške je svakog radnog dana:<br>
                (Ponedeljak - Petak) <b>od 8:30h do 16:30h</b>
            </div>
        </c:when>
        <c:otherwise>
            <div style="float:left;width:716px;margin-top:25px;height:100%;margin-left:10px;color:#58595b;font-size: 13px;line-height:16px;">
                <b>Poštovani Partneri,</b><br><br>
                Darex Partner Podrška je na raspolaganju za sva Vaša pitanja, sugestije i konsultacije, ukoliko
                naiðete<br>
                na bilo kakav problem u toku korišæenja naše aplikacije Web Ordering.<br><br>
                Odgovore na Vaša pitanja možete dobiti putem:<br>
                telefon: <b>065 31 95 298</b><br>
                e-mail: <b><a style="color:#58595b;" href="mailto:webpodrska@darex.rs">webpodrska@darex.rs</a></b> , na
                koji æemo odgovoriti u što kraæem vremenskom roku.<br><br>
                Radno vreme Darex Partner Podrške je svakog radnog dana:<br>
                (Ponedeljak - Petak) <b>od 8:30h do 16:30h</b>
            </div>
        </c:otherwise>
    </c:choose>

</div>
