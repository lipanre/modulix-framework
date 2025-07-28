package com.modulix.framework.common.core.util;



import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 树工具类
 *
 * @author LiPan
 */
public class TreeUtil {

    /**
     * 树根节点，parentId
     */
    public static final Long DEFAULT_TREE_ROOT_PARENT_ID = 0L;

    /**
     * 构建树
     *
     * @param nodes 节点列表
     * @param <T>   节点标识符
     * @return 树根节点列表
     */
    public static <T, N extends TreeNode<T>> List<N> buildTree(List<N> nodes) {
        return buildTree(nodes, parentId -> Objects.equals(parentId, DEFAULT_TREE_ROOT_PARENT_ID));
    }

    /**
     * 构建树
     *
     * @param nodes 节点列表
     * @param <T>   节点标识符
     * @return 树根节点列表
     */
    public static <T, N extends TreeNode<T>> List<N> buildTree(List<N> nodes, Predicate<T> rootCondition) {
        // 1. 过滤出来根节点
        List<N> roots = nodes.stream().filter(node -> rootCondition.test(node.getParentId())).toList();
        // 2. 构建子树
        return roots.parallelStream()
                .peek(root -> buildTree(root, nodes))
                .sorted(Comparator.comparing(TreeNode::getSort, Comparator.nullsLast(Integer::compareTo)))
                .toList();
    }

    /**
     * 构建以指定父节点为根节点的树
     *
     * @param parent 父节点
     * @param nodes 节点列表
     * @param <T> 标识符类型
     */
    @SuppressWarnings("unchecked")
    public static <T, P extends TreeNode<T>> void buildTree(P parent, List<? extends TreeNode<T>> nodes) {
        List<? extends TreeNode<T>> children = nodes.stream()
                .filter(node -> Objects.equals(node.getParentId(), parent.getId()))
                .sorted(Comparator.comparing(TreeNode::getSort, Comparator.nullsLast(Integer::compareTo)))
                .toList();
        if (children.isEmpty()) {
            return;
        }
        // 子节点再构建子树
        children.parallelStream().forEach(node -> buildTree(node, nodes));
        parent.setChildren((List<TreeNode<T>>) children);
    }
}
