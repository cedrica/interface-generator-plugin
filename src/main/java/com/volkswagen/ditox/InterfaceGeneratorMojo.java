package com.volkswagen.ditox;

import com.volkswagen.ditox.exception.EmptyParameterException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author Cedric Leumaleu
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class InterfaceGeneratorMojo extends AbstractMojo {
	@Parameter(required = true) private String outputDir;
	@Parameter(required = true) private String outputFileName;
	@Parameter(required = true) private String inputClass;

	@Override
	public void execute() throws EmptyParameterException {
		if(inputClass == null || outputDir == null || outputFileName == null)
			throw new EmptyParameterException("All parameter are required in oder for the plugin to work");

		getLog().info("Interface convertion of the class: " + inputClass);
		try {
			Class clazz = Class.forName(inputClass);
			String interfaceAsString = StringUtil.convertClassIntoInterfaceStr(clazz);
			StringUtil.writeDataToOutput(interfaceAsString, outputDir, outputFileName);
		} catch (ClassNotFoundException e) {
			getLog().error("Error while instanciing "  + inputClass +": "+e.getMessage());
		}
		getLog().info("Convertion successfully done");
	}


	// generate setter for test
	InterfaceGeneratorMojo setOutputDir(String outputDir) {
		this.outputDir = outputDir;
		return  this;
	}

	InterfaceGeneratorMojo setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
		return this;
	}

	InterfaceGeneratorMojo setInputClass(String inputClass) {
		this.inputClass = inputClass;
		return this;
	}
}
