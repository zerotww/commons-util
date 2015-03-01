package org.tww.sort;

public class Sort {
	/*
	 * 交换位子
	 */

	public void swap(int[] t, int from, int to) {
		int temp = t[from];
		t[from] = t[to];
		t[to] = temp;
	}

	/*
	 * 选择排序法
	 */
	public void optionSort(int[] t) {
		for (int i = 0; i < t.length; i++) {
			int min = i;
			for (int j = i; j < t.length; j++) {
				if (t[j] < t[min]) {
					min = j;
				}
			}
			swap(t, i, min);
		}
	}

	/*
	 * 冒泡法排序
	 */
	public void bubbleSort(int[] t) {
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t.length - i - 1; j++) {
				if (t[j] > t[j + 1])
					swap(t, j, j + 1);
			}
		}
	}

	/*
	 * 插入法排序
	 */
	public void insertSort(int[] t) {
		for (int i = 1; i < t.length; i++) {
			int num = t[i];
			int j = i;
			while (j > 0 && t[j - 1] > num) {
				t[j] = t[j - 1];
				j--;
			}
			t[j] = num;
		}
	}

	/*
	 * 快速排序法
	 */
	public void quickSort(int[] t, int lo0, int hi0) {
		int lo = lo0;
		int hi = hi0;
		boolean flag = true;
		if (lo >= hi) {
			return;
		}
		while (lo != hi) {
			if (t[lo] > t[hi]) {
				swap(t, lo, hi);
				flag = !flag;
			}
			if (flag) {
				hi--;
			} else {
				lo++;
			}
		}
		lo--;
		hi++;
		quickSort(t, lo0, lo);
		quickSort(t, hi, hi0);
	}

}
