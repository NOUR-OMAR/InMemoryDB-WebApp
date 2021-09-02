<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
<title>Admin View</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
 <link
      rel="stylesheet"
      href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
      integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
      crossorigin="anonymous"
    />
</head>

<body>
	<security:authorize access="hasRole('ROLE_ADMIN')">

	<header>
    		<nav class="navbar navbar-expand-md navbar-dark"
    			style="background-color: LightGray">

    			<ul class="navbar-nav">
    				<li><a href="<%=request.getContextPath()%>/logout"
    					class="nav-link">Close and Log out</a></li>

    			</ul>


 <ul class="navbar-nav">
      <div class="row">
        <div class="col-md col-lg-3">
          <div class="dropdown">
            <button
              class="btn btn-block btn btn-primary dropdown-toggle"
              type="button"

              data-toggle="dropdown"
            >
            Options
            </button>

            <div class="dropdown-menu bg-warning text-center">
<button onclick="document.getElementById('id04').style.display='block'" class="dropdown-item  custom-width" >show employees with salaries less than a value</button>
              <button onclick="document.getElementById('id03').style.display='block'" class="dropdown-item custom-width" >show employees with salaries greater than a value</button>
<button onclick="document.getElementById('id02').style.display='block'" class="dropdown-item  custom-width" >show employees with salaries equals a value</button>
            <button onclick="document.getElementById('id05').style.display='block'" class="dropdown-item custom-width" >show employees with specific names</button>
    <a href="<%=request.getContextPath()%>/departmentView" class="dropdown-item">Show Departments</a>

            </div>
          </div>
        </ul>
</nav>
    	</header>
<br>


         <div class="container text-left">

           <a href="javascript:toggle('showEmployeeForm')" class="btn btn-success">Add Employee</a>
                              </div>
                <br>


	<div id="wrap">

		<div class="container">


			<hr>

			<br>

			<table class="datatable table table-striped table-bordered">
            				<thead>
            					<tr>

            						<th>Employee id</th>
            						<th>Employee name</th>
                                    <th>Employee salary</th>
            						<th>Employee department id</th>
            						<th>Employee department name</th>
            						<th>Employee department location</th>
            						<th>Update employee</th>
                                   <th>Delete employee</th>

            					</tr>
            				</thead>
            				<tbody>

            					<c:forEach items="${employees}" var="employee"  >
            						<tr>
            							<td><c:out value="${employee.id}" /></td>
            							<td><c:out value="${employee.name}" /></td>
            							<td><c:out value="${employee.salary}" /></td>
            							<td><c:out value="${employee.department.id}" /></td>
            							<td><c:out value="${employee.department.name}" /></td>
            							<td><c:out value="${employee.department.location}" /></td>
                               <!-- <td><a href="<c:url value='/updateEmployee-${employee.id}' />" class="btn btn-success custom-width">update</a></td> -->
                          <td>   <button onclick="document.getElementById('id01').style.display='block'" class="btn btn-success custom-width" >update</button>
</td>
                                 <td><a href="<c:url value='/deleteEmployee-${employee.id}' />" class="btn btn-danger custom-width">delete</a></td>

            						</tr>
            					</c:forEach>
            				</tbody>

            			</table>


		</div>

		<div class="row" style="margin-right: 10px; margin-left: 10px">
			<p class="demo demo4_bottom"></p>
		</div>


	</div>


	<div class="container col-md-5" id="showEmployeeForm" style="display: none;" >
    		<div class="card">
    			<div class="card-body">

    					<form action="/addEmployee" >

    				<caption>
    					<h2>
                			Add new Employee
    					</h2>
    				</caption>


    				<fieldset class="form-group">
    					<label>Employee Id</label> <input type="text"
    						value="<c:out value='${employee.id}' />" class="form-control"
    						name="id" required="required">
    				</fieldset>

    				<fieldset class="form-group">
    					<label>Employee name</label>
    					<input type="text"
    						value="<c:out value='${employee.name}' />" class="form-control"
    						name="name">
    				</fieldset>

    				<fieldset class="form-group">
                    					<label>Employee salary</label> <input type="text"
                    						value="<c:out value='${employee.salary}' />" class="form-control"
                    						name="salary">
                    				</fieldset>

                    			<fieldset class="form-group">
                                                        	 <label><b>Employee department </b></label>
                                                                                        <select name="departmentId">
                                                                                                                    <c:forEach items="${departments}" var="dept">
                                                                                                                        <option value="${dept.id}">${dept.name}</option>
                                                                                                                    </c:forEach>
                                                                                                                </select>

                                                            	</fieldset>


    				<button type="submit" class="btn btn-success">Add</button>
    				</form>
    			</div>
    		</div>
    		</div>



                <div  id="id01" class="modal"  role="dialog">
                  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content animate" action="/updateEmployee">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>ID</b></label>
                       <input type="text"     value="${employee.id}"  name="id">


	</fieldset>
	<fieldset class="form-group">
                            <label><b>Name</b></label>
                            <input type="text" placeholder="Enter Name" name="name" required>
	</fieldset>
<fieldset class="form-group">
                            <label><b>Salary</b></label>
                            <input type="text" placeholder="Enter salary" name="salary" required>
                            	</fieldset>
			<fieldset class="form-group">
                                                        	 <label><b>Employee department </b></label>
                                                                                        <select name="departmentId">
                                                                                                                    <c:forEach items="${departments}" var="dept">
                                                                                                                        <option value="${dept.id}">${dept.name}</option>
                                                                                                                    </c:forEach>
                                                                                                                </select>

                                                            	</fieldset>
                                                        			        <div class="clearfix">
                                <button type="button" onclick="document.getElementById('id01').style.display='none'" class="btn btn-danger ">Cancel</button>
                                <button type="submit" class="btn btn-success">Save</button>
                            </div>
                        </div>
                    </form>
                </div>

 <div  id="id02" class="modal"  role="dialog">
                  <span onclick="document.getElementById('id02').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content animate" action="/filterSalaryEQ">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>salary</b></label>
                       <input type="text"     value="<c:out value='${employee.salary}' />" class="form-control" name="salary">

	</fieldset>

                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('id02').style.display='none'" class="btn btn-danger ">Cancel</button>
                                <button type="submit" class="btn btn-success">get</button>
                            </div>
                        </div>
                    </form>
                </div>

 <div  id="id03" class="modal"  role="dialog">
                  <span onclick="document.getElementById('id03').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content animate" action="/filterSalaryGT">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>salary</b></label>
                       <input type="text"     value="<c:out value='${employee.salary}' />" class="form-control" name="salary">

	</fieldset>

                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('id03').style.display='none'" class="btn btn-danger ">Cancel</button>
                                <button type="submit" class="btn btn-success">get</button>
                            </div>
                        </div>
                    </form>
                </div>


<div  id="id04" class="modal"  role="dialog">
                  <span onclick="document.getElementById('id04').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content animate" action="/filterSalaryLT">
                        <div class="container">
                        <fieldset class="form-group">
                            <label><b>salary</b></label>
                       <input type="text"     value="<c:out value='${employee.salary}' />" class="form-control" name="salary">

	</fieldset>

                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('id04').style.display='none'" class="btn btn-danger ">Cancel</button>
                                <button type="submit" class="btn btn-success">get</button>
                            </div>
                        </div>
                    </form>
                </div>

                <div  id="id05" class="modal"  role="dialog">
                                  <span onclick="document.getElementById('id05').style.display='none'" class="close" title="Close Modal">×</span>
                                    <form class="modal-content animate" action="/listNames">
                                        <div class="container">
                                        <fieldset class="form-group">
                                            <label><b>name</b></label>
                                       <input type="text"     value="<c:out value='${employee.name}' />" class="form-control" name="name">

                	</fieldset>

                                            <div class="clearfix">
                                                <button type="button" onclick="document.getElementById('id05').style.display='none'" class="btn btn-danger ">Cancel</button>
                                                <button type="submit" class="btn btn-success">get</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>


 <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
 		<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
 		<script src="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/jquery.dataTables.min.js"></script>
 		<script >
 		$.extend( true, $.fn.dataTable.defaults, {
                	"sDom": "<'row'<'col-sm-12'<'pull-right'f><'pull-left'l>r<'clearfix'>>>t<'row'<'col-sm-12'<'pull-left'i><'pull-right'p><'clearfix'>>>",
                    "sPaginationType": "bs_normal",
                    "oLanguage": {
                        "sLengthMenu": "Show _MENU_ Rows",
                        "sSearch": ""
                    }
                } );

                /* Default class modification */
                $.extend( $.fn.dataTableExt.oStdClasses, {
                	"sWrapper": "dataTables_wrapper form-inline"
                } );

                /* API method to get paging information */
                $.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
                {
                	return {
                		"iStart":         oSettings._iDisplayStart,
                		"iEnd":           oSettings.fnDisplayEnd(),
                		"iLength":        oSettings._iDisplayLength,
                		"iTotal":         oSettings.fnRecordsTotal(),
                		"iFilteredTotal": oSettings.fnRecordsDisplay(),
                		"iPage":          oSettings._iDisplayLength === -1 ?
                			0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
                		"iTotalPages":    oSettings._iDisplayLength === -1 ?
                			0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
                	};
                };

                /* Bootstrap style pagination control */
                $.extend( $.fn.dataTableExt.oPagination, {
                	"bs_normal": {
                		"fnInit": function( oSettings, nPaging, fnDraw ) {
                			var oLang = oSettings.oLanguage.oPaginate;
                			var fnClickHandler = function ( e ) {
                				e.preventDefault();
                				if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
                					fnDraw( oSettings );
                				}
                			};
                			$(nPaging).append(
                				'<ul class="pagination">'+
                					'<li class="prev disabled"><a href="#"><span class="glyphicon glyphicon-chevron-left"></span>&nbsp;'+oLang.sPrevious+'</a></li>'+
                					'<li class="next disabled"><a href="#">'+oLang.sNext+'&nbsp;<span class="glyphicon glyphicon-chevron-right"></span></a></li>'+
                				'</ul>'
                			);
                			var els = $('a', nPaging);
                			$(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
                			$(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
                		},
                		"fnUpdate": function ( oSettings, fnDraw ) {
                			var iListLength = 5;
                			var oPaging = oSettings.oInstance.fnPagingInfo();
                			var an = oSettings.aanFeatures.p;
                			var i, ien, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);
                			if ( oPaging.iTotalPages < iListLength) {
                				iStart = 1;
                				iEnd = oPaging.iTotalPages;
                			}
                			else if ( oPaging.iPage <= iHalf ) {
                				iStart = 1;
                				iEnd = iListLength;
                			} else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
                				iStart = oPaging.iTotalPages - iListLength + 1;
                				iEnd = oPaging.iTotalPages;
                			} else {
                				iStart = oPaging.iPage - iHalf + 1;
                				iEnd = iStart + iListLength - 1;
                			}
                			for ( i=0, ien=an.length ; i<ien ; i++ ) {
                				$('li:gt(0)', an[i]).filter(':not(:last)').remove();
                				for ( j=iStart ; j<=iEnd ; j++ ) {
                					sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
                					$('<li '+sClass+'><a href="#">'+j+'</a></li>')
                						.insertBefore( $('li:last', an[i])[0] )
                						.bind('click', function (e) {
                							e.preventDefault();
                							if ( oSettings.oApi._fnPageChange(oSettings, parseInt($('a', this).text(),10)-1) ) {
                								fnDraw( oSettings );
                							}
                						} );
                				}
                				if ( oPaging.iPage === 0 ) {
                					$('li:first', an[i]).addClass('disabled');
                				} else {
                					$('li:first', an[i]).removeClass('disabled');
                				}

                				if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                					$('li:last', an[i]).addClass('disabled');
                				} else {
                					$('li:last', an[i]).removeClass('disabled');
                				}
                			}
                		}
                	},
                	"bs_two_button": {
                		"fnInit": function ( oSettings, nPaging, fnCallbackDraw )
                		{
                			var oLang = oSettings.oLanguage.oPaginate;
                			var oClasses = oSettings.oClasses;
                			var fnClickHandler = function ( e ) {
                				if ( oSettings.oApi._fnPageChange( oSettings, e.data.action ) )
                				{
                					fnCallbackDraw( oSettings );
                				}
                			};
                			var sAppend = '<ul class="pagination">'+
                				'<li class="prev"><a href="#" class="'+oSettings.oClasses.sPagePrevDisabled+'" tabindex="'+oSettings.iTabIndex+'" role="button"><span class="glyphicon glyphicon-chevron-left"></span>&nbsp;'+oLang.sPrevious+'</a></li>'+
                				'<li class="next"><a href="#" class="'+oSettings.oClasses.sPageNextDisabled+'" tabindex="'+oSettings.iTabIndex+'" role="button">'+oLang.sNext+'&nbsp;<span class="glyphicon glyphicon-chevron-right"></span></a></li>'+
                				'</ul>';
                			$(nPaging).append( sAppend );
                			var els = $('a', nPaging);
                			var nPrevious = els[0],
                				nNext = els[1];
                			oSettings.oApi._fnBindAction( nPrevious, {action: "previous"}, fnClickHandler );
                			oSettings.oApi._fnBindAction( nNext,     {action: "next"},     fnClickHandler );
                			if ( !oSettings.aanFeatures.p )
                			{
                				nPaging.id = oSettings.sTableId+'_paginate';
                				nPrevious.id = oSettings.sTableId+'_previous';
                				nNext.id = oSettings.sTableId+'_next';
                				nPrevious.setAttribute('aria-controls', oSettings.sTableId);
                				nNext.setAttribute('aria-controls', oSettings.sTableId);
                			}
                		},
                		"fnUpdate": function ( oSettings, fnCallbackDraw )
                		{
                			if ( !oSettings.aanFeatures.p )
                			{
                				return;
                			}
                			var oPaging = oSettings.oInstance.fnPagingInfo();
                			var oClasses = oSettings.oClasses;
                			var an = oSettings.aanFeatures.p;
                			var nNode;
                			for ( var i=0, iLen=an.length ; i<iLen ; i++ )
                			{
                				if ( oPaging.iPage === 0 ) {
                					$('li:first', an[i]).addClass('disabled');
                				} else {
                					$('li:first', an[i]).removeClass('disabled');
                				}

                				if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                					$('li:last', an[i]).addClass('disabled');
                				} else {
                					$('li:last', an[i]).removeClass('disabled');
                				}
                			}
                		}
                	},
                	"bs_four_button": {
                		"fnInit": function ( oSettings, nPaging, fnCallbackDraw )
                			{
                				var oLang = oSettings.oLanguage.oPaginate;
                				var oClasses = oSettings.oClasses;
                				var fnClickHandler = function ( e ) {
                					e.preventDefault()
                					if ( oSettings.oApi._fnPageChange( oSettings, e.data.action ) )
                					{
                						fnCallbackDraw( oSettings );
                					}
                				};
                				$(nPaging).append(
                					'<ul class="pagination">'+
                					'<li class="disabled"><a href="#" tabindex="'+oSettings.iTabIndex+'" class="'+oClasses.sPageButton+" "+oClasses.sPageFirst+'"><span class="glyphicon glyphicon-backward"></span>&nbsp;'+oLang.sFirst+'</a></li>'+
                					'<li class="disabled"><a href="#" tabindex="'+oSettings.iTabIndex+'" class="'+oClasses.sPageButton+" "+oClasses.sPagePrevious+'"><span class="glyphicon glyphicon-chevron-left"></span>&nbsp;'+oLang.sPrevious+'</a></li>'+
                					'<li><a href="#" tabindex="'+oSettings.iTabIndex+'" class="'+oClasses.sPageButton+" "+oClasses.sPageNext+'">'+oLang.sNext+'&nbsp;<span class="glyphicon glyphicon-chevron-right"></span></a></li>'+
                					'<li><a href="#" tabindex="'+oSettings.iTabIndex+'" class="'+oClasses.sPageButton+" "+oClasses.sPageLast+'">'+oLang.sLast+'&nbsp;<span class="glyphicon glyphicon-forward"></span></a></li>'+
                					'</ul>'
                				);
                				var els = $('a', nPaging);
                				var nFirst = els[0],
                					nPrev = els[1],
                					nNext = els[2],
                					nLast = els[3];
                				oSettings.oApi._fnBindAction( nFirst, {action: "first"},    fnClickHandler );
                				oSettings.oApi._fnBindAction( nPrev,  {action: "previous"}, fnClickHandler );
                				oSettings.oApi._fnBindAction( nNext,  {action: "next"},     fnClickHandler );
                				oSettings.oApi._fnBindAction( nLast,  {action: "last"},     fnClickHandler );
                				if ( !oSettings.aanFeatures.p )
                				{
                					nPaging.id = oSettings.sTableId+'_paginate';
                					nFirst.id =oSettings.sTableId+'_first';
                					nPrev.id =oSettings.sTableId+'_previous';
                					nNext.id =oSettings.sTableId+'_next';
                					nLast.id =oSettings.sTableId+'_last';
                				}
                			},
                		"fnUpdate": function ( oSettings, fnCallbackDraw )
                			{
                				if ( !oSettings.aanFeatures.p )
                				{
                					return;
                				}
                				var oPaging = oSettings.oInstance.fnPagingInfo();
                				var oClasses = oSettings.oClasses;
                				var an = oSettings.aanFeatures.p;
                				var nNode;
                				for ( var i=0, iLen=an.length ; i<iLen ; i++ )
                				{
                					if ( oPaging.iPage === 0 ) {
                						$('li:eq(0)', an[i]).addClass('disabled');
                						$('li:eq(1)', an[i]).addClass('disabled');
                					} else {
                						$('li:eq(0)', an[i]).removeClass('disabled');
                						$('li:eq(1)', an[i]).removeClass('disabled');
                					}

                					if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                						$('li:eq(2)', an[i]).addClass('disabled');
                						$('li:eq(3)', an[i]).addClass('disabled');
                					} else {
                						$('li:eq(2)', an[i]).removeClass('disabled');
                						$('li:eq(3)', an[i]).removeClass('disabled');
                					}
                				}
                			}
                	},
                	"bs_full": {
                		"fnInit": function ( oSettings, nPaging, fnCallbackDraw )
                			{
                				var oLang = oSettings.oLanguage.oPaginate;
                				var oClasses = oSettings.oClasses;
                				var fnClickHandler = function ( e ) {
                					if ( oSettings.oApi._fnPageChange( oSettings, e.data.action ) )
                					{
                						fnCallbackDraw( oSettings );
                					}
                				};
                				$(nPaging).append(
                					'<ul class="pagination">'+
                					'<li class="disabled"><a href="#" tabindex="'+oSettings.iTabIndex+'" class="'+oClasses.sPageButton+" "+oClasses.sPageFirst+'">'+oLang.sFirst+'</a></li>'+
                					'<li class="disabled"><a href="#" tabindex="'+oSettings.iTabIndex+'" class="'+oClasses.sPageButton+" "+oClasses.sPagePrevious+'">'+oLang.sPrevious+'</a></li>'+
                					'<li><a href="#" tabindex="'+oSettings.iTabIndex+'" class="'+oClasses.sPageButton+" "+oClasses.sPageNext+'">'+oLang.sNext+'</a></li>'+
                					'<li><a href="#" tabindex="'+oSettings.iTabIndex+'" class="'+oClasses.sPageButton+" "+oClasses.sPageLast+'">'+oLang.sLast+'</a></li>'+
                					'</ul>'
                				);
                				var els = $('a', nPaging);
                				var nFirst = els[0],
                					nPrev = els[1],
                					nNext = els[2],
                					nLast = els[3];
                				oSettings.oApi._fnBindAction( nFirst, {action: "first"},    fnClickHandler );
                				oSettings.oApi._fnBindAction( nPrev,  {action: "previous"}, fnClickHandler );
                				oSettings.oApi._fnBindAction( nNext,  {action: "next"},     fnClickHandler );
                				oSettings.oApi._fnBindAction( nLast,  {action: "last"},     fnClickHandler );
                				if ( !oSettings.aanFeatures.p )
                				{
                					nPaging.id = oSettings.sTableId+'_paginate';
                					nFirst.id =oSettings.sTableId+'_first';
                					nPrev.id =oSettings.sTableId+'_previous';
                					nNext.id =oSettings.sTableId+'_next';
                					nLast.id =oSettings.sTableId+'_last';
                				}
                			},
                		"fnUpdate": function ( oSettings, fnCallbackDraw )
                			{
                				if ( !oSettings.aanFeatures.p )
                				{
                					return;
                				}
                				var oPaging = oSettings.oInstance.fnPagingInfo();
                				var iPageCount = $.fn.dataTableExt.oPagination.iFullNumbersShowPages;
                				var iPageCountHalf = Math.floor(iPageCount / 2);
                				var iPages = Math.ceil((oSettings.fnRecordsDisplay()) / oSettings._iDisplayLength);
                				var iCurrentPage = Math.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength) + 1;
                				var sList = "";
                				var iStartButton, iEndButton, i, iLen;
                				var oClasses = oSettings.oClasses;
                				var anButtons, anStatic, nPaginateList, nNode;
                				var an = oSettings.aanFeatures.p;
                				var fnBind = function (j) {
                					oSettings.oApi._fnBindAction( this, {"page": j+iStartButton-1}, function(e) {
                						if ( oSettings.oApi._fnPageChange( oSettings, e.data.page ) ) {
                							fnCallbackDraw( oSettings );
                						}
                						e.preventDefault();
                					} );
                				};
                				if ( oSettings._iDisplayLength === -1 )
                				{
                					iStartButton = 1;
                					iEndButton = 1;
                					iCurrentPage = 1;
                				}
                				else if (iPages < iPageCount)
                				{
                					iStartButton = 1;
                					iEndButton = iPages;
                				}
                				else if (iCurrentPage <= iPageCountHalf)
                				{
                					iStartButton = 1;
                					iEndButton = iPageCount;
                				}
                				else if (iCurrentPage >= (iPages - iPageCountHalf))
                				{
                					iStartButton = iPages - iPageCount + 1;
                					iEndButton = iPages;
                				}
                				else
                				{
                					iStartButton = iCurrentPage - Math.ceil(iPageCount / 2) + 1;
                					iEndButton = iStartButton + iPageCount - 1;
                				}
                				for ( i=iStartButton ; i<=iEndButton ; i++ )
                				{
                					sList += (iCurrentPage !== i) ?
                						'<li><a tabindex="'+oSettings.iTabIndex+'">'+oSettings.fnFormatNumber(i)+'</a></li>' :
                						'<li class="active"><a tabindex="'+oSettings.iTabIndex+'">'+oSettings.fnFormatNumber(i)+'</a></li>';
                				}
                				for ( i=0, iLen=an.length ; i<iLen ; i++ )
                				{
                					nNode = an[i];
                					if ( !nNode.hasChildNodes() )
                					{
                						continue;
                					}
                					$('li:gt(1)', an[i]).filter(':not(li:eq(-2))').filter(':not(li:eq(-1))').remove();
                					if ( oPaging.iPage === 0 ) {
                						$('li:eq(0)', an[i]).addClass('disabled');
                						$('li:eq(1)', an[i]).addClass('disabled');
                					} else {
                						$('li:eq(0)', an[i]).removeClass('disabled');
                						$('li:eq(1)', an[i]).removeClass('disabled');
                					}
                					if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                						$('li:eq(-1)', an[i]).addClass('disabled');
                						$('li:eq(-2)', an[i]).addClass('disabled');
                					} else {
                						$('li:eq(-1)', an[i]).removeClass('disabled');
                						$('li:eq(-2)', an[i]).removeClass('disabled');
                					}
                					$(sList)
                						.insertBefore('li:eq(-2)', an[i])
                						.bind('click', function (e) {
                							e.preventDefault();
                							if ( oSettings.oApi._fnPageChange(oSettings, parseInt($('a', this).text(),10)-1) ) {
                								fnCallbackDraw( oSettings );
                							}
                						});
                				}
                			}
                	}
                } );


                /*
                 * TableTools Bootstrap compatibility
                 * Required TableTools 2.1+
                 */
                if ( $.fn.DataTable.TableTools ) {
                	// Set the classes that TableTools uses to something suitable for Bootstrap
                	$.extend( true, $.fn.DataTable.TableTools.classes, {
                		"container": "DTTT btn-group",
                		"buttons": {
                			"normal": "btn",
                			"disabled": "disabled"
                		},
                		"collection": {
                			"container": "DTTT_dropdown dropdown-menu",
                			"buttons": {
                				"normal": "",
                				"disabled": "disabled"
                			}
                		},
                		"print": {
                			"info": "DTTT_print_info modal"
                		},
                		"select": {
                			"row": "active"
                		}
                	} );

                	// Have the collection use a bootstrap compatible dropdown
                	$.extend( true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
                		"collection": {
                			"container": "ul",
                			"button": "li",
                			"liner": "a"
                		}
                	} );
                }</script>


 		<script type="text/javascript">
 		$(document).ready(function() {
 			$('.datatable').dataTable({
 				"sPaginationType": "bs_full"
 			});
 			$('.datatable').each(function(){
 				var datatable = $(this);
 				// SEARCH - Add the placeholder for Search and Turn this into in-line form control
 				var search_input = datatable.closest('.dataTables_wrapper').find('div[id$=_filter] input');
 				search_input.attr('placeholder', 'Search');
 				search_input.addClass('form-control input-sm');
 				// LENGTH - Inline-Form control
 				var length_sel = datatable.closest('.dataTables_wrapper').find('div[id$=_length] select');
 				length_sel.addClass('form-control input-sm');
 			});
 		});


function toggle(layer) {
    var d = document.getElementById(layer);
    d.style.display = (d.style.display == 'none') ? '' : 'none';
    }


   var modal = document.getElementById('id01');

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }


 var modal = document.getElementById('id02');

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }

         var modal = document.getElementById('id03');

                window.onclick = function(event) {
                    if (event.target == modal) {
                        modal.style.display = "none";
                    }
                }
                 var modal = document.getElementById('id04');

                        window.onclick = function(event) {
                            if (event.target == modal) {
                                modal.style.display = "none";
                            }
                        }
                        var modal = document.getElementById('id05');

                                                window.onclick = function(event) {
                                                    if (event.target == modal) {
                                                        modal.style.display = "none";
                                                    }
                                                }


		</script>
</security:authorize>
</body>
</html>