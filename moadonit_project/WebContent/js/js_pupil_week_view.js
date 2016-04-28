/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/** ********************************************** */

$.extend($.jgrid.ajaxOptions, {
	async : false
});
var currentDate;
/** ********************************************** */
// TODO //* START PAGE FUNCTIONS */
/** ********************************************** */
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
	$("#list")
			.jqGrid(
					{
						url : "PupilRegistration?action=getWeekGrid&pupilID="
								+ pupilID,
						datatype : "json",
						mtype : 'POST',
						colNames : [ '', '', 'יום ראשון', 'יום שני',
								'יום שלישי', 'יום רביעי', 'יום חמישי' ],
						ajaxGridOptions : {
							async : false
						},
						rowattr : function(rd) {

							if (rd.startDate) {
								currentDate = new Date(rd.startDate);
							}
						},
						loadComplete : function(data) {
							if (parseInt(data.records, 10) == 0) {
								$("#pager div.ui-paging-info").show();
							} else {
								$("#pager div.ui-paging-info").hide();
							}
						},
						loadError : function(xhr, status, error) {
							alert("complete loadError");
						},
						colModel : [
								{
									name : 'type',
									index : 'type',

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

								}, {
									name : 'monday',
									index : 'monday',

								}, {
									name : 'tuesday',
									index : 'tuesday',

								}, {
									name : 'wednesday',
									index : 'wednesday',

								}, {
									name : 'thursday',
									index : 'thursday',

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


function loadRegistrationGrid(pupilID) {

	var regoptions = {
		value : "1:לא רשום;2:מועדונית;3:אוכל בלבד"
	}, lastSelection = -1;

	
	$("#listRegistration").jqGrid(
			{
				url : "PupilRegistration?action=getRegistration&pupilID="
						+ pupilID,
				datatype : "json",
				mtype : 'POST',
				editurl : "PupilRegistration?action=edit",
				colNames : [ 'תאריך התחלה', 'יום ראשון', 'יום שני',
						'יום שלישי', 'יום רביעי', 'יום חמישי' ,'X'],
				loadComplete : function(data) {
					if (parseInt(data.records, 10) == 0) {
						$("#listRegistrationPager div.ui-paging-info").show();
					} else {
						$("#listRegistrationPager div.ui-paging-info").hide();
					}
					
					var ids = $("#listRegistration").jqGrid('getDataIDs');
					for (var i = 0; i < ids.length; i++) 
					{
					    var rowId = ids[i];
					    var rowData = $('#listRegistration').jqGrid ('getRowData', rowId);

					    var d = getDateFromValue(rowData.startDate);
						if (d && currentDate) {
							if (d.getTime() < currentDate.getTime()) {
								$('#jEditButton_'+rowId).hide();
								$('#jDeleteButton_'+rowId).hide();
							}
						}
					}
					
				},
				rowattr : function(rd) {

					if (rd.startDate && currentDate) {
						var d = new Date(rd.startDate);
						if (d.getTime() > currentDate.getTime()) { // future
																	// registration
							return {
								/*"class" : 'not-editable-row',*/
								"style" : "background:#7697B7;","data-isHistory": false
							};
						} else if (d.getTime() === currentDate.getTime()) { // current
																			// registration
							return {
								"style" : "background:#8BB5F2;","data-isHistory": false
							};
						} else { // history registration
					
							return {
								"class" : 'not-editable-row',
								"style" : "background:#9E9F9F;","data-isHistory": true
							};
						}

					}
				},
				loadError : function(xhr, status, error) {
					alert("complete loadError");
				},
				onSelectRow: function(id) { 
					if (id && id !== lastSelection) {
						var grid = $("#listRegistration");
						grid.jqGrid('restoreRow', lastSelection);
						var isCurrentHistory = $('#gview_listRegistration div #'+ id).attr('data-isHistory');
						var islastSelectHistory = $('#gview_listRegistration div #'+ lastSelection).attr('data-isHistory');
						
						if (islastSelectHistory === 'false') {
							$('#jEditButton_'+lastSelection).show();
							$('#jDeleteButton_'+lastSelection).show();
							$('#jSaveButton_'+lastSelection).hide();
							$('#jCancelButton_'+lastSelection).hide();
						}
						
						//grid.jqGrid('editRow', id, {keys : true});
						lastSelection = id;
					}
		        },
				/*ondblClickRow : function(id) {
					if (id && id !== lastSelection) {
						var grid = $("#listRegistration");
						grid.jqGrid('restoreRow', lastSelection);
						grid.jqGrid('editRow', id, {keys : true});
						lastSelection = id;
					}
				},*/
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
							 /*formatter:'date', formatoptions: {srcformat: 'U',
							 newformat:'dd/mm/yyyy'},*/
							/*formatter: function (cellvalue, options, rowObject) {
								if (cellValue) {
									var d = new Date(cellValue);
									$.fn.fmatter.call(this, "date", d, options, rowObject);
								} else {
									return '';
								}
												               
				            }
							,*/
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
							editoptions : regoptions
							
							
						}, {
							
							name : 'monday',
							index : 'monday',
							edittype : "select",
							editable : true,
							editoptions : regoptions

						}, {
							
							name : 'tuesday',
							index : 'tuesday',
							edittype : "select",
							editable : true,
							editoptions : regoptions

						}, {
							
							name : 'wednesday',
							index : 'wednesday',
							edittype : "select",
							editable : true,
							editoptions : regoptions
						}, {
							
							name : 'thursday',
							index : 'thursday',
							edittype : "select",
							editable : true,
							editoptions : regoptions
							
						},
						{name : 'actions', index: 'actions', formatter:'actions', align: "center",							
						    formatoptions: {
						        keys: true,
						        editbutton: true,
						        delOptions: { url: "PupilRegistration?action=delete&pupilID="+ pupilID }
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

function getDateFromValue(value){
	if (typeof value === 'string') {
		var arr = value.split("/");
		var d = new Date(arr[2], arr[1] - 1, arr[0]);
		return d;
	}else if(typeof value === 'number'){
		var d = new Date(value);
		return d;
	}
	
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