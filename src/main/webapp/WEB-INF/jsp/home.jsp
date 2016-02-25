<%@page contentType="text/html"%>
<%@page pageEncoding="Windows-1250"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="content">
    <c:choose>
        <c:when test="${not empty flashSize}">
            <object width="716px" height="${flashSize}" style="margin:0;padding:0;margin-left:10px;"
                    classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                    codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0">
                <param name="SRC" value="/WO/flash/${oj}/naslovna.swf"/>
                <param name="scale" value="exactfit"/>
                <param name="wmode" value="transparent"/>
                <embed src="/WO/flash/${oj}/naslovna.swf" width="716px" height="${flashSize}" wmode="transparent"/>
            </object>
        </c:when>
        <c:otherwise>
            <div style="float:left;">
                <div class="navigation_bar_left dark_gray_gradient" style="width:622px;">
                    <h3>NASLOVNA</h3>
                </div>
                <div class="navigation_bar_right" style="width:64px;"><img src="images/strelice.png" alt=""/></div>
            </div>
        </c:otherwise>
    </c:choose>


    <c:if test="${not empty vesti}">
        <div class="title_div dark_gray_gradient" style="width:716px;">
            <h3 class="dark_gray_gradient" style="margin-bottom: 5px;font-size:15px;color:#fff;">VESTI / AKTUELNO</h3>
            <c:forEach items="${vesti}" var="item">
                <div class="download">${item.naziv}</div>
                <div style="float: right;"><input type="button" value="DOWNLOAD" class="btn light_gray_gradient" onclick="window.open('/${item.fajlUrl}')"/></div>
                <div style="clear:both"></div>
            </c:forEach>

        </div>
    </c:if>
    <c:if test="${not empty productList}">
    <div class="title_div light_gray_gradient" style="width:714px;height:35px;border:1px solid #b3b3b3;border-radius:5px;margin-top: 0;margin-bottom: 0;">
        <h3 style="color: #4c4b4b;">IZDVAJAMO IZ AKCIJE</h3>
    </div>
    <div style="width:736px;">
    <c:forEach items="${productList}" var="item">
            	<div class="product_item">
                    <div class="dezen">${item.dezenIstruktira}</div>
                    <img id="dialog-img-${item.proizvod}" class="product_item_img opener" src="/WO/images/medium/${item.proizvod}.jpg" alt=""/>
                    <img src="images/Akcije.png" alt="" style="position: absolute;top: 34px;right: 11px;" />
                    <div style="width:220px;padding:0 5px 0 5px;border-bottom: 1px solid #b3b3b3;">
                        <div class="cena">${item.cena} eur/${item.jedinicaMere.skracenaOznaka}</div>
                        <div class="proizvodjac ellipsis">${item.proizvod} - ${item.proizvodjac}</div>
                        <div class="ime multiline">${item.nazivProizvoda} ${item.dodatniNaziv}</div>
                    </div>
                    <div class="kol_pak ellipsis">Kolièina po pakovanju4: ${item.kolicinaPoPakovanju} ${item.jedinicaMere.skracenaOznaka}</div>
                    <div class="kolicina">Poruèiti: <input type="number" min="1" step="1" id="orderedQuantity-${item.proizvod}" value=""/>${item.jedinicaMereAltRef.skracenaOznaka}</div>
                 	<div class="zalihe ellipsis" id="raspolozivo-${item.proizvod}">Zalihe4: ${item.raspolozivo} ${item.jedinicaMere.skracenaOznaka}</div>
                    <div class="potvrda">
						<div style="float: left;"><input type="submit" id="dialog-${item.proizvod}"  class="btn zoom opener"  value="UVEÆAJ"/></div>
					    <div style="float: right;"><input type="submit" id="basket-${item.proizvod}" class="btn basket add_to_basket"  value="DODAJ U KORPU"/></div>
					</div>
                    <div class="dialog" id="opener-dialog-${item.proizvod}" title="${item.nazivProizvoda} ${item.dodatniNaziv}">
                        <img style="margin:0px;border:none;" width="700px" height="490px" src="/WO/images/large/${item.proizvod}.jpg" alt="" />
                    </div>
                 </div>
    </c:forEach>
    </div>
    </c:if>
</div>
