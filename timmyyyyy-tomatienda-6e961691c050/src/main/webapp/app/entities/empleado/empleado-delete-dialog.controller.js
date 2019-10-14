(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('EmpleadoDeleteController',EmpleadoDeleteController);

    EmpleadoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Empleado'];

    function EmpleadoDeleteController($uibModalInstance, entity, Empleado) {
        var vm = this;

        vm.empleado = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Empleado.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
