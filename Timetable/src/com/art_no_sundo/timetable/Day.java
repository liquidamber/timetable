package com.art_no_sundo.timetable;

public enum Day {
	SUN, MON, TUE, WED, THU, FRI, SAT;

	public boolean isWeekday() {
		if(this == SUN || this == SAT) return false;
		else return true;
	}
}
