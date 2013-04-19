package com.art_no_sundo.timetable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.art_no_sundo.timetable.Line.Timetable;
import com.art_no_sundo.timetable.Train.TimeSegment;

public class WebEkikaraDataSource implements DataSource {
	private TimetableHttpClient client;
	private XPathFactory xpathFac;

	private static String normalizeWhitespace(String str) {
		return str.replaceAll("[ \\t\\r\\n]", "");
	}

	private static String getCleanText(Node node) {
		return normalizeWhitespace(node.getTextContent());
	}

	private static String getCleanText(NodeList list, int i) {
		return normalizeWhitespace(list.item(i).getTextContent());
	}

	public WebEkikaraDataSource() {
		client = new TimetableHttpClient();
		xpathFac = XPathFactory.newInstance();
	}

	@Override
	public Line[] findLine(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Station[] findStation(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timetable getLineTimetable(Line line, Direction direction, Day day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.art_no_sundo.timetable.Station.Timetable getStationTimetable(
			Station station, Line line, Direction direction, Day day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.art_no_sundo.timetable.Train.Timetable getTrainTimetable(
			Train train) throws IOException {
		final String encoding = "Windows-31J";
		URL url = train.getId().getSourceId();
		Train.Timetable timetable = train.new Timetable();

		Node htmlNode = client.getNodeFromUrl(url, encoding);
		assert (htmlNode != null);

		NodeList trNodes;

		XPath xpath = xpathFac.newXPath();
		try {
			trNodes = (NodeList) xpath
					.evaluate(
							"//html//div[@id='container02']/table/tbody/tr/td/table/tbody/tr/td[@class='lowBg01']/table/tbody/tr",
							htmlNode, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
		assert (trNodes != null);

		XPathExpression tdxpath;
		try {
			tdxpath = xpath.compile("td");
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
		for (int i = 0; i < trNodes.getLength(); ++i) {
			Node trNode = trNodes.item(i);
			NodeList tdNodes;
			try {
				tdNodes = (NodeList) tdxpath.evaluate(trNode,
						XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				throw new RuntimeException(e);
			}

			if (tdNodes.getLength() == 2) {
				String name = getCleanText(tdNodes, 0);
				String value = getCleanText(tdNodes, 1);
				if (name.equals("列車名"))
					train.setName(value);
				else if (name.equals("列車番号"))
					train.setTrainId(value);
				else if (name.equals("列車予約コード"))
					train.setTicketInfo(value);
				else if (name.equals("連結車両"))
					train.setCarInfo(value);
				else if (name.equals("備考"))
					train.setAdditionalInfo(value);
				else if (name.equals("運転日"))
					train.setDateInfo(value);
			} else if (tdNodes.getLength() == 3) {
				TimeSegment seg;
				Node stationNode = tdNodes.item(0);
				if (getCleanText(stationNode).equals("駅名(駅名コード)")) {
					continue;
				}

				NodeList timeNodes;
				try {
					timeNodes = (NodeList) xpath.evaluate(
							".//span[@class='l']", tdNodes.item(1),
							XPathConstants.NODESET);
				} catch (XPathExpressionException e) {
					throw new RuntimeException(e);
				}
				assert (timeNodes != null);
				assert (timeNodes.getLength() == 2);

				String track = getCleanText(tdNodes, 2);

				Node stationLinkNode;
				String stationURIstr;
				String stationCode;
				try {
					stationLinkNode = (Node) xpath.evaluate(".//a",
							stationNode, XPathConstants.NODE);
					stationURIstr = (String) xpath.evaluate(".//a/@href",
							stationNode, XPathConstants.STRING);
					stationCode = normalizeWhitespace((String) xpath
							.evaluate(
									".//span[@class='textBold']/following-sibling::text()",
									stationNode, XPathConstants.STRING));
					assert (stationURIstr != null);
					assert (stationLinkNode != null);
					assert (stationCode != null);
				} catch (XPathExpressionException e) {
					throw new RuntimeException(e);
				}
				String stationName = getCleanText(stationLinkNode);
				Station station = null;
				try {
					station = new Station(new DataSourceId(this,
							url.toURI().resolve(stationURIstr)
									.toURL()), null, stationName, null);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String timeArrStr = getCleanText(timeNodes, 0);
				String timeDepStr = getCleanText(timeNodes, 1);
				if (timeArrStr.indexOf("レ") != -1) {
					seg = new Train.TimeSegment(station, PositionType.MIDDLE,
							TransitType.PASS, null, null, track);
				} else {
					final Pattern patArr = Pattern
							.compile("(\\d\\d):(\\d\\d)着");
					final Pattern patDep = Pattern
							.compile("(\\d\\d):(\\d\\d)発");
					Date dArr, dDep;
					Matcher mArr = patArr.matcher(timeArrStr);
					Matcher mDep = patDep.matcher(timeDepStr);
					PositionType posType;
					if (mArr.matches() && mDep.matches()) {
						posType = PositionType.MIDDLE;
					} else if (mArr.matches()) {
						posType = PositionType.END;
					} else if (mDep.matches()) {
						posType = PositionType.START;
					} else {
						// cannot enter, there are no types
						posType = PositionType.MIDDLE;
					}
					if (mArr.matches()) {
						dArr = new GregorianCalendar(2000, 0, 1,
								Integer.parseInt(mArr.group(1)),
								Integer.parseInt(mArr.group(2))).getTime();
					} else {
						dArr = null;
					}
					if (mDep.matches()) {
						dDep = new GregorianCalendar(2000, 0, 1,
								Integer.parseInt(mDep.group(1)),
								Integer.parseInt(mDep.group(2))).getTime();
					} else {
						dDep = null;
					}
					seg = new Train.TimeSegment(station, posType,
							TransitType.STOP, dArr, dDep, track);
				}
				timetable.segments.add(seg);
			}
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
