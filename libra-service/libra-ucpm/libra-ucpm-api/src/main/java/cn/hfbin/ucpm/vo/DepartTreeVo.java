package cn.hfbin.ucpm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hfbin
 * @email huangfubin00@gmail.com
 * @date 2021/7/6 11:26 下午
 * @description:
 */

@Data
@ApiModel(value = "部门响应对象")
public class DepartTreeVo extends DepartVo implements Serializable{
    private static final long serialVersionUID = -1L;
    @ApiModelProperty(value = "子菜单")
    private List<DepartTreeVo> children;
    @ApiModelProperty(value = "部门名称")
    private String label;
    

    /**
     * list转tree
     * @param list list源数据
     * @return List数结构
     */
    public static List<DepartTreeVo> listToTree(List<DepartTreeVo> list) {
        //用递归找子。
        List<DepartTreeVo> treeList = new ArrayList<>();
        for (DepartTreeVo tree : list) {
            //根目录的parentId为0
            if (tree.getParentId() == 0) {
                treeList.add(findChildren(tree, list));
            }
        }
        return treeList;
    }

    private static DepartTreeVo findChildren(DepartTreeVo tree, List<DepartTreeVo> list) {
        for (DepartTreeVo node : list) {
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
