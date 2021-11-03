<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style1.css"/>

</head>

<body>
<div class="content-table">
    <div class="flex-row">
        <div class="cell">
            Title
        </div>
        <div class="cell">
            Author
        </div>
        <div class="cell">
            Publ Date
        </div>
        <div class="cell">
            Amount
        </div>
        <div class="cell">
            Actions
        </div>
    </div>


    <c:forEach var="book" items="${listOfBooks}">

        <div class="flex-row">
            <div class="cell">
                <form action="${pageContext.request.contextPath}/" method="get">
                    <input type="hidden" name="bookId" value="<c:out value='${book.bookId}'/>"/>
                    <input type="hidden" name="action" value="viewExisting"/>
                    <input type="submit" name="view" value="<c:out value='${book.title}'/>"/>
                </form>
            </div>
            <div class="cell">
                <c:out value="${book.authors}"/>
            </div>
            <div class="cell">
                <c:out value="${book.publDate}"/>
            </div>
            <div class="cell">
                <c:out value="${book.copiesExistingAmount}"/>
            </div>
            <div class="cell">
                <form action="${pageContext.request.contextPath}/" method="post">
                    <input type="hidden" name="bookId" value="<c:out value='${book.bookId}'/>"/>
                    <input type="hidden" name="action" value="remove"/>
                    <input type="submit" name="view" value="remove"/>
                </form>
            </div>
        </div>
    </c:forEach>


    <form class="button-add" action="${pageContext.request.contextPath}/" method="get">
        <input type="hidden" name="action" value="addNew"/>
        <input type="submit" name="add" value="ADD"/>
    </form>
</div>


</body>

</html>

<%--                <a href="<%=request.getContextPath()%>/edit_book?book_id=<c:out value='${book.book_id}'/>"><c:out value="${book.title}"/></a>
                <a href="<%=request.getContextPath()%>/remove_book?book_id=<c:out value='${book.book_id}'/>">Remove</a>

--%>