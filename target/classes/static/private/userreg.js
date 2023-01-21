var app = angular.module("UserManagment", ['ui.bootstrap', 'datatables']);

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


//user module controller 
app.controller("UserController", function ($scope, $http, Excel, $timeout) {

	$scope.hodlists = [];
	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoUserListFetch();
	function autoUserListFetch() {
		showHideLoad();
		$http({
			method: 'GET',
			url: 'getUser'

		}).then(function successCallback(response) {
			//console.log(response);
			$scope.user_list = response.data;
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


	//HTTP POST/PUT methods for add/edit customer
	// with the help of id, we are going to find out whether it is put or post operation
	$scope.hoc_privileges_id = {};
	$scope.submitUser = function () {
		showHideLoad();

		var i = 0;
		$scope.form.sub_module_id = [];
		angular.forEach($scope.sub_module_id, function (val, key) {
			if (val == true) {
				$scope.form.sub_module_id[i] = key;
				i = i + 1;
			}
		});
		var j = 0;

		$scope.form.hoc_privileges_id = [];
		angular.forEach($scope.hoc_privileges_id, function (val, key) {
			if (val == true) {
				$scope.form.hoc_privileges_id[j] = key;
				j = j + 1;
			}
		});
		console.log($scope.form);


		var method = "POST";
		var url = 'addUser';
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

	$scope.fileUpload = {
		module: "",
		file: []
	}
	$scope.AddSaveprofilePicUpload = function () {
		var form_data = new FormData;
		form_data.append('module', 'profilepic');
		form_data.append("file", $scope.fileUpload.profilepic);
		//console.log(form_data);

		var config = {
			transformResponse: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		}
		var url = "api/upload/uploadProfilePic";

		$http.post(url, form_data, config).then(
			// Success
			function (response) {
				$scope.form.profilePicLink = response.data;
				$scope.submitUser();
			},
			// Error
			function (response) {
				$scope.form.profilePicLink = "profilPic.png";
				$scope.submitUser();
				console.log(response);
			}
		);
	}
	$scope.profilePicUpload = function () {
		var form_data = new FormData;
		form_data.append('module', 'profilepic');
		form_data.append("file", $scope.fileUpload.file);
		//console.log(form_data);

		var config = {
			transformResponse: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		}
		var url = "api/upload/uploadProfilePic";

		$http.post(url, form_data, config).then(
			// Success
			function (response) {
				$scope.form.profilePicLink = response.data;
				$scope.editSaveUser();
			},
			// Error
			function (response) {
				$scope.form.profilePicLink = "profilPic.png";
				$scope.editSaveUser();
				console.log(response);
			}
		);
	}


	function _success(response) {
		console.log(response);
		showHideLoad(true);
		autoUserListFetch();
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
		//console.log(response);
		// document.getElementById("loader").style.display = "none";
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

	$scope.AddUser = function () {
		// showHideLoad();
		$scope.fetchHod();
		$scope.fetchHocPrivilege();
		$scope.fetchRoles();
		$scope.fetchViewPrivilege();
		$scope.fetchLocations();
		$scope.changeView('addUser');
		// showHideLoad(true);
	}
	$scope.fetchHod = function () {
		showHideLoad();
		$http({
			method: 'GET',
			url: 'getHodList'

		}).then(function successCallback(response) {

			$scope.hod_lists = response.data;
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.fetchHocPrivilege = function () {
		$http({
			method: 'GET',
			url: 'getPrivileges'

		}).then(function successCallback(response) {

			$scope.hoc_privileges = response.data;
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.fetchRoles = function () {
		$http({
			method: 'GET',
			url: 'getRoleList'

		}).then(function successCallback(response) {

			$scope.roles = response.data;
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.fetchViewPrivilege = function () {
		$http({
			method: 'GET',
			url: 'getViewPrivileges'

		}).then(function successCallback(response) {

			$scope.view_privileges = response.data;
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.fetchLocations = function () {
		$http({
			method: 'GET',
			url: 'api/master/getLocation'

		}).then(function successCallback(response) {
			//console.log(response);
			$scope.locations = response.data;
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.module_id = [];
	$scope.sub_module_id = [];
	$scope.isAllSelected = false;
	$scope.toggleAll = function () {
		if ($scope.isAllSelected == false) {
			$scope.isAllSelected = true;
		} else {
			$scope.isAllSelected = false;
		}
		angular.forEach($scope.view_privileges, function (itm) {

			$scope.module_id[itm.id] = $scope.isAllSelected;
			angular.forEach(itm.menu, function (val) {
				$scope.sub_module_id[val.id] = $scope.isAllSelected;
			});
		});

	}
	$scope.toggleAllModuleWise = function (id, key) {
		var toggleStatus = !$scope.module_id[id];

		angular.forEach($scope.view_privileges[key].menu, function (itm) {
			$scope.sub_module_id[itm.id] = toggleStatus;
		});

	}
	// $scope.optionToggled = function () {
	// 	$scope.isAllSelected = $scope.view_privileges.every(function (itm) {
	// 		return $scope.form.module_id[itm.id];
	// 	})
	// }


	$scope.editUser = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'getUserById'

		}).then(function successCallback(response) {
			$scope.fetchHod();
			$scope.fetchHocPrivilege();
			$scope.fetchRoles();
			$scope.fetchViewPrivilege();
			$scope.fetchLocations();
			$scope.form = response.data;
			angular.forEach($scope.form.sub_module_id, function (val, key) {
				$scope.sub_module_id[val] = true;
			});
			angular.forEach($scope.form.module_id, function (val, key) {
				$scope.module_id[val] = true;
			});
			angular.forEach($scope.form.hoc_privileges_id, function (val, key) {
				$scope.hoc_privileges_id[val] = true;
			});
			$scope.changeView('editUser');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.editSaveUser = function () {
		// $scope.profilePicUpload();
		showHideLoad();
		var i = 0;
		$scope.form.sub_module_id = [];
		angular.forEach($scope.sub_module_id, function (val, key) {
			if (val == true) {
				$scope.form.sub_module_id[i] = key;
				i = i + 1;
			}
		});
		var j = 0;

		$scope.form.hoc_privileges_id = [];
		angular.forEach($scope.hoc_privileges_id, function (val, key) {
			if (val == true) {
				$scope.form.hoc_privileges_id[j] = key;
				j = j + 1;
			}
		});
		if ($scope.form.password == null) {
			$scope.form.password = '';
		}
		//console.log($scope.form);
		var method = "PUT";
		var url = 'updateUser';
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

	$scope.deleteUser = function (id) {
		// var confirmRemove = confirm("Are you sure you want to delete this location?");

		Swal.fire({
			title: 'Are you sure?',
			text: "you want to delete this User!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				showHideLoad();
				var method = "DELETE";
				var url = 'deleteUser';
				$http({
					method: method,
					params: { 'id': id },
					url: url,
					headers: {
						'Content-Type': 'application/json'
					},
					transformResponse: angular.identity
				}).then(function successCallback(response) {
					//console.log(response);
					showHideLoad(true);
					Swal.fire(
						'Deleted!',
						'User has been deleted.',
						'success'
					)
					autoUserListFetch();
				}, function errorCallback(response) {
					console.log(response);
				});

			}
		})
		// if (confirmRemove == true) {

		// }
	}

	$scope.ChangeStatus = function (id, type) {
		// var confirmRemove = confirm("Are you sure you want to delete this location?");

		Swal.fire({
			title: 'Are you sure?',
			text: "you want to update status of this User!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes'
		}).then((result) => {
			if (result.isConfirmed) {
				showHideLoad();
				var method = "PUT";
				if (type == 'active') {
					var url = 'userDeactivation';
				} else {
					var url = 'userActivation';
				}
				$http({
					method: method,
					params: { 'id': id },
					url: url,
					headers: {
						'Content-Type': 'application/json'
					},
					transformResponse: angular.identity
				}).then(function successCallback(response) {
					//console.log(response);
					showHideLoad(true);
					Swal.fire(
						'Updated!',
						response.data,
						'success'
					)
					autoUserListFetch();
				}, function errorCallback(response) {
					console.log(response);
				});

			}
		})
		// if (confirmRemove == true) {

		// }
	}

	$scope.checkUsername = function (username) {
		// console.log(username);
		if (username != undefined) {

			showHideLoad();
			$http({
				method: 'GET',
				params: { 'username': username },
				url: 'CheckUsername'

			}).then(function successCallback(response) {

				$scope.username_status = response.data;
				console.log($scope.username_status);
				if ($scope.username_status == false) {
					$('#username_msg').hide();
					$('#username_msg_edit').hide();
				} else {
					$('#username_msg').show();
					$('#username_msg_edit').show();
				}
				showHideLoad(true);
			}, function errorCallback(response) {
				console.log(response.statusText);
			});
		}
	}

	$scope.CheckUserEmail = function (email) {
		// console.log(email);
		if (email != undefined) {

			showHideLoad();
			$http({
				method: 'GET',
				params: { 'email': email },
				url: 'CheckUserEmail'

			}).then(function successCallback(response) {

				$scope.email_status = response.data;
				console.log($scope.email_status);
				if ($scope.email_status == false) {
					$('#email_msg').hide();
					$('#email_msg_edit').hide();
				} else {
					$('#email_msg').show();
					$('#email_msg_edit').show();
				}
				showHideLoad(true);
			}, function errorCallback(response) {
				console.log(response.statusText);
			});
		}
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
app.controller("RolesController", function ($scope, $http, Excel, $timeout) {

	$scope.hodlists = [];
	$scope.form = {};
	$scope.views = {};
	$scope.views.list = true;

	autoRoleListFetch();
	function autoRoleListFetch() {
		showHideLoad();
		$http({
			method: 'GET',
			url: 'getRoleList'

		}).then(function successCallback(response) {
			//console.log(response);
			$scope.role_list = response.data;
			$scope.changeView('list');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}

	$scope.exportToExcel = function (tableId) {
		//console.log(tableId);
		var exportHref = Excel.tableToExcel(tableId, 'WireWorkbenchDataExport');
		$timeout(function () { location.href = exportHref; }, 100);
	}
	$scope.AddRole = function () {
		showHideLoad();
		$scope.fetchViewPrivilege();
		$scope.form = {};
		$scope.sub_module_id = {};
		$scope.privilages = {};
		$scope.fetchHocPrivilege();
		$scope.changeView('addRole');
		showHideLoad(true);
	}


	//HTTP POST/PUT methods for add/edit customer
	// with the help of id, we are going to find out whether it is put or post operation
	$scope.privilages = [];
	$scope.submitRole = function () {
		showHideLoad();
		var i = 0;
		$scope.form.sub_module_id = [];
		angular.forEach($scope.sub_module_id, function (val, key) {
			if (val == true) {
				$scope.form.sub_module_id[i] = key;
				i = i + 1;
			}
		});
		var j = 0;
		$scope.form.privilages = [];
		angular.forEach($scope.privilages, function (val, key) {
			if (val == true) {
				$scope.form.privilages[j] = key;
				j = j + 1;
			}
		});

		$scope.form.name = 'ROLE_' + $scope.form.name
		//console.log($scope.form);
		var method = "POST";
		var url = 'addRole';
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
		autoRoleListFetch();
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

	$scope.fetchViewPrivilege = function () {
		showHideLoad();
		$http({
			method: 'GET',
			url: 'getViewPrivileges'

		}).then(function successCallback(response) {
			//console.log(response);
			$scope.view_privileges = response.data;
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.fetchHocPrivilege = function () {
		$http({
			method: 'GET',
			url: 'getPrivileges'

		}).then(function successCallback(response) {

			$scope.hoc_privileges = response.data;
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}

	$scope.module_id = [];
	$scope.sub_module_id = [];
	$scope.isAllSelected = false;
	$scope.toggleAll = function () {
		if ($scope.isAllSelected == false) {
			$scope.isAllSelected = true;
		} else {
			$scope.isAllSelected = false;
		}
		angular.forEach($scope.view_privileges, function (itm) {

			$scope.module_id[itm.id] = $scope.isAllSelected;
			angular.forEach(itm.menu, function (val) {
				$scope.sub_module_id[val.id] = $scope.isAllSelected;
			});
		});

	}
	$scope.toggleAllModuleWise = function (id, key) {
		var toggleStatus = !$scope.module_id[id];
		// if ($scope.module_id[id] == false) {
		// 	$scope.module_id[id] = true;
		// } else {
		// 	$scope.module_id[id] = false;
		// }
		angular.forEach($scope.view_privileges[key].menu, function (itm) {
			$scope.sub_module_id[itm.id] = toggleStatus;
		});

	}

	$scope.editRole = function (id) {
		showHideLoad();
		$http({
			method: 'GET',
			params: { 'id': id },
			url: 'getRoleById'

		}).then(function successCallback(response) {
			$scope.fetchHocPrivilege();
			$scope.fetchViewPrivilege();
			$scope.form = response.data;
			angular.forEach($scope.form.sub_module_id, function (val, key) {
				$scope.sub_module_id[val] = true;
			});
			angular.forEach($scope.form.module, function (val, key) {
				$scope.module_id[val] = true;
			});
			angular.forEach($scope.form.privilages, function (val, key) {
				$scope.privilages[val] = true;
			});
			$scope.changeView('edit');
			showHideLoad(true);
		}, function errorCallback(response) {
			console.log(response.statusText);
		});
	}
	$scope.editSaveRole = function () {
		showHideLoad();
		var i = 0;
		$scope.form.sub_module_id = [];
		angular.forEach($scope.sub_module_id, function (val, key) {
			if (val == true) {
				$scope.form.sub_module_id[i] = key;
				i = i + 1;
			}
		});
		var j = 0;

		$scope.form.privilages = [];
		angular.forEach($scope.privilages, function (val, key) {
			if (val == true) {
				$scope.form.privilages[j] = key;
				j = j + 1;
			}
		});
		console.log($scope.form);
		var method = "POST";
		var url = 'editRole';
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
	$scope.deleteRole = function (id) {
		// var confirmRemove = confirm("Are you sure you want to delete this location?");

		Swal.fire({
			title: 'Are you sure?',
			text: "you want to delete this role!",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.isConfirmed) {
				showHideLoad();
				var method = "DELETE";
				var url = 'deleteRole';
				$http({
					method: method,
					params: { 'id': id },
					url: url,
					headers: {
						'Content-Type': 'application/json'
					},
					transformResponse: angular.identity
				}).then(function successCallback(response) {
					//console.log(response);
					showHideLoad(true);
					Swal.fire(
						'Deleted!',
						response.data,
						'success'
					)
					autoRoleListFetch();
				}, function errorCallback(response) {
					console.log(response);
				});

			}
		})
		// if (confirmRemove == true) {

		// }
	}

	$scope.changeView = function (view) {
		if (view == "addRole" || view == "list" || view == "show") {
			$scope.form = {};
		}
		$scope.views.addRole = false;
		$scope.views.edit = false;
		$scope.views.list = false;
		$scope.views[view] = true;
	}


});