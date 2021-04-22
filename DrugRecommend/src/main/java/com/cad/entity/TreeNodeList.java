package com.cad.entity;

import java.util.List;

public class TreeNodeList {
    String NodeName;
    List<String> LeafNode;
    List<String> subNode;

    public String getNodeName() {
        return NodeName;
    }

    public void setNodeName(String nodeName) {
        NodeName = nodeName;
    }

    public List<String> getLeafNode() {
        return LeafNode;
    }

    public void setLeafNode(List<String> leafNode) {
        LeafNode = leafNode;
    }

    public List<String> getSubNode() {
        return subNode;
    }

    public void setSubNode(List<String> subNode) {
        this.subNode = subNode;
    }
}
