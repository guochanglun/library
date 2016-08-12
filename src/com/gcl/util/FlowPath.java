package com.gcl.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import com.gcl.bean.Client;
import com.gcl.form.Forms;

public class FlowPath {

	/**
	 * 登录到系统
	 */
	public static String login(String loginuser, String passwd) {
		HttpPost post = new HttpPost(Client.loginUrl);

		String result = null;
		try {
			UrlEncodedFormEntity entity = Forms.login().setNumber(loginuser)
					.setPasswd(passwd).build();

			post.setEntity(entity);
			CloseableHttpResponse response = Client.client.execute(post);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
			response.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取借阅的书的信息
	 */
	public static String getBorrowedBook() {
		HttpGet get = new HttpGet(Client.borrowUrl);
		get.setHeader("Referer",
				"http://222.206.65.12/reader/redr_cust_result.php");
		String result = null;
		try {
			CloseableHttpResponse response = Client.client.execute(get);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 搜索书籍
	 */
	public static String search(String bookname)
			throws ClientProtocolException, IOException {
		String param = "?historyCount=1" + "&strSearchType=title"
				+ "&match_flag=forward" + "&showmode=list" + "&displaypg=100"
				+ "&sort=M_PUB_YEAR" + "&orderby=desc" + "&doctype=ALL"
				+ "&strText=" + bookname;

		HttpGet get = new HttpGet(Client.searchUrl + param);

		CloseableHttpResponse response = Client.client.execute(get);

		String result = EntityUtils.toString(response.getEntity(), "utf-8");

		return result;
	}

	/**
	 * 续借书籍
	 */
	public static String continueBorrow(String id) {
		String url = Client.renewUrl + "?bar_code=" + id + "&time="
				+ new Date().getTime();
		HttpGet get = new HttpGet(url);
		String result = null;
		try {
			CloseableHttpResponse response = Client.client.execute(get);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
