<%--
  Created by IntelliJ IDEA.
  User: 傻逼
  Date: 2018/1/25
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>肇院易班迎新报名系统</title>
    <script>
        function showFileInput(){
            var fileInput=document.getElementById("fileInput");
            fileInput.click();
        }
        var time = 0;
        function addLine(addButton){
            //创建内部div对象
            var divElement = document.createElement("div");
            //创建input对象[file类型]
            var inputElement1 = document.createElement("input");
            inputElement1.type="file";
            inputElement1.name="upfile";
            //创建input对象[button类型]
            var inputElement2 = document.createElement("input");
            inputElement2.type="button";
            inputElement2.value="删除";
            //对删除按钮添加事件监听
            inputElement2.onclick=function(){
                //取得该按钮所在行的直接父元素
                var divElement = this.parentNode.parentNode;
                //通过父元素删除直接子元素
                divElement.removeChild(this.parentNode);
                time--;
                if(time < 5){
                    //按钮生效
                    addButton.disabled=false;
                    //addButton.style.visibility="visible";
                }
            }
            //依次将file类型和button类型的input对象加入到内部div对象中
            divElement.appendChild(inputElement1);
            divElement.appendChild(inputElement2);
            //再次内部div对象加入到外部div对象
            var outDivElement = document.getElementById("outDiv");
            outDivElement.appendChild(divElement);
            time++;
            if(time == 5){
                //将按钮失效
                addButton.disabled=true;
                //addButton.style.visibility="hidden";
            }
        }
        <%--
        function HttpSimple() {
            <c:set var="access_token" value="${requestScope.access_token}"/>
            <c:set var="to_yb_uid" value="10967292"/>
            <c:set var="content" value="测试添加好友接口"/>
            ${(sessionScope.yibanAPI.Addfriend(access_token,to_yb_uid,content))}
        }
        --%>
    </script>
</head>
<body>
易班欢迎您！${pageContext.session.getAttribute("yb_usernick")}
<img src="${pageContext.session.getAttribute("yb_userhead")}" alt="${pageContext.session.getAttribute("yb_usernick")}" style="height: 30px;width: 30px"/><br/>
<form action="Add" method="post" enctype="multipart/form-data" >
    电话号码：<input type="text" name="phonenumber" style="border-top: hidden;border-left: hidden;border-right: hidden;"/><br/>
    第一志愿：<select name="first">
                <OPTION>办公室</OPTION>
                <OPTION>新闻部</OPTION>
                <OPTION>策划部</OPTION>
                <OPTION>运营部</OPTION>
                <OPTION>技术部</OPTION>
              </select>
    <br/>
    第二志愿：<select name="second">
                <OPTION>办公室</OPTION>
                <OPTION>新闻部</OPTION>
                <OPTION>策划部</OPTION>
                <OPTION>运营部</OPTION>
                <OPTION>技术部</OPTION>
            </select>
    <br/>
    意愿理由:<textarea style="vertical-align: middle;" name="reason" rows="3" cols="20"></textarea>
    <br/>
    兴趣爱好:<textarea  name="instest" rows="3" cols="20" style="vertical-align: middle;"></textarea>
    <br/>
    <table>
        <td>
            <div id="outDiv"></div>
        </td>
        <td>
            <input type="button" value="添加上传文件" onclick="addLine(this)"/>
        </td>
    </table>
    <br/>
    <input type="submit" value="报名">
    <br/><br/>
    推荐好友：<c:forEach var="i" begin="0" end="${sessionScope.friendList.size()-1}">
    <%--${sessionScope.friendList.getJSONObject(i).get("yb_userid")}
    <c:set var="token" value="${sessionScope.access_token}"/>
    ${pageContext.request.setAttribute(access_token,token)}
    <c:set var="id" value="10967292"/>
    <c:set var="con" value="测试添加好友接口"/>
    --%>
    <a href = "#" onclick="HttpSimple()">
            ${sessionScope.friendList.getJSONObject(i).get("yb_usernick")}&nbsp;
    </a>
    </c:forEach>
    <br/><br/>
    资讯推送：<br/>
    <c:forEach var="i" begin="0" end="${sessionScope.newsList.size()-1}">
        <a href = "${sessionScope.newsList.getJSONObject(i).get("push_href")}">
                ${sessionScope.newsList.getJSONObject(i).get("push_title")}<br/>
        </a>
    </c:forEach>
</form>

</body>
</html>
