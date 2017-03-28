package com.wise.core.jutils.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.ArrayUtils;

public class LNUtils {
	
	/**
	 * 检测是否为空字符串或为null
	 * 
	 * @param str
	 *            待检测的字符串
	 * @return 若str为null或空字符串则返回true; 否则返加false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() <= 0;
	}
	

	/**
	 * 检测是否为空格串、空字符串或null
	 * 
	 * @param str
	 *            待检测的字符串
	 * @return 若str为null或空字符串则或空格串返回false; 否则返加true
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	/**
	 * 检测是否为空格串、空字符串或null
	 * 
	 * @param str
	 *            待检测的字符串
	 * @return 若str为null或空字符串则或空格串返回true; 否则返加false
	 */
	public static boolean isBlank(String str) {
		if (isEmpty(str)) {
			return true;
		}

		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}

		return true;
	}
	

	public static List<Integer> toIntegerList(String str, String regex) {
		StringTokenizer t = new StringTokenizer(str, regex);
		List<Integer> l = new ArrayList<Integer>();
		while (t.hasMoreElements()) {
			String s = t.nextToken();
			if (isNotBlank(s)) {
				l.add(Integer.parseInt(s));
			}
		}
		return l;
	}

	public static Integer[] toIntegerArray(String str, String regex) {
		List<Integer> list = toIntegerList(str, regex);
		Integer[] arr = new Integer[list.size()];
		return list.toArray(arr);
	}

	/**
	 * 统计arr数组中包含e元素的个数
	 * 
	 * @param arr
	 * @param e
	 * @return
	 */
	public static int countElements(Object[] arr, Object e) {
		int count = 0;
		for (Object o : arr) {
			if (o.equals(e)) {
				count++;
			}
		}
		return count;
	}

	public static Object[] sort(Object[] arr) {
		Arrays.sort(arr);
		return arr;
	}

	/**
	 * 检查one数组是否包含two数组所有元素
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean containsAll(Object[] one, Object[] two) {
		for (Object b : two) {
			if (!ArrayUtils.contains(one, b)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查数组中是否有相同元素
	 * 
	 * @param array
	 * @return
	 */
	public static boolean duplicate(Object[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i].equals(array[j])) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean duplicate(Object[] one, Object[] two) {
		for (Object a : one) {
			for (Object b : two) {
				if (a.equals(b)) {
					return true;
				}
			}
		}
		return false;
	}

	public static Object[] intersect(Object[] one, Object[] two) {
		Set<Object> a = new HashSet(Arrays.asList(one));
		Set<Object> b = new HashSet(Arrays.asList(two));
		a.retainAll(b);
		return a.toArray();
	}

	public static String join(Object[] array, String str) {
		StringBuffer sb = new StringBuffer();
		for (Object element : array) {
			sb.append(element).append(str);
		}
		sb.delete(sb.length() - str.length(), sb.length());
		return sb.toString();
	}

	public static List<String> partition(String str, int partition) {
		List<String> parts = new ArrayList<String>();
		int len = str.length();
		for (int i = 0; i < len; i += partition) {
			parts.add(str.substring(i, Math.min(len, i + partition)));
		}
		return parts;
	}

	public static int combination(int m, int n) {
		if (n == 0 || m == 0) {
			return 1;
		}
		if (n > m) {
			return 0;
		}
		if (n > m / 2) {
			n = m - n;
		}
		double a = 0;
		for (int i = m; i >= (m - n + 1); i--) {
			a += Math.log(i);
		}
		for (int i = n; i >= 1; i--) {
			a -= Math.log(i);
		}
		a = Math.exp(a);
		return (int) Math.round(a);
	}

	public static int getCombinCount(int a, int d) {
		if (d > a) {
			return 0;
		}
		if (a == d || d == 0) {
			return 1;
		}
		if (d == 1) {
			return a;
		}
		int b = 1;
		int e = 1;
		for (int c = 0; c < d; c++) {
			b *= a - c;
			e *= d - c;
		}
		return b / e;
	}

	/**
	 * 快速排序法
	 * 
	 * @param e
	 *            排序的数组
	 * @param first
	 *            排序的第一位(从0开始)
	 * @param end
	 *            排序的最后一位(总位数-1)
	 */
	public static void QuickSort(int e[], int first, int end) {
		int i = first, j = end, temp = e[first];
		while (i < j) {
			while (i < j && e[j] >= temp)
				j--;
			e[i] = e[j];
			while (i < j && e[i] <= temp)
				i++;
			e[j] = e[i];
		}
		e[i] = temp;
		if (first < i - 1)
			QuickSort(e, first, i - 1);
		if (end > i + 1)
			QuickSort(e, i + 1, end);
	}

	/**
	 * 字符串数组转换成int数组
	 * 
	 * @param numbers
	 *            字符串数组
	 * @param num
	 *            取多少位
	 * @return
	 */
	public static int[] StringArrayToIntArray(String[] numbers, int num) {
		num = numbers.length < num ? numbers.length : num;
		int[] ia = new int[num];
		for (int i = 0; i < num; i++) {
			ia[i] = Integer.parseInt(numbers[i]);
		}
		return ia;
	}

	/**
	 * 补全传入整数的位数
	 * 
	 * @return
	 */
	public static String CompletionCount(int _Num, int _Cont) {
		int _Length = (_Num + "").length();
		String _TemNum = "";
		for (int i = _Length; i < _Cont; i++) {
			_TemNum += "0";
		}
		return _TemNum + _Num;
	}

	public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
		int listSize = list.size(); // list的大小
		int page = (listSize + (pageSize - 1)) / pageSize; // 页数
		List<List<T>> listArray = new ArrayList<List<T>>(); // 创建list数组,用来保存分割后的list
		for (int i = 0; i < page; i++) { // 按照数组大小遍历
			List<T> subList = new ArrayList<T>(); // 数组每一位放入一个分割后的list
			for (int j = 0; j < listSize; j++) { // 遍历待分割的list
				int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;// 当前记录的页码(第几页)
				if (pageIndex == (i + 1)) { // 当前记录的页码等于要放入的页码时
					subList.add(list.get(j)); // 放入list中的元素到分割后的list(subList)
				}
				if ((j + 1) == ((j + 1) * pageSize)) { // 当放满一页时退出当前循环
					break;
				}
			}
			listArray.add(subList); // 将分割后的list放入对应的数组的位中
		}
		return listArray;
	}
}
