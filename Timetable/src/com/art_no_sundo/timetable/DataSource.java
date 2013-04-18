/**
 *
 */
package com.art_no_sundo.timetable;

import java.io.IOException;
import java.net.URL;

/**
 * @author liquid
 *
 */
public interface DataSource {
	Line[] findLine(String query);
	Station[] findStation(String query);

	Line.Timetable getLineTimetable(Line line, Direction direction, Day day)
			throws IOException;
	Station.Timetable getStationTimetable(Station station, Line line, Direction direction, Day day)
			throws IOException;
	Train.Timetable getTrainTimetable(Train train)
			throws IOException;

	Line getLine(URL id);
	Train getTrain(URL id);
	Station getStation(URL id);
}
