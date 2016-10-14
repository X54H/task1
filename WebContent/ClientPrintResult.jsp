<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
	table,th,td {
	  border : 1px solid black;
	  border-collapse: collapse;
	}
	th,td {
	  padding: 5px;
	}
	</style>
<head>
<title>Task 1</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
                $.get("http://localhost:8080/task-1/servlet", function(responseText) {
               	  var table="<tr><th>AirportCode</th><th>CityOrAirportName</th><th>Country</th><th>CountryCode</th><th>Dialer</th><th>Currency</th><th>RunwayLengthFeet</th></tr>";
               	  var counter = 0;
                	$(responseText).find("Table").each(function() {	
                		if (counter % 2 === 0) {
                			counter++;
                			return;
                		} else {
                			counter++;
                		}
	                	var airportcode = $(this).find("AirportCode").text();
                		table += "<tr><td>" + airportcode + "</td><td>"
                		
	                	var cityOrAiportName = $(this).find("CityOrAirportName").text();
	                	table += cityOrAiportName + "</td><td>";
	                	
	                	var Country = $(this).find("Country").text();
	                	table += Country + "</td><td>";
	                
	                	var countryCode = $(this).find("CountryCode").text();
	                	table += countryCode + "</td><td>";
	                	
	                	var dialer = $(this).find("dialer").text();
	                	table += "+" + dialer + "</td><td>";
	                	
	                	var currency = $(this).find("Currency").text();
	                	table += currency + "</td><td>";
	                	
	                	var RunwayLengthFeet = $(this).find("RunwayLengthFeet").text();
	                	table += RunwayLengthFeet + "</td></tr>";
						
	                	document.getElementById("demo").innerHTML = table;	
                	
                	});
                });
        </script>
</head>
<body>
	<table id="demo"></table>
</body>
</html>