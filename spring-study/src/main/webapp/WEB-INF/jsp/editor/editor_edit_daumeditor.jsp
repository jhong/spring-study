<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="net.study.common.BizConst" %>
<%@ page import="net.study.common.BizCondition" %>
<%@ page import="net.study.common.DataObject"%>
<%@ page import="net.study.editor.EditorVo"%>
<%
EditorVo form = (EditorVo)request.getAttribute("editorVo");
BizCondition condition = (BizCondition)request.getAttribute("condition");
List attachList = (List)request.getAttribute("attachList");
%>

<script type="text/javaScript">
<!--
/*
 * 저장
 */
function doRegist() {
	
	var form = document.forms["subForm"];
	
	form.command.value = "modify";
	if (form.dbmode.value == "<%=BizConst.CODE_DB_INSERT%>")
		form.command.value = "regist";
	
	form.contents.value = Editor.getContent();

	// 첨부파일
	var attachArr = [];
	Editor.getAttachBox().datalist.each(function(elmt){
		var attach = {
			"filekey": elmt.data.filekey,
			"deletedMark": elmt.deletedMark
		};
		attachArr.push(attach);
	});
	form.attachstr.value = JSON.stringify(attachArr);
	console.log("attachstr="+form.attachstr.value);
	
	form.submit();
}

/*
 * 삭제
 */
function doDelete() {

	var form = document.forms["subForm"];
	form.command.value = "delete";

	form.submit();
}

/*
 * 목록으로 이동
 */
function goList() {

	var form = document.forms["subForm"];
	form.command.value = "viewList";

	form.submit();
}

//-->
</script>

<div class="layout_2">
<div class="content">


<div class="btn_container">
	<input type="button" value="저장" onclick="doRegist();"/>
	<input type="button" value="삭제" onclick="doDelete();"/>
	<input type="button" value="목록으로" onclick="goList();"/>
</div>

<form:form commandName="editorVo" id="subForm" name="subForm" method="post" action="${pageContext.request.contextPath}/editor.do">
<input type="hidden" name="command" />
<input type="hidden" name="dbmode" value="<%=condition.get("dbmode")%>"/>
<input type="hidden" name="editorType" value="<%=condition.get("editorType")%>"/>
<form:hidden path="bbskey"/>
<form:hidden path="bbstype" value="N"/>
<form:hidden path="status" value="USE"/>
<form:hidden path="contents"/>
<form:hidden path="attachstr"/>

<!-- sub title -->
<div class="sub_title">
	<h2>daumeditor 사용</h2>
</div>
<!--// sub title-->
<div class="bd_wrap">
	<table class="view" summary="">
	    <caption></caption>
		<colgroup>
			<col width="18%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>
		<tr>
			<th scope="row" class="ln_r lft"><label for="title">TITLE</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="title" cssErrorClass="type_text err" class="type_text" size="50" maxlength="248"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="title" id="" name="title.errors" cssClass="err" delimiter="</span><span name='title.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		</tbody>
	</table>
</div> <!--// bd_wrap -->

</form:form>


<jsp:include page="/WEB-INF/jsp/include/daumeditor.jsp" />

</div><!-- // content -->
</div><!-- // layout_2 -->



<script type="text/javascript">
var config = {
	txHost: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) http://xxx.xxx.com */
	txPath: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) /xxx/xxx/ */
	txService: 'sample', /* 수정필요없음. */
	txProject: 'sample', /* 수정필요없음. 프로젝트가 여러개일 경우만 수정한다. */
	initializedId: "", /* 대부분의 경우에 빈문자열 */
	wrapper: "tx_trex_container", /* 에디터를 둘러싸고 있는 레이어 이름(에디터 컨테이너) */
	form: 'tx_editor_form'+"", /* 등록하기 위한 Form 이름 */
	txIconPath: "common/js/daumeditor/images/icon/editor/", /*에디터에 사용되는 이미지 디렉터리, 필요에 따라 수정한다. */
	txDecoPath: "common/js/daumeditor/images/deco/contents/", /*본문에 사용되는 이미지 디렉터리, 서비스에서 사용할 때는 완성된 컨텐츠로 배포되기 위해 절대경로로 수정한다. */
	canvas: {
		styles: {
			color: "#123456", /* 기본 글자색 */
			fontFamily: "굴림", /* 기본 글자체 */
			fontSize: "10pt", /* 기본 글자크기 */
			backgroundColor: "#fff", /*기본 배경색 */
			lineHeight: "1.5", /*기본 줄간격 */
			padding: "8px" /* 위지윅 영역의 여백 */
		},
		showGuideArea: false
	},
	events: {
		preventUnload: false
	},
	sidebar: {
		attachbox: {
			show: true,
			confirmForDeleteAll: true
		}
	},
	size: {
		contentWidth: 700 /* 지정된 본문영역의 넓이가 있을 경우에 설정 */
	}
};

EditorJSLoader.ready(function(Editor) {
	var editor = new Editor(config);
	Editor.getCanvas().setCanvasSize({
		height: "200px"
	});
});
	
/**
 * Editor.save()를 호출한 경우 데이터가 유효한지 검사하기 위해 부르는 콜백함수로
 * 상황에 맞게 수정하여 사용한다.
 * 모든 데이터가 유효할 경우에 true를 리턴한다.
 * @function
 * @param {Object} editor - 에디터에서 넘겨주는 editor 객체
 * @returns {Boolean} 모든 데이터가 유효할 경우에 true
 */
function validForm(editor) {
	// Place your validation logic here

	// sample : validate that content exists
	var validator = new Trex.Validator();
	var content = editor.getContent();
	if (!validator.exists(content)) {
		alert('내용을 입력하세요');
		return false;
	}

	return true;
}

/**
 * Editor.save()를 호출한 경우 validForm callback 이 수행된 이후
 * 실제 form submit을 위해 form 필드를 생성, 변경하기 위해 부르는 콜백함수로
 * 각자 상황에 맞게 적절히 응용하여 사용한다.
 * @function
 * @param {Object} editor - 에디터에서 넘겨주는 editor 객체
 * @returns {Boolean} 정상적인 경우에 true
 */
function setForm(editor) {
	console.log("setForm() start... editor="+editor);
	var i, input;
	var form = editor.getForm();
	var content = editor.getContent();
	
	// 본문 내용을 필드를 생성하여 값을 할당하는 부분
	var textarea = document.createElement('textarea');
	textarea.name = 'content';
	textarea.value = content;
	form.createField(textarea);
	
	/* 아래의 코드는 첨부된 데이터를 필드를 생성하여 값을 할당하는 부분으로 상황에 맞게 수정하여 사용한다.
	 첨부된 데이터 중에 주어진 종류(image,file..)에 해당하는 것만 배열로 넘겨준다. */
	var images = editor.getAttachments('image');
	for (i = 0; i < images.length; i++) {
	    // existStage는 현재 본문에 존재하는지 여부
	    if (images[i].existStage) {
	        // data는 팝업에서 execAttach 등을 통해 넘긴 데이터
	        alert('attachment information - image[' + i + '] \r\n' + JSON.stringify(images[i].data));
	        input = document.createElement('input');
	        input.type = 'hidden';
	        input.name = 'attach_image';
	        input.value = images[i].data.imageurl;  // 예에서는 이미지경로만 받아서 사용
	        form.createField(input);
	    }
	}
	
	var files = editor.getAttachments('file');
	for (i = 0; i < files.length; i++) {
	    input = document.createElement('input');
	    input.type = 'hidden';
	    input.name = 'attach_file';
	    input.value = files[i].data.attachurl;
	    form.createField(input);
	}
	return true;
}


function loadContent() {
	var attachments = {};
	attachments['image'] = [];
	attachments['file'] = [];
	/*
	attachments['image'].push({
		'attacher': 'image',
		'data': {
			'imageurl': 'http://cfile273.uf.daum.net/image/2064CD374EE1ACCB0F15C8',
			'filename': 'github.gif',
			'filesize': 59501,
			'originalurl': 'http://cfile273.uf.daum.net/original/2064CD374EE1ACCB0F15C8',
			'thumburl': 'http://cfile273.uf.daum.net/P150x100/2064CD374EE1ACCB0F15C8'
		}
	});
	attachments['file'].push({
		'attacher': 'file',
		'data': {
			'attachurl': 'http://cfile297.uf.daum.net/attach/207C8C1B4AA4F5DC01A644',
			'filemime': 'image/gif',
			'filename': 'editor_bi.gif',
			'filesize': 640
		}
	});
	*/
	<%
	for (int i=0; attachList!=null && i<attachList.size(); i++) {
		DataObject attachData = (DataObject)attachList.get(i);
		String filekey = attachData.getString("filekey");
		String filename = attachData.getString("filename");
		int filesize = attachData.getIntValue("filesize");
		String mimetype = attachData.getString("mimetype");
		if (mimetype == null) mimetype = "";
		if (mimetype.indexOf("image") >= 0) {
			%>
			attachments['image'].push({
				'attacher': 'image',
				'data': {
					'filekey': '<%=filekey%>',
					'imageurl': '<%=request.getContextPath()%>/attach.do?command=downloadImage&filekey=<%=filekey%>',
					'filename': '<%=filename%>',
					'filesize': <%=filesize%>,
					'originalurl': '<%=request.getContextPath()%>/attach.do?command=downloadImage&filekey=<%=filekey%>',
					'thumburl': '<%=request.getContextPath()%>/attach.do?command=downloadImage&filekey=<%=filekey%>'
				}
			});
			<%
		} else {
			%>
			attachments['file'].push({
				'attacher': 'file',
				'data': {
					'filekey': '<%=filekey%>',
					'attachurl': 'http://cfile297.uf.daum.net/attach/207C8C1B4AA4F5DC01A644',
					'filemime': '<%=mimetype%>',
					'filename': '<%=filename%>',
					'filesize': <%=filesize%>
				}
			});
			<%
		}
	}
	%>
	
	/* 저장된 컨텐츠를 불러오기 위한 함수 호출 */
	Editor.modify({
		"attachments": function () { /* 저장된 첨부가 있을 경우 배열로 넘김, 위의 부분을 수정하고 아래 부분은 수정없이 사용 */
			var allattachments = [];
			for (var i in attachments) {
				allattachments = allattachments.concat(attachments[i]);
			}
			return allattachments;
		}(),
		"content": document.getElementById("contents") /* 내용 문자열, 주어진 필드(textarea) 엘리먼트 */
	});
}

$(document).ready(function() {
	loadContent();
});
</script>

