<%@page contentType="text/html" %>
<%@page pageEncoding="Windows-1250" %>
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
                    <img src="images/info.png" alt="" style="vertical-align:middle;"/> Korpa je uspe�no potvr�ena.&nbsp;
                    <c:forEach items="${fakture}" var="item">
                        <a href="javascript:;">${item.id.idFinDokumenta}/${item.id.organizacionaJedinica}/${item.id.godina}/${item.id.idVd}</a>&nbsp;&nbsp;
                    </c:forEach>
                    <input type="hidden" id="idDokumenta" name="idDokumenta" value=""/>
                    <input type="hidden" id="userRight"
                           value="${loginUser.woUser.userType == 'INTERNI' ||  loginUser.woUserHasRights['PFD']}"/>
                </div>
            </form>
        </c:when>
    </c:choose>
    <c:if test="${not empty loginUser.basket}">
        <form id="chceckOutBasket" action="chceckOutBasket" method="POST">
            <table class="korpa" border="0" cellpadding=0 cellspacing=0>
                <th>R. br.</th>
                <th style="text-align:left">�ifra</th>
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
                    <tr id="korpa_uvecaj_${item.value.proizvod.proizvod}" style="border-bottom: 1px solid #b3b3b3">
                        <td class="underllned">
                            <c:set var="rbStavke" value="${item.value.id}"/>
                            <c:choose>
                                <c:when test="${i.count < 10}">00${i.count}</c:when>
                                <c:when test="${i.count < 100}">0${i.count}</c:when>
                                <c:otherwise>${i.count}</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="underllned" style="text-align:left">${item.value.proizvod.proizvod}</td>
                        <td class="underllned">
                            <c:choose>
                                <c:when test="${loginUser.woUser.userType == 'INTERNI'}">
                                    <div class="multiline"
                                         style="width:208px;">${item.value.proizvod.nazivProizvoda} ${item.value.proizvod.dodatniNaziv}</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="multiline"
                                         style="width:208px;">${item.value.proizvod.nazivProizvoda} ${item.value.proizvod.dodatniNaziv}</div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="underllned">${item.value.kolPoPakovanju}</td>
                        <td class="underllned">${item.value.kolicina}</td>
                        <td class="underllned" width="40px">${item.value.proizvod.jedinicaMere.skracenaOznaka}</td>
                        <td class="underllned" style="text-align:left">${item.value.cena}</td>
                        <td class="underllned" style="text-align:left">${item.value.vrednost}</td>
                        <c:if test="${loginUser.woUser.userType == 'INTERNI'}">
                            <td class="underllned" style="text-align:left" class="not_link">${item.value.rabat}</td>
                            <td class="underllned" style="text-align:left" class="not_link"><input type="text" name="dodatniRabat"
                                                                                style="width:40px;border:solid 1px #4c4b4b;color: #4c4b4b;"/>
                            </td>
                        </c:if>
                        <td>
                            <img src="images/remove.png" alt=""
                                 onClick="location.href='removeFromBasket?productId=${item.key}'"/>

                            <div class="dialog" id="uvecaj_${item.value.proizvod.proizvod}"
                                 title="${item.value.proizvod.proizvod}  ${item.value.proizvod.nazivProizvoda} ${item.value.proizvod.dodatniNaziv}">
                                <img style="margin:0px;border:none;" width="700px" height="490px"
                                     src="/WO/images/large/${item.value.proizvod.proizvod}.jpg" alt=""/>
                            </div>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <table class="sastav" ${item.value.woRezervacijaSastavaList.size() == 0 ? "hidden" : ""} >
                                <th style="width: 70%">Sastav</th>
                                <th style="width: 15%">Koli�ina</th>
                                <th style="width: 15%">Cena</th>
                                <c:forEach items="${item.value.woRezervacijaSastavaList}" var="compositeItem"
                                           varStatus="i">
                                    <tr id="korpa_uvecaj_${compositeItem.proizvod.proizvod}" style="border-bottom: 1px solid #b3b3b3">
                                        <td style="text-align:left">
                                            <div class="multiline">${compositeItem.proizvod.proizvod} ${compositeItem.proizvod.nazivProizvoda} ${compositeItem.proizvod.dodatniNaziv}</div>
                                        </td>
                                        <td>${compositeItem.kolicina}</td>
                                        <td>${compositeItem.cena}</td>
                                        <div class="dialog" id="uvecaj_${compositeItem.proizvod.proizvod}"
                                             title="${compositeItem.proizvod.proizvod}  ${compositeItem.proizvod.nazivProizvoda} ${compositeItem.proizvod.dodatniNaziv}">
                                            <img style="margin:0px;border:none;" width="700px" height="490px"
                                                 src="/WO/images/large/${compositeItem.proizvod.proizvod}.jpg" alt=""/>
                                        </div>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
            </table>


            <div class="korpa_total">
                <hr color="#bbbbbb" class="thin"/>
                <div class="prevoz_placanje">
                    <input type="radio" name="nacinTransporta" value="1"/><label>Vozilo dobavlja�a</label><br>
                    <input type="radio" name="nacinTransporta" value="2"/><label>Vozilo kupca</label><br>
                    <input type="radio" name="nacinTransporta" value="5"/><label>Brza po�ta</label>
                </div>
                <div class="prevoz_placanje">
                    <input type="hidden" name="maxRokPlacanja" value="${loginUser.woPartnerSetting[0].maxRokPlacanja}"/>
                    <input type="radio" name="nacinPlacanja" value="CAS"/><label>Gotovina</label><br>
                    <input type="radio" name="nacinPlacanja"
                           value="ODL" ${ loginUser.woPartnerSetting[0].maxRokPlacanja == 0 ? "disabled":""} /><label>Odlo�eno</label><br>
                    <input type="radio" name="nacinPlacanja" value="AVA"/><label>Avans</label>
                </div>
                <div class="total">
                    <h1>Ukupna bruto vrednost bez PDV-a :</h1>

                    <h2>${loginUser.orderValue} &euro;</h2>

                    <h1>Ukupna bruto vrednost sa PDV-om:</h1>

                    <h2>${loginUser.orderValueWithTax} &euro;</h2>
                </div>
                <hr color="#bbbbbb" class="thin" style="clear:both;margin-bottom:5px;"/>
                <br></br>
            <c:choose>
                <c:when test="${loginUser.woUser.userType == 'INTERNI'}">
                    <!--[if lte IE 9]><div style="margin-left:10px;color:#4c4b4b">Napomena</div><![endif]-->
                    <textarea name="napomena" placeholder="Napomena" cols="30" rows="5" spellcheck="false"></textarea>
                </c:when>
                <c:otherwise>
                    <select name="adresaIsporuke" class="adresa_isporuke">
                        <option value="-1">Adresa Isporuke</option>
                        <c:forEach items="${loginUser.woUser.ocpPoslovniPartner.ocpAdresaIsporukes}" var="item">
                            <option value="${item.adresa}">${item.adresa}</option>
                        </c:forEach>
                    </select>
                    <br></br>
                    <!--[if lte IE 9]><div style="margin-left:10px;color:#4c4b4b">Adresa Isporuke</div><![endif]-->
                    <textarea name="adresa" placeholder="Adresa Isporuke" cols="30" rows="5"
                              spellcheck="false"></textarea>
                </c:otherwise>
            </c:choose>

            </div>
            <hr color="#bbbbbb" class="thin" style="margin-left:10px;margin-bottom: 10px;"/>

            <div style="float: left;margin-left:10px;"><input type="button" class="btn ponisti" value="PONI�TI"
                                                              onClick="location.href='removeBasket'"/></div>
            <div style="float: right;margin-left:10px;"><input type="button" id="chceckOutBasketBtn" class="btn potvrdi"
                                                               value="POTVRDI"/></div>
        </form>
    </c:if>

</div>
