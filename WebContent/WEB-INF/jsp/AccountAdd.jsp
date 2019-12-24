<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Account Add</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>
<body style="padding: 20px 10%">
	<h1>accounts</h1>
	<h3 style="color: darkgray">[info] ${ param.action == "uiAdd" ? "Add" : "Edit" } Account...</h3>
		<form method="post" action="./account">
			<input type="hidden" name="action" value="${ param.action == 'uiAdd' ? 'add' : 'edit' }">
			<input type="hidden" name="id" value="${ param.action == 'uiAdd' ? '' : account.getId() }">
			<div class="form-group">
				<label>Name</label>
				<select name="memberId" class="custom-select">
					<option value="-1" selected disabled>
						${ param.action == 'uiAdd' ? '請選擇...' : member.getName() }
					</option>
					<c:forEach var="list" items="${ memberList }">
						<option value="${ list.getId()}">${ list.getName() }</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label>Account</label>
				<input type="text" class="form-control" name="account"
					value="${ param.action == 'uiAdd' ? '' : account.getAccount() }">
			</div>
			<div class="form-group">
				<label>Password</label>
				<input type="password" class="form-control" name="password"
					value="${ param.action == 'uiAdd' ? '' : account.getPassword() }">
			</div>
			<div class="form-group">
				<label>Status</label>
				<input type="text" class="form-control" name="status" readonly
					value="${ param.action == 'uiAdd' ? '已啟用' : account.getStatus() == 1 ? '已啟用' : (account.getStatus() == 0 ? '停用' : '已鎖定') }">
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
				window.location.href = 'account?action=search'
			}
		}
	</script>
</body>
</html>