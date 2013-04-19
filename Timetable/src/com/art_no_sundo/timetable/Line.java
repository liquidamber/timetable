package com.art_no_sundo.timetable;

import java.io.IOException;

public class Line {
	DataSourceId id;
	String name, nameYomi, company, companyYomi;

	public Line(Line obj) {
		this.id = obj.id;
		this.name = obj.name;
		this.nameYomi = obj.nameYomi;
		this.company = obj.company;
		this.companyYomi = obj.companyYomi;
	}

	public Line(DataSourceId id,
			String name, String nameYomi,
			String company, String companyYomi) {
		this.id = id;
		this.name = name;
		this.nameYomi = nameYomi;
		this.company = company;
		this.companyYomi = companyYomi;
	}

	public Line(DataSourceId dataSourceId, String name, String company) {
		this(dataSourceId, name, name, company, company);
	}

	Timetable getLineTimeTable(Direction direction, Day day) throws IOException {
		return id.getSource().getLineTimetable(this, direction, day);
	}

	public static class Timetable {
		private DataSourceId id;
		private Line line;
		private DirectionLabel direction;
		private Day day;

		Timetable(Timetable obj) {
			this.id = obj.id;
			this.line = obj.line;
			this.direction = obj.direction;
			this.day = obj.day;
		}

		Timetable(DataSourceId id, Line line, DirectionLabel direction, Day day) {
			this.id = id;
			this.line = line;
			this.direction = direction;
			this.day = day;
		}
	}
}
