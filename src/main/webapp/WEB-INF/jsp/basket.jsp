<%@page contentType="text/html"%>
<%@page pageEncoding="Windows-1250"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="content">
    <div style="float:left;">
        <div class="navigation_bar_left dark_gray_gradient" style="width:622px;">
            <h3 style="border-radius: 5px 5px;">KORPA</h3>
        </div>
        <div class="navigation_bar_right" style="width:64px;"><img src="images/strelice.png" alt=""/></div>
    </div>
    <c:choose>
        <c:when test="${not empty errorMsg}">
            <div class="msg_div">
                <img src="images/error.png" alt="" style="vertical-align:middle;"/>&nbsp;&nbsp;${errorMsg}
            </div>
        </c:when>
        <c:when test="${protvrdaKorpe=='success' && not empty fakture}">
            <form id="formFakture" action="predracun" method="post" target="_blank">
                <div class="msg_div">
                    <img src="images/info.png" alt="" style="vertical-align:middle;"/> Korpa je uspešno potvrðena.&nbsp;
                    <c:forEach items="${fakture}" var="item">
                        <a href="javascript:;">${item.id.idFinDokumenta}/${item.id.organizacionaJedinica}/${item.id.godina}/${item.id.idVd}</a>&nbsp;&nbsp;
                    </c:forEach>
                    <input type="hidden" id="idDokumenta" name="idDokumenta" value=""/>
                    <input type="hidden" id="userRight" value="${loginUser.woUser.userType == 'INTERNI' ||  loginUser.woUserHasRights['PFD']}"/>
                </div>
            </form>
        </c:when>
    </c:choose>
    <c:if test="${not empty loginUser.basket}">
    <form id="chceckOutBasket" action="chceckOutBasket" method="POST">
    <table class="korpa" border="0" cellpadding=0 cellspacing=0>
        <th>R. br.</th>
        <th style="text-align:left">Šifra</th>
        <th style="text-align:left;width:203px;">Naziv Prizvoda</th>
        <th>kol/pak</th>
        <th>kol/JM</th>
        <th>JM</th>
        <th style="text-align:left">Cena &euro;/JM</th>
        <th style="text-align:left">Iznos &euro;</th>
        <c:if test="${loginUser.woUser.userType == 'INTERNI'}">
        <th style="text-align:left">Rabat</th>
        <th style="text-align:left">Ekstra</th>
        </c:if>
        <th style="width:16px;"></th>
        <c:forEach items="${loginUser.basket}" var="item" varStatus="i">
        <tr id="korpa_uvecaj_${item.value.proizvod.proizvod}">
            <td>
                <c:set var="rbStavke" value="${item.value.id}"/>
                <c:choose>
                    <c:when test="${i.count < 10}">00${i.count}</c:when>
                    <c:when test="${i.count < 100}">0${i.count}</c:when>
                    <c:otherwise>${i.count}</c:otherwise>
                </c:choose>
            </td>
            <td style="text-align:left">${item.value.proizvod.proizvod}</td>
            <td>
                <c:choose>
                    <c:when test="${loginUser.woUser.userType == 'INTERNI'}">
                        <div class="multiline" style="width:220px;">${item.value.proizvod.nazivProizvoda} ${item.value.proizvod.dodatniNaziv}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="multiline" style="width:280px;">${item.value.proizvod.nazivProizvoda} ${item.value.proizvod.dodatniNaziv}</div>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${item.value.kolPoPakovanju}</td>
            <td>${item.value.kolicina}</td>
            <td width="40px">${item.value.proizvod.jedinicaMere.skracenaOznaka}</td>
            <td style="text-align:left">${item.value.cena}</td>
            <td style="text-align:left">${item.value.vrednost}</td>
            <c:if test="${loginUser.woUser.userType == 'INTERNI'}">
                <td style="text-align:left" class="not_link">${item.value.rabat}</td>
                <td style="text-align:left" class="not_link"><input type="text" name="dodatniRabat" style="width:40px;border:solid 1px #4c4b4b;color: #4c4b4b;"/></td>
            </c:if>
            <td>
                <img src="images/remove.png" alt="" onClick="location.href='removeFromBasket?productId=${item.key}'"/>
                <div class="dialog" id="uvecaj_${item.value.proizvod.proizvod}" title="${item.value.proizvod.nazivProizvoda} ${item.value.proizvod.dodatniNaziv}">
                    <img style="margin:0px;border:none;" width="700px" height="490px" src="/WO/images/large/${item.value.proizvod.proizvod}.jpg" alt=""/>
                </div>
            </td>
        </tr>
        </c:forEach>
    </table>


    <div class="korpa_total">
        <hr color="#bbbbbb" class="thin"/>
        <div class="prevoz_placanje">
            <input type="radio" name="nacinTransporta" value="1"/><label>Vozilo dobavljaèa</label><br>
            <input type="radio" name="nacinTransporta" value="2"/><label>Vozilo kupca</label><br>
            <input type="radio" name="nacinTransporta" value="5"/><label>Brza pošta</label>
        </div>
        <div class="prevoz_placanje">
            <input  type="hidden"  name="maxRokPlacanja" value="${loginUser.woPartnerSetting[0].maxRokPlacanja}"/>
            <input type="radio" name="nacinPlacanja" value="CAS"/><label>Gotovina</label><br>
            <input type="radio" name="nacinPlacanja" value="ODL" ${ loginUser.woPartnerSetting[0].maxRokPlacanja == 0 ? "disabled":""} /><label>Odloženo</label><br>
            <input type="radio" name="nacinPlacanja" value="AVA" /><label>Avans</label>
        </div>
        <div class="total">
            <h1>Ukupna bruto vrednost bez PDV-a :</h1>

            <h2>${loginUser.orderValue} &euro;</h2>

            <h1>Ukupna bruto vrednost sa PDV-om:</h1>

            <h2>${loginUser.orderValueWithTax} &euro;</h2>
        </div>
        <hr color="#bbbbbb"  class="thin" style="clear:both;margin-bottom:5px;"/>
    </div>
    <c:choose>
            <c:when test="${loginUser.woUser.userType == 'INTERNI'}">
                <!--[if lte IE 9]><div style="margin-left:10px;color:#4c4b4b">Napomena</div><![endif]-->
                <textarea name="napomena" placeholder="Napomena" cols="30" rows="5" spellcheck="false"></textarea>
            </c:when>
            <c:otherwise>
                <!--[if lte IE 9]><div style="margin-left:10px;color:#4c4b4b">Adresa Isporuke</div><![endif]-->
                <textarea name="adresa" placeholder="Adresa Isporuke" cols="30" rows="5" spellcheck="false"></textarea>
            </c:otherwise>
    </c:choose>



    <hr color="#bbbbbb"  class="thin" style="margin-left:10px;margin-bottom: 10px;"/>

    <div style="float: left;margin-left:10px;"><input type="button" class="btn ponisti" value="PONIŠTI" onClick="location.href='removeBasket'" /></div>
    <div style="float: right;margin-left:10px;"><input type="button" id="chceckOutBasketBtn" class="btn potvrdi" value="POTVRDI"/></div>
    </form>
    </c:if>

</div>
