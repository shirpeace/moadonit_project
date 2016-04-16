/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/** ********************************************** */

/* the current user the us logged if to the system */
var currentUserId = '<%=session.getAttribute("userid")%>';

// define state for the editable page
var state = {
	EDIT : 1,
	READ : 0,
};
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
						colNames : [ '', 'יום ראשון', 'יום שני', 'יום שלישי',
								'יום רביעי', 'יום חמישי' ],
						loadComplete : function(data) {
							if (parseInt(data.records, 10) == 0) {
								$("#pager div.ui-paging-info").show();
							} else {
								$("#pager div.ui-paging-info").hide();
							}
						},
						colModel : [ {
							name : 'type',
							index : 'type',

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
	$("#listRegistration").jqGrid(
			{
				url : "PupilRegistration?action=getRegistration&pupilID="
						+ pupilID,
				datatype : "json",
				mtype : 'POST',
				colNames : [  'תאריך התחלה', 'יום ראשון', 'יום שני',
						'יום שלישי', 'יום רביעי', 'יום חמישי' ],
				loadComplete : function(data) {
					if (parseInt(data.records, 10) == 0) {
						$("#listRegistrationPager div.ui-paging-info").show();
					} else {
						$("#listRegistrationPager div.ui-paging-info").hide();
					}
				},
				colModel : [ {
					name : 'startDate',
					index : 'startDate',

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
	jQuery("#listRegistration").jqGrid('navGrid', '#listRegistrationPager', {
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
