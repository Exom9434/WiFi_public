<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.db.LocationHistoryDAO" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    LocationHistoryDAO.deleteLocation(id);
    response.sendRedirect("history.jsp");
%>
