(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('ConlineDialogController', ConlineDialogController);

    ConlineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Conline', 'Cliente', 'Producto'];

    function ConlineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Conline, Cliente, Producto) {
        var vm = this;

        vm.conline = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clientes = Cliente.query();
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.conline.id !== null) {
                Conline.update(vm.conline, onSaveSuccess, onSaveError);
            } else {
                Conline.save(vm.conline, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tomatiendaApp:conlineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
