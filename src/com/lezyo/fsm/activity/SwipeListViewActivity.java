/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.lezyo.fsm.activity;

import java.util.Arrays;
import java.util.LinkedList;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lezyo.fsm.R;
import com.lezyo.fsm.activity.base.IPullToRefreshSwipeListView;
import com.lezyo.fsm.activity.base.IPullToRefreshSwipeListView.FooterRefreshListener;
import com.lezyo.fsm.activity.base.NetWorkActivity;
import com.lezyo.fsm.bean.teacher.LoginResponse;
import com.lezyo.fsm.utils.CommonUtils;
import com.tongfang.teacher.customview.swipemenu.SwipeMenu;
import com.tongfang.teacher.customview.swipemenu.SwipeMenuCreator;
import com.tongfang.teacher.customview.swipemenu.SwipeMenuItem;
import com.tongfang.teacher.customview.swipemenu.SwipeMenuListView;
import com.tongfang.teacher.customview.swipemenu.SwipeMenuListView.OnMenuItemClickListener;

public final class SwipeListViewActivity extends NetWorkActivity {
	private final static int LOGIN_REQUEST = 1;
	
	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;

	private LinkedList<String> mListItems;
//	private IPullToRefreshSwipeListView mPullRefreshListView;
	private ArrayAdapter<String> mAdapter;

//	private View footView;
	private SwipeMenuListView actualListView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_list);
//		footView = View.inflate( this , R.layout.pull_to_refresh_foot_layout, null );
		
		actualListView = (SwipeMenuListView) findViewById(R.id.pull_refresh_list);
//
//		// Set a listener to be invoked when the list should be refreshed.
//		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<SwipeMenuListView>() {
//			@Override
//			public void onRefresh(PullToRefreshBase<SwipeMenuListView> refreshView) {
//				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
//						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//
//				// Update the LastUpdatedLabel
//				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//
//				// Do work to refresh the list here.
//				new GetDataTask().execute();
//			}
//		});
		
		

//		// Add an end-of-list listener
//		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
//
//			@Override
//			public void onLastItemVisible() {
//				Toast.makeText(PullToRefreshListActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
//				mListItems.addLast("Added after refresh...");
//				mAdapter.notifyDataSetChanged();
//			}
//		});

//		actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);

		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));

		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);

		/**
		 * Add Sound Event Listener
		 */
//		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
//		soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
//		soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
//		soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
//		mPullRefreshListView.setOnPullEventListener(soundListener);

		// You can also just use setListAdapter(mAdapter) or
		// mPullRefreshListView.setAdapter(mAdapter)
		actualListView.setAdapter(mAdapter);
//		mPullRefreshListView.initFooterView();
//		mPullRefreshListView.setFooterView(R.layout.pull_to_refresh_foot_layout, 120);
//		actualListView.setOnFooterRefreshListener(new FooterRefreshListener() {
//			@Override
//			public void footerRefreshing() {
//				// TODO Auto-generated method stub
//				  SwipeListViewActivity.this.sendConnection("KJ10008", new String[]{"UserCode","UserPassword","LoginSource"},
//			    		  new String[]{"13622111111","222222","2"}, LOGIN_REQUEST, true, LoginResponse.class);
//			}
//		});
		
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem openItem = new SwipeMenuItem( mContext );
				openItem.setBackground(new ColorDrawable(Color.parseColor("#ff4861")));
				openItem.setWidth(CommonUtils.dip2px(mContext,70));
				openItem.setTitle("删除");
				openItem.setTitleSize(18);
				openItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(openItem);

			}
		};
		actualListView.setMenuCreator(creator);
		actualListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				//删除
//				deleteContact(position,DEL_CONTACT);
				return false;
			}
		});
	}
//
//	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
//
//		@Override
//		protected String[] doInBackground(Void... params) {
//			// Simulates a background job.
//			try {
//				Thread.sleep(4000);
//			} catch (InterruptedException e) {
//			}
//			return mStrings;
//		}
//
//		@Override
//		protected void onPostExecute(String[] result) {
//			mListItems.addFirst("Added after refresh...");
//			mAdapter.notifyDataSetChanged();
//
//			// Call onRefreshComplete when the list has been refreshed.
//			mPullRefreshListView.onRefreshComplete();
//
//			super.onPostExecute(result);
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		menu.add(0, MENU_MANUAL_REFRESH, 0, "Manual Refresh");
//		menu.add(0, MENU_DISABLE_SCROLL, 1,
//				mPullRefreshListView.isScrollingWhileRefreshingEnabled() ? "Disable Scrolling while Refreshing"
//						: "Enable Scrolling while Refreshing");
//		menu.add(0, MENU_SET_MODE, 0, mPullRefreshListView.getMode() == Mode.BOTH ? "Change to MODE_PULL_DOWN"
//				: "Change to MODE_PULL_BOTH");
//		menu.add(0, MENU_DEMO, 0, "Demo");
		return super.onCreateOptionsMenu(menu);
	}

//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
//
//		menu.setHeaderTitle("Item: " + getListView().getItemAtPosition(info.position));
//		menu.add("Item 1");
//		menu.add("Item 2");
//		menu.add("Item 3");
//		menu.add("Item 4");
//
//		super.onCreateContextMenu(menu, v, menuInfo);
//	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
//		MenuItem disableItem = menu.findItem(MENU_DISABLE_SCROLL);
//		disableItem
//				.setTitle(mPullRefreshListView.isScrollingWhileRefreshingEnabled() ? "Disable Scrolling while Refreshing"
//						: "Enable Scrolling while Refreshing");
//
//		MenuItem setModeItem = menu.findItem(MENU_SET_MODE);
//		setModeItem.setTitle(mPullRefreshListView.getMode() == Mode.BOTH ? "Change to MODE_FROM_START"
//				: "Change to MODE_PULL_BOTH");

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
//			case MENU_MANUAL_REFRESH:
//				new GetDataTask().execute();
//				mPullRefreshListView.setRefreshing(false);
//				break;
//			case MENU_DISABLE_SCROLL:
//				mPullRefreshListView.setScrollingWhileRefreshingEnabled(!mPullRefreshListView
//						.isScrollingWhileRefreshingEnabled());
//				break;
//			case MENU_SET_MODE:
//				mPullRefreshListView.setMode(mPullRefreshListView.getMode() == Mode.BOTH ? Mode.PULL_FROM_START
//						: Mode.BOTH);
//				break;
//			case MENU_DEMO:
//				mPullRefreshListView.demo();
//				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };

	@Override
	protected void onSuccess(Object result, int where) {
		// TODO Auto-generated method stub
			mListItems.addLast("Added after refresh...");
		    mAdapter.notifyDataSetChanged();
//		    mPullRefreshListView.onFooterRefreshComplete(130);
	}

	@Override
	protected void onFailure(String errMsg, Object result, int where) {
		// TODO Auto-generated method stub
		
	}
}
