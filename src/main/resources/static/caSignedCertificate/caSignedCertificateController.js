var app = angular.module('caSignedCertificate.controllers',[]);

app.controller('caSignedCertificateController',['$scope','caSignedCertificateService','$location',
	function($scope,caSignedCertificateService,$location){
	
	$scope.issuersCNs =  [
	      {id: '1', name: 'Option A'},
	      {id: '2', name: 'Option B'},
	      {id: '3', name: 'Option C'}
	    ];
	$scope.issuerAliases = [	      {id: '1', name: 'Alias A'},
	      {id: '2', name: 'Alias B'},
	      {id: '3', name: 'Alias C'}];
	
	$scope.loadAliases = function(){
		caSignedCertificateService.loadAliases($scope.issuerCN.name)
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