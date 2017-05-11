var services = angular.module('findCertificate.services',['ngResource']);

services.service('findCertificateService', ['$http', function($http){
	
	this.findCertificate = function(certificate){
		return $http.get("/getExistingCertificate/" + certificate);
	}
	
}])