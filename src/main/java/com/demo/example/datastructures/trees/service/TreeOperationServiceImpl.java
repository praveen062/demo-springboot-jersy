package com.demo.example.datastructures.trees.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.demo.example.datastructures.trees.data.TreeNode;
import com.demo.example.support.serialization.FromJsonHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

@Service
public class TreeOperationServiceImpl implements TreeOperationService {

	private FromJsonHelper fromApiJsonHelper;
	private JsonElement jsonElement;

	public TreeOperationServiceImpl(final FromJsonHelper fromApiJsonHelper) {
		this.fromApiJsonHelper = fromApiJsonHelper;
	}

	@Override
	public TreeNode<String> insertNodeData(TreeNode<String> root, String json) {
		jsonElement = fromApiJsonHelper.parse(json);
		JsonArray jsondata = fromApiJsonHelper.extractJsonArrayNamed("items", jsonElement);
		
		for(JsonElement element: jsondata){
			String value = fromApiJsonHelper.extractStringNamed("value", element);
			root = insertNode(root, value);
		}
		return root;
	}

	@Override
	public TreeNode<String> insertNodeDataIntoAvlTree(TreeNode<String> root, String json) {
		jsonElement = fromApiJsonHelper.parse(json);
		JsonArray jsondata = fromApiJsonHelper.extractJsonArrayNamed("items", jsonElement);
		
		for(JsonElement element: jsondata){
			String value = fromApiJsonHelper.extractStringNamed("value", element);
			root = insertDataInAvalTree(root, value);
		}
		return root;
	}
	
	
	@Override
	public TreeNode<String> insertNode(TreeNode<String> head, String item) {
		if (head == null) {
			head = new TreeNode<String>(item);
		} else {
			TreeNode<String> node = null;
			Queue<TreeNode<String>> queue = new LinkedList<TreeNode<String>>();
			queue.add(head);
			while (queue.peek() != null) {
				node = queue.poll();
				System.out.println("call node"+node.data);
				if (node.leftLink == null){
					System.out.println("the root node to left of "+node.data);
					System.out.println("the item is "+item);
					node.leftLink = new TreeNode<String>(item);
					break;
				}
				else if (node.rightLink == null){
					System.out.println("the root node to right of "+node.data);
					System.out.println("the item is "+item);
					node.rightLink = new TreeNode<String>(item);
					break;
				}else{ if (node.leftLink != null)
						queue.add(node.leftLink);
					if (node.rightLink != null)
						queue.add(node.rightLink);
				}
			}
		}
		
		System.out.println("call"+head.data);
		return head;
	}

	@Override
	public void inOrderTraversal(TreeNode<String> item,List<String> items) {
		if(item == null){
			return;
		}else{
			inOrderTraversal(item.leftLink, items);
			items.add(item.data);
			inOrderTraversal(item.rightLink, items);
		}
	}

	@Override
	public void preOrderTraversal(TreeNode<String> item,List<String> items) {
		if(item == null){
			return;
		}else{
			items.add(item.data);
			preOrderTraversal(item.leftLink, items);
			preOrderTraversal(item.rightLink, items);
		}
	}

	@Override
	public void postOrderTraversal(TreeNode<String> item,List<String> items) {
		if(item == null){
			return;
		}else{
			postOrderTraversal(item.leftLink, items);
			postOrderTraversal(item.rightLink, items);
			items.add(item.data);
		}
	}

	@Override
	public void levelOrderTraversal(TreeNode<String> item,List<String> items) {
		if(item == null){
			return;
		}else{
			Queue<TreeNode<String>> queue = new LinkedList<TreeNode<String>>();
			queue.add(item);
			while(queue.peek() != null){
				TreeNode<String> node= queue.poll();
				items.add(node.data);
				if(node.leftLink != null)
				queue.add(node.leftLink);
				if(node.rightLink != null)
				queue.add(node.rightLink);
			}
		}

	}

	@Override
	public int heightOfATree(TreeNode<String> root) {
		if(root == null){
			return 0;
		}else{
			int heightOfLeftSubTree = heightOfATree(root.leftLink);
			int heightOfRightSubTree = heightOfATree(root.rightLink);
			int maxHeight = 1 + Integer.max(heightOfLeftSubTree, heightOfRightSubTree);
			return maxHeight;
		}
	}

	@Override
	public int numberOfNodesInaTree(TreeNode<String> root) {
		if(root == null){
			return 0;
		}else{
			int numberOfNodesInLeftSubTree = numberOfNodesInaTree(root.leftLink); 
			int numberOfNodesInRightSubTree = numberOfNodesInaTree(root.rightLink);
			return 1+ numberOfNodesInLeftSubTree + numberOfNodesInRightSubTree;
		}
	}

	@Override
	public void printLeftSubTree(TreeNode<String> root,List<String> items) {
		if(root == null){
			return;
		}else{
			items.add(root.data);
			printLeftSubTree(root.leftLink,items);
		}
		
	}

	@Override
	public void printRightSubTree(TreeNode<String> root,List<String> items) {
		if(root == null){
			return;
		}else{
			items.add(root.data);
			printRightSubTree(root.rightLink,items);
		}
	}

	@Override
	public void printEdgesOfTheTree(TreeNode<String> root,List<String> items) {
		if(root == null){
			return;
		}else{
			printleftNodesExcludeleaf(root,items);
			printLeafNodeOfTheTree(root, items);
			if(root.rightLink != null)
			printRightNodesExcludeleaf(root.rightLink, items);
		}
	}
	
	private void printleftNodesExcludeleaf(TreeNode<String> root,List<String> items){
		if(root == null){
			return;
		}else{
			if(root.leftLink != null || root.rightLink != null){
				items.add(root.data);
			}
			printleftNodesExcludeleaf(root.leftLink,items);
		}
	}
	
	private void printRightNodesExcludeleaf(TreeNode<String> root,List<String> items){
		if(root == null){
			return;
		}else{
			printRightNodesExcludeleaf(root.rightLink,items);
			if(root.leftLink != null || root.rightLink != null){
				items.add(root.data);
			}
		}
	}

	@Override
	public void printLeafNodeOfTheTree(TreeNode<String> root,List<String> items) {
		if(root == null){
			return;
		}else{
			if(root.leftLink == null && root.rightLink == null){
				items.add(root.data);
			}
			printLeafNodeOfTheTree(root.leftLink,items);
			printLeafNodeOfTheTree(root.rightLink,items);
		}
	}

	@Override
	public TreeNode<String> insertDataInAvalTree(TreeNode<String> root,String data) {
		if(root == null) {
			return new TreeNode<String>(data);
		}else {
			if(data != null) {
				if(data.compareTo(root.data) < 0) {
					root.leftLink = insertDataInAvalTree(root.leftLink,data);
				}else if(data.compareTo(root.data) > 0) {
					root.rightLink = insertDataInAvalTree(root.rightLink, data);
				}else {
					return root;
				}
				
				root.height = 1 + Integer.max(height(root.leftLink), height(root.rightLink));
				int balance = getBalance(root);
				if(balance < -1){
						if(data.compareTo(root.data) < 0 && root.leftLink != null && data.compareTo(root.leftLink.data) < 0){
							root = rightRotate(root);
						}else{
							if(root.leftLink !=  null)
							root = doubleRightRotate(root);
						}
				}else if(balance > 1){
						if(data.compareTo(root.data) > 0 && root.rightLink != null && data.compareTo(root.rightLink.data) > 0){
							root = leftRotate(root);
						}else{
							if(root.rightLink != null)
								root = doubleLeftRotate(root);
						}
				}
				
				
			}
			return root;	
		}
	}
	
	private TreeNode<String> rightRotate(TreeNode<String> root){
		TreeNode<String> top = root.leftLink;
		TreeNode<String> bottom = null;
		if(top != null)
			bottom = top.rightLink;
		
		top.rightLink = root;
		root.leftLink = bottom;
		
		top.height = Integer.max(height(top.leftLink), height(top.rightLink)) + 1;
		root.height = Integer.max(height(root.leftLink), height(root.rightLink)) + 1;
		return top;
	}
	
	private TreeNode<String> leftRotate(TreeNode<String> root){
		TreeNode<String> top = root.rightLink;
		TreeNode<String> bottom =  null;
		if(top != null)
			bottom = top.leftLink;
		
		top.leftLink = root;
		root.rightLink = bottom;
		
		top.height = Integer.max(height(top.leftLink), height(top.rightLink)) + 1;
		root.height = Integer.max(height(root.leftLink), height(root.rightLink)) + 1;
		return top;
	}
	
	private  TreeNode<String> doubleLeftRotate(TreeNode<String> root){
		root.leftLink = rightRotate(root.leftLink);
		return leftRotate(root);
	}
	
	private  TreeNode<String> doubleRightRotate(TreeNode<String> root){
		root.rightLink = leftRotate(root.rightLink);
		
		return rightRotate(root);
	}
	
	@Override
	public void getAvlTreeData(TreeNode<String> root, List<String> data) {
		
	}
	
	int height(TreeNode<String> N) {
        if (N == null){
            return 0;
        }
        else{
        	return N.height;
        }
    }
	
	 int getBalance(TreeNode<String> N) {
	        if (N == null)
	            return 0;
	        else
	        {
	        	return height(N.rightLink) - height(N.leftLink);
	        }
	    }

}
