var app = angular.module("AdminManagment", ['ui.bootstrap', 'datatables', 'ckeditor']);

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

//Company module controller 
app.controller("CompanyController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoComapnyListFetch();
	function autoComapnyListFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getCompany'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.companies = response.data;
			$scope.company_length = $scope.companies.length;
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
	$scope.AddCompany = function () {
		$scope.changeView('add');
		$scope.fetchCountry();
		$scope.fetchLocations();
	}
	$scope.addSaveCompany = function () {
		console.log($scope.form);
		if ($scope.form.details.length > 0 && $scope.form.details[0].required == true && ($scope.form.details[0].file_upload == undefined || $scope.form.details[0].file_upload == '') && ($scope.form.details[0].link == undefined && $scope.form.details[0].link == '')) {
			$('#docReq').show();
		} else {
			$('#docReq').hide();
			showHideLoad();
			var method = "POST";
			var url = 'api/admin/postCompany';
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
	};
	function _success(response) {
		showHideLoad(true);
		autoComapnyListFetch();
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

	$scope.editCompany = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'api/admin/getCompanyByid'

		}).then(function successCallback(response) {
			console.log(response.data);
			$scope.form = response.data;
			$scope.fetchCountry();
			$scope.fetchLocations();
			// if ($scope.form.details.length == 0) {
			$scope.fetchFileUploadList('C');
			// }
			$scope.fetchState($scope.form.countrycode);
			$scope.changeView('edit');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.editSaveCompany = function () {
		console.log($scope.form);
		if ($scope.form.details[0].required == true) {
			$('#docReq').show();
		} else {
			$('#docReq').hide();
			showHideLoad();

			var method = "PUT";
			var url = 'api/admin/updateCompany';
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
	};
	$scope.deleteCompany = function (id) {
		Swal.fire({
			title: 'Are you sure?',
			text: "you want to delete this company!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				showHideLoad();
				var method = "DELETE";
				var url = 'api/admin/deleteCompany';
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
					autoComapnyListFetch();
				}, function errorCallback(response) {
					console.log(response);
				});
			}
		})
	}
	$scope.uploadAnyFile = function (kee, fileup) {
		var form_data = new FormData;
		form_data.append('module', 'company');
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
		var url = 'api/admin/deleteCompanyDocument';
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

// Application module controller
app.controller("ApplicationController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoApplicationListFetch();
	function autoApplicationListFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getApplicationSetup'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.applications = response.data;
			$scope.application_length = $scope.applications.length;
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
	$scope.AddApplication = function (tableId) {
		$scope.changeView('add');
	}

	$scope.addSaveApplication = function () {
		showHideLoad();
		var method = "POST";
		var url = 'api/admin/postApplicationSetup';
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
		autoApplicationListFetch();
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
	$scope.editApplication = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'api/admin/getApplicationSetupByid'

		}).then(function successCallback(response) {
			console.log(response.data);
			$scope.form = response.data;
			$scope.form.isDelete = String(response.data.isDelete);
			$scope.changeView('edit');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.editSaveApplication = function () {
		console.log($scope.form);
		showHideLoad();

		var method = "PUT";
		var url = 'api/admin/updateApplicationSetup';
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
	$scope.deleteApplication = function (id) {
		Swal.fire({
			title: 'Are you sure?',
			text: "you want to delete this Data!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				showHideLoad();
				var method = "DELETE";
				var url = 'api/admin/deleteApplicationSetup';
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
					autoApplicationListFetch();
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

//Year module controller 
app.controller("YearController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoYearListFetch();
	function autoYearListFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getFinancialYear'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.years = response.data;
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

	$scope.addSaveYear = function () {
		console.log($scope.form);
		showHideLoad();
		if ($scope.form.id == '' || $scope.form.id == undefined) {
			var method = "POST";
			var url = 'api/admin/postFinancialYear';
		} else {
			var method = "PUT";
			var url = 'api/admin/updateFinancialYear';
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
		$('#add_year_modal').modal('hide');
		autoYearListFetch();
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
		$('#add_year_modal').modal('hide');
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
	$scope.editYear = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'api/admin/getFinancialYearByid'

		}).then(function successCallback(response) {
			console.log(response.data);
			$('#add_year_modal').modal('show');
			showHideLoad(true);
			$scope.form = response.data;
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}

	$scope.deleteYear = function (id) {
		// var confirmRemove = confirm("Are you sure you want to delete this location?");
		Swal.fire({
			title: 'Are you sure?',
			text: "you want to delete this financial year!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				var method = "DELETE";
				var url = 'api/admin/deleteFinancialYear';
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
					autoYearListFetch();
				}, function errorCallback(response) {
					console.log(response);
				});

			}
		})
		// if (confirmRemove == true) {

		// }
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

//Document module controller 
app.controller("IdentificationController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoDocumentListFetch();
	function autoDocumentListFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getIdentificationSetup'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.documents = response.data;
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
	$scope.AddDocument = function () {
		$scope.fetchMenu();
		$scope.fetchYear();
		$scope.changeView('add');
	}
	$scope.addSaveDocument = function () {
		showHideLoad();
		var method = "POST";
		var url = 'api/admin/postIdentificationSetup';
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
		autoDocumentListFetch();
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

	$scope.fetchMenu = function () {
		showHideLoad();
		$http({
			method: 'GET',
			url: 'getMenuForIdentification'

		}).then(function successCallback(response) {

			$scope.menus = response.data;
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
	$scope.editDocument = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'api/admin/getIdentificationSetupByid'

		}).then(function successCallback(response) {
			console.log(response.data);
			$scope.fetchMenu();
			$scope.fetchYear();
			$scope.form = response.data;
			$scope.form.isYearUse = String(response.data.isYearUse);
			$scope.form.tradingVendor = String(response.data.tradingVendor);
			$scope.changeView('edit');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.editSaveDocument = function () {

		console.log($scope.form);
		showHideLoad();

		var method = "PUT";
		var url = 'api/admin/updateIdentificationSetup';
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
	$scope.deleteDocument = function (id) {
		Swal.fire({
			title: 'Are you sure?',
			text: "you want to delete this document!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				showHideLoad();
				var method = "DELETE";
				var url = 'api/admin/deleteIdentificationSetup';
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
					autoDocumentListFetch();
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

//Email module controller 
app.controller("EmailController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoEmailListFetch();
	function autoEmailListFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getEmailSetup'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.emails = response.data;
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
	$scope.AddEmail = function () {

		$scope.fetchMenu();
		$scope.changeView('add');
		$scope.form.menuid = null;
	}
	$scope.addSaveEmail = function () {
		showHideLoad();
		var method = "POST";
		var url = 'api/admin/postEmailSetup';
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
		autoEmailListFetch();
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

	$scope.fetchMenu = function () {
		showHideLoad();
		$http({
			method: 'GET',
			url: 'getMenuForEmail'

		}).then(function successCallback(response) {

			$scope.menus = response.data;
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.editEmail = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'api/admin/getEmailSetupByid'

		}).then(function successCallback(response) {
			console.log(response.data);
			$scope.fetchMenu();
			$scope.form = response.data;
			$scope.changeView('edit');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.editSaveEmail = function () {

		console.log($scope.form);
		showHideLoad();

		var method = "PUT";
		var url = 'api/admin/updateEmailSetup';
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
	$scope.deleteEmail = function (id) {
		Swal.fire({
			title: 'Are you sure?',
			text: "you want to delete this email!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				showHideLoad();
				var method = "DELETE";
				var url = 'api/admin/deleteEmailSetup';
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
					autoEmailListFetch();
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

//Approval module controller 
app.controller("ApprovalController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoApprovalFetch();
	function autoApprovalFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getApprovalSetup'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.approval_setups = response.data;
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
	$scope.AddApprovalSetup = function () {
		$scope.fetchMenu();
		$scope.fetchUser();
		$('#add_approval_modal').modal('show');
	}
	$scope.addSaveApprovalSetup = function () {
		console.log($scope.form);
		showHideLoad();
		if ($scope.form.id == '' || $scope.form.id == undefined) {
			var method = "POST";
			var url = 'api/admin/postApprovalSetup';
		} else {
			var method = "PUT";
			var url = 'api/admin/updateApprovalSetup';
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
		$('#add_approval_modal').modal('hide');
		showHideLoad(true);
		autoApprovalFetch();
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
		$('#add_approval_modal').modal('hide');
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
	$scope.fetchMenu = function () {
		showHideLoad();
		$http({
			method: 'GET',
			url: 'getMenuForApproval'

		}).then(function successCallback(response) {

			$scope.menus = response.data;
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.fetchUser = function () {
		showHideLoad();
		$http({
			method: 'GET',
			url: 'getUser'

		}).then(function successCallback(response) {

			$scope.users = response.data;
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.editApprovalSetup = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'api/admin/getApprovalSetupByid'

		}).then(function successCallback(response) {
			console.log(response.data);
			$scope.fetchMenu();
			$scope.fetchUser();
			$scope.form = response.data;
			$('#add_approval_modal').modal('show');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}

	$scope.deleteApprovalSetup = function (id) {
		// var confirmRemove = confirm("Are you sure you want to delete this location?");
		Swal.fire({
			title: 'Are you sure?',
			text: "you want to delete this Approval setup!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				showHideLoad();
				var method = "DELETE";
				var url = 'api/admin/deleteApprovalSetup';
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
					autoApprovalFetch();
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

//Login History module controller 
app.controller("LoginHistoryController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoLoginListFetch();
	function autoLoginListFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getLoginHostory'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.login_history = response.data;
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

	$scope.AdvanceSearchModal = function (id) {
		showHideLoad();
		$scope.fetchUsers();
		$('#advance_search_modal').modal('show');
		showHideLoad(true);
	}
	$scope.AdvanceSearchLoginHistory = function () {
		if ($scope.form.username == undefined && $scope.form.from == undefined && $scope.form.to == undefined) {
			$('#advance_search_modal').modal('hide');
			Swal.fire(
				'Error!',
				'Please select at least one filter',
				'error'
			)
		} else if ($scope.form.from != undefined && $scope.form.to == undefined || $scope.form.from == undefined && $scope.form.to != undefined) {
			$('#advance_search_modal').modal('hide');
			Swal.fire(
				'Error!',
				'Please select a complete date range',
				'error'
			)
		} else {
			// alert('Minimum length for student search is 3');
			if ($scope.form.username == undefined) {
				$scope.form.username = 0;
			}
			if ($scope.form.from == undefined) {
				$scope.form.from = 0;
			}
			if ($scope.form.to == undefined) {
				$scope.form.to = 0;
			}
			console.log($scope.form);
			showHideLoad();
			$http({
				method: 'POST',
				url: 'api/admin/postLoginHostory',
				data: angular.toJson($scope.form),
				headers: {
					'Content-Type': 'application/json'
				},
				transformResponse: angular.identity

			}).then(function successCallback(response) {
				console.log(response);
				$scope.login_history = JSON.parse(response.data);
				$('#advance_search_modal').modal('hide');
				showHideLoad(true);
			}, function errorCallback(response) {
				console.log(response.statusText);
			});
		}
	}
	$scope.fetchUsers = function () {
		$http({
			method: 'GET',
			url: 'getUser'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.users = response.data;
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.getIndvUserActivities = function (username, login_time, logout_time) {
		$scope.form.username = username;
		$scope.form.from = login_time;
		$scope.form.to = logout_time;
		console.log($scope.form);
		$http({
			method: 'POST',
			url: 'api/admin/getTransactionlog',
			data: angular.toJson($scope.form),
			headers: {
				'Content-Type': 'application/json'
			},
			transformResponse: angular.identity


		}).then(function successCallback(response) {
			console.log(response);
			$scope.activities = JSON.parse(response.data);
			$scope.changeView('activity_list');
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.changeView = function (view) {
		if (view == "add" || view == "list" || view == "show") {
			$scope.form = {};
		}
		$scope.views.add = false;
		$scope.views.edit = false;
		$scope.views.list = false;
		$scope.views.activity_list = false;
		$scope.views[view] = true;
	}


});

//Transaction Log module controller 
app.controller("TransactionLogController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoTransactionListFetch();
	function autoTransactionListFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getLoginHostory'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.transactions = response.data;
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

//Menu module controller 
app.controller("MenuController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoMenuListFetch();
	function autoMenuListFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getViewPrivilege'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.menus = response.data;
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

	$scope.AddMenu = function () {
		$scope.form = {};
		$('#add_menu_modal').modal('show');
	}

	$scope.addSaveMenu = function () {
		//console.log($scope.form);
		showHideLoad();
		if ($scope.form.id == '' || $scope.form.id == undefined) {
			var method = "POST";
			var url = 'api/admin/postViewPrivilege';
		} else {
			var method = "PUT";
			var url = 'api/admin/updateViewPrivilege';
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
		$('#add_menu_modal').modal('hide');
		autoMenuListFetch();
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
		$('#add_menu_modal').modal('hide');
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
	$scope.fetchMenuByid = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'api/admin/getViewPrivilegeByid'

		}).then(function successCallback(response) {
			console.log(response.data);
			$scope.form = response.data;
			$scope.form.isApproval = String(response.data.isApproval);
			$scope.form.isEmail = String(response.data.isEmail);
			$scope.form.isIdentify = String(response.data.isIdentify);
			$('#add_menu_modal').modal('show');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}

	$scope.deleteMenu = function (id) {
		// var confirmRemove = confirm("Are you sure you want to delete this location?");
		Swal.fire({
			title: 'Are you sure?',
			text: "you want to delete this Menu!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				var method = "DELETE";
				var url = 'api/admin/deleteViewPrivilege';
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
					autoMenuListFetch();
				}, function errorCallback(response) {
					console.log(response);
				});

			}
		})
		// if (confirmRemove == true) {

		// }
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

app.controller("DocumentController", function ($scope, $http, Excel, $timeout) {

	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoDocumentListFetch();
	function autoDocumentListFetch() {

		showHideLoad();
		$http({
			method: 'GET',
			url: 'api/admin/getDocument'

		}).then(function successCallback(response) {
			console.log(response);
			$scope.documents = response.data;
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

	// $scope.AddMenu = function () {
	//     $scope.form = {};
	//     $('#add_menu_modal').modal('show');
	// }

	$scope.addEditSaveDocument = function () {
		console.log($scope.form);
		showHideLoad();
		var method = "PUT";
		var url = 'api/admin/updateDocument';
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
		autoDocumentListFetch();
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
	$scope.editDocument = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'api/admin/getDocumentByid'

		}).then(function successCallback(response) {
			console.log(response.data);
			$scope.form = response.data;
			angular.forEach($scope.form.details, function (val, key) {
				val.required = String(val.required);
			});
			$scope.changeView('add');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	// $scope.fileUpload = {
	//     module: "",
	//     file_upload: []
	// }
	$scope.uploadAnyFile = function (kee, fileup) {
		var form_data = new FormData;
		form_data.append('module', 'document');
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
				$scope.form.details[kee].file = response.data;
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
	$scope.form.details = [];
	$scope.appendNewitem = function () {
		var counNew_item_list = 0;
		if (typeof $scope.form.details === "undefined") {
			counNew_item_list = 0
		} else {
			counNew_item_list = $scope.form.details.length;
		}
		if (counNew_item_list > 0) {
			$scope.form.details.push({ 'document': '', 'file': '', 'required': '' });
		} else {
			$scope.form.details = [];
			$scope.form.details.push({ 'document': '', 'file': '', 'required': '' });
		}
	}
	$scope.removeAppendItemlist = function (index, id) {
		console.log(id);
		if (id == undefined) {
			$scope.form.details.splice(index, 1);
		} else {
			showHideLoad();
			$http({
				method: 'DELETE',
				params: { 'id': id },
				url: 'api/admin/deleteDocumentDetails',
				headers: {
					'Content-Type': 'application/json'
				},
				transformResponse: angular.identity
			}).then(function successCallback(response) {
				console.log(response.data);
				$scope.form.details.splice(index, 1);
				showHideLoad(true);
			}, function errorCallback(response) {
				console.log(response.statusText);
			});
		}
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
	$scope.removeFile = function (kee) {
		$scope.form.details[kee].file = "";
	}
	$scope.changeView = function (view) {

		$scope.views.add = false;
		$scope.views.edit = false;
		$scope.views.list = false;
		$scope.views[view] = true;
	}


});
