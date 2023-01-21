var app = angular.module("ForgetPasswordModule", ['ui.bootstrap', 'datatables']);


//Forget Pass Controller
app.controller("ForgetPassController", function ($scope, $http, $timeout, $location) {

    $scope.form = {};
    $scope.forgoPass = function () {
        $http({
            method: 'PUT',
            params: { 'username': $scope.form.username, 'email': $scope.form.email },
            url: 'forgetPass',
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity

        }).then(function successCallback(response) {
            Swal.fire({
                text: response.data,
                icon: "success",
                buttonsStyling: !1,
                confirmButtonText: "Ok, got it!",
                customClass: {
                    confirmButton: "btn btn-primary"
                }
            })
        }, function errorCallback(response) {
            Swal.fire({
                text: response.data,
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "Ok, got it!",
                customClass: {
                    confirmButton: "btn btn-primary"
                }
            })
        });
    }

});