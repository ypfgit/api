function TreeCombo(name, hiddenName, renderId, treeId,url,initValue,initName,placeHolder) {
	this.name = name;
	this.hiddenName = hiddenName;
	this.renderId = renderId;
	this.treeId = treeId;
	this.url = url;
	this.initValue=initValue||'';
	this.initName=initName||'';
	this.placeHolder = placeHolder||'请选择...';
	var that = this;

	/*
	 * 设备类型下拉树的设置
	 */
	this.treeSetting = {
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parent_id"
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(that.treeId);
				nodes = zTree.getSelectedNodes();
				$("#J_" + that.hiddenName).val(nodes[0].id);
				$("#J_" + that.name).val(nodes[0].name);
			}
		}
	};

	var html = '<input id="J_'
			+ this.name
			+ '" class="input-text" placeHolder="'+this.placeHolder+'" value="'+this.initName+'" name="'
			+ this.name
			+ '" readonly="readonly">'
			+ '<input id="J_'
			+ this.hiddenName
			+ '" type="hidden" value="'
			+ this.initValue
			+ '" name="'
			+ this.hiddenName
			+ '">'
			+ '<div id="'
			+ this.treeId
			+ 'Div" class="hide" style="position: absolute;z-index:1000; border: 1px #CCC solid; background-color: #F0F6E4;">'
			+ '   <ul id="' + this.treeId
			+ '" class="ztree" style="margin-top:0;"></ul> ' + '</div> ';

	$('#' + renderId).append(html);
	
	
	$('#J_'+name).click(function() {
		$.ajax({
			url : that.url,
			type : 'POST',
			data : {},
			async : false,
			success : function(data) {
				var zTree = $.fn.zTree.init($("#" + that.treeId), that.treeSetting,
						data);

				zTree.expandAll(true);

				var deptObj = $("#J_" + that.name);
				var deptOffset = $("#J_" + that.name).offset();
				$("#" + that.treeId + "Div").css({
					left : "15px",
					top : "30px"
				}).slideDown("fast");

				$('#' + that.treeId).css({
					width : deptObj.outerWidth() - 12 + "px"
				});
				var node = zTree.getNodeByParam("id", $('#' + that.hiddenName).val(),
						null)
				zTree.selectNode(node);

				$("body").bind("mousedown", function(event){
					if (event.target.id.indexOf('switch') == -1) {
						$("#"+that.treeId+"Div").fadeOut("fast");
						$("body").unbind("mousedown");
					}
				});
			}
		});
    });
}

/*
 * 下拉树的点击事件
 */
/*TreeCombo.prototype.treeOnClick = function(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(this.treeId);
	nodes = zTree.getSelectedNodes();
	$("#J_" + this.hiddenName).val(nodes[0].id);
	$("#J_" + this.name).val(nodes[0].name);
}*/

/*
 * 展示下拉树
 */
TreeCombo.prototype.showTree = function() {
	var that = this;
	
}
