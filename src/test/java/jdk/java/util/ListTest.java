package jdk.java.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDK8 List api 테스트 유닛
 * 
 * @since 2018-12-28
 * @author fixalot
 */
public class ListTest {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ListTest.class);

	/**
	 * 리스트의 "d"를 찾아 "b"앞에 끼워넣기
	 * 
	 * @author fixalot
	 */
	@Test
	public void changeOrder1() {
		ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "f"));
		Assert.assertEquals("[a, b, c, d, f]", (String.valueOf(list)));
		
//		Collections.rotate(list, 2); // 이거 아님
//		Assert.assertEquals("[d, f, a, b, c]", (String.valueOf(list)));
		
		// 일단 찾고
		int index = list.indexOf("d");
		Assert.assertEquals(3, index);
		
		String target = list.get(3);
		list.remove(3);
		list.add(1, target);
		Assert.assertEquals("[a, d, b, c, f]", String.valueOf(list));
	}
}
