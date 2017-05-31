var app = angular.module('signedCertificate.controllers',[]);
app.controller('signedCertificateController',['$scope','signedCertificateService','$location',
	function($scope,service,$location){
	
	$scope.createCertificate = function(){
		service.createCertificate($scope.certificateRequest)
		.then(function(response){
			$scope.certificateRequest = {};
		},
		function(response){
			
		})
	}
	

}])