<%-- 
    Document   : addFeedback
    Created on : Mar 15, 2025, 10:30:00 AM
    Author     : Admin
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <jsp:include page="head.jsp" />
        <title>Add Product Feedback</title>
        <style>
            .rating-container {
                direction: rtl;
                text-align: left;
                margin-bottom: 20px;
            }
            
            .rating-container input {
                display: none;
            }
            
            .rating-container label {
                color: #ddd;
                font-size: 30px;
                padding: 0 5px;
                cursor: pointer;
                display: inline-block;
            }
            
            .rating-container label:hover,
            .rating-container label:hover ~ label,
            .rating-container input:checked ~ label {
                color: #ffc107;
            }
            
            .star-label {
                cursor: pointer;
            }
            
            .product-image {
                max-width: 100px;
                max-height: 100px;
                object-fit: cover;
                border-radius: 4px;
                border: 1px solid #eee;
            }
            
            .form-card {
                border-radius: 10px;
                transition: all 0.3s ease;
            }
            
            .form-card:hover {
                box-shadow: 0 10px 20px rgba(0,0,0,0.1) !important;
            }
            
            .product-info {
                border-bottom: 1px solid #eee;
                padding-bottom: 15px;
                margin-bottom: 20px;
            }
            
            .product-name {
                font-weight: 600;
                color: #333;
            }
            
            .btn-actions {
                margin-top: 20px;
            }
            
            /* Order details styles */
            .order-details {
                background-color: #f8f9fa;
                border-radius: 8px;
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid #eaeaea;
            }
            
            .order-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-bottom: 10px;
                border-bottom: 1px solid #e0e0e0;
                margin-bottom: 15px;
            }
            
            .order-id {
                font-weight: bold;
                color: #2f3ba2;
            }
            
            .order-status {
                padding: 4px 8px;
                background-color: #28a745;
                color: white;
                border-radius: 4px;
                font-size: 12px;
            }
            
            .order-info-row {
                display: flex;
                margin-bottom: 8px;
            }
            
            .order-info-label {
                width: 150px;
                font-weight: 500;
                color: #666;
            }
            
            .order-product {
                display: flex;
                padding: 15px 0;
                border-bottom: 1px solid #e0e0e0;
            }
            
            .product-details {
                flex: 1;
                padding-left: 15px;
            }
            
            .product-price {
                display: flex;
                justify-content: space-between;
                margin-top: 8px;
                color: #666;
            }
            
            .product-category {
                color: #777;
                font-size: 12px;
            }
            
            .order-summary {
                margin-top: 15px;
                padding-top: 15px;
                border-top: 1px solid #e0e0e0;
            }
            
            .summary-row {
                display: flex;
                justify-content: space-between;
                margin-bottom: 8px;
            }
            
            .summary-total {
                font-weight: bold;
                color: #e74c3c;
                font-size: 18px;
            }
            
            .address-info {
                margin-top: 15px;
                padding: 10px;
                background-color: #fff;
                border-radius: 4px;
                border: 1px dashed #ccc;
            }
            
            .section-title {
                font-weight: 600;
                margin-bottom: 10px;
                padding-bottom: 5px;
                border-bottom: 2px solid #8AAAE5;
                color: #2f3ba2;
            }
            
            .product-card {
                border: 1px solid #eee;
                border-radius: 8px;
                padding: 15px;
                margin-bottom: 15px;
                transition: all 0.3s ease;
                cursor: pointer;
            }
            
            .product-card:hover {
                border-color: #8AAAE5;
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }
            
            .product-card.selected {
                border-color: #2f3ba2;
                background-color: #f0f4ff;
            }
            
            .product-row {
                display: flex;
                align-items: center;
            }
            
            .product-select {
                margin-right: 15px;
            }
            
            #feedbackForm {
                display: none;
                margin-top: 20px;
                padding-top: 20px;
                border-top: 1px dashed #ccc;
            }
        </style>
    </head>
    <body class="bgc">
        <div id="main-content" class="wrap">
            <!-- Include header -->
            <jsp:include page="header.jsp" />

            <!-- Main Content -->
            <div class="container mt-4">
                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="card shadow form-card">
                            <div class="card-header bg-white">
                                <h4 class="mb-0">Add Product Feedback</h4>
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty sessionScope.errorMessage}">
                                    <div class="alert alert-danger alert-dismissible fade show">
                                        ${sessionScope.errorMessage}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                    <c:remove var="errorMessage" scope="session"/>
                                </c:if>
                                
                                <!-- Order Details -->
                                <div class="order-details">
                                    <div class="order-header">
                                        <div class="order-id">
                                            <i class="fas fa-receipt"></i> Order #${orderInfo.orderId}
                                        </div>
                                        <div class="order-status">
                                            ${orderInfo.orderStatus}
                                        </div>
                                    </div>
                                    
                                    <div class="order-info">
                                        <div class="order-info-row">
                                            <div class="order-info-label">Order Date:</div>
                                            <div><fmt:formatDate value="${orderInfo.orderDate}" pattern="dd/MM/yyyy HH:mm"/></div>
                                        </div>
                                        <div class="order-info-row">
                                            <div class="order-info-label">Payment Method:</div>
                                            <div>${orderInfo.paymentMethod}</div>
                                        </div>
                                    </div>
                                    
                                    <!-- Step 1: Select Product to Review -->
                                    <div class="mt-4">
                                        <h5 class="section-title">Select Product to Review</h5>
                                        
                                        <c:forEach var="product" items="${orderDetails}" varStatus="status">
                                            <div class="product-card" onclick="selectProduct(${product.orderDetailId}, ${status.index})">
                                                <div class="product-row">
                                                    <div class="product-select">
                                                        <input type="radio" name="selectedProduct" id="product_${product.orderDetailId}" 
                                                               value="${product.orderDetailId}" class="form-check-input">
                                                    </div>
                                                    <div class="product-image-container">
                                                        <img src="${product.productImage}" alt="${product.productName}" class="product-image">
                                                    </div>
                                                    <div class="product-details">
                                                        <div class="product-name">${product.productName}</div>
                                                        <div class="product-category">Category: ${product.categoryName}</div>
                                                        <c:if test="${not empty product.optionName}">
                                                            <div class="product-option">Option: ${product.optionName}</div>
                                                        </c:if>
                                                        <div class="product-price">
                                                            <div>Price: <fmt:formatNumber value="${product.finalPrice}" type="currency"/></div>
                                                            <div>Quantity: ${product.quantity}</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                        
                                        <div class="mt-3">
                                            <button type="button" class="btn btn-primary" onclick="showFeedbackForm()">
                                                Continue to Feedback
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- Step 2: Form đánh giá sau khi chọn sản phẩm -->
                                <div id="feedbackForm">
                                    <h5 class="section-title">Your Feedback</h5>
                                    
                                    <div id="selectedProductInfo" class="mb-4">
                                        <!-- Product information will be displayed here -->
                                    </div>
                                    
                                    <form action="${pageContext.request.contextPath}/feedback/add" method="post">
                                        <input type="hidden" id="orderDetailId" name="orderDetailId" value="">
                                        <input type="hidden" name="orderId" value="${orderId}">
                                        
                                        <div class="mb-3">
                                            <label class="form-label fw-bold">Rating <span class="text-danger">*</span></label>
                                            <div class="rating-container">
                                                <input type="radio" id="star5" name="rating" value="5" checked required>
                                                <label for="star5">★</label>
                                                <input type="radio" id="star4" name="rating" value="4" required>
                                                <label for="star4">★</label>
                                                <input type="radio" id="star3" name="rating" value="3" required>
                                                <label for="star3">★</label>
                                                <input type="radio" id="star2" name="rating" value="2" required>
                                                <label for="star2">★</label>
                                                <input type="radio" id="star1" name="rating" value="1" required>
                                                <label for="star1">★</label>
                                            </div>
                                        </div>
                                        
                                        <div class="mb-3">
                                            <label for="content" class="form-label fw-bold">Your Comment</label>
                                            <textarea class="form-control" id="content" name="content" rows="5" 
                                                      placeholder="Share your thoughts about this product..."></textarea>
                                        </div>
                                        
                                        <div class="btn-actions">
                                            <a href="${pageContext.request.contextPath}/CustomerOrderHistoryController" class="btn btn-outline-secondary">
                                                Cancel
                                            </a>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fas fa-paper-plane"></i> Submit Feedback
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Include footer -->
            <jsp:include page="footer.jsp" />
        </div>
        
        <script>
            // Mảng lưu trữ thông tin sản phẩm
            const products = [
                <c:forEach var="product" items="${orderDetails}" varStatus="status">
                {
                    orderDetailId: ${product.orderDetailId},
                    productName: "${product.productName}",
                    productImage: "${product.productImage}",
                    categoryName: "${product.categoryName}",
                    optionName: "${product.optionName}",
                    finalPrice: ${product.finalPrice},
                    quantity: ${product.quantity}
                }<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            
            console.log("Loaded products:", products);
            
            let selectedProductIndex = -1;
            
            // Hàm chọn sản phẩm
            function selectProduct(orderDetailId, index) {
                console.log("Selecting product with orderDetailId:", orderDetailId, "index:", index);
                
                // Bỏ chọn tất cả
                document.querySelectorAll('.product-card').forEach(card => {
                    card.classList.remove('selected');
                });
                
                // Chọn sản phẩm được click
                const radioElement = document.getElementById('product_' + orderDetailId);
                if (radioElement) {
                    radioElement.checked = true;
                    console.log("Radio element checked:", radioElement.checked);
                } else {
                    console.error("Radio element not found for orderDetailId:", orderDetailId);
                }
                
                // Chọn card tương ứng - sửa lại cách tìm card
                try {
                    const productCards = document.querySelectorAll('.product-card');
                    if (index < productCards.length) {
                        productCards[index].classList.add('selected');
                        console.log("Added 'selected' class to card at index:", index);
                    } else {
                        console.error("Card index out of range:", index, "total cards:", productCards.length);
                    }
                } catch (error) {
                    console.error("Error selecting product card:", error);
                }
                
                selectedProductIndex = index;
            }
            
            // Thêm event listener cho từng product card khi trang tải xong
            document.addEventListener('DOMContentLoaded', function() {
                const productCards = document.querySelectorAll('.product-card');
                console.log("Total product cards found:", productCards.length);
                
                productCards.forEach((card, index) => {
                    const radioInput = card.querySelector('input[type="radio"]');
                    if (radioInput) {
                        const orderDetailId = radioInput.value;
                        card.addEventListener('click', function() {
                            selectProduct(orderDetailId, index);
                        });
                    }
                });
                
                // Nếu chỉ có 1 sản phẩm, tự động chọn
                if (productCards.length === 1) {
                    const radioInput = productCards[0].querySelector('input[type="radio"]');
                    if (radioInput) {
                        selectProduct(radioInput.value, 0);
                    }
                }
            });
            
            // Hiển thị form đánh giá sau khi chọn sản phẩm
            function showFeedbackForm() {
                const selectedProductRadio = document.querySelector('input[name="selectedProduct"]:checked');
                console.log("Selected product radio:", selectedProductRadio);
                
                if (!selectedProductRadio) {
                    alert('Please select a product to review!');
                    return;
                }
                
                const orderDetailId = selectedProductRadio.value;
                console.log("Selected orderDetailId:", orderDetailId);
                document.getElementById('orderDetailId').value = orderDetailId;
                
                // Hiển thị thông tin sản phẩm đã chọn
                if (selectedProductIndex < 0 || selectedProductIndex >= products.length) {
                    console.error("Invalid selectedProductIndex:", selectedProductIndex);
                    // Tìm product bằng orderDetailId thay vì dựa vào index
                    let found = false;
                    for (let i = 0; i < products.length; i++) {
                        if (products[i].orderDetailId == orderDetailId) {
                            selectedProductIndex = i;
                            found = true;
                            break;
                        }
                    }
                    
                    if (!found) {
                        alert('Lỗi khi tìm thông tin sản phẩm. Vui lòng thử lại.');
                        return;
                    }
                }
                
                const product = products[selectedProductIndex];
                console.log("Selected product:", product);
                
                // Tạo nội dung HTML bằng DOM API
                let productInfoDiv = document.getElementById('selectedProductInfo');
                productInfoDiv.innerHTML = '';
                
                let productRow = document.createElement('div');
                productRow.className = 'product-row';
                
                // Thêm ảnh sản phẩm
                let imgContainer = document.createElement('div');
                imgContainer.className = 'product-image-container';
                let img = document.createElement('img');
                img.src = product.productImage;
                img.alt = product.productName;
                img.className = 'product-image';
                imgContainer.appendChild(img);
                productRow.appendChild(imgContainer);
                
                // Thêm thông tin sản phẩm
                let detailsDiv = document.createElement('div');
                detailsDiv.className = 'product-details';
                
                // Tên sản phẩm
                let nameDiv = document.createElement('div');
                nameDiv.className = 'product-name';
                nameDiv.textContent = product.productName;
                detailsDiv.appendChild(nameDiv);
                
                // Danh mục
                let categoryDiv = document.createElement('div');
                categoryDiv.className = 'product-category';
                categoryDiv.textContent = 'Category: ' + product.categoryName;
                detailsDiv.appendChild(categoryDiv);
                
                // Tùy chọn (nếu có)
                if (product.optionName) {
                    let optionDiv = document.createElement('div');
                    optionDiv.className = 'product-option';
                    optionDiv.textContent = 'Option: ' + product.optionName;
                    detailsDiv.appendChild(optionDiv);
                }
                
                // Giá và số lượng
                let priceDiv = document.createElement('div');
                priceDiv.className = 'product-price';
                
                let priceLabelDiv = document.createElement('div');
                priceLabelDiv.textContent = 'Price: $' + product.finalPrice.toFixed(2);
                
                let quantityDiv = document.createElement('div');
                quantityDiv.textContent = 'Quantity: ' + product.quantity;
                
                priceDiv.appendChild(priceLabelDiv);
                priceDiv.appendChild(quantityDiv);
                detailsDiv.appendChild(priceDiv);
                
                productRow.appendChild(detailsDiv);
                productInfoDiv.appendChild(productRow);
                
                // Hiển thị form đánh giá
                document.getElementById('feedbackForm').style.display = 'block';
                
                // Scroll đến form đánh giá
                document.getElementById('feedbackForm').scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
            
            // Thêm sự kiện để xử lý việc chọn sao khi form hiển thị
            document.addEventListener('DOMContentLoaded', function() {
                // Auto-dismiss alerts after 5 seconds
                setTimeout(function() {
                    const alerts = document.querySelectorAll('.alert');
                    alerts.forEach(function(alert) {
                        const closeBtn = new bootstrap.Alert(alert);
                        closeBtn.close();
                    });
                }, 5000);
            });
        </script>
    </body>
</html> 