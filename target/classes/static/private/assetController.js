var app = angular.module("AssetManagment", ['ui.bootstrap', 'datatables']);

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

//Asset Visibility Register module controller 
app.controller("AssetRegisterController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoAssetRegisterListFetch();
    function autoAssetRegisterListFetch() {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/asset/getAssetRegistration'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.assets = response.data;
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

    $scope.AddAssetRegister = function () {
        $scope.form = {};
        $scope.fetchAssetstype();
        $scope.fetchStoreData();
         $scope.fetchYear();

        $scope.changeView('add');
    }
    $scope.addSaveAssetRegister = function () {
        if ($scope.form.visibilityType != 'P') {
            $scope.form.totalAmount = 0.00;
            $scope.form.cost = 0.00;
        }
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'api/asset/postAssetRegistration';

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
        autoAssetRegisterListFetch();
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
    $scope.fetchStoreData = function (type) {
        showHideLoad();
        if (type == 'S') {
            var url = 'api/master/getStore';
        } else {
            var url = 'api/master/getWarehouse';

        }
        $http({
            method: 'GET',
            url: url

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.stores = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    
     $scope.fetchYear = function () {
		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getFinancialYear'

		}).then(function successCallback(response) {

			$scope.years = response.data;
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}

    $scope.fetchAssetGst = function (type) {
        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/asset/getAssetGST'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.gst = response.data;
            // $scope.getTotalAmount($scope.form.cost, $scope.form.gst);
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.getTotalAmount = function (cost, gst) {
        let val = (cost * ((100 + gst) / 100));
        $scope.form.totalAmount = parseFloat(val).toFixed(2);
    }



    $scope.openFetchItems = function (tableId) {
        $('#list_itms_modal').modal('show');
        $http({
            method: 'GET',
            url: 'api/master/getProDivFamily'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.items = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.selectItem = function (faimly) {
        $('#list_itms_modal').modal('hide');
        $scope.form.productFamily = faimly;
    }

    $scope.fetchAssetstype = function () {
        showHideLoad();
        $http({
            method: 'GET',
            // params: { 'id' },
            url: 'api/master/getAssetsType'

        }).then(function successCallback(response) {

            $scope.assetstypes = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    
	
    $scope.editAssetReg = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/asset/getAssetRegistrationById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.fetchAssetstype();
            $scope.fetchStoreData(response.data.outletType);
            $scope.form = response.data;
            $scope.form.store_type = response.data.outletType;
            $scope.fetchAssetGst();
             $scope.fetchYear();

            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.editSaveAssetReg = function () {
        if ($scope.form.visibilityType != 'P') {
            $scope.form.totalAmount = 0.00;
            $scope.form.cost = 0.00;
        }
        showHideLoad();
        console.log($scope.form);
        var method = "PUT";
        var url = 'api/asset/updateAssetRegistration';
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    }
    $scope.deleteAssetReg = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Asset!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/asset/deleteAssetRegistration';
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
                    autoAssetRegisterListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }
$scope.uploadAnyFile = function (fileup) {
        var form_data = new FormData;
        form_data.append('module', 'asset-registration');
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
                $scope.form.assetFile = response.data;
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
	console.log(link);
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

//Asset Visibility Booking controller 
app.controller("AssetBookingController", function ($scope, $http, Excel, $timeout) {
    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;


    autoAssetsVisibilityBookingListFetch();
    function autoAssetsVisibilityBookingListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/asset/getAssetBooking'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.assetsBookings = response.data;
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

    $scope.BookingAssetsVisibility = function () {
        $scope.form = {};
        $scope.changeView('add');

    }
    $scope.months = {
        January: '01',
        February: '02',
        March: '03',
        April: '04',
        May: '05',
        June: '06',
        July: '07',
        August: '08',
        September: '09',
        October: '10',
        November: '11',
        December: '12',
    }
    $scope.month_data = ["", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    $scope.addSaveAssetsVisibilityBooking = function () {
        console.log($scope.form);
        showHideLoad();
        var no_months = parseInt($scope.form.noMth);
        console.log(no_months);
        // var booked_upto = $scope.form.bookingUpto;
        var start_month = $scope.form.strtMth;
        var strt_mth = start_month.split('.');


        var int_month = strt_mth[0];

        var int_year = parseInt(strt_mth[1]);
        var int_day = 01;
        if (no_months == 1) {
            no_months = no_months;
        } else {
            no_months = no_months - 1;
        }

        var d = new Date(int_year, parseInt($scope.months[int_month]), int_day);

        var new_date = d.setMonth(parseInt($scope.months[int_month]) + no_months);

        var a = new Date(+new_date);

        var end_date = a.toLocaleDateString("en-IN");
        var end_date_array = end_date.split('/');
        var end_month = $scope.month_data[a.getMonth()];
        var end_year = end_date_array[2];
        console.log(end_month);

        console.log(end_year);
        $scope.form.strtMth = strt_mth[1] + '-' + strt_mth[0];
        if ($scope.form.noMth == 1) {
            $scope.form.endMth = strt_mth[1] + '-' + strt_mth[0];
        } else {
            $scope.form.endMth = end_year + '-' + end_month;
        }

        console.log($scope.form);
        var method = "POST";
        var url = 'api/asset/postAssetBooking';

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            }, transformResponse: angular.identity
        }).then(_success, _error);
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        autoAssetsVisibilityBookingListFetch();
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


    $scope.openAssetModal = function (type) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'type': type },
            url: 'api/asset/getAssetByOutletType'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.assetdata = response.data;
            $('#assets_item_modal').modal('show');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.selectItemId = function (assetdata) {
        $('#assets_item_modal').modal('hide');

        $scope.form.assetRegId = assetdata.id;
        $scope.form.outletName = assetdata.outletName;
        $scope.form.assetsTypeName = assetdata.assetsTypeName;
        $scope.form.assetNo = assetdata.assetNo;
        $scope.form.year = assetdata.year;
        $scope.form.productFamily = assetdata.productFamily;
        $scope.form.maxsf = assetdata.maxsf;
        $scope.form.assetFile = assetdata.assetFile;
        $scope.form.cost = assetdata.cost;
        $scope.form.gst = assetdata.gst;
        $scope.form.assetsTypeId = assetdata.assetsTypeId;
        $scope.form.visibilityType = assetdata.visibilityType;
    }

    $scope.selectArticleModal = function (id, family) {
        $http({
            method: 'GET',
            params: { 'vendorId': id, 'family': family },
            url: 'api/asset/getArticleForAssetBookingByVendorId'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.form.products = response.data;
            $scope.form.details = [];
            // angular.forEach($scope.form.product_details, function (val, key) {
            //     $scope.productId[val.id] = true;
            // });
            // $scope.form.product_details = [];
            $('#link_article_modal').modal('show');


        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.deleteSelectShowProducts = function (index) {
        $scope.form.details.splice(index, 1);
    }
    $scope.removeSelectProducts = function (index, product) {
        if (product.id != undefined) {
            showHideLoad();
            var method = "DELETE";
            var url = 'api/asset/deleteAssetBookingDetails';
            $http({
                method: method,
                params: { 'id': product.id },
                url: url,
                headers: {
                    'Content-Type': 'application/json'
                },
                transformResponse: angular.identity
            }).then(function successCallback(response) {
                console.log(response);
                $scope.form.details.splice(index, 1);
                showHideLoad(true);
                Swal.fire(
                    'Deleted!',
                    response.data,
                    'success'
                )
            }, function errorCallback(response) {
                console.log(response);
            });
        } else {
            $scope.form.details.splice(index, 1);
        }
    }

    $scope.selectShowProducts = function (product) {
        $scope.form.details.push({ articleId: product.id, articleName: product.name, eanCode: product.eanCode, primaryDoc: '', secondaryDoc: '' });
        $('#link_article_modal').modal('hide');
    }

    getVendorForAssetVisibilityBooking();
    function getVendorForAssetVisibilityBooking() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorForProduct'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendor_aseet_booking = response.data;
            $scope.changeView('list');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.calculateAmount = function (no_mth, gst) {
        let amt = ($scope.form.cost * no_mth);
        let total = amt * ((gst + 100) / 100);
        $scope.form.amount = amt;
        $scope.form.totalAmount = parseFloat(total).toFixed(2);
    }

    $scope.editAssetsVisibilityBooking = function (id) {
        $scope.form = {};
        console.log(id);
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/asset/getAssetBookingId'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;

            var strt_mth = $scope.form.strtMth.split('-');
            $scope.form.strtMth = strt_mth[1] + '.' + strt_mth[0];
            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }


    $scope.editSaveAssetsVisibilityBooking = function () {

        console.log($scope.form);
        showHideLoad();
        var no_months = parseInt($scope.form.noMth);
        console.log(no_months);
        // var booked_upto = $scope.form.bookingUpto;
        var start_month = $scope.form.strtMth;
        var strt_mth = start_month.split('.');


        var int_month = strt_mth[0];

        var int_year = parseInt(strt_mth[1]);
        var int_day = 01;
        if (no_months == 1) {
            no_months = no_months;
        } else {
            no_months = no_months - 1;
        }

        var d = new Date(int_year, parseInt($scope.months[int_month]), int_day);

        var new_date = d.setMonth(parseInt($scope.months[int_month]) + no_months);

        var a = new Date(+new_date);

        var end_date = a.toLocaleDateString("en-IN");
        var end_date_array = end_date.split('/');
        var end_month = $scope.month_data[a.getMonth()];
        var end_year = end_date_array[2];
        console.log(end_month);

        console.log(end_year);
        $scope.form.strtMth = strt_mth[1] + '-' + strt_mth[0];
        if ($scope.form.noMth == 1) {
            $scope.form.endMth = strt_mth[1] + '-' + strt_mth[0];
        } else {
            $scope.form.endMth = end_year + '-' + end_month;
        }

        console.log($scope.form);
        var method = "PUT";
        var url = 'api/asset/updateAssetBooking';
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    }
    $scope.deleteAssetsVisibilityBooking = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this AssetsVisibilityBooking!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/assetsvisibility/deleteAssetsVisibilityBooking';
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
                    autoAssetsVisibilityBookingListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }
    $scope.uploadAnyFile = function (kee, fileup, type) {
        var form_data = new FormData;
        form_data.append('module', 'asset-booking');
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
                if (type == 'first') {
                    $scope.form.details[kee].primaryDoc = response.data;
                } else {
                    $scope.form.details[kee].secondaryDoc = response.data;
                }
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


//Assets Visibility Booking Approval  module controller 
app.controller("AssetBookingApprovalController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoAssetsVisibilityBookingApprovalListFetch();
    function autoAssetsVisibilityBookingApprovalListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/asset/getAssetsVisibilityBookingForApprove'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.assetsapprovals = response.data;
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


    $scope.AssetsvisibilitybookApprovalForm = function (id) {
        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/asset/getAssetBookingId'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form.id = id;
            //$scope.form.type = 'approve';

            $('#formReason').hide();
            $('#formDiscount').hide();
            $('#RejectButton').show();
            $('#RejectFormButton').hide();
            $('#approveFormButton').hide();
            $('#cancelbutton').hide();
            $('#assetsvisibility_book_approve_modal').modal('show');
            $scope.asset_approval = response.data;
            if ($scope.asset_approval.discount == undefined || $scope.asset_approval.discount == null) {
                $scope.form.discount = 0;
            } else {
                $scope.form.discount = $scope.asset_approval.discount;
            }
            $scope.form.id = id;
            showHideLoad(true);
            // $scope.changeView('edit');
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.assetsvisibilitybookDisApprovalForm = function (id) {
        // $scope.add_location_modal != $scope.add_location_modal;
        $scope.form.id = id;
        $scope.form.type = 'disapprove';
        $('#assets_visibility_book_disapprove_modal').modal('show');
    }

    $scope.addSaveApproveAssetsvisibility = function (id) {
        console.log($scope.form);
        showHideLoad();
        var method = "PUT";
        if ($scope.form.type == 'approve') {
            var paramtr = { 'id': id, 'discount': $scope.form.discount };
            var url = 'api/asset/updateAssetBookingApproval';
        } else {
            var paramtr = { 'id': id, 'reason': $scope.form.reason };
            var url = 'api/asset/updateAssetBookingDisapprove';
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
    };
    function _success(response) {
        showHideLoad(true);
        console.log(response);
        $('#assetsvisibility_book_approve_modal').modal('hide');
        autoAssetsVisibilityBookingApprovalListFetch();
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
        $('#assetsvisibility_book_approve_modal').modal('hide');
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

    $scope.VisbilityBooking = function (type) {
        if (type == 'reject') {
            $scope.form.type = 'reject';
            $('#formReason').show();
            $('#formDiscount').hide();
            $('#formDiscountreadonly').hide();
            $('#RejectButton').hide();
            $('#RejectFormButton').show();
            $('#approveFormButton').hide();
            $('#cancelbutton').show();
        } else {
            $scope.form.type = 'approve';
            $('#formReason').hide();
            $('#formDiscount').show();
            $('#formDiscountreadonly').show();
            $('#RejectButton').hide();
            $('#RejectFormButton').hide();
            $('#approveFormButton').show();
            $('#cancelbutton').show();
        }
    }

    $scope.changeView = function (view) {
        if (view == "addUser" || view == "list" || view == "show") {
            $scope.form = {};
        }
        $scope.views.addUser = false;
        $scope.views.ViewEditRegistartionForm = false;
        $scope.views.list = false;
        $scope.views[view] = true;
    }


});

//Asset Visibility Execution module controller 
app.controller("AssetExecutionController", function ($scope, $http, Excel, $timeout) {

    $scope.form = {};
    $scope.views = {};
    $scope.views.list = true;

    autoAssetsExecutionListFetch();
    function autoAssetsExecutionListFetch() {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/asset/getAssetExecution'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.assetsExecutions = response.data;
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

    $scope.AddExecutionAssets = function () {
        $scope.form = {};
        $scope.getVendorForAssetVisibilityExecution();
        $scope.changeView('add');
    }

    $scope.openAssetEcutionModal = function (id) {
        $('#bookings_item_modal').modal('show');
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/asset/getAssetsBookingByVendorIdForExecution'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.asset_ext = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }
    $scope.selectItemId = function (asset) {
        $('#bookings_item_modal').modal('hide');
        //console.log(assetstype);
        $scope.form.assetRegId = asset.id;
        $scope.form.outletName = asset.outletName;
        $scope.form.assetsTypeName = asset.assetsTypeName;
        $scope.form.assetNo = asset.assetNo;
        $scope.form.productFamily = asset.productFamily;
        $scope.form.cost = asset.cost;
        $scope.form.assetsTypeId = asset.assetsTypeId;
        $scope.form.bookingUpto = asset.endMth;
        $scope.form.narration = asset.narration;
        $scope.form.vendorId = asset.vendorId;
        $scope.form.assetbookingId = asset.id;

    }

    $scope.getVendorForAssetVisibilityExecution = function () {

        showHideLoad();
        $http({
            method: 'GET',
            url: 'api/vendor/getVendorForProduct'

        }).then(function successCallback(response) {
            console.log(response);
            $scope.vendor_aseet_booking = response.data;
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.addSaveAssetsVisibilityExe = function () {
        console.log($scope.form);
        showHideLoad();
        var method = "POST";
        var url = 'api/asset/postAssetExecution';

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
        autoAssetsExecutionListFetch();
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

    $scope.editAssetsVisibilityExe = function (id) {

        showHideLoad();
        $http({
            method: 'GET',
            params: { 'id': id },
            url: 'api/asset/getAssetExecutionById'

        }).then(function successCallback(response) {
            console.log(response.data);
            $scope.form = response.data;
            $scope.getVendorForAssetVisibilityExecution();
            $scope.changeView('edit');
            showHideLoad(true);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    $scope.editSaveAssetsVisibilityExe = function () {
        showHideLoad();

        console.log($scope.form);
        var method = "PUT";
        var url = 'api/asset/updateAssetExecution';
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.form),
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: angular.identity
        }).then(_success, _error);
    }
    $scope.deleteAssetsVisibilityExe = function (id) {
        Swal.fire({
            title: 'Are you sure?',
            text: "you want to delete this Asset Execution!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                showHideLoad();
                var method = "DELETE";
                var url = 'api/asset/deleteAssetExecution';
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
                    autoAssetsExecutionListFetch();
                }, function errorCallback(response) {
                    console.log(response);
                });

            }
        })
    }

    $scope.uploadAnyFile = function (fileup) {
        var form_data = new FormData;
        form_data.append('module', 'asset-execution');
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
                $scope.form.executionFile = response.data;
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