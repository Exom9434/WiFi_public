<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.db.LocationHistoryDAO" %>
<%
    float lat = Float.parseFloat(request.getParameter("lat"));
    float lon = Float.parseFloat(request.getParameter("lon"));

    LocationHistoryDAO.saveLocation(lat, lon);
    response.sendRedirect("history.jsp");
%>
