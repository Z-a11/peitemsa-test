<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update</title>
</head>
<body>
	<form action="/peitemsa/test?m=xg" method="post">
		<c:forEach items="${DATA}" var="temp">
		<p style="display: none;">项目id:<input name="id"  type="text" value="${temp.itemid }"></p>
		<p style="">项目名称:<input name="name"  type="text" value="${temp.itemname }"></p>
				  <td>
				  项目类别:
				  <select name="type" value="${t.typeid}">
				  <c:forEach items="${type }" var="t">
				  <option value ="${t.typeid }">${t.typename }</option>
				  </c:forEach>
				  </select>
				  </td>
		<p>是否必查:
		<c:if test="${temp.necessary==1}">
		<input name="necessary" type="checkbox" checked="checked" value="1">
		</c:if>
		<c:if test="${temp.necessary==0}">
		<input name="necessary" type="checkbox" value="0">
		</c:if>
		</p>
		<p>参考值：
		<input name="ref"  type="text" value="${temp.ref }">
		</p>
		<p>价格：
		<input name="price"  type="text" value="${temp.price }">
		</p>
		<p>说明：
		<input name="info"  type="text" value="${temp.info }">
		</p>
		<p><input type="submit" value="提交"></p>
		</c:forEach>
	</form>
</body>
</html>