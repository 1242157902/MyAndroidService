function pushway(v)
{
	switch(v)
	{
	case 0:
	  return '即时推送';//v取空时返回0
	  break;
	case 1:
	  return '定时推送';
	  break;
	default:
	  return '错误';
	}
}

function adshowform(namex)
{
	var suffix = namex.split('.');
	switch(suffix[1])
	{
	case 'jpg':
	  return '图片';
	  break;
	case 'bmp':
	  return '图片';
	  break;
	case 'png':
	  return '图片';
	  break;
	case 'gif':
	  return '动态图片';
	  break;
	default:
	  return '未知格式';
	}
}

function enGender(v)
{
	if(v==1||v=='1'||v=='男') return '男';
	else if(v==2||v=='2'||v=='女')  return '女';
	else return '';
}

function enPushOper(v)
{
	if(v==1||v=='1') return '推送';
	else if(v==2||v=='2')  return '取消';
	else return '错误';
}

function enRcvType(v)
{
	if(v=='default') return '默认序列';
	else if(v=='common')  return '普推序列';
	else if(v=='special')  return '特推序列';
	else return '错误';
}
function enAge(v)
{
	if(v.match(/^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/g)==null) return '';
	var birth = v.split('-');
	var birthyear=birth[0];
	var myDate = new Date();	
	var nowyear=myDate.getFullYear();
	return nowyear-birthyear;
}
function enMbos(v)
{
	switch(v)
	{
	case 1:
		  return 'Andriod';
		  break;
	case 2:
		  return 'IOS';
		  break;
	default:
	  return '未知系统';
	}
}
function enProvider(v)
{
	switch(v)
	{
	case '1183':
	  return '移动183卡';
	  break;
	default:
	  return '未知运营商';
	}
}
function enArea(v)
{
	switch(v)
	{
	case '0101':
	      return '北京';
	      break;
	case '0201':
		  return '河北 石家庄';
		  break;
	case '0202':
		  return '河北 唐山';
		  break;
	case '0203':
		  return '河北 秦皇岛';
		  break;
	case '0204':
		  return '河北 邯郸';
		  break;
	case '0205':
		  return '河北 邢台';
		  break;
	case '0206':
		  return '河北 保定';
		  break;
	case '0207':
		  return '河北 张家口';
		  break;
	case '0208':
		  return '河北 承德';
		  break;
	case '0209':
		  return '河北 沧州';
		  break;
	case '0210':
		  return '河北 廊坊';
		  break;
	case '0211':
		  return '河北 衡水';
		  break;
	default:
	      return '未知归属地';
	}
}
function enMbtype(v)
{
	var str='未知品牌';
	var str2='未知型号';
	var brand=v.substring(0,2);
	var type=v.substring(2);
	switch(brand)
	{
	case '01':
		  str='苹果';
		  switch(type)
			{
			case '0001':
				  str2='iphone4';
				  break;
			case '0002':
				  str2='iphone4s';
				  break;
			case '0003':
				  str2='iphone5';
				  break;
			case '0004':
				  str2='iphone5c';
				  break;
			case '0005':
				  str2='iphone5s';
				  break;
			case '0006':
				  str2='iphone6';
				  break;
			case '0007':
				  str2='iphone6 plus';
				  break;
			default:
			      str2='未知型号';
			      break;
			};
		  break;
	case '02':
		  str='小米';
		  break;
	case '03':
		  str='三星';
		  break;
	case '04':
		  str='华为';
		  break;
	case '05':
		  str='酷派';
		  switch(type)
			{
			case '0001':
				  str2='Coolpad 8705';
				  break;
			case '0002':
				  str2='Coolpad 8765';
				  break;
			default:
			      str2='未知型号';
		          break;
			}
		  break;
	case '06':
		  str='联想';
		  break;
	case '07':
		  str='索尼';
		  break;
	case '08':
		  str='诺基亚';
		  break;
	case '09':
		  str='魅族';
		  break;
	case '10':
		  str='HTC';
		  break;
	case '11':
		  str='Vivo';
		  break;
	case '12':
		  str='中兴';
		  break;
	case '13':
		  str='Oppo';
		  break;
	case '14':
		  str='LG';
		  break;
	case '15':
		  str='金立';
		  break;
	case '16':
		  str='朵唯';
		  break;
	default:
	      str='未知品牌'; 
	      break;
	}
	return str+" "+str2;
}
function enPhstatus(v)
{  
	switch(v)
	{
	case '0':
		  return '未推送';
		  break;
	case '1':
		  return '已推送';
		  break;
	default:
	  return '错误';
	}
}
function enRecstatus(v)
{
	switch(v)
	{
	case '0':
		  return '未接收';
		  break;
	case '1':
		  return '已接收';
		  break;
	default:
	      return '错误';
	}
}
function enPhoption(phstatus,imei)
{
	//if(imei.match(/^[A-Za-z0-9]{15}$/g)==null) return '<a href="javascript:" style="font:12px;">无效</a>';
	switch(phstatus)
	{
	case '0':
		  return '<a href="javascript:" onclick="singleokpush(\''+ imei+'\');" style="font:12px;">推送</a>';
		  break;
	case '1':
		  return '<a href="javascript:" onclick="singlecancelpush(\''+ imei+'\');" style="font:12px;">取消</a>';
		  break;
	default:
	      return '<a href="javascript:" style="font:12px;">无效</a>';
	}
}
function enAreaCheckbox(v)
{
	var areaTxt="";
	switch(v)
	{
	case '01':
		  areaTxt+= '<input type=checkbox value="0101" name=mbarea /><span>北京</span><br/>';
	      break;
	case '02':
		  areaTxt+='<input type=checkbox value="0201" name=mbarea /><span>石家庄</span><br/>';
		  areaTxt+='<input type=checkbox value="0202" name=mbarea /><span>唐山</span><br/>';
		  areaTxt+='<input type=checkbox value="0203" name=mbarea /><span>秦皇岛</span><br/>';
		  areaTxt+='<input type=checkbox value="0204" name=mbarea /><span>邯郸</span><br/>';
		  areaTxt+='<input type=checkbox value="0205" name=mbarea /><span>邢台</span><br/>';
		  areaTxt+='<input type=checkbox value="0206" name=mbarea /><span>保定</span><br/>';
		  areaTxt+='<input type=checkbox value="0207" name=mbarea /><span>张家口</span><br/>';
		  areaTxt+='<input type=checkbox value="0208" name=mbarea /><span>承德</span><br/>';
		  areaTxt+='<input type=checkbox value="0209" name=mbarea /><span>沧州</span><br/>';
		  areaTxt+='<input type=checkbox value="0210" name=mbarea /><span>廊坊</span><br/>';
		  areaTxt+='<input type=checkbox value="0211" name=mbarea /><span>衡水</span><br/>';
		  break;
	default:
          areaTxt+='<input type=checkbox value="0000" name=mbarea /><span>未知归属地</span><br/>';
	      break;
	}
	return  areaTxt;
}

function enMBTheme(v)
{
	if(v==0||v=='0') return '主题模式';
	else if(v==1||v=='1')  return '解锁模式';
	else return '错误';
}

function enMBThemeOpersrc(v)
{
	if(v=='weixin') return '微信端';
	else if(v=='client')  return '手机客户端';
	else if(v=='single')  return '服务器单改';
	else if(v=='batch')  return '服务器批量';
	else return '错误';
}