jQuery(document).ready(function() {
        $("#list").jqGrid({
                url : "FullPupilCardController?action=getAll",
                datatype : "json",
                mtype : 'POST',
                colNames : ['מספר','שם פרטי' , 'שם משפחה' , 'מגדר', 'כיתה', 'רשום'],
                colModel : [ {
                        name : 'id',
                        index : 'id',
                        width : 100,
                        hidden: true
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
                        name : 'gender',
                        index : 'gender',
                        width : 100,
                        editable : true,
                  /*      search: true,
                        stype: 'select'*/
                }, {
                        name : 'Grade',
                        index : 'Grade',
                        width : 100,
                        editable : true
                }, {
                    name : 'isReg',
                    index : 'isReg',
                    width : 100,
                    editable : true
                } ],
                pager : '#pager',
                rowNum : 30,
                rowList : [ ],
                sortname : 'Grade',
              /*  sortorder : 'desc',*/
                direction:"rtl",
                viewrecords : true,
                gridview : true,
                height: 200,
               
               /* caption: 'כל התלמידים',*/
                jsonReader : {
                        repeatitems : false,
                },
                editurl : "FullPupilCardController"
        });
        jQuery("#list").jqGrid('navGrid', '#pager', {
                edit : true,
                add : true,
                del : true,
                search : true
        });
        
      
        jQuery("#list").jqGrid('filterToolbar',{autosearch:true});

});