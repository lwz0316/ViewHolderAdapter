package com.lwz.android.viewholderadapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * ViewHodler 适配器
 * <p>
 * 该适配器是通过流行的 ViewHolder 来优化 AdapterView 的性能。该适配器使得使用者只关心数据绑定部分而不用去关心如何实现
 * </p>
 * @author lwz
 *
 * @param <T> List 中的类型
 */
public abstract class ViewHolderAdapter<T> extends BaseAdapter {
	
	private Context mContext;
	private List<T> mData;
	private int mLayoutRes;
	
	public ViewHolderAdapter(Context context, List<T> data, int layoutRes) {
		mContext = context;
		mData = data;
		mLayoutRes = layoutRes;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public T getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if( convertView == null ) {
			convertView = View.inflate(mContext, mLayoutRes, null);
		}
		
		bindData(position, convertView, getItem(position));
		
		return convertView;
	}
	
	abstract protected void bindData(int pos, View convertView, T itemData);

	public <K extends View> K getViewFromHolder( View convertView, int id ) {
		return ViewHolder.getView(convertView, id);
	}
}
