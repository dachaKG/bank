var app = angular.module('findCertificate.controllers',[]);

app.controller('findCertificateController',['$scope','findCertificateService','$location',
	function($scope, findCertificateService,$location) {
	
		$scope.findCertificate = function(serialNumber){
			findCertificateService.findCertificate(serialNumber).then(
				function(response){
					$scope.state = response.data;
				}, function(response){
					alert("ne postoji");
				}
			)
		}

}])