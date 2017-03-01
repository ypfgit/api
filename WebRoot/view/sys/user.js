$(document)
    .ready(
        function() {
        	
        	var treeCombo = new TreeCombo("dept_name", "dept_id", "J_treeCombo", "J_columnTree", r_path + "/dept/getTreeList");
        	var validate = null;
            
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
                            VM.deptid = treeNode.id;
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
                deptid: null,
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
                    param.deptid = this.deptid;
                    param.queryname = this.queryname;
                    return param;
                },
                addItemInit: function() {
                    this.mode = "add";

                    $('#J_form').clearForm();
                    $('#J_form').setForm({status:1});

                    var index = layer.open({
                        type: 1,
                        title: '新增--会员',
                        maxmin: false,
                        //area: ['600px', '600px'],
                        content: $('#J_layer')//,
                        //btn: ['保存', '关闭'], // 可以无限个按钮
                        /*yes: function(index, layero) {
                            $("#J_form").validate();
                            if ($("#J_form").valid()) {
                                $("#J_form").ajaxSubmit({
                                    url: r_path + '/user/save',
                                    type: "post",
                                    success: function(re) {
                                        if (re.success) {
                                            //layer.close(index);
                                            _table.draw();
                                            $('#J_userid').val(re.id);
                                            layer.msg('用户信息保存成功', {
                                                icon: 1,
                                                time: 2000
                                            });
                                        } else {
                                            layer.msg(re.msg, {
                                                icon: 1,
                                                time: 1000
                                            });
                                        }
                                    }
                                });
                            }
                        }*/
                    });
                    layer.full(index);
                },
                reviewItemInit: function(item) {
                    this.mode = "review";

                    if (!item) {
                        return;
                    }
                    
                    $("input[name='v_company'][value="+item.v_company+"]").attr("checked",true); 
                    
                    $("input[name='v_card'][value="+item.v_card+"]").attr("checked",true); 
                    
                    $("input[name='card_id'][value="+item.card_id+"]").attr("checked",true); 
                    
                    $("#J_cardbegin").val(item.card_begin);
                    
                    $("#J_cardend").val(item.card_end);
                    
                    var index = layer.open({
                        type: 1,
                        maxmin: false,
                        title: "企业会员审核",
                        area: '600px',
                        content: $('#J_review'),
                        btn: ['保存', '关闭'], // 可以无限个按钮
                        yes: function(index, layero) {
                        	var form = $("form","#J_review");
                        	
                            if (form.valid()) {
                                form.ajaxSubmit({
                                    url: r_path + '/user/review',
                                    data:{id:item.id},
                                    type: "post",
                                    success: function() {
                                        layer.close(index);
                                        _table.draw();
                                    }
                                });
                            }
                        }
                    });
                    
                },
                editItemInit: function(item) {
                    this.mode = "edit";

                    if (!item) {
                        return;
                    }
                    
                    
                    $('#J_form').setForm(item);

                    $('#J_user_role').val(item.role_id);
                    
                    $('#J_photo').empty();
                    
                    $.get(r_path+'/user/companyImg',{userid:item.id},function(r){
                    	if(r.length>0){
                    		var $photoDiv = $('#J_photo');
                    		for(var i=0;i<r.length;i++){
                    			var photo = '<img alt="'+r[i].name+'" src="'+r_path+'/'+r[i].path+'" layer-src="'+r_path+'/'+r[i].path+'" width="200" class="thumbnail">';
                    			$photoDiv.append(photo);
                    		}
                    		
                    		 layer.photos({
                         	    photos: '#J_photo'
                         	  });
                    	}
                    });
                    
                  //validate.resetForm();
                    $("#J_form").validate();

                    var index = layer.open({
                        type: 1,
                        maxmin: false,
                        title: "编辑--会员",
                        area: ['600px', '600px'],
                        content: $('#J_layer'),
                        yes: function(index, layero){
                          alert(111);
                          }
                    });
                    layer.full(index);
                }
            };

            var zTree = $.fn.zTree.init($("#J_tree"), setting, [{ name: "森林公安", id: "001", parent_id: "0", isParent: true, open: true }]);
            var nodes = zTree.getNodes();
            if (nodes.length > 0) {
                zTree.selectNode(nodes[0]);
                VM.deptid = nodes[0].id;
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
                                    url: r_path + "/user/manageDatas",
                                    cache: false, // 禁用缓存
                                    data: param, // 传入已封装的参数
                                    dataType: "json",
                                    success: function(
                                        result) {

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
                                    data: "id",
                                    visible: false
                                }, {
                                    className: "text-c va-m", // 文字过长时用省略号显示，CSS实现
                                    data: "name"
                                }, {
                                    className: "text-c va-m",
                                    data: "login_name",
                                    orderable: false
                                }, {
                                    className: "text-c va-m",
                                    data: "status",
                                    render: function(
                                        data,
                                        type,
                                        row,
                                        meta) {
                                        if (data == '1') {
                                            return '<span class="label label-success radius">正常</span>';
                                        } else {
                                            return '<span class="label label-error radius">禁用</span>';
                                        }
                                    }
                                }, {
                                    className: "text-c va-m",
                                    data: "role_name",
                                    orderable: false
                                }, {
                                    className: "text-c va-m",
                                    data: "dept_name",
                                    orderable: false
                                }, {
                                    className: "text-c va-m",
                                    data: "phone",
                                    orderable: false
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
                            "createdRow": function(row, data, index) {

                                var $btnEdit = $('<a style="text-decoration:none" class="btn-edit" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a>');
                                var $btnReview = $('<a style="text-decoration:none" class="ml-10 btn-review"  href="javascript:;" title="企业会员审核"><i class="Hui-iconfont">&#xe6b4;</i></a>');
                                var $btnDel = $('<a style="text-decoration:none" class="ml-10 btn-del"  href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a>');
                                $('td', row).eq(8).append($btnEdit).append($btnDel);
                            },
                            "drawCallback": function(settings) {

                            }
                        })).api(); // 此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

            
            $('#J_query').click(function (){
              	 VM.queryname = $('#J_title').val(); 
              	    _table.draw();
              });
            
            $table.on('click', ".btn-edit", function() {
            	
            	
            	$("#J_loginname").attr("readOnly","true");
            	 $('#J_loginname').css('background-color','#CFCFCF');
                // 点击编辑按钮
                var item = _table.row($(this).closest('tr'))
                    .data();
                $(this).closest('tr').addClass("active")
                    .siblings().removeClass("active");

                VM.currentItem = item;
                VM.editItemInit(item);
            }).on('click', ".btn-review", function() {
                // 点击编辑按钮
                var item = _table.row($(this).closest('tr'))
                    .data();
                $(this).closest('tr').addClass("active")
                    .siblings().removeClass("active");

                VM.currentItem = item;
                VM.reviewItemInit(item);
            }).on('click', ".btn-del", function() {
                // 点击编辑按钮
                var item = _table.row($(this).closest('tr'))
                    .data();
                $(this).closest('tr').addClass("active")
                    .siblings().removeClass("active");
                VM.currentItem = item;

                layer.confirm('确认要删除吗？', { icon: 3, title: '提示' }, function(index) {
                    $.post(r_path + '/user/delete', { ids: item.id },
                        function() {
                            layer.msg('已删除!', {
                                icon: 1,
                                time: 1000
                            });
                            _table.draw();
                        });

                });
            });

            $("#J_add").click(function() {
            	
            	//设置只读：$("#J_loginname").attr("readOnly","true");
            	//取消只读：$("#id").attr("readOnly",false);
            	$("#J_loginname").attr("readOnly",false);
            	  $('#J_loginname').css('background-color','white');
                VM.addItemInit();
            });

            validate =$("#J_form").validate({
                rules: {
                    id: {
                        required: false
                    },
                    name: {
                        required: true,
                    },
                    status: {
                        required: true,
                    },
                    password:{
                    	required:true
                    },
                    login_name: {
                        required: true,
                    },
                    role_id: {
                        required: true,
                    },
                    dept_name: {
                        required: true,
                    },
                    phone: {
                        required: true,
                    },
                    company:{required:true}

                },
                onkeyup: false,
                focusCleanup: true,
                success: "valid"
//                	,
//                submitHandler: function(form) {
//                    $(form).ajaxSubmit({
//                        url: r_path + '/user/edit',
//                        type: "post",
//                        success: function() {
//                            layer.closeAll();
//                        }
//                    });
//                }
            });

            $('#J_catName').click(function() {
                showCatTree();
            });

            /*
             * 设备类型下拉树的设置
             */
            var catTreeSetting = {
                view: {
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "id",
                        pIdKey: "parent_id",
                        rootPId: ""
                    }
                },
                callback: {
                    onClick: catTreeOnClick
                }
            };

            /*
             * 设备类型下拉树的点击事件
             */
            function catTreeOnClick(e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("J_catTree");
                nodes = zTree.getSelectedNodes();
                $("#J_catId").val(nodes[0].id);
                $("#J_catName").val(nodes[0].name);
            }

            /*
             * 展示设备类型SelectTree
             */
            function showCatTree() {
                $.ajax({
                    url: r_path + '/user/getCatTree',
                    type: 'POST',
                    data: {},
                    async: false,
                    success: function(data) {
                        var zTree = $.fn.zTree.init($("#J_catTree"), catTreeSetting, data);

                        zTree.expandAll(true);

                        var deptObj = $("#J_catName");
                        var deptOffset = $("#J_catName").offset();
                        $("#J_treeDiv").css({
                            left: "15px",
                            top: "30px"
                        }).slideDown("fast");

                        $('#J_catTree').css({
                            width: deptObj
                                .outerWidth() - 12 + "px"
                        });
                        var node = zTree.getNodeByParam("id", $('#J_catId').val(), null)
                        zTree.selectNode(node);

                        $("body").bind("mousedown",
                            onBodyDownByCatTree);
                    }
                });
            }
            /*
             * Body鼠标按下事件回调函数
             */
            function onBodyDownByCatTree(event) {
                if (event.target.id.indexOf('switch') == -1) {
                    hideDeviceTypeMenu();
                }
            }
            /*
             * 隐藏设备类型Tree
             */
            function hideDeviceTypeMenu() {
                $("#J_treeDiv").fadeOut("fast");
                $("body").unbind("mousedown", onBodyDownByCatTree);
            }
            
            $('#J_submit').click(function(){
            	$("#J_form").validate();
                if ($("#J_form").valid()) {
                	if(VM.mode=='add'){
                		$('#J_userid').val('');
                		$("#J_form").ajaxSubmit({
                			url: r_path + '/user/save',
                			type: "post",
                			success: function(re) {
                				if (re.success) {
                					_table.draw();
                					$('#J_userid').val(re.id);
                					layer.msg('人员信息保存成功', {
                						icon: 1,
                						time: 2000
                					},function(){
                						layer.closeAll();
                					});
                				} else {
                					layer.msg(re.msg, {
                						icon: 0,
                						time: 1000
                					});
                				}
                			}
                		});
                	}else{
                		$("#J_form").validate();
                        if ($("#J_form").valid()) {
                            $("#J_form").ajaxSubmit({
                                url: r_path + '/user/edit',
                                type: "post",
                                success: function(res) {
                                	
                                 	layer.msg("人员信息修改成功！", {
                                         icon: 1,
                                         time: 1000
                                     },function(){
                 						layer.closeAll();
                                    	 _table.draw();
                 					});
                                	
                                }
                            });
                        }
                	}
                }
            });
            
            $('#J_close').click(function(){
            	layer.closeAll();
            });

        });
