package com.art_no_sundo.timetable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;

public final class MockDataSource implements DataSource {

	@Override
	public Line[] findLine(String query) {
		Line result[] = new Line[2];
		URL url[] = new URL[2];
		try {
			url[0] = new URL("http://www.example.com/line/a/");
			url[1] = new URL("http://www.example.com/line/b/");
		} catch (MalformedURLException e) {
		}
		result[0] = new Line(new DataSourceID(this, url[0]), "路線A", "ロセンA", "会社A", "カイシャA");
		result[1] = new Line(new DataSourceID(this, url[1]), "路線B", "ロセンB", "会社B", "カイシャB");
		return result;
	}

	@Override
	public Station[] findStation(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Line.Timetable getLineTimetable(Line line, Direction direction,
			Day day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Station.Timetable getStationTimetable(Station station, Line line,
			Direction direction, Day day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Train.Timetable getTrainTimetable(Train train) {
		final int LENGTH = 20;
		Train.Timetable timetable = train.new Timetable();
		for(int i=0; i < LENGTH; ++i) {
			Date date1 = new GregorianCalendar(2000, 0, 1, 12, i*5+0).getTime();
			Date date2 = new GregorianCalendar(2000, 0, 1, 12, i*5+1).getTime();
			timetable.segments.add(
				new Train.TimeSegment(
					new Station(null, null,
						"駅" + Integer.toString(i),
						"エキ" + Integer.toString(i)),
						PositionType.fromIndex(i, LENGTH),
						TransitType.STOP, date1, date2));
		}
		return timetable;
	}

	@Override
	public Line getLine(URL id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Train getTrain(URL id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Station getStation(URL id) {
		// TODO Auto-generated method stub
		return null;
	}

}
