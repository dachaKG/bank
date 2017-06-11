var app = angular.module('faktura.controllers',[]);


app.controller('fakturaController',['$scope','fakturaService','$location',
	function($scope, fakturaService, $location) {

	$scope.faktura = new Object();
	$scope.findFirm = function(){
		fakturaService.findFirm().then(
			function(response){
				$scope.faktura.nazivDobavljaca = response.data.name;
				$scope.faktura.adresaDobavljaca = response.data.address;
				$scope.faktura.pibDobavljaca = response.data.pibFirm;
				
			}
		)
	}
	
	$scope.findAll = function(){
		fakturaService.findAll().then(
				function(response){
					$scope.fakture = response.data;
				}, function(response){
					alert("greska");
				}
			)
		
		
	}
	
	$scope.sendFaktura = function(){
		fakturaService.send($scope.faktura).then(
			function(response){
				alert("uspesno dodata faktura");
			}, function(response){
				alert("greska");
			}
		)
	}
	
	$scope.findAllFirms = function(){
		fakturaService.findAllFirms().then(
			function(response){
				$scope.firms = response.data;
			}
		)
	}
	
	$scope.openModal = function(){
		var modal = document.getElementById('myModal');
		modal.style.display = "block";		
	}
	
	$scope.okModal = function(){
		var modal = document.getElementById('myModal');
		modal.style.display = "none";		
	
	}
	$scope.selectedFirm = null;
	$scope.setSelectedFirm = function(firm){
		$scope.selectedFirm = firm;
		$scope.faktura.nazivKupca = firm.name;
		$scope.faktura.pibKupca = firm.pibFirm;
		$scope.faktura.adresaKupca = firm.address;
		
	}
	$scope.obradi = function(faktura){
		fakturaService.obradi(faktura)
		.then(function(response){
			$scope.findAll();
		},
		function(response){
			
		})
	}
}]);