angular.module("TesteApp", []).value('urlBase',
		'http://localhost:8081/E-COMMERCE//main/').controller(
		"ChamadoController", function($http, urlBase) {
			var self = this;
			self.usuario = 'E-ECOMMERCE_LITE';
		});