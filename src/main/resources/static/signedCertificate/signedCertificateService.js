var services = angular.module('signedCertificate.services',['ngResource']);

services.service('signedCertificateService',['$http',function($http){
	this.loadAliases = function(commonName){
		return $http.get("/client/certificates/commonName/"+commonName+"/aliases");
	}
	this.loadCNs = function(){
		return $http.get("/client/certificates/commonName");
	}
	this.createCertificate = function(certificate){
		return $http.post("/client/certificates",certificate);
	}	
}])