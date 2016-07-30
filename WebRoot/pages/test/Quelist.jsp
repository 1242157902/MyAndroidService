<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic ProgressBar - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.2.6/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.2.6/themes/icon.css" />
    <link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.2.6/demo/demo.css" />
    <script type="text/javascript"	src="js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
    <script type="text/javascript"	src="js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
</head>
<body>
   <h2>Basic ProgressBar</h2>
   <p>Click the button below to show progress information.</p>
   <div style="margin:20px 0;">
       <a href="#" class="easyui-linkbutton" onclick="start()">Start</a>
   </div>
  <div id="p" class="easyui-progressbar" style="width:400px;"></div>
   <script>
     function start(){
          var value = $('#p').progressbar('getValue');
          if (value < 100){
              value += Math.floor(Math.random() * 10);2
            $('#p').progressbar('setValue', value);
              setTimeout(arguments.callee, 200);
          }
        };
    </script>
    </body>
</html>