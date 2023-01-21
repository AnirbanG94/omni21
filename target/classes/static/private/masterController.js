var app = angular.module("MasterManagment", ['ui.bootstrap', 'datatables']);

app.factory('Excel', function ($window) {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
        base64 = function (s) { return $window.btoa(unescape(encodeURIComponent(s))); },
        format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) };
    return {
        tableToExcel: function (tableId, worksheetName) {
            var table = $(tableId),
                ctx = { worksheet: worksheetName, table: table.html() },
                href = uri + base64(format(template, ctx));
            return href;
        }
    };
})

// DIRECTIVE - FILE MODEL
app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };

}]);
// Tot Master Controller

app.controller("TotController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoTotListFetch();
    function autoTotListFetch() {

        //showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getTot'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.Tot = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddTot = function () {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form = {};
        $('#add_location_modal').modal('show');
       
    }
    
    
    
    $scope.addSaveTot = function () {
       console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
           var method = "POST";
            var url = 'api/master/postToT';
        } else {
            var method = "PUT";
            var url = 'api/master/updateTot';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
  
  function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_location_modal').modal('hide');
        autoTotListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#add_location_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
   
    $scope.editTot = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getTotById'

        }).then(function successCallback(response) {
            console.log(response.data);
            //$scope.fetchCountry();
            $('#add_location_modal').modal('show');
            showHideLoad(true);
           // $scope.fetchState(response.data.countryId);
            $scope.form = response.data;
            // $scope.form.countryId = 103;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }


    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }
   
    
        $scope.deleteTot = function (id) {
        // var confirmRemove = confirm("Are you sure you want to delete this location?");
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Terms Of Trade!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'api/master/deleteTot';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoTotListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
        // if (confirmRemove == true) {

        // }
    }


});

// Terms Of Trade Creation Controller

app.controller("TotCreationController", function ($scope, $http, Excel, $timeout) {

      $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoTotCreationListFetch();
    function autoTotCreationListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getTotCreation'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.TotCreation = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    } 
    
    $scope.fetchParticulars = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getTot'

        }).then(function successCallback(response) {
           
            $scope.form.details  = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    
     $scope.getMasterValidFor = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getMasterValidFor'

        }).then(function successCallback(response) {
            $scope.masterTot = response.data;
           
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    
       $scope.fetchVendors = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorForProduct'

        }).then(function successCallback(response) {

            $scope.vendors = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    
         $scope.fetchArticlesByVendorIdForToT = function (id) {
        //showHideLoad();
        $http({
            method: 'GET',
            params: { 'vendorId': id },
            url: 'api/master/getArticlesByVendorForToT'
            
        }).then(function successCallback(response) {

            $scope.articlesTot = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    
          $scope.getAllFromTotMaster = function () {
        //showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getAllFromTotMaster'
            
        }).then(function successCallback(response) {

            $scope.masterTot = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    
    
    $scope.getParticularsByValidFor = function(validFor){
	   console.log(validFor);
	 if ($scope.form.validFor === 'true' ) {
		    var gkmsp = document.getElementById("gkmsp").value;
		    var gkm = document.getElementById("gkm").value;
		    var cost = document.getElementById("cost").value;
		     var costsp = document.getElementById("costsp").value;
		    if(gkm == 'true'){
            var method = "GET";
            var paramtr = {'validFor':validFor};
            var url = 'api/master/getParticularsByGkmAll';
            }
            else if(cost == 'true'){
	        console.log(cost);
	        var method = "GET";
	        var paramtr = {'validFor':validFor};
            var url = 'api/master/getParticularsByCostALL';
            }
            else if(costsp == 'true'){
	        var method = "GET";
	        var paramtr = {'validFor':validFor};
	        var url = "'api/master/getParticulasByCostSpecific'"

}

        } else {
          alert("method is not working");
        }
        $http({
            method: method,
            params: paramtr,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
	
}

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }
    
         $scope.deleteSelectShowProducts = function (index) {
        $scope.form.details.splice(index, 1);
    }
    
    
     $scope.selectArticleModal = function (id) {
	  $('#link_article_modal').modal('show');
	  $scope.fetchArticlesByVendorIdForToT(id);
  /*      $scope.productId = [];
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/vendor/getVendorFinalProductByVendorIdForPo'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.products = response.data;
            angular.forEach($scope.form.product_details, function (val, key) {
                $scope.productId[val.id] = true;
            });
            
            $scope.form.product_details = [];
        }, function errorCallback(response) {
            console.log(response.statusText);
        });*/

    }

    
    $scope.AddTotCreation = function () {
	
	    $scope.fetchVendors();
	     $scope.fetchParticulars();
	      $scope.getMasterValidFor();
	     $scope.changeView('add');
 
    }
    
     $scope.addSaveTotCreation = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'api/master/addSaveTotCreation';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
  
  function _success(response) {
        showHideLoad(true);
        console.log(response);
       // $('#add_location_modal').modal('hide');
       autoTotCreationListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    function _error(response) {
        console.log(response);
        showHideLoad(true);
       // $('#add_location_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    
    $scope.EditTotCreation = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "PUT";
        var url = 'api/master/EditTotCreation';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
  
  function _success(response) {
        showHideLoad(true);
        console.log(response);
       // $('#add_location_modal').modal('hide');
       
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    function _error(response) {
        console.log(response);
        showHideLoad(true);
       // $('#add_location_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    
    
    $scope.editTotCreation = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getTotCreationById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchVendors();
            $scope.fetchParticulars();
            $scope.changeView('edit');
            showHideLoad(true);
           // $scope.fetchState(response.data.countryId);
            $scope.form = response.data;
            // $scope.form.countryId = 103;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }


    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.edit = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }
    
    
        $scope.deleteTotCreation = function (id) {
        // var confirmRemove = confirm("Are you sure you want to delete this location?");
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Terms Of Trade!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'api/master/deleteTotCreation';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoTotCreationListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
        // if (confirmRemove == true) {

        // }
    }


});


//Location module controller 
app.controller("LocationController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoLocationListFetch();
    function autoLocationListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getLocation'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.locations = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddLocation = function () {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form = {};
        $('#add_location_modal').modal('show');
        $scope.fetchCountry();
    }
    $scope.addSaveLocation = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postLocation';
        } else {
            var method = "PUT";
            var url = 'api/master/updateLocation';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_location_modal').modal('hide');
        autoLocationListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#add_location_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    $scope.editLocation = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getLocationById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchCountry();
            $('#add_location_modal').modal('show');
            showHideLoad(true);
            $scope.fetchState(response.data.countryId);
            $scope.form = response.data;
            // $scope.form.countryId = 103;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.fetchCountry = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getCountry'

        }).then(function successCallback(response) {

            $scope.countries = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchState = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getStateByCountryCode'

        }).then(function successCallback(response) {

            $scope.states = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.deleteLocation = function (id) {
        // var confirmRemove = confirm("Are you sure you want to delete this location?");
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this location!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'api/master/deleteLocation';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoLocationListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
        // if (confirmRemove == true) {

        // }
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

// role module controller
app.controller("OutletController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoOutletListFetch();
    function autoOutletListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getOutlet'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.outlets = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }
    $scope.AddOutlet = function (tableId) {
        $scope.changeView('add');
        $scope.fetchCountry();
        $scope.fetchLocations();
        $scope.fetchWarehouse();
    }

    $scope.addSaveOutlet = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'api/master/addOutlet';
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        autoOutletListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        showHideLoad(true);
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    $scope.fetchCountry = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getCountry'

        }).then(function successCallback(response) {

            $scope.countries = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchState = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getStateByCountryCode'

        }).then(function successCallback(response) {

            $scope.states = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchLocations = function () {
        $http({
            method: 'GET',
            url: 'api/master/getLocation'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.locations = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchWarehouse = function () {
        $http({
            method: 'GET',
            url: 'api/master/getWarehouse'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.warehouses = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.editOutlet = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getOutletByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            $scope.fetchCountry();
            $scope.fetchLocations();
            $scope.fetchWarehouse();
            $scope.fetchState($scope.form.countrycode);
            $scope.fetchFileUploadList($scope.form.type);
            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSaveOutlet = function () {
        console.log($scope.form);
        showHideLoad();

        var method = "PUT";
        var url = 'api/master/updateOutlet';
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    $scope.deleteOutlet = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this outlet!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteOutlet';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoOutletListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });
            }
        })
    }
    $scope.fetchFileUploadListForAdd = function (type) {
        $http({
            method: 'GET',
            params: { 'code': type },
            url: 'api/admin/getDocumentByCode'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.details = response.data.details;


        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchFileUploadList = function (type) {
        $http({
            method: 'GET',
            params: { 'code': type },
            url: 'api/admin/getDocumentByCode'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.file_details = response.data.details;
            if ($scope.form.details.length == 0) {
                $scope.form.details = response.data.details;
            } else if ($scope.form.details.length != $scope.form.file_details) {
                angular.forEach($scope.form.file_details, function (val, key) {
                    console.log($scope.form.details[key]);
                    if ($scope.form.details[key] == undefined || val.refId != $scope.form.details[key].refId) {
                        $scope.form.details.push(val);
                    } else {
                        $scope.form.details[key].file = val.file;
                    }
                });
            } else {
                angular.forEach($scope.form.file_details, function (val, key) {
                    console.log($scope.form.details[key]);
                    $scope.form.details[key].file = val.file;
                });
            }

        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.uploadAnyFile = function (kee, fileup) {
        var form_data = new FormData;
        form_data.append('module', 'office');
        form_data.append("file", fileup);
        //console.log(form_data);

        var config = {
            transformResponse: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }
        var url = "api/upload/addFile";

        $http.post(url, form_data, config).then(
            // Success
            function (response) {
                console.log(response);
                $scope.form.details[kee].link = response.data;
                Swal.fire({
                    text: "File Uploaded",
                    icon: "success",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
            },
            // Error
            function (response) {
                Swal.fire({
                    text: "File Upload failed! Please Upload Again",
                    icon: "error",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
                console.log(response);
            }
        );
    }
    $scope.downloadFile = function (link) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'link': link },
            responseType: 'arraybuffer',
            url: 'api/upload/getFile'

        }).then(function successCallback(response) {
            console.log(response.data);
            var file = new Blob([response.data], { type: 'application/excel' });
            var fileURL = URL.createObjectURL(file);
            var a = document.createElement('a');
            a.href = fileURL;
            a.target = '_blank';
            a.download = link;
            document.body.appendChild(a);
            a.click();
            showHideLoad(true);
        }, function errorCallback(response) {
            Swal.fire({
                text: "File Not Found! Please Upload Again",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "Ok, got it!",
                customClass: {
                    confirmButton: "btn btn-primary"
                }
            })
            showHideLoad(true);
        });
    }
    $scope.removeFile = function (kee, id) {
        $scope.form.details[kee].link = "";
        showHideLoad();
        var method = "DELETE";
        var url = 'api/master/deleteOutletDocument';
        $http({
            method: method,
            params: { 'id': id },
            url: url,
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.details.splice(kee, 1);
            showHideLoad(true);
            Swal.fire(
                'Deleted!',
                response.data,
                'success'
            )
        }, function errorCallback(response) {
            console.log(response);
        });
    }
    $scope.changeView = function (view) {
        if (view == "add" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.add = false;
        $scope.views.edit = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//region module controller 
app.controller("RegionController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoRegionListFetch();
    function autoRegionListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getRegion'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.regions = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddEditRegion = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postRegion';
        } else {
            var method = "PUT";
            var url = 'api/master/updateRegion';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_region_modal').modal('hide');
        autoRegionListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#add_region_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    $scope.editRegion = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getRegionByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#add_region_modal').modal('show');
            showHideLoad(true);
            $scope.form = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.deleteRegion = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this region!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteRegion';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoRegionListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});
//payment module controller 
app.controller("PaymentController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoPaymentListFetch();
    function autoPaymentListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getPaymentmode'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.payment_modes = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.addSavePaymentMode = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postPaymentmode';
        } else {
            var method = "PUT";
            var url = 'api/master/updatePaymentmode';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_payment_modal').modal('hide');
        autoPaymentListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#add_payment_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    $scope.editPaymentMode = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getPaymentmodeByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#add_payment_modal').modal('show');
            showHideLoad(true);
            $scope.form = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deletePaymentMode = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this payment mode!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deletePaymentmode';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoPaymentListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }


    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//assets type module controller
app.controller("AssetsTypeController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoAssetsListFetch();
    function autoAssetsListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getAssetsType'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.assets_type = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.addSaveAssets = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/addAssetsType';
        } else {
            var method = "PUT";
            var url = 'api/master/updateAssetsType';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_assets_modal').modal('hide');
        autoAssetsListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#add_assets_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    $scope.editAssets = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getAssetsTypeByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#add_assets_modal').modal('show');
            showHideLoad(true);
            $scope.form = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteAssets = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this assets type!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteAssetsType';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoAssetsListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }


    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

// role module controller
app.controller("TransportationController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoTransportListFetch();
    function autoTransportListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getTransportation'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.transports = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }
    $scope.AddTransport = function () {
        $scope.changeView('add');
        $scope.fetchCountry();
        $scope.fetchLocations();
        $scope.fetchFileUploadListForAdd('T');
    }

    $scope.AddSaveTransport = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'api/master/addTransportation';
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        autoTransportListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        showHideLoad(true);
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    $scope.fetchCountry = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getCountry'

        }).then(function successCallback(response) {

            $scope.countries = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchState = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getStateByCountryCode'

        }).then(function successCallback(response) {

            $scope.states = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchLocations = function () {
        $http({
            method: 'GET',
            url: 'api/master/getLocation'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.locations = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.editTransport = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getTransportationByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            $scope.fetchCountry();
            $scope.fetchLocations();
            $scope.fetchState($scope.form.countrycode);
            $scope.fetchFileUploadList('T');
            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSaveTransport = function () {
        console.log($scope.form);
        showHideLoad();

        var method = "PUT";
        var url = 'api/master/updateTransportation';
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    $scope.deleteTransport = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this outlet!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteTransportation';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoTransportListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });
            }
        })
    }
    $scope.fetchFileUploadListForAdd = function (type) {
        $http({
            method: 'GET',
            params: { 'code': type },
            url: 'api/admin/getDocumentByCode'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.details = response.data.details;


        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchFileUploadList = function (type) {
        $http({
            method: 'GET',
            params: { 'code': type },
            url: 'api/admin/getDocumentByCode'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.file_details = response.data.details;
            if ($scope.form.details.length == 0) {
                $scope.form.details = response.data.details;
            } else if ($scope.form.details.length != $scope.form.file_details) {
                angular.forEach($scope.form.file_details, function (val, key) {
                    console.log($scope.form.details[key]);
                    if ($scope.form.details[key] == undefined || val.refId != $scope.form.details[key].refId) {
                        $scope.form.details.push(val);
                    } else {
                        $scope.form.details[key].file = val.file;
                    }
                });
            } else {
                angular.forEach($scope.form.file_details, function (val, key) {
                    console.log($scope.form.details[key]);
                    $scope.form.details[key].file = val.file;
                });
            }

        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.uploadAnyFile = function (kee, fileup) {
        var form_data = new FormData;
        form_data.append('module', 'office');
        form_data.append("file", fileup);
        //console.log(form_data);

        var config = {
            transformResponse: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }
        var url = "api/upload/addFile";

        $http.post(url, form_data, config).then(
            // Success
            function (response) {
                console.log(response);
                $scope.form.details[kee].link = response.data;
                Swal.fire({
                    text: "File Uploaded",
                    icon: "success",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
            },
            // Error
            function (response) {
                Swal.fire({
                    text: "File Upload failed! Please Upload Again",
                    icon: "error",
                    buttonsStyling: !1,
                    confirmButtonText: "Ok, got it!",
                    customClass: {
                        confirmButton: "btn btn-primary"
                    }
                })
                console.log(response);
            }
        );
    }
    $scope.downloadFile = function (link) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'link': link },
            responseType: 'arraybuffer',
            url: 'api/upload/getFile'

        }).then(function successCallback(response) {
            console.log(response.data);
            var file = new Blob([response.data], { type: 'application/excel' });
            var fileURL = URL.createObjectURL(file);
            var a = document.createElement('a');
            a.href = fileURL;
            a.target = '_blank';
            a.download = link;
            document.body.appendChild(a);
            a.click();
            showHideLoad(true);
        }, function errorCallback(response) {
            Swal.fire({
                text: "File Not Found! Please Upload Again",
                icon: "error",
                buttonsStyling: !1,
                confirmButtonText: "Ok, got it!",
                customClass: {
                    confirmButton: "btn btn-primary"
                }
            })
            showHideLoad(true);
        });
    }
    $scope.removeFile = function (kee, id) {
        $scope.form.details[kee].link = "";
        showHideLoad();
        var method = "DELETE";
        var url = 'api/master/deleteTransportationDocument';
        $http({
            method: method,
            params: { 'id': id },
            url: url,
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.details.splice(kee, 1);
            showHideLoad(true);
            Swal.fire(
                'Deleted!',
                response.data,
                'success'
            )
        }, function errorCallback(response) {
            console.log(response);
        });
    }
    $scope.changeView = function (view) {
        if (view == "add" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.add = false;
        $scope.views.edit = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//Reason module controller 
app.controller("ReasonController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoReasonListFetch();
    function autoReasonListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getReason'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.reasons = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddReason = function () {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form = {};
        $('#add_reason_modal').modal('show');
    }
    $scope.addSaveReason = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postReason';
        } else {
            var method = "PUT";
            var url = 'api/master/updateReason';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_reason_modal').modal('hide');
        autoReasonListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#add_reason_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    $scope.editReason = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getReasonByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            // $scope.form.countryId = 103;
            $('#add_reason_modal').modal('show');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteReason = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this reason!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteReason';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoReasonListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//Bay module controller 
app.controller("BayController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoBayListFetch();
    function autoBayListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getBay'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.bays = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddBay = function () {
        $scope.form = {};
        $scope.fetchOutlet();
        $scope.fetchClasses();
        $('#add_bay_modal').modal('show');
    }
    $scope.addSaveBay = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postBay';
        } else {
            var method = "PUT";
            var url = 'api/master/updateBay';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_bay_modal').modal('hide');
        autoBayListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#add_bay_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }
    $scope.fetchOutlet = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getWarehouse'

        }).then(function successCallback(response) {

            $scope.outlets = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchClasses = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getProClass'

        }).then(function successCallback(response) {

            $scope.classes = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editBay = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getBaybyId'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchOutlet();
            $scope.fetchClasses();
            $scope.form = response.data;
            $('#add_bay_modal').modal('show');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteBay = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this bay!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteBay';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoBayListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }

});

//cluster module controller 
app.controller("ClusterController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoClusterListFetch();
    function autoClusterListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getCluster'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.clusters = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddCluster = function () {
        $scope.form = {};
        $('#add_cluster_modal').modal('show');
        $scope.fetchCountry();
    }
    $scope.addSaveCluster = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postCluster';
        } else {
            var method = "PUT";
            var url = 'api/master/updateCluster';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_cluster_modal').modal('hide');
        autoClusterListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#add_cluster_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    $scope.editCluster = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getClusterByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#add_cluster_modal').modal('show');
            showHideLoad(true);
            $scope.form = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.viewCluster = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getClusterByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#view_cluster_modal').modal('show');
            showHideLoad(true);
            $scope.form = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteCluster = function (id) {
        // var confirmRemove = confirm("Are you sure you want to delete this location?");
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this cluster!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'api/master/deleteCluster';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoClusterListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
        // if (confirmRemove == true) {

        // }
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//schedule module controller 
app.controller("ScheduleController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoScheduleListFetch();
    function autoScheduleListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getSchedule'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.schedules = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.AddSchedule = function () {
        $scope.form = {};
        $('#add_Schedule_modal').modal('show');
        $scope.fetchCountry();
    }
    $scope.addSaveSchedule = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postSchedule';
        } else {
            var method = "PUT";
            var url = 'api/master/updateSchedule';
        }
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#add_Schedule_modal').modal('hide');
        autoScheduleListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#add_Schedule_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    $scope.editCluster = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getScheduleByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#add_Schedule_modal').modal('show');
            showHideLoad(true);
            $scope.form = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteCluster = function (id) {
        // var confirmRemove = confirm("Are you sure you want to delete this location?");
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this schedule!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'api/master/deleteSchedule';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoScheduleListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
        // if (confirmRemove == true) {

        // }
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//Link cluster module controller 
app.controller("ClusterMappingController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoClusterMappingListFetch();
    function autoClusterMappingListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getCluster'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.clusters = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.exportToExcel = function (tableId) {
        console.log(tableId);
        var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
        $timeout(function () { location.href = exportHref; }, 100); // trigger download
    }

    $scope.articleId = [];
    $scope.outletId = [];
    $scope.linkArticle = function (id, article) {
        $scope.form = {};
        $scope.articleId = [];

        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getArticles'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#link_article_modal').modal('show');
            $scope.form.articles = response.data;
            $scope.form.clusterId = id;
            angular.forEach(article, function (val, key) {
                $scope.articleId[val.id] = true;
            });
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.linkStore = function (id, outlet) {
        $scope.form = {};
        $scope.outletId = [];
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getStore'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#link_store_modal').modal('show');
            $scope.form.stores = response.data;
            $scope.form.clusterId = id;
            angular.forEach(outlet, function (val, key) {
                $scope.outletId[val.id] = true;
            });
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.addSaveClusterArticleLinking = function () {
        showHideLoad();
        var i = 0;
        console.log($scope.articleId);
        $scope.form.articleId = [];
        angular.forEach($scope.articleId, function (val, key) {
            if (val == true) {
                $scope.form.articleId[i] = key;
                i = i + 1;
            }
        });
        var method = "PUT";
        var url = 'api/master/clusterMappingForArticle';
        if ($scope.form.articleId.length == 0) {
            // $scope.form.articleId = [0];
        }
        console.log($scope.form);
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    $scope.addSaveClusterStoreLinking = function () {
        showHideLoad();
        var i = 0;
        console.log($scope.outletId);
        $scope.form.outletId = [];
        angular.forEach($scope.outletId, function (val, key) {
            if (val == true) {
                $scope.form.outletId[i] = key;
                i = i + 1;
            }
        });
        var method = "PUT";
        var url = 'api/master/clusterMappingForStore';
        if ($scope.form.outletId.length == 0) {
            //$scope.form.outletId = [0];
        }
        console.log($scope.form);
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#link_article_modal').modal('hide');
        $('#link_store_modal').modal('hide');
        autoClusterMappingListFetch();
        Swal.fire({
            text: response.data,
            icon: "success",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    function _error(response) {
        console.log(response);
        showHideLoad(true);
        $('#link_article_modal').modal('hide');
        $('#link_store_modal').modal('hide');
        Swal.fire({
            text: response.data,
            icon: "error",
            buttonsStyling: !1,
            confirmButtonText: "Ok, got it!",
            customClass: {
                confirmButton: "btn btn-primary"
            }
        })
    }

    $scope.editCluster = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getClusterByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#add_cluster_modal').modal('show');
            showHideLoad(true);
            $scope.form = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.viewCluster = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getClusterByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $('#view_cluster_modal').modal('show');
            showHideLoad(true);
            $scope.form = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteCluster = function (id) {
        // var confirmRemove = confirm("Are you sure you want to delete this location?");
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this cluster!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                var method = "DELETE";
                var url = 'api/master/deleteCluster';
                $http({
                    method: method,
                    params: { 'id': id },
                    url: url,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    transformResponse: angular.identity
                }).then(function successCallback(response) {
                    console.log(response);
                    showHideLoad(true);
                    Swal.fire(
                        'Deleted!',
                        response.data,
                        'success'
                    )
                    autoClusterListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
        // if (confirmRemove == true) {

        // }
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.editUser = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});