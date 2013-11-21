<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet  version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/TR/REC-html40">
<xsl:template match="/CODE">

<html>
<head>
<title>CODE</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<style>
body
	{
	font-size: 12pt;
	font-family: 굴림,Arial;
	margin-height:0pt;
	margin-width:0pt;
	margin-top:15pt;
	margin-left:0pt;
	scrollbar-face-color: #CACAD1;
	scrollbar-highlight-color: #000000;
	scrollbar-3dlight-color: #CACAD1;
	scrollbar-shadow-color: #000000;
	scrollbar-darkshadow-color: #CACAD1;
	scrollbar-track-color: #CACAD1;
	scrollbar-arrow-color: #000000;
}
	
td
	{
	font-size: 9pt;
	FONT-FAMILY: 굴림,Arial;
	}

.title1
	{
	font-size: 13pt;
	font-weight:bold;
	}	
}		
.sTitle {
	WIDTH: 30px;
}
.sRight {
	margin-left : 30px;
}
.sRightPlus {
	margin-left : 36px;
}
</style>
</head>

<body>
<center>
<table width="650" border="2" bordercolor="black">
<tr>
	<td width="650" align="center">

		<table width="606" height="930" border="0" cellspacing="3" cellpadding="0">

		<!--문서명 기록 시작-->
		<tr height="20"><td> </td></tr>
		<tr> 
		<td>
			<table width="600" border="0" cellpadding="2" cellspacing="0" bordercolor="black" style="border-collapse:collapse">
			<tr> 
				<td width="600" align="center" valign="top">
				<span class="title1">
				코 드<br />
				</span>
				</td>
			</tr>
			</table>
		</td>
		</tr>
		<!--문서명 종료-->

		<tr><td> </td></tr>
		<tr height="20"><td></td></tr>
		<tr> 
			<td>
				<table width="600" border="0" cellpadding="0" cellspacing="0" bordercolor="black" style="border-collapse:collapse">
				<tr> 
					<td width="30" align="left" valign="top">	
					</td>
					<td  align="left" valign="top">
						<table width="100%" border="1" cellpadding="5" cellspacing="5" bordercolor="black" style="border-collapse:collapse">
						<tr>
							<td  align="left" valign="top">
								<span>Except so far as otherwise expressly stated, this documentary credit is subject to the "Uniform Customs and Practice for Documentary Credits" (1993 Revision)
								International Chamber of Commerce (Publication No.500)</span>
							</td>
						</tr>
						</table>
					</td>
					<td width="30" align="left" valign="top">
					</td>
					<!--필요없는 경우 삭제 종료-->
				</tr>
				</table>
			</td>
		</tr>

		<tr height="20"><td> </td></tr>
		<tr> 
			<td>
				<table width="600" border="0" cellpadding="2" cellspacing="0" bordercolor="black" style="border-collapse:collapse">
				<tr height="0"><td width="100%" colspan="3"> </td></tr>
				<tr> 
					<td width="185" align="left" valign="top">
					<span>코드카테고리</span>
					</td>
					<td width="5" align="center" valign="top">
					<span>:</span>
					</td>
					<td width="410" align="left" valign="top">
						<span><xsl:value-of select="CODECATEGORYKEY"/></span>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr> 
			<td>
				<table width="600" border="0" cellpadding="2" cellspacing="0" bordercolor="black" style="border-collapse:collapse">
				<tr height="0"><td width="100%" colspan="3"> </td></tr>
				<tr> 
					<td width="185" align="left" valign="top">
					<span>코드</span>
					</td>
					<td width="5" align="center" valign="top">
					<span>:</span>
					</td>
					<td width="410" align="left" valign="top">
						<span><xsl:value-of select="CODE"/></span>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr> 
			<td>
				<table width="600" border="0" cellpadding="2" cellspacing="0" bordercolor="black" style="border-collapse:collapse">
				<tr height="0"><td width="100%" colspan="3"> </td></tr>
				<tr> 
					<td width="185" align="left" valign="top">
					<span>코드명</span>
					</td>
					<td width="5" align="center" valign="top">
					<span>:</span>
					</td>
					<td width="410" align="left" valign="top">
						<span><xsl:value-of select="CODENAME"/></span>
						(<span><xsl:value-of select="CODEENGNAME"/></span>)
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr> 
			<td>
				<table width="600" border="0" cellpadding="2" cellspacing="0" bordercolor="black" style="border-collapse:collapse">
				<tr height="0"><td width="100%" colspan="3"> </td></tr>
				<tr> 
					<td width="185" align="left" valign="top">
					<span>코드설명</span>
					</td>
					<td width="5" align="center" valign="top">
					<span>:</span>
					</td>
					<td width="410" align="left" valign="top">
						<span><xsl:value-of select="CODEEXPLAIN"/></span>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		
		</table>
	</td>
</tr>
</table>
</center>

</body>
</html>


</xsl:template>
</xsl:stylesheet>