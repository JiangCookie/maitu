$(document).ready(function () {

    //分类拼接HTML函数
    function PJHTMLren( name , categoryImage, categoryID){
        var categoryUrl = ajaxRoot + GLOBAL_AJAX_URL.imageList + '?' + categoryID;
        var imageUrl = ajaxRoot + categoryImage;
        var HTMLbank='<div class="col-xs-12 col-sm-6 col-md-3" style="height: 250px">'+
                        '<div class="sol">'+
                            '<a href="' + categoryUrl + '">'+
                                '<img style="height: 200px" class="img-responsive" src="' + imageUrl + '" alt="First category picture">'+
                                '<span class="topic__name" style="position: absolute; top: 45%; left: 25%;font-size: 18px;font-weight: 600; z-index: 1;color: whitesmoke;">' + name + '</span>'+
                            '</a>'+
                        '</div>'+
                    '</div>';
        return HTMLbank;
    }

    //分类一级查询
    $.ajax({
        url: ajaxRoot+GLOBAL_AJAX_URL.categoryList,
        type: "GET",
        beforeSend: setHeader,
        success: function (res) {
            for (i in res.data) {
               var name = res.data[i].name;
               var categoryImage = res.data[i].categoryImage;
               var categoryID = res.data[i].id;
               var html = PJHTMLren( name , categoryImage, categoryID);

               $("#category").append(html);
            }



            console.log("分类"+res.data[0].name);

        }
    })

});