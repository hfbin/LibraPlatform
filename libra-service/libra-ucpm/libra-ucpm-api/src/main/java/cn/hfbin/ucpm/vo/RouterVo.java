package cn.hfbin.ucpm.vo;

import cn.hfbin.ucpm.entity.Menu;
import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangfubin
 * @Description: RouterVo 类
 * @Date: 2021/6/25
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "前端路由")
public class RouterVo implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "路径PATH")
    private String path;
    @ApiModelProperty(value = "菜单名称")
    private String name;
    @ApiModelProperty(value = "组件")
    private String component;
    @ApiModelProperty(value = "元数据")
    private RouterMetaVo meta;
    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden = false;
    @ApiModelProperty(value = "总是显示")
    private Boolean alwaysShow = false;
    @JsonIgnore
    @ApiModelProperty(value = "id")
    private Long id;
    @JsonIgnore
    @ApiModelProperty(value = "父id")
    private Long parentId;
    @ApiModelProperty(value = "排序")
    private Integer sortValue;
    @ApiModelProperty(value = "子菜单")
    private List<RouterVo> children;

    /**
     * 根据子集处理是否显示
     *
     * @return
     */
    public Boolean getAlwaysShow() {
        return !CollectionUtil.isEmpty(getChildren());
    }

    /**
     * list转tree
     * @param list list源数据
     * @return List数结构
     */
    public static List<RouterVo> listToTree(List<Menu> list) {
        List<RouterVo> routerVoList = new ArrayList<>();
        list.forEach(data -> {
            RouterVo routerVo = new RouterVo();
            BeanUtils.copyProperties(data,routerVo);
            RouterMetaVo routerMetaVo = new RouterMetaVo();
            routerMetaVo.setIcon(data.getIcon());
            routerMetaVo.setBreadcrumb(true);
            routerMetaVo.setTitle(data.getName());
            routerMetaVo.setNoCache(data.getKeepAlive() == 0);
            routerVo.setSortValue(data.getSortNo());
            routerVo.setMeta(routerMetaVo);
            routerVoList.add(routerVo);
        });
        //用递归找子。
        List<RouterVo> treeList = new ArrayList<>();
        for (RouterVo tree : routerVoList) {
            //根目录的parentId为0
            if (tree.getParentId() == 0) {
                treeList.add(findChildren(tree, routerVoList));
            }
        }
        return treeList;
    }

    private static RouterVo findChildren(RouterVo tree, List<RouterVo> list) {
        for (RouterVo node : list) {
            if (node.getParentId().longValue() == tree.getId().longValue()) {
                if (tree.getChildren() == null) {
                    tree.setChildren(new ArrayList<>());
                }
                tree.getChildren().add(findChildren(node, list));
            }
        }
        return tree;
    }

}
