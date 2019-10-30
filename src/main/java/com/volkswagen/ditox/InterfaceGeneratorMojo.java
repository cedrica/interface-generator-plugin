package com.volkswagen.ditox;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author Cedric Leumaleu
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class InterfaceGeneratorMojo extends AbstractMojo {
	@Parameter private String outputDir;
	@Parameter private String outputFileName;
	@Parameter private String inputClass;

	@Override
	public void execute() {
		getLog().info("Interface convertion of the class: " + inputClass);
		try {
			Class clazz = Class.forName(inputClass);
			String interfaceAsString = StringUtil.convertClassIntoInterfaceStr(clazz);
			StringUtil.writeDataToOutput(interfaceAsString, outputDir, outputFileName);
		} catch (ClassNotFoundException e) {
			getLog().error("Error while instanciing "  + inputClass );
		}
		getLog().info("Convertion successfully done");
	}


}
