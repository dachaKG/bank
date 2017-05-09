'use strict';


angular.module('routerApp', ['ui.router',
	'nationalBank.services', 'nationalBank.controllers']) 
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
	.state('addNationalBank',{
		url : '/addNationalBank',
		templateUrl : 'nationalBank/addNationalBank.html',
		controller : 'nationalBankController'
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
