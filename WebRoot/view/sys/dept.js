$(document).ready(
    function() {

        var treeCombo = new TreeCombo("parent_name", "parent_id", "J_treeCombo", "J_columnTree", r_path + "/dept/getTreeList");
        var setting = {
            view: {
                dblClickExpand: false,
                showLine: false,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true
                },
                key: {
                    pIdKey: 'parent_id'
                }

            },
            async: {
                url: r_path + '/dept/getTreeNodes',
                enable: true,
                autoParam: ["id"]
            },
            callback: {
                onClick: function(event, treeId, treeNode) {
                    /*if (!treeNode.leaf) {
                        var zTree = $.fn.zTree.getZTreeObj("J_tree");
                        zTree.expandNode(treeNode);
                    }*/
                    VM.treeid = treeNode.id;
                    _table.draw();
                },
                onAsyncSuccess: function(event, treeId, treeNode, msg) {
                    /*var selectedNode = zTree.getSelectedNodes();
                    var nodes = zTree.getNodes();
                    zTree.expandNode(nodes[0], true);*/
                    
                    var nodes = treeNode.children;
                    
                    for(var i=0;i<nodes.length;i++){
                    	zTree.expandNode(nodes[i],true,false,true,true);
                    }
                    
                }
            }
        };


        var VM = {
            mode: null,
            menuid: null,
            queryname:null,
            layerIndex: null,
            currentItem: null,
            fuzzySearch: true,
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
                param.treeid = this.treeid;
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
                    //content : r_path + "/menu/edit",
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
                    title: "编辑菜单",
                    area: '500px',
                    //content : r_path + "/menu/edit",
                    content: $('#J_layer')
                });
                //layer.full(index);
            }
        };

        var zTree = $.fn.zTree.init($("#J_tree"), setting, [{ name: "森林公安", id: "001", parent_id: "0", isParent: true, open: true }]);
        var nodes = zTree.getNodes();
        if (nodes.length > 0) {
            zTree.selectNode(nodes[0]);
            VM.treeid = nodes[0].id;
            //zTree.expandAll(true);
            zTree.reAsyncChildNodes(zTree.getNodes()[0], "refresh", false);
        }

        var $table = $('.table-sort');

        var _table = $table
            .dataTable(
                $.extend(
                    true, {},
                    CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
                        ajax: function(data,
                            callback,
                            settings) { // ajax配置为function,手动调用异步查询
                            // 封装请求参数
                            var param = VM
                                .getQueryCondition(data);
                            $.ajax({
                                type: "GET",
                                url: r_path + "/dept/manageDatas",
                                cache: false, // 禁用缓存
                                data: param, // 传入已封装的参数
                                dataType: "json",
                                success: function(
                                    result) {
                                    // 异常判断与处理
                                    // if
                                    // (result.errorCode)
                                    // {
                                    // return;
                                    // }

                                    // 封装返回数据，这里仅演示了修改属性名
                                    var returnData = {};
                                    returnData.draw = data.draw; // 这里直接自行返回了draw计数器,应该由后台返回
                                    returnData.recordsTotal = result.totalRow;
                                    returnData.recordsFiltered = result.totalRow; // 后台不实现过滤功能，每次查询均视作全部结果
                                    returnData.data = result.list;
                                    // 调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                                    // 此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
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
                            } // 制定列不参与排序
                        ],
                        columns: [
                            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX, {
                                className: "text-c va-m", // 文字过长时用省略号显示，CSS实现
                                data: "id"
                            }, {
                                className: "text-c va-m", // 文字过长时用省略号显示，CSS实现
                                data: "parent_name"
                            }, {
                                className: "text-c va-m",
                                data: "name",
                                orderable: false,
                                width: "80px"
                            }, {
                                className: "text-c va-m",
                                data: "sort_no"
                            }, {
                                className: "text-c va-m",
                                data: "remark",
                                orderable: false
                            }, {
                                className: "text-c va-m f-14",
                                data: null,
                                defaultContent: "",
                                orderable: false,
                                width: "120px"
                            }
                        ],
                        "createdRow": function(
                            row, data,
                            index) {

                            var $btnEdit = $('<a style="text-decoration:none" class="btn-edit" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
                            var $btnDel = $('<a style="text-decoration:none" class="ml-10 btn-del"  href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a>');
                            $('td', row).eq(6).append($btnEdit).append($btnDel);
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
            var item = _table.row($(this).closest('tr')).data();
            $(this).closest('tr').addClass("active").siblings()
                .removeClass("active");
            VM.currentItem = item;
            VM.editItemInit(item);
        }).on('click', ".btn-del", function() {
            // 点击编辑按钮
            var item = _table.row($(this).closest('tr')).data();
            $(this).closest('tr').addClass("active").siblings()
                .removeClass("active");
            VM.currentItem = item;

            layer.confirm('确认要删除吗？',{icon: 3, title:'提示'}, function(index) {
            	$.post(r_path + '/dept/delete', { ids: item.id },
                        function() {
                            layer.msg('已删除!', {
                                icon: 1,
                                time: 1000
                            });
                            zTree.reAsyncChildNodes(zTree.getNodes()[0], "refresh", false);
                            _table.draw();
                        });
            });
        });

        $("#J_add").click(function() {
            VM.addItemInit();
        });

        $("#J_form").validate({
            rules: {
                id: {
                    required: true
                },
                sort_no: {
                    required: true
                },
                name: {
                    required: true,
                },
                parent_name:{
                	required: true,
                }

            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function(form) {
                var url = '';
                if(VM.mode=='add'){
                    url = r_path + '/dept/save';
                }else if(VM.mode=='edit'){
                    url = r_path + '/dept/edit';
                }
                $(form).ajaxSubmit({
                    url: url,
                    type: "post",
                    success: function() {
                    	zTree.reAsyncChildNodes(zTree.getNodes()[0], "refresh", false);
                    	_table.ajax.reload();
                        layer.closeAll();
                        
                        var msg;
                        if(VM.mode=='add'){
                            msg="部门保存成功！"
                        }else if(VM.mode=='edit'){
                        	 msg="部门修改成功！"
                        }
                        
                    	layer.msg(msg, {
                            icon: 0,
                            time: 1000
                        });
//                        var sb = layer.alert(msg, {
//                       	 skin: 'layui-layer-lan' //样式类名
//                       	  ,closeBtn: 0
//                       	}, function(){
//                       		layer.close(sb);
//                       	});
                    }
                });
            }
        });
    });
