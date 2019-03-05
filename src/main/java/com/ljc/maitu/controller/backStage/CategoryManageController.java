package com.ljc.maitu.controller.backStage;

import com.google.common.base.Strings;
import com.ljc.maitu.common.ServerResponse;
import com.ljc.maitu.controller.BasicController;
import com.ljc.maitu.service.CategoryService;
import com.ljc.maitu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * @author JYH
 * 2019/2/11 14:44
 */
@RestController
@RequestMapping("/manage/category/")
public class CategoryManageController extends BasicController {



    @Autowired
    public CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Value("${image.categoryBasic.Space}")
    private String categoryBasic_Space;

    @RequestMapping("add_categoryImage")
    public ServerResponse addCategory( @RequestParam("file") MultipartFile file ) throws IOException {
        // 保存到数据库中的相对路径
        String categoryImage ;

        if(file != null){
            String fileName = file.getOriginalFilename();
            if(!Strings.isNullOrEmpty(fileName)){

                //本地保存路径
                String localPath = categoryBasic_Space + "categoryImage" + File.separator + fileName;

                //数据库保存路径
                categoryImage = "categoryImage" + File.separator + fileName;

                File outFile = new File(localPath);
                if(outFile.getParentFile() != null || outFile.isDirectory() ){
                    // 创建父文件夹
                    outFile.getParentFile().mkdirs();
                }
                file.transferTo(outFile);
            }else {
                return ServerResponse.createByErrorMessage("上传出错");
            }
        }   else {
            return ServerResponse.createByErrorMessage("上传文件为空...");
        }
        return ServerResponse.createBySuccess(categoryImage);
    }

    @RequestMapping("add_category")
    public ServerResponse addCategory(String categoryName,String categoryImage,@RequestParam(value = "parentId", defaultValue = "0") int parentId) throws IOException {
        return categoryService.addCategory(categoryName,parentId,categoryImage);
    }

    @RequestMapping("set_category_name")
    public ServerResponse setCategoryName(Integer categoryId, String categoryName){
            //更新categoryName
            return categoryService.updateCategoryName(categoryId,categoryName);
    }

    @RequestMapping("get_category")
    public ServerResponse getChildrenParallelCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
            return categoryService.getChildrenParallelCategory(categoryId);

    }

    @RequestMapping("get_deep_category")
    public ServerResponse getCategoryAndDeepChildrenCategory(@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
            //查询当前节点的id和递归子节点的id
//            0->10000->100000
            return categoryService.selectCategoryAndChildrenById(categoryId);

    }
}
