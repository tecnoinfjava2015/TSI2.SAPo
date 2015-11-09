//No andó. La cookie está luego de hacer login, pero no logro verla con angular
(function() {
	'use strict';
	angular.module('sapo', ['ngCookies'])
	.controller('CookieController', ['$cookies', function ($cookies) {
		alert($cookies.get("sapoUser"));
	}]
)});