<div class="pageContent">
	<form method="post" action="push/notification" 
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone_1);">
		<div class="pageFormContent" layoutH="55">
			<fieldset>
				<legend>推送内容</legend>
				<dl class="nowrap">
					<dt style="color: red;">不能超过72个汉字</dt>
					<dd>
						<textarea name="alert" class="required" maxlength="72" onmousedown="s(event,this)"
							style="width: 600px; height: 50px">
			</textarea>
					</dd>
				</dl>
			</fieldset>
			<fieldset>
				<legend>推送对象</legend>
				<label><input type="checkbox" name="aps" value="0" />IOS开发环境</label>
				<label><input type="checkbox" name="aps" value="1" />IOS生产环境</label>
				<label><input type="checkbox" name="aps" value="2"
					checked="checked" />Android</label> <label style="display: none"><input type="checkbox"
					name="aps" value="3" />WinpHone</label>
				<div class="divider"></div>
				<dl class="nowrap">
					<dd>
						<label><input type="radio" name="recipient_type"
							checked="checked" value="0"/>广播(所有人)</label> <label><input type="radio"
							name="recipient_type" value="1"/>设备标签(Tag)</label> <label><input
							type="radio" name="recipient_type" value="2"/>设备别名(Alias)</label> <label><input
							type="radio" name="recipient_type" value="3"/>Registration ID</label>
					</dd>
				</dl>
				
				<dl class="nowrap" style="display:none;" id="tags_block">
					<dd>
						<input name="device_tags.id" value="" type="hidden">
						<input name="device_tags.name" type="text" placeholder="输入设备标签" style="width: 600px;" />
						<a class="btnLook" href="vtag/list" lookupGroup="device_tags">有效标签</a>
						<span class="info">(lookup)</span>
					</dd>
				</dl>
				
				<dl class="nowrap" style="display:none" id="alias_block">
					<dd>
					 <input style="width: 600px" name="device_aliases" placeholder="输入设备别名"  type="text"  />
					</dd>
				</dl>
				
				<dl class="nowrap" style="display:none" id="registrationid_block">
					<dd>
						 <input style="width: 600px" name="device_registrationids" placeholder="输入Registration ID"  type="text"  />
					</dd>
				</dl>
			</fieldset>
			<fieldset>
				<legend>发送时间</legend>
				<label><input type="radio" name="delivery_type"
					checked="checked" value="0"/>立即</label> <label><input type="radio"
					name="delivery_type" value="1"/>定时</label> <label style="display:none"><input type="radio" 
					name="delivery_type" value="2"/> 定速推送</label>
				<div class="divider"></div>
				
				<dl class="nowrap" style="display:none" id="date_select">
					<dd>
						<input type="text" name="delivery_date" class="date"
							dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" /> <a
							class="inputDateButton" href="javascript:;">选择</a> <span
							class="info">yyyy-MM-dd HH:mm:ss</span>
					</dd>
				</dl>
				
				<div class="divider"></div>
				<dl class="nowrap" style="display:none" id="duration_time">
					<dd>
					<label>推送将分布在 </label> <input name="duration_time" type="text" placeholder="不能超过1440分钟"/><label>分钟内完成</label>
					</dd>
				</dl>
				
			</fieldset>



			<div class="panel close collapse" defH="150">
				<h1>可选设置</h1>
				<div>
					<fieldset>
						<label>离线消息保留时长</label> <select class="combox" name="time_to_live">
							<option value="86400" selected="selected">默认(1天)</option>
							<option value="0">0 - 不保留</option>
							<option value="60">1 分钟</option>
							<option value="600">10 分钟</option>
							<option value="3600">1 小时</option>
							<option value="10800">3 小时</option>
							<option value="43200">12 小时</option>
							<option value="259200">3 天</option>
							<option value="864000">10 天</option>
						</select>
					</fieldset>
				</div>
			</div>
		</div>

		<div class="formBar" align="center">
			<ul>
				<li><button type="submit">发送</button>
				</li>
			</ul>
		</div>
	</form>
</div>
 <script type="text/javascript">
 		$(function () {
	 		$("[name = delivery_type]:radio").bind("click", function () {
	 		 	var v =  $("input[name='delivery_type']:checked").val();
	 		 	if(v == 0){//立即
	 		 		$("#date_select").hide();
	 		 		$("#duration_time").hide();
	 		 	}else if(v == 1){//定时
	 		 		$("#date_select").show();
	 		 		$("#duration_time").hide();
	 		 	}else if(v == 2){//定速推送
	 		 		$("#date_select").hide();
	 		 		$("#duration_time").show();
	 		 	}
	 		});
            // chkItem事件
            $("[name = recipient_type]:radio").bind("click", function () {
               var v =  $("input[name='recipient_type']:checked").val();
               if(v == 0){//所有人
               		$("#tags_block").hide();
               		$("#alias_block").hide();
               		$("#registrationid_block").hide();
               		$("input[name='device_tags.name']").removeClass();
               		$("input[name='device_aliases']").removeClass();
               		$("input[name='device_registrationids']").removeClass();
               }else if(v == 1){// 设备标签(Tag)
               		$("#tags_block").show();
               		$("#alias_block").hide();
               		$("#registrationid_block").hide();
               		
               		$("input[name='device_tags.name']").addClass("required");
               		$("input[name='device_aliases']").removeClass();
               		$("input[name='device_registrationids']").removeClass();
               }else if(v == 2){// 设备别名(Alias)
               		$("#tags_block").hide();
               		$("#alias_block").show();
               		$("#registrationid_block").hide();
               		
               		$("input[name='device_tags.name']").removeClass();
               		$("input[name='device_aliases']").addClass("required");
               		$("input[name='device_registrationids']").removeClass();
               }else if(v == 3){// Registration ID
               		$("#tags_block").hide();
               		$("#alias_block").hide();
               		$("#registrationid_block").show();
               		
               		$("input[name='device_tags.name']").removeClass();
               		$("input[name='device_aliases']").removeClass();
               		$("input[name='device_registrationids']").addClass("required");
               }
            });
        });
        
        function s(e,a){
			 if ( e && e.preventDefault )
		            e.preventDefault();
			else 
			window.event.returnValue=false;
				a.focus();
		
		}
		
		function navTabAjaxDone_1(json){
		   /* alert(JSON.stringify(json)); */  
	      DWZ.ajaxDone(json);
	      if (json.statusCode == DWZ.statusCode.ok){
	            /* if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
	                  
	            } else { //重新载入当前navTab页面
	                  navTabPageBreak();
	            } */
	            if(json.params != null){
	            	var array = json.params.split(',');
	            	if(array.length == 4){
	            		navTab.openTab(array[0], array[1], {title:array[2], fresh:true, data:{'logger_type':array[3]}});
	            	}
				 /* navTab.openTab('loggerList', 'logger/list', {title:'推送历史', fresh:true, data:{'logger_type':'0'}}); */
	            }
	            if ("closeCurrent" == json.callbackType) {
	                  setTimeout(function(){navTab.closeCurrentTab();}, 100);
	            } else if ("forward" == json.callbackType) {
	                  navTab.reload(json.forwardUrl);
	            }
	      }
		}
		
 </script>