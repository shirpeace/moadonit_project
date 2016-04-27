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
				},
				rowattr : function(rd) {

					if (rd.startDate && currentDate) {
						var d = new Date(rd.startDate);
						if (d.getTime() > currentDate.getTime()) { // future
																	// registration
							return {
								/*"class" : 'not-editable-row',*/
								"style" : "background:#7697B7;"
							};
						} else if (d.getTime() === currentDate.getTime()) { // current
																			// registration
							return {
								"style" : "background:#8BB5F2;"
							};
						} else { // history registration
						return {
								"class" : 'not-editable-row',
								"style" : "background:#9E9F9F;"
							};
						}

					}
				},
				loadError : function(xhr, status, error) {
					alert("complete loadError");
				},

				ondblClickRow : function(id) {
					if (id && id !== lastSelection) {
						var grid = $("#listRegistration");
						grid.jqGrid('restoreRow', lastSelection);
						grid.jqGrid('editRow', id, {keys : true});
						lastSelection = id;
					}
				},
				colModel : [
						{
							name : "startDate",
							index : 'startDate',
							sorttype : "date",
							editable : true,
							// formatter:'date', formatoptions: {srcformat: 'U',
							// newformat:'dd/mm/yyyy'}/*,
							formatter : function(cellValue, opts, rwd) {
								if (cellValue) {
									return $.fn.fmatter.call(this, "date",
											new Date(cellValue), opts, rwd);
								} else {
									return '';
								}
							}
						}, {
							
							name : 'sunday',
							index : 'sunday',
							/*edittype : "select",
							formatter : 'select',
							editoptions : regoptions*/
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
							
						},{name : 'actions', index: 'actions', formatter:'actions',
						    formatoptions: {
						        keys: true,
						        editbutton: false,
						        delOptions: { url: "PupilRegistration?action=getRegistration&pupilID="+ pupilID }
						        }},
						        ],
				pager : '#listRegistrationPager',
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
				 * jQuery(this).getRowData(rowId); var pupilID = rowData.id;
				 * window.location.href =
				 * "pupil_card_view.jsp?pupil="+pupilID+""; },
				 */
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