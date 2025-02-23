<%-- 
    Document   : upost
    Created on : Feb 22, 2025, 4:27:10 PM
    Author     : NgocNNCE181950
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="assets/css/upost.css">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h1>Add Post</h1>
            <div class="form-container">
                <form action="CustomerUpdatePostController" method="post">
                    <input type="hidden" value="${data.postId}" name="postId" /><br/>
                    <label for="title">Title</label>
                    <input id="title" type="text" value="${data.title}" name="title" />
                    <label for="content">Content</label>
                    <input id="content" type="text" value="${data.content}" name="content" />
                    <input type="submit" value="Update Post"><br/>
                </form>
                <p>
                    ${msg}
                </p>
            </div>
        </div>
    </body>
</html>
