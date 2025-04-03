<%-- 
    Document   : community
    Created on : Feb 17, 2025, 9:30:00 AM
    Author     : tvhun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Pet Community - Pet Heaven</title>
        <link rel="stylesheet" href="assets/css/global.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/community.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="icon" href="Pet Heaven.png" type="image/png">
        <style>
            .container.main-content {
                max-width: 800px;
                margin: 0 auto;
            }
            
            .user-profile {
                display: flex;
                align-items: center;
                margin-bottom: 20px;
            }
            
            .user-profile .user-avatar {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                margin-right: 15px;
            }
            
            .user-profile h4 {
                margin: 0;
                font-size: 18px;
            }
            
            .login-buttons {
                margin-left: auto;
            }
        </style>
    </head>
    <body>
        <!-- HEADER -->
        <header class="taskbar">
            <nav class="container">
                <div class="logo">
                    <a href="/Home"><img src="assets/images/Pet Heaven.png" alt="PetShop" /></a>
                </div>
                <div class="menu" data-show="0">
                    <div class="d-flex h-100 justify-content-center align-items-center">
                        <ul>
                            <li class="search-item">
                                <form class="search-form" action="#" method="get">
                                    <input type="text" placeholder="Search..." />
                                    <button type="submit"><i class="bx bx-search"></i></button>
                                </form>
                            </li>

                            <li class="products">
                                <a href="/ProductList">
                                    <i class='bx bx-archive'></i>
                                </a>
                                <div class="product-dropdown">
                                    <div class="dropdown-grid">
                                        <c:forEach var="category" items="${categories}" varStatus="status">
                                            <div class="dropdown-category">
                                                <h4>${category.categoryName}</h4>
                                                <ul>
                                                    <li><a href="/ProductList?category=${category.categoryId}">${category.categoryName}</a></li>
                                                </ul>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </li>
                            <li class="community">
                                <a href="news.jsp"><i class="bx bx-globe"></i></a>
                            </li>
                            <li class="partner">
                                <a href="partner.jsp"><i class="bx bx-bell"></i></a>
                            </li>
                            <li>
                                <a href="about.jsp" class="account-link">
                                    <i class="bx bx-user"></i>
                                    <span class="account-text"></span>
                                </a>
                            </li>
                            <li>
                                <a href="/Cart" class="cart-link" style="position: relative;">
                                    <i class="bx bx-cart"></i>
                                    <span class="cart-badge" id="cartCountDisplay">${cartCount}</span>
                                    <span class="cart-text"></span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="d-flex align-items-center d-block d-lg-none">
                    <button class="res-menu d-block d-lg-none">
                        <span></span>
                        <span></span>
                        <span></span>
                    </button>
                </div>
            </nav>
        </header>

        <div class="container main-content">
            <!-- User profile section -->
            <c:choose>
                <c:when test="${isLoggedIn}">
                    <div class="user-profile">
                        <c:choose>
                            <c:when test="${not empty sessionScope.account.profileImage}">
                                <img src="${sessionScope.account.profileImage}" alt="User Avatar" class="user-avatar">
                            </c:when>
                            <c:otherwise>
                                <img src="assets/images/avatar-default.jpg" alt="User Avatar" class="user-avatar">
                            </c:otherwise>
                        </c:choose>
                        <h4>${sessionScope.account.username}</h4>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="user-profile">
                        <img src="assets/images/avatar-default.jpg" alt="Guest" class="user-avatar">
                        <h4>Guest</h4>
                        <div class="login-buttons">
                            <a href="login" class="btn btn-primary btn-sm">Login</a>
                            <a href="register" class="btn btn-outline-primary btn-sm">Register</a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
            
            <!-- Form đăng bài - Chỉ hiển thị khi đã đăng nhập -->
            <c:if test="${isLoggedIn}">
                <div class="create-post">
                    <div class="post-header">
                        <c:choose>
                            <c:when test="${not empty sessionScope.account.profileImage}">
                                <img src="${sessionScope.account.profileImage}" alt="User Avatar" class="user-avatar">
                            </c:when>
                            <c:otherwise>
                                <img src="assets/images/avatar-default.jpg" alt="User Avatar" class="user-avatar">
                            </c:otherwise>
                        </c:choose>
                        <div class="post-input">
                            <form action="CreatePost" method="POST" enctype="multipart/form-data">
                                <textarea name="postContent" placeholder="What's your pet up to?"></textarea>
                                <div class="post-attachments">
                                    <label class="attachment-btn">
                                        <i class='bx bx-image'></i> Photo/Video
                                        <input type="file" name="postImage" hidden accept="image/*">
                                    </label>
                                    <label class="attachment-btn feeling-btn">
                                        <i class='bx bx-happy-beaming'></i> Feeling
                                        <div class="feeling-dropdown">
                                            <div class="feeling-item" data-feeling="happy">😊 Feeling happy</div>
                                            <div class="feeling-item" data-feeling="joyful">😄 Feeling joyful</div>
                                            <div class="feeling-item" data-feeling="sad">😢 Feeling sad</div>
                                            <div class="feeling-item" data-feeling="excited">🥳 Feeling excited</div>
                                            <div class="feeling-item" data-feeling="tired">😴 Feeling tired</div>
                                        </div>
                                        <input type="hidden" name="feeling" id="selectedFeeling">
                                    </label>
                                </div>
                                <button type="submit" class="post-btn">Post</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:if>
            
            <!-- Thông báo đăng nhập để đăng bài - Hiển thị khi chưa đăng nhập -->
            <c:if test="${!isLoggedIn}">
                <div class="login-prompt">
                    <div class="card mb-3">
                        <div class="card-body text-center">
                            <i class='bx bx-user-circle fs-1'></i>
                            <h5 class="mt-3">Login to share about your pets</h5>
                            <p>Join the pet-loving community, share and interact with fellow enthusiasts!</p>
                            <a href="login" class="btn btn-primary">Login Now</a>
                        </div>
                    </div>
                </div>
            </c:if>

            <!-- Danh sách bài đăng -->
            <div class="post-list">
                <c:forEach var="post" items="${posts}">
                    <div class="post-card" data-post-id="${post.postId}">
                        <div class="post-header">
                            <c:choose>
                                <c:when test="${not empty post.authorAvatar}">
                                    <img src="${post.authorAvatar}" alt="User Avatar" class="user-avatar">
                                </c:when>
                                <c:otherwise>
                                    <img src="assets/images/avatar-default.jpg" alt="User Avatar" class="user-avatar">
                                </c:otherwise>
                            </c:choose>
                            <div class="post-info">
                                <h5>${post.authorName}</h5>
                                <p class="post-time">
                                    <fmt:formatDate value="${post.createdDate}" pattern="MM/dd/yyyy HH:mm" />
                                    <c:if test="${not empty post.feelingActivity}">
                                        - feeling ${post.feelingActivity}
                                    </c:if>
                                </p>
                            </div>
                            <div class="post-options">
                                <i class='bx bx-dots-horizontal-rounded'></i>
                                <div class="options-dropdown">
                                    <ul>
                                        <c:if test="${isLoggedIn}">
                                            <li><a href="#"><i class='bx bx-bookmark'></i> Save post</a></li>
                                            <c:if test="${post.authorId == sessionScope.account.accountId}">
                                                <li><a href="#" class="edit-post-btn"><i class='bx bx-edit'></i> Edit</a></li>
                                                <li><a href="#" class="delete-post-btn text-danger"><i class='bx bx-trash'></i> Delete</a></li>
                                            </c:if>
                                            <li><a href="#"><i class='bx bx-hide'></i> Hide post</a></li>
                                        </c:if>
                                        <c:if test="${!isLoggedIn}">
                                            <li><a href="login"><i class='bx bx-log-in'></i> Login to interact</a></li>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="post-content">
                            <p>${post.content}</p>
                            <c:if test="${not empty post.postImage}">
                                <div class="post-image">
                                    <img src="${post.postImage}" alt="Post Image" onclick="openImageModal(this.src)">
                                </div>
                            </c:if>
                        </div>
                        <div class="post-footer">
                            <div class="reactions-summary">
                                <div class="reaction-icons">
                                    <c:if test="${post.reactionCount > 0}">
                                        <span class="reaction-icon like"><i class='bx bxs-like'></i></span>
                                        <span class="reaction-icon love"><i class='bx bxs-heart'></i></span>
                                        <span class="reaction-count">${post.reactionCount}</span>
                                    </c:if>
                                    <c:if test="${post.reactionCount == 0}">
                                        <span>Be the first to like this post</span>
                                    </c:if>
                                </div>
                                <span class="comment-count">${post.commentCount} comments</span>
                            </div>
                            <div class="reaction-buttons">
                                <c:choose>
                                    <c:when test="${isLoggedIn}">
                                        <!-- Hiển thị nút reaction đầy đủ cho người dùng đã đăng nhập -->
                                        <button class="reaction-btn ${post.userReacted ? 'active' : ''}" 
                                                data-reaction="like" 
                                                data-post-id="${post.postId}" 
                                                ${post.userReacted ? 'data-reaction-type="'.concat(post.userReactionType).concat('"') : ''}>
                                            <!-- Hiển thị icon phù hợp dựa trên reaction type -->
                                            <c:choose>
                                                <c:when test="${post.userReacted}">
                                                    <i class='${post.userReactionType == 1 ? "bx bxs-like" : post.userReactionType == 2 ? "bx bxs-heart" : post.userReactionType == 3 ? "bx bxs-laugh" : post.userReactionType == 4 ? "bx bxs-surprised" : post.userReactionType == 5 ? "bx bxs-sad" : "bx bxs-angry"}'></i>
                                                    ${post.userReactionType == 1 ? "Liked" : post.userReactionType == 2 ? "Loved" : post.userReactionType == 3 ? "Haha" : post.userReactionType == 4 ? "Wow" : post.userReactionType == 5 ? "Sad" : "Angry"}
                                                </c:when>
                                                <c:otherwise>
                                                    <i class='bx bx-like'></i> Like
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="reaction-picker">
                                                <div class="reaction-item" data-type="1"><i class='bx bxs-like'></i></div>
                                                <div class="reaction-item" data-type="2"><i class='bx bxs-heart'></i></div>
                                                <div class="reaction-item" data-type="3"><i class='bx bxs-laugh'></i></div>
                                                <div class="reaction-item" data-type="4"><i class='bx bxs-surprised'></i></div>
                                                <div class="reaction-item" data-type="5"><i class='bx bxs-sad'></i></div>
                                                <div class="reaction-item" data-type="6"><i class='bx bxs-angry'></i></div>
                                            </div>
                                        </button>
                                        <button class="reaction-btn" data-post-id="${post.postId}" data-reaction="comment">
                                            <i class='bx bx-comment'></i> Comment
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Hiển thị nút yêu cầu đăng nhập cho người dùng chưa đăng nhập -->
                                        <a href="login" class="reaction-btn">
                                            <i class='bx bx-like'></i> Login to like
                                        </a>
                                        <a href="login" class="reaction-btn">
                                            <i class='bx bx-comment'></i> Login to comment
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            
                            <!-- Phần bình luận -->
                            <div class="comments-section">
                                <c:if test="${isLoggedIn}">
                                    <div class="comment-input">
                                        <c:choose>
                                            <c:when test="${not empty sessionScope.account.profileImage}">
                                                <img src="${sessionScope.account.profileImage}" alt="User Avatar" class="user-avatar">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="assets/images/avatar-default.jpg" alt="User Avatar" class="user-avatar">
                                            </c:otherwise>
                                        </c:choose>
                                        <form action="AddComment" method="POST" class="comment-form">
                                            <input type="hidden" name="postId" value="${post.postId}">
                                            <input type="text" name="commentContent" placeholder="Write a comment..." required>
                                            <button type="submit"><i class='bx bx-send'></i></button>
                                        </form>
                                    </div>
                                </c:if>
                                <c:if test="${!isLoggedIn}">
                                    <div class="login-to-comment">
                                        <a href="login">Login to comment</a>
                                    </div>
                                </c:if>
                                
                                <div class="comments-list">
                                    <c:if test="${post.commentCount > 3 && not empty post.comments && post.comments.size() > 0}">
                                        <div class="view-more-comments">
                                            <a href="javascript:void(0)" class="view-comments-btn" data-post-id="${post.postId}">
                                                View more comments (${post.commentCount - post.comments.size()} more comments)
                                            </a>
                                        </div>
                                    </c:if>
                                    
                                    <c:forEach var="comment" items="${post.comments}">
                                        <div class="comment-item" data-comment-id="${comment.commentId}">
                                            <c:choose>
                                                <c:when test="${not empty comment.authorAvatar}">
                                                    <img src="${comment.authorAvatar}" alt="User Avatar" class="user-avatar">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="assets/images/avatar-default.jpg" alt="User Avatar" class="user-avatar">
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="comment-content">
                                                <div class="comment-bubble">
                                                    <h6>${comment.authorName}</h6>
                                                    <p>${comment.content}</p>
                                                </div>
                                                <div class="comment-actions">
                                                    <c:if test="${isLoggedIn}">
                                                        <a href="#" class="comment-like">Like</a>
                                                        <a href="#" class="comment-reply" data-comment-id="${comment.commentId}">Reply</a>
                                                    </c:if>
                                                    <span class="comment-time">
                                                        <fmt:formatDate value="${comment.createdDate}" pattern="MM/dd/yyyy HH:mm" />
                                                    </span>
                                                </div>
                                                
                                                <!-- Form trả lời bình luận (ẩn mặc định, chỉ hiển thị khi đã đăng nhập) -->
                                                <c:if test="${isLoggedIn}">
                                                    <div class="reply-form-container" style="display: none;">
                                                        <div class="comment-input">
                                                            <c:choose>
                                                                <c:when test="${not empty sessionScope.account.profileImage}">
                                                                    <img src="${sessionScope.account.profileImage}" alt="User Avatar" class="user-avatar">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img src="assets/images/avatar-default.jpg" alt="User Avatar" class="user-avatar">
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <form action="AddComment" method="POST" class="comment-form">
                                                                <input type="hidden" name="postId" value="${post.postId}">
                                                                <input type="hidden" name="parentCommentId" value="${comment.commentId}">
                                                                <input type="text" name="commentContent" placeholder="Reply to ${comment.authorName}..." required>
                                                                <button type="submit"><i class='bx bx-send'></i></button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                
                                                <!-- Hiển thị các phản hồi -->
                                                <c:if test="${not empty comment.replies}">
                                                    <div class="replies-container" data-parent-id="${comment.commentId}">
                                                        <c:forEach var="reply" items="${comment.replies}">
                                                            <div class="comment-item reply-item" data-comment-id="${reply.commentId}" data-parent-id="${reply.parentCommentId}">
                                                                <c:choose>
                                                                    <c:when test="${not empty reply.authorAvatar}">
                                                                        <img src="${reply.authorAvatar}" alt="User Avatar" class="user-avatar">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <img src="assets/images/avatar-default.jpg" alt="User Avatar" class="user-avatar">
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <div class="comment-content">
                                                                    <div class="comment-bubble">
                                                                        <h6>${reply.authorName}</h6>
                                                                        <p>${reply.content}</p>
                                                                    </div>
                                                                    <div class="comment-actions">
                                                                        <c:if test="${isLoggedIn}">
                                                                            <a href="#" class="comment-like">Like</a>
                                                                            <a href="#" class="comment-reply" data-comment-id="${reply.commentId}">Reply</a>
                                                                        </c:if>
                                                                        <span class="comment-time">
                                                                            <fmt:formatDate value="${reply.createdDate}" pattern="MM/dd/yyyy HH:mm" />
                                                                        </span>
                                                                    </div>
                                                                    
                                                                    <!-- Form trả lời bình luận (nested) -->
                                                                    <c:if test="${isLoggedIn}">
                                                                        <div class="reply-form-container" style="display: none;">
                                                                            <div class="comment-input">
                                                                                <c:choose>
                                                                                    <c:when test="${not empty sessionScope.account.profileImage}">
                                                                                        <img src="${sessionScope.account.profileImage}" alt="User Avatar" class="user-avatar">
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <img src="assets/images/avatar-default.jpg" alt="User Avatar" class="user-avatar">
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                                <form action="AddComment" method="POST" class="comment-form">
                                                                                    <input type="hidden" name="postId" value="${post.postId}">
                                                                                    <input type="hidden" name="parentCommentId" value="${reply.commentId}">
                                                                                    <input type="text" name="commentContent" placeholder="Reply to ${reply.authorName}..." required>
                                                                                    <button type="submit"><i class='bx bx-send'></i></button>
                                                                                </form>
                                                                            </div>
                                                                        </div>
                                                                    </c:if>
                                                                    
                                                                    <!-- Nested replies container -->
                                                                    <div class="replies-container" data-parent-id="${reply.commentId}"></div>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </c:if>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                
                <!-- Hiển thị thông báo nếu không có bài viết -->
                <c:if test="${empty posts}">
                    <div class="no-posts-message">
                        <div class="empty-state">
                            <i class='bx bx-camera'></i>
                            <h4>No posts yet</h4>
                            <p>Be the first to share a moment about your pet!</p>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        
        <!-- Modal xem ảnh lớn - Có thể xem không cần đăng nhập -->
        <div class="image-modal" id="imageModal">
            <span class="close-modal">&times;</span>
            <img class="modal-content" id="modalImage">
        </div>

        <!-- FOOTER -->
        <footer class="w-100" style="background:#8AAAE5">
            <div class="container">
                <div class="row mt-3 mb-3">
                    <div class="col-md-6">
                        <h5>
                            General Information
                            <span class="line-remove" style="width: 78px;"></span>
                        </h5>
                        <h4 class="mt-2 pt-2 com-name">Pet Heaven</h4>
                        <p class="com-phone">
                            <i class="fas fa-phone-alt"></i>
                            <a href="#" title="0999.999.999">0999 999 999</a>
                        </p>
                        <p class="com-email">
                            <i class="fas fa-envelope"></i>
                            <a href="#" title="cskh@petheaven.vn">cskh@petheaven.vn</a>
                        </p>
                        <address class="com-address">
                            <i style="width: 22px;" class="fas fa-map-marker-alt"></i>Ninh Kiều, Cần Thơ 
                        </address>
                    </div>
                    <div class="col-md-3">
                        <h5>
                            ABOUT US
                            <span class="line-remove" style="width: 78px;"></span>
                        </h5>
                        <ul>
                            <li><a href="about.html" title="Giới thiệu">About </a></li>
                            <li><a href="product.html" title="Sản phẩm">Products</a></li>
                            <li><a href="news.html" title="Tin tức">Community</a></li>
                            <li><a href="contact.html" title="Đối tác">Contact</a></li>
                        </ul>
                    </div>
                    <div class="col-md-3">
                        <h5>
                            CONNECT WITH US
                            <span class="line-remove" style="width: 78px;"></span>
                        </h5>
                        <div class="mt-4 social-icon">
                            <a href="#" target="_blank">
                                <i class="fab fa-facebook-square"></i>
                            </a>
                            <a href="#" target="_blank">
                                <i class="far fa-envelope"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </footer>

        <!-- jQuery & Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="assets/js/community.js"></script>
        <script type="text/javascript" src="assets/js/javascript.js"></script>
        <script type="text/javascript" src="assets/js/dropdown.js"></script>
    </body>
</html>