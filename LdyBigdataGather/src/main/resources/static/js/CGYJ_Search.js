$(function(){
    $('#layoutWest').panel({
        onResize: function(width, height){
            var windowWidth = $(document.body).innerWidth();
            var westHeight = $("#layoutWest").innerHeight();
            var select_conditionHeight = $("#select_condition").innerHeight();
            $("#data_grid").datagrid("resize", {
                width: windowWidth - width - 20,
                height: westHeight - select_conditionHeight - 10,
            });
            $("#select_condition").panel("resize", {
                width: windowWidth - width - 20,
            });
        },
    });
    
    window.onresize = function(){
        setResize();
    };
    $("#select_condition").panel({
        title: " ",
        noheader: false,
        collapsible: true,
    });
    
    $("#yjsj_start").datebox({
        formatter: function(date){
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            if (m < 10) 
                m = "0" + m;
            if (d < 10) 
                d = "0" + d;
            var strDate = "" + y + '/' + m + '/' + d;
            //alert(strDate);
            return strDate;
        },
        parser: function(s){
            var t = Date.parse(s);
            if (isNaN(t)) {
                return new Date();
            }
            else {
                return new Date(t);
            }
        },
    });
    
    $("#yjsj_end").datebox({
        formatter: function(date){
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            if (m < 10) 
                m = "0" + m;
            if (d < 10) 
                d = "0" + d;
            var strdate = "" + y + "/" + m + "/" + d;
            //alert(strdate);
            return strdate;
        },
        parser: function(strDate){
            var t = Date.parse(strDate);
            if (isNaN(t)) {
                return new Date();
            }
            else {
                return new Date(strDate);
            }
        },
    });
    
    $("#yjxxly").combobox({
        width: 100,
        panelHeight: 100,
    });
    
    $("#yjjb").combobox({
        width: 100,
        panelHeight: 120,
    });
    
    $("#rylb").combobox({
        method: 'post',
        url: 'ZhongDianRenYuan.do',
        valueField: 'valueField',
        textField: 'textField',
        width: 130,
    });
    
    $("#czzt").combobox({
        width: 100,
        panelHeight: 120,
    });
    
    $("#jsdw").combotree({
        method: 'post',
        url: 'DanWei.do',
        width: 200,
        panelWidth: 300,
        panelHeight: 300,
        onlyLeafCheck: true,
        cascadeCheck: false,
        lines: true,
        //dnd: true,
        animate: true,
    });
    
    $("#qszt").combobox({
        width: 100,
        panelHeight: 120,
    });
    
    $("#gjlb").combobox({
        width: 100,
        panelWidth: 100,
    });
    
    $("#fkzt").combobox({
        width: 100,
        panelWidth: 100,
        panelHeight: 80,
    });
    
    $("#data_grid").datagrid({
        columns: [[{
            field: 'ID',
            title: '序号',
            width: 40,
            //fixed: true,
        }, {
            field: 'QSBS',
            title: '状态',
            width: 50,
            formatter: function(value, rowData, rowIndex){
                if (value == "未反馈") {
                    return "<span style='color:red;'>" +
                    value +
                    "</span>";
                }
                else 
                    if (value == "未签收") {
                        return "<span style='color:red;'>" +
                        value +
                        "</span>";
                    }
                    else {
                        return value;
                    }
            },
        }, {
            field: 'ZDRYXM',
            title: '姓名',
            width: 100,
            //align: 'right'
        }, {
            field: 'SFZH',
            title: '身份证号',
            width: 100
        }, {
            field: 'YJXXBH',
            title: '预警编号',
            width: 100
        }, {
            field: 'YJJB',
            title: '预警级别',
            width: 100,
            formatter: function(value, rowData, rowIndex){
                if (value == "黄色") {
                    return "<a href='javascript:void(0)' style='background-color:yellow;color:red;'>" +
                    value +
                    "</a >";
                }
                else 
                    if (value == "蓝色") {
                        return "<span style='background-color:blue;color:red;'>" +
                        value +
                        "</span>";
                    }
                    else {
                        return value;
                    }
            },
            styler: function(value, rowData, rowIndex){
                if (value == "黄色") {
                    //return "background-color:yellow;color:red;";
                }
            },
        }, {
            field: 'zdryxl',
            title: '人员类别',
            width: 100
        }, {
            field: 'DTXXLB',
            title: '轨迹类别',
            width: 100
        }, {
            field: 'yjjsdw',
            title: '接收单位',
            width: 100
        }, {
            field: 'CZJG',
            title: '处置结果',
            width: 100
        }, {
            field: 'YJFBSJ',
            title: '预警时间',
            width: 100
        }, {
            field: 'ssjly',
            title: '预警来源',
            width: 100
        }, {
            field: 'ypzt',
            title: '研判状态',
            width: 100
        }, {
            field: 'QSCS',
            title: '提示',
            width: 100
        }, ]],
        pagination: true,
        pagePosition: 'bottom',
        pageSize: 10,
        pageList: [10, 20, 30],
        pageNumber: 1,
        width: 1000,
        height: 500,
        singleSelect: true,
        //fitColumns: true,
        onClickCell: function(index, field, value){
            if (field == "YJJB") {
                alert(index + field + value);
            }
        }
    });
    setResize();
    
});
function setResize(){
    /*
     alert($(window).height()); //浏览器时下窗口可视区域高度
     alert($(document).height()); //浏览器时下窗口文档的高度
     alert($(document.body).height());//浏览器时下窗口文档body的高度
     alert($(document.body).outerHeight(true));//浏览器时下窗口文档body的总高度 包括border padding margin
     alert($(window).width()); //浏览器时下窗口可视区域宽度
     alert($(document).width());//浏览器时下窗口文档对于象宽度
     alert($(document.body).width());//浏览器时下窗口文档body的高度
     alert($(document.body).outerWidth(true));//浏览器时下窗口文档body的总宽度 包括border padding margin
     
     alert($(document).scrollTop()); //获取滚动条到顶部的垂直高度
     alert($(document).scrollLeft()); //获取滚动条到左边的垂直宽度
     */
    var windowWidth = $(document.body).innerWidth();
    var westWidth = $("#layoutWest").width();
    var westHeight = $("#layoutWest").innerHeight();
    var select_conditionHeight = $("#select_condition").innerHeight();
    //alert("setResize" + westHeight);
    $("#data_grid").datagrid("resize", {
        width: windowWidth - westWidth - 20,
        height: westHeight - select_conditionHeight - 10,
    });
    $("#select_condition").panel("resize", {
        width: windowWidth - westWidth - 26,
    });
};
function Reset(){
    $("#yjsj_start").datebox("setValue", "");
    $("#yjsj_end").datebox("setValue", "");
    $("#sfzh").val("");
    $("#ryxm").val("");
    $("#yjxxly").combobox("select", "");
    $("#czzt").combobox("select", "");
    $("#rylb").combobox("select", "");
    $("#jsdw").combotree("setText", "");
    $("#yjbh").val("");
    $("#yjjb").combobox("select", "");
    $("#qszt").combobox("select", "");
    //gjlb
    $("#fkzt").combobox("select", "");
    
};
function selects(){
    var start_time = "";
    var start_end = "";
    var strStart = $("#yjsj_start").datebox("getValue");
    if (strStart != "") {
        var date = new Date(strStart);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        if (m < 10) 
            m = "0" + m;
        if (d < 10) 
            d = "0" + d;
        start_time = "" + y + m + d + '000000';
        //alert(start_time); 
    }
    var strEnd = $("#yjsj_end").datebox("getValue");
    if (strEnd != '') {
        date = new Date(strEnd);
        y = date.getFullYear();
        m = date.getMonth() + 1;
        d = date.getDate();
        if (m < 10) 
            m = "0" + m;
        if (d < 10) 
            d = "0" + d;
        start_end = "" + y + m + d + '235959';
        //alert(start_end);
    }
    var yjjsdw = $("#jsdw").combotree("getText");
    if (yjjsdw != '') {
        yjjsdw = $("#jsdw").combotree("getValue");
    }
    $("#data_grid").datagrid({
        method: 'post',
        pageNumber: 1,
        loadMsg: "正在加载数据。。。",
        url: 'YuJingXinXi.do',
        queryParams: {
            ZDRYXM: $("#ryxm").val(), //重点人员姓名
            SFZH: $("#sfzh").val(),//身份证号
            YJLY: $("#yjxxly").combobox("getValue"),//预警来源
            CZZT: $("#czzt").combobox("getValue"),// 操作状态	
            zdryxl: $("#rylb").combobox("getValue"),// 重点人员细类	
            yjjsdw: yjjsdw,// 预警接收单位
            YJXXBH: $("#yjbh").val(), // 预警信息编号
            YJJB: $("#yjjb").combobox("getValue"),// 预警级别
            QSCS: $("#qszt").combobox("getValue"), // 签收标示 签收超时 
            DTXXLB: "",// 动态信息类别 轨迹类别
            YJFBSJ_Start: start_time,//预警发布时间 
            YJFBSJ_End: start_end,
            FKCS: $("#fkzt").combobox("getValue"),// 签收超时 反馈超时
        },
    
    });
    //$.post("/IIIP/YJXXAction.do");

};
