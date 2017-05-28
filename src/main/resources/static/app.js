'use strict';


angular.module('routerApp', ['ui.router',
	'nationalBank.services', 'nationalBank.controllers',
	'findCertificate.services','findCertificate.controllers', 
	'nationalBank.services', 'nationalBank.controllers',
	'caSignedCertificate.services','caSignedCertificate.controllers',
	'signedCertificate.services','signedCertificate.controllers',
	'revokeCertificate.services','revokeCertificate.controllers']) 

.config(function($stateProvider,$urlRouterProvider){
	
	//$urlRouterProvider.otherwise('/country');
	
	
	$stateProvider
	.state('nationalBank',{
		url : '/nationalBank',
		templateUrl : 'nationalBank/nationalBankPartial.html',
		controller : 'nationalBankController'
	})
	.state('addCertificate',{
		url : '/addCertificate',
		templateUrl : 'nationalBank/addCertificate.html',
		controller : 'nationalBankController'
	})
	.state('addCaSignedCertificate',{
		url : '/addCaSignedCertificate',
		templateUrl : 'caSignedCertificate/caSignedCertificate.html',
		controller : 'caSignedCertificateController'
	})
	.state('addSignedCertificate',{
		url : '/addSignedCertificate',
		templateUrl : 'signedCertificate/signedCertificate.html',
		controller : 'signedCertificateController'
	})
	.state('addNationalBank',{
		url : '/addNationalBank',
		templateUrl : 'nationalBank/addNationalBank.html',
		controller : 'nationalBankController'
	})
	.state('findCertificate',{
		url : '/getExistingCertificate',
		templateUrl : 'findExistingCertificate/findCertificate.html',
		controller : 'findCertificateController'
	})
	.state('revokeCertificate',{
		url : '/revokeCertificate',
		templateUrl : 'revokeCertificate/revokeCertificate.html',
		controller : 'revokeCertificateController'
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
});
