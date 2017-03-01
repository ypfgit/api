var VM = {
    layerIndex: null,
    currentItem: null,
    queryname:null,
    fuzzySearch: true,
    mode: null,
    getQueryCondition: function(data) {
        var param = {};
        // 组装排序参数
        if (data.order && data.order.length && data.order[0]) {
            switch (data.order[0].column) {
                case 1:
                    param.orderColumn = "id";
                    break;
                case 2:
                    param.orderColumn = "text";
                    break;
                default:
                    param.orderColumn = "id";
                    break;
            }
            param.orderDir = data.order[0].dir;
        }
        // 组装分页参数
        param.startIndex = data.start;
        param.pageSize = data.length;
        param.queryname = this.queryname;
        return param;
    },
    addItemInit: function() {
        this.mode = "add";
        $('#J_form').clearForm();
        var index = layer.open({
            type: 1,
            maxmin: true,
            title: "添加",
            area: '500px',
            content: $('#J_layer')
        });
    },
    editItemInit: function(item) {
        this.mode = "edit";

        if (!item) {
            return;
        }

        $('#J_form').setForm(item);

        var index = layer.open({
            type: 1,
            maxmin: true,
            title: "编辑",
            area: '500px',
            content: $('#J_layer')
        });
        // layer.full(index);
    },
    perItemInit: function(item) {
        var index = layer.open({
            type: 2,
            maxmin: true,
            title: "角色授权",
            area: ['500px', '500px'],
            content: r_path + '/role/permission/' + item.id,
            btn: ['保存', '关闭'], //可以无限个按钮
            yes: function(index, layero) {
                //var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']];
                iframeWin.submit();
//                layer.confirm('角色授权成功！');
                layer.msg('角色授权成功！', {
                    icon: 0,
                    time: 1000
                }, function(){
                    layer.close(index);
            	});
//                var sb = layer.alert('角色授权成功！', {
//                	 skin: 'layui-layer-lan' //样式类名
//                	  ,closeBtn: 0
//                	}, function(){
//                		layer.close(sb);
//                	});
            }
        });
    },
    actionItemInit: function(item) {
        var index = layer.open({
            type: 2,
            maxmin: true,
            title: "操作授权",
            //area: ['500px','500px'],
            content: r_path + '/role/action/' + item.id,
            btn: ['保存', '关闭'], //可以无限个按钮
            yes: function(index, layero) {
                //var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']];
                iframeWin.submit();
                //layer.close(index);
            }
        });
        layer.full(index);
    }
};

$(document).ready(
    function() {
        var $table = $('.table-sort');

        var _table = $table.dataTable(
            $.extend(
                true, {},
                CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
                    ajax: function(data,
                        callback,
                        settings) { // ajax配置为function,手动调用异步查询
                        var param = VM.getQueryCondition(data);
                        $.ajax({
                            type: "GET",
                            url: r_path + "/role/manageDatas",
                            cache: false, // 禁用缓存
                            data: param, // 传入已封装的参数
                            dataType: "json",
                            success: function(
                                result) {

                                var returnData = {};
                                returnData.draw = data.draw; // 这里直接自行返回了draw计数器,应该由后台返回
                                returnData.recordsTotal = result.totalRow;
                                returnData.recordsFiltered = result.totalRow; // 后台不实现过滤功能，每次查询均视作全部结果
                                returnData.data = result.list;
                                callback(returnData);
                            },
                            error: function(
                                XMLHttpRequest,
                                textStatus,
                                errorThrown) {
                                alert("查询失败");
                            }
                        });
                    },
                    "aoColumnDefs": [{
                        "sClass": "center",
                        "orderable": false,
                        "aTargets": [0, 2]
                            // 制定列不参与排序
                    }],
                    columns: [
                        CONSTANT.DATA_TABLES.COLUMN.CHECKBOX, {
                            className: "text-c va-m",
                            data: "id"
                        }, {
                            className: "text-c va-m",
                            data: "name"
                        }, {
                            className: "text-c va-m",
                            data: "remark",
                            width: "80px"
                        }, {
                            className: "text-c va-m f-14",
                            data: null,
                            defaultContent: "",
                            orderable: false,
                            width: "120px"
                        }
                    ],
                    "createdRow": function(row, data, index) {

                        var $btnEdit = $('<a style="text-decoration:none" class="btn-edit" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
                        var $btnPer = $('<a style="text-decoration:none" class="ml-10 btn-per" href="javascript:;" title="菜单授权"><i class="Hui-iconfont">&#xe70c;</i></a>');
                        var $btnDel = $('<a style="text-decoration:none" class="ml-10 btn-del"  href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a>');
                        var $btnAction = $('<a style="text-decoration:none" class="ml-10 btn-action" href="javascript:;" title="操作授权"><i class="Hui-iconfont">&#xe624;</i></a>');

                        $('td', row).eq(4).append($btnEdit).append($btnPer).append($btnAction).append($btnDel);
                    },
                    "drawCallback": function(
                        settings) {

                    }
                })).api(); // 此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

        
        $('#J_query').click(function (){
          	 VM.queryname = $('#J_title').val(); 
          	    _table.draw();
          });
        
        $table.on('click', ".btn-edit", function() {
            // 点击编辑按钮
            var item = _table.row($(this).closest('tr'))
                .data();
            $(this).closest('tr').addClass("active")
                .siblings().removeClass("active");
            VM.currentItem = item;
            VM.editItemInit(item);
        }).on('click', ".btn-del", function() {
            // 点击编辑按钮
            var item = _table.row($(this).closest('tr'))
                .data();
            $(this).closest('tr').addClass("active").siblings().removeClass("active");
            VM.currentItem = item;

            layer.confirm('将同时删除相关的<strong>用户角色信息,角色授权信息</strong>,确认要删除吗？', { icon: 3, title: '提示' }, function(index) {
                $.post(r_path + '/role/delete/' + item.id);
                layer.msg('已删除!', {
                    icon: 1,
                    time: 1000
                });
                _table.draw();
            });

        }).on('click', ".btn-per", function() {
            // 点击编辑按钮
            var item = _table.row($(this).closest('tr')).data();
            $(this).closest('tr').addClass("active").siblings().removeClass("active");
            VM.currentItem = item;
            VM.perItemInit(item);
        }).on('click', ".btn-action", function() {
            // 点击编辑按钮
            var item = _table.row($(this).closest('tr'))
                .data();
            $(this).closest('tr').addClass("active")
                .siblings().removeClass("active");
            VM.currentItem = item;
            VM.actionItemInit(item);
        });

        $("#btn-add").click(function() {
            VM.addItemInit();
        });

        // 表单验证
        $("#J_form").validate({
            rules: {
                name: {
                    required: true,
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function(form) {
                var url = "";
                if (VM.mode == 'edit') {
                    url = r_path + '/role/edit';
                } else {
                    url = r_path + '/role/add';
                }
                $(form).ajaxSubmit({
                    url: url,
                    type: "post",
                    success: function() {
                        layer.closeAll();
                        
                        var msg;
                        if(VM.mode=='add'){
                            msg="角色保存成功！"
                        }else if(VM.mode=='edit'){
                        	 msg="角色修改成功！"
                        }
                        
                    	layer.msg(msg, {
                            icon: 0,
                            time: 1000
                        });
                        
                        _table.draw();
                    }
                });
            }
        });
    });
