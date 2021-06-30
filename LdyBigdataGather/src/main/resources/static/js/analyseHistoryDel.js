$(function () {
    $('#delAnalyseLayout').layout({
            fit: true,
        }
    );
    $("#delAnalyseBatchType").combobox({});
    $("#btnSelectAnalyseBatchDel").linkbutton({
        iconCls: 'icon-search',
    });

    $("#analyseBatchDel").datalist({
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
            console.log("分析批次:" + batchId)
            $.ajax({
                url: 'get/getAnalyseBatchTime',
                data: {batchID: batchId,},
                success: function (r) {
                    $("#analyseTime").textbox("setValue", r);
                }
            })

        }
    });
    $("#analyseTime").textbox({
        //iconCls: 'icon-cancel',
    });
    $("#btnAnalyseBatchDel").linkbutton({
        iconCls: 'icon-cancel',
    });
    delSetAnalyseResize();

    window.onresize = function () {
        delSetAnalyseResize();
    }
});

function selectAnalyseBatchDel() {
    var status = $("#delAnalyseBatchType").combobox("getValue")
    $("#analyseBatchDel").datalist({
        url: 'get/getAnalyseBatch',
        queryParams: {
            status: status,
        }
    });
}

function analyseBatchDel() {
    var time = $("#analyseTime").textbox('getValue');
    console.log("删除：" + time)
    $.ajax({
        type: 'POST',
        url: 'get/delAnalyseTask',
        data: {time: time},
    });
    selectAnalyseBatchDel();
}

function delSetAnalyseResize() {
    var windowCenterHeight = $("#layoutCenter").innerHeight();
    var windowCenterWidth = $("#layoutCenter").width();
    console.log("analyse.html Width=" + windowCenterWidth + ",Height=" + windowCenterHeight);
    $("#delAnalyseLayout").height(windowCenterHeight);
    $("#delAnalyseLayout").width(windowCenterWidth);

    $("#delAnalyseLayoutWest").height(windowCenterHeight - 2);
    $("#delAnalyseLayoutCenter").height(windowCenterHeight - 2);
    var analyseLayoutWestWidth = $("#analyseLayoutWest").width();
    $("#delAnalyseLayoutCenter").width(windowCenterWidth - analyseLayoutWestWidth);

}