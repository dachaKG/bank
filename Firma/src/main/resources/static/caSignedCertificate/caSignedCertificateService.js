var services = angular.module('caSignedCertificate.services',['ngResource']);
services.service('caSignedCertificateService',['$http',function($http){
	
	this.loadAliases = function(commonName){
		return $http.get("/nationalBank/commonName/"+commonName+"/aliases");
	}
	this.loadCNs = function(){
		return $http.get("/nationalBank/commonName");
	}
	this.createCertificate = function(certificate){
		return $http.post("/certificates",certificate);
	}
}])