package com.smart.life.utils;

import java.util.List;
import com.smart.life.R;
import com.smart.life.myview.CustomDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyShare {

	private Context context;
	private CustomDialog dialog;

	public MyShare(Context context) {
		super();
		this.context = context;

		dialog = new CustomDialog(context, R.layout.share, R.style.Theme_Dialog);
		CustomDialog.MyBuilder myBuilder = dialog.new MyBuilder(context);
		myBuilder.setCancelable(true);
		myBuilder.create();
		dialog.show();
	}

	public void share(final String shareContent) {
		// ���ŷ���
		RelativeLayout relativeLayout_share_sms = (RelativeLayout) dialog
				.findViewById(R.id.relativeLayout_share_sms);
		relativeLayout_share_sms.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("���ŷ���");

				Uri smsToUri = Uri.parse("smsto:");
				Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
				sendIntent.putExtra("address", "5554"); // �绰���룬����ȥ���Ļ���Ĭ�Ͼ�û�е绰
				sendIntent.putExtra("sms_body", shareContent);
				sendIntent.setType("vnd.android-dir/mms-sms");
				((Activity) context).startActivityForResult(sendIntent, 1);
				dialog.cancel();
			}
		});

		// ����΢������
		RelativeLayout relativeLayout_share_sina = (RelativeLayout) dialog
				.findViewById(R.id.relativeLayout_share_sina);
		relativeLayout_share_sina.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, shareContent);
				PackageManager pm = ((Activity) context).getPackageManager();
				List<ResolveInfo> matches = pm.queryIntentActivities(intent,
						PackageManager.MATCH_DEFAULT_ONLY);
				String packageName = "com.sina.weibo";
				ResolveInfo info = null;
				for (ResolveInfo each : matches) {
					String pkgName = each.activityInfo.applicationInfo.packageName;
					if (packageName.equals(pkgName)) {
						info = each;
						break;
					}
				}
				if (info == null) {
					Toast.makeText(((Activity) context), "û���ҵ�����΢��", 1000)
							.show();
					return;
				} else {
					intent.setClassName(packageName, info.activityInfo.name);
				}

				((Activity) context).startActivity(intent);
				dialog.cancel();
			}
		});

		// �������
		RelativeLayout relativeLayout_share_more = (RelativeLayout) dialog
				.findViewById(R.id.relativeLayout_share_more);
		relativeLayout_share_more.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("image/*");
				intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
				intent.putExtra(Intent.EXTRA_TEXT, shareContent);

				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				((Activity) context).startActivity(Intent.createChooser(intent,
						"����"));
				dialog.cancel();
			}
		});
	}

}
