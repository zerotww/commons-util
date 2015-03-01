package com.nriet.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class TextUtil {

	public static void main(String[] args) {
		File file = new File("C:/Documents and Settings/Administrator/桌面/chem.sh");
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> result = new ArrayList<String>();
		for (String string : lines) {
			if (!result.contains(string)) {
				result.add(string);
			}
		}
		File newFile = new File("C:/Documents and Settings/Administrator/桌面/new_chem.sh");
		try {
			FileUtils.writeLines(newFile, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
