let FlashMart = angular.module('FlashMart', []);

// FlashMart.config(function ($routeProvider) {
// 	$routeProvider
// 		.when('/', {
// 			templateUrl: 'home.html',
// 			controller: 'ListProduct'
// 		})
// 		.when('/detail', {
// 			templateUrl: 'chitiet.html'
// 		})
// 		.when('/cart', {
// 			templateUrl: 'cart.html'
// 		})
// 		.when('/login', {
// 			templateUrl: 'login.html'
// 		})
// 		.when('/register', {
// 			templateUrl: 'register.html'
// 		})
// 		.when('/faqs', {
// 			templateUrl: 'FAQs.html'
// 		})
// 		.when('/news', {
// 			templateUrl: 'trangtintuc.html'
// 		})
// 		.otherwise({
// 			redirectTo: ''
// 		})
// });

FlashMart.controller('ListProduct', function ($scope,$http) {
	$scope.list = [];
	const url = '/user/product';
	$http.get(url).then(Response => {
		$scope.list = Response.data;
	}).catch(error => error);

});