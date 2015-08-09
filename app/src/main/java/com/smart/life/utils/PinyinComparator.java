package com.smart.life.utils;


import java.util.Comparator;

import com.smart.life.domain.City;


public class PinyinComparator implements Comparator<City>{

	public int compare(City lhs, City rhs) {
		String str1 =  PingYinUtil.getPingYin(lhs.getName());
		String str2 =  PingYinUtil.getPingYin(rhs.getName());
		
//		compa
		
		return str1.compareTo(str2);
	}
}
