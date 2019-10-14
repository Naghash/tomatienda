(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('CfisicaDeleteController',CfisicaDeleteController);

    CfisicaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cfisica'];

    function CfisicaDeleteController($uibModalInstance, entity, Cfisica) {
        var vm = this;

        vm.cfisica = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cfisica.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
