<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: 傻逼
  Date: 2018/1/26
  Time: 21:32
  To change this template use File | Settings | File Templates.
    <%@ page contentType="text/html;charset=UTF-8" %>
    <%@ page contentType="application/msexcel;charset=utf-8" %>
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql"  prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page contentType="application/msexcel;charset=utf-8" %>

<html>
<head>
    <title>查看所有报名信息</title>

    ${pageContext.response.setHeader("Content-disposition","inline; filename=test1.xls")}
</head>
<body>

<c:set var="sql">
    select * from EnrollList
</c:set>
<c:set var="page" value="${pageContext.request.getParameter('page')>0?pageContext.request.getParameter('page'):1}"/>
<sql:setDataSource driver="com.microsoft.sqlserver.jdbc.SQLServerDriver" url="jdbc:sqlserver://localhost:1433;DatabaseName=Enroll" user="zheng" password="zws19970423" var="jdbc"/>

<sql:query var="list" dataSource="${jdbc}" sql="${sql}" startRow="${(page-1)*10}"/>

<c:set var="map" value="${list.getRows()}"/>
<table border="1">
    <tr>
        <td>昵称</td>
        <td>易班id</td>
        <td>手机号码</td>
        <td>第一志愿</td>
        <td>第二志愿</td>
        <td>理由</td>
        <td>兴趣</td>
        <td>文件</td>
    </tr>

    <c:forEach var="one" items="${map}">
        <tr>
            <td>${one.yb_usernick}</td>
            <td>${one.yb_userid}</td>
            <td>${one.phonenumber}</td>
            <td>${one.first}</td>
            <td>${one.second}</td>
            <td>${one.reason}</td>
            <td>${one.instest}</td>
            <td>${one.fileLocal}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
