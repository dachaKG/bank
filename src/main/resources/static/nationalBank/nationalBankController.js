var app = angular.module('nationalBank.controllers',[]);

app.controller('nationalBankController',['$scope','nationalBankService','$location',
	function($scope, nationalBankService,$location) {
	
	/*function findAll(){
		nationalBankService.findAll().then(
			function(response){
				if(response.data.length == 0){
				//	$location.path('/nationalBank/addCertificate');
				}
			}
		)
	}
	
	findAll();*/
	
	$scope.createCertificate = function(){
		nationalBankService.createCertificate($scope.certificateRequest).then(
			function(response){
				$scope.state = undefined;
				$location.path('/addCertificate')
			}, function(response){
				alert("greska pri dodavanju");
			});
	}
	
	
	
}]);