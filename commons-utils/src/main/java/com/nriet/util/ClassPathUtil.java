package com.nriet.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ClassPathUtil {

	public static File getFileFromClassPath(String filename) {
		Resource resource = new ClassPathResource(filename);
		try {
			return resource.getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static InputStream getInputStreamFromClassPath(String filename) {
		Resource resource = new ClassPathResource(filename);
		try {
			return resource.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
