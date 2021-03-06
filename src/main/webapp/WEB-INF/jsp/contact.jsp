<%@page contentType="text/html" %>
<%@page pageEncoding="Windows-1250" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content">
    <c:choose>
        <c:when test="${not empty flashSize}">
            <object width="716px" height="${flashSize}" style="margin:0;padding:0;margin-left:10px;"
                    classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                    codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0">
                <param name="SRC" value="/WO/flash/${oj}/kontakt.swf"/>
                <param name="scale" value="exactfit"/>
                <param name="wmode" value="transparent"/>
                <embed src="/WO/flash/${oj}/kontakt.swf" width="716px" height="${flashSize}" wmode="transparent"/>
            </object>
        </c:when>
        <c:otherwise>
            <div style="float:left;">
                <div class="navigation_bar_left dark_gray_gradient" style="width:622px;">
                    <h3>KONTAKT</h3>
                </div>
                <div class="navigation_bar_right" style="width:64px;"><img src="images/strelice.png" alt=""/></div>
            </div>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${oj == 30}">
            <table style="float:left;width:716px;color:#58595b;margin-left:10px;font-size:12px;line-height:16px;">
                <tr>
                    <td colspan="3"
                        style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Regionalni
                        menad�er
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <b>Boris Tomi�</b><br>
                        tel: +382 (0)20 225 289<br>
                        mob: +382 (0)68 044 554<br>
                        e-mail: <b><a style="color:#58595b;" href="mailto:boris.tomic@darexmax.me">boris.tomic@darexmax.me</a></b><br><br>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"
                        style="font-size:16px;color:#faa635;font-weight: 600;padding-top:25px;padding-bottom: 10px;">
                        Prodaja
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Radmila Azanjac</b><br>
                        tel: +381 (0)11 20 91 622<br>
                        fax: +381 (0)11 20 91 699<br>
                        mob: +381 (0)65 31 95 300<br>
                        e-mail: <b><a style="color:#58595b;" href="mailto:rada.azanjac@darex.rs">rada.azanjac@darex.rs&nbsp;</a></b>
                    </td>
                    <td>
                        <b>Vera Maslakovi�</b><br>
                        tel: +381 (0)11 20 91 628<br>
                        fax: +381 (0)11 20 91 699<br>
                        mob: +381 (0)65 31 95 301<br>
                        e-mail: <b><a style="color:#58595b;" href="mailto:vera.maslakovic@darex.rs">vera.maslakovic@darex.rs</a></b>
                    </td>
                    <td>
                        <b>An�ela Kremenovi�</b><br>
                        tel: +381 (0)11 20 91 621<br>
                        fax: +381 (0)11 20 91 699<br>
                        mob: +381 (0)65 31 95 240<br>
                        e-mail: <b><a style="color:#58595b;" href="mailto:andjela.kremenovic@darex.rs">andjela.kremenovic@darex.rs</a></b>
                    </td>

                </tr>
                <tr>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Direktor prodaje
                    </td>
                    <td></td>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Izvr�ni direktor
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Bojan Peri�</b><br>
                        mob: +381 (0)65 31 95 278<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:dusko.curcic@darex.rs">bojan.peric@darex.rs</a></b>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        �ef
                        distributivnog centra
                    </td>
                    <td></td>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Izvr�ni direktor
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Du�ko Curci�</b><br>
                        tel: +381 (0)11 20 91 650<br>
                        mob: +381 (0)65 31 95 255<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:dusko.curcic@darex.rs">dusko.curcic@darex.rs</a></b>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Darexmax d.o.o. Podgorica
                    </td>
                    <td></td>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Distributivni Centar Ivanjica
                    </td>
                </tr>
                <tr>
                    <td>
                        Vojvode Ilije Plamenca bb, Zabjelo<br>
                        81000 Podgorica<br>
                        tel: +382 (0)20 225 289<br>
                        fax: +382 (0)20 225 289<br>
                        e-mail: <b><a style="color:#58595b;" href="mailto:distribucija@darexmax.me">distribucija@darexmax.me</a></b>
                    </td>
                    <td></td>
                    <td>
                        Bukovica bb<br>
                        32250 Ivanjica<br>
                        tel: +381 (0)32 642 372<br>
                        fax: +381 (0)11 20 91 699<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:distribucija@darex.rs">distribucija@darex.rs</a></b>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td>
                        <b>Radno vreme :</b><br>
                        pon - pet 8:30 - 16:30 h<br>
                        sub 9:00 - 17:00 h<br>
                        ned neradni dan
                    </td>
                </tr>
                <tr>
                    <td colspan="3" style="text-align: center;padding-top: 50px;padding-bottom: 25px;">
                        Sve primedbe i sugestije poslati na e-mail: <b><a style="color:#58595b;"
                                                                          href="mailto:primedbe.sugestije@darexmax.me">primedbe.sugestije@darexmax.me</a></b><br>
                        Reklamacije mo�ete poslati na e-mail: <b><a style="color:#58595b;"
                                                                    href="mailto:reklamacije@darexmax.me">reklamacije@darexmax.me</a></b><br>
                    </td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <table style="float:left;width:716px;color:#58595b;margin-left:10px;font-size:12px;line-height:16px;">
                <tr>
                    <td colspan="3"
                        style="font-size:16px;color:#faa635;font-weight: 600;padding-top:25px;padding-bottom: 10px;">
                        Prodaja
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Radmila Azanjac</b><br>
                        tel: 011 20 91 622<br>
                        fax: 011 20 91 699<br>
                        mob: 065 31 95 300<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:rada.azanjac@darex.rs">rada.azanjac@darex.rs</a></b>
                    </td>
                    <td>
                        <b>Vera Maslakovi�</b><br>
                        tel: 011 20 91 628<br>
                        fax: 011 20 91 699<br>
                        mob: 065 31 95 301<br>
                        e-mail: <b><a style="color:#58595b;" href="mailto:vera.maslakovic@darex.rs">vera.maslakovic@darex.rs</a></b>
                    </td>
                    <td>
                        <b>An�ela Kremenovi�</b><br>
                        tel: 011 20 91 621<br>
                        fax: 011 20 91 699<br>
                        mob: 065 31 95 240<br>
                        e-mail: <b><a style="color:#58595b;" href="mailto:andjela.kremenovic@darex.rs">andjela.kremenovic@darex.rs</a></b>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"
                        style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Regionalni
                        menad�eri prodaje - plo�e
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Radi�a Vatazovi�</b><br>
                        mob: 065 3195 284<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:radisa.vatazovic@darex.rs">radisa.vatazovic@darex.rs</a></b><br><br>
                    </td>
                    <td>
                        <b>Aleksandar Petkovi�</b><br>
                        mob: 065 3195 270<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:aleksandar.petkovic@darex.rs">aleksandar.petkovic@darex.rs</a></b><br><br>
                    </td>
                    <td>
                        <b>Nikola Gavrilovi�</b><br>
                        mob: 065 3195 254<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:nikola.gavrilovic@darex.rs">nikola.gavrilovic@darex.rs</a></b><br><br>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Marjan Jovi�</b><br>
                        mob: 065 3195 669<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:marjan.jovic@darex.rs">marjan.jovic@darex.rs</a></b><br><br><br>
                    </td>
                    <td>
                        <b>Vladimir Kotarac</b><br>
                        mob: 065 3195 243<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:vladimir.kotarac@darex.rs">vladimir.kotarac@darex.rs</a></b><br><br>
                    </td>
                    <td>
                        <b>Nikola Krsti�</b><br>
                        mob: 065 3195 242<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:nikola.krstic@darex.rs">nikola.krstic@darex.rs</a></b><br><br>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"
                        style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Regionalni
                        menad�eri prodaje - okov
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Nemanja Ra�kovi�</b><br>
                        mob: 065 3195 244<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:nemanja.raskovic@darex.rs">nemanja.raskovic@darex.rs</a></b><br><br>
                    </td>
                    <td>
                        <b>Marko Todorovi�</b><br>
                        mob: 065 3195 283<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:marko.todorovic@darex.rs">marko.todorovic@darex.rs</a></b><br><br>
                    </td>
                    <td>
                        <b>Sla�ana Beci�</b><br>
                        mob: 065 3195 302<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:sladjana.becic@darex.rs">sladjana.becic@darex.rs</a></b><br><br>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Bo�ko Raci�</b><br>
                        mob: 065 3195 668<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:bosko.racic@darex.rs">bosko.racic@darex.rs</a></b><br><br>
                    </td>
                    <td>
                    </td>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Direktor prodaje okov:
                    </td>
                    <td></td>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;"><!--Direktor
                prodaje-->
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>�eljko Rankovi�</b><br>
                        mob: 065 3195 298<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:zeljko.rankovic@darex.rs">zeljko.rankovic@darex.rs</a></b>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        �ef
                        distributivnih centara
                    </td>
                    <td></td>
                    <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;"><!--Direktor
                prodaje-->
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Du�ko �ur�i�</b><br>
                        tel: 011 20 91 650<br>
                        mob: 065 31 95 255<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:dusko.curcic@darex.rs">dusko.curcic@darex.rs</a></b>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="3"
                        style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">
                        Distributivni Centri
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Distributivni Centar Novi Beograd</b><br>
                        Autoput za Zagreb 29<br>
                        11077 Beograd<br>
                        tel: 011 20 91 620<br>
                        fax: 011 20 91 699<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:distribucija@darex.rs">distribucija@darex.rs</a></b>
                    </td>
                    <td>
                        <b>Distributivni Centar Ni�</b><br>
                        Bulevar Cara Konstantina 80 - 84<br>
                        18000 Ni�<br>
                        tel: 018 208 100<br>
                        fax: 011 20 91 699<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:distribucija@darex.rs">distribucija@darex.rs</a></b><br>
                    </td>
                    <td>
                        <b> Distributivni Centar Ivanjica</b><br>
                        Bukovica bb<br>
                        32250 Ivanjica<br>
                        tel: 032 642 372<br>
                        fax: 011 20 91 699<br>
                        e-mail: <b><a style="color:#58595b;"
                                      href="mailto:distribucija@darex.rs">distribucija@darex.rs</a></b>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Radno vreme :</b><br>
                        pon - pet 8:30 - 16:30 h<br>
                        sub 9:00 - 17:00 h<br>
                        ned neradni dan
                    </td>
                    <td>
                        <b>Radno vreme :</b><br>
                        pon - pet 8:30 - 16:30 h<br>
                        sub 9:00 - 17:00 h<br>
                        ned neradni dan
                    </td>
                    <td>
                        <b>Radno vreme :</b><br>
                        pon - pet 8:30 - 16:30 h<br>
                        sub 9:00 - 17:00 h<br>
                        ned neradni dan
                    </td>
                </tr>
                <tr>
                    <td colspan="3" style="text-align: center;padding-top: 50px;padding-bottom: 25px;">
                        Sve primedbe i sugestije poslati na e-mail: <b><a style="color:#58595b;"
                                                                          href="mailto:primedbe.sugestije@darex.rs">primedbe.sugestije@darex.rs</a></b><br>
                        Reklamacije mo�ete poslati na e-mail: <b><a style="color:#58595b;"
                                                                    href="mailto:reklamacije@darex.rs">reklamacije@darex.rs</a></b><br>
                    </td>
                </tr>
            </table>
        </c:otherwise>
    </c:choose>


</div>
