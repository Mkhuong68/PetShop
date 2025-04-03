/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAOs.CustomerOrderDAO;
import DAOs.DeliveryInformationDAO;
import DB.DBConnection;
import Model.Account;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author tvhun
 */
public class DeliveryTrackingController extends HttpServlet {

    private DeliveryInformationDAO deliveryDAO;

    @Override
    public void init() throws ServletException {
        deliveryDAO = new DeliveryInformationDAO();
    }

    private String getGoongApiKey() {
        String apiKey = getServletConfig().getInitParameter("goongApiKey");
        // Kiểm tra giá trị apiKey từ web.xml
        if (apiKey == null || apiKey.isEmpty() || "your_goong_api_key".equals(apiKey)) {
            // Sử dụng key hardcode làm giá trị mặc định nếu không có trong cấu hình
            return "6gUMlPS65Jqh7BBm14lAR0zpPSK4CXvY34EXMKfx";
        }
        return apiKey;
    }
    
    private String getGoongMaptilesKey() {
        String maptilesKey = getServletConfig().getInitParameter("goongMaptilesKey");
        // Kiểm tra giá trị maptilesKey từ web.xml
        if (maptilesKey == null || maptilesKey.isEmpty() || "your_goong_maptiles_key".equals(maptilesKey)) {
            // Sử dụng key hardcode làm giá trị mặc định nếu không có trong cấu hình
            return "54pNo1msWtTacmtTVOFLYWJKLEawnb9AZxqNDsmA";
        }
        return maptilesKey;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Kiểm tra xác thực người dùng
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");

            if (account == null || account.getRoleId() != 2) {
                response.sendRedirect("login");
                return;
            }

            // Lấy delivery ID từ request
            String deliveryIdStr = request.getParameter("deliveryId");
            if (deliveryIdStr == null || deliveryIdStr.isEmpty()) {
                response.sendRedirect("deliveryList");
                return;
            }
            
            int deliveryId = Integer.parseInt(deliveryIdStr);
            
            // Lấy thông tin tracking
            Map<String, Object> trackingInfo = this.deliveryDAO.getDeliveryTrackingInfo(deliveryId);
            
            if (trackingInfo == null) {
                response.sendRedirect("deliveryList?error=invalid_delivery");
                return;
            }
            
            // Kiểm tra route_id và lấy các điểm trên tuyến đường nếu có
            int routeId = 0;
            if (trackingInfo.get("routeId") != null) {
                routeId = ((Number) trackingInfo.get("routeId")).intValue();
            }
            
            List<Map<String, Object>> routePoints = new ArrayList<>();
            
            if (routeId > 0) {
                routePoints = this.deliveryDAO.getRoutePoints(routeId);
            }
            
            // Thiết lập attributes cho JSP
            request.setAttribute("trackingInfo", trackingInfo);
            request.setAttribute("routePoints", routePoints);
            request.setAttribute("maptilesKey", getGoongMaptilesKey());
            request.setAttribute("apiKey", getGoongApiKey());
            
            // Forward đến JSP
            request.getRequestDispatcher("/delivery-tracking.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            System.err.println("Lỗi định dạng số: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("deliveryList?error=invalid_format");
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("deliveryList?error=server_error");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Kiểm tra xác thực người dùng
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");

            if (account == null || account.getRoleId() != 2) {
                response.getWriter().write("{\"success\": false, \"message\": \"Không có quyền truy cập\"}");
                return;
            }

            // Lấy các tham số từ request
            String action = request.getParameter("action");
            String deliveryIdStr = request.getParameter("deliveryId");

            if (action == null || deliveryIdStr == null) {
                response.getWriter().write("{\"success\": false, \"message\": \"Thiếu tham số\"}");
                return;
            }

            int deliveryId = Integer.parseInt(deliveryIdStr);

            // Xử lý các hành động
            if ("updateLocation".equals(action)) {
                // Cập nhật vị trí hiện tại
                String latStr = request.getParameter("latitude");
                String lngStr = request.getParameter("longitude");
                String silentUpdate = request.getParameter("silent");
                boolean isSilent = "true".equals(silentUpdate);

                if (latStr == null || lngStr == null) {
                    response.getWriter().write("{\"success\": false, \"message\": \"Thiếu tham số vị trí\"}");
                    return;
                }

                double latitude = Double.parseDouble(latStr);
                double longitude = Double.parseDouble(lngStr);

                // Kiểm tra tọa độ có hợp lệ không (nằm trong Việt Nam)
                if (latitude < 8.0 || latitude > 24.0 || longitude < 102.0 || longitude > 110.0) {
                    response.getWriter().write("{\"success\": false, \"message\": \"Tọa độ không hợp lệ\"}");
                    return;
                }

                boolean updated = this.deliveryDAO.updateCurrentLocation(deliveryId, latitude, longitude);

                if (updated) {
                    // Thêm điểm mới vào tuyến đường nếu có route_id
                    Map<String, Object> trackingInfo = this.deliveryDAO.getDeliveryTrackingInfo(deliveryId);
                    if (trackingInfo != null && trackingInfo.get("routeId") != null) {
                        int routeId = ((Number) trackingInfo.get("routeId")).intValue();
                        if (routeId > 0) {
                            // Thêm điểm mới vào tuyến đường
                            this.deliveryDAO.addRoutePoint(routeId, latitude, longitude);
                        }
                    }
                    
                    // Kiểm tra và cập nhật tọa độ đích nếu có
                    String destLatStr = request.getParameter("destLatitude");
                    String destLngStr = request.getParameter("destLongitude");
                    
                    if (destLatStr != null && destLngStr != null) {
                        try {
                            double destLatitude = Double.parseDouble(destLatStr);
                            double destLongitude = Double.parseDouble(destLngStr);
                            
                            // Lưu tọa độ đích vào database
                            this.deliveryDAO.cacheDestinationCoordinates(deliveryId, destLatitude, destLongitude);
                        } catch (NumberFormatException e) {
                            // Bỏ qua lỗi này, không chặn luồng chính
                            System.err.println("Lỗi khi parse tọa độ đích: " + e.getMessage());
                        }
                    }
                    
                    // Lấy giới hạn tốc độ nếu cần
                    Map<String, Object> response_data = new java.util.HashMap<>();
                    response_data.put("success", true);
                    response_data.put("message", isSilent ? "OK" : "Vị trí đã được cập nhật thành công");
                    
                    // Lấy thông tin giới hạn tốc độ từ API
                    try {
                        Map<String, Object> speedInfo = this.deliveryDAO.getSpeedLimit(latitude, longitude, getGoongApiKey());
                        if (speedInfo != null && !speedInfo.isEmpty()) {
                            response_data.put("speed_info", speedInfo);
                        }
                    } catch (Exception e) {
                        System.err.println("Lỗi khi lấy thông tin giới hạn tốc độ: " + e.getMessage());
                    }
                    
                    // Chuyển Map thành JSON String
                    String jsonResponse = "{";
                    for (Map.Entry<String, Object> entry : response_data.entrySet()) {
                        jsonResponse += "\"" + entry.getKey() + "\":";
                        if (entry.getValue() instanceof String) {
                            jsonResponse += "\"" + entry.getValue() + "\",";
                        } else if (entry.getValue() instanceof Boolean) {
                            jsonResponse += entry.getValue() + ",";
                        } else if (entry.getValue() instanceof Map) {
                            jsonResponse += mapToJsonString((Map<String, Object>) entry.getValue()) + ",";
                        } else {
                            jsonResponse += "\"" + entry.getValue() + "\",";
                        }
                    }
                    
                    // Xóa dấu phẩy cuối cùng nếu có
                    if (jsonResponse.endsWith(",")) {
                        jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 1);
                    }
                    jsonResponse += "}";
                    
                    response.getWriter().write(jsonResponse);
                } else {
                    response.getWriter().write("{\"success\": false, \"message\": \"Không thể cập nhật vị trí\"}");
                }

            } else if ("markDelivered".equals(action)) {
                // Đánh dấu đã giao hàng và cập nhật vị trí cuối cùng nếu có
                String latStr = request.getParameter("latitude");
                String lngStr = request.getParameter("longitude");
                
                // Cập nhật vị trí nếu có
                if (latStr != null && lngStr != null) {
                    try {
                        double latitude = Double.parseDouble(latStr);
                        double longitude = Double.parseDouble(lngStr);
                        this.deliveryDAO.updateCurrentLocation(deliveryId, latitude, longitude);
                        
                        // Thêm điểm cuối cùng vào tuyến đường
                        Map<String, Object> trackingInfo = this.deliveryDAO.getDeliveryTrackingInfo(deliveryId);
                        if (trackingInfo != null && trackingInfo.get("routeId") != null) {
                            int routeId = ((Number) trackingInfo.get("routeId")).intValue();
                            if (routeId > 0) {
                                this.deliveryDAO.addRoutePoint(routeId, latitude, longitude);
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Bỏ qua lỗi vị trí, vẫn tiếp tục cập nhật trạng thái
                        System.err.println("Lỗi định dạng tọa độ khi đánh dấu đã giao: " + e.getMessage());
                    }
                }
                
                // Cập nhật trạng thái
                Map<String, String> result = this.deliveryDAO.updateDeliveryStatus(deliveryId, 5); // 5 = Delivered

                if ("true".equals(result.get("success"))) {
                    response.getWriter().write("{\"success\": true, \"message\": \"" + result.get("message") + "\"}");
                } else {
                    response.getWriter().write("{\"success\": false, \"message\": \"" + result.get("error") + "\"}");
                }
            } else if ("getDirections".equals(action)) {
                // API để lấy thông tin tuyến đường
                String startLatStr = request.getParameter("startLat");
                String startLngStr = request.getParameter("startLng");
                String endLatStr = request.getParameter("endLat");
                String endLngStr = request.getParameter("endLng");
                
                if (startLatStr == null || startLngStr == null || endLatStr == null || endLngStr == null) {
                    response.getWriter().write("{\"success\": false, \"message\": \"Thiếu tham số tọa độ\"}");
                    return;
                }
                
                try {
                    double startLat = Double.parseDouble(startLatStr);
                    double startLng = Double.parseDouble(startLngStr);
                    double endLat = Double.parseDouble(endLatStr);
                    double endLng = Double.parseDouble(endLngStr);
                    
                    // Lấy thông tin tuyến đường
                    Map<String, Object> directions = this.deliveryDAO.getDirections(
                        startLat, startLng, endLat, endLng, getGoongApiKey());
                    
                    if (directions != null && directions.containsKey("json")) {
                        // Lấy luôn thông tin khoảng cách và thời gian
                        Map<String, Object> distanceMatrix = this.deliveryDAO.getDistanceMatrix(
                            startLat, startLng, endLat, endLng, getGoongApiKey());
                        
                        String jsonResponse = "{\"success\": true, \"directions\": " + directions.get("json");
                        
                        if (distanceMatrix != null && distanceMatrix.containsKey("json")) {
                            jsonResponse += ", \"distance_matrix\": " + distanceMatrix.get("json");
                        }
                        
                        jsonResponse += "}";
                        response.getWriter().write(jsonResponse);
                    } else {
                        response.getWriter().write("{\"success\": false, \"message\": \"Không thể lấy thông tin tuyến đường\"}");
                    }
                } catch (NumberFormatException e) {
                    response.getWriter().write("{\"success\": false, \"message\": \"Định dạng tọa độ không hợp lệ\"}");
                } catch (Exception e) {
                    response.getWriter().write("{\"success\": false, \"message\": \"Lỗi khi lấy thông tin tuyến đường: " + e.getMessage() + "\"}");
                }
            } else if ("geocode".equals(action)) {
                // API để geocode địa chỉ
                String address = request.getParameter("address");
                
                if (address == null || address.trim().isEmpty()) {
                    response.getWriter().write("{\"success\": false, \"message\": \"Thiếu địa chỉ\"}");
                    return;
                }
                
                try {
                    // Geocode địa chỉ
                    Map<String, Object> geocodeResult = this.deliveryDAO.geocodeAddress(address, getGoongApiKey());
                    
                    if (geocodeResult != null && geocodeResult.containsKey("lat") && geocodeResult.containsKey("lng")) {
                        // Nếu geocode thành công, lưu tọa độ vào database nếu có deliveryId
                        if (deliveryId > 0) {
                            double lat = (double) geocodeResult.get("lat");
                            double lng = (double) geocodeResult.get("lng");
                            this.deliveryDAO.cacheDestinationCoordinates(deliveryId, lat, lng);
                        }
                        
                        // Chuyển Map thành JSON String
                        String jsonResponse = mapToJsonString(geocodeResult);
                        response.getWriter().write("{\"success\": true, \"geocode\": " + jsonResponse + "}");
                    } else {
                        response.getWriter().write("{\"success\": false, \"message\": \"Không thể geocode địa chỉ\"}");
                    }
                } catch (Exception e) {
                    response.getWriter().write("{\"success\": false, \"message\": \"Lỗi khi geocode địa chỉ: " + e.getMessage() + "\"}");
                }
            } else if ("sendCustomerNotification".equals(action)) {
                try {
                    int orderId = Integer.parseInt(request.getParameter("orderId"));
                    deliveryId = Integer.parseInt(request.getParameter("deliveryId"));
                    String message = request.getParameter("message");
                    
                    if (message == null || message.trim().isEmpty()) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"success\": false, \"message\": \"Notification message cannot be empty\"}");
                        return;
                    }
                    
                    CustomerOrderDAO orderDAO = new CustomerOrderDAO();
                    boolean isValidOrderId = orderDAO.checkOrderExists(orderId);
                    
                    if (!isValidOrderId) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"success\": false, \"message\": \"Invalid order ID\"}");
                        return;
                    }
                    
                    // Lưu thông báo vào bảng Notifications - sử dụng thông báo ngắn gọn
                    try {
                        // Tạo một thông báo ngắn gọn hơn - tối đa 100 ký tự
                        String notificationPrefix = "Order #" + orderId + ": ";
                        int maxMessageLength = 100 - notificationPrefix.length();
                        
                        // Cắt thông báo nếu dài hơn độ dài tối đa
                        String shortMessage = message;
                        if (message.length() > maxMessageLength) {
                            shortMessage = message.substring(0, maxMessageLength - 3) + "...";
                        }
                        
                        // Sử dụng DeliveryInformationDAO để gửi thông báo 
                        DeliveryInformationDAO deliveryDAO = new DeliveryInformationDAO();
                        boolean notificationSent = deliveryDAO.sendCustomerNotification(orderId, notificationPrefix + shortMessage);
                        
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        
                        if (notificationSent) {
                            response.getWriter().write("{\"success\": true, \"message\": \"Notification sent successfully\", \"orderId\": " + orderId + ", \"deliveryId\": " + deliveryId + "}");
                        } else {
                            response.getWriter().write("{\"success\": false, \"message\": \"Failed to send notification - no customer found\"}");
                        }
                    } catch (Exception e) {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"success\": false, \"message\": \"Error sending notification: " + e.getMessage() + "\"}");
                    }
                } catch (NumberFormatException e) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"success\": false, \"message\": \"Invalid ID format: " + e.getMessage() + "\"}");
                }
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Hành động không hợp lệ\"}");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi định dạng số: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.getWriter().write("{\"success\": false, \"message\": \"Lỗi không xác định: " + e.getMessage() + "\"}");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    /**
     * Chuyển đổi Map thành JSON String đơn giản
     * @param map Map cần chuyển đổi
     * @return JSON String
     */
    private String mapToJsonString(Map<String, Object> map) {
        if (map == null) return "{}";
        
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":");
            
            Object value = entry.getValue();
            if (value instanceof String) {
                json.append("\"").append(value).append("\"");
            } else if (value instanceof Number || value instanceof Boolean) {
                json.append(value);
            } else if (value instanceof Map) {
                json.append(mapToJsonString((Map<String, Object>) value));
            } else if (value == null) {
                json.append("null");
            } else {
                json.append("\"").append(value).append("\"");
            }
            
            first = false;
        }
        
        json.append("}");
        return json.toString();
    }

}