package com.art_no_sundo.timetable;

import java.net.URL;

public class DataSourceID {
	DataSource source;
	URL sourceid;

	public DataSourceID(DataSourceID obj) {
		this.source = obj.source;
		this.sourceid = obj.sourceid;
	}

	public DataSourceID(DataSource source, URL sourceid) {
		this.source = source;
		this.sourceid = sourceid;
	}

	public Line getLine() {
		return source.getLine(sourceid);
	}

	public Train getTrain() {
		return source.getTrain(sourceid);
	}

	public Station getStation() {
		return source.getStation(sourceid);
	}
}
