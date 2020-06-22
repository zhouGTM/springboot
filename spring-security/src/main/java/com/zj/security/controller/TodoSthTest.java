package com.zj.security.controller;


import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class TodoSthTest {
	
	public static List<Person> arrays = Arrays.asList(new Person("zhou", 35, "Bj"), new Person("zhou", 35, "Bj"),
			new Person("zhou", 35, "Bj"));
	private static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	public static void main(String[] args) {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				System.out.println("您好！");
//			}
//		}).start();
//		new Thread(() -> System.out.println("----------")).start();
//		
//		Runnable r1 = () -> System.out.println("helloword!");
//		r1.run();
//
//		Comparable<Integer> com = (a) -> a;
//		int to = com.compareTo(3);
//		System.out.println(to);
//
//		new TreeSet<Integer>((a, b) -> a - b);
//
//		List<Person> sts = filterStrDo(arrays, new FilterStrPredicate());
//		sts.forEach(s -> System.out.println(s.getAddress()));
		

        String input = "123456";
        System.out.println("MD5加密" + "\n"
                         + "明文：" + input + "\n"
                         + "无盐密文：" + md5WithOutSalt(input));
        System.out.println("带盐密文：" + md5WithSalt(input,0));
		
	}
	
	public static String md5WithOutSalt(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return byte2HexStr(md.digest(inputStr.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	public static String md5WithSalt(String inputStr, int type ){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			String salt = null;
			if (type == 0) {
				salt = salt();
			} else if (type == 1){
				 String queriedHash=null;//从库中查找到的hash值
	             salt=getSaltFromHash(queriedHash);
			}
			
			String inputWithSalt = inputStr + salt;
            String hashResult = byte2HexStr(md.digest(inputWithSalt.getBytes()));//哈希计算,转换输出
            System.out.println("加盐密文："+hashResult);
 
            /*将salt存储到hash值中，用于登陆验证密码时使用相同的盐*/
            char[] cs = new char[48];
            for (int i = 0; i < 48; i += 3) {
                cs[i] = hashResult.charAt(i / 3 * 2);
                cs[i + 1] = salt.charAt(i / 3);//输出带盐，存储盐到hash值中;每两个hash字符中间插入一个盐字符
                cs[i + 2] = hashResult.charAt(i / 3 * 2 + 1);
            }
            hashResult = new String(cs);
            return hashResult;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	
	public static String salt() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(16);
		for (int i = 0; i < sb.capacity(); i++) {
			sb.append(hex[random.nextInt(16)]);
		}
		return sb.toString();
	}

	private static String getSaltFromHash(String queriedHash) {
        StringBuilder sb = new StringBuilder();
        char [] h = queriedHash.toCharArray();
        for(int i=0; i<queriedHash.length(); i+=3){
            sb.append(h[i+1]);
        }
        return sb.toString();

	}

	private static String byte2HexStr(byte[] digest) {
		int len = digest.length;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < len; i++) {
			byte bit = digest[i];
			result.append(hex[bit >>> 4 & 0xf]);
			result.append(bit & 0xf);
		}
		return result.toString();
	}

	@Test
	public void toList() {
//		List<String> list = Stream.of("a", "b", "c").collect(Collectors.toList());
//		assertEquals(Arrays.asList("a", "b", "c"), list);
		
//		List<String> collect = Stream.of("a", "b", "c").map(str -> str.toUpperCase()).collect(Collectors.toList());
//		assertEquals(Arrays.asList("A", "B", "C"), collect);
		
//		List<String> collect = Stream.of("a1", "012b", "sasdas").filter(str -> Character.isDigit(str.charAt(0))).collect(Collectors.toList());
//		assertEquals(Arrays.asList("012b"), collect);
		
		List<String> collect2 = Stream.of("zhangsan", "lisi", "wangwu").filter(str -> str.equals("wangwu")).collect(Collectors.toList());
		System.out.println(collect2);
	}
	
	public void test5() {
		List<Person> filterStrDo = filterStrDo(arrays, new MyPredicate<Person>() {
			
			@Override
			public boolean isCorrect(Person t) {
				return t.getAddress() == "Bj";
			}
		});
		
		for (Person person : filterStrDo) {
			System.out.println(person);
		}
	}
	
	public void test6() {
		List<Person> filterStrDo = filterStrDo(arrays, (e) -> e.getAddress() == "Bj");
		filterStrDo.forEach(System.out::println);
		filterStrDo.forEach(p -> System.out.println(p.getAddress()));
	}
	
	@Test
	public void test7() { 
		List<String> collect = Stream.of("a", "b", "c").collect(Collectors.toList());
		collect.forEach(System.out::println);
	}

	private static List<Person> filterStrDo(List<Person> ps, MyPredicate<Person> myPredicate) {
		List<Person> persons = new ArrayList<Person>();
		for (Person p : ps) {
			if (myPredicate.isCorrect(p)) {
				persons.add(p);
			}
		}

		return persons;
	}		
}
