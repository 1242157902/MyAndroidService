$(function(){
		$('a[pushtitle]').click(function() {
			var src = $(this).attr('pushtitle');
			var title =  $(this).html();
			if ($('#tt').tabs('exists', title)) {
				$('#tt').tabs('select', title);
			} else {
				$('#tt').tabs('add',
				{
					title :title,
					href :src,
					closable : true
				});
			}
		});
		$('#tt').tabs(
		{
			onClose: function(mytab) {
			  var thetitle=mytab.substring(mytab.length-4,mytab.length-2);
			  if(thetitle=="内容"||thetitle=="序列"||thetitle=="推送"){
			  var re = eval("/"+thetitle+"/");
			  var tabs = $('#tt').tabs('tabs');  
			  var ttab = new Array();
			  for(var j=0;j<tabs.length;j++){  
		          var x = tabs[j].panel('options').title; 
		          if(re.test(x)) {
		        	ttab.push(x);
		          }
		      }
			  for(var i=0;i<ttab.length;i++)
			  {  $("#tt").tabs('close',ttab[i]);}
			  
			  }
			}
		});
});