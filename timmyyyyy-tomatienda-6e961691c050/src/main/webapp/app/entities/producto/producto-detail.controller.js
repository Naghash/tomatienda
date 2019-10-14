(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('ProductoDetailController', ProductoDetailController);

    ProductoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Producto', 'Conline', 'Cfisica'];

    function ProductoDetailController($scope, $rootScope, $stateParams, previousState, entity, Producto, Conline, Cfisica) {
        var vm = this;

        vm.producto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tomatiendaApp:productoUpdate', function(event, result) {
            vm.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
