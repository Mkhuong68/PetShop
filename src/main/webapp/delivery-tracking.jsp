<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Tracking - PetShop</title>

        <script src="https://cdn.jsdelivr.net/npm/@goongmaps/goong-js@1.0.9/dist/goong-js.js" defer></script>
        <link href="https://cdn.jsdelivr.net/npm/@goongmaps/goong-js@1.0.9/dist/goong-js.css" rel="stylesheet" />
 
        <script src='https://api.mapbox.com/mapbox-gl-js/v2.9.1/mapbox-gl.js' defer></script>
        <link href='https://api.mapbox.com/mapbox-gl-js/v2.9.1/mapbox-gl.css' rel='stylesheet' />

        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css" integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin=""/>
        <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js" defer></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

        <style>
            :root {
                --primary-color: #4CAF50;
                --secondary-color: #2196F3;
                --danger-color: #f44336;
                --success-color: #4CAF50;
                --warning-color: #ff9800;
                --light-color: #f1f1f1;
                --dark-color: #333;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f5f5f5;
                color: #333;
            }

            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
            }
            .tracking-error {
                background-color: #ffebee;
                border-left: 5px solid var(--danger-color);
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 4px;
                color: #d32f2f;
                font-weight: 500;
                display: flex;
                align-items: flex-start;
            }
            
            .tracking-error i {
                font-size: 24px;
                margin-right: 15px;
                margin-top: 2px;
            }
            
            .tracking-error p {
                margin: 5px 0;
            }

            .tracking-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
                background-color: white;
                padding: 15px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            .tracking-title {
                font-size: 24px;
                font-weight: bold;
                color: var(--dark-color);
            }

            .tracking-id {
                color: var(--secondary-color);
            }

            .status-badge {
                padding: 8px 15px;
                border-radius: 20px;
                font-weight: bold;
                color: white;
                background-color: var(--primary-color);
            }

            .status-pending {
                background-color: var(--warning-color);
            }
            .status-confirmed {
                background-color: #2196F3;
            }
            .status-packed {
                background-color: #9C27B0;
            }
            .status-delivering {
                background-color: #FF9800;
            }
            .status-delivered {
                background-color: var(--success-color);
            }
            .status-cancelled {
                background-color: var(--danger-color);
            }

            .tracking-content {
                display: flex;
                gap: 20px;
                margin-bottom: 20px;
            }

            .map-container {
                flex: 2;
                height: 500px;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                position: relative;
            }

            #map {
                width: 100%;
                height: 100%;
            }

            .map-loading {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(255,255,255,0.8);
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                z-index: 100;
            }

            .spinner {
                border: 5px solid #f3f3f3;
                border-top: 5px solid var(--primary-color);
                border-radius: 50%;
                width: 50px;
                height: 50px;
                animation: spin 2s linear infinite;
                margin-bottom: 15px;
            }

            @keyframes spin {
                0% {
                    transform: rotate(0deg);
                }
                100% {
                    transform: rotate(360deg);
                }
            }

            .order-info {
                flex: 1;
                background-color: white;
                border-radius: 8px;
                padding: 20px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            .info-section {
                margin-bottom: 25px;
            }

            .info-title {
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 15px;
                color: var(--dark-color);
                border-bottom: 1px solid #eee;
                padding-bottom: 10px;
            }

            .info-item {
                margin-bottom: 12px;
                display: flex;
                align-items: flex-start;
            }

            .info-label {
                font-weight: bold;
                width: 120px;
                color: #666;
            }

            .info-value {
                flex: 1;
                color: #333;
            }

            .action-buttons {
                display: flex;
                gap: 10px;
                margin-top: 20px;
            }

            .btn {
                padding: 12px 20px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-weight: bold;
                transition: all 0.3s;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 8px;
            }

            .btn-primary {
                background-color: var(--primary-color);
                color: white;
            }

            .btn-primary:hover {
                background-color: #3e8e41;
            }

            .btn-secondary {
                background-color: var(--secondary-color);
                color: white;
            }

            .btn-secondary:hover {
                background-color: #0b7dda;
            }

            .btn-success {
                background-color: var(--success-color);
                color: white;
            }

            .btn-success:hover {
                background-color: #3e8e41;
            }

            .btn-danger {
                background-color: var(--danger-color);
                color: white;
            }

            .btn-danger:hover {
                background-color: #da190b;
            }

            .notification {
                position: fixed;
                top: 20px;
                right: 20px;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
                color: white;
                z-index: 1000;
                opacity: 0;
                transform: translateY(-20px);
                transition: all 0.3s ease;
            }

            .notification.success {
                background-color: var(--success-color);
            }

            .notification.error {
                background-color: var(--danger-color);
            }

            .notification.show {
                opacity: 1;
                transform: translateY(0);
            }

            .route-info {
                margin-top: 20px;
                background-color: white;
                border-radius: 8px;
                padding: 20px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            .route-stats {
                display: flex;
                gap: 20px;
                margin-bottom: 15px;
            }

            .route-stat {
                flex: 1;
                text-align: center;
                padding: 15px;
                border-radius: 8px;
                background-color: #f5f5f5;
            }

            .stat-value {
                font-size: 24px;
                font-weight: bold;
                color: var(--primary-color);
            }

            .stat-label {
                font-size: 14px;
                color: #666;
            }

            .map-error {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                text-align: center;
                color: var(--danger-color);
                background-color: rgba(255,255,255,0.8);
                padding: 20px;
                border-radius: 8px;
                max-width: 80%;
            }

            .btn:disabled {
                background-color: #cccccc;
                cursor: not-allowed;
                opacity: 0.7;
            }

            .btn-loading {
                position: relative;
                pointer-events: none;
            }

            .btn-loading:after {
                content: "";
                position: absolute;
                width: 16px;
                height: 16px;
                top: 50%;
                left: 50%;
                margin-top: -8px;
                margin-left: -8px;
                border-radius: 50%;
                border: 2px solid rgba(255,255,255,0.3);
                border-top-color: #fff;
                animation: spin 1s linear infinite;
            }

            @media (max-width: 768px) {
                .tracking-content {
                    flex-direction: column;
                }

                .map-container, .order-info {
                    width: 100%;
                }

                .map-container {
                    height: 350px;
                }

                .action-buttons {
                    flex-direction: column;
                }
            }

            /* Remove negative filter effects of Goong map in dark mode */
            .goongjs-ctrl-attrib-button,
            .goongjs-ctrl-logo,
            .goongjs-ctrl-geolocate,
            .goongjs-ctrl-navigation {
                filter: none !important;
                -webkit-filter: none !important;
            }

            /* Style for popups */
            .goongjs-popup-content {
                padding: 12px 15px;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .goongjs-popup-content h3 {
                margin-top: 0;
                margin-bottom: 8px;
                color: var(--primary-color);
                font-size: 16px;
            }

            .goongjs-popup-content p {
                margin: 0;
                color: #555;
            }

            .payment-badge {
                padding: 4px 8px;
                border-radius: 4px;
                font-weight: bold;
                font-size: 12px;
                color: white;
                display: inline-block;
            }

            .payment-badge.paid {
                background-color: var(--success-color);
            }

            .payment-badge.unpaid {
                background-color: var(--danger-color);
            }

            /* Thêm CSS cho hiển thị thông tin tốc độ */
            .speed-info {
                position: absolute;
                top: 20px;
                right: 20px;
                background-color: rgba(255, 255, 255, 0.9);
                padding: 10px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
                z-index: 10;
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .speed-badge {
                background-color: var(--danger-color);
                color: white;
                border-radius: 50%;
                width: 40px;
                height: 40px;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                font-weight: bold;
            }

            .road-name {
                max-width: 150px;
                font-weight: bold;
            }

            .notification-inputs {
                margin-bottom: 15px;
            }
            
            .notification-select, 
            .notification-textarea {
                width: 100%;
                padding: 10px;
                margin-bottom: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-family: inherit;
            }
            
            .notification-textarea {
                resize: vertical;
            }
            
            .sent-notifications {
                margin-top: 20px;
            }
            
            .notifications-title {
                font-size: 16px;
                font-weight: bold;
                margin-bottom: 10px;
                color: #555;
            }
            
            .notification-history {
                max-height: 200px;
                overflow-y: auto;
                border: 1px solid #eee;
                border-radius: 4px;
                padding: 10px;
                background: #f9f9f9;
            }
            
            .notification-item {
                padding: 10px;
                border-bottom: 1px solid #eee;
                margin-bottom: 8px;
            }
            
            .notification-item:last-child {
                border-bottom: none;
                margin-bottom: 0;
            }
            
            .notification-message {
                font-size: 14px;
                margin-bottom: 5px;
            }
            
            .notification-time {
                font-size: 12px;
                color: #777;
                text-align: right;
            }
            
            .no-notifications {
                color: #999;
                text-align: center;
                padding: 15px;
            }
        </style>

        <script>
         
            let goongJsLoaded = false;

           
            function checkGoongJsLoaded() {
                if (window.goongjs) {
                    goongJsLoaded = true;
                    console.log("Goong JS loaded successfully");
                } else {
                    console.log("Waiting for Goong JS to load...");
                    setTimeout(checkGoongJsLoaded, 500);
                }
            }

           
            document.addEventListener('DOMContentLoaded', function() {
                checkGoongJsLoaded();
            });

            
            document.addEventListener('DOMContentLoaded', function() {
                console.log("Checking tracking info at page load...");
                
                
                const trackingInfo = {
                    deliveryId: '${trackingInfo.deliveryId}',
                    orderId: '${trackingInfo.orderId}',
                    customerAddress: '${trackingInfo.customerAddress}',
                    paymentStatus: '${trackingInfo.paymentStatus}',
                    paymentMethod: '${trackingInfo.paymentMethod}',
                    statusId: '${trackingInfo.statusId}',
                    latitude: '${trackingInfo.latitude}',
                    longitude: '${trackingInfo.longitude}'
                };
                
                console.log("Tracking Info:", trackingInfo);
                
                // Check for missing info
                const missingFields = [];
                for (const [key, value] of Object.entries(trackingInfo)) {
                    if (!value && key !== 'latitude' && key !== 'longitude') {
                        missingFields.push(key);
                    }
                }
                
                if (missingFields.length > 0) {
                    console.error("Missing tracking info fields:", missingFields);
                    const errorMessage = document.createElement('div');
                    errorMessage.className = 'tracking-error';
                    errorMessage.innerHTML = '<i class="fas fa-exclamation-circle"></i> ' +
                        '<p>Some tracking information is missing. Please refresh the page or contact support.</p>' +
                        '<p>Missing fields: ' + missingFields.join(', ') + '</p>';
                    document.querySelector('.container').prepend(errorMessage);
                }
            });
        </script>
    </head>
    <body>
        <div class="container">
            <div class="tracking-header">
                <div>
                    <h1 class="tracking-title">Order Tracking <span class="tracking-id">#${trackingInfo.deliveryId}</span></h1>
                    <p>Order ID: <strong>#${trackingInfo.orderId}</strong></p>
                </div>
                <div class="status-badge ${trackingInfo.statusId == 1 ? 'status-pending' : 
                                           trackingInfo.statusId == 2 ? 'status-confirmed' : 
                                           trackingInfo.statusId == 3 ? 'status-packed' : 
                                           trackingInfo.statusId == 4 ? 'status-delivering' :
                                           trackingInfo.statusId == 5 ? 'status-delivered' : 'status-cancelled'}">
                         ${trackingInfo.statusName}
                     </div>
                </div>

                <div class="tracking-content">
                    <div class="map-container">
                        <div id="map"></div>
                        <div id="mapLoading" class="map-loading">
                            <div class="spinner"></div>
                            <p>Loading map...</p>
                        </div>
                    </div>

                    <div class="order-info">
                        <div class="info-section">
                            <h2 class="info-title"><i class="fas fa-info-circle"></i> Order Information</h2>
                            <div class="info-item">
                                <span class="info-label">Order ID:</span>
                                <span class="info-value">#${trackingInfo.orderId}</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Order Date:</span>
                                <span class="info-value">
                                    <fmt:formatDate value="${trackingInfo.orderDate}" pattern="MM/dd/yyyy HH:mm" />
                                </span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Status:</span>
                                <span class="info-value">${trackingInfo.statusName}</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Payment:</span>
                                <span class="info-value">
                                    ${trackingInfo.paymentMethod} - 
                                    <span class="payment-badge ${trackingInfo.paymentStatus == '1' || trackingInfo.paymentStatus == 1 ? 'paid' : 'unpaid'}">
                                        ${trackingInfo.paymentStatus == '1' || trackingInfo.paymentStatus == 1 ? 'Paid' : 'Unpaid'}
                                    </span>
                                </span>
                            </div>
                        </div>

                        <div class="info-section">
                            <h2 class="info-title"><i class="fas fa-user"></i> Customer Information</h2>
                            <div class="info-item">
                                <span class="info-label">Customer Name:</span>
                                <span class="info-value">${trackingInfo.customerName}</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Phone Number:</span>
                                <span class="info-value">${trackingInfo.customerPhone}</span>
                            </div>
                        </div>

                        <div class="info-section">
                            <h2 class="info-title"><i class="fas fa-map-marker-alt"></i> Delivery Address</h2>
                            <div class="info-item">
                                <span class="info-label">Address:</span>
                                <span class="info-value">${trackingInfo.customerAddress}</span>
                            </div>
                        </div>

                        <div class="info-section">
                            <h2 class="info-title"><i class="fas fa-truck"></i> Delivery Information</h2>
                            <div class="info-item">
                                <span class="info-label">Staff:</span>
                                <span class="info-value">${trackingInfo.staffName}</span>
                            </div>
                        </div>

                        <div class="info-section" id="notificationSection">
                            <h2 class="info-title"><i class="fas fa-bell"></i> Customer Notifications</h2>
                            <div class="notification-inputs">
                                <select id="notificationTemplate" class="notification-select">
                                    <option value="">Select notification template...</option>
                                    <option value="We have started shipping your order. Please keep your phone ready for delivery updates.">Starting delivery - Keep phone ready</option>
                                    <option value="We will arrive at your location within 1 hour. Please prepare for delivery.">Arriving within 1 hour</option>
                                    <option value="Your order has been delivered. Thank you for choosing our service!">Delivery completed</option>
                                    <option value="We are experiencing a slight delay. We'll reach you soon.">Delivery delay</option>
                                    <option value="We're trying to reach you. Please respond to our calls.">Trying to reach customer</option>
                                    <option value="custom">Custom message...</option>
                                </select>
                                <textarea id="customNotificationText" class="notification-textarea" placeholder="Enter custom notification message or edit template..." rows="3"></textarea>
                                <button id="sendNotificationBtn" class="btn btn-primary">
                                    <i class="fas fa-paper-plane"></i> Send Notification
                                </button>
                            </div>
                            <div class="sent-notifications">
                                <h3 class="notifications-title">Sent Notifications</h3>
                                <div id="notificationHistory" class="notification-history">
                                    
                                    <div class="no-notifications">No notifications sent yet</div>
                                </div>
                            </div>
                        </div>

                        <div class="action-buttons">
                            <button id="updateLocationBtn" class="btn btn-primary">
                                <i class="fas fa-location-arrow"></i> Update Location
                            </button>

                            <c:if test="${trackingInfo.statusId != 5}">
                                <button id="markDeliveredBtn" class="btn btn-success">
                                    <i class="fas fa-check-circle"></i> Mark as Delivered
                                </button>
                            </c:if>

                            <a href="deliveryList" class="btn btn-secondary">
                                <i class="fas fa-arrow-left"></i> Back
                            </a>
                        </div>
                    </div>
                </div>

                <div class="route-info">
                    <h2 class="info-title"><i class="fas fa-route"></i> Route Information</h2>
                    <div class="route-stats">
                        <div class="route-stat">
                            <div id="totalDistance" class="stat-value">--</div>
                            <div class="stat-label">Total Distance</div>
                        </div>
                        <div class="route-stat">
                            <div id="estimatedTime" class="stat-value">--</div>
                            <div class="stat-label">Estimated Time</div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="notification" class="notification"></div>

            
            <div style="display: none;">
                <input type="hidden" id="trackingInfoCurrentLat" value="${trackingInfo.currentLatitude}" />
                <input type="hidden" id="trackingInfoCurrentLng" value="${trackingInfo.currentLongitude}" />
                <input type="hidden" id="trackingInfoDeliveryId" value="${trackingInfo.deliveryId}" />
                <input type="hidden" id="trackingInfoOrderId" value="${trackingInfo.orderId}" />
                <input type="hidden" id="trackingInfoPaymentStatus" value="${trackingInfo.paymentStatus}" />
                <input type="hidden" id="trackingInfoPaymentMethod" value="${trackingInfo.paymentMethod}" />
                <input type="hidden" id="trackingInfoStatusId" value="${trackingInfo.statusId}" />
                <input type="hidden" id="trackingInfoCustomerAddress" value="${trackingInfo.customerAddress}" />
                <input type="hidden" id="maptilesKeyValue" value="${maptilesKey}" />
                <input type="hidden" id="apiKeyValue" value="${apiKey}" />
            </div>

            <script>
               
                let currentLat = 10.0322;
                let currentLng = 105.7685;
                let destinationLat, destinationLng;
                let mapInitialized = false;
                let currentMarker;
                let destinationMarker;
                let routeLine;
                let geocodeTimer;
                let autoUpdateInterval;
                const autoUpdateFrequency = 30000; 

                
                function getInputValue(id, defaultValue) {
                    const element = document.getElementById(id);
                    if (element && element.value && element.value !== "null") {
                        return element.value;
                    }
                    return defaultValue;
                }

                function getNumericInputValue(id, defaultValue) {
                    const value = getInputValue(id, null);
                    if (value !== null) {
                        const parsedValue = parseFloat(value);
                        return isNaN(parsedValue) ? defaultValue : parsedValue;
                    }
                    return defaultValue;
                }

                
                document.addEventListener('DOMContentLoaded', function () {
                    
                    currentLat = getNumericInputValue('trackingInfoCurrentLat', currentLat);
                    currentLng = getNumericInputValue('trackingInfoCurrentLng', currentLng);
                    const deliveryId = getInputValue('trackingInfoDeliveryId', "0");
                    const maptilesKey = getInputValue('maptilesKeyValue', "");
                    const apiKey = getInputValue('apiKeyValue', "");
                    const customerAddress = getInputValue('trackingInfoCustomerAddress', "");
                    const paymentStatus = getInputValue('trackingInfoPaymentStatus', "0");
                    const paymentMethod = getInputValue('trackingInfoPaymentMethod', "");
                    const statusId = getNumericInputValue('trackingInfoStatusId', 0);
                    
                    
                    console.log('========= TRACKING INFO VALIDATION =========');
                    console.log('Customer address:', customerAddress);
                    
                    if (!customerAddress || customerAddress.trim() === '') {
                        
                        console.error('Missing delivery address');
                        const containerElement = document.querySelector('.container');
                        if (containerElement) {
                            const errorElement = document.createElement('div');
                            errorElement.className = 'tracking-error';
                            errorElement.innerHTML = 
                                '<i class="fas fa-exclamation-triangle"></i>' +
                                '<div>' +
                                    '<p><strong>Cannot display map</strong></p>' +
                                    '<p>Invalid delivery address. Please check order information.</p>' +
                                '</div>';
                            containerElement.insertBefore(errorElement, containerElement.firstChild);
                        }
                    }
                    
                    console.log('DeliveryId initialized as:', deliveryId);
                    
                   
                    if (!deliveryId || deliveryId === "" || deliveryId === "null" || deliveryId === "0") {
                        console.error('Invalid deliveryId detected in page load:', deliveryId);
                        const containerElement = document.querySelector('.container');
                        if (containerElement) {
                            const errorElement = document.createElement('div');
                            errorElement.className = 'tracking-error';
                            errorElement.innerHTML = 
                                '<i class="fas fa-exclamation-triangle"></i>' +
                                '<div>' +
                                    '<p><strong>Cannot track order</strong></p>' +
                                    '<p>Invalid order ID. Please check your information.</p>' +
                                '</div>';
                            containerElement.insertBefore(errorElement, containerElement.firstChild);
                        }
                    }
                    
                    
                    console.log('=================== PAYMENT STATUS DEBUG ===================');
                    console.log('Payment Status Type:', typeof parseInt(paymentStatus));
                    console.log('Payment Status Raw Value:', paymentStatus);
                    console.log('Payment Status Numeric Value:', parseInt(paymentStatus));
                    
                   
                    console.log('TrackingInfo object:', {
                        deliveryId: parseInt(deliveryId),
                        orderId: parseInt(getInputValue('trackingInfoOrderId', "0")),
                        paymentStatus: paymentStatus,
                        paymentMethod: paymentMethod
                    });
                    
                    
                    const paymentBadge = document.querySelector('.payment-badge');
                    if (paymentBadge) {
                        
                        if (paymentStatus === '1' || paymentStatus === 1 || paymentStatus === 'true' || paymentStatus === true) {
                            paymentBadge.textContent = 'Paid';
                            paymentBadge.classList.add('paid');
                            paymentBadge.classList.remove('unpaid');
                        } else {
                            paymentBadge.textContent = 'Unpaid';
                            paymentBadge.classList.add('unpaid');
                            paymentBadge.classList.remove('paid');
                        }
                    }
                    
                    // Kiểm tra các giá trị API key
                    console.log('API Key available:', apiKey ? 'Yes' : 'No');
                    console.log('Map Tiles Key available:', maptilesKey ? 'Yes' : 'No');
                    console.log('Map API Key:', apiKey);
                    console.log('Map Tiles Key:', maptilesKey);
                    
                    if (!apiKey || !maptilesKey) {
                        console.error('Missing API keys');
                        showNotification('Missing map API connection information', 'error');
                        // Hiển thị lỗi trong div map-loading
                        const mapLoading = document.getElementById('mapLoading');
                        if (mapLoading) {
                            mapLoading.innerHTML = 
                                '<div class="map-error">' +
                                    '<i class="fas fa-exclamation-triangle"></i>' +
                                    '<p>Cannot load map. Missing API key.</p>' +
                                '</div>';
                        }
                    } else {
                        // Khởi tạo bản đồ nếu có địa chỉ hợp lệ
                        if (customerAddress && customerAddress.trim() !== '') {
                            // Thực hiện kiểm tra xem Goong JS đã sẵn sàng chưa
                            function waitForGoongAndInitialize() {
                                if (window.goongjs) {
                                    // Đối với Goong Maps, maptilesKey là key cho tiles
                                    console.log("Initializing map with maptilesKey:", maptilesKey);
                                    initializeMap(maptilesKey);
                                    
                                    // Sau khi map được khởi tạo, gọi geocode với API key
                                    setTimeout(() => {
                                        if (mapInitialized) {
                                            geocodeAddress(customerAddress, apiKey);
                                        }
                                    }, 1000);
                                    
                                    // Khởi tạo auto-update nếu đơn hàng đang giao
                                    startAutoUpdate();
                                } else {
                                    console.log("Waiting for Goong JS to load...");
                                    setTimeout(waitForGoongAndInitialize, 500);
                                }
                            }
                            
                            // Bắt đầu kiểm tra
                            waitForGoongAndInitialize();
                        }
                    }
                });

                // Function to start auto-update interval
                function startAutoUpdate() {
                    // Tạm dừng nếu đã có interval đang chạy
                    stopAutoUpdate();
                    
                    // Only start auto update if order is in delivery (status_id = 4)
                    const statusId = getNumericInputValue('trackingInfoStatusId', 0);
                    if (statusId === 4) {
                        autoUpdateInterval = setInterval(() => {
                            // Update location automatically but don't show notification
                            updateCurrentLocation(true);
                        }, autoUpdateFrequency);
                    }
                }

                // Function to stop auto-update
                function stopAutoUpdate() {
                    if (autoUpdateInterval) {
                        clearInterval(autoUpdateInterval);
                        autoUpdateInterval = null;
                    }
                }

                // Initialize map with Goong Maps
                function initializeMap(apiKey) {
                    if (mapInitialized || !window.goongjs) {
                        return;
                    }
                    
                    console.log('Initializing map with API key:', apiKey);
                    
                    try {
                        // Thiết lập token truy cập cho Goong Maps - phải thiết lập TRƯỚC khi tạo Map
                        window.goongjs.accessToken = apiKey;
                        console.log('Access token set:', window.goongjs.accessToken);
                        
                        // Kiểm tra token trước khi khởi tạo
                        if (!apiKey || apiKey === 'null' || apiKey === '') {
                            throw new Error('Invalid API key');
                        }
                        
                        // Tạo đối tượng bản đồ với style URL đúng
                        map = new window.goongjs.Map({
                            container: 'map',
                            style: 'https://tiles.goong.io/assets/goong_map_web.json?api_key=' + apiKey,
                            center: [currentLng, currentLat], // [lng, lat]
                            zoom: 14
                        });

                        // Xử lý sự kiện khi bản đồ đã tải xong
                        map.on('load', function () {
                            mapInitialized = true;
                            console.log('Map loaded successfully');
                            
                            // Thêm các điều khiển điều hướng
                            map.addControl(new window.goongjs.NavigationControl());
                            
                            // Thêm điều khiển vị trí
                            map.addControl(new window.goongjs.GeolocateControl({
                                positionOptions: {
                                    enableHighAccuracy: true
                                },
                                trackUserLocation: true
                            }));
                            
                            // Ẩn màn hình loading
                            const mapLoading = document.getElementById('mapLoading');
                            if (mapLoading) {
                                mapLoading.style.display = 'none';
                            }
                            
                            // Thêm marker vị trí hiện tại
                            if (currentLat && currentLng) {
                                currentMarker = new window.goongjs.Marker({
                                    color: '#2196F3'
                                })
                                .setLngLat([currentLng, currentLat])
                                .setPopup(new window.goongjs.Popup().setHTML('<h3>Current Location</h3><p>Delivery Staff</p>'))
                                .addTo(map);
                            }
                                                    
                            // Add destination marker
                            if (destinationMarker) {
                                destinationMarker.setLngLat([destinationLng, destinationLat]);
                                destinationMarker.getPopup().setHTML('<h3>Delivery Address</h3><p>' + formattedAddress + '</p>');
                            } else {
                                destinationMarker = new window.goongjs.Marker({
                                    color: '#f44336'
                                })
                                .setLngLat([destinationLng, destinationLat])
                                .setPopup(new window.goongjs.Popup().setHTML('<h3>Delivery Address</h3><p>' + formattedAddress + '</p>'))
                                .addTo(map);
                            }
                        });
                        
                        // Xử lý lỗi bản đồ
                        map.on('error', function(e) {
                            console.error('Map error:', e);
                            const mapLoading = document.getElementById('mapLoading');
                            if (mapLoading) {
                                mapLoading.innerHTML = 
                                    '<div class="map-error">' +
                                        '<i class="fas fa-exclamation-triangle"></i>' +
                                        '<p>Cannot load map. Error: ' + (e.error ? e.error.message : 'Unknown') + '</p>' +
                                    '</div>';
                            }
                        });
                    } catch (error) {
                        console.error('Error initializing map:', error);
                        const mapLoading = document.getElementById('mapLoading');
                        if (mapLoading) {
                            mapLoading.innerHTML = 
                                '<div class="map-error">' +
                                    '<i class="fas fa-exclamation-triangle"></i>' +
                                    '<p>Cannot initialize map. Error: ' + error.message + '</p>' +
                                '</div>';
                        }
                    }
                }

                // Update map with current location
                function updateMapWithCurrentLocation(lat, lng) {
                    if (!mapInitialized || !map) {
                        console.error('Map not initialized');
                        return;
                    }
                    
                    console.log('Updating map with current location:', lat, lng);
                    
                    // Update global variables
                    currentLat = lat;
                    currentLng = lng;
                    
                    // Update or create current marker
                    if (currentMarker) {
                        currentMarker.setLngLat([lng, lat]);
                    } else {
                        currentMarker = new window.goongjs.Marker({
                            color: '#2196F3'
                        })
                        .setLngLat([lng, lat])
                        .setPopup(new window.goongjs.Popup().setHTML('<h3>Current Location</h3><p>Delivery Staff</p>'))
                        .addTo(map);
                    }
                    
                    // If we have destination coordinates, update the route
                    if (destinationLat && destinationLng) {
                        getDirections(lat, lng, destinationLat, destinationLng);
                    }
                }

                // Geocode address to coordinates
                function geocodeAddress(address, apiKey) {
                    console.log('Geocoding address:', address);
                    
                    if (!address || address.trim() === '') {
                        showNotification('Invalid address', 'error');
                        return;
                    }
                    
                    // Prepare data for server request
                    const reqData = new URLSearchParams();
                    reqData.append('action', 'geocode');
                    reqData.append('deliveryId', getInputValue('trackingInfoDeliveryId', '0'));
                    reqData.append('address', address.trim());
                    
                    fetch('delivery-tracking', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: reqData
                    })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`Server error! status: ${response.status}`);
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log('Geocode API response:', data);
                        
                        if (data.success && data.geocode) {
                            // Extract location data
                            destinationLat = data.geocode.lat;
                            destinationLng = data.geocode.lng;
                            const formattedAddress = data.geocode.formatted_address || address;
                            
                            // Add destination marker
                            if (destinationMarker) {
                                destinationMarker.setLngLat([destinationLng, destinationLat]);
                                destinationMarker.getPopup().setHTML('<h3>Delivery Address</h3><p>' + formattedAddress + '</p>');
                            } else {
                                destinationMarker = new window.goongjs.Marker({
                                    color: '#f44336'
                                })
                                .setLngLat([destinationLng, destinationLat])
                                .setPopup(new window.goongjs.Popup().setHTML('<h3>Delivery Address</h3><p>' + formattedAddress + '</p>'))
                                .addTo(map);
                            }
                            
                            // Get directions if we have current location
                            if (currentLat && currentLng) {
                                getDirections(currentLat, currentLng, destinationLat, destinationLng);
                            }
                            
                            // Adjust view
                            fitBounds();
                        } else {
                            console.error('No geocode results found');
                            showNotification('Could not find coordinates for address', 'error');
                        }
                    })
                    .catch(error => {
                        console.error('Error geocoding address:', error);
                        showNotification('Error finding address coordinates', 'error');
                    });
                }

                // Get directions between two points
                function getDirections(startLat, startLng, endLat, endLng) {
                    console.log('Getting directions:', startLat, startLng, 'to', endLat, endLng);
                    
                    // Chuyển đổi tất cả tọa độ thành số và kiểm tra giá trị
                    const originLat = parseFloat(startLat);
                    const originLng = parseFloat(startLng);
                    const destLat = parseFloat(endLat);
                    const destLng = parseFloat(endLng);
                    
                    console.log('Coordinates after parsing:');
                    console.log('originLat:', originLat, 'type:', typeof originLat);
                    console.log('originLng:', originLng, 'type:', typeof originLng);
                    console.log('destLat:', destLat, 'type:', typeof destLat);
                    console.log('destLng:', destLng, 'type:', typeof destLng);
                    
                    if (isNaN(originLat) || isNaN(originLng) || isNaN(destLat) || isNaN(destLng)) {
                        console.error('Invalid coordinates for directions (NaN values detected)');
                        showNotification('Invalid coordinates for directions', 'error');
                        return;
                    }
                    
                    // Get API key from hidden field
                    const apiKey = getInputValue('apiKeyValue', '');
                    if (!apiKey) {
                        console.error('Missing API key for directions');
                        showNotification('Missing API key for directions', 'error');
                        return;
                    }
                    
                    // Tạo chuỗi origin và destination TRƯỚC KHI đưa vào URL
                    const origin = originLat.toString() + ',' + originLng.toString();
                    const destination = destLat.toString() + ',' + destLng.toString();
                    
                    console.log('Origin string:', origin);
                    console.log('Destination string:', destination);
                    
                    // Tính toán khoảng cách thẳng giữa hai điểm (km)
                    const distance = calculateDistance(originLat, originLng, destLat, destLng);
                    console.log('Direct distance between points (km):', distance);
                    
                    // Xây dựng URL - KHÔNG sử dụng template literal để tránh lỗi khi ghép các biến
                    // Thay vì: `https://rsapi.goong.io/Direction?origin=${origin}&destination=${destination}&vehicle=car&api_key=${apiKey}`
                    // Sử dụng phép nối chuỗi thông thường:
                    const directionsUrl = 'https://rsapi.goong.io/Direction' + 
                                           '?origin=' + encodeURIComponent(origin) + 
                                           '&destination=' + encodeURIComponent(destination) + 
                                           '&vehicle=car' + 
                                           '&api_key=' + apiKey;
                    
                    console.log('Directions URL:', directionsUrl);
                    
                    fetch(directionsUrl)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(`Goong API error: ${response.status}`);
                            }
                            return response.json();
                        })
                        .then(data => {
                            console.log('Goong Directions API response:', data);
                            
                            // Kiểm tra cả phản hồi từ API - có thể không có status hoặc status khác OK
                            if (data.routes && data.routes.length > 0) {
                                const route = data.routes[0];
                                
                                // Update distance and duration info
                                if (route.legs && route.legs.length > 0) {
                                    const leg = route.legs[0];
                                    document.getElementById('totalDistance').textContent = leg.distance.text;
                                    document.getElementById('estimatedTime').textContent = leg.duration.text;
                                }
                                
                                // Draw route if polyline is available
                                if (route.overview_polyline && route.overview_polyline.points) {
                                    drawRouteFromPolyline(route.overview_polyline.points);
                                } else {
                                    console.warn('No polyline found, falling back to straight line');
                                    drawStraightLine(originLng, originLat, destLng, destLat);
                                }
                            } else {
                                console.error('No valid route found in response. Status:', data.status);
                                
                                // Nếu status là NOT_FOUND, hiển thị thông báo cụ thể
                                if (data.status === 'NOT_FOUND') {
                                    showNotification('No route found between the points. Drawing straight line instead.', 'error');
                                } else {
                                    showNotification('Could not find a route. Using straight line instead.', 'error');
                                }
                                
                                // Cập nhật thông tin khoảng cách và thời gian ước tính thủ công dựa trên khoảng cách thẳng
                                const distanceText = distance.toFixed(1) + ' km';
                                const estimatedMinutes = Math.ceil(distance * 3); // ước tính 3 phút/km
                                const durationText = estimatedMinutes + ' mins';
                                
                                document.getElementById('totalDistance').textContent = distanceText;
                                document.getElementById('estimatedTime').textContent = durationText;
                                
                                drawStraightLine(originLng, originLat, destLng, destLat);
                            }
                        })
                        .catch(error => {
                            console.error('Error getting directions from Goong API:', error);
                            showNotification('Error getting directions. Using straight line instead.', 'error');
                            drawStraightLine(originLng, originLat, destLng, destLat);
                        });
                }

                // Hàm tính khoảng cách giữa hai điểm (Haversine formula)
                function calculateDistance(lat1, lon1, lat2, lon2) {
                    const R = 6371; // Radius of the earth in km
                    const dLat = deg2rad(lat2 - lat1);
                    const dLon = deg2rad(lon2 - lon1);
                    const a = 
                        Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
                        Math.sin(dLon/2) * Math.sin(dLon/2); 
                    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
                    const d = R * c; // Distance in km
                    return d;
                }

                function deg2rad(deg) {
                    return deg * (Math.PI/180);
                }

                // Draw straight line between two points
                function drawStraightLine(startLng, startLat, endLng, endLat) {
                    if (!mapInitialized || !map) {
                        return;
                    }
                    
                    // Remove existing route if any
                    if (routeLine && map.getSource('route')) {
                        map.removeLayer('route');
                        map.removeSource('route');
                    }
                    
                    // Add source and layer for straight line
                    map.addSource('route', {
                        type: 'geojson',
                        data: {
                            type: 'Feature',
                            properties: {},
                            geometry: {
                                type: 'LineString',
                                coordinates: [
                                    [startLng, startLat],
                                    [endLng, endLat]
                                ]
                            }
                        }
                    });
                    
                    map.addLayer({
                        id: 'route',
                        type: 'line',
                        source: 'route',
                        layout: {
                            'line-join': 'round',
                            'line-cap': 'round'
                        },
                        paint: {
                            'line-color': '#0078ff',
                            'line-width': 5,
                            'line-opacity': 0.75
                        }
                    });
                    
                    routeLine = true;
                    fitBounds();
                }

                // Draw route from polyline points
                function drawRouteFromPolyline(polylinePoints) {
                    if (!mapInitialized || !map) {
                        return;
                    }
                    
                    // Remove existing route if any
                    if (routeLine && map.getSource('route')) {
                        map.removeLayer('route');
                        map.removeSource('route');
                    }
                    
                    // Decode polyline với hàm giải mã tự tạo thay vì phụ thuộc vào Goong API
                    let decodedCoordinates = [];
                    try {
                        decodedCoordinates = decodePolyline(polylinePoints);
                    } catch (error) {
                        console.error('Error decoding polyline:', error);
                        decodedCoordinates = [
                            [currentLng, currentLat],
                            [destinationLng, destinationLat]
                        ];
                    }
                    
                    if (!decodedCoordinates || decodedCoordinates.length < 2) {
                        console.error('Invalid polyline decoding result');
                        return;
                    }
                    
                    // Add source and layer for route
                    map.addSource('route', {
                        type: 'geojson',
                        data: {
                            type: 'Feature',
                            properties: {},
                            geometry: {
                                type: 'LineString',
                                coordinates: decodedCoordinates
                            }
                        }
                    });
                    
                    map.addLayer({
                        id: 'route',
                        type: 'line',
                        source: 'route',
                        layout: {
                            'line-join': 'round',
                            'line-cap': 'round'
                        },
                        paint: {
                            'line-color': '#0078ff',
                            'line-width': 5,
                            'line-opacity': 0.75
                        }
                    });
                    
                    routeLine = true;
                    fitBounds();
                }

                // Hàm giải mã polyline - thuật toán giải mã mã polyline Google/Goong 
                function decodePolyline(str) {
                    let index = 0,
                        lat = 0,
                        lng = 0,
                        coordinates = [],
                        shift = 0,
                        result = 0,
                        byte = null,
                        latitude_change,
                        longitude_change;

                    // Decode polyline
                    while (index < str.length) {
                        // For each byte
                        byte = null;
                        shift = 0;
                        result = 0;

                        do {
                            byte = str.charCodeAt(index++) - 63;
                            result |= (byte & 0x1f) << shift;
                            shift += 5;
                        } while (byte >= 0x20);

                        latitude_change = ((result & 1) ? ~(result >> 1) : (result >> 1));

                        shift = result = 0;

                        do {
                            byte = str.charCodeAt(index++) - 63;
                            result |= (byte & 0x1f) << shift;
                            shift += 5;
                        } while (byte >= 0x20);

                        longitude_change = ((result & 1) ? ~(result >> 1) : (result >> 1));

                        lat += latitude_change;
                        lng += longitude_change;

                        // [lng, lat] format cho Goong/Mapbox
                        coordinates.push([lng * 1e-5, lat * 1e-5]);
                    }

                    return coordinates;
                }

                // Fit map bounds to show all markers
                function fitBounds() {
                    if (!mapInitialized || !map || !currentLat || !currentLng || !destinationLat || !destinationLng) {
                        return;
                    }
                    
                    try {
                        const bounds = new window.goongjs.LngLatBounds()
                            .extend([currentLng, currentLat])
                            .extend([destinationLng, destinationLat]);
                        
                        map.fitBounds(bounds, {
                            padding: {top: 50, bottom: 50, left: 50, right: 50},
                            maxZoom: 15,
                            duration: 1000
                        });
                    } catch (error) {
                        console.error('Error adjusting map bounds:', error);
                    }
                }

                // Update current location
                function updateCurrentLocation(silent = false) {
                    // Trả về Promise để có thể sử dụng .finally()
                    return new Promise((resolve, reject) => {
                        // Get current position from browser
                        if (navigator.geolocation) {
                            navigator.geolocation.getCurrentPosition(
                                function(position) {
                                    const lat = position.coords.latitude;
                                    const lng = position.coords.longitude;
                                    
                                    // Prepare data for server request
                                    const reqData = new URLSearchParams();
                                    reqData.append('action', 'updateLocation');
                                    reqData.append('deliveryId', getInputValue('trackingInfoDeliveryId', '0'));
                                    reqData.append('latitude', lat);
                                    reqData.append('longitude', lng);
                                    
                                    fetch('delivery-tracking', {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/x-www-form-urlencoded'
                                        },
                                        body: reqData
                                    })
                                    .then(response => {
                                        if (!response.ok) {
                                            throw new Error(`Server error! status: ${response.status}`);
                                        }
                                        return response.json();
                                    })
                                    .then(data => {
                                        console.log('Update location response:', data);
                                        
                                        if (data.success) {
                                            // Update map with new location
                                            updateMapWithCurrentLocation(lat, lng);
                                            
                                            // Show speed limit if available
                                            if (data.speed_limit) {
                                                showSpeedLimit(data.speed_limit, data.road_name);
                                            }
                                            
                                            if (!silent) {
                                                showNotification('Location updated successfully', 'success');
                                            }
                                            resolve(data);
                                        } else {
                                            if (!silent) {
                                                showNotification(data.message || 'Could not update location', 'error');
                                            }
                                            reject(new Error(data.message || 'Could not update location'));
                                        }
                                    })
                                    .catch(error => {
                                        console.error('Error updating location:', error);
                                        if (!silent) {
                                            showNotification('Error updating location', 'error');
                                        }
                                        reject(error);
                                    });
                                },
                                function(error) {
                                    console.error('Geolocation error:', error);
                                    if (!silent) {
                                        showNotification('Could not get current position', 'error');
                                    }
                                    reject(error);
                                },
                                {
                                    enableHighAccuracy: true,
                                    timeout: 10000,
                                    maximumAge: 0
                                }
                            );
                        } else {
                            if (!silent) {
                                showNotification('Your browser does not support geolocation', 'error');
                            }
                            reject(new Error('Browser does not support geolocation'));
                        }
                    });
                }

                // Show speed limit information
                function showSpeedLimit(speedLimit, roadName) {
                    const speedInfoElement = document.getElementById('speedInfo');
                    if (speedInfoElement) {
                        speedInfoElement.innerHTML = 
                            '<div class="speed-limit">' +
                                '<div class="speed-limit-circle">' +
                                    '<span>' + speedLimit + '</span>' +
                                    '<small>km/h</small>' +
                                '</div>' +
                                '<div class="road-info">' +
                                    '<div>' + (roadName || 'Current Road') + '</div>' +
                                '</div>' +
                            '</div>';
                    }
                }

                // Mark delivery as completed
                function markAsDelivered() {
                    const reqData = new URLSearchParams();
                    reqData.append('action', 'markDelivered');
                    reqData.append('deliveryId', getInputValue('trackingInfoDeliveryId', '0'));
                    
                    return fetch('delivery-tracking', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        },
                        body: reqData
                    })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`Server error! status: ${response.status}`);
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log('Mark delivered response:', data);
                        
                        if (data.success) {
                            showNotification('Order has been marked as delivered', 'success');
                            // Reload page after 2 seconds
                            setTimeout(() => {
                                window.location.reload();
                            }, 2000);
                        } else {
                            showNotification(data.message || 'Could not update order status', 'error');
                        }
                        
                        return data;
                    })
                    .catch(error => {
                        console.error('Error marking as delivered:', error);
                        showNotification('Error updating order status', 'error');
                        throw error;
                    });
                }

                // Show notification
                function showNotification(type, message) {
                    // Lấy hoặc tạo container thông báo
                    let notificationsList = document.getElementById('notifications-list');
                    
                    // Nếu chưa có container, tạo container mới
                    if (!notificationsList) {
                        notificationsList = document.createElement('div');
                        notificationsList.id = 'notifications-list';
                        notificationsList.className = 'notifications-container';
                        notificationsList.style.position = 'fixed';
                        notificationsList.style.top = '20px';
                        notificationsList.style.right = '20px';
                        notificationsList.style.zIndex = '1000';
                        document.body.appendChild(notificationsList);
                    }
                    
                    const notificationItem = document.createElement('div');
                    notificationItem.className = 'notification ' + type;
                    
                    const now = new Date();
                    const timeString = now.toLocaleTimeString();
                    
                    notificationItem.innerHTML = 
                        '<div class="notification-message">' + message + '</div>' + 
                        '<div class="notification-time">' + timeString + '</div>';
                    
                    notificationsList.appendChild(notificationItem);
                    
                    // Tự động xóa thông báo sau 5 giây
                    setTimeout(() => {
                        notificationItem.classList.add('fade-out');
                        setTimeout(() => {
                            if (notificationItem.parentNode) {
                                notificationItem.parentNode.removeChild(notificationItem);
                            }
                        }, 500);
                    }, 5000);
                }

                // Hàm gửi thông báo tùy chỉnh
                function sendCustomNotification() {
                    // Lấy tin nhắn từ textarea với ID đúng như trong HTML
                    const customNotificationText = document.getElementById('customNotificationText');
                    
                    if (!customNotificationText) {
                        console.error('Không thể tìm thấy phần tử input tin nhắn thông báo');
                        showNotification('error', 'Error: Notification field not found');
                        return;
                    }
                    
                    const customMessage = customNotificationText.value;
                    
                    if (!customMessage || customMessage.trim() === '') {
                        showNotification('error', 'Please enter a notification message');
                        return;
                    }
                    
                    // Lấy thông tin đơn hàng từ trackingData
                    const orderId = trackingData.orderId;
                    const deliveryId = trackingData.deliveryId;
                    const encodedMessage = encodeURIComponent(customMessage);
                    
                    if (!orderId || !deliveryId) {
                        showNotification('error', 'Missing order information');
                        return;
                    }
                    
                    // Hiển thị trạng thái tải
                    const sendButton = document.getElementById('sendNotificationBtn');
                    if (sendButton) {
                        sendButton.classList.add('btn-loading');
                        sendButton.disabled = true;
                    }
                    
                    // Tạo chuỗi thời gian hiện tại - khai báo biến ở đây để có phạm vi toàn hàm
                    const now = new Date();
                    const timeString = now.toLocaleTimeString() + ', ' + now.toLocaleDateString();
                    
                    // Gửi thông báo tới server
                    fetch('delivery-tracking', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                        body: 'action=sendCustomerNotification&orderId=' + orderId + '&message=' + encodedMessage + '&deliveryId=' + deliveryId
                    })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            showNotification('success', 'Notification sent successfully');
                            
                            // Thêm thông báo vào lịch sử
                            const notificationHistory = document.getElementById('notificationHistory');
                            if (notificationHistory) {
                                // Xóa thông báo "không có thông báo" nếu có
                                const noNotificationsEl = notificationHistory.querySelector('.no-notifications');
                                if (noNotificationsEl) {
                                    noNotificationsEl.remove();
                                }
                                
                                // Tạo item mới
                                const notificationItem = document.createElement('div');
                                notificationItem.className = 'history-item';
                                
                                notificationItem.innerHTML = 
                                    '<div class="history-message">' + customMessage + '</div>' + 
                                    '<div class="history-time">' + timeString + '</div>';
                                
                                notificationHistory.appendChild(notificationItem);
                            }
                            
                            // Xóa tin nhắn đã nhập
                            customNotificationText.value = '';
                            
                            // Lưu vào localStorage với thời gian được tạo
                            saveNotificationToHistory(customMessage, timeString);
                        } else {
                            showNotification('error', data.message || 'Failed to send notification');
                        }
                    })
                    .catch(error => {
                        console.error('Error sending notification:', error);
                        showNotification('error', 'Error sending notification: ' + error.message);
                    })
                    .finally(() => {
                        // Xóa trạng thái tải
                        if (sendButton) {
                            sendButton.classList.remove('btn-loading');
                            sendButton.disabled = false;
                        }
                    });
                }

                // Hàm lưu thông báo vào lịch sử
                function saveNotificationToHistory(message, time) {
                    const deliveryId = trackingData.deliveryId;
                    if (!deliveryId) return;
                    
                    // Nếu không cung cấp time, tạo thời gian hiện tại
                    if (!time) {
                        const now = new Date();
                        time = now.toLocaleTimeString() + ', ' + now.toLocaleDateString();
                    }
                    
                    const historyKey = 'notification_history_' + deliveryId;
                    let history = [];
                    
                    try {
                        const savedHistory = localStorage.getItem(historyKey);
                        if (savedHistory) {
                            history = JSON.parse(savedHistory);
                        }
                    } catch (e) {
                        console.error('Error loading history:', e);
                    }
                    
                    // Thêm thông báo mới
                    history.unshift({
                        message: message,
                        time: time
                    });
                    
                    // Giới hạn số lượng
                    if (history.length > 10) {
                        history = history.slice(0, 10);
                    }
                    
                    // Lưu lại
                    localStorage.setItem(historyKey, JSON.stringify(history));
                }

                // Set up event listeners when page is loaded
                document.addEventListener('DOMContentLoaded', function() {
                    // Set up event listeners for buttons
                    const updateLocationBtn = document.getElementById('updateLocationBtn');
                    if (updateLocationBtn) {
                        updateLocationBtn.addEventListener('click', function() {
                            // Add loading class and disable button
                            this.classList.add('btn-loading');
                            this.disabled = true;
                            
                            updateCurrentLocation().finally(() => {
                                // Remove loading class and enable button after completion
                                setTimeout(() => {
                                    this.classList.remove('btn-loading');
                                    this.disabled = false;
                                }, 1000);
                            });
                        });
                    }
                    
                    const markDeliveredBtn = document.getElementById('markDeliveredBtn');
                    if (markDeliveredBtn) {
                        markDeliveredBtn.addEventListener('click', function() {
                            // Add loading class and disable button
                            this.classList.add('btn-loading');
                            this.disabled = true;
                            
                            markAsDelivered().finally(() => {
                                // Keep loading state until page reloads
                                setTimeout(() => {
                                    if (this.disabled) {
                                        this.classList.remove('btn-loading');
                                        this.disabled = false;
                                    }
                                }, 5000);
                            });
                        });
                    }
                    
                    // Stop auto-update when page is hidden
                    document.addEventListener('visibilitychange', function() {
                        if (document.visibilityState === 'hidden') {
                            stopAutoUpdate();
                        } else {
                            startAutoUpdate();
                        }
                    });
                });

                // Xử lý custom notification
                document.addEventListener('DOMContentLoaded', function() {
                    const notificationTemplate = document.getElementById('notificationTemplate');
                    const customNotificationText = document.getElementById('customNotificationText');
                    const sendNotificationBtn = document.getElementById('sendNotificationBtn');
                    const notificationHistory = document.getElementById('notificationHistory');
                    
                    // Hiển thị section notification chỉ khi role là người giao hàng
                    const statusId = getNumericInputValue('trackingInfoStatusId', 0);
                    const notificationSection = document.getElementById('notificationSection');
                    
                    if (notificationSection) {
                        // Kiểm tra quyền - chỉ hiển thị khi đơn hàng đang trong quá trình giao (status 4) hoặc đã cấu hình
                        if (statusId !== 4 && statusId !== 3) {
                            notificationSection.style.display = 'none';
                        }
                    }
                    
                    // Xử lý chọn template
                    if (notificationTemplate) {
                        notificationTemplate.addEventListener('change', function() {
                            if (this.value === 'custom') {
                                customNotificationText.value = '';
                                customNotificationText.placeholder = 'Enter your custom message here...';
                                customNotificationText.focus();
                                updateCharCount(); // Update character count
                            } else if (this.value !== '') {
                                customNotificationText.value = this.value;
                                updateCharCount(); // Update character count
                            }
                        });
                    }
                    
                    // Thêm trình đếm ký tự
                    if (customNotificationText) {
                        const charCount = document.getElementById('charCount');
                        const charCounter = charCount.parentElement;
                        
                        function updateCharCount() {
                            const count = customNotificationText.value.length;
                            charCount.textContent = count;
                            
                            if (count >= 70) {
                                charCounter.classList.add('limit-reached');
                            } else {
                                charCounter.classList.remove('limit-reached');
                            }
                        }
                        
                        customNotificationText.addEventListener('input', updateCharCount);
                        updateCharCount(); // Initialize on load
                    }
                    
                    // Xử lý gửi thông báo
                    if (sendNotificationBtn) {
                        sendNotificationBtn.addEventListener('click', function() {
                            const message = customNotificationText.value.trim();
                            
                            if (!message) {
                                showNotification('error', 'Please enter a notification message');
                                return;
                            }
                            
                            // Add loading state
                            this.classList.add('btn-loading');
                            this.disabled = true;
                            
                            // Gửi thông báo tới server
                            sendCustomNotification();
                        });
                    }
                });

                // Thêm định nghĩa biến trackingData nếu chưa có
                // Lấy dữ liệu tracking từ các input hidden
                const trackingData = {
                    deliveryId: getNumericInputValue('trackingInfoDeliveryId', 0),
                    orderId: getNumericInputValue('trackingInfoOrderId', 0),
                    statusId: getNumericInputValue('trackingInfoStatusId', 0),
                    currentLat: getNumericInputValue('trackingInfoCurrentLat', 0),
                    currentLng: getNumericInputValue('trackingInfoCurrentLng', 0)
                };

                // Thêm vào phần document ready hoặc window.onload
                document.addEventListener('DOMContentLoaded', function() {
                    // Không cần gọi loadNotificationHistory ở đây nữa
                    // vì đã được xử lý trong event listener ở trên
                    
                    // ... existing code ...
                });
            </script>
        </div>
        
        <!-- Container cho các thông báo tạm thời -->
        <div id="notifications-list" class="notifications-container"></div>
        
    </body>
</html>