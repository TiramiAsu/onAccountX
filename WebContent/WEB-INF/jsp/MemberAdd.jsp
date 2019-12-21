<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Member Add</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>
<body style="padding: 20px 10%">
	<h1>members</h1>
	<h3 style="color: darkgray">[info] Add Member...</h3>
		<form method="post" action="./member">
			<div class="form-group">
				<label>Name</label>
				<input type="text" class="form-control" name="name">
			</div>
			<div class="form-group">
				<label>Email</label>
				<input type="email" class="form-control" name="email">
			</div>
			<div class="form-group">
				<label>Phone</label>
				<input type="text" class="form-control" name="phone">
			</div>
			<button type="button" class="btn btn-outline-dark" onclick="doCancel()">Cancel</button>
			<button type="submit" class="btn btn-primary" onclick="doConfirm()">Finish</button>
		</form>
	<script>
		function doConfirm() {
			if (confirm('是否要新增帳號')) {
				return true
			}
			return false
		}
		function doCancel() {
			if (confirm('是否要取消新增帳號?')) {
				window.location.href = 'member?action=search'
			}
		}
	</script>
</body>
</html>