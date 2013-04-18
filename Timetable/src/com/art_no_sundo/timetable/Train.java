package com.art_no_sundo.timetable;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class Train {
	DataSourceID id;
	private String name;
	private String trainId;
	private String type;
	private String carInfo, ticketInfo, dateInfo, additionalInfo;

	public Train(DataSourceID id,
		String name, String trainid, String type,
		String car_info, String ticket_info, String date_info, String additional_info) {
		this.id = id;
		this.name = name;
		this.trainId = trainid;
		this.type = type;
		this.carInfo = car_info;
		this.ticketInfo = ticket_info;
		this.dateInfo = date_info;
		this.additionalInfo = additional_info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrainId() {
		return trainId;
	}

	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}

	public String getTicketInfo() {
		return ticketInfo;
	}

	public void setTicketInfo(String ticketInfo) {
		this.ticketInfo = ticketInfo;
	}

	public String getDateInfo() {
		return dateInfo;
	}

	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public DataSourceID getId() {
		return id;
	}

	public Timetable getTimetable()
			throws IOException {
		return this.getId().source.getTrainTimetable(this);
	}

	static public class TimeSegment {
		Station station;
		PositionType position_type;
		TransitType transit_type;
		java.util.Date arrive, depart;
		String track;

		public TimeSegment(
				Station station,
				PositionType pos_type,
				TransitType transit_type,
				java.util.Date arrive, java.util.Date depart) {
			this.station = station;
			this.position_type = pos_type;
			this.transit_type = transit_type;
			this.arrive = arrive;
			this.depart = depart;
			this.track = "";
		}

		public TimeSegment(
				Station station,
				PositionType pos_type,
				TransitType transit_type,
				java.util.Date arrive, java.util.Date depart,
				String track) {
			this.station = station;
			this.position_type = pos_type;
			this.transit_type = transit_type;
			this.arrive = arrive;
			this.depart = depart;
			this.track = track;
		}

		public Station getStation() {
			return station;
		}

		public String getTimeString() {
			switch (transit_type) {
			case PASS:
				return "レ";
			case NOTHROUGH:
				return "||";
			case STOP:
				switch (position_type) {
				case START:
					return DateFormat.getTimeInstance(DateFormat.SHORT).format(depart);
				case END:
					return DateFormat.getTimeInstance(DateFormat.SHORT).format(arrive);
				case MIDDLE:
					return
							DateFormat.getTimeInstance(DateFormat.SHORT).format(arrive)
							+ "\n"
							+DateFormat.getTimeInstance(DateFormat.SHORT).format(depart);
				default: // return at the end.
				}
			default: // return at the end.
			}
			return "--:--";
		}
	}

	public class Timetable {
		public List<TimeSegment> segments;

		Timetable() {
			this.segments = new ArrayList<TimeSegment>();
		}

		Timetable(List<TimeSegment> segments) {
			this.segments = segments;
		}
	}
}
