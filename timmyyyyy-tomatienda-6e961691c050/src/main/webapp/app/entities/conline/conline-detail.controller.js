(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('ConlineDetailController', ConlineDetailController);

    ConlineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Conline', 'Cliente', 'Producto'];

    function ConlineDetailController($scope, $rootScope, $stateParams, previousState, entity, Conline, Cliente, Producto) {
        var vm = this;

        vm.conline = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tomatiendaApp:conlineUpdate', function(event, result) {
            vm.conline = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
