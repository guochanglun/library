package com.gcl.library;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.R;
import com.gcl.bean.Book;
import com.gcl.service.HtmlSer;
import com.gcl.util.NetState;
import com.gcl.util.ToastUtil;

public class SearchActivity extends Activity {

	private TextView name;
	private Button go;

	private ListView lv;
	private TextView title;
	private ImageView left;
	
	private ProgressBar bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		title = (TextView) findViewById(R.id.title);
		left = (ImageView) findViewById(R.id.left);

		lv = (ListView) findViewById(R.id.search_list);

		name = (TextView) findViewById(R.id.search_name);
		go = (Button) findViewById(R.id.search_btn);
		bar = (ProgressBar) findViewById(R.id.progress);
		
		title.setText("搜索书籍");
		left.setImageResource(R.drawable.back);

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SearchActivity.this.finish();
			}
		});

		go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!NetState.with(SearchActivity.this).detectNetState()){
					ToastUtil.showMsg(SearchActivity.this, "网络未连接");
					return;
				}
				String nameString = name.getText().toString();
				if (nameString != null && nameString.length() != 0) {
					go.setEnabled(false);
					bar.setVisibility(View.VISIBLE);
					new AsyncTask<String, Integer, List<Book>>() {

						@Override
						protected List<Book> doInBackground(String... params) {
							return HtmlSer.getSearchBook(params[0]);
						}

						protected void onPostExecute(List<Book> result) {
							if(result.size() > 0) {
								lv.setAdapter(new SearchAdapter(result));
							} else {
								Toast.makeText(SearchActivity.this, "木有搜索到...", Toast.LENGTH_SHORT).show();
							}
							bar.setVisibility(View.INVISIBLE);
							name.setText("");
							go.setEnabled(true);
						};

					}.execute(nameString);
				}
			}
		});
	}

	class SearchAdapter extends BaseAdapter {

		private List<Book> list;

		public SearchAdapter(List<Book> list) {
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

				convertView = View.inflate(SearchActivity.this,
						R.layout.search_item, null);
				holder.rest = (TextView) convertView
						.findViewById(R.id.search_rest);
				holder.name = (TextView) convertView
						.findViewById(R.id.search_name);
				holder.author = (TextView) convertView
						.findViewById(R.id.search_author);
				holder.publish = (TextView) convertView
						.findViewById(R.id.search_publish);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final Book book = list.get(position);
			holder.name.setText(book.getBookName());
			holder.rest.setText(book.getAvailable());
			holder.author.setText(book.getAuthor());
			holder.publish.setText(book.getPublishInfo());

			return convertView;
		}

	}

	class ViewHolder {
		TextView name;
		TextView rest;
		TextView author;
		TextView publish;
	}

}
