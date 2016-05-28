/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/** ********************************************** */

$.extend($.jgrid.ajaxOptions, {
	async : false
});
/*$.jgrid.defaults.width = 780;*/

var currentDate;
var regoptions = {	value : ""	};
var selectOptionsData;
var COurseTime = [];
/** ********************************************** */
// TODO //* START PAGE FUNCTIONS */
/** ********************************************** */

/**
 * on select of change 
 */
function ondayChange(selectElem){
	$("#checkAllDays").prop('checked', false);
	
}

/**
 * on checkbox of all week change
 * @param checkElem
 */
function checkChange(checkElem){
	
	 if(checkElem.checked) {
	        //Do stuff
		 var selects = $("div #regDays select");
		 $(selects).each(function() {
			  $( this ).val( $( this ).find("option:eq(1)").val());
			  console.log($( this ).find('option:selected'));
			  //$('select[name=name]').find('option:eq(1)').attr('selected', 'selected');
		 });
		// $("#target").val($("#target option:first").val());
	 }
}

function fillRegSelectOptions(){
	var selects = $("div #regDays select");
	$(selects).each(function() {	//iterate over the select elements	 
		var select = this; //get reference to the current select
		$(select).find('option').remove();
		$.each(selectOptionsData, function(key, value) {  // iterate over the values from server that we fetched earlier
			var  name, obj = value;
	        for (name in obj) { // iterate the keys of the object that represent the option data and get the key and value
	            if (obj.hasOwnProperty(name)) { // if the key is direct key of object , not inherited 
	            	// build an option and add it to select
	            	$(select).append($("<option></option>")
		                    .attr("value",name)
		                    .text(obj[name])); 
	            }
	        }  
		});	
	});
}
function setPupilCardData(pupil) {

	if (pupil != undefined) {

		$('.page-header').html(pupil.firstName + " " + pupil.lastName);
	}
}

function loadPupilCard(dataString) {

	$.ajax({
		async : false,
		type : 'GET',
		datatype : 'jsonp',
		url : "FullPupilCardController",
		data : dataString,
		success : function(data) {
			if (data != undefined) {
				pupilID = data.pupilNum;
				pupilData = data;
				setPupilCardData(pupilData);

			} else
				alert("לא קיימים נתונים");
		},
		error : function(e) {
			alert("שגיאה בשליפת נתונים");
			console.log("error");

		}

	});

}

function loadWeekGrid(pupilID) {
	var grid = jQuery("#list");
	var idx = 0;
	grid
			.jqGrid(
					{
						url : "PupilRegistration?action=getWeekGrid&pupilID="
								+ pupilID,
						datatype : "json",
						mtype : 'POST',
						colNames : [ '','', '', 'יום ראשון', 'יום שני',
								'יום שלישי', 'יום רביעי', 'יום חמישי' ],
						ajaxGridOptions : {
							async : false
						},
						rowattr : function(rd) {
							if(idx == 0){
								idx++;
								//first row of grid
								return {
									/*"class" : 'not-editable-row',*/
									"style" : "color:"+colors.presernt+";","data-isHistory": false
								};
							}
							
//							style="background:'+colors.presernt+'"
							/*if (rd.startDate) {
								currentDate = new Date(rd.startDate);
							}*/
						},
						loadComplete : function(data) {
							
							if (parseInt(data.records, 10) == 0) {
								currentDate = new Date(); //default date is today if there are no rows in this grid 
								$("#pager div.ui-paging-info").show();
							} else {
								currentDate = new Date(data.rows[0].startDate); 
								$("#pager div.ui-paging-info").hide();
							}
						},
						loadError : function(xhr, status, error) {
							/*alert("complete loadError");*/
						},
						colModel : [
								{
									name : 'title',
									index : 'title',
									hidden : true,
								},
								{
									name : 'type',
									index : 'type',
									title:false
								},
								{
									name : "startDate",
									index : 'startDate',
									sorttype : "date",
									hidden : true,
									// formatter:'date', formatoptions:
									// {srcformat: 'U',
									// newformat:'dd/mm/yyyy'}/*,
									formatter : function(cellValue, opts, rwd) {
										if (cellValue) {
											return $.fn.fmatter.call(this,
													"date",
													new Date(cellValue), opts,
													rwd);
										} else {
											return '';
										}
									}
								}, {
									name : 'sunday',
									index : 'sunday',
									cellattr: formatCell,
									title:false
								}, {
									name : 'monday',
									index : 'monday',
									cellattr: formatCell,
									title:false

								}, {
									name : 'tuesday',
									index : 'tuesday',
									cellattr: formatCell,
									title:false

								}, {
									name : 'wednesday',
									index : 'wednesday',
									cellattr: formatCell,
									title:false
								}, {
									name : 'thursday',
									index : 'thursday',
									cellattr: formatCell,
									title:false
								
								} ],
						pager : '#pager',
						autowidth : true,
						shrinkToFit : true,
						rowNum : 30,
						rowList : [],
						sortname : 'sunday',
						/* scroll: true, */
						direction : "rtl",
						viewrecords : true,
						gridview : true,
						height : "100%",
						width : "100%",
						loadui:"block",
						/*
						 * ondblClickRow: function(rowId) { var rowData =
						 * jQuery(this).getRowData(rowId); var pupilID =
						 * rowData.id; window.location.href =
						 * "pupil_card_view.jsp?pupil="+pupilID+""; },
						 */
						jsonReader : {
							repeatitems : false,
						},
						/* editurl : "FullPupilCardController", */
						recreateFilter : true,
						pgbuttons : false, // disable page control like next,
						// back button
						pgtext : null, // disable pager text like 'Page 0 of
						// 10'
						viewrecords : true

					});
	jQuery("#list").jqGrid('navGrid', '#pager', {
		edit : false,
		add : false,
		del : false,
		search : false,
		refresh : false
	});

	/*
	 * jQuery("#list").jqGrid('filterToolbar',{autosearch:true, stringResult:
	 * true});
	 */

}



function formatCell (rowId, val, rawObject, cm, rdata) {
	
	var arr ;
	var titleObj = null;
	//arr.push(cm.index);
	titleObj = rawObject.title.split(";");
	if (titleObj[0].length > 1 && val != "&#160;" && rawObject.title != "" ) {
		
		for (var int = 0; int < titleObj.length; int++) {
			arr = titleObj[int].split(",");
			if(arr[3] == cm.index){
				var title = 'title="' + val;
				var dd = new Intl.DateTimeFormat("he-IL").format(new Date(arr[2]));
				title += '\nשעות: ' + arr[0] + ' - ' +arr[1] + "\nתאריך התחלה: " + dd.replaceAll(".","/");
			}
			
			// var row = $('#list > tbody > tr')
		}
		
		// var cell = row.cells[cell_index];
		/*if(rowId != 1){
			for (var int = 0; int < COurseTime.length; int++) {
				for (var j = 0; j < COurseTime[int].length; j++) {
					if (COurseTime[int][3] == cm.index) {
						title += ' class="ui-state-error-text ui-state-error"';
					}
				}
			}
		}*/
		
		COurseTime.push(arr);
		
		return title += '"';
	}

	else 
		return null;
}
function centerForm ($form, grid) {
    $form.closest('div.ui-jqdialog').position({
        my: "center",
        of: grid.closest('div.ui-jqgrid')
    });
}

function setRegoptions(){
	
	var temp = selectOptionsData; //get data from server and build the html
	var s = '';
	for (var int = 0; int < temp.length; int++) {
		var  name, obj = temp[int];
        for (name in obj) {
            if (obj.hasOwnProperty(name)) {
            	s += name + ':' + obj[name] + ';';
            }
        }        
	}
	s = s.substring(0, s.lastIndexOf(";"));
	regoptions.value = s;
	
}
function loadRegistrationGrid(pupilID) {
	setRegoptions();
	var oldDateVal = new Date(), lastSelection = -1, grid =$("#listRegistration"),	
	myDelOptions = {
			 rowData : {},
			
			 onClose : function (rowID, response) {
				 
		        },
			 errorTextFormat: function (response) {
				 
				 	$(this).restoreAfterErorr = false;
				 	var overlay = $('body > div.ui-widget-overlay'); //.is(":visible");
					 if (overlay.is(":visible")) {
						 overlay.remove();	
						 $('#delmodlistRegistration').remove();
						 
					}
				 	bootbox.alert("שגיאה במחיקת רשומה. נא נסה שוב.",
							function() {
					});
				 				
		            return true;
		           
			       
			    },
			   afterSubmit: function (response, postdata) {
				   response = $.parseJSON(response.responseText);
				   // delete row
	                grid.delRowData(rowData.id);
	                bootbox.alert("רשומה נמחקה בהצלחה",
							function() {
					});
			        
				   console.log(response);
			        return [true, "success"];
			    },
			    afterComplete: function (response, postdata, formid) {
			    	response = $.parseJSON(response.responseText);
			       
			    },
		  	serializeDelData: function(postdata) { 
				return { rtm : JSON.stringify(createPostData(pupilID, rowData,false))  } ;
	        },
	       
            onclickSubmit: function(rp_ge, rowid) {
                // we can use onclickSubmit function as "onclick" on "Delete" button
               
                rowData = $(this).jqGrid("getRowData", rowid);
                $.extend(rowData, {id: rowid});
                var d = getDateFromValue( rowData.startDate);
                rp_ge.url = "PupilRegistration?" + $.param({
                	action: "delete",
                	pupilID: pupilID,
                    startDate : d.getTime(),
                });
               /* // delete row
                grid.delRowData(rowid);*/
               
                $("#delmod"+grid[0].id).hide();

               /* if (grid[0].p.lastpage > 1) {
                    // reload grid to make the row from the next page visable.
                    // TODO: deleting the last row from the last page which number is higher as 1
                    grid.trigger("reloadGrid", [{page:grid[0].p.page}]);
                }*/

                return {}; // you can return additional data which will be sent to the server
                //return true;
            },            
        };

	$("#listRegistration").jqGrid(
			{
				url : "PupilRegistration?action=getRegistration&pupilID="
						+ pupilID,
				datatype : "json",
				mtype : 'POST',
				editurl : "PupilRegistration?action=edit&pupilID="+ pupilID,
				colNames : [ 'תאריך התחלה', 'יום ראשון', 'יום שני',
						'יום שלישי', 'יום רביעי', 'יום חמישי' ,'פעולה'],					
				loadComplete : function(data) {
					if (parseInt(data.records, 10) == 0) {
						$("#listRegistrationPager div.ui-paging-info").show();
					} else {
						//$("#listRegistrationPager div.ui-paging-info").hide();
					}
							
					/* START hide edit/delete buttons for history records */
					var ids = $("#listRegistration").jqGrid('getDataIDs');
					for (var i = 0; i < ids.length; i++) 
					{
					    var rowId = ids[i];
					    var rowData = $('#listRegistration').jqGrid ('getRowData', rowId);

					    var d = getDateFromValue(rowData.startDate);
						if (d && currentDate) {
							if (d.getTime() <= currentDate.getTime()) { 
								$('#jEditButton_'+rowId).hide();
								$('#jDeleteButton_'+rowId).hide();
							}
						}
					}
					/*  END edit/delete buttons for history records */
				},
				rowattr : function(rd) {

					// set the style and editable of the row 
					if (rd.startDate && currentDate) {
						var d = new Date(rd.startDate);
						if (d.getTime() > currentDate.getTime()) { // future
																	// registration
							return {
								/*"class" : 'not-editable-row',*/
								"style" : "background:"+colors.future+";","data-isHistory": false
							};
						} else if (d.getTime() === currentDate.getTime()) { // current
																			// registration
							return {
								"style" : "color:"+colors.presernt+";","data-isHistory": true
							};
						} else { // history registration
					
							return {
								"class" : 'not-editable-row',
								/*"style" : "background:#9E9F9F;",*/
								"data-isHistory": true
							};
						}

					}
				},
				loadError : function(xhr,st,err) {
					alert("Type: "+st+"; Response: "+ xhr.status + " "+xhr.statusText);
			    },				
				serializeRowData: function(postdata) { 
					return { rtm : JSON.stringify(createPostData(pupilID, postdata,true)), _oldDateVal: oldDateVal.getTime()  } ;
		        },
		      
				onSelectRow: function(id) { 
					if (id && id !== lastSelection) {
						var grid = $("#listRegistration");
						grid.jqGrid('restoreRow', lastSelection);						
						var islastSelectHistory = $('#gview_listRegistration div #'+ lastSelection).attr('data-isHistory');
						
						if (islastSelectHistory === 'false') {
							$('#jEditButton_'+lastSelection).show();
							$('#jDeleteButton_'+lastSelection).show();
							$('#jSaveButton_'+lastSelection).hide();
							$('#jCancelButton_'+lastSelection).hide();
						}
											
						lastSelection = id;
					}
		        },
				colModel : [				           
						{
							name : "startDate",
							index : 'startDate',
							sorttype : "date",
							editable : true,
							editoptions: {
		                            size: 20,
		                            dataInit: function (el) {
		                                $(el).datepicker({
		                				    format: "dd/mm/yyyy",
		                				    language: "he" ,
		                				    startDate: "today",
		                				    maxViewMode: 0,
		                				    minViewMode: 0,
		                				    todayBtn: true,
		                				    keyboardNavigation: false,
		                				    daysOfWeekDisabled: "5,6",
		                				    todayHighlight: true,
		                				    toggleActive: true 
		                				}); 
		                            },
		                            defaultValue: function () {
		                                var currentTime = new Date();
		                                var month = parseInt(currentTime.getMonth() + 1);
		                                month = month <= 9 ? "0" + month : month;
		                                var day = currentTime.getDate();
		                                day = day <= 9 ? "0" + day : day;
		                                var year = currentTime.getFullYear();
		                                return day + "/" + month + "/" + year;
		                            }
							 },
	
							formatter : function (cellValue, opts, rwd) {								
								if (cellValue) {
									 getDateFromValue(cellValue);
									
									var d = $.fn.fmatter.call(this, "date",
											getDateFromValue(cellValue), opts, rwd);
									return d;
								} else {
									return '';
								}
							}
				            , formatoptions: { newformat: "d/m/Y" },
						}, {
							
							name : 'sunday',
							index : 'sunday',
							edittype : "select",
							editable : true,							
							editoptions :regoptions
							
							
						}, {
							
							name : 'monday',
							index : 'monday',
							edittype : "select",
							editable : true,
							editoptions :regoptions

						}, {
							
							name : 'tuesday',
							index : 'tuesday',
							edittype : "select",
							editable : true,
							editoptions :regoptions

						}, {
							
							name : 'wednesday',
							index : 'wednesday',
							edittype : "select",
							editable : true,
							editoptions :regoptions
						}, {
							
							name : 'thursday',
							index : 'thursday',
							edittype : "select",
							editable : true,
							editoptions :regoptions
							
						},
						{name : 'actions', index: 'actions', formatter:'actions', align: "center",	sortable:false,formatter:'actions',						
						    formatoptions: {
						        keys: true,
						        editbutton: true,
						        onEdit:function(rowid) {
		                            //do somethinf if you need on edit button click
						        	
						        	// get html content of cell
						        	var cellContent = $(this).getCell(rowid,'startDate'); 
						        	// convert it to a datePicker and get value of the cell
						        	var oldDate = $( '#'+$(cellContent).attr('id') ).datepicker( "getDate" );
						        	oldDateVal = oldDate;
		                         },								
		                         onSuccess:function(jqXHR) {
		                             // the function will be used as "succesfunc" parameter of editRow function
		                             // (see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:inline_editing#editrow)
		                        	
		                             // we can verify the server response and interpret it do as an error
		                             // in the case we should return false. In the case onError will be called
		                             return true;
		                         },
		                         onError:function(rowid, jqXHR, textStatus) {
		                             // the function will be used as "errorfunc" parameter of editRow function
		                             // (see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:inline_editing#editrow)
		                             // and saveRow function
		                             // (see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:inline_editing#saverow)
		                        	 bootbox.alert("שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.",
		                 					function() {
		                 					});
		                            /* alert("in onError used only for remote editing:"+
		                                   "\nresponseText="+jqXHR.responseText+
		                                   "\nstatus="+jqXHR.status+
		                                   "\nstatusText"+jqXHR.statusText+
		                                   "\n\nWe don't need return anything");*/
		                         },
		                         afterSave:function(rowid) {
		                             /*alert("in afterSave (Submit): rowid="+rowid+"\nWe don't need return anything");*/
		                         },
		                         afterRestore:function(rowid) {
		                             
		                         },		                         
						        delOptions: myDelOptions /*{ url: "PupilRegistration?action=delete&pupilID="+ pupilID }*/
						        }},
						        ],
				pager : '#listRegistrationPager',
				autowidth : true,
				shrinkToFit : true,
				rowNum : 30,
				rowList : [],
				sortname : 'sunday',
				direction : "rtl",
				viewrecords : true,
				gridview : true,
				height : "100%",
				width : "100%",
				
				jsonReader : {
					repeatitems : false,
				},
				/* editurl : "FullPupilCardController", */
				recreateFilter : true,
				pgbuttons : false, // disable page control like next, back
				// button
				pgtext : null, // disable pager text like 'Page 0 of 10'
				viewrecords : true

			});
	parameters = {
		edit : true,
		editicon : "ui-icon-pencil",
		add : true,
		addicon : "ui-icon-plus",
		save : true,
		saveicon : "ui-icon-disk",
		cancel : true,
		cancelicon : "ui-icon-cancel",
		addParams : {
			useFormatter : false
		},
		editParams : {}
	}, jQuery("#listRegistration").jqGrid('navGrid', '#listRegistrationPager',
			{
				edit : false,
				add : false,
				del : false,
				search : false,
				refresh : false
			});

	/*
	 * jQuery("#list").jqGrid('filterToolbar',{autosearch:true, stringResult:
	 * true});
	 */

}

/**
 * create the data to be sent to edit function on server
 * @param pupilID
 * @param dateVal 
 * @param rowData from grid edit row
 * @returns { json objectc } - regToMoadonit data
 */
function createPostData(pupilID, rowData,isEdit){
	
	var rtm = new Object();
	
	var mydate = getDateFromValue( rowData.startDate);
	rtm.id = {
		startDate : mydate,
		pupilNum : pupilID
	}; // pk
	if (isEdit) {
			rtm.tblRegType1 = {
				typeNum : rowData.sunday
			};
			rtm.tblRegType2 = {
				typeNum : rowData.monday
			};
			rtm.tblRegType3 = {
				typeNum : rowData.tuesday
			};
			rtm.tblRegType4 = {
				typeNum :rowData.wednesday
			};
			rtm.tblRegType5 = {
				typeNum : rowData.thursday
			};
	}
	
	rtm.registerDate = new Date();
	/* rtm.writenBy = currentUserId; */
	rtm.tblRegSource = {
		sourceNum : $('#reason').val()
	};

	return rtm;
}


function saveRegistraion() {

	var rtm = new Object();
	var parts = $('#datePick').val().split('/');
	var mydate = new Date(parts[2], parts[1] - 1, parts[0]);

	rtm.id = {
		startDate : mydate,
		pupilNum : pupilID
	}; // pk
	rtm.tblRegType1 = {
		typeNum : $('#sunday').val()
	};
	rtm.tblRegType2 = {
		typeNum : $('#monday').val()
	};
	rtm.tblRegType3 = {
		typeNum : $('#tuesday').val()
	};
	rtm.tblRegType4 = {
		typeNum : $('#wednesday').val()
	};
	rtm.tblRegType5 = {
		typeNum : $('#thursday').val()
	};
	rtm.registerDate = new Date();
	/* rtm.writenBy = currentUserId; */
	rtm.tblRegSource = {
		sourceNum : $('#reason').val()
	};

	var result;

	$.ajax({
		async : false,
		type : 'POST',
		datatype : 'jsonp',
		url : "PupilRegistration",
		data : {
			action : "addRegistration",
			rtm : JSON.stringify(rtm)
		},
		success : function(data) {
			if (data != undefined) {
				/* alert(data); */
				if (data.msg == "1") {
					$("#list").trigger("reloadGrid");
					$("#listRegistration").trigger("reloadGrid");

					result = true;
				} else if (data.msg == "0") {
					result = false;
					bootbox.alert(data.result, function() {
					});
				}
			}
		},
		error : function(e) {
			result = false;
			console.log(e);
			bootbox.alert("שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.",
					function() {
					});
		}

	});

	return result;

}

function goToByScroll(id){
    // Reove "link" from the ID
  id = id.replace("link", "");
    // Scroll
  $('html,body').animate({
      scrollTop: $("#"+id).offset().top},
      'slow');
}

$(function() {

		$('#detailsLink').attr('href','pupil_card_view.jsp?li=0&pupil=' + pupilID);
		$('#scheduleLink').attr('href','pupil_week_view.jsp?li=1&pupil=' + pupilID);
		$('#regLink').attr('href','pupil_week_view.jsp?reg=1&li=2&pupil=' + pupilID);
		$('#oneTimeLink').attr('href', 'pupil_one_time_act.jsp?li=3&pupil=' + pupilID);
		
		var dataString = 'id='+ pupilID + '&action=' + "get";
		selectOptionsData = getRegTypesData(); //get data from server for the registration types select elements
		fillRegSelectOptions();
		loadPupilCard(dataString);
		
		 $('#datePick').datepicker({
			    format: "dd/mm/yyyy",
			    language: "he" ,
			     startDate: "today",
			    maxViewMode: 0,
			    minViewMode: 0,
			    todayBtn: true,
			    keyboardNavigation: false,
			    daysOfWeekDisabled: "5,6",
			    todayHighlight: true,
			    toggleActive: true 
			}); 
		 
		loadWeekGrid(pupilID);
		
		loadRegistrationGrid(pupilID); 
				
		if(selectedLi == 1)
			$('#scheduleLink').parent().addClass('active');
		else if(selectedLi == 2){
			$('#regLink').parent().addClass('active');
			
	        goToByScroll('endP');  // scroll to a elemnt endP. 
		}
		
		if (reg != undefined && reg ==1 ) {
			
			$("#editReg").slideToggle(100, function () {

		    }); 
		}
		
		$('#btnSave').click(function() {
			var result;
			var form = $("#regform");
			if (form.valid()){
				result = saveRegistraion();
				if (result) {						
					bootbox.alert("נתונים נשמרו בהצלחה", function() {			        				
		        	});
				}
			}
			
		});
		
		
		$('#regHeaderlink').click(function(e) {
			 e.preventDefault(); 
			 $("#editReg").slideToggle(100, function () {
				 goToByScroll('endP');
			    }); 
		});
		
		$('#regLink').click(function(e) {
			 e.preventDefault(); 
			 $('#regLink').parent().addClass('active');
			 $('#scheduleLink').parent().removeClass('active');
			 $("#editReg").show(100, function () {
				 goToByScroll('endP');
			    }); 
			
		});
		
		
		 /* set the validattion for form */
		var validator = $("#regform").validate({
			
			errorPlacement: function(error, element) {
				// Append error within linked label					
				error.css("color", "red");				
				$( element )
					.closest( "form" )
						.find( "label[for='" + element.attr( "id" ) + "']" )
							.append(  error );
			},
			rules: {   
				
				// set a rule to inputs
				// input must have name and id attr' and with same value !!!
				datePick : {  
					required: true
					
					}
				
			}			
		});
		
});