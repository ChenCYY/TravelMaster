package org.nku.travelmaster.activity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;



import org.nku.travelmaster.activity.R;

import android.util.Xml;
public class NewsXmlParser {
	// 新闻列表
	// 滑动图片的集合，这里设置成了固定加载，当然也可动态加载�?
	private int[] slideImages = {
			R.drawable.image01,
			R.drawable.image02,
			R.drawable.image03,
			R.drawable.image04,
			R.drawable.image05};
	
	// 滑动标题的集�?
	private int[] slideTitles = {
			R.string.title1,
			R.string.title2,
			R.string.title3,
			R.string.title4,
			R.string.title5,
	};
	
	// 滑动链接的集�?
	private String[] slideUrls = {
			"http://mobile.csdn.net/a/20120616/2806676.html",
			"http://cloud.csdn.net/a/20120614/2806646.html",
			"http://mobile.csdn.net/a/20120613/2806603.html",
			"http://news.csdn.net/a/20120612/2806565.html",
			"http://mobile.csdn.net/a/20120615/2806659.html",
	};
	
	public int[] getSlideImages(){
		return slideImages;
	}
	
	public int[] getSlideTitles(){
		return slideTitles;
	}
	
	public String[] getSlideUrls(){
		return slideUrls;
	}
	
	/**
	 * 获取XmlPullParser对象
	 * @param result
	 * @return
	 */
}
