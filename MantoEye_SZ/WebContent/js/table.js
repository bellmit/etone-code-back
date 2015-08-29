function jumpPage(pageNo){
		$("#pageNo").val(pageNo);
		document.forms[0].submit();
	}
	
function sort(orderBy,defaultOrder){
	if($("#orderBy").val()==orderBy){
			if($("#order").val()==""){
			$("#order").val(defaultOrder);}
			else if($("#order").val()=="desc"){
			$("#order").val("asc");}
			else if($("#order").val()=="asc"){
			$("#order").val("desc");}
		}
		else{
			$("#orderBy").val(orderBy);
			$("#order").val(defaultOrder);
		}
	document.forms[0].submit();
	}
function changePageSize(){

	$("#pageNo").val(1);
	document.forms[0].submit();
}
function jumpToPage(pageNo){
	if(pageNo <0  ||  pageNo!=parseInt(pageNo) ){
		pageNo = 1;

		//return;
	}
	$("#pageNo").val(pageNo);
	document.forms[0].submit();
}
function goBack(url){
	 document.forms[0].action= url;
	 document.forms[0].submit();
}
