<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<form id="pagerForm" action="vtag/list">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="20" />
</form>

<div class="pageHeader">
	<div class="subBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" multLookup="tags" warn="至少选择一个标签">选择带回</button></div></div></li>
		</ul>
	</div>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="tags" /></th>
				<th >标签</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tags}" var="tag">
			<tr>
				<td><input type="checkbox" name="tags" value="{name:'${tag}'}"/></td>
				<td>${tag}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>每页</span>
			<span>${numPerPage}条，共${totalCount}条</span>
		</div>
		<div class="pagination"  targetType="dialog" totalCount="${totalCount}" numPerPage="20" pageNumShown="10" currentPage="${pageNum}"></div>
	</div>
	
</div>
