(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('CfisicaDetailController', CfisicaDetailController);

    CfisicaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cfisica', 'Empleado', 'Producto'];

    function CfisicaDetailController($scope, $rootScope, $stateParams, previousState, entity, Cfisica, Empleado, Producto) {
        var vm = this;

        vm.cfisica = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tomatiendaApp:cfisicaUpdate', function(event, result) {
            vm.cfisica = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
