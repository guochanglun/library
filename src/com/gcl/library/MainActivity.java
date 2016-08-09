package com.gcl.library;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.library.R;
import com.gcl.bean.BorrowBook;
import com.gcl.service.HtmlSer;
import com.gcl.util.FlowPath;

public class MainActivity extends Activity {

	private TextView title;
	private ImageView left;
	private ImageView right;

	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		title = (TextView) findViewById(R.id.title);
		left = (ImageView) findViewById(R.id.left);
		right = (ImageView) findViewById(R.id.right);
		lv = (ListView) findViewById(R.id.borrow);

		title.setText("借阅书籍");
		left.setVisibility(View.INVISIBLE);
		right.setImageResource(R.drawable.search);
		
		right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, SearchActivity.class));
			}
		});
		
		// 登录系统
		new AsyncTask<String, Integer, String>(){

			@Override
			protected String doInBackground(String... params) {
				try {
					FlowPath.login("14110501053", "454110");
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return "";
			}
			
		}.execute("");
		
		// 获取数据
		new AsyncTask<String, Integer, List<BorrowBook>>() {

			@Override
			protected List<BorrowBook> doInBackground(String... params) {
				return HtmlSer.getBorrowedBook();
			}
			
			@Override
			protected void onPostExecute(List<BorrowBook> result) {
				lv.setAdapter(new BorrowAdapter(result));
			}
		}.execute("");
	}

	class BorrowAdapter extends BaseAdapter {

		private List<BorrowBook> list;

		public BorrowAdapter(List<BorrowBook> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();

				convertView = View.inflate(MainActivity.this,R.layout.borrow_item, null);
				holder.order = (TextView) convertView.findViewById(R.id.order);
				holder.name = (TextView) convertView.findViewById(R.id.book_name);
				holder.in = (TextView) convertView.findViewById(R.id.book_in);
				holder.out = (TextView) convertView.findViewById(R.id.book_out);
				holder.renew = (Button) convertView.findViewById(R.id.renew);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final BorrowBook bBook = list.get(position);
			holder.order.setText((position+1) + "");
			holder.in.setText("应还日期" + bBook.getReturnDate());
			holder.out.setText("借阅日期" + bBook.getBorrowDate());
			holder.name.setText(bBook.getBookName());
			holder.renew.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//
				}
			});
			return convertView;
		}

	}

	class ViewHolder {
		TextView name;
		TextView order;
		TextView in;
		TextView out;
		Button renew;
	}
}
