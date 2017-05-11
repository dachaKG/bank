var services = angular.module('caSignedCertificate.services',['ngResource']);
services.service('caSignedCertificateService',['$http',function($http){
	
	this.loadAliases = function(commonName){
		return $http.get("/certificates/commonName/"+commonName+"/aliases");
	}
	this.loadCNs = function(){
		return $http.get("/certificates/commonName");
	}
	this.createCertificate = function(certificate){
		return $http.post("/certificates",certificate);
	}
}])