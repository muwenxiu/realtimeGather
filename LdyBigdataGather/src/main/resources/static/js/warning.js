$(function () {
        $('#serverWarning').datagrid({
            loadMsg: "正在加载数据。。。",
            url: 'get/getBackstageService',
            //fitColumns:true,//宽度自适应
            columns: [[
                {field: "backstageServiceName", title: "服务名称", width: 200},
                {
                    field: "backstageServiceStatus", title: "状态", width: 200, formatter: function (value, rowData, rowIndex) {
                        if (value == 'ERROR') {
                            return "<span style='color: red'> " + value + "</span>";
                        } else {
                            return value;
                        }
                    },
                },
                {
                    field: "serverOper", title: "操作", width: 50, formatter: function (value, rowData, rowIndex) {
                        var str = '<a href="#" name="opera" class="easyui-linkbutton" ></a>';
                        return str
                    }
                },
            ]],
            method: 'post'
        });
        $('#onTimeGatherWarning').datagrid({
            //fitColumns:true,//宽度自适应
            columns: [[
                {field: 'errorBatch', width: 200, title: "报错批次"},
                {field: 'errorTaskID', width: 200, title: "报错任务ID"},

            ]]
        });


    }
);