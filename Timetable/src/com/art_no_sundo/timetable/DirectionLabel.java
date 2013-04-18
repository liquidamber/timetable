package com.art_no_sundo.timetable;

public class DirectionLabel {
	Direction direction;
	String label;

	public DirectionLabel(DirectionLabel obj) {
		this.direction = obj.direction;
		this.label = obj.label;
	}

	public DirectionLabel(Direction direction, String label) {
		this.direction = direction;
		this.label = label;
	}
}
