package com.volkswagen.didas.core.domain.mandico.ditox;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class StringUtilTest {

	@Test public void changeFirstCharakterToUpperCase() {
		Assert.assertEquals("IchBinDoff",StringUtil.changeFirstCharakterToUpperCase("ichBinDoff"));
	}

	@Test public void writeDataToOutput() throws IOException {
		StringUtil.writeDataToOutput("test", System.getProperty("user.dir")+"\\test.java");
		Assert.assertTrue(FileUtils.fileExists(System.getProperty("user.dir")+"\\test.java"));
		String content = FileUtils.fileRead(System.getProperty("user.dir")+"\\test.java");
		assertEquals("test", content);
	}

	@Test public void convertClassIntoInterfaceStr() {
		String expected = "package com.volkswagen.didas.core.domain.mandico.ditox;\nimport java.util.List;\npublic interface IDiToXPropertyTypes {\n\t"
				+ "public void setAge(int age);\n\tpublic void setName(String name);\n}";
		String actual = StringUtil.convertClassIntoInterfaceStr(TestForTest.class);
		Assert.assertEquals(expected,actual);
	}

	class TestForTest{
		private int age;
		private String name;
	}
}