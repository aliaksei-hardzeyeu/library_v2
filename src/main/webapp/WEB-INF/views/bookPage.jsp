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
        <%--        <img src="http://127.0.0.1:8887/${book.isbn}${book.coverExtension}" height=70% alt="cover_file">--%>
    </div>

    <div class="table-values">
        BOOK-FORM

        <form id="send-values" action="${pageContext.request.contextPath}/" method="post">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="<c:out value="${book.title}"/>" required="required">${message1}<br>

            <label for="authors">Author:</label>
            <input type="text" id="authors" name="authors" value="<c:out value="${book.authors}"/>"required="required">${message2}<br>

            <label for="publisher">Publisher:</label>
            <input type="text" id="publisher" name="publisher" value="<c:out value="${book.publisher}"/>"required="required">${message3}<br>

            <label for="publDate">Publication date:</label>
            <input type="date" id="publDate" name="publDate" value="<c:out value="${book.publDate}"/>"required="required">${message4}<br>

            <label for="genres">Genre:</label>
            <input type="text" id="genres" name="genres" value="<c:out value="${book.genres}"/>"required="required">${message5}<br>

            <label for="pageCount">Page count:</label>
            <input type="number" id="pageCount" name="pageCount" value="<c:out value="${book.pageCount}"/>"required="required">${message6}<br>

            <label for="isbn">ISBN:</label>
            <input type="text" id="isbn" name="isbn" value="<c:out value="${book.isbn}"/>"required="required">${message7}<br>

            <label for="description">Description:</label>
            <input type="text" id="description" name="description" value="<c:out value="${book.des}"/>">${message8}<br>

            <label for="amount">Total amount:</label>
            <input type="number" id="amount" name="amount" value="<c:out value="${book.copiesExistingAmount}"/>" readonly><br>

            <label for="changeAmount">Change amount(Enter +n or -n):</label>
            <input type="number" id="changeAmount" name="changeAmount" value="0" required>${message9}<br>

            <input type="hidden" id="givenAmount" name="givenAmount" value="<c:out value="${book.givenAmount}"/>"><br>
<%--            for all amounts from model--%>
<%--            <input type="hidden" id="givenAmount" name="givenAmount" value="<c:out value="${book.copiesExistingAmount}"/>"><br>--%>
            <input type="hidden" id="borrowed" name="borrowed" value="<c:out value="${book.borrowed}"/>"><br>
            <input type="hidden" id="damaged" name="damaged" value="<c:out value="${book.damaged}"/>"><br>
            <input type="hidden" id="lost" name="lost" value="<c:out value="${book.lost}"/>"><br>
            <input type="hidden" id="returned" name="returned" value="<c:out value="${book.returned}"/>"><br>
            <input type="hidden" id="currentlyAvailableAmount" name="currentlyAvailableAmount" value="<c:out value="${book.currentlyAvailableAmount}"/>"><br>

            <label for="status">Status:</label>
            <input type="text" id="status" name="status" value="<c:out value="${book.status}"/>" readonly><br>


            <input type="hidden" id="bookId" name="bookId" value="<c:out value="${book.bookId}"/>"><br>

            <input type="hidden" name="action" value="update"/>

            <%--            <div>--%>
            <%--                <label for="file">Choose a file</label>--%>
            <%--                <input type="file" id="file" name="file" accept="image/*">--%>
            <%--            </div>--%>
            <input type="submit" form="send-values" name="update"/>
        </form>


        <button onclick="window.location.href='/';">
            Discard changes
        </button>
    </div>

    <div class="content-table">
        BORROWS TABLE
        <div class="flex-row">
            <div class="cell">
                Reader email
            </div>
            <div class="cell">
                Reader name
            </div>
            <div class="cell">
                Borrow date
            </div>
            <div class="cell">
                Due date
            </div>
            <div class="cell">
                Return date
            </div>
        </div>


        <c:forEach var="borrow" items="${listOfBorrows}">

            <div class="flex-row">
                <div class="cell">
                    <c:out value="${borrow.userEmail}"/>
                </div>

                <div class="cell">
                        <%--here should be modal window to switch status of borrow--%>
                    <a href="#openModal${borrow.borrowId}"><c:out value="${borrow.userName}"/></a>
                </div>

                <div class="cell">
                    <c:out value="${borrow.borrowDate}"/>
                </div>

                <div class="cell">
                    <c:out value="${borrow.dueDate}"/>
                </div>

                <div class="cell">
                    <c:out value="${borrow.returnDate}"/>
                </div>

                <div id="openModal${borrow.borrowId}" class="modal">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title">Borrow status</h3>
                            </div>

                            <div class="modal-body">
                                <form method="post" action="${pageContext.request.contextPath}/borrow">
                                    <p>Please select borrow status:</p>
                                    <div>
                                        <input type="radio" id="statusChoice1"
                                               name="status" value="returned" required>
                                        <label for="statusChoice1">Returned</label>

                                        <input type="radio" id="statusChoice2"
                                               name="status" value="damaged">
                                        <label for="statusChoice2">Returned & damaged</label>

                                        <input type="radio" id="statusChoice3"
                                               name="status" value="lost">
                                        <label for="statusChoice3">Lost</label>

                                        <input type="hidden" id="borrowId3" name="borrowId" value="${borrow.borrowId}">
                                        <input type="hidden" id="bookId2" name="bookId" value="${book.bookId}">
                                        <input type="hidden" id="action" name="action" value="changeBorrowStatus">

                                    </div>
                                    <div>
                                        <button type="submit">Submit</button>
                                    </div>
                                </form>


                                <a href="#close" title="Close" class="close">Discard</a>
                            </div>

                        </div>

                    </div>
                </div>

            </div>

        </c:forEach>

        <c:choose>
            <c:when test="${book.currentlyAvailableAmount > 0}">
                <a href="#openModal1111">!!!!Add borrow!!!!</a>
                <br />
            </c:when>
            <c:otherwise>
                U can`t borrow book, dude. No copies left.
                <br />
            </c:otherwise>
        </c:choose>


        <div id="openModal1111" class="modal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title">New borrow</h3>
                    </div>
                    <div class="modal-body">


                        <div class="table-values">
                            BORROW-FORM

                            <form id="send-borrows" action="${pageContext.request.contextPath}/borrow" method="post">
                                <label for="email">Email:</label>
                                <input type="email" id="email" name="email" value="" required="required"><br>


                                <label for="name">Name:</label>
                                <input type="text" id="name" name="name" value="" required="required"><br>


                                <p>Choose time period</p>

                                <input type="radio" id="period1" name="period" value="1" required="required">
                                <label for="period1">1 month</label><br>

                                <input type="radio" id="period2" name="period" value="2">
                                <label for="period2">2 months</label><br>

                                <input type="radio" id="period3" name="period" value="3">
                                <label for="period3">3 months</label><br>

                                <input type="radio" id="period6" name="period" value="6">
                                <label for="period6">6 month</label><br>

                                <input type="radio" id="period12" name="period" value="12">
                                <label for="period1">12 months</label><br>


                                <label for="comment">Comment:</label>
                                <input type="text" id="comment" name="comment" value=""><br>

                                <input type="hidden" id="bookIdBorrow" name="bookId" value="${book.bookId}"><br>
                                <input type="hidden" name="action" value="addBorrow"/>
                                <input type="submit" form="send-borrows" name="submit"/>

                                <a href="#close" title="Close" class="close">Discard</a>
                            </form>


                        </div>


                    </div>
                </div>
            </div>
        </div>




    </div>


</div>
<script>

</script>
</body>

</html>