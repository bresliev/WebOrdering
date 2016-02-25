<%@page contentType="text/html" %>
<%@page pageEncoding="Windows-1250" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="G1">
    <c:choose>
        <c:when test="${oj == 30}"><a href="http://www.darexmax.rs" target="_blank"><img src="images/logo/logo_dm.png"
                                                                                         alt=""/></a></c:when>
        <c:when test="${oj == 40}"><a href="http://www.darexitison.rs" target="_blank"><img
                src="images/logo/logo_di.png" alt=""/></a></c:when>
        <c:otherwise><a href="http://www.darex.rs" target="_blank"><img src="images/logo/logo_d.png"
                                                                        alt=""/></a></c:otherwise>
    </c:choose>
    <div class="G3">
        <div class="G3_L light_gray_gradient">
            <ul>
                <li>
                    <a href='home' ${url == '/home' ? "class='selected'":""}>Naslovna</a>
                </li>
                <li>
                    <a href='contact' ${url == '/contact' ? "class='selected'":""}>Kontakt</a>
                </li>
                <li>
                    <a href='support' ${url == '/support' ? "class='selected'":""}>Podrška</a>
                </li>
                <li>
                    <a href='download' ${url == '/download' ? "class='selected'":""}>Download</a>
                </li>
            </ul>
        </div>
        <div class="G3_D">
            <c:choose>
                <c:when test="${loginUser.woUser.userType == 'INTERNI'}">
                    Welcome ${loginUser.woUser.nickname} [${loginUser.woUser.ocpPoslovniPartner.naziv}]
                </c:when>
                <c:otherwise>
                    Welcome ${loginUser.woUser.ocpPoslovniPartner.naziv}
                </c:otherwise>
            </c:choose>

            <input type="button" class="btn login" value="SingOut" onClick="location.href='logout'"
                   style="margin-right:5px;"/>
        </div>
    </div>
    <div class="G2">
        <ul>
            <li>
                <a href='#' class="G2_MENI" id="AKCIJA">Akcija</a>
            </li>
            <li>
                <a href='#' class="G2_MENI" id="RASPRODAJA">Rasprodaja</a>
            </li>
            <li>
                <a href='#' class="G2_MENI" id="NOVO">Novo</a>
            </li>
            <li>
                <a href='#' class="G2_MENI" id="NAJAKTUELNIJE">Najaktuelnije</a>
            </li>
            <li>
                <a href='http://opc.blum.com/?calledFromInternal=false&calledFromPortal=true' target="_blank" class="G2_MENI" id="BLUM">Blum
                    Konfigurator</a>
            </li>
            <c:if test="${oj == 199 || oj == 309}">
                <li>
                    <!--a href='#' class="G2_MENI_NEW" id="41">Oprema/Vozila</a-->
                    <a href="oprema" ${url == '/oprema' ? "class='selected'":""}>Oprema i Vozila</a>
                </li>
            </c:if>
        </ul>
        <c:if test="${loginUser.woUser.userType == 'INTERNI'}">
            <form id="changePartnerForm" action="changePartner" method="post">
                <input id="partnerId" name="partnerId" type="hidden" value=""/>
            </form>
            <div class="ui-widgetT" style="float:right;line-height:37px;"><input type="text" value="PARTNER"
                                                                                 id="partnerPretraga"
                                                                                 style="width:240px;margin-right:5px;"
                                                                                 onfocus="javascript: this.value = ''"
                                                                                 onblur="javascript: if (this.value=='') this.value = 'KOMITENT'"/>
            </div>
        </c:if>
    </div>
</div>
