(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('ProductoDialogController', ProductoDialogController);

    ProductoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Producto', 'Conline', 'Cfisica'];

    function ProductoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Producto, Conline, Cfisica) {
        var vm = this;

        vm.producto = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.conlines = Conline.query();
        vm.cfisicas = Cfisica.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.producto.id !== null) {
                Producto.update(vm.producto, onSaveSuccess, onSaveError);
            } else {
                Producto.save(vm.producto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tomatiendaApp:productoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.caducidad = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
