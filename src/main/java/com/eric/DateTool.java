package com.eric;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.FastDateFormat;

public class DateTool {

	private Date date;

	private String format = "EEE, dd MMM yyyy HH:mm:ss Z";

	private TimeZone zone = TimeZone.getDefault();

	public DateTool() {
	}

	private DateTool copy() {
		DateTool copy = new DateTool();
		copy.date = date;
		copy.zone = zone;
		copy.format = format;
		return copy;
	}

	public DateTool timeZone(String zone) {
		DateTool tool = copy();
		tool.zone = TimeZone.getTimeZone(zone);
		return tool;
	}

	public DateTool pattern(String format) {
		DateTool tool = copy();
		tool.format = format;
		return tool;
	}

	public DateTool getFormatRfc822() {
		return pattern("EEE, dd MMM yyyy HH:mm:ss Z");
	}

	public DateTool getFormatShortDate() {
		return pattern("MM-dd-yyyy");
	}

	public DateTool getNow() {
		DateTool copy = copy();
		copy.date = new Date();
		return copy;
	}

	public DateTool timestamp(Number t) {
		DateTool copy = copy();
		copy.date = t == null ? null : new Date(t.longValue());
		return copy;
	}

	public Date getDate() {
		return date;
	}

	public DateTool midnight(Number month, Number day, Number year) {
		return timestamp(month, day, year, 0, 0, 0, 0);
	}

	public DateTool noon(Number month, Number day, Number year) {
		return timestamp(month, day, year, 12, 0, 0, 0);
	}

	public DateTool timestamp(Number month, Number day, Number year, Number hour, Number minute, Number second,
			Number millis) {

		DateTool copy = copy();
		Calendar c = Calendar.getInstance(zone);
		c.set(Calendar.MONTH, month.intValue());
		c.set(Calendar.DAY_OF_MONTH, day.intValue());
		c.set(Calendar.YEAR, year.intValue());
		c.set(Calendar.HOUR_OF_DAY, hour.intValue());
		c.set(Calendar.MINUTE, minute.intValue());
		c.set(Calendar.SECOND, second.intValue());
		c.set(Calendar.MILLISECOND, millis.intValue());
		copy.date = c.getTime();
		return copy;
	}

	public int getYear() {
		Calendar c = Calendar.getInstance(zone);
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public String getAllTimeZones() {
		return ArrayUtils.toString(TimeZone.getAvailableIDs());
	}

	@Override
	public String toString() {
		return FastDateFormat.getInstance(format, zone).format(date == null ? new Date() : date);
	}
}
