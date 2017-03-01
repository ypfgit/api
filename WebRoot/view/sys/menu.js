$(document).ready(
    function() {

    	var treeCombo = new TreeCombo("parent_name", "parent_id", "J_treeCombo", "J_columnTree", r_path + "/menu/getTreeList");
    	
        var setting = {
            view: {
                dblClickExpand: false,
                showLine: false,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: false
                },
                key: {
                    title: "text",
                    name: "text",
                    url: "xUrl"
                }

            },
            callback: {
                onClick: function(event, treeId, treeNode) {
                    if (!treeNode.leaf) {
                        var zTree = $.fn.zTree.getZTreeObj("J_tree");
                        zTree.expandNode(treeNode);
                    }
                    VM.menuid = treeNode.id;
                    _table.draw();
                }
            }
        };

        var zNodes = r_menus;

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
                param.menuid = this.menuid;
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


        var zTree = $.fn.zTree.init($("#J_tree"), setting, zNodes);
        var nodes = zTree.getNodes();
        if (nodes.length > 0) {
            zTree.selectNode(nodes[0]);
            VM.menuid = nodes[0].id;
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
                                url: r_path + "/menu/manageDatas",
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
                                data: "text"
                            }, {
                                className: "text-c va-m",
                                data: "url",
                                orderable: false,
                                width: "80px"
                            }, {
                                className: "text-c va-m",
                                data: "sort_no"
                            }, {
                                className: "text-c va-m",
                                data: "icon"
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
                            $('td', row).eq(7).append($btnEdit).append($btnDel);
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

            layer.confirm('确认要删除吗？', function(index) {
                $.post(r_path + '/menu/delete',{id:item.id,parentid:item.parent_id}, function() {
                    layer.msg('已删除!', { icon: 1, time: 1000 });
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
                parent_name: {
                    required: true,
                },
                url: {
                    required: true,
                },
                sort_no: {
                    required: true,
                },
                text: {
                    required: true,
                }

            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function(form) {

                var url = '';
                if(VM.mode=='add'){
                    url = r_path + '/menu/save';
                }else if(VM.mode=='edit'){
                    url = r_path + '/menu/edit';
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
                               msg="保存成功！"
                           }else if(VM.mode=='edit'){
                           	 msg="修改成功！"
                           }
                           
                       	layer.msg(msg, {
                               icon: 0,
                               time: 1000
                           });
                           
                       
                    }
                });
            }
        });
        
        
        
        /*
         * 上传图标-wq 从case-dtal直接复制过来用的
         */
        
        
        function criminalPhoto(){
        	// 初始化Web Uploader
        	var uploader = WebUploader.create({

        	    // 选完文件后，是否自动上传。
        	    auto: true,

        	    // swf文件路径
        	    swf: r_path+'/resource/webuploader/0.1.5/Uploader.swf',

        	    // 文件接收服务端。
        	    server: r_path+'/case/uploadCriminal',

        	    // 选择文件的按钮。可选。
        	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        	    pick: '#picker',

        	    // 只允许选择图片文件。
        	    accept: {
        	        title: 'Images',
        	        extensions: 'jpg,jpeg,png',
        	        mimeTypes: 'image/jpg,image/jpeg,image/png'
        	    }
        	});
        	
        	/*uploader.on('beforeFileQueued',function(){
        		var userid = $('#J_userid').val();
        		if(userid==''||userid==null){
        			layer.msg('请先保存用户信息,再上传企业资料', {
                        icon: 2,
                        time: 2000
                    });
        			
        			return false;
        		}
        	});
        	uploader.on( 'uploadBeforeSend', function( block, data ) {  
        			data.userid = $('#J_userid').val();  
            	    data.username = $('#J_loginname').val();
        	});  
        	
        	// 当有文件添加进来的时候
        	uploader.on( 'fileQueued', function( file ) {
        	    var $li = $(
        	            '<div id="' + file.id + '" class="file-item thumbnail">' +
        	                '<img>' +
        	                '<div class="info">' + file.name + '</div>' +
        	            '</div>'
        	            ),
        	        $img = $li.find('img');


        	    // $list为容器jQuery实例
        	    $('#fileList').append( $li );

        	    // 创建缩略图
        	    // 如果为非图片文件，可以不用调用此方法。
        	    // thumbnailWidth x thumbnailHeight 为 100 x 100
        	    uploader.makeThumb( file, function( error, src ) {
        	        if ( error ) {
        	            $img.replaceWith('<span>不能预览</span>');
        	            return;
        	        }

        	        $img.attr( 'src', src );
        	    }, 110, 110 );
        	});
        	
        	// 文件上传过程中创建进度条实时显示。
        	uploader.on( 'uploadProgress', function( file, percentage ) {
        	    var $li = $( '#'+file.id ),
        	        $percent = $li.find('.progress span');

        	    // 避免重复创建
        	    if ( !$percent.length ) {
        	        $percent = $('<p class="progress"><span></span></p>')
        	                .appendTo( $li )
        	                .find('span');
        	    }

        	    $percent.css( 'width', percentage * 100 + '%' );
        	});*/

        	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
        	uploader.on( 'uploadSuccess', function( file,res ) {
        	    //$( '#'+file.id ).addClass('upload-state-done');
        	    $('#J_criminal_photo').val(res.path);
        	});

        	// 文件上传失败，显示上传出错。
        	/*uploader.on( 'uploadError', function( file ) {
        	    var $li = $( '#'+file.id ),
        	        $error = $li.find('div.error');

        	    // 避免重复创建
        	    if ( !$error.length ) {
        	        $error = $('<div class="error"></div>').appendTo( $li );
        	    }

        	    $error.text('上传失败');
        	});*/

        	// 完成上传完了，成功或者失败，先删除进度条。
        	/*uploader.on( 'uploadComplete', function( file ) {
        	    $( '#'+file.id ).find('.progress').remove();
        	});*/
        }
        
        criminalPhoto();
    });
