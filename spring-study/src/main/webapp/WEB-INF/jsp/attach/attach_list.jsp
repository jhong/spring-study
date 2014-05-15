<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
<!--

/*
 * 삭제
 */
function doDelete() {

	var formName = "listForm";
	var cmd = "delete";
	var $grid = $("#dataList");
	var rowIds = $grid.jqGrid("getGridParam","selarrrow");
	if (rowIds.length == 0) {
		alert("삭제할 항목을 선택해 주세요!");
		return;
	}

	if (confirm(rowIds.length + "개의 항목을 삭제하시겠습니까?")){
		
		var rowData = new Array();
		$(rowIds).each(function(idx, rowId) {
			var row = $grid.jqGrid("getRowData", rowId);
			//rowData.push($.param(row));
			rowData.push(row);
		});
		var dataToSend = JSON.stringify(rowData);	
		
		var form = document.forms[formName];
		var action = form.action;
		var url = action+"?command="+cmd;
		$.ajax({
		    url: url,
		    data: { selectedData : dataToSend },
		    success: function(msg) {
		    	$(form).submit();
		    	alert(msg);
		    },
	        error: function (request, status, error) {
	        	alert("송신 도중 에러가 발생하였습니다.\n"+error);
	        	if (error == "Bad Request") {
	        		alert("TODO: 목록이 template 코드 그대로인 경우 데이터에 따라 Bad Request 발생함... 추후 목록 정리 예정!!");
	        	}
	        }
		});
		
	}
	
}


$(function(){ 
  $("#dataList").jqGrid({
    url:"<c:url value='attach.do?command=findList'/>",
    datatype: "json",
    mtype: "POST",
    postData: {
    	page : function() { return $("#dataList").getGridParam("page"); },
    	rowCountPerPage : function() { return $("#dataList").getGridParam("rowNum"); },
    	sortType : function() { return $("#dataList").getGridParam("sortname"); },
    	sortValue : function() { return $("#dataList").getGridParam("sortorder"); },
//    	searchDateType : function() { return $("[name=searchDateType]").val(); },
//    	searchStartDate : function() { return $("[name=searchStartDate]").val(); },
//    	searchEndDate : function() { return $("[name=searchEndDate]").val(); },
//    	searchStatus : function() { return $("[name=searchStatus]").val(); },
//    	searchType1 : function() { return $("[name=searchType1]").val(); },
//    	searchValue1 : function() { return $("[name=searchValue1]").val(); }//,
//    	searchType2 : function() { return $("[name=searchType2]").val(); },
//    	searchValue2 : function() { return $("[name=searchValue2]").val(); },
//    	searchType3 : function() { return $("[name=searchType3]").val(); },
//    	searchValue3 : function() { return $("[name=searchValue3]").val(); }
    },	    
    colNames:['FILEKEY', 'HOUSEKEY', 'COMPANYKEY', 'FILENAME', 'MIMETYPE', 'CHARSET', 'FILESIZE'
			, 'UPLOADDATE', 'FILEDESC', 'REF_NO', 'REF_NO2', 'REF_NO_SEQ', 'USERID'],
    colModel :[ 
		{name:"filekey", index:"filekey", width:128},
		{name:"housekey", index:"housekey", width:30},
		{name:"companykey", index:"companykey", width:30},
		{name:"filename", index:"filename", width:170, formatter:viewFormatter},
		{name:"mimetype", index:"mimetype", width:120},
		{name:"charset", index:"charset", width:35},
		{name:"filesize", index:"filesize", width:85, align:"right"},

		{name:"uploaddate", index:"uploaddate", width:80},
		{name:"filedesc", index:"filedesc", width:50},
		{name:"refno", index:"refno", width:120},
		{name:"refno2", index:"refno2", width:30},
		{name:"refnoseq", index:"refnoseq", width:30, align:"right"},
		{name:"userid", index:"userid", width:35} 
    ],
    pager: "#dataPager",
    page: ("${condition.page}" > 1 ? "${condition.page}" : 1),
    rowNum:("${condition.rowCountPerPage}" > 1 ? "${condition.rowCountPerPage}" : 20),
    rowList:[10,20,30],
    height: 200,
    //autowidth: true,
    rownumbers: true,
    sortname: "uploaddate",
    sortorder: "desc",
    viewrecords: true,
    gridview: true,
    multiselect: true,
    caption: "",
    emptyrecords: "",
    loadonce: false,
    loadComplete: function(data) {
    	//noData(data);
	},
	jsonReader : {
        root: "data",
        page: "page",
        total: "total",
        records: "records",
        repeatitems: false,
        cell: "cell",
        //id: "id"
        id: "filekey"
	},
	onSelectRow: function(ids) { //row 선택시 처리. ids는 선택한 row
		//clickListPage("listForm", "dataList", "", ids, "findDetail", "filekey"); // checkbox 클릭시 반응하는 것 막기 위해 주석 처리함
	}
  }); 
});

// 상세페이지 이동 formatter
function viewFormatter(cellvalue, options, rowObject) {
	var linkStr = "<a href=\"attach.do?command=findDetail&selected_key="+rowObject.filekey+"\">"+rowObject.filename+"</a>";
	return linkStr;
}

// 개설금액 formatter
function issueamtFormatter(cellvalue, options, rowObject) {
	var str = "";
	if (cellvalue != null){ 
		str = rowObject.issuecurrency+" "+gridNumFormatter(cellvalue);
	}
	return str;
}

/*
 * 엑셀 다운로드
 */
function downloadExcel(){
	var listForm = document.forms["listForm"];
	var actionOri = listForm.action;
	
	listForm.action = "<c:url value='/attach.do?command=excelDownload'/>";
   	listForm.submit();
   	
   	listForm.action = actionOri; // action값 복원!!
}

/*
 * onload시 실행
 */
$(document).ready(function(){

	// 검색 수행시 화면전환 없이 jqGrid 호출 하도록 
	$("#listForm").submit(function() {
		$("#dataList").clearGridData();
		
	    var newUrl = "<c:url value='attach.do?command=findList'/>" + $(this).serialize();
	    $("#dataList").jqGrid("setGridParam","url", newUrl).trigger("reloadGrid");
	    return false;
	});
  	$("#dataList").setGridWidth($("div#content").width(),false);
  	$("#dataList").setGridHeight("100%",false);
});
//-->
</script>

<div class="layout_2">
<div class="content">

<div class="btn_container">
	<a href="<c:url value='attach.do?command=entry'/>">[등록]</a>
	<input type="button" value="삭제" onclick="doDelete();"/>
</div>

<form id="listForm" name="listForm" method="post" action="<%=request.getContextPath()%>/attach.do">
<input type="hidden" name="command"/>
<input type="hidden" name="selected_key" value="${condition.selected_key}"/>
<input type="hidden" name="page" value="${condition.page}"/>
<input type="hidden" name="rowCountPerPage" value="${condition.rowCountPerPage}"/>
</form>

<div class="gy_box">
<table id="dataList"><tr><td/></tr></table> 
<div id="dataPager"></div>
</div>

</div><!-- // content -->
</div><!-- // layout_2 -->
