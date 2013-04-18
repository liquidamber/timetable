package com.art_no_sundo.timetable;

import java.io.IOException;

public class Station {
	private DataSourceID id;
	private Line lines[];
	private String name;
	private String nameyomi;

	public Station(Station obj) {
		this.id = obj.id;
		this.lines = obj.lines;
		this.name = obj.name;
		this.nameyomi = obj.nameyomi;
	}

	public Station(DataSourceID id, Line lines[], String name, String nameyomi) {
		this.id = id;
		this.lines = lines;
		this.name = name;
		this.nameyomi = nameyomi;
	}

	public Timetable getStationTimeTable(Line line, Direction direction, Day day)
			throws IOException {
		return id.source.getStationTimetable(this, line, direction, day);
	}

	public String getName() {
		return name;
	}

	public DataSourceID getId() {
		return id;
	}

	public Line[] getLines() {
		return lines;
	}

	public String getNameyomi() {
		return nameyomi;
	}

	public static class Timetable {
		DataSourceID id;
		Line line;
		Station station;
		DirectionLabel direction;
		Day day;

		public Timetable(DataSourceID id, Line line, Station station, DirectionLabel direction, Day day) {
			this.id = id;
			this.line = line;
			this.station = station;
			this.direction = direction;
			this.day = day;
		}
	}
}
