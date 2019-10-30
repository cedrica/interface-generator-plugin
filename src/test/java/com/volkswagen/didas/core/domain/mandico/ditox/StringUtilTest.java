package com.volkswagen.didas.core.domain.mandico.ditox;

import org.codehaus.plexus.util.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class StringUtilTest {

	private static final String USER_DIR = "user.dir";
	@Test public void changeFirstCharakterToUpperCase() {
		Assert.assertEquals("IchBinDoff",StringUtil.changeFirstCharakterToUpperCase("ichBinDoff"));
	}

	@Test public void writeDataToOutput() throws IOException {
		StringUtil.writeDataToOutput("test", System.getProperty(USER_DIR),"test.java");
		Assert.assertTrue(FileUtils.fileExists(System.getProperty(USER_DIR)+"\\test.java"));
		String content = FileUtils.fileRead(System.getProperty(USER_DIR)+"\\test.java");
		assertEquals("test", content);
	}

	@Test public void convertClassIntoInterfaceStr() {
		String expected = "package com.volkswagen.didas.core.domain.mandico.ditox;\nimport java.util.List;\npublic interface IDiToXPropertyTypes {\n\t"
				+ "public void setAge(int age);\n\tpublic int getAge();\n\tpublic void setName(String name);\n\tpublic String getName();\n}";
		String actual = StringUtil.convertClassIntoInterfaceStr(TestForTest.class);
		Assert.assertEquals(expected,actual);
	}

	class TestForTest{
		private int age;
		private String name;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}