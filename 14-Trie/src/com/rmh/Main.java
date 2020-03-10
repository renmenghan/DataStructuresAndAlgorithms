package com.rmh;
import com.rmh.Trie;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Trie<Integer> trie = new Trie<>();
		trie.add("cat", 1);
		trie.add("dog", 2);
		trie.add("catalog", 3);
		trie.add("cast", 4);
		trie.add("天天", 5);
		Asserts.test(trie.size() == 5);
		Asserts.test(trie.startsWith("do"));
		Asserts.test(trie.startsWith("c"));
		Asserts.test(trie.startsWith("ca"));
		Asserts.test(trie.startsWith("cat"));
		Asserts.test(trie.startsWith("cata"));
		Asserts.test(!trie.startsWith("hehe"));
		Asserts.test(trie.get("天天") == 5);
//		Asserts.test(trie.remove("cat") == 1);
//		Asserts.test(trie.remove("catalog") == 3);
//		Asserts.test(trie.remove("cast") == 4);
//		Asserts.test(trie.size() == 2);
//		Asserts.test(trie.startsWith("小"));
//		Asserts.test(trie.startsWith("do"));
//		Asserts.test(!trie.startsWith("c"));
	}

}
