package com.lezyo.fsm.activity.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ListAdapter;

import com.lezyo.fsm.R;
import com.tongfang.teacher.customview.swipemenu.SwipeMenuLayout;
import com.tongfang.teacher.customview.swipemenu.SwipeMenuListView;

public class IPullToRefreshSwipeListView extends PullToRefreshSwipeView {
	private final int MIN_DATA_SEIZE = 10;
	private Context context;
    private boolean  footRefreshing;
    private FooterRefreshListener footerFreshListener;
    private View footView;
    private LayoutInflater layoutInflater;
    private SwipeMenuListView listView;
	public IPullToRefreshSwipeListView(Context context) {
		super(context);
		init( context );
	}

	public IPullToRefreshSwipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init( context );
	}

	public IPullToRefreshSwipeListView(Context context, Mode mode) {
		super(context, mode);
		init( context );
	}

	public IPullToRefreshSwipeListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
		init( context );
	}
	private void init (Context context) {
		this.context = context;
		this.setOnLastItemVisibleListener(new HanderLastItemListener());
		listView = this.getRefreshableView();
		layoutInflater = LayoutInflater.from(context);
		footView = layoutInflater.inflate(R.layout.pull_to_refresh_foot_layout,  null );
//		this.setOnScrollListener(new OnScrollListener() {
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//			}
//			
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, int totalItemCount) {
//				SwipeMenuLayout touchView = listView.getSwipeMenuLayout();
//				if (totalItemCount >0 && touchView!= null  && touchView.isOpen()) {
//					touchView.closeMenu();
//				}
//			}
//		});
	}
	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		initFooterView();
	}

	private class HanderLastItemListener implements OnLastItemVisibleListener{
	    @Override
	   public void onLastItemVisible() {
		  if ( !footRefreshing && footerFreshListener != null ) {
			  footRefreshing = true; 
			  footerFreshListener.footerRefreshing();
		  }
	    }
	}
	//－－－－－－－－－－－－－－－－－－实现方法－－－－－－－－－－－－－－－－－
	//初始化调用一次设置尾部
	public void initFooterView() {
		Adapter adapter = listView.getAdapter();
		if ( adapter != null ) {
			if ( adapter.getCount() >= MIN_DATA_SEIZE && listView.getFooterViewsCount() == 1) {
					listView.addFooterView( footView );
			}
			else if ( adapter.getCount() < MIN_DATA_SEIZE && listView.getFooterViewsCount() == 2) {
				listView.removeFooterView(footView);
			}
		}
	}
	public void setOnFooterRefreshListener (FooterRefreshListener footerRefreshListener) {
	    this.footerFreshListener = footerRefreshListener;	
	}
	public void onFooterRefreshComplete(int dataAllSize) {
		footRefreshing = false;
		Adapter adapter = listView.getAdapter();
		if ( adapter!= null && adapter.getCount()>= dataAllSize) {
			if ( listView.getFooterViewsCount() > 1 ) {
			listView.removeFooterView( footView );
			}
		}
		else if ( adapter!= null && adapter.getCount()< dataAllSize ){
			System.out.println(listView.getFooterViewsCount());
			if ( listView.getFooterViewsCount() == 1 ) {
			listView.addFooterView( footView );
			}
		}
	}
	public interface FooterRefreshListener  {
		 void footerRefreshing ();
	}
}
