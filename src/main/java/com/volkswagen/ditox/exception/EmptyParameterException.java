package com.volkswagen.ditox.exception;

import org.apache.maven.plugin.MojoFailureException;

public class EmptyParameterException extends MojoFailureException {
	public EmptyParameterException(String message) {
		super(message);
	}
}
