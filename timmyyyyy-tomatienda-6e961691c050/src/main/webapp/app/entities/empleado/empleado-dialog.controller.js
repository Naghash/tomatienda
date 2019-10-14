(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('EmpleadoDialogController', EmpleadoDialogController);

    EmpleadoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Empleado', 'Cfisica'];

    function EmpleadoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Empleado, Cfisica) {
        var vm = this;

        vm.empleado = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cfisicas = Cfisica.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.empleado.id !== null) {
                Empleado.update(vm.empleado, onSaveSuccess, onSaveError);
            } else {
                Empleado.save(vm.empleado, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tomatiendaApp:empleadoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechanac = false;
        vm.datePickerOpenStatus.ingreso = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
