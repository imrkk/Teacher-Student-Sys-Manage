package com.stu_teach.manage.exception;

@SuppressWarnings("serial")
public class ManageException extends RuntimeException{
	
	public ManageException() {
		super();
	}

	public ManageException(String message) {
		super(message);
	}

	public ManageException(String message, Throwable e) {
		super(message, e);
	}


}