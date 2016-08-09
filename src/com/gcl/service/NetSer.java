package com.gcl.service;

import java.util.List;

import com.gcl.bean.BorrowBook;

import android.os.AsyncTask;

public class NetSer {

	public List<BorrowBook> getBorrowBook() {
		
		List<BorrowBook> list = null;
		
		new AsyncTask<String, Integer, List<BorrowBook>>() {

			@Override
			protected List<BorrowBook> doInBackground(String... params) {
				return null;
			}
			
			@Override
			protected void onPostExecute(List<BorrowBook> result) {
				//list = result;
			}
		};
		return list;
	}
}
