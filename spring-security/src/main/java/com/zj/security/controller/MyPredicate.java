package com.zj.security.controller;

public interface MyPredicate<T>{
	
	public boolean isCorrect(T t);

}
