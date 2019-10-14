(function() {
    'use strict';

    angular
        .module('tomatiendaApp')
        .controller('ConlineDeleteController',ConlineDeleteController);

    ConlineDeleteController.$inject = ['$uibModalInstance', 'entity', 'Conline'];

    function ConlineDeleteController($uibModalInstance, entity, Conline) {
        var vm = this;

        vm.conline = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Conline.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
