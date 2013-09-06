<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link rel="stylesheet" href="${prefix!}?file=codemirror.css">
<link rel="stylesheet" href="${prefix!}?file=docs.css">
<script src="${prefix!}?file=codemirror.js"></script>
<script src="${prefix!}?file=groovy.js"></script>


<title>groovy shell</title>
</head>

<body>
<div id="inputbox">
  <textarea id="intext" name="intext" onchange="change()">${intext!}</textarea>
  <BUTTON onclick="sub()">submmit </BUTTON>
</div>

 <textarea id="outtext">
 	${outtext!}
 </textarea>
 
 
</body>
<STYLE >
	#inputbox{border:1px black; }
	#outtext{border:1px black; margin-top:30px;font-size:9px;font-color:blue;}
	
</STYLE>
<script type="text/javascript">
      var editor = CodeMirror.fromTextArea(document.getElementById("intext"), {
        lineNumbers: true,
        matchBrackets: true,
        mode: "text/x-groovy",
      });
	  
	  
	  var editor2 = CodeMirror.fromTextArea(document.getElementById("outtext"), {
        lineNumbers: false,
        matchBrackets: false,
        mode: "text/x-groovy",
		readOnly:true
      });
	  

	  
	  
	  function sub(){ 	
		url="${prefix!}?code="+encodeURIComponent(editor.getValue());
		window.location=url;
	  }
	  
	  editor2.setValue(editor2.getValue().replace(/^\s*/,''));
</script>
</html>
