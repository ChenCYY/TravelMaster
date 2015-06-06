package org.nku.travelmaster.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.nku.travelmaster.activity.R;
import org.nku.travelmaster.internet.WebAccessUtils;
import org.nku.travelmaster.po.Attractions;
import org.nku.travelmaster.po.upost;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ForumFragment extends Fragment {
	private List<Map<String, ?>> lstData;
	private ListView lstBlogs;
	private int attsum;
	private Button btn_sendnewpost;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.forum_layout, container,
				false);
		lstBlogs=(ListView) newsLayout.findViewById(R.id.listBlogs);
		btn_sendnewpost=(Button)newsLayout.findViewById(R.id.btn_sendnewpost);
		this.lstData = fetchData();
		
		 SimpleAdapter adapter = new SimpleAdapter(getActivity(), this.lstData,
					R.layout.blog, new String[] { "txtHeader","txtContents","txtUsers","txtTime","txtReplynum"}, new int[] {R.id.txtHeader,R.id.txtContents,R.id.txtUsers,R.id.txtTime,R.id.txtReplynum});
		 this.lstBlogs.setOnItemClickListener(new ItemOcl());
		 lstBlogs.setAdapter(adapter);
		 btn_sendnewpost.setOnClickListener(new Viewocl());
		return newsLayout;
	}
	
	private class Viewocl implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(ForumFragment.this.getActivity(), SendNewPostActivity.class);
			startActivity(intent);
		}

}
	 private List<Map<String, ?>> fetchData() {
	    	// TODO Auto-generated method stub
			// 步骤4-1：创建一个空集合对象
			List<Map<String, ?>> lst = new ArrayList<Map<String, ?>>();
			// 步骤4-2：创建一个列表中选项对象并实例化
			
			String response = WebAccessUtils.httpRequest("ADShowPostsServlet");
			
			Type ListPost = new TypeToken<ArrayList<upost>>() {}.getType();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
			List<upost> lstPosts = gson.fromJson(response, ListPost);
			
			for (upost posts : lstPosts) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("mid", posts.getPid());
				item.put("txtHeader", posts.getPtitle());
				item.put("txtContents", posts.getContents());
				item.put("txtUsers", posts.getUname());
				item.put("txtTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA).format(posts.getPostdate()));
				item.put("txtReplynum", posts.getReplynum());
				
				lst.add(item);
				
			}
			attsum=lst.size();
			System.out.println("帖子总数：> "+ attsum);
			return lst;

	}
	 private class ItemOcl implements AdapterView.OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				// 步骤6-1：使用该方法的position参数获取选中的选项对象并赋值到Map集合中
				Map<String, ?> selectedItem = lstData.get(position);
				// 测试
				Toast.makeText(getActivity(), "您选中的是编号为:"+selectedItem.get("mid"), Toast.LENGTH_LONG).show();
				int pid=(Integer) selectedItem.get("mid");
				Intent intent = new Intent();
				intent.setClass(ForumFragment.this.getActivity(), ShowPostActivity.class);
				System.out.println("ForumActivity:Pid="+pid);
				
				intent.putExtra("pid", Integer.toString(pid));
				startActivity(intent);
			}
			
		}

}
