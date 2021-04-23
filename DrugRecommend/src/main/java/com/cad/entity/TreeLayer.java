package com.cad.entity;

import java.util.List;


public class TreeLayer {
    TreeNode node;
    List<TreeNode> childList;

    public TreeNode getNode() {
        return node;
    }

    public void setNode(TreeNode node) {
        this.node = node;
    }

    public List<TreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }
}
