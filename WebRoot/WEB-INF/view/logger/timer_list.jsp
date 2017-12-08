<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<form id="pagerForm" action="timerlogger/list">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="20" />
	<input type="hidden" name="orderField" value="${orderField}" />
	<input type="hidden" name="orderDirection" value="${orderDirection}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="timerlogger/list" method="post">
	<input type="hidden" name="pageNum" value="1" />
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label></label>
				<select class="combox" name="logger_type">
				
				<c:choose>
					<c:when test="${logger_type == '0'}">
						<option value="0" selected="selected">通知</option>
						<option value="1">自定义消息</option>
					</c:when>
					<c:when test="${logger_type == '1'}">
						<option value="0" >通知</option>
						<option value="1" selected="selected">自定义消息</option>
					</c:when>
					<c:otherwise>
						<option value="0" selected="selected">通知</option>
						<option value="1" >自定义消息</option>
					</c:otherwise>
				</c:choose>
				</select>
			</li>
		</ul>
	
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a title="确实要取消这些定时任务吗?" target="selectedTodo" rel="time" postType="string" href="timerlogger/delete" class="delete"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="98%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="time" class="checkboxCtrl"></th>
				<th width="100"  >预定时间</th>
				<th width="100"  >内容</th>
				<th width="100" >接收者类型</th>
				<th width="100" >目标(ANDROID|IOS)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${loggers}" var="logger">
			<tr target="logger_ids" rel="${logger.id }">
				<td><input name="time" value="${logger.timer_time }" type="checkbox"></td>
				<td>${logger.timer_time }</td>
				<td>${logger.alert }</td>
				<td>
					<c:if test="${logger.recipient_type == '0'}">
						广播(所有人)
					</c:if>
					<c:if test="${logger.recipient_type == '1'}">
						设备标签(Tag)
					</c:if>
					<c:if test="${logger.recipient_type == '2'}">
						设备别名(Alias)
					</c:if>
					<c:if test="${logger.recipient_type == '3'}">
						Reg.ID
					</c:if>
				
				</td>
				<td>
					<c:if test="${logger.target_android }">
						Y|
					</c:if>
					<c:if test="${!logger.target_android }">
						N|
					</c:if>
					<c:if test="${logger.target_ios }">
						Y
					</c:if>
					<c:if test="${!logger.target_ios }">
						N
					</c:if>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	
	<div class="panelBar" >
		<div class="pages">
			<span>每页显示${pageSize}条，共${totalCount}条</span>
		</div>
		
		<div class="pagination"  targetType="navTab" totalCount="${totalCount}" numPerPage="20" pageNumShown="10" currentPage="${pageNum}"></div>

	</div>

</div>
