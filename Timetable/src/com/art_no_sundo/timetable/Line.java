package com.art_no_sundo.timetable;

import java.io.IOException;

public class Line {
	DataSourceID id;
	String name, nameyomi, company, companyyomi;

	public Line(Line obj) {
		this.id = obj.id;
		this.name = obj.name;
		this.nameyomi = obj.nameyomi;
		this.company = obj.company;
		this.companyyomi = obj.companyyomi;
	}

	public Line(DataSourceID id, String name, String nameyomi, String company, String companyyomi) {
		this.id = id;
		this.name = name;
		this.nameyomi = nameyomi;
		this.company = company;
		this.companyyomi = companyyomi;
	}

	Timetable getLineTimeTable(Direction direction, Day day) throws IOException {
		return id.source.getLineTimetable(this, direction, day);
	}

	public static class Timetable {
		DataSourceID id;
		Line line;
		DirectionLabel direction;
		Day day;

		Timetable(Timetable obj) {
			this.id = obj.id;
			this.line = obj.line;
			this.direction = obj.direction;
			this.day = obj.day;
		}

		Timetable(DataSourceID id, Line line, DirectionLabel direction, Day day) {
			this.id = id;
			this.line = line;
			this.direction = direction;
			this.day = day;
		}
	}
}
