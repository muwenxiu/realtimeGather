$(function () {
    $('#analyseLayout').layout({
            fit: true,
        }
    );
    $("#analyseBatchType").combobox({});
    $("#btnSelectAnalyseBatch").linkbutton({
        iconCls: 'icon-search',
    });

    $("#analyseBatch").datalist({
        url: 'get/getAnalyseBatch',
        queryParams: {
            status: '',
        },
        checkbox: false,
        lines: true,
        valueField: 'key',
        textField: 'value',
        onSelect: function (rowIndex, rowData) {
            var batchId = rowData.value;
            console.log(batchId)
            analyseInfo(batchId);
        }
    });

    analyseInfo(1234);
    setAnalyseResize();

    window.onresize = function () {
        setAnalyseResize();
    }
});

function selectAnalyseBatch() {
    var status = $("#analyseBatchType").combobox("getValue")
    $("#analyseBatch").datalist({
        url: 'get/getAnalyseBatch',
        queryParams: {
            status: status,
        }
    });
}

function analyseInfo(batchId) {
    $("#analyseInfoTable").datagrid({
        method: 'post',
        showHeader: true,
        noheader: true,
        loadMsg: "正在加载数据。。。",
        url: 'get/getAnalyseTaskInfo',
        queryParams: {
            batchId: batchId,
        },
    });
}

function setAnalyseResize() {
    var windowCenterHeight = $("#layoutCenter").innerHeight();
    var windowCenterWidth = $("#layoutCenter").width();
    console.log("analyse.html Width=" + windowCenterWidth + ",Height=" + windowCenterHeight);
    $("#analyseLayout").height(windowCenterHeight);
    $("#analyseLayout").width(windowCenterWidth);

    $("#analyseLayoutWest").height(windowCenterHeight - 2);
    $("#analyseLayoutCenter").height(windowCenterHeight - 2);
    var analyseLayoutWestWidth=$("#analyseLayoutWest").width();
    $("#analyseLayoutCenter").width(windowCenterWidth - analyseLayoutWestWidth);
    $("#analyseInfoTable").datagrid("resize", {
        width: windowCenterWidth - analyseLayoutWestWidth - 20,
    });
}