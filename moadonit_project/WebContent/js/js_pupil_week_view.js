
/*************************************************/
//TODO //* START Global PARMS and FUNCTIONS */
/*************************************************/

/*   the current user the us logged if to the system */
var currentUserId =	 '<%=session.getAttribute("userid")%>';	


//define state for the editable page
var state = {
    EDIT: 1,
    READ: 0,	    
};
/*************************************************/
//TODO //*  START PAGE FUNCTIONS       */
/*************************************************/
function loadRegistrationGrid(pupilID){
	  $("#list").jqGrid({
        url : "PupilRegistration?action=getRegistration&pupilID="+ pupilID,
        datatype : "json",
        mtype : 'POST',
        colNames : ['','יום ראשון' , 'יום שני' , 'יום שלישי', 'יום רביעי', 'יום חמישי'],
        colModel : [ {
                name : 'type',
                index : 'type',
                width : 100,               
        }, {
                name : 'sunday',
                index : 'sunday',
                width : 100,    
                
        }, {
                name : 'monday',
                index : 'monday',
                width : 100,    
                
        }, {
                name : 'tuesday',
                index : 'tuesday',
                width : 100,                                
                
        }, {
                name : 'wednesday',
                index : 'wednesday',
                width : 100,
                width : 100,    
        }, {
            name : 'thursday', 
            index : 'thursday',
            width : 100,            
        } ],
        pager : '#pager',
        rowNum : 30,
        rowList : [ ],
        sortname : 'sunday',
        /*scroll: true,*/
        direction:"rtl",
        viewrecords : true,
        gridview : true,
        height: "100%",
       /* ondblClickRow: function(rowId) {
            var rowData = jQuery(this).getRowData(rowId); 
            var pupilID = rowData.id;
            window.location.href = "pupil_card_view.jsp?pupil="+pupilID+"";
        },*/
        jsonReader : {
                repeatitems : false,
        },
        /*editurl : "FullPupilCardController",*/
        recreateFilter:true,               
        pgbuttons: false,     // disable page control like next, back button
        pgtext: null         // disable pager text like 'Page 0 of 10'
        /*viewrecords: false*/
        
});
jQuery("#list").jqGrid('navGrid', '#pager', {
        edit : false,
        add : false,
        del : false,
        search : false,
        refresh: false
});


/*jQuery("#list").jqGrid('filterToolbar',{autosearch:true, stringResult: true});*/


}	 	
