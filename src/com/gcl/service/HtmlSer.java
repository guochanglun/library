package com.gcl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gcl.bean.Book;
import com.gcl.bean.BorrowBook;
import com.gcl.util.FlowPath;

/**
 * 解析html, 返回需要的数据
 */
public class HtmlSer {

	/**
	 * 获取已经借过的书
	 */
	public static List<BorrowBook> getBorrowedBook() {

		String html = FlowPath.getBorrowedBook();
		List<BorrowBook> list = new ArrayList<>();

		Document doc = Jsoup.parse(html);
		Element ele = doc.select("table").get(0);
		Elements trEles = ele.getElementsByTag("tr");

		for (int i = 1; i < trEles.size(); i++) {
			Elements tdEles = trEles.get(i).getElementsByTag("td");
			BorrowBook bb = new BorrowBook();
			bb.setBookName(tdEles.get(1).text());
			bb.setBorrowDate(tdEles.get(3).text());
			bb.setReturnDate(tdEles.get(4).text());
			bb.setNum(tdEles.get(0).text());
			list.add(bb);
		}
		return list;
	}

	/**
	 * 得到搜索的书
	 */
	public static List<Book> getSearchBook(String bookname) {

		String html = null;
		try {
			html = FlowPath.search(bookname);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Book> list = new ArrayList<>();
		if(html == null ) return null;
		Document doc = Jsoup.parse(html);
		Elements eles = doc.select(".list_books");
		Book b = null;

		for (Element ele : eles) {
			b = new Book();
			Element h3 = ele.select("h3").get(0);
			Element p = ele.select("p").get(0);

			// 得到书名
			String bookName = h3.select("a").text();
			b.setBookName(bookName);

			// 得到编号
			h3.select("a, span").remove();
			String num = h3.text();
			b.setNum(num);

			// 得到书本个数
			String[] bb = p.select("span").get(0).text().split(" ");
			b.setCount(bb[0]);
			b.setAvailable(bb[1]);

			// 出版信息
			p.select("span").remove();
			String[] span = p.text().split(" ");
			int t = span.length;
			String author = "";
			for (int i = 0; i < t - 1; i++) {
				author += span[i];
			}
			b.setAuthor(author);
			b.setPublishInfo(span[t - 1]);

			list.add(b);
		}

		return list;
	}
}
