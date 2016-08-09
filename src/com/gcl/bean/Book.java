package com.gcl.bean;

/**
 * Bean: 书籍
 * 
 * @author gcl
 * 
 */
public class Book {
	/** 书名 */
	private String bookName;
	/** 书籍编号 */
	private String num;
	/** 馆藏总数 */
	private String count;
	/** 可借总数 */
	private String available;
	/** 作者 */
	private String author;
	/** 出版信息：出版社和出版日期 */
	private String publishInfo;

	public Book() {

	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishInfo() {
		return publishInfo;
	}

	public void setPublishInfo(String publishInfo) {
		this.publishInfo = publishInfo;
	}

	@Override
	public String toString() {
		return "Book [bookName=" + bookName + ", num=" + num + ", count="
				+ count + ", available=" + available + ", author=" + author
				+ ", publishInfo=" + publishInfo + "]";
	}
}
