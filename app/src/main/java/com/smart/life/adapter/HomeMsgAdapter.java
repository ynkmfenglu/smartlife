package com.smart.life.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.smart.life.R;
import com.smart.life.domain.HomeMsg;
import com.smart.life.ui.HomeMsgDetailActivity;
import com.smart.life.utils.SplitNetImagePath;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeMsgAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<HomeMsg> allList;
	private Intent intent;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;

	public HomeMsgAdapter(Context context, ArrayList<HomeMsg> allList) {
		this.context = context;
		this.allList = allList;

		intent = new Intent(context, HomeMsgDetailActivity.class);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return allList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderView holderView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.home_listitem, null);
			holderView = new HolderView();
			holderView.msg_title = (TextView) convertView
					.findViewById(R.id.homemsg_title);
			holderView.msgdate = (TextView) convertView
					.findViewById(R.id.homemsg_date);
			holderView.image_HomeMsg = (ImageView) convertView
					.findViewById(R.id.image_homemsg);
			holderView.image_msgtype = (ImageView) convertView
					.findViewById(R.id.image_msgtype);
			holderView.relativeLayout = (RelativeLayout) convertView
					.findViewById(R.id.rl_homemsg_item);

			convertView.setTag(holderView);
		} else {
			holderView = (HolderView) convertView.getTag();
		}

		holderView.msg_title.setText(allList.get(position)
				.getTitle());
		holderView.msgdate.setText(allList.get(position).getDate());
		if (allList.get(position).getMsgtype() == 0) {
			holderView.image_msgtype.setImageResource(R.drawable.ic_addr);
		} else if (allList.get(position).getMsgtype() == 1) {
			holderView.image_msgtype.setImageResource(R.drawable.ic_group);
		}

		holderView.image_HomeMsg.setImageResource(R.drawable.test);
		holderView.relativeLayout.setBackgroundResource(R.drawable.round_coners);
		String paths[] = SplitNetImagePath.splitNetImagePath(allList.get(position).getPic());
		ImageLoader.getInstance().displayImage(paths[0], holderView.image_HomeMsg, options, animateFirstListener);

		final int getPosition = position;
		holderView.relativeLayout.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent.putExtra("allPosition", getPosition);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class HolderView {
		private ImageView image_HomeMsg, image_msgtype;
		private TextView msg_title, msgdate;
		private RelativeLayout relativeLayout;
	}

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
