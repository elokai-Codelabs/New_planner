<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="shortcut icon" href="img/icon.png">
	<title>Route Planner</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="css/bootstrap-select.min.css" rel="stylesheet">
	<link href="css/route.css" rel="stylesheet">
	
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap-select.min.js"></script>
	<script src="js/route.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
</head>
<body>
	<center>
		<h1><a class="hypercolor" href="http://www.bbc.co.uk/london/travel/downloads/tube_map.html">Route Planner</a></h1>
	</center>
	<div class="container">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<form method="Get" accept-charset="utf-8" class="form" action="DisplayRoute"
					role="form">
					<legend></legend>
					<div class="row">
						<div class="col-xs-5 col-md-5">
							<select class="selectpicker show-tick show-menu-arrow" id="station1" data-width="100%" data-live-search="true" data-size="auto"
								name="station1">
								<c:forEach var="station" items="${stations}">
									<option value="${station}"/>${station}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-xs-2 col-md-2">
						<center>
							<button type="button" class="btn btn-default btn-md" id="swap">
								<span class="glyphicon glyphicon-refresh"></span>
							</button>
						</center>
						</div>
						<div class="col-xs-5 col-md-5">
							<select class="selectpicker show-tick show-menu-arrow" id="station2" data-width="100%" data-live-search="true" data-size="auto"
								name="station2">
								<c:forEach var="station" items="${stations}">
									<option value="${station}"/>${station}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</form>
				<div id="container"></div>	
			</div>
		</div>
	</div>
	
	
</body>
</html>