var services = angular.module('nationalBank.services',['ngResource']);

services.service('nationalBankService',['$http', function($http){
	
	this.findAll = function(){
		return $http.get("/nationalBank");
	}
	
	this.addCertificate = function(certificate){
		return $http.post("/addCertificate",certificate);
	}
	
	this.addNationalBank = function(nationalBank){
		return $http.post("/addNationalBank",nationalBank);
	}
	
}])