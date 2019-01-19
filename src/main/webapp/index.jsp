<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
</head>
<script type="text/javascript">
    console.log("<%=request.getContextPath()%>/index");
    top.location.href = "<%=request.getContextPath()%>/index";//javascript页面跳转，防止页面嵌套，直接跳到最顶部窗口
</script>
<body>
</body>
</html>
