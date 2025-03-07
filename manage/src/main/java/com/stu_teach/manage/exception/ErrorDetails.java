package com.stu_teach.manage.exception;

import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
 public class ErrorDetails {
	private String code;
	private String message;
	private List<Cause> causes;

	public ErrorDetails(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public void addCause(String value, String label) {
		if (this.getCauses() == null) {
			this.causes = new LinkedList<>();
		}
		this.causes.add(new Cause(value, label));
	}

@Getter
@Setter
	@AllArgsConstructor
	class Cause {
		private String value;
		private String label;
	}
}