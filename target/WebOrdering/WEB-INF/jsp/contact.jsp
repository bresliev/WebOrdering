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
                style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">Regionalni
                menadžer
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <b>Boris Tomiæ</b><br>
                tel: +382 (0)20 225 289<br>
                mob: +382 (0)68 044 554<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:boris.tomic@darexmax.me">boris.tomic@darexmax.me</a></b><br><br>
            </td>
        </tr>
        <tr>
            <td colspan="3"
                style="font-size:16px;color:#faa635;font-weight: 600;padding-top:25px;padding-bottom: 10px;">Prodaja
            </td>
        </tr>
        <tr>
            <td>
                <b>Ivana Daboviæ</b><br>
                tel: +381 (0)11 20 91 621<br>
                fax: +381 (0)11 20 91 699<br>
                mob: +381 (0)65 31 95 240<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:ivana.dabovic@darex.rs">ivana.dabovic@darex.rs</a></b>
            </td>
            <td>
                <b>Radmila Azanjac</b><br>
                tel: +381 (0)11 20 91 622<br>
                fax: +381 (0)11 20 91 699<br>
                mob: +381 (0)65 31 95 300<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:rada.azanjac@darex.rs">rada.azanjac@darex.rs&nbsp;</a></b>
            </td>
            <td>
                <b>Vera Maslakoviæ</b><br>
                tel: +381 (0)11 20 91 628<br>
                fax: +381 (0)11 20 91 699<br>
                mob: +381 (0)65 31 95 301<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:vera.maslakovic@darex.rs">vera.maslakovic@darex.rs</a></b>
            </td>
        </tr>
        <tr>
            <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">Šef
                distributivnog centra
            </td>
            <td></td>
            <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">Izvršni direktor
            </td>
        </tr>
        <tr>
            <td>
                <b>Duško Curciæ</b><br>
                tel: +381 (0)11 20 91 650<br>
                mob: +381 (0)65 31 95 255<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:dusko.curcic@darex.rs">dusko.curcic@darex.rs</a></b>
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
                e-mail: <b><a style="color:#58595b;" href="mailto:distribucija@darex.rs">distribucija@darex.rs</a></b>
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
                Reklamacije možete poslati na e-mail: <b><a style="color:#58595b;" href="mailto:reklamacije@darexmax.me">reklamacije@darexmax.me</a></b><br>
            </td>
        </tr>
    </table>
</c:when>
<c:otherwise>
    <table style="float:left;width:716px;color:#58595b;margin-left:10px;font-size:12px;line-height:16px;">
        <tr>
            <td colspan="3"
                style="font-size:16px;color:#faa635;font-weight: 600;padding-top:25px;padding-bottom: 10px;">Prodaja
            </td>
        </tr>
        <tr>
            <td>
                <b>Ivana Daboviæ</b><br>
                tel: 011 20 91 621<br>
                fax: 011 20 91 699<br>
                mob: 065 31 95 240<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:ivana.dabovic@darex.rs">ivana.dabovic@darex.rs</a></b>
            </td>
            <td>
                <b>Radmila Azanjac</b><br>
                tel: 011 20 91 622<br>
                fax: 011 20 91 699<br>
                mob: 065 31 95 300<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:rada.azanjac@darex.rs">rada.azanjac@darex.rs</a></b>
            </td>
            <td>
                <b>Vera Maslakoviæ</b><br>
                tel: 011 20 91 628<br>
                fax: 011 20 91 699<br>
                mob: 065 31 95 301<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:vera.maslakovic@darex.rs">vera.maslakovic@darex.rs</a></b>
            </td>
        </tr>
        <tr>
            <td colspan="3"
                style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">Regionalni
                menadžeri
            </td>
        </tr>
        <tr>
            <td>
                <b>Bojan Periæ</b><br>
                mob: 065 31 95 278<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:bojan.peric@darex.rs">bojan.peric@darex.rs</a></b><br><br>

                <b>Radiša Vatazoviæ</b><br>
                mob: 065 31 95 284<br>
                e-mail: <b><a style="color:#58595b;"
                              href="mailto:radisa.vatazovic@darex.rs">radisa.vatazovic@darex.rs</a></b>
            </td>
            <td>
                <b>Miodrag Tasiæ</b><br>
                mob: 065 31 95 242<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:miodrag.tasic@darex.rs">miodrag.tasic@darex.rs</a></b><br><br>

                <b>Slaviša Petroviæ</b><br>
                mob: 065 31 95 244<br>
                e-mail: <b><a style="color:#58595b;"
                              href="mailto:slavisa.petrovic@darex.rs">slavisa.petrovic@darex.rs</a></b>
            </td>
            <td>
                <b>Nenad Avramoviæ</b><br>
                mob: 065 31 95 254<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:nenad.avramovic@darex.rs">nenad.avramovic@darex.rs</a></b><br><br>

                <b>Vladimir Beševiæ</b><br>
                mob: 065 31 95 243<br>
                e-mail: <b><a style="color:#58595b;"
                              href="mailto:vladimir.besevic@darex.rs">vladimir.besevic@darex.rs</a></b>
            </td>
        </tr>
        <tr>
            <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;">Šef
                distributivnih centara
            </td>
            <td></td>
            <td style="font-size:16px;color:#faa635;font-weight: 600;padding-top: 25px;padding-bottom: 10px;"><!--Direktor
                prodaje-->
            </td>
        </tr>
        <tr>
            <td>
                <b>Duško Curciæ</b><br>
                tel: 011 20 91 650<br>
                mob: 065 31 95 255<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:dusko.curcic@darex.rs">dusko.curcic@darex.rs</a></b>
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
                Autoput Beograd - Zagreb bb<br>
                11077 Beograd<br>
                tel: 011 20 91 620<br>
                fax: 011 20 91 699<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:distribucija@darex.rs">distribucija@darex.rs</a></b>
            </td>
            <td>
                <b>Distributivni Centar Niš</b><br>
                Bulevar Cara Konstantina 80 - 84<br>
                18000 Niš<br>
                tel: 018 207 352,018 207 353<br>
                fax: 011 20 91 699<br>
                e-mail: <b><a style="color:#58595b;"
                              href="mailto:distribucija@darex.rs">distribucija@darex.rs</a></b><br>
            </td>
            <td>
                <b>Distributivni Centar Ivanjica</b><br>
                Bukovica bb<br>
                32250 Ivanjica<br>
                tel: 032 642 372<br>
                fax: 011 20 91 699<br>
                e-mail: <b><a style="color:#58595b;" href="mailto:distribucija@darex.rs">distribucija@darex.rs</a></b>
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
                Reklamacije možete poslati na e-mail: <b><a style="color:#58595b;" href="mailto:reklamacije@darex.rs">reklamacije@darex.rs</a></b><br>
            </td>
        </tr>
    </table>
</c:otherwise>
</c:choose>


</div>
