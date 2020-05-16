//提交的方法名称
var method = "";
var listParam = "";
var saveParam = "";
// IP地址
var uri = 'http://localhost:';
// 端口号
//var port = '8080/';
// HTTP请求方法
var httpMethod = 'POST';
 var port = '80/';
// var uri = 'http://118.25.130.181:';
var item = 'news/';
// 模块
var mode = 'admin/';
// 当前数据表格是否全选
var isSelectAll = false;
// 带新增按钮工具条的数据表格
$(function(){
	//加载表格数据
	$('#grid').datagrid({
		url:uri + port + item + mode + name + 'list' + listParam,
		columns:columns,
		method: 'get',
		pagination: true,
		dataType:'JSON',
        singleSelect: true,// 如果为true，则只允许选择一行。
        striped : true, 	// 是否显示斑马线效果
        rownumbers : true,	//如果为true，则显示一个行号列
		toolbar: [{
			text: '新增',
			iconCls: 'icon-add',
			handler: function(){
                // 打开前清空表单
                $('#editDlg').form('clear');
				//设置保存按钮提交的方法为add
				method = "add";
				//关闭编辑窗口
				$('#editDlg').dialog('open');
			}
		}]
	});

	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});
	// 定义对话框默认的高与宽
	var h = 400;	
	var w = 400;
	if(typeof(height) != "undefined"){
		h = height;
	}
	if(typeof(width) != "undefined"){
		w = width;
	}
	//初始化编辑窗口
	$('#editDlg').dialog({
		title: '编辑',//窗口标题
		width: w,//窗口宽度
		height: h,//窗口高度
		closed: true,//窗口是是否为关闭状态, true：表示关闭
		modal: true//模式窗口
	});

	//点击保存按钮
	$('#btnSave').bind('click',function(){
		//做表单字段验证，当所有字段都有效的时候返回true。该方法使用validatebox(验证框)插件。
		var isValid = $('#editForm').form('validate');
		if(isValid == false){
			return;
		}
		var formData = $('#editForm').serializeJSON();
		$.ajax({
			url:  uri + port + item + mode + name + method + saveParam,
			data: JSON.stringify(formData),
		    contentType:"application/json;charset=utf-8",
			type: httpMethod,
			success:function(rtn){
				$.messager.alert("提示",rtn.msg,'info',function(){
					//成功的话，我们要关闭窗口
					$('#editDlg').dialog('close');
					//刷新表格数据
					$('#grid').datagrid('reload');
				});
			}
		});
        httpMethod = 'POST';
	});
	
	// 生成状态查询的下拉框
	$('.select-style').combobox({
		url: uri + port + item + 'getState',
		valueField: 'id',
		textField: 'text'
	});
});


/**
 * 删除
 */
function del(id){
	$.messager.confirm("确认","确认要删除吗？",function(yes){
		if(yes){
			$.ajax({
				url:  uri + port + item + mode + name + 'delete',
				data: JSON.stringify(id),
				contentType:"application/json;charset=utf-8",
				type: 'delete',
				success:function(rtn){
					$.messager.alert("提示",rtn.msg,'info',function(){
						//刷新表格数据
						$('#grid').datagrid('reload');
					});
				}
			});
		}
	});
}

/**
 * 修改
 */
function edit(id){
    var row = $('#grid').datagrid('getSelected');
    httpMethod = 'PUT';
    if (row) {
        $('#editDlg').dialog('open');
        // 设置保存按钮提交的方法为update
        method = "update";
        $('#editForm').form('load', row);
    }
	// //弹出窗口
	// $('#editDlg').dialog('open');
	// //清空表单内容
	// $('#editForm').form('clear');
	// //设置保存按钮提交的方法为update
	// method = "update";
	// //加载数据
	// $('#editForm').form('load',uri + items + name + 'find' + '?id=' + id);
}

// 格式化时间日期 格式 yyyy-mm-dd.html dd.html::ss:tt 可以省略时间只保留日期
Date.prototype.Format = function (fmt) {
	  var o = {
		"M+": this.getMonth() + 1, //月份			
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时			
		"m+": this.getMinutes(), //分			
		"s+": this.getSeconds(), //秒		
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度			
		"S": this.getMilliseconds() //毫秒			
	  };			
	  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));		
	  for (var k in o)		
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));			
	  return fmt;			
};

// 显示当前状态文本
function showStatus(value) {
	var state;
	if(value == 1)
		state = '正常';
	else if (value == 2)
		state = '注销';
	else if (value == 3)
		state = '未上线';
	return state;
}
/**
 * 自动将form表单封装成json对象
 */
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

/**
 * 更新数据
 */
function update() {
	var data = $('#editForm').serializeObject();
	var jsonData = JSON.stringify(data);
	$.ajax({
        type: 'PUT',
        contentType: 'application/json; charset=UTF-8',
        dataType: "json",
        url: uri + port + item + mode + name + 'update',
        data: JSON.stringify(data), //或者直接 s,结束
        async: true,
        success: function (rtn) {
            $.messager.alert("提示",rtn.msg,'info',function(){
                //成功的话，我们要关闭窗口
                $('#editDlg').dialog('close');
                //刷新表格数据
                $('#grid').datagrid('reload');
            });
        }
	});
	
}

// 在另一个页面提交数据
function saveData() {
	 var data = $('#contentAddForm').serializeObject();
	 var jsonData = JSON.stringify(data);
	 $.ajax({
		 type: 'POST',
		 contentType: 'application/json; charset=UTF-8',
		 dataType: "json",
		 url: uri + port + item + mode + name + 'add',
		 data: jsonData, //或者直接 s,结束
		 async: true,
		 success: function (data) {			
			$.messager.alert("提示",data.msg,'info',function(){
				if (data.suc)
					//成功的话，我们要关闭窗口
					$('#contentAddForm').form('clear');
				// //刷新表格数据
				// $('#grid').datagrid('reload');
			});
		 }
	 });
}
// 在同页面提交数据
// function savedata() {
//     var id = $('#id').val();
//     if (id != '')
//     	return update();
// 	 var data = $('#editForm').serializeObject();
// 	 var jsonData = JSON.stringify(data);
// 	 $.ajax({
// 		 type: 'POST',
// 		 contentType: 'application/json; charset=UTF-8',
// 		 dataType: "json",
// 		 url: uri + port + item + mode + name + 'add',
// 		 data: JSON.stringify(data), //或者直接 s,结束
// 		 async: true,
// 		 success: function (data) {
// 				  $.messager.alert("提示", data.msg, 'info', function () {
//                       $('#editForm').form('clear');
// 					  $('#editForm').form('close');
// 					  //刷新表格数据
// 					  $('#grid').datagrid('reload');
// 				  });
// 			  // if (data.suc == true) {
// 				// $.messager.show({
// 				// 	title: '提示',
// 				// 	msg: data.msg,
// 				// 	width:400,
// 				// 	height:200,
// 				// 	style:{left:500, top:100},
// 				// 	timeout:1000,
// 				// 	showType:'fade'
// 				// });
// 				// $('#editForm').form('clear');
// 			}
// 	 });
// }
// 省略过多的内容
function conciseContent(value) {
	if(value.length>20)
		return "<span title='"+value+"'>"+value.substring(0,20)+"..."+"</span>";
	else
		return "<span title='"+value+"'>"+value.substring(0,value.length)+"</span>";
}

/**
 * 清除查询条件
 */
function clearSearchform() {
    $('#searchForm').form('clear');
}

// 关掉的对话框的方法
function closeDialog() {
	$("#editDlg").dialog('close');
}


// $('#select').change(function (e) {
// 	alert('change');
// 	allselectRow();
// });

//全选
function allselectRow() {
    $('#grid').datagrid('selectAll');
}
//反选
function unselectRow(tableName) {
    var s_rows = $.map($('#grid').datagrid('getSelections'),
        function(n) {
            return $('#grid').datagrid('getRowIndex', n);
        });
    $('#grid').datagrid('selectAll');
    $.each(s_rows, function(i, n) {
        $('#grid').datagrid('unselectRow', n);
    });

}
//全清
// 数据表格的全选与全取消操作
// function selectAll() {
//     $('.easyui-checkbox').each(function (i, n) {
//     	if(!isSelectAll) {
//             $(n).attr('checked', 'checked');
//         }
//     	else {
//             $(n).attr('checked', '');
//             $(n).prop('checked','true');
//         }
//     });
//     isSelectAll = !isSelectAll;
// }