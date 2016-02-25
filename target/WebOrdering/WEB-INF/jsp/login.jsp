<%@page contentType="text/html"%>
<%@page pageEncoding="Windows-1250"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="position:absolute;width:580px;top: 50%;left: 50%;margin-top:-150px;margin-left:-290px;text-align:center;font-size:13px;color:#4c4c4c;font-weight:600;">
    <img src="images/login.png" alt=""/>
    <form action="login" method="POST">
        <c:if test="${not empty errorMsg}">
           <p style="color:red;font-size:12px;margin: 2px">${errorMsg}</p>
        </c:if>
        <div style="background: url(images/login_g.png) no-repeat;height:67px;padding-top:11px;padding-left:11px">
            <div style="background: url(images/login_o.png) no-repeat;width:558px;line-height:46px;height:46px;">
                KORISNIK : <input type="text" name="userName"/>
                LOZINKA : <input type="password" name="password"/>
                <input type="submit" class="btn" value="Log In" style="color:#fefefe;background:#838383;"/>
            </div>
        </div>

        <table cellspacing=0 cellpadding=0 border=0 style="width:580px;margin:5px;">
            <tr>
                <td><img src="images/logo/logo_d_s.png" alt=""/></td>
                <td><img src="images/logo/logo_dm_s.png" alt="" style="margin-left: 40px;" /></td>
                <td><img src="images/logo/logo_dh_s.png" alt=""/></td>
                <!--td><img src="images/logo/logo_di_s.png" alt=""/></td-->
            </tr>
            <tr>
				<td><input type="radio" name="oj" value="19" ${param.oj == 19 ? "checked":""}></td>
                <td><input type="radio" name="oj" value="30" ${param.oj == 30 ? "checked":""} style="margin-left: 40px;"></td>
				<td><input type="radio" name="oj" value="20" ${param.oj == 20 ? "checked":""}></td>
				<!--td><input type="radio" name="oj" value="40" ${param.oj == 40 ? "checked":""}></td-->
            </tr>
        </table>
    </form>
</div>