package com.twin.gzbox;

import java.util.Date;

public class Shout {

	private String author;
	private Date date;
	private String text;
	
	
	public Shout(String author, Date date, String text) {	
		this.author = author;
		this.date = date;
		this.text = text;
	}


	public String getAuthor() {
		return author;
	}


	public Date getDate() {
		return date;
	}


	public String getText() {
		return text;
	}	
		
	@Override
	public String toString() {
		return date.toLocaleString() + " " + author + " " + text;
	}
	
}
