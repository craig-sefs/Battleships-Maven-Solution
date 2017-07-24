package com.fm.tdd.utils;

public abstract class Assert {

	private Assert() {

	}

	public static void notBlank(String s, String message) {
		notNull(s, message);

		if (s.trim().length() < 1) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notNull(Object s, String message) {
		if (null == s) {
			throw new IllegalArgumentException(message);
		}
	}

}
