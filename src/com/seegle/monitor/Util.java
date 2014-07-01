package com.seegle.monitor;

public class Util {
	
	static String millisTimeToDotFormat(long ms, boolean needDay, boolean needMills) {
        
        long sec = 1000;
        long min = sec*60;
        long hour = min*60;
        long day = hour*24;
          
        long days = ms/day;
        long hours = (ms - days*day) / hour;
        long mins = (ms - days*day - hours*hour) / min;
        long secs = (ms - days*day - hours*hour - mins*min) / sec;
        long millis = ms - days*day - hours*hour - mins*min - secs*sec;
  
          
        String strDay = days < 10 ? "0"+days : ""+days;
        String strHour = hours < 10 ? "0"+hours : ""+hours;
        String strMin = mins < 10 ? "0"+mins : ""+mins;
        String strSec = secs < 10 ? "0"+secs : ""+secs;
        String strMills = millis < 100 ? "0"+millis : ""+millis;
          
        String tmpForDay = needDay ? (strDay + ":") : "";
        String tmpForMills = needMills ? ("." + strMills) : "";
          
        return (tmpForDay + strHour + ":" + strMin + ":" + strSec + tmpForMills);
          
    }
}
