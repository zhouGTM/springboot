package com.zj.security.controller;

public class FilterStrPredicate implements MyPredicate<Person>{

	@Override
	public boolean isCorrect(Person t) {
		return t.getAge() >= 18;
	}

}
