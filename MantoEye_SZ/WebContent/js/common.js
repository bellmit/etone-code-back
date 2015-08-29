 
 	var dayOrHour='day';
	function preStartDate(){
		if(dayOrHour=='hour'){
			new WdatePicker({maxDate:'#F{$dp.$D(\'d_searchDateEnd\')}',dateFmt:'yyyy-MM-dd HH:00'});
		}else if(dayOrHour=='month'){
			new WdatePicker({maxDate:'#F{$dp.$D(\'d_searchDateEnd\')}',dateFmt:'yyyy-MM'});
		}else if(dayOrHour=='day') {
			new WdatePicker({maxDate:'#F{$dp.$D(\'d_searchDateEnd\')}',dateFmt:'yyyy-MM-dd'});
		}else if(dayOrHour=='week'){
			new WdatePicker({maxDate:'#F{$dp.$D(\'d_searchDateEnd\')}',dateFmt:'yyyy-MM-dd'});
		}
	}
	function preStartDateByType(type){
		dayOrHour = type;
		if(dayOrHour=='hour'){
			new WdatePicker({maxDate:'#F{$dp.$D(\'d_searchDateEnd\')}',dateFmt:'yyyy-MM-dd HH:00'});
		}else if(dayOrHour=='month'){
			new WdatePicker({maxDate:'#F{$dp.$D(\'d_searchDateEnd\')}',dateFmt:'yyyy-MM'});
		}else if(dayOrHour=='day') {
			new WdatePicker({maxDate:'#F{$dp.$D(\'d_searchDateEnd\')}',dateFmt:'yyyy-MM-dd'});
		}else if(dayOrHour=='week'){
			new WdatePicker({maxDate:'#F{$dp.$D(\'d_searchDateEnd\')}',dateFmt:'yyyy-MM-dd'});
		}
	}
	
	function preEndDate(){
		if(dayOrHour=='hour'){
			new WdatePicker({minDate:'#F{$dp.$D(\'d_searchDateStart\')}',dateFmt:'yyyy-MM-dd HH:00'});
		}else if(dayOrHour=='month'){
			new WdatePicker({minDate:'#F{$dp.$D(\'d_searchDateStart\')}',dateFmt:'yyyy-MM'});
		}else if(dayOrHour=='day') {
			new WdatePicker({minDate:'#F{$dp.$D(\'d_searchDateStart\')}',dateFmt:'yyyy-MM-dd'});
		}else if(dayOrHour=='week'){
			new WdatePicker({minDate:'#F{$dp.$D(\'d_searchDateStart\')}',dateFmt:'yyyy-MM-dd'});
		}
	}
	
	<!--function selChange(){-->
		<!--var timeSel=document.getElementById('timeSel');-->
		<!--var selIndex=timeSel.options.selectedIndex;-->
		<!--if(timeSel.options[selIndex].value=='hour'){-->
		<!--		dayOrHour='hour';-->
		<!--}else {-->
		<!--	  dayOrHour='day';-->
		<!--}-->
		
		<!--document.getElementById("start").value='';-->
		<!--document.getElementById("end").value='';-->
	<!--}-->
	
	
	
	function selChange(){
		var timeSel=document.getElementById('d_timeLevel');
		var selIndex=timeSel.options.selectedIndex;
		if(timeSel.options[selIndex].value=='2'){
				dayOrHour='day';
		}else if(timeSel.options[selIndex].value=='4'){
			  dayOrHour='month';
		}else if(timeSel.options[selIndex].value=='1'){
			  dayOrHour='hour';
		}else if(timeSel.options[selIndex].value=='3'){
			 dayOrHour='week';
		}
		document.getElementById("d_searchDateStart").value='';
		document.getElementById("d_searchDateEnd").value='';
	}
 