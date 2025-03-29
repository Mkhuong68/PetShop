package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MoMoPaymentServlet extends HttpServlet {

    private static final String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Parameters
        String partnerCode = "MOMO";
        String accessKey = "F8BBA842ECF85";
        String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
        String requestId = partnerCode + System.currentTimeMillis();
        String orderId = requestId;
        String orderInfo = " Pay with MoMo";
        String redirectUrl = "http://localhost:8081/ConfirmCheckOut";
        String ipnUrl = redirectUrl;
        String totalAmount = (String) request.getSession().getAttribute("totalAmount");
        if (totalAmount != null) {
            int dotIndex = totalAmount.indexOf(".");
            if (dotIndex != -1) {
                totalAmount = totalAmount.substring(0, dotIndex);
            }
        }
        String requestType = "payWithATM";
        String e = request.getParameter("pickup");
        int takeAtStore = 0;
        if (e != null) {
            takeAtStore = 1;
        }
        String extraData = request.getParameter("discountCode") + "SLIPT" + takeAtStore;
        String rawSignature = "accessKey=" + accessKey + "&amount=" + totalAmount + "&extraData=" + extraData
                + "&ipnUrl=" + ipnUrl + "&orderId=" + orderId + "&orderInfo=" + orderInfo
                + "&partnerCode=" + partnerCode + "&redirectUrl=" + redirectUrl
                + "&requestId=" + requestId + "&requestType=" + requestType;
        String signature = hmacSHA256(secretKey, rawSignature);
        RequestBody requestBody = new RequestBody(partnerCode, accessKey, requestId, totalAmount, orderId, orderInfo,
                redirectUrl, ipnUrl, extraData, requestType, signature, "en");
        String jsonPayload = new Gson().toJson(requestBody);
        String jsonResponse = sendPostRequest(endpoint, jsonPayload);
        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
        if (jsonObject != null && jsonObject.has("payUrl")) {
            String payUrl = jsonObject.get("payUrl").getAsString();
            response.sendRedirect(payUrl);
        } else {
            response.sendRedirect("http://localhost:8081/Cart");
        }
    }

    private String hmacSHA256(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String sendPostRequest(String endpoint, String jsonInput) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        try ( OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        int responseCode = conn.getResponseCode();
        BufferedReader reader;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
        }
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = reader.readLine()) != null) {
            response.append(responseLine.trim());
        }
        return response.toString();
    }

    private static class RequestBody {

        String partnerCode;
        String accessKey;
        String requestId;
        String amount;
        String orderId;
        String orderInfo;
        String redirectUrl;
        String ipnUrl;
        String extraData;
        String requestType;
        String signature;
        String lang;

        public RequestBody(String partnerCode, String accessKey, String requestId, String amount, String orderId,
                String orderInfo, String redirectUrl, String ipnUrl, String extraData, String requestType,
                String signature, String lang) {
            this.partnerCode = partnerCode;
            this.accessKey = accessKey;
            this.requestId = requestId;
            this.amount = amount;
            this.orderId = orderId;
            this.orderInfo = orderInfo;
            this.redirectUrl = redirectUrl;
            this.ipnUrl = ipnUrl;
            this.extraData = extraData;
            this.requestType = requestType;
            this.signature = signature;
            this.lang = lang;
        }
    }
}
