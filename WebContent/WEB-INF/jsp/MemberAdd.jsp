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
	<h3 style="color: darkgray">[info] ${ param.action == "uiAdd" ? "Add" : "Edit" } Member...</h3>
		<form method="post" action="./member">
			<input type="hidden" name="action" value="${ param.action == 'uiAdd' ? 'add' : 'edit' }">
			<input type="hidden" name="id" value="${ member.getId() }">
			<div class="form-group">
				<label>Name</label>
				<input type="text" class="form-control" name="name"
					value="${ param.action == 'uiAdd' ? '' : member.getName() }">
			</div>
			<div class="form-group">
				<label>Email</label>
				<input type="email" class="form-control" name="email"
					value="${ param.action == 'uiAdd' ? '' : member.getEmail() }">
			</div>
			<div class="form-group">
				<label>Phone</label>
				<input type="text" class="form-control" name="phone"
					value="${ param.action == 'uiAdd' ? '' : member.getPhone() }">
			</div>
			<button type="button" class="btn btn-outline-dark" onclick="doCancel()">Cancel</button>
			<button type="submit" class="btn btn-primary" onclick="return doConfirm()">Finish</button>
		</form>
	<script>
		function doConfirm() {
			if (confirm('是否要' + "${ param.action == 'uiAdd' ? '新增' : '更新' }" + '帳號?')) {
				return true
			}
			return false
		}
		function doCancel() {
			if (confirm('是否要取消' + "${ param.action == 'uiAdd' ? '新增' : '更新' }" + '帳號?' )) {
				window.location.href = 'member?action=search'
			}
		}
	</script>
</body>
</html>