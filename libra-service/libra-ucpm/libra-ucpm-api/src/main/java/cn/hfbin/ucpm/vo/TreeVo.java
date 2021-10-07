package cn.hfbin.ucpm.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangfubin
 * @Description: MenuVo 类
 * @Date: 2021/7/19
 */
@Data
@ApiModel(value = "树结构响应实体")
public class TreeVo {
    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 父ID(为0则一级)
     */
    @ApiModelProperty(value = "父ID(为0则一级)")
    private Long parentId;
    /**
     * 菜单标题
     */
    @ApiModelProperty(value = "菜单标题")
    private String name;

    /**
     * 备注字段
     */
    @ApiModelProperty(value = "备注字段")
    private String remark;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点")
    private List<TreeVo> child;

    /**
     * list转tree
     * @param list list源数据
     * @return List数结构
     */
    public static List<TreeVo> listToTree(List<TreeVo> list) {
        //用递归找子。
        List<TreeVo> treeList = new ArrayList<>();
        for (TreeVo tree : list) {
            //根目录的parentId为0
            if (tree.getParentId() == 0) {
                treeList.add(findChildren(tree, list));
            }
        }
        return treeList;
    }

    private static TreeVo findChildren(TreeVo tree, List<TreeVo> list) {
        for (TreeVo node : list) {
            if (node.getParentId().longValue() == tree.getId().longValue()) {
                if (tree.getChild() == null) {
                    tree.setChild(new ArrayList<>());
                }
                tree.getChild().add(findChildren(node, list));
            }
        }
        return tree;
    }
}
