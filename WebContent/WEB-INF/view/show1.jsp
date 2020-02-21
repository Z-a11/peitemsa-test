<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视图1</title>
</head>
<body>
<div style="text-align: center;">
<h1>体验项目查询</h1>
<p><select name="type" value="">
     <c:forEach items="${type }" var="t">
  		<option value ="${t.typeid }">${t.typename }</option>
 	  </c:forEach>
		</select>
		<input type="button" value="查询" id="cx">
	</p>
	<table width="500" border="1" style="margin-left: 490px">
		<thead>
			<tr>
				<td>项目id</td>
				<td>项目名称</td>
				<td>项目类别</td>
				<td>是否必查</td>
				<td>参考值</td>
				<td>价格</td>
				<td>说明</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody class="tbody">
			<c:forEach items="${DATA }" var="temp">
			<tr>
				<td>${temp.itemid }</td>
				<td>${temp.itemname }</td>
				<c:forEach items="${type }" var="t">
				<c:if test="${temp.typeid==t.typeid }">
				  <td>${t.typename}</td>
				</c:if>
				</c:forEach>
				<c:if test="${temp.necessary==1 }">
				<td>是</td>
				</c:if>
				<c:if test="${temp.necessary==0 }">
				<td>否</td>
				</c:if>
				<td>${temp.ref }</td>
				<td>${temp.price }</td>
				<td>${temp.info }</td>
				<td><a href="/peitemsa/test?m=update&&id=${temp.itemid }">修改</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
     
	<script type="text/javascript"src="/peitemsa/js/jquery-1.8.3.js" ></script>
	<script type="text/javascript">
	  $(document).on("click","#cx",function(){
		  var id=$("[name='type']").val();
		  location.href="/peitemsa/test?m=cx&&typeid="+id;
		});
	</script>
</body>
</html>