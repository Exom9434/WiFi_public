<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="WiFi_public.WifiDao" %>
<%
    String idParam = request.getParameter("id");

    if (idParam != null) {
        try {
            int id = Integer.parseInt(idParam);
            WifiDao.deleteLocation(id);  // WifiDao에서 삭제 기능을 호출
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "올바르지 않은 ID 값입니다.");
            return;
        }
    } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID 값이 필요합니다.");
        return;
    }

    response.sendRedirect("history.jsp");
%>
