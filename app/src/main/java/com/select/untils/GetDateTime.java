package com.select.untils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDateTime {
	public String getTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		return df.format(new Date());
	}
}
