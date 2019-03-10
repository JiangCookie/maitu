$(function () {

    //查询分类，插入下拉选择框
    $.ajax({
        url: ajaxRoot+GLOBAL_AJAX_URL.categoryList,
        type: "GET",
        beforeSend: setHeader,
        async: false,
        success: function (res) {
            for(index in res.data){
                $("select[name='interest']").append("<option value='"+res.data[index].id+"'>"+res.data[index].name+"</option>");
            }

        }
    })
});

layui.use(['layer', 'form'],function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;

    //触发事件
    var active = {
        natice: function () {
            layer.open({
                type: 1,
                title: ['上传图片','font-size:18px'],
                area: ['450px', '500px'],
                btnAlign: 'c',
                content: $("#add-main"),
                success: function(layero, index){
                    console.log(layero, index);
                }
            })
        }
    }


    $('#addImage').on('click', function(){
        active.natice();
    });


    //表单监听提交
    form.on('submit(save)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
})

