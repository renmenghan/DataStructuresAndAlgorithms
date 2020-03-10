package com.rmh;

import java.util.HashMap;

public class Trie<V> {
	
	private int size;
	private Node<V> root;
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void clear() {
		size = 0;
		root = null;;
	}
	
	public V get(String key) {
		Node<V> node = node(key);
		return node != null && node.word ? node.value : null;
	}
	
	public boolean contains(String key) {
		Node<V> node = node(key);
		return node != null && node.word;
	}
	
	public V add(String key,V value) {
		keyCheck(key);
		//
		if (root == null) {
			root = new Node<>();
		}
		Node<V> node = root;
		int length = key.length();
		for (int i = 0; i < length; i++) {
			char c = key.charAt(i);
			boolean emptyChildren = node.children == null ;
			Node<V> childNode =emptyChildren ? null : node.children.get(c);
			if (childNode == null) {
				childNode = new Node<>();
				node.children = emptyChildren ? new HashMap<>() :node.children;
				node.children.put(c, childNode);
			}
			node = childNode;
		}
		
		
		// 覆盖；
		if (node.word) {
			V oldValue = node.value;
			node.value = value;
			return oldValue;
		}
		
		node.word = true;
		node.value = value;
		size++;
		return null;
	}
	
	public V remove(String key) {
		return null;
	}
	
	public boolean startsWith(String prefix) {
		return node(prefix) != null;
	}
	
	private Node<V> node(String key){

		keyCheck(key);
		Node<V> node = root;
		int length = key.length();
		for (int i = 0; i < length; i++) {
			if (node == null || node.children == null || node.children.isEmpty()) {
				return null;
			}
			char c = key.charAt(i);
			node = node.children.get(c);
			
		}
		return node;
	}
	
	private void keyCheck(String key) {
		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException("key null");
		}
	}
	
	private static class Node<V>{
		HashMap<Character, Node<V>> children;
		V value;
		boolean word;
		
		
	}
	
	
}
