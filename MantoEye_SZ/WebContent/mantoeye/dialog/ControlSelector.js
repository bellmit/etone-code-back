////////////////////////////////APN选择框////////////////////////////////////

String.prototype.trim = function () {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
function searchTitle(id){
	var inputValue=$("#"+id+"_title_search").val();
	if(inputValue.trim()!=""){
		$("#"+id+"_select option").each(function(){
			$(this).remove();
		});		
		$("#"+id+"_hide_select option").each(function(){
				if(this.text.indexOf(inputValue)>=0){
					$("#"+id+"_select").append("<option value='" +this.value + "'>" + this.text + "</option");
					
				}
		});
	}
}
function showDialog(id){
	$('#'+id+'_title_dialog').css({'display':'block'});	
}   
function confirmDialog(id){
	$('#'+id+'_title').attr('value',$("#"+id+"_select option:selected").val());
	$('#'+id+'_title_dialog').css({'display':'none'});
}
function closeDialog(id){
	$('#'+id+'_title_dialog').css({'display':'none'});
}
function confirmDialog1(id){
	$('#'+id+'_title').attr('value',$("#"+id+"_select option:selected").attr("text"));
	$('#'+id+'_title_dialog').css({'display':'none'});
}
