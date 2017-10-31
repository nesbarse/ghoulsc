moduloSalida.controller('SalidaViewController', ['$scope', '$routeParams', 'serverService','$location',
    function ($scope, $routeParams, serverService,$location) {
        $scope.title = "Vista de salida";
        $scope.icon = "fa-file-text-o";
        $scope.ob = 'salida';
        $scope.id = $routeParams.id;
        serverService.getDataFromPromise(serverService.promise_getOne($scope.ob, $scope.id)).then(function (data) {
            $scope.bean = data.message;
        });
        $scope.close = function () {
            $location.path('/home');
        };
        $scope.plist = function () {
            $location.path('/salida/plist');
        };
        $scope.back = function () {
            window.history.back();
        };
    }]);