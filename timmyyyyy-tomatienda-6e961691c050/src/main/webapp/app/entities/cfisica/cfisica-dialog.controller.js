(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('CfisicaDialogController', CfisicaDialogController);

    CfisicaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cfisica', 'Empleado', 'Producto'];

    function CfisicaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cfisica, Empleado, Producto) {
        var vm = this;

        vm.cfisica = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.empleados = Empleado.query();
        vm.productos = Producto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cfisica.id !== null) {
                Cfisica.update(vm.cfisica, onSaveSuccess, onSaveError);
            } else {
                Cfisica.save(vm.cfisica, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tomatiendaApp:cfisicaUpdate', result);
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
