<%@page contentType="text/html"%>
<%@page pageEncoding="Windows-1250"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div style="width:154px;float:left;">
    <div class="D1 dark_gray_gradient">
        <h3 class="dark_gray_gradient">MOJA KORPA</h3>
        <h4>Trenutna kupovina</h4>
        <h5>Odabrani broj artikala:</h5>
        <h4 id="broj_stavki">${fn:length(loginUser.basket)}</h4>
        <h5>Ukupna bruto vrednost sa PDV-om:</h5>
        <h4 id="ukupna_cena">${loginUser.orderValueWithTax} EUR</h4>
        <input type="submit" class="btn basket" style="margin: 5px;" value="Pregled Korpe" onClick="location.href='basket'"/>
    </div>
    <c:if test="${loginUser.woUser.userType == 'INTERNI' and not empty loginUser.woUser.ocpPoslovniPartner.intNapomena}">
      <div class="D1 dark_gray_gradient" style="margin-top:10px;min-height: 182px;">
          <h3 class="dark_gray_gradient">INTERNA NAPOMENA</h3>
          <h5>${loginUser.woUser.ocpPoslovniPartner.intNapomena}</h5>
      </div>
    </c:if>
    <c:if test="${not empty najprodavanije}">
    <div class="D1 najprodavanije">
        <h3 class="light_gray_gradient">NAJPRODAVANIJE</h3>
        <div id="div-najprodavanije">
        <c:forEach items="${najprodavanije}" var="item" varStatus="status">
            <c:if test="${status.index < 4}">
                <div id="najprodavanije-${item.proizvod}" class="fade najprod">
                    <img width="120px" height="84px" src="/WO/images/small/${item.proizvod}.jpg"  alt=""/>
                    <div class="naziv multiline">${item.nazivProizvoda} ${item.dodatniNaziv}</div>
                    <div class="cena">${item.cena} EUR</div>
                </div>
                <c:if test="${not status.last && status.index < 3}">
                    <hr class="thin" style="margin:10px;">
                </c:if>
            </c:if>
        </c:forEach>
        </div>
    </div>
    </c:if>
</div>