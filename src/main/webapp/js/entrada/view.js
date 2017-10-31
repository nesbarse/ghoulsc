moduloEntrada.controller('EntradaViewController', ['$scope', '$routeParams', 'serverService','$location',
    function ($scope, $routeParams, serverService,$location) {
        $scope.title = "Vista de entrada";
        $scope.icon = "fa-file-text-o";
        $scope.ob = 'entrada';
        $scope.id = $routeParams.id;
        serverService.getDataFromPromise(serverService.promise_getOne($scope.ob, $scope.id)).then(function (data) {
            $scope.bean = data.message;
        });
        $scope.close = function () {
            $location.path('/home');
        };
        $scope.plist = function () {
            $location.path('/entrada/plist');
        };
        $scope.back = function () {
            window.history.back();
        };
    }]);