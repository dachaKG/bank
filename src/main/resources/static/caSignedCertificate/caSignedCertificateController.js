var app = angular.module('caSignedCertificate.controllers',[]);

app.controller('caSignedCertificateController',['$scope','caSignedCertificateService','$location','addCaSignedCertificate',
	function($scope,caSignedCertificateService,$location, addCaSignedCertificate){
	
	$scope.addCaSignedCertificate = addCaSignedCertificate;
	
	$scope.issuersCNs =  [
	      /*{id: '1', name: 'Option A'},
	      {id: '2', name: 'Option B'},
	      {id: '3', name: 'Option C'}*/
	    ];
	$scope.issuerAliases = [/*	      {id: '1', name: 'Alias A'},
	      {id: '2', name: 'Alias B'},
	      {id: '3', name: 'Alias C'}*/];
	
	$scope.loadAliases = function(){
		caSignedCertificateService.loadAliases($scope.issuerCommonName.name)
		.then(function(response){
			$scope.issuerAliases = [];
			for(i = 0; i < response.data.length;i++){
				$scope.issuerAliases.push({id:i,name : response.data[i]})
			}
		},
		function(response){
			alert("GRESKA PRI UCITAVANJU ALIASA");
		})
	}
	
	$scope.createCertificate = function(){
		$scope.certificateRequest.issuerCommonName = $scope.issuerCommonName.name;
		$scope.certificateRequest.issuerAlias = $scope.issuerAlias.name;
		$scope.certificateRequest.certificateAuthority = 'true';

		caSignedCertificateService.createCertificate($scope.certificateRequest)
		.then(function(response){
			
		},
		function(response){
			
		})
	}
	
	function loadCNs(){
		caSignedCertificateService.loadCNs()
		.then(function(response){
			$scope.issuersCNs = [];
			for(i = 0; i < response.data.length;i++){
				$scope.issuersCNs.push({id:i,name : response.data[i]})
			}			
		},
		function(response){
			alert("GRESKA");
		})
	}
	loadCNs();
	
}]);