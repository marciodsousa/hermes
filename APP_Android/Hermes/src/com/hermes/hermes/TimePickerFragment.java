package com.hermes.hermes;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	private OnTimeSetListener ontimeSet;
	public String formattedTime = "";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of DatePickerDialog and return it
		return new TimePickerDialog(getActivity(), ontimeSet, hour, minute, true);
	}

	public void onTimeSet(TimePicker view, int hour, int minute) {
		Calendar c = Calendar.getInstance();
		c.set(hour, minute);

		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
		formattedTime = sdf.format(c.getTime());
		
	}
	
	 public void setCallBack(OnTimeSetListener ontime) {
		 ontimeSet = ontime;
		 }

}