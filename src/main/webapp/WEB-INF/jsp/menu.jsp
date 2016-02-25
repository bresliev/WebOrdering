<%@page pageEncoding="Windows-1250"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="nivo" value="-1"/>


<div style="width:210px;float:left;">
    <div class="side_menu">
            <h3 class="light_gray_gradient">PROIZVODI</h3>
        <c:forEach items="${CompanySetting.meni[oj]}" var="item" varStatus="i">
            <c:choose>
                <c:when test="${item.nivo == '1' && item.nivo > nivo}">
                    <ul>
                        <li id="${item.id.klasifikacija}">
                            <a href="#">${item.naziv}</a>
                </c:when>
                <c:when test="${item.nivo > '1' && item.nivo > nivo}">
                    <ul style="display:none;">
                        <li id="${item.id.klasifikacija}">
                            <a href="#">${item.naziv}</a>
                </c:when>

                <c:when test="${item.nivo == nivo}">
                        </li>
                        <li id="${item.id.klasifikacija}">
                            <a href="#">${item.naziv}</a>
                </c:when>
                <c:when test="${item.nivo == '1' && nivo == '2'}">
                          </li>
                       </ul>
                    </li>
                    <li id="${item.id.klasifikacija}">
                            <a href="#">${item.naziv}</a>
                </c:when>
                <c:when test="${item.nivo == '1' && nivo == '3'}">
                             </li>
                           </ul>
                         </li>
                       </ul>
                    </li>
                    <li id="${item.id.klasifikacija}">
                            <a href="#">${item.naziv}</a>
                </c:when>
                <c:when test="${item.nivo > '1' && item.nivo < nivo}">
                        </li>
                    </ul>
                    </li>
                        <li id="${item.id.klasifikacija}">
                            <a href="#">${item.naziv}</a>
                </c:when>
            </c:choose>
            <c:set var="nivo" value="${item.nivo}"/>
        </c:forEach>
              </li>
        </ul>

    </div>
</div>




