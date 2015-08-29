<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {

	});

	function addTab(opts) {
		if (opts.href) {
			var centerTabs = $('#index_centerTabs');
			if (centerTabs.tabs('exists', opts.title))
				centerTabs.tabs('select', opts.title);
			else
				centerTabs.tabs('add', opts);
		}
	}
</script>

<div id="index_centerTabs" class="easyui-tabs"
	data-options="border:false,fit:true">
	<div title="首頁">放假盧卡斯</div>
</div>