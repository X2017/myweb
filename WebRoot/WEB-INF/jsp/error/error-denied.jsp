<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>
<html>
<head>
<title>无权限访问此页面--耐奇电气-电力信息与综合服务云平台</title>
<body>
	<div class="panel">
    <%
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        pageContext.setAttribute("statusCode", statusCode);

        String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
        String queryString = request.getQueryString();
        String url = uri + (queryString == null || queryString.length() == 0 ? "" : "?" + queryString);
        pageContext.setAttribute("url", url);

        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        request.setAttribute("exception", exception);
    %>
    <c:if test="${statusCode eq 404}">
        <h3 style='display:inline;'>页面没有找到！</h3><br/>对不起，暂时没有找到您所访问的页面地址,请联系管理员解决此问题！&nbsp;&nbsp;&nbsp;&nbsp;
    </c:if>

    <c:if test="${statusCode ne 404}">
        <h3 style='display:inline;'>服务器程序出问题了！</h3><br/>对不起,您访问的页面出了一点内部小问题，刷新重新访问或先去别的页面转转,过会再来吧~！&nbsp;&nbsp;&nbsp;&nbsp;
        <c:if test="${not empty exception}">
            <%
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                exception.printStackTrace(printWriter);
                pageContext.setAttribute("stackTrace", stringWriter.toString());
            %>
        </c:if>
    </c:if>
	</div>
</body>
</html>