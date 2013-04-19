package com.art_no_sundo.timetable;

import java.net.URL;

public class DataSourceId {
	private DataSource source;
	private URL sourceId;

	public DataSourceId(DataSourceId sourceId) {
		this.source = sourceId.source;
		this.sourceId = sourceId.sourceId;
	}

	public DataSourceId(DataSource source, URL sourceId) {
		this.source = source;
		this.sourceId = sourceId;
	}

	public DataSource getSource() {
		return source;
	}

	public URL getSourceId() {
		return sourceId;
	}

	public Line getLine() {
		return source.getLine(sourceId);
	}

	public Train getTrain() {
		return source.getTrain(sourceId);
	}

	public Station getStation() {
		return source.getStation(sourceId);
	}
}
