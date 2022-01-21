package com.offcn.project.controller;

import com.offcn.common.response.AppResponse;
import com.offcn.common.utils.OSSTemplate;
import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.resp.ProjectDetailVo;
import com.offcn.project.vo.resp.ProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.offcn.common.response.AppResponse.ok;

@RestController
@RequestMapping("/project")
@Api(tags = "项目模块")
public class ProjectController {

    @Autowired
    private OSSTemplate ossTemplate;

    @Autowired
    private ProjectCreateService projectService;

    @PostMapping("/upload")
    public AppResponse<Map<String,Object>> uploadFile(@RequestParam("file") MultipartFile[] files) throws IOException {
        Map<String,Object> map = new HashMap();
        List<String> list = new ArrayList<>();
        if(files != null && files.length > 0){
            for(MultipartFile item : files){
                // 获取文件的文件流 item.getInputStream()
                // 获取文件之前的名字 item.getOriginalFilename();
                String fileUrl = ossTemplate.upload(item.getInputStream(), item.getOriginalFilename());
                list.add(fileUrl);
            }
        }
        map.put("urls",list);
        return ok(map);
    }

    @ApiOperation(value = "展示所有的项目")
    @GetMapping("/all")
    public AppResponse<List<ProjectVo>> findAllProject(){
        // 1.创建集合用于存储所有项目
        List<ProjectVo> list = new ArrayList<>();
        // 2.查询
        List<TProject> prosVos = projectService.findAllProject();
        // 3.当前的内容都是TProject，而返回值的类型是 ProjectVo
        for(TProject tProject : prosVos){
            // 获取当前项目名
            Integer projectId = tProject.getId();
            // 根据项目ID查询头图片
            List<TProjectImages> images = projectService.getProjectImagesByProjectId(projectId);
            ProjectVo projectVo = new ProjectVo();
            // 复制属性
            BeanUtils.copyProperties(tProject,projectVo);
            // 还少头图片，添加头图片
            if (images != null && images.size() > 0) { // 非空验证
                for (TProjectImages imgs : images) {
                    // 获取头图片
                    if (imgs.getImgtype() == 0) {
                        projectVo.setHeaderImage(imgs.getImgurl());
                    }
                }
            }
            list.add(projectVo);
        }
        return AppResponse.ok(list);
    }


    @ApiOperation(value = "查询项目的详细数据")
    @GetMapping("/findProject/{projectId}")
    public AppResponse<ProjectDetailVo> findProjectInfo(@PathVariable("projectId") Integer projectId){
        ProjectDetailVo detailVo = new ProjectDetailVo();
        TProject projectInfo = projectService.findProjectInfo(projectId);
        BeanUtils.copyProperties(projectInfo,detailVo);
        // 图片的添加
        List<TProjectImages> images = projectService.getProjectImagesByProjectId(projectId);
        detailVo.setDetailsImage(new ArrayList<>());
        if(images == null){ // 无图片的情况下，相关图片均设置为null
            detailVo.setHeaderImage(null);
        }else {
            for(TProjectImages img : images){
                if(img.getImgtype() == 0){ // 头图
                    detailVo.setHeaderImage(img.getImgurl());
                }else { // 尾图
                    detailVo.getDetailsImage().add(img.getImgurl());
                }
            }
        }
        // 回报的添加
        List<TReturn> returns = projectService.findTReturnByProjectId(projectId);
        detailVo.setProjectReturn(returns);
        return AppResponse.ok(detailVo);
    }


    @ApiOperation(value = "查询所有的项目标签")
    @GetMapping("/findAllTag")
    public AppResponse<List<TTag>> findAllTag(){
        List<TTag> tags = projectService.findAllTag();
        return AppResponse.ok(tags);
    }

    @ApiOperation(value = "查询所有的项目分类")
    @GetMapping("/findAllType")
    public AppResponse<List<TType>> findAllType(){
        List<TType> allType = projectService.findAllType();
        return AppResponse.ok(allType);
    }

    @ApiOperation(value = "根据回报Id获取回报信息")
    @GetMapping("/findReturnById/{returnId}")
    public TReturn findReturnById(@PathVariable("returnId") Integer returnId){
        return projectService.findReturnInfo(returnId);
    }

//    @ApiOperation(value = "根据项目Id获取回报信息")
    @GetMapping("/details/returns/{projectId}")
    public AppResponse<List<TReturn>> getReturnListByPid(@PathVariable("projectId") Integer projectId){
        List<TReturn> returns = projectService.findTReturnByProjectId(projectId);
        return AppResponse.ok(returns);
    }
}
