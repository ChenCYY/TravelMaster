package org.nku.travelmaster.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.nku.travelmaster.internet.WebAccessUtils;
import org.nku.travelmaster.po.upost;
import org.nku.travelmaster.activity.R;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPostActivity extends Activity {

	private List<Map<String, ?>> lstData_Replys;
	private List<Map<String, ?>> FirstData;
	private ListView first;
	private ListView lstReplys;
	
	int pid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_showpost);

		Intent intent = getIntent();
		System.out.println("intentString=" + intent.getStringExtra("pid"));
		this.pid = Integer.parseInt(intent.getStringExtra("pid"));

		this.lstReplys = (ListView) this.findViewById(R.id.lstReplys);
		this.first = (ListView) this.findViewById(R.id.lstfirst);
		this.lstData_Replys = fetchReplyData();
		this.FirstData=fetchFirstData();
				
		TextView tvtitle=(TextView)this.findViewById( R.id.postName);
		tvtitle.setText((String)FirstData.get(0).get("txtTitle"));
		
		//TextView tvname=(TextView)this.findViewById( R.id.txtNickName);
		//tvname.setText((String)FirstData.get(0).get("txtNickName"));
		//TextView tvdate=(TextView)this.findViewById( R.id.txtPublish);
		//tvdate.setText((String)FirstData.get(0).get("txtPublish"));
		//TextView tvcontent=(TextView)this.findViewById( R.id.txtContents);
		//tvcontent.setText((String)FirstData.get(0).get("txtContents"));
		
		Log.d("DUG", "+++"+FirstData);
		SimpleAdapter firstadaper = new SimpleAdapter(this, this.FirstData,
				R.layout.listitem_first, new String[] { "imgPhoto",
						"txtNickName", "txtPublish", "txtContents", 
						 }, new int[] { R.id.imgPhoto,
						R.id.txtNickName, R.id.txtPublish, R.id.txtContents,
						 });
		this.first.setAdapter(firstadaper);
		
		Log.d("DUG", "+++"+lstData_Replys);
		SimpleAdapter adaper = new SimpleAdapter(this, this.lstData_Replys,
				R.layout.listitem_post, new String[] { "txtPhoto",
						"txtNickName", "txtPublish", "txtPosts"
						}, new int[] { R.id.imgPhoto,
						R.id.txtNickName, R.id.txtPublish, R.id.txtPosts});
		this.lstReplys.setAdapter(adaper);
		
		

	}

	/*private List<Map<String, ?>> fetchRereplyData(int pid) {
		// TODO Auto-generated method stub
		List<Map<String, ?>> lst = new ArrayList<Map<String, ?>>();

		// System.out.println(pid);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String pid_data = gson.toJson(pid);

		System.out.println("Activity:Pid=" + pid);
		System.out.println("Activity:pid_data" + pid_data);

		String uri = "http://192.168.191.1:8001/TravelMaster/";
		String webServiceName = "ShowReplyServlet";
		uri += webServiceName;
		System.out.println(uri);

		List<NameValuePair> lstNameValuePairs = new ArrayList<NameValuePair>();
		lstNameValuePairs.add(new BasicNameValuePair("pid_data", pid_data));
		String data = "";
		HttpPost httpRequestByPost = new HttpPost(uri);
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					lstNameValuePairs, HTTP.UTF_8);
			httpRequestByPost.setEntity(entity);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequestByPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				data = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Type ListPost = new TypeToken<ArrayList<upost>>() {
		}.getType();
		Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<upost> lstPosts = gson2.fromJson(data, ListPost);

		for (upost post : lstPosts) {
			Map<String, Object> item = new HashMap<String, Object>();
			String rereply;
			rereply = post.getUname()
					+ post.getContents()
					+ new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
							.format(post.getPostdate());
			item.put("pid", post.getPid());
			item.put("txtRereply", rereply);

			lst.add(item);

		}
		return lst;

	}
*/
	
	private List<Map<String, ?>> fetchFirstData() {
		// TODO Auto-generated method stub
		List<Map<String, ?>> lst = new ArrayList<Map<String, ?>>();

		// System.out.println(pid);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String pid_data = gson.toJson(pid);

		System.out.println("Activity:Pid=" + pid);
		System.out.println("Activity:pid_data" + pid_data);

		//String uri = "http://192.168.245.1:8001/TravelMaster/";
		//String webServiceName = "ShowReplyServlet";
		//uri += webServiceName;
		//System.out.println(uri);

		/*List<NameValuePair> lstNameValuePairs = new ArrayList<NameValuePair>();
		lstNameValuePairs.add(new BasicNameValuePair("pid_data", pid_data));
		
		String response = WebAccessUtils.httpRequest("ShowReplyServlet", lstNameValuePairs);
		*/
		String uri = "http://192.168.191.1:8001/TravelMaster/";
		String webServiceName = "ADShowFirstServlet";
		String parm="?pid_data=";
		uri += webServiceName;
		uri += parm+pid_data;
		System.out.println(uri);

		List<NameValuePair> lstNameValuePairs = new ArrayList<NameValuePair>();
		lstNameValuePairs.add(new BasicNameValuePair("pid_data", pid_data));
		String data = "";
		HttpPost httpRequestByPost = new HttpPost(uri);
		System.out.println("hhhhhhhhhh");
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					lstNameValuePairs, HTTP.UTF_8);
			httpRequestByPost.setEntity(entity);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequestByPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				data = EntityUtils.toString(httpResponse.getEntity());
				System.out.println("First Json Data:> " + data);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Type ListPost = new TypeToken<upost>() {
		}.getType();
		Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		upost First = gson2.fromJson(data, ListPost);

		Map<String, Object> item = new HashMap<String, Object>();
			item.put("pid", First.getPid());
			item.put("imgPhoto", R.drawable.p2);
			item.put("txtNickName", First.getUname());
			item.put("txtPublish", new SimpleDateFormat("yyyy-MM-dd",
					Locale.CHINA).format(First.getPostdate()));
			item.put("txtTitle", First.getPtitle());
			item.put("txtContents", First.getContents());
			Log.d("DUG", First.getPid()+First.getPtitle()+First.getUname()+First.getContents());
			lst.add(item);
		
		return lst;
	}
	private List<Map<String, ?>> fetchReplyData() {
		// TODO Auto-generated method stub
		List<Map<String, ?>> lst = new ArrayList<Map<String, ?>>();

		// System.out.println(pid);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String pid_data = gson.toJson(pid);

		System.out.println("Activity:Pid=" + pid);
		System.out.println("Activity:pid_data" + pid_data);

		//String uri = "http://192.168.245.1:8001/TravelMaster/";
		//String webServiceName = "ShowReplyServlet";
		//uri += webServiceName;
		//System.out.println(uri);

		/*List<NameValuePair> lstNameValuePairs = new ArrayList<NameValuePair>();
		lstNameValuePairs.add(new BasicNameValuePair("pid_data", pid_data));
		
		String response = WebAccessUtils.httpRequest("ShowReplyServlet", lstNameValuePairs);
		*/
		String uri = "http://192.168.191.1:8001/TravelMaster/";
		String webServiceName = "ADShowReplyServlet";
		String parm="?pid_data=";
		uri += webServiceName;
		uri += parm+pid_data;
		System.out.println(uri);

		List<NameValuePair> lstNameValuePairs = new ArrayList<NameValuePair>();
		lstNameValuePairs.add(new BasicNameValuePair("pid_data", pid_data));
		String data = "";
		HttpPost httpRequestByPost = new HttpPost(uri);
		System.out.println("hhhhhhhhhh");
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					lstNameValuePairs, HTTP.UTF_8);
			httpRequestByPost.setEntity(entity);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequestByPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				data = EntityUtils.toString(httpResponse.getEntity());
				System.out.println("Json Data:> " + data);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Type ListPost = new TypeToken<ArrayList<upost>>() {}.getType();
		Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<upost> lstPosts = gson2.fromJson(data, ListPost);
		Log.d("DUG",data);
		for (upost post : lstPosts) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("pid", post.getPid());
			item.put("txtPhoto", R.drawable.p2);
			item.put("txtNickName", post.getUname());
			item.put("txtPublish", new SimpleDateFormat("yyyy-MM-dd",
					Locale.CHINA).format(post.getPostdate()));
			item.put("txtPosts", post.getContents());
			lst.add(item);
		}
		return lst;
	}
	/*private class ItemOcl2 implements AdapterView.OnItemClickListener {

		

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,int position, long arg3) {
			// TODO Auto-generated method stub
			// 步骤6-1：使用该方法的position参数获取选中的选项对象并赋值到Map集合中
			
			Map<String, ?> selectedItem = lstData_Replys.get(position);
			// 测试
			int p=(Integer) selectedItem.get("pid");
			if(p!=pid){
			Toast.makeText(getApplicationContext(), "您选中的是编号为:"+p, Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent();
			intent.setClass(ShowPostActivity.this, ShowReplyActivity.class);
			System.out.println("BlogActivity:Pid="+p);
			
			intent.putExtra("pid", Integer.toString(p));
			startActivity(intent);
			
			}
		}

	}*/

	/*public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.reply_actionbar, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.ab_replypost:
			Intent intent = new Intent();
			intent.setClass(ShowPostActivity.this, ReplyPostActivity.class);
			System.out.println("ShowPostActivity:Pid=" + pid);

			intent.putExtra("pid", Integer.toString(pid));
			startActivity(intent);

			finish();
			break;
		default:
			break;
		}
		return true;
	}*/

}
