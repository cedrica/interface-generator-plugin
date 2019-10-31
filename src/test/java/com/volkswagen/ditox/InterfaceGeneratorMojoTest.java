package com.volkswagen.ditox;

import com.volkswagen.ditox.exception.EmptyParameterException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtil.class, Files.class, FileUtils.class, Class.class})
public class InterfaceGeneratorMojoTest {

	@Test
	public void testExecute() throws Exception {
		// configuration
		mockStatic(StringUtil.class);
		mockStatic(Files.class);
		mockStatic(FileUtils.class);
		mockStatic(Class.class);
		mockStatic(Log.class);

		when(Class.forName(Matchers.anyString())).thenCallRealMethod();
		when(StringUtil.convertClassIntoInterfaceStr(Matchers.any())).thenReturn("test");
		Path path = Paths.get(System.getProperty("user.dir")+"\\test-dir");
		// mock static void method
		doNothing().when(StringUtil.class, "writeDataToOutput",Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
		when(Files.createDirectories(Paths.get(System.getProperty("user.dir")+"\\test-dir"))).thenReturn(path);
		when(Files.createFile(Paths.get(path.getFileName()+"\\test.java"))).thenReturn(path);

		// use
		new InterfaceGeneratorMojo().setInputClass("java.lang.Object").setOutputDir(System.getProperty("user.dir")+"\\test-dir").setOutputFileName("test").execute();

		// check if what i configure is done as desired
		Assert.assertEquals("test",StringUtil.convertClassIntoInterfaceStr(Object.class));
	}



}
