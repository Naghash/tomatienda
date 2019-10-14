(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('EmpleadoDetailController', EmpleadoDetailController);

    EmpleadoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Empleado', 'Cfisica'];

    function EmpleadoDetailController($scope, $rootScope, $stateParams, previousState, entity, Empleado, Cfisica) {
        var vm = this;

        vm.empleado = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tomatiendaApp:empleadoUpdate', function(event, result) {
            vm.empleado = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
