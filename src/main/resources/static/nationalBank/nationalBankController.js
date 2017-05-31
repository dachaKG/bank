var app = angular.module('nationalBank.controllers',[]);


app.controller('nationalBankController',['$scope','nationalBankService','$location','addCertificate',
	function($scope, nationalBankService,$location, addCertificate) {
	
	
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
	
	$scope.addCertificate = addCertificate;
	
	
	$scope.createCertificate = function(){
		
		nationalBankService.createCertificate($scope.certificateRequest).then(
			function(response){
				$scope.state = 'home.addCertificate.feedback';
				$location.path('home/addCertificate/feedback')
			}, function(response){
				alert("greska pri dodavanju");
			});
	}
	
	
	
}]);