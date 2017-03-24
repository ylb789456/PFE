package fr.polytech.view;

import java.util.HashMap;
import java.util.Map;

public class TestHashMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> map1 = new HashMap<String,Integer>();
		map1.put("a" , 1);
		map1.put("b" , 2);
		 
		HashMap<String, Integer> map2 = new HashMap<String,Integer>();
		map2=new HashMap<String,Integer>(map1);
		map1.put("c" , 3);
		map1.put("d", 10); 
		System.out.println(map1);
		System.out.println(map2);
	}

}
