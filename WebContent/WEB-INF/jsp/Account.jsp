<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Account Management</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>
<body style="padding: 20px 10%">
	<h1>accounts</h1>
	<h3 style="color: darkgray">[Info] ${ msg }</h3>
	<table class="table">
		<thead>
			<tr>
				<th>id</th>
				<th>Account</th>
				<th>status</th>
				<th>errorTimes</th>
				<th>timeModify</th>
				<th>Owner</th>
				<th>Operate</th>
			</tr>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th>
					<button type="button" class="btn btn-primary"
						onclick="location.href='account?action=uiAdd'">Add</button>
					<button type="button" class="btn btn-outline-info"
						onclick="location.href='account?action=search'">Search</button>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${ accountList }">
				<tr>
					<td>${ list.getId() }</td>
					<td>${ list.getAccount() }</td>
					<td>${ list.getStatus() == 1 ? 'Enable' : (list.getStatus() == 0 ? 'Disable' : 'Lock') }</td>
					<td>${ list.getErrorTime() }</td>
					<td>${ list.getTimeModify() }</td>
					<td>
						<c:forEach var="m" items="${ memberList }">
							${ list.getMemberId() == m.getId() ? m.getName() : '' }
						</c:forEach>
					</td>
					<td>
						<button type="button" class="btn btn-outline-primary"
							onclick="location.href='account?action=uiEdit&id=' + '${ list.getId() }'">Edit</button>
						<button type="button" class="btn btn-outline-danger"
							onclick="doRemove('${ list.getId() }')">Remove</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script>
		// 頁面載入完, 執行方法檢查是否有資訊
		window.onload = function () {
			if ('${ msg }' !== '') {
				alert('${ msg }')
			}
		}
		function doRemove (id) {
			if (confirm('是否要刪除 id ' + id)) {
				window.location.href = 'account?action=remove&id=' + id
			}
		}
	</script>
</body>
</html>