jQuery(document).ready(function() {
        $("#list").jqGrid({
                url : "GridServlet",
                datatype : "json",
                mtype : 'POST',
                colNames : ['שם פרטי' , 'שם משפחה' , 'מגדר', 'כיתה', 'רשום?'],
                colModel : [ {
                        name : 'id',
                        index : 'id',
                        width : 100
                }, {
                        name : 'firstName',
                        index : 'firstName',
                        width : 150,
                        editable : true
                }, {
                        name : 'lastName',
                        index : 'lastName',
                        width : 150,
                        editable : true
                }, {
                        name : 'Grade',
                        index : 'city',
                        width : 100,
                        editable : true
                }, {
                        name : 'Grade',
                        index : 'state',
                        width : 100,
                        editable : true
                } ],
                pager : '#pager',
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                sortname : 'invid',
                sortorder : 'desc',
                direction:"rtl",
                viewrecords : true,
                gridview : true,
                caption : 'Data Report',
                jsonReader : {
                        repeatitems : false,
                },
                editurl : "GridServlet"
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {
                edit : true,
                add : true,
                del : true,
                search : true
        });
});