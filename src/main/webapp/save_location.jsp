<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String xPosParam = request.getParameter("x_pos"); // x 좌표
    String yPosParam = request.getParameter("y_pos"); // y 좌표

    if (xPosParam != null && yPosParam != null) {
        try {
            float xPos = Float.parseFloat(xPosParam);
            float yPos = Float.parseFloat(yPosParam);

            request.setAttribute("x_pos", xPos);
            request.setAttribute("y_pos", yPos);

            // WifiServlet을 호출하여 위치 저장
            request.getRequestDispatcher("/wifi").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "올바르지 않은 좌표 값입니다.");
        }
    } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "x_pos 및 y_pos 값이 필요합니다.");
    }
%>
