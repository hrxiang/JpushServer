<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MB_Jpush</title>

<link
	href="<%=request.getContextPath()%>/themes/default/style.css"
	rel="stylesheet" type="text/css" media="screen" />
<link href="<%=request.getContextPath()%>/themes/css/core.css"
	rel="stylesheet" type="text/css" media="screen" />
<link href="<%=request.getContextPath()%>/themes/css/print.css"
	rel="stylesheet" type="text/css" media="print" />
<link
	href="<%=request.getContextPath()%>/uploadify/css/uploadify.css"
	rel="stylesheet" type="text/css" media="screen" />
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="js/speedup.js" type="text/javascript"></script>
<![endif]-->

<script src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.cookie.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.bgiframe.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/uploadify/scripts/jquery.uploadify.js"
	type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/js/dwz.core.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.util.date.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/js/dwz.validate.method.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.regional.zh.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.barDrag.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.drag.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.tree.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.accordion.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.ui.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.theme.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.switchEnv.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.alertMsg.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.contextmenu.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.navTab.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.tab.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.resize.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.dialog.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.dialogDrag.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.sortDrag.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.cssTable.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.stable.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.taskBar.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.ajax.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.pagination.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.database.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.datepicker.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.effects.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.panel.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.checkbox.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.history.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.combox.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/dwz.print.js"
	type="text/javascript"></script>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="<%=request.getContextPath()%>/js/dwz.regional.zh.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		DWZ.init("dwz.frag.xml", {
			loginUrl : "login_dialog.html",
			loginTitle : "登录", // 弹出登录对话框
			//		loginUrl:"login.html",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			}, //【可选】
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "themes"
				}); // themeBase 相对于index页面的主题base路径
			}
		});
	});
	
	$(document).ready(function(){
  		// 在这里写你的代码...
  		/* navTab.openTab('notification', 'push/notification', {title:'我的任务', fresh:true, data:{}}); */
	});
</script>
<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="javascript:;">标志</a> 
				<ul class="nav">
					<li><a href="javascript:;">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<!--<li theme="red"><div>红色</div></li>-->
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>

			<!-- navMenu -->

		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>邦购推送平台</h2>
					<div>收缩</div>
				</div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionContent">
						<ul class="tree treeFolder" >
							<li><a href="push/notification" target="navTab" rel="notification" >发送通知</a>
								<li><a href="push/message" target="navTab" rel="message">自定义消息</a>
							</li>
								<li><a href="logger/list" target="navTab"
									rel="loggerList">推送历史</a></li></li>
								<li><a href="timerlogger/list" target="navTab"
									rel="timerloggerList">定时消息</a></li></li>
								<!-- <li><a href="tag/list" target="navTab"
									rel="tagList">tag</a></li></li> -->
								<!-- <li><a href="http://www.baidu.com" target="navTab"
									rel="page1">alias</a></li></li> -->

						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
						</ul>
					</div>
				</div>
				<div class="navTab-panel tabsPageContent layoutBox">
				</div>
			</div>
		</div>

	</div>

	<div id="footer">
		Copyright &copy; 2014 <a href="javascript:;" >metersbonwe</a>
	</div>

</body>
</html>
