var app = angular.module("GKMManagement", ['ui.bootstrap', 'datatables']);

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


// Item module controller
app.controller("GKMController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoGKMListFetch();
    function autoGKMListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getGKMData'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.gkms = response.data;
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
    $scope.AddItem = function (tableId) {
        $scope.form.new_items = [];
        $scope.form.new_items.pro_sub_class = [];
        $scope.form.new_items.push({ 'pro_division': '', 'pro_family': '', 'pro_class': '', 'pro_sub_class': [] });
        $scope.fetchDivision();
        $scope.fetchFaimly();
        $scope.fetchClasses();
        $scope.changeView('add');
    }
    $scope.appendNewitem = function () {
        var counNew_item_list = 0;
        if (typeof $scope.form.new_items === "undefined") {
            counNew_item_list = 0
        } else {
            counNew_item_list = $scope.form.new_items.length;
        }
        if (counNew_item_list > 0) {
            $scope.form.new_items.push({ 'pro_division': '', 'pro_family': '', 'pro_class': '', 'pro_sub_class': [] });
        } else {
            $scope.form.new_items = [];
            $scope.form.new_items.push({ 'pro_division': '', 'pro_family': '', 'pro_class': '', 'pro_sub_class': [] });
        }
    }
    $scope.appendNewSubClass = function (kee) {
        var count_calss = 0;
        if (typeof $scope.form.new_items[kee].pro_sub_class === "undefined") {
            count_calss = 0
        } else {
            count_calss = $scope.form.new_items[kee].pro_sub_class.length;
        }
        if (count_calss > 0) {
            $scope.form.new_items[kee].pro_sub_class.push({ 'sub_class': '' });
        } else {
            $scope.form.new_items[kee].pro_sub_class = [];
            $scope.form.new_items[kee].pro_sub_class.push({ 'sub_class': '' });
        }
        console.log($scope.form);
    }
    $scope.removeAppendItemlist = function (index) {
        $scope.form.new_items.splice(index, 1);
    }
    $scope.removeAppendSubClasslist = function (index, key) {
        $scope.form.new_items[key].pro_sub_class.splice(index, 1);
    }
    $scope.addSaveItem = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'api/master/postItem';
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
        autoItemListFetch();
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
    $scope.fetchDivision = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getProDivision'

        }).then(function successCallback(response) {

            $scope.divisions = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.fetchFaimly = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getProFamily'

        }).then(function successCallback(response) {

            $scope.families = response.data;
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

    $scope.editItem = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getItemByid'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            $scope.fetchDivision();
            $scope.fetchFaimly();
            $scope.fetchClasses();
            $('#edit_item_modal').modal('show');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSaveItem = function () {
        console.log($scope.form);
        showHideLoad();

        var method = "PUT";
        var url = 'api/master/updateItem';
        $('#edit_item_modal').modal('hide');
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
    $scope.deleteItem = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this item!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteItem';
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
                    autoItemListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });
            }
        })
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

//Brand module controller 
app.controller("BrandController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoBrandListFetch();
    function autoBrandListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getBrand'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.brands = response.data;
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

    $scope.AddBrand = function () {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form = {};
        $('#add_brand_modal').modal('show');
    }
    $scope.addSaveReason = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postBrand';
        } else {
            var method = "PUT";
            var url = 'api/master/updateBrand';
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
        $('#add_brand_modal').modal('hide');
        autoBrandListFetch();
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
        $('#add_brand_modal').modal('hide');
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

    $scope.editBrand = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getBrandId'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            showHideLoad(true);
            $('#add_brand_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteBrand = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this brand!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteBrand';
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
                    autoBrandListFetch();
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

//Manufacture module controller 
app.controller("ManufactureController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoManufactureListFetch();
    function autoManufactureListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getManufacturer'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.manufactures = response.data;
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

    $scope.AddManufacture = function () {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form = {};
        $('#add_manufacture_modal').modal('show');
    }
    $scope.addSaveManufacture = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postManufacturer';
        } else {
            var method = "PUT";
            var url = 'api/master/updateManufacturer';
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
        $('#add_manufacture_modal').modal('hide');
        autoManufactureListFetch();
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
        $('#add_manufacture_modal').modal('hide');
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

    $scope.editManufacture = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getManufacturerById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            showHideLoad(true);
            $('#add_manufacture_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteManufacture = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Manufacture!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteManufacturer';
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
                    autoManufactureListFetch();
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

//Packaging Type module controller 
app.controller("PackagingTypeController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoPackTypeListFetch();
    function autoPackTypeListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getPacktype'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.pack_types = response.data;
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

    $scope.AddPackType = function () {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form = {};
        $('#add_pack_type_modal').modal('show');
    }
    $scope.addSavePackType = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/postPacktype';
        } else {
            var method = "PUT";
            var url = 'api/master/updatePacktype';
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
        $('#add_pack_type_modal').modal('hide');
        autoPackTypeListFetch();
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
        $('#add_pack_type_modal').modal('hide');
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

    $scope.editPackType = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getPacktypeById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            showHideLoad(true);
            $('#add_pack_type_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deletePackType = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Packaging type!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deletePacktype';
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
                    autoPackTypeListFetch();
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

//Purchase Group module controller 
app.controller("PurchaseGroupController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoPurchaseGroupListFetch();
    function autoPurchaseGroupListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getPurchaseGroup'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.purchase_groups = response.data;
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

    $scope.AddPurchaseGroup = function () {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form = {};
        $('#add_purchase_group_modal').modal('show');
    }
    $scope.addSavePurchaseGroup = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/savePurchaseGroup';
        } else {
            var method = "PUT";
            var url = 'api/master/updatePurchaseGroup';
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
        $('#add_purchase_group_modal').modal('hide');
        autoPurchaseGroupListFetch();
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
        $('#add_purchase_group_modal').modal('hide');
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

    $scope.editPurchaseGroup = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getPurchaseGroupById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            showHideLoad(true);
            $('#add_purchase_group_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deletePurchaseGroup = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this purchase group!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deletePurchaseGroup';
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
                    autoPurchaseGroupListFetch();
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

//UOM  module controller 
app.controller("UOMController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoUOMListFetch();
    function autoUOMListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getUOMDetails'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.uoms = response.data;
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

    $scope.AddUOM = function () {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form = {};
        $('#add_UOM_modal').modal('show');
    }
    $scope.addSaveUOM = function () {
        console.log($scope.form);
        showHideLoad();
        if ($scope.form.id == '' || $scope.form.id == undefined) {
            var method = "POST";
            var url = 'api/master/saveUOMDetails';
        } else {
            var method = "PUT";
            var url = 'api/master/updateUOM';
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
        $('#add_UOM_modal').modal('hide');
        autoUOMListFetch();
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
        $('#add_UOM_modal').modal('hide');
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

    $scope.editUOM = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getUOMDetailsById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            showHideLoad(true);
            $('#add_UOM_modal').modal('show');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteUOM = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this UOM!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deleteUOM';
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
                    autoUOMListFetch();
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

//Article module controller 
app.controller("ArticleController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoArticleListFetch();
    function autoArticleListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getArticles'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.articles = response.data;
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
    $scope.getLocations = function (id) {
        $scope.fetchLocations();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getArticlesById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            angular.forEach($scope.form.location_id, function (val, key) {
                $scope.location_id[val] = true;
            });
            $scope.form.id = id;
            $('#location_list_modal').modal('show');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.SaveLinkedLocation = function () {
        showHideLoad();
        var i = 0;
        $scope.form.location_id = [];
        angular.forEach($scope.location_id, function (val, key) {
            if (val == true) {
                $scope.form.location_id[i] = key;
                i = i + 1;
            }
        });
        var method = "POST";
        var url = 'api/master/postLocationInArtical';
        if ($scope.form.location_id.length == 0) {
            $scope.form.location_id = [0];
        }
        console.log($scope.form);
        $http({
            method: method,
            url: url,
            params: { 'id': $scope.form.id, 'location_id': $scope.form.location_id },
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        autoArticleListFetch();
        $('#location_list_modal').modal('hide');
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
        $('#location_list_modal').modal('hide');
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

    $scope.fetchLocations = function () {
        $http({
            method: 'GET',
            url: 'api/master/getStoreLocation'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.locations = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.location_id = [];
    $scope.IsCheckall = false;
    $scope.toggleAll = function () {
        if ($scope.IsCheckall == false) {
            $scope.IsCheckall = true;
        } else {
            $scope.IsCheckall = false;
        }
        angular.forEach($scope.locations, function (itm) {

            $scope.location_id[itm.id] = $scope.IsCheckall;

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

//Article status Type module controller 
app.controller("ArticleStatusController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoArticleStatusListFetch();
    function autoArticleStatusListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getArticleStatus'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.article_status = response.data;
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

    $scope.AddPackType = function () {
        $scope.getArticles();
        $scope.form = {};
        $scope.changeView('add');
    }
    $scope.addSaveArticleStatus = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'api/master/postArticleStatus';

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
        autoArticleStatusListFetch();
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
    $scope.getArticles = function () {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/master/getArticles'

        }).then(function successCallback(response) {

            $scope.articles = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.getItemDetails = function (id) {
        showHideLoad();

        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getArticlesById'

        }).then(function successCallback(response) {

            $scope.item = response.data;
            $scope.form.pro_division = $scope.item.pro_division;
            $scope.form.pro_family = $scope.item.pro_family;
            $scope.form.pro_class = $scope.item.pro_class;
            $scope.form.pro_sub_class = $scope.item.pro_sub_class;
            $scope.form.productid = $scope.item.productId;
            $scope.form.status = $scope.item.status + '';
            $scope.form.blockProcurement = $scope.item.blockProcurement + '';
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });

    }
    $scope.editArticleStatus = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/master/getArticleStatusById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            $scope.form.status = response.data.status + '';
            $scope.form.blockProcurement = response.data.blockProcurement + '';
            $scope.getArticles();
            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.editSaveArticleStatus = function () {
        console.log($scope.form);
        showHideLoad();

        var method = "PUT";
        var url = 'api/master/updateArticleStatus';
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
    $scope.deleteArticleStatus = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Packaging type!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/master/deletePacktype';
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
                    autoPackTypeListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
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