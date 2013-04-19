package com.art_no_sundo.timetable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public final class MockDataSource implements DataSource {

	// Line A: [X, U, V, W]
	// Line B: [X, Y, Z]
	// Line あ: [W, い, ろ, は]
	// Line い: [い, Y, に, ほ, へ, と]

	private Line[] lines;
	private Station[] stations;

	public MockDataSource() {
		final int nLines = LineType.values().length;
		final int nStations = StationType.values().length;

		ArrayList<ArrayList<Line>> linesOfStation =
				new ArrayList<ArrayList<Line>>(nStations);
		this.lines = new Line[nLines];
		this.stations = new Station[nStations];

		for(LineType lt : LineType.values()){
			lines[lt.ordinal()] =
					new Line(new DataSourceId(this, lt.getUrl()),
							lt.getName(), lt.getNameYomi(),
							lt.getCompany(), lt.getCompanyYomi());
			for(StationType st : lt.getStations()){
				linesOfStation.get(st.ordinal()).add(lines[lt.ordinal()]);
			}
		}

		for(StationType st : StationType.values()){
			stations[st.ordinal()] =
					new Station(new DataSourceId(this, st.getUrl()),
							(Line[]) linesOfStation.get(st.ordinal()).toArray(),
							st.getName(), st.getNameYomi());
		}
	}

	@Override
	public Line[] findLine(String query) {
		return this.lines;
	}

	@Override
	public Station[] findStation(String query) {
		return this.stations;
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
		for(LineType lt : LineType.values()) {
			if(lt.getUrl() == id) {
				return this.lines[lt.ordinal()];
			}
		}
		// TODO You should throw "Object not found" exception
		return null;
	}

	@Override
	public Train getTrain(URL id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Station getStation(URL id) {
		for(StationType st : StationType.values()) {
			if(st.getUrl() == id) {
				return this.stations[st.ordinal()];
			}
		}
		// TODO You should throw "Object not found" exception
		return null;
	}

	private static enum LineType {
		LINE_EA("http://www.example.com/timetable/line/abc_a/", "Line A", "ABC Railways",
				new StationType[]{
				StationType.STATION_EX, StationType.STATION_EU,
				StationType.STATION_EV, StationType.STATION_EW, }),
		LINE_EB("http://www.example.com/timetable/line/abc_b/", "Line B", "ABC Railways",
				new StationType[] {
				StationType.STATION_EX, StationType.STATION_EY,
				StationType.STATION_EZ, }),
		LINE_JA("http://www.example.com/timetable/line/iroha_a/",
				"アルミサエル線", "あるみさえるせん", "いろは鉄道", "いろはてつどう",
				new StationType[] {
				StationType.STATION_EW, StationType.STATION_JI,
				StationType.STATION_JRO, StationType.STATION_JHA, }),
		LINE_JI("http://www.example.com/timetable/line/iroha_i/",
				"イロウル線", "いろうるせん", "いろは鉄道", "いろはてつどう",
				new StationType[] {
				StationType.STATION_JI, StationType.STATION_EY,
				StationType.STATION_JNI, StationType.STATION_JHO,
				StationType.STATION_JHE, }),
		;

		private URL url;
		private String name, nameYomi;
		private String company, companyYomi;
		private StationType stations[];

		LineType(String url,
				String name, String company,
				StationType[] stations){
			this(url, name, name, company, company, stations);
		}

		LineType(String url,
				String name, String nameYomi, String company, String companyYomi,
				StationType[] stations){
			try {
				this.url = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			this.name = name;
			this.nameYomi = nameYomi;
			this.company = company;
			this.companyYomi = company;
			this.stations = stations;
		}

		public URL getUrl() {
			return url;
		}

		public String getName() {
			return name;
		}

		public String getNameYomi() {
			return nameYomi;
		}

		public String getCompany() {
			return company;
		}

		public String getCompanyYomi() {
			return companyYomi;
		}

		public StationType[] getStations() {
			return stations;
		}
	}

	private static enum StationType {
		STATION_EU("http://www.example.com/timetable/station/e_u/", "Station U"),
		STATION_EV("http://www.example.com/timetable/station/e_v/", "Station V"),
		STATION_EW("http://www.example.com/timetable/station/e_w/", "Station W"),
		STATION_EX("http://www.example.com/timetable/station/e_x/", "Station X"),
		STATION_EY("http://www.example.com/timetable/station/e_y/", "Station Y"),
		STATION_EZ("http://www.example.com/timetable/station/e_z/", "Station Z"),
		STATION_JI("http://www.example.com/timetable/station/j_i/", "碇", "いかり"),
		STATION_JRO("http://www.example.com/timetable/station/j_ro/", "ロンギヌスの槍", "ろんぎぬすのやり"),
		STATION_JHA("http://www.example.com/timetable/station/j_ha/", "波形パターン青", "はけいぱたーんあお"),
		STATION_JNI("http://www.example.com/timetable/station/j_ni/", "弐号機", "にごうき"),
		STATION_JHO("http://www.example.com/timetable/station/j_ho/", "フライホイール", "にごうき"),
		STATION_JHE("http://www.example.com/timetable/station/j_he/", "ヘイフリック限界", "にごうき"),
		STATION_JTO("http://www.example.com/timetable/station/j_to/", "トウジ", "とうじ"),
		;

		private URL url;
		private String name, nameYomi;

		StationType(String url, String name) {
			this(url, name, name);
		}

		StationType(String url, String name, String nameYomi) {
			try {
				this.url = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			this.name = name;
			this.nameYomi = nameYomi;
		}

		public URL getUrl() {
			return url;
		}

		public String getName() {
			return name;
		}

		public String getNameYomi() {
			return nameYomi;
		}
	}
}
