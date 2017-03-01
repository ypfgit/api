var code;

function showCode(str) {
    if (!code)
        code = $("#code");
    code.empty();
    code.append("<li>" + str + "</li>");
}


function initUpload(){
	var $list = $("#thelist");
    var $btn = $("#btn-star");
    var state = "pending";

    var uploader = WebUploader.create({
        auto: true,
        swf: r_path+'/resource/webuploader/0.1.5/Uploader.swf',

        // 文件接收服务端。
        server: r_path+"/news/upload",

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',

        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false,
        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'jpg,jpeg,png',
	        mimeTypes: 'image/jpg,image/jpeg,image/png'
        }
    });
    
    uploader.on( 'beforeFileQueued', function( file ) {
    	var logos = $('img','#thelist');
		if(logos.length==1){
			layer.msg('只能上传1张缩略图', {
                icon: 2,
                time: 2000
            });
			return false;
		}
	});
 // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
	
    	var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
            '<button class="file-del btn btn-primary-outline radius size-MINI" type="button"> <i class="Hui-iconfont c-error">&#xe706;</i> </button>'+
                '<img>' +
                '<div class="info">' + file.name + '<i class="Hui-iconfont c-success r">&#xe6a7;</i></div>' +
            '</div>'
            ),
        $img = $li.find('img');
    	
    	// $list为容器jQuery实例
        $list.append( $li );

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
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function(file,res) {
    	
    	$( '#'+file.id ).addClass('upload-state-done');
        $('#J_thumb').val(res.path);
        
        var ss = '<button class="file-del btn btn-primary-outline radius size-MINI" type="button"> <i class="Hui-iconfont c-error">&#xe706;</i> </button>';
	    $('#'+file.id).append(ss);
	    
	    $('.file-del','#thelist').click(function(){
			var parent = $(this).parent();
     			parent.remove();
     			
     			$('#J_thumb').val('');
     	});
        
    });

    // 文件上传失败，显示上传出错。
    uploader.on('uploadError', function(file,res) {
    	var $li = $( '#'+file.id ),
        $error = $li.find('div.error');

    // 避免重复创建
    if ( !$error.length ) {
        $error = $('<div class="error"></div>').appendTo( $li );
    }

    $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on('uploadComplete', function(file,res) {
    	$( '#'+file.id ).find('.progress').remove();
    });
    
    VM.up = uploader;
}


var VM = {
		up:null,
	mode: null,
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

        return param;
    },
    addItemInit: function() {
    	this.mode = "add";
        $('#J_form').clearForm();
        $('#thelist').empty();
        var index = layer.open({
            type: 1,
            maxmin: true,
            title: "添加",
            area: '500px',
            //content : r_path + "/menu/edit",
            content: $('#J_layer'),
            success:function(layero,index){
            	initUpload();
            },
            cancel:function(){
            	VM.up.destroy();
            }
        });
        layer.full(index);
    },
    editItemInit: function(item) {
    	this.mode="edit";
    	
        if (!item) {
            return;
        }
        
    	$('#J_form').setForm(item);
    	if(item.iscomment=='1'){
    		$('#J_iscomment').iCheck("check");
    	}
        
    	if(item.status=='1'){
    		$('#J_paper').hide();
    		$('#J_submit').text('保存');
    	}
    	
    	$('#thelist').empty();
    	var $li = $(
	            '<div id="' + item.id + '" class="file-item thumbnail upload-state-done">' +
	            '<button class="file-del btn btn-primary-outline radius size-MINI" type="button"> <i class="Hui-iconfont c-error">&#xe706;</i> </button>'+
	                '<img src="' + r_path + '/' + item.thumb + '" height="110" width="110">' +
	                '<div class="info">' + '' + '<i class="Hui-iconfont c-success r">&#xe6a7;</i></div>' +
	            '</div>'
	            );
	 
	    $('#thelist').append( $li );
	    
	    $('.file-del','#thelist').click(function(){
			var parent = $(this).parent();
     			parent.remove();
     			
     			$('#J_thumb').val('');
     	});

        var index = layer.open({
            type: 1,
            maxmin: true,
            title: "编辑信息",
            // content : r_path + "/menu/edit",
            content: $('#J_layer'),
            success:function(layero,index){
            	initUpload();
            },
            cancel:function(){
            	VM.up.destroy();
            }
        });
        layer.full(index);
    }
};

$(document).ready(
    function() {
    	
    	var treeCombo = new TreeCombo("columnname", "columnid", "J_treeCombo", "J_columnTree", r_path + "/column/getTreeList");

        var $table = $('.table-sort');

        var _table = $table.dataTable(
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
                            url: r_path + "/news/manageDatas",
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
                            data: "id",
                            visible: false
                        }, {
                            className: "text-c va-m", // 文字过长时用省略号显示，CSS实现
                            data: "title"
                        }, {
                            className: "text-c va-m",
                            data: "columnname",
                            orderable: false,
                            width: "80px"
                        }, {
                            className: "text-c va-m",
                            data: "createdate"
                        }, {
                            className: "text-c va-m",
                            data: 'hits',
                            orderable: false
                        }, {
                            className: "text-c va-m f-14",
                            data: "status",
                            width: "120px",
                            render: function(data, type, row, meta) {
                                if (data == '0') {
                                    return '<span class="label label-default radius">草稿</span>';
                                } else if (data == '1') {
                                    return '<span class="label label-primary radius">发布</span>';
                                } 
                            }
                        }, {
                            className: "text-c va-m f-14",
                            data: null,
                            defaultContent: "",
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

        $table.on('click', ".btn-edit", function() {
            // 点击编辑按钮
            var item = _table.row($(this).closest('tr')).data();
            $(this).closest('tr').addClass("active").siblings().removeClass("active");
            VM.currentItem = item;
            VM.editItemInit(item);
        }).on('click', ".btn-del", function() {
            // 点击编辑按钮
            var item = _table.row($(this).closest('tr')).data();
            $(this).closest('tr').addClass("active").siblings().removeClass("active");
            VM.currentItem = item;

            layer.confirm('确认要删除吗？', function(index) {
                $.post(r_path + '/news/delete',{ids:item.id}, function() {
                    layer.msg('已删除!', { icon: 1, time: 1000 });
                    _table.draw();
                });
            });
        });

        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });
        
        $("#J_add").click(function() {
            VM.addItemInit();
        });
        
        

        var ue = UE.getEditor('J_editor');
        ue.ready(function(){
        	if(VM.mode=="edit"){
        		$.get(r_path+'/news/getNewsContent',{id:VM.currentItem.id},function(re){
        			ue.setContent(re.content);
                });
        	}
        })

        $("#J_form").validate({
            rules: {
                id: {
                    required: false
                },
                content: {
                    required: true,
                },
                columnname:{
                	required:true
                },
                title:{required:true},
                sortno:{
                	digits:true
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function(form) {
            	var url = '';
                if(VM.mode=='add'){
                    url = r_path + '/news/save';
                }else if(VM.mode=='edit'){
                    url = r_path + '/news/edit';
                }
                $(form).ajaxSubmit({
                    url: url,
                    type:'post',
                    type: "post",
                    success: function() {
                        layer.closeAll();
                    }
                });
            }
        });
        
        $('#J_paper')
        .click(
            function() {
                $("#J_form").validate();
                if (!$("#J_form").valid()) {
                	layer.msg('请将信息填写完整!', {
                        icon: 2,
                        time: 1000
                    });
                    return;
                }
                
                var url = '';
                if(VM.mode=='add'){
                    url = r_path + '/news/save';
                }else if(VM.mode=='edit'){
                    url = r_path + '/news/edit';
                }
                
                $('#J_form').ajaxSubmit({
                	url:url,
                	type:'post',
                    data: {
                        "status": '0'
                    },
                    success: function() {
                    	_table.ajax.reload();
                    	VM.up.destroy();
                    	layer.closeAll();
                    }
                });
            });
        
        $('#J_submit')
        .click(
            function() {
                $("#J_form").validate();
                if (!$("#J_form").valid()) {
                	layer.msg('请将信息填写完整!', {
                        icon: 2,
                        time: 1000
                    });
                    return;
                }
               
                var url = '';
                if(VM.mode=='add'){
                    url = r_path + '/news/save';
                }else if(VM.mode=='edit'){
                    url = r_path + '/news/edit';
                }
                $('#J_form').ajaxSubmit({
                	url:url,
                	type:'post',
                    data: {
                        "status": '1'
                    },
                    success: function() {
                    	_table.ajax.reload();
                    	VM.up.destroy();
                    	layer.closeAll();
                    }
                });
            });
        
        $('#J_close').click(function(){
        	VM.up.destroy();
        	layer.closeAll();
        });
    });
