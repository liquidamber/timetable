package com.art_no_sundo.timetable;

public enum PositionType {
	START, MIDDLE, END;

	public static PositionType fromIndex(int index, int size) {
		if(index == 0) return START;
		if(index == size - 1) return END;
		return MIDDLE;
	}
}
