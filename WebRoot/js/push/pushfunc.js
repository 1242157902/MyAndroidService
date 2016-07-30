function serializeForm(form){
	   var obj = {};
	   $.each(form.serializeArray(),function(index){
					if(obj[this['name']]){
						obj[this['name']] = obj[this['name']] + ','+this['value'];
					} else {
						obj[this['name']] =this['value'];
					}
		});
		return obj;
}
 

function grayColor(hex){ 
	r=parseInt(hex.substr(0,2),16) / 255;
	g=parseInt(hex.substr(2,2),16) / 255;
	b=parseInt(hex.substr(4,2),16) / 255;
	return ((0.213 * r +	0.715 * g +	0.072 *b)< 0.5 ? '#FFF' : '#000');
} 

$(function() {
	$("#devicestatis").hide();$("#devicestatis").attr("title",1);	
	$("#slidestatis").hide();$("#slidestatis").attr("title",1);	
});

function deviceshowhide(){
	if($("#devicestatis").attr("title")==1)   {$("#devicestatis").show();$("#devicestatis").attr("title",0);}
	else  {$("#devicestatis").hide();$("#devicestatis").attr("title",1);}
}
function slideshowhide(){
	if($("#slidestatis").attr("title")==1)   {$("#slidestatis").show();$("#slidestatis").attr("title",0);}
	else  {$("#slidestatis").hide();$("#slidestatis").attr("title",1);}
}