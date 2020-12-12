package com.example.cook_book.model;

public class Recipe {
	private String title;
	private String content;

	public Recipe(){}
	public Recipe(String title, String content){

		this.title = title;
		this.content= content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
