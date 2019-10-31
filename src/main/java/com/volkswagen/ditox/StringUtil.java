package com.volkswagen.ditox;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.codehaus.plexus.util.FileUtils;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

class StringUtil {

	private static final Log log = new SystemStreamLog();

	private StringUtil(){}

	static String changeFirstCharakterToUpperCase(String fieldName){
		if(fieldName == null || fieldName.isEmpty())
			return null;
		return String.join("",String.valueOf(Character.toUpperCase(fieldName.charAt(0))), fieldName.substring(1));
	}

	static void writeDataToOutput(String data, String outputDir, String outputFileName) {
		try{
			Files.createDirectories(Paths.get(outputDir));
			if(!FileUtils.fileExists(outputDir+"\\"+outputFileName)){
				Files.createFile(Paths.get(outputDir+"\\"+outputFileName));
			}
			FileUtils.fileWrite(outputDir+"\\"+outputFileName,data);
		} catch (Exception e){
			log.error(e.getMessage());
		}
	}

	static  String convertClassIntoInterfaceStr(Class inputClazz) {
		log.info("input: " + inputClazz);
		Field[] fields = inputClazz.getDeclaredFields();
		StringBuilder data = new StringBuilder().append("package ").append(inputClazz.getPackage().getName()).append(";\n").append("import java.util.List;\n")
				.append("public interface IDiToXPropertyTypes {\n");
		for (Field field : fields) {
			String fieldName = field.getName();
			if(fieldName.startsWith("this")){
				continue;
			}
			Class typeClass = field.getType();
			String typeStr = typeClass.getSimpleName();
			data.append("\tpublic void").append(" set").append(StringUtil.changeFirstCharakterToUpperCase(fieldName)).append("(").append(typeStr).append(" ").append(fieldName).append(");\n")
				.append("\tpublic ").append(typeStr).append(" get").append(StringUtil.changeFirstCharakterToUpperCase(fieldName)).append("();\n");
		}
		data.append("}");
		return data.toString();
	}
}
