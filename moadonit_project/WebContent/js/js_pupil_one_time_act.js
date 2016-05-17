var currentDate;

$(function() {
		$('#detailsLink').attr('href','pupil_card_view.jsp?li=0&pupil=' + pupilID);
		$('#scheduleLink').attr('href','pupil_week_view.jsp?li=1&pupil=' + pupilID);
		$('#regLink').attr('href','pupil_week_view.jsp?li=2&pupil=' + pupilID);
		$('#oneTimeLink').attr('href', 'pupil_one_time_act.jsp?li=3&pupil=' + pupilID);
	
		$("#saveBtn").click(function() {
			if($("#datePick").val() != "" && $("#type").val() != ""){
				var msg = "התלמיד/ה  "+pupilData.firstName + " " + pupilData.lastName;
				
				msg+= " ת/ירשם באופן חד-פעמי לתאריך "+ $("#datePick").val();
				bootbox.confirm(msg, function(result) {
					if (result === true)  
						result = saveOneTimeAct(pupilID);			
						if(result === true){
							loadhistoryRegsGrid(pupilID); 
					}
					                            
					   
				});
					
				}
//			else
//				tell the user to enter date
				
			
			return false;
		});
		
		loadPupilOneAct(dataString);			
		loadhistoryRegsGrid(pupilID); 
		
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
});

function loadPupilOneAct(dataString) {
	$.ajax({
  		async: false,
		type: 'GET',
		datatype: 'jsonp',
        url: "FullPupilCardController",
        data: dataString,
        success: function(data) {
        	if(data != undefined){
        		pupilID = data.pupilNum;
        		pupilData = data;
        		$('.page-header').html(pupilData.firstName + " " + pupilData.lastName);
        		
        	}
        	else
        		console.log("לא קיימים נתונים");
        },
        error: function(e) {
        	console.log("error");
			
        }
        
      }); 
	  }



function loadhistoryRegsGrid(pupilID) {

	var regoptions = {	value : "1:לא רשום;2:מועדונית;3:אוכל בלבד"	},oldDateVal = new Date(), lastSelection = -1, grid =$("#listRegistration"),
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
                    regDate : d.getTime(), /* Shir variable name changed*/
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
				url : "PupilRegistration?action=getOneTimeReg&pupilID=" /*Shir - action*/
						+ pupilID,
				datatype : "json",
				mtype : 'POST',
				editurl : "PupilRegistration?action=oneTimeEdit&pupilID="+ pupilID,  /*Shir */
				colNames : [ 'תאריך', 'סוג רישום', 'פעולה'],					
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
							if (d.getTime() < currentDate.getTime()) {
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
								"style" : "background:#9E9F9F;",
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
							name : "regDate",
							index : 'regDate',
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
							
							name : 'regType',
							index : 'regType',
							edittype : "select",
							editable : true,							
							editoptions : regoptions
							
							
						},
						{
							
							name : 'actions', 
							index: 'actions', 
							formatter:'actions', 
							align: "center",	
							sortable:false,
							formatter:'actions',						
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
				sortname : 'regDate',
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
	parameters = {    /* Shir  maybe change to fa-fa icons?*/
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


function saveOneTimeAct(pupilID){
	
	var oneTime = new Object();
	var oneTimePK = new Object();
	oneTimePK.pupilNum = pupilID;
	oneTimePK.specificDate = $("#datePick").val();
	oneTime.id = oneTimePK;
	oneTime.regType = oneTimePK;
	
	$.ajax({
	  		async: false,
			type: 'POST',
			datatype: 'jsonp',
	        url: "PupilRegistration",
	        
	        data: { 
	        	action : "saveOneTime",
	        	pupilID : pupilID,
	        	oneTimeReg : JSON.stringify(oneTime)
	        	  },
	        	
	        success: function(data) {
	        	if(data != undefined){
	        		result = true;
        			pupilID = data.result;
        				
        		};
	        },
	        error: function(e) {
	        	result = false;
	        	console.log(e);
			        	bootbox.alert("שגיאה בשמירת הנתונים, נא בדוק את הערכים ונסה שוב.", function() {	   	        		 
			        	});			
	        }
	        
	      }); 
	
}