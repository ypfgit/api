

$(document).ready(
    function() {
    	
    	$.Huitab("#J_tab .tabBar span","#J_tab .tabCon","current","click","0");
    	
    	function initFile(){
    		$.get(r_path+'/setting/info',{},function(r){
    			
    			if(!$.isEmptyObject(r)){
    				$('#J_form1').setForm(r);
    				
    				if(!$.isEmptyObject(r.logo)){
    					var $logoDiv = $('#logoList');
    					
    					var logo = '<div id="logo" fileid="logo" class="file-item thumbnail upload-state-done">'
        				+'<button class="file-del btn btn-primary-outline radius size-MINI" type="button"> <i class="Hui-iconfont c-error">&#xe706;</i> </button>'
        				+'<img width="100" height="100" layer-src="'
        				+r_path
        				+'/'+r.logo
        				+'" src="'
        				+r_path+'/'
        				+r.logo
        				+'" filepath="'+r.logo+'"><div class="info">'
        				+''+'</div></div>';
    					
    					$logoDiv.append(logo);
    					
    					layer.photos({
                     	    photos: '#logoList'
                     	  });
    					
    					$('.file-del','#logoList').click(function(){
                			var parent = $(this).parent();
                     		parent.remove();
                     	});
    				}
        			
        			/*var banner = [];
        			
        			$.isEmptyObject(r.banner1)||banner.push(r.banner1);
        			$.isEmptyObject(r.banner2)||banner.push(r.banner2);
        			$.isEmptyObject(r.banner3)||banner.push(r.banner3);
        			$.isEmptyObject(r.banner4)||banner.push(r.banner4);
        			$.isEmptyObject(r.banner5)||banner.push(r.banner5);
        			
        			var $photoDiv = $('#fileList');
            		for(var i=0;i<banner.length;i++){
            			
            			var photo = '<div id="banner'+i+'" fileid="banner'+i+'" class="file-item thumbnail upload-state-done">'
            				+'<button class="file-del btn btn-primary-outline radius size-MINI" type="button"> <i class="Hui-iconfont c-error">&#xe706;</i> </button>'
            				+'<img layer-src="'
            				+r_path
            				+'/'+banner[i]
            				+'" src="'
            				+r_path+'/'
            				+banner[i]
            				+'" filepath="'+banner[i]+'" width="128" height="128"><div class="info">'
            				+''+'</div></div>';
            			$photoDiv.append(photo);
            		}
            		  layer.photos({
                 	    photos: '#fileList'
                 	  });
            		 
            		 
            		$('.file-del','#fileList').click(function(){
            			var parent = $(this).parent();
                 		parent.remove();
                 	});*/
            		
            		
    			}
    		});
                
                // 初始化Web Uploader
            	/*var uploader = WebUploader.create({

            	    // 选完文件后，是否自动上传。
            	    auto: true,

            	    // swf文件路径
            	    swf: r_path+'/resource/webuploader/0.1.5/Uploader.swf',

            	    // 文件接收服务端。
            	    server: r_path+'/setting/upload',

            	    // 选择文件的按钮。可选。
            	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            	    pick: '#filePicker',

            	    // 只允许选择图片文件。
            	    accept: {
            	        title: 'Images',
            	        extensions: 'jpg,jpeg,png',
            	        mimeTypes: 'image/jpg,image/jpeg,image/png'
            	    }
            	});
            	
            	uploader.on( 'uploadBeforeSend', function( block, data ) {  
            			data.userid = r_user.id;  
                	    data.username = r_user.login_name;
            	});  
            	
            	uploader.on( 'beforeFileQueued', function( file ) {
            		var logos = $('img','#slideList');
            		if(logos.length==5){
            			layer.msg('只能上传五张幻灯图', {
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
            	    }, 128, 128 );
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
            	uploader.on( 'uploadSuccess', function( file ,res) {
            	    $( '#'+file.id ).addClass('upload-state-done');
            	    var ss = '<button class="file-del btn btn-primary-outline radius size-MINI" type="button"> <i class="Hui-iconfont c-error">&#xe706;</i> </button>';
            	    $('#'+file.id).append(ss);
            	    $('img','#'+file.id).attr('src',r_path+'/'+res.path).attr('filepath',res.path).css('height','128px').css('width','128px');
            	    
            	    $('.file-del','#fileList').click(function(){
            			var parent = $(this).parent();
                 		parent.remove();
                 	});
            	    
            	});

            	// 文件上传失败，显示上传出错。
            	uploader.on( 'uploadError', function( file ) {
            	    var $li = $( '#'+file.id ),
            	        $error = $li.find('div.error');

            	    // 避免重复创建
            	    if ( !$error.length ) {
            	        $error = $('<div class="error"></div>').appendTo( $li );
            	    }

            	    $error.text('上传失败');
            	});

            	// 完成上传完了，成功或者失败，先删除进度条。
            	uploader.on( 'uploadComplete', function( file ) {
            	    $( '#'+file.id ).find('.progress').remove();
            	});*/
    	}
        
    	initFile();
    	     
        	
        	$('#J_submit1').click(function(){
        		
        		var list = $('img','#fileList');
        		/*var data={banner1:'',banner2:'',banner3:'',banner4:'',banner5:'',logo:''};
        		if(list.length>0){
        			list.each(function(i){
        				if(i==0){
        					data.banner1= $(this).attr('filepath');
        				}
        				if(i==1){
        					data.banner2= $(this).attr('filepath');
        				}
        				if(i==2){
        					data.banner3= $(this).attr('filepath');
        				}
        				if(i==3){
        					data.banner4= $(this).attr('filepath');
        				}
        				if(i==4){
        					data.banner5= $(this).attr('filepath');
        				}
        				//eval("data.top" + i + "='" + $(this).attr('src')+"'");
        			});
        		}*/
        		var data ={};
        		var logos = $('img','#logoList');
        		
        		if(logos.length>0){
        			data.logo = $(logos[0]).attr('filepath');
        		}
        		
        		$('#J_form1').ajaxSubmit({
                    url: r_path + '/setting/saveSetting',
                    type: "post",
                    data:data,
                    success: function() {
                    	layer.msg('保存成功', {
                            icon: 1,
                            time: 1500
                        });
                    }
                });
        	});
        	
        	function initLogo(){
        		
        		// 初始化Web Uploader
            	var uploader = WebUploader.create({

            	    // 选完文件后，是否自动上传。
            	    auto: true,

            	    // swf文件路径
            	    swf: r_path+'/resource/webuploader/0.1.5/Uploader.swf',

            	    // 文件接收服务端。
            	    server: r_path+'/setting/upload',

            	    // 选择文件的按钮。可选。
            	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            	    pick: '#logoPicker',

            	    // 只允许选择图片文件。
            	    accept: {
            	        title: 'Images',
            	        extensions: 'jpg,jpeg,png',
            	        mimeTypes: 'image/jpg,image/jpeg,image/png'
            	    }
            	});
            	
            	
            	uploader.on( 'uploadBeforeSend', function( block, data ) {  
            			data.userid = r_user.id;  
                	    data.username = r_user.login_name;
            	});  
            	
            	 uploader.on( 'beforeFileQueued', function( file ) {
            		 var logos = $('img','#logoList');
             		if(logos.length==1){
             			layer.msg('只能上传一张LOGO图', {
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
            	                '<div class="info">' + file.name + '</div>' +
            	            '</div>'
            	            ),
            	        $img = $li.find('img');


            	    // $list为容器jQuery实例
            	    $('#logoList').append( $li );

            	    // 创建缩略图
            	    // 如果为非图片文件，可以不用调用此方法。
            	    // thumbnailWidth x thumbnailHeight 为 100 x 100
            	    uploader.makeThumb( file, function( error, src ) {
            	        if ( error ) {
            	            $img.replaceWith('<span>不能预览</span>');
            	            return;
            	        }

            	        $img.attr( 'src', src );
            	    }, 128, 128 );
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
            	uploader.on( 'uploadSuccess', function( file ,res) {
            	    $( '#'+file.id ).addClass('upload-state-done');
            	    $('img','#'+file.id).attr('src',r_path+'/'+res.path).attr('filepath',res.path).css('height','auto').css('width','auto');
            	    
            	    var ss = '<button class="file-del btn btn-primary-outline radius size-MINI" type="button"> <i class="Hui-iconfont c-error">&#xe706;</i> </button>';
            	    $('#'+file.id).append(ss);
            	    
            	    $('.file-del','#logoList').click(function(){
            			var parent = $(this).parent();
                 			parent.remove();
                 	});
            	    
            	});

            	// 文件上传失败，显示上传出错。
            	uploader.on( 'uploadError', function( file ) {
            	    var $li = $( '#'+file.id ),
            	        $error = $li.find('div.error');

            	    // 避免重复创建
            	    if ( !$error.length ) {
            	        $error = $('<div class="error"></div>').appendTo( $li );
            	    }

            	    $error.text('上传失败');
            	});

            	// 完成上传完了，成功或者失败，先删除进度条。
            	uploader.on( 'uploadComplete', function( file ) {
            	    $( '#'+file.id ).find('.progress').remove();
            	});
        	}
        	
        	initLogo();
        	
    });
