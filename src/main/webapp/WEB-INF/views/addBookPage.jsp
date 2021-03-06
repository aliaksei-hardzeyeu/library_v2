<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Book Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style2.css"/>

</head>
<body>
<div class="main-container">
    <div class="cover">
        <%--        // using Web Server for Chrome due to security issues -> Chrome does not allow img viewing from local sources--%>
        <%--        // so we need to emulate server for local files!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!edit below--%>
                <img src="http://127.0.0.1:8887/${book.isbn}${book.coverExtension}" height=70% alt="cover_file">
    </div>

    <div class="table-values">
        BOOK-FORM
        <form id="send-values" action="${pageContext.request.contextPath}/" method="post" enctype="multipart/form-data">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${book.title}" required="required">${message1}<br>

            <label for="authors">Author:</label>
            <input type="text" id="authors" name="authors" value="${book.authors}" required="required">${message2}<br>

            <label for="publisher">Publisher:</label>
            <input type="text" id="publisher" name="publisher" value="${book.publisher}" required="required">${message3}<br>

            <label for="publDate">Publication date:</label>
            <input type="date" id="publDate" name="publDate" value="${book.publDate}" required="required">${message4}<br>

            <label for="genres">Genre:</label>
            <input type="text" id="genres" name="genres" value="${book.genres}" required="required">${message5}<br>

            <label for="pageCount">Page count:</label>
            <input type="number" id="pageCount" name="pageCount" value="${book.pageCount}" required="required">${message6}<br>

            <label for="isbn">ISBN:</label>
            <input type="text" id="isbn" name="isbn" value="${book.isbn}" required="required">${message7}<br>

            <label for="description">Description:</label>
            <input type="text" id="description" name="description" value="${book.des}"><br>

            <label for="givenAmount">Total amount:</label>
            <input type="number" id="givenAmount" name="givenAmount" required="required" value="${book.givenAmount}">${message8}<br>


            <input type="hidden" name="action" value="add"/>
                        <div>
                            <label for="file">Choose a file</label>
                            <input type="file" id="file" name="file" accept="image/*">
                        </div>
            <input type="submit" form="send-values" name="update"/>${message10}
        </form>

        <button onclick="window.location.href='/';">
            Discard changes
        </button>
    </div>
</div>
</body>

</html>
