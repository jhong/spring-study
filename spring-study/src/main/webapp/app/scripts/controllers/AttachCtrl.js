'use strict';

var contextPath = '/spring-study';

angular.module('springStudyApp').
	controller('AttachListCtrl', function($scope, $http, $location, $filter) {
		
		// 목록 조회
		function getList() {
			$http.get(contextPath+'/rst/attaches/list').
				success(function(result) {
					/*
					var dataList = result.data;
					for (var i=0; i<dataList.length; i++) {
						var tmp = dataList[i];
						tmp.checked = '';
						console.log('i='+i+', tmp.filekey='+tmp.filekey+', tmp.checked='+tmp.checked);
						dataList[i] = tmp;
					}
					*/
					$scope.result = result;
				});
		}
		getList();
		
        // 삭제
		$scope.doDelete = function(filekey) {
			console.log('[AttachListCtrl] doDelete() filekey='+filekey);
			if (confirm("삭제하시겠습니까?")) {
				$http({
					method: 'delete',
					url: contextPath+'/rst/attaches/'+filekey,
					data: $scope.attachVo
				}).success(function(data, status) {                       
		            alert("Success ... " + status);
		            getList();
		        }).error(function(data, status) {
		            alert("Error ... " + status);
		        });
			}
		};
		
		/*
		$scope.toggleChecked = function(data) {
			var tmpArr = $scope.checkedArr;
			var idx = tmpArr.indexOf(data);
			
		    // is currently selected
		    if (idx > -1) {
		    	tmpArr.splice(idx, 1);
		    }

		    // is newly selected
		    else {
		    	tmpArr.push(data);
		    }
			$scope.checkedArr = tmpArr;
		}
		*/
		
        // check 된 데이터 삭제
		$scope.doDeleteChecked = function() {
			var arr = $scope.result.data;
			var selected_key = '';
			for (var i=0; i<arr.length; i++) {
				var row = arr[i];
				if (!row.checked) continue;
				if (i > 0) selected_key += ',';
				selected_key += row.filekey;
			}
			console.log('doDeleteChecked() selected_key='+selected_key);
			
			if (confirm("선택된 데이터를 삭제하시겠습니까?")) {
				$http({
					method: 'delete',
					url: contextPath+'/rst/attaches/'+selected_key
				}).success(function(data, status) {                       
		            alert("Success ... " + status);
		            getList();
		        }).error(function(data, status) {
		            alert("Error ... " + status);
		        });
			}
			
		};
		
	})
	.controller('AttachEditCtrl', function($scope, $http, $location, $routeParams) {
		console.log('AttachEditCtrl start().... path='+$location.path());
		$scope.attachVo = {};
		
		if ($routeParams.filekey !== undefined) {
			//console.log('AttachEditCtrl start().... $routeParams.filekey='+$routeParams.filekey);
			$http.get(contextPath+'/rst/attaches/'+$routeParams.filekey)
				.success(function(data) {
					//console.log('AttachEditCtrl findDetail().... data='+JSON.stringify(data));
					$scope.attachVo = data;
				}).error(function(data, status) {
		            alert("Error ... " + status);
		        });
		}
		
        $scope.setTitle = function(fileElmt) {
            var fileVal=fileElmt.value;
            var filename = fileVal.replace(/^.*[\\\/]/, '');
            var title = filename.substr(0, filename.lastIndexOf('.'));
            $('#filename').val(title);
            $('#filename').focus();
            $scope.attachVo.filename=title;
        };
        
        // 저장
		$scope.doRegist = function() {
			//var formData=new FormData();
			//formData.append("file", $scope.attachVo.file);
			//console.log('[AttachEditCtrl] doRegist() formData='+JSON.stringify(formData));
			console.log('[AttachEditCtrl] doRegist() $scope.attachVo='+JSON.stringify($scope.attachVo));
			$http({
				method: 'post',
				url: contextPath+'/rst/attaches/',
				//headers: { 'Content-Type': 'multipart/form-data'},
				/*
				//transformRequest: transformRequestAsFormPost,
				data: {
					//id: 4,
					filename: "Kim"//,
					//status: "Best Friend"
				}
				*/
				data: $scope.attachVo
			}).success(function(data, status) {                       
	            alert("Success ... " + status);
	        }).error(function(data, status) {
	            alert("Error ... " + status);
	        });			
		};
		
        // 수정
		$scope.doModify = function() {
			console.log('[AttachEditCtrl] doModify() $scope.attachVo='+JSON.stringify($scope.attachVo));
			$http({
				method: 'put',
				url: contextPath+'/rst/attaches/'+$scope.attachVo.filekey,
				data: $scope.attachVo
			}).success(function(data, status) {                       
	            alert("Success ... " + status);
	        }).error(function(data, status) {
	            alert("Error ... " + status);
	        });			
		};

        // 삭제
		$scope.doDelete = function() {
			console.log('[AttachEditCtrl] doDelete() $scope.attachVo='+JSON.stringify($scope.attachVo));
			$http({
				method: 'delete',
				url: contextPath+'/rst/attaches/'+$scope.attachVo.filekey,
				data: $scope.attachVo
			}).success(function(data, status) {                       
	            alert("Success ... " + status);
	            $location.path('/rst/attaches');
	        }).error(function(data, status) {
	            alert("Error ... " + status);
	        });			
		};

        // 목록으로
		$scope.goList = function() {
			console.log('[AttachEditCtrl] goList() start...');
			$location.path('/rst/attaches'); 
		};

	});