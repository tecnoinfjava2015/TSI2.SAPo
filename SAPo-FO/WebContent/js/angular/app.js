angular.module("app",["ngRoute","ngResource"])
.config(function($routeProvider){
	   $routeProvider
	   .when(":tenat/product/create",{
	    	controller: "productsFormCtrl",
	    	templateUrl: "pages/createProduct.html"
	    }).otherwise("/")
});