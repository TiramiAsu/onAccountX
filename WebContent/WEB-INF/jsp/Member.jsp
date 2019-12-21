<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Member Management</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>
<body style="padding: 20px 10%">
	<h1>members</h1>
	<h3>[Info] ${ msg }</h3>
	<table class="table">
		<thead>
			<tr>
				<th>id</th>
				<th>Name</th>
				<th>Email</th>
				<th>Phone</th>
				<th>Operate</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th>
					<button type="button" class="btn btn-primary"
						onclick="location.href='member?action=uiAdd'">Add</button>
					<button type="button" class="btn btn-outline-info"
						onclick="location.href='member?action=search'">Search</button>
				</th>
			</tr>
			<c:forEach var="list" items="${ memberList }">
				<tr>
					<td>${ list.getId() }</td>
					<td>${ list.getName() }</td>
					<td>${ list.getEmail() }</td>
					<td>${ list.getPhone() }</td>
					<td>
						<button type="button" class="btn btn-outline-primary">Edit</button>
						<button type="button" class="btn btn-outline-danger">Remove</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>