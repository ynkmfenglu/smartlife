package com.smart.life.utils;

/**
 * Created by fenglu on 2015/8/7.
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.smart.life.R;

/**
 * ����ʱ��ѡ��ؼ� ʹ�÷����� private EditText inputDate;//��Ҫ���õ�����ʱ���ı��༭�� private String
 * initDateTime="2012��9��3�� 14:44",//��ʼ����ʱ��ֵ �ڵ���¼���ʹ�ã�
 * inputDate.setOnClickListener(new OnClickListener() {
 *
 * @Override public void onClick(View v) { DateTimePickDialogUtil
 *           dateTimePicKDialog=new
 *           DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
 *           dateTimePicKDialog.dateTimePicKDialog(inputDate);
 *
 *           } });
 *
 * @author
 */
public class DateTimePickDialogUtil implements OnDateChangedListener, OnTimeChangedListener {
    private DatePicker datePicker;
    private AlertDialog ad;
    private String dateTime;
    private String initDateTime;
    private Activity activity;

    /**
     * ����ʱ�䵯��ѡ����캯��
     *
     * @param activity
     *            �����õĸ�activity
     * @param initDateTime
     *            ��ʼ����ʱ��ֵ����Ϊ�������ڵı��������ʱ���ʼֵ
     */
    public DateTimePickDialogUtil(Activity activity, String initDateTime) {
        this.activity = activity;
        this.initDateTime = initDateTime;

    }

    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.YEAR) + "��"
                    + calendar.get(Calendar.MONTH) + "��"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "�� ";
        }

        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
    }

    /**
     * ��������ʱ��ѡ��򷽷�
     *
     * @param inputDate
     *            :Ϊ��Ҫ���õ�����ʱ���ı��༭��
     * @return
     */
    public AlertDialog dateTimePicKDialog(final EditText inputDate) {
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_date, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        init(datePicker);

        ad = new AlertDialog.Builder(activity)
                .setTitle(initDateTime)
                .setView(dateTimeLayout)
                .setPositiveButton("����", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(dateTime);
                    }
                })
                .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText("");
                    }
                }).show();

        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // �������ʵ��
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), 0,0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        dateTime = sdf.format(calendar.getTime());
        ad.setTitle(dateTime);
    }

    /**
     * ʵ�ֽ���ʼ����ʱ��2012��07��02�� 16:45 ��ֳ��� �� �� ʱ �� ��,����ֵ��calendar
     *
     * @param initDateTime
     *            ��ʼ����ʱ��ֵ �ַ�����
     * @return Calendar
     */
    private Calendar getCalendarByInintData(String initDateTime) {
        String date;
        Calendar calendar = Calendar.getInstance();
        String [] str = initDateTime.split("-");
        if (str.length < 2) {
            date = "1970-01-01";
            str = date.split("-");
        }
        String yearStr = str[0];
        String monthStr = str[1]; // ��
        String dayStr = str[2]; // ��

        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
        int currentDay = Integer.valueOf(dayStr.trim()).intValue();

        calendar.set(currentYear, currentMonth, currentDay, 0, 0);
        return calendar;
    }
}