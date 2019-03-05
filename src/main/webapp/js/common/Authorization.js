$(document).ready(function () {
    $.ajax({
        url: ajaxRoot+GLOBAL_AJAX_URL.get_information,
        type: "POST",
        async: false,
        headers: {
            "Authorization": getCookie()
        },
        error : function(xhr){
            if (xhr.status == 401) {
                top.location.href="adminLogin.html"
            }
        }
    })
})

// $("#addCategory").click(function () {
//     $.ajax({
//         url: ajaxRoot+GLOBAL_AJAX_URL.add_category,
//         type: "POST",
//         async: false,
//         beforeSend: setHeader,
//         data:{
//             categoryName: $('input[name="categoryName"]').val()
//         },
//         success: function (res) {
//             console.log(res);
//         }
//     })
// });