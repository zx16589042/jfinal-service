<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<title>接口详情</title>
		<meta name="author" content="yueyouqian"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Content-Type" content="text/html">
		<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<style type="text/css">
			table {border-collapse: collapse;border-spacing: 0;}
			div#bodyContent table.t {border: 1px solid #e4e4e4;}
			div#bodyContent table.t td, table.t th {border-right: 1px solid #e4e4e4;border-bottom: 1px solid #e4e4e4;color: #000;}
			div#bodyContent table.t td {padding: 10px 0;padding-left: 10px;}
			div#bodyContent p, div#bodyContent pre {font-family: Tahoma,"SimSun";line-height: 24px;font-size: 12px;}
			div#bodyContent table.t tr td, div#bodyContent table.t tr td * {line-height: 18px;}
			div#bodyContent table.t th {font-size: 14px;line-height: 20px;padding: 7px 0;background-color: #efefef;text-align: center;}
			.code {width: 749px;font-size: 14px;color: #595959;border: 1px dashed #cecfcf;background-color: #f5f6f7;padding: 6px 12px;margin-top: 10px;margin-bottom: 10px; display:none}
			.btn_blue{border:1px solid #0476BD;border-radius:2px;padding:3px 8px 4px;font-size:14px;color:#fff!important;background:#319FDE;background:-webkit-gradient(linear,0 0,0 100%,from(#319FDE),to(#188ED4));background:-moz-linear-gradient(top,#319FDE,#188ED4);background:-o-linear-gradient(top,#319FDE,#188ED4);filter:progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#319FDE,endColorStr=#188ED4);-ms-filter:"progid:DXImageTransform.Microsoft.gradient(startColorStr='#319FDE',EndColorStr='#188ED4')"}
			.param_name{cursor: pointer;}
		</style>
	</head>
	<body>
		<div style="padding-left:10%">
			<p style="padding-top:30px"></p><h3>接口调用说明</h3><p></p>
			<div id="bodyContent">
				<table class="t">
					<tbody>
						<tr>
							<th width="120"><b>URL</b></th>
							<td width="642">${url}</td>
						</tr>
						<tr>
							<th width="120"><b>HTTP请求方式</b></th>
							<td width="642">${methodType}</td>
						</tr>
						<tr>
							<th width="120"><b>接口功能说明</b></th>
							<td width="642">${description}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<p></p><h3>输入参数说明</h3><p></p>
			<div id="bodyContent">
				<table class="t">
					<tbody>
						<tr>
							<th width="80"><b>参数名称</b></th>
							<th width="80"><b>是否必须</b></th>
							<th width="80"><b>类型</b></th>
							<th><b>描述</b></th>
						</tr>
						<c:forEach begin="0" end="${fn:length(parameters)-1}" var="i">
						<tr>
							<td class="param_name"><b>${parameters[i].name}</b></td>
							<td><font color="red">${parameters[i].required}</font></td>
							<td>${parameters[i].type}</td>
							<td width="520">${parameters[i].description}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<p></p><h3>接口调用测试</h3><p></p>
			<span>请求方式</span>
			<input type="radio" id="method-radio-1" name="method" value="get" class="radio" checked="checked">GET 
			<input type="radio" id="method-radio-2" name="method" value="post" class="radio">POST
			<p><textarea style="width:775px;height:78px;" id="params-content" name="request_line"></textarea></p>
			<p><button id="sig-submit" class="btn btn_blue">提交</button><button onclick="$('#params-content').val('')" class="btn btn_blue" style="margin-left:20px">清空</button></p>
			<div class="code"><p id="result" style="min-height:48px"></p></div>
		</div>
	</body>
	<script type="text/javascript">
		$("#sig-submit").click(function() {
			$.ajax({
				url:"${url}", data: $("#params-content").val(), dataType: "text",
				type:$("#method-radio-1").is(":checked") ? "get" : "post",
				success: function(data) {
					$("#result").html(data);
					$(".code").show();
				},
				error: function(err) {
					$("#result").html(err.status + " " + err.statusText);
					$(".code").show();
				}
			});
		});
		$(".param_name").click(function() {
			var params = $("#params-content").val();
			if(params.indexOf(this.innerText) == -1) {
				if (params.charAt(params.length-1) != "&" && params.replace(/\s+/g,"").length > 0) {
					params = params + "&" + this.innerText + "=";
				} else {
					params = params + this.innerText + "=";
				}
				$("#params-content").val(params);
			}
		});
	</script>
</html>