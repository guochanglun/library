package com.gcl.bean;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Client {
	
	// cookieStore
	public static BasicCookieStore cookieStore = new BasicCookieStore();
	// 统一客户端
	public static CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
	
	// 首页地址
	public static String indexUrl = "http://lib.sdut.edu.cn/";
	
	// 登录地址
	public static String loginUrl = "http://222.206.65.12/reader/redr_verify.php";
	
	// 查看借阅信息
	public static String borrowUrl = "http://222.206.65.12/reader/book_lst.php";
	
	// 续借连接
	public static String renewUrl = "http://222.206.65.12/reader/ajax_renew.php";
	
	// 查找书籍
	public static String searchUrl = "http://222.206.65.12/opac/openlink.php";
	
	
}
