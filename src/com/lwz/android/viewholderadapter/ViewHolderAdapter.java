package com.lwz.android.viewholderadapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * ViewHodler 适配器
 * <p> 该适配器是通过流行的 ViewHolder 来优化 AdapterView 的性能。该适配器使得使用者只关心数据绑定部分而不用去关心如何实现
 * 
 * <p> ViewHolder 适配器，内部已经实现  {@link ViewHolder} 的逻辑，子类只需要简单实现抽象方法即可
 * @author lwz
 *
 * @param <T> List 中的类型
 */
public abstract class ViewHolderAdapter<T> extends BaseAdapter {
	
	Context mContext;
	List<T> mData;
	int mLayoutRes;
	View mCurrentConvertView;
	
	/**
	 * 使用该构造方法必须使用  {@link #update(Collection)} 或者 {@link #append(Collection)} 方法来更新数据
	 * @param context
	 * @param layoutRes
	 */
	public ViewHolderAdapter(Context context, int layoutRes) {
		this(context, new ArrayList<T>(), layoutRes);
	}
	
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
	
	public List<T> getData() {
		return mData;
	}
	
	/**
	 * 用新数据替换所有的旧数据
	 * <p>NOTE:数据源的指向并没有改变, 只是将数据源的数据{@link List #clear()}再 {@link List #addAll(Collection)}
	 * @param newData
	 */
	public synchronized void update(Collection<? extends T> newData) {
		mData.clear();
		if( newData != null ) {
			mData.addAll(newData);
		}
		notifyDataSetChanged();
	}
	
	/**
	 * 直接将源数据替换，将新数据的指向设置给适配器
	 * @param newData
	 */
	public void replaceOriginData(List<T> newData) {
		mData = (List<T>) newData;
		notifyDataSetChanged();
	}
	
	
	/**
	 * 在原有数据的基础上再添加数据
	 * <p>NOTE:数据源的指向并没有改变，只是在原有数据源的基础上添加数据
	 * @param appendData
	 */
	public synchronized void append(Collection<? extends T> appendData) {
		if( appendData == null || appendData.isEmpty() ) {
			return;
		}
		mData.addAll(appendData);
		notifyDataSetChanged();
	}
	
	/**
	 * 添加一个数据
	 * @param item
	 */
	public synchronized void add(T item) {
		mData.add(item);
		notifyDataSetChanged();
	}
	
	/**
	 * 清空所有数据
	 */
	public void clear() {
		mData.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if( convertView == null ) {
			convertView = View.inflate(mContext, mLayoutRes, null);
		}
		mCurrentConvertView = convertView;
		bindData(position, convertView, getItem(position));
		bindData(position, getItem(position));
		return convertView;
	}
	
	@Deprecated
	protected void bindData(int pos, View convertView, T itemData) {
		
	}
	
	/**
	 * 内部应使用 {@link #getViewFromHolder(int)} 来得到 View 从而绑定适配器
	 * @param pos
	 * @param itemData
	 */
	abstract protected void bindData(int pos, T itemData);
	
	@Deprecated
	public <K extends View> K getViewFromHolder( View convertView, int id ) {
		return ViewHolder.getView(convertView, id);
	}
	
	public <K extends View> K getViewFromHolder(int id ) {
		return ViewHolder.getView(mCurrentConvertView, id);
	}
}
