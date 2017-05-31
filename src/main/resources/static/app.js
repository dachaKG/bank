'use strict';


angular.module('routerApp', ['ui.router',
	'nationalBank.services', 'nationalBank.controllers',
	'findCertificate.services','findCertificate.controllers', 
	'nationalBank.services', 'nationalBank.controllers',
	'caSignedCertificate.services','caSignedCertificate.controllers',
	'signedCertificate.services','signedCertificate.controllers',
	'revokeCertificate.services','revokeCertificate.controllers',
	'csr.services','csr.controllers',
	'user.services', 'user.controllers']) 


.config(function($stateProvider,$urlRouterProvider){
	
	$urlRouterProvider.otherwise('/home');
	
	
	$stateProvider
	.state('home.nationalBank',{
		url : '/nationalBank',
		templateUrl : 'nationalBank/nationalBankPartial.html',
		controller : 'nationalBankController'
	})
	.state('home.addCertificate',{
		url : '/addCertificate',
		templateUrl : 'nationalBank/addCertificate.html',
		controller : 'nationalBankController'
	})
	.state('home.addCaSignedCertificate',{
		url : '/addCaSignedCertificate',
		templateUrl : 'caSignedCertificate/caSignedCertificate.html',
		controller : 'caSignedCertificateController'
	})
	.state('home.addSignedCertificate',{
		url : '/addSignedCertificate',
		templateUrl : 'signedCertificate/signedCertificate.html',
		controller : 'signedCertificateController'
	})
	
	.state('home.addNationalBank',{
		url : '/addNationalBank',
		templateUrl : 'nationalBank/addNationalBank.html',
		controller : 'nationalBankController'
	})
	.state('home.findCertificate',{
		url : '/getExistingCertificate',
		templateUrl : 'findExistingCertificate/findCertificate.html',
		controller : 'findCertificateController'
	})
	.state('home.revokeCertificate',{
		url : '/revokeCertificate',
		templateUrl : 'revokeCertificate/revokeCertificate.html',
		controller : 'revokeCertificateController'
	})	
	.state('home.csr',{
		url : '/csr',
		templateUrl : 'csr/signCsr.html',
		controller : 'csrController'
	})		
	.state('home.changePassword',{
		url : '/changePassword',
		templateUrl : 'user/changePassword.html',
		controller : 'userController'
	})
	.state('home',{
		url : '/home',
		templateUrl : 'home.html',
		controller : 'userController'
	})
	
	/*.state('country', {
		url : '/country',
		templateUrl : 'country/countryList.html',
		controller : 'countryController'
	})
	.state('addCountry',{
		url : '/addCountry',
		templateUrl : 'country/addCountry.html',
		controller : 'countryController'
	})
	.state('nextPlaces',{
		url : '/nextPlaces',
		templateUrl  : 'country/nextPlaces.html',
		controller : 'countryController'
	})
	.state('places', {
		url : '/places',
		templateUrl : 'place/placeList.html',
		controller : 'placeController'
	})*/
}).service('appService', ['$http', function($http){
	
	this.userDetails = function(){
		return $http.get("/userDetails");
	}
	
}]).controller('appController',['$scope','appService','$location',
	function($scope, appService,$location, Authentication) {
		
		$scope.authorities = [];
		userDetails();
		$scope.role_admin = false;
		$scope.role_banker = false;
		$scope.role_user = false;
		
		$scope.toggleShowDiv = function(){
		    console.log('fired');
		    $scope.role_banker = !$scope.role_banker;
		  }
		
		function userDetails(){
			appService.userDetails().then(
				function(response){
					for(var i = 0 ; i < response.data.authorities.length; i++){
						$scope.authorities.push(response.data.authorities[i].authority);
					}
					
					auth();
					console.log($scope.authorities);
					// $scope.authorities.push(response.data.authorities);
				}, function(response){
					alert("greska pri logout-u");
				}
			)
		}
		
		function auth(){
			for(var i = 0 ; i < $scope.authorities.length; i++){
				if($scope.authorities[i] == "ROLE_ADMIN"){
					$scope.role_admin = true;
				} else if($scope.authorities[i] == "ROLE_BANKER"){
					$scope.role_banker = true;
				} else {
					$scope.role_user = true;
				}
			}
		}

}]);
