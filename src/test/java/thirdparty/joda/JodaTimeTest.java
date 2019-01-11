package thirdparty.joda;

import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.MonthDay;
import org.joda.time.Period;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * joda time test 유닛<br>
 * 사실 org.joda.time은 JDK8에서 java.time 패키지로 흡수되었고 더 이상 major 업데이트 계획이 없다. 그래서 이 프로젝트에 있기가 좀 애매함...
 * 
 * @since 2017-07-27
 * @author fixalot
 */
public class JodaTimeTest {
	private static final Logger logger = LoggerFactory.getLogger(JodaTimeTest.class);

	/**
	 * ISO 날짜 표기(Zulu 타임존, UTC, 그리니치 표준시각)법으로 DateTime 생성
	 * 
	 * @author fixalot
	 */
	@Test
	public void toDateTimeWithISODateTimeString() {
		DateTime dateTime = new DateTime("2017-04-18T01:24:48.842Z");
		Assert.assertEquals("2017-04-18T10:24:48.842+09:00", dateTime.toString());
	}
	
	@Test
	public void getLocalDate() {
		LocalDate a = new LocalDate();
		LocalDate b = LocalDate.now();
		Assert.assertTrue(a.equals(b));
		logger.debug(String.valueOf(a)); // yyyy-MM-dd
	}

	@Test
	public void getMonthDay() {
		MonthDay a = new MonthDay();
		MonthDay b = MonthDay.now();
		Assert.assertTrue(a.equals(b));
		logger.debug(String.valueOf(a)); // --MM-dd
	}

	@Test
	public void getYearMonth() {
		YearMonth a = new YearMonth();
		YearMonth b = YearMonth.now();
		Assert.assertTrue(a.equals(b));
		logger.debug(String.valueOf(a)); // yyyy-MM
	}

	@Test
	public void getFirstAndLastDateOfMonth() {
		YearMonth c = new YearMonth("2017-06");
		DateTime dateTime = c.toDateTime(null);
		Assert.assertEquals(1, dateTime.dayOfMonth().withMinimumValue().getDayOfMonth());
		Assert.assertEquals(30, dateTime.dayOfMonth().withMaximumValue().getDayOfMonth());
	}

	@Test
	public void getNow() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String dt = DateTime.now().toString(formatter);
		logger.debug("testNow: " + dt);
	}

	@Test
	public void parseFromString() {
		DateTime dt = new DateTime("2017-04-18T16:41:34.219+09:00");
		logger.debug(dt.toString());
		DateTime dt2 = new DateTime(2017, 4, 18, 16, 41, 34, 219);
		Assert.assertEquals(dt, dt2);
	}

	@Test
	public void parseFromStringWithFormatter() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTime dt = formatter.parseDateTime("2016-02-05 00:00:00.000");
		Assert.assertEquals("2016-02-05T00:00:00.000+09:00", dt.toString());

		try {
			formatter.parseDateTime("20160205");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		DateTime dt2 = new DateTime("2015-01-01");
		Assert.assertEquals("2015-01-01T00:00:00.000+09:00", String.valueOf(dt2));
	}

	@Test
	public void toStringWithFormatter() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss:SSS");
		DateTime dt = new DateTime();
		DateTime newDt = dt.withYear(2020).withMonthOfYear(2).withDayOfMonth(29).withHourOfDay(23).withMinuteOfHour(59)
				.withSecondOfMinute(59).withMillisOfSecond(10);

		Assert.assertEquals("2020-02-29T23:59:59.010+09:00", newDt.toString());
		Assert.assertEquals("2020-02-29 23:59:59:010", newDt.toString(formatter));
	}

	@Test
	public void parseFromJavaUtilDate() {
		Date date = GregorianCalendar.getInstance().getTime();
		DateTime dt = new DateTime(date);
		logger.debug("testFromJavaUtilDate: " + dt.toString());
	}

	@Test
	public void testGetter() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTime dt = formatter.parseDateTime("2016-02-15 13:40:30.123");

		// 시대(ERA)
		Assert.assertEquals(1, dt.getEra());
		// 세기
		Assert.assertEquals(20, dt.getCenturyOfEra());
		// 년
		Assert.assertEquals(2016, dt.getYear());
		Assert.assertEquals(16, dt.getYearOfCentury());
		Assert.assertEquals(2016, dt.getYearOfEra());
		Assert.assertEquals(2016, dt.getWeekyear());
		// 달
		Assert.assertEquals(2, dt.getMonthOfYear());
		// 주
		Assert.assertEquals(7, dt.getWeekOfWeekyear());
		// 지역
		Assert.assertEquals("Asia/Seoul", dt.getZone().toString());
		// 일
		Assert.assertEquals(46, dt.getDayOfYear());
		Assert.assertEquals(15, dt.getDayOfMonth());
		Assert.assertEquals(1, dt.getDayOfWeek());
		// 시간
		Assert.assertEquals(13, dt.getHourOfDay());
		// 분
		Assert.assertEquals(820, dt.getMinuteOfDay());
		Assert.assertEquals(40, dt.getMinuteOfHour());
		// 초
		Assert.assertEquals(49230, dt.getSecondOfDay());
		Assert.assertEquals(30, dt.getSecondOfMinute());
		// 밀리초
		Assert.assertEquals(123, dt.getMillisOfSecond());
		Assert.assertEquals(1455511230123L, dt.getMillis());
	}

	/**
	 * 실패한 테스트
	 * 
	 * @author fixalot
	 */
	@Test
	public void splitDate() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime start = formatter.parseDateTime("2017-01-01");
		DateTime end = formatter.parseDateTime("2017-02-03");
		logger.debug(start.toString());
		logger.debug(end.toString());

//		Period p = new Period(start, end);
//		(p.getYears() * 365) 
//		p.getDays()

		Interval interval = new Interval(start.toDate().getTime(), end.toDate().getTime());
		Period period = interval.toPeriod();

		System.out.printf("%d years, %d months, %d days, %d hours, %d minutes, %d seconds%n", period.getYears(), period.getMonths(),
				period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds());
	}

	@Test
	public void plusDay() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss:SSS");
		DateTime dt = new DateTime(2020, 2, 29, 23, 59, 59, 10);
		DateTime newInstance = dt.plusDays(1);
		Assert.assertEquals("2020-03-01 23:59:59:010", newInstance.toString(formatter));
		newInstance = dt.plus(Period.days(1));
		Assert.assertEquals("2020-03-01 23:59:59:010", newInstance.toString(formatter));
		newInstance = dt.plus(new Duration(24L * 60L * 60L * 1000L));
		Assert.assertEquals("2020-03-01 23:59:59:010", newInstance.toString(formatter));
	}

	@Test
	public void calculateMonth() {
		DateTime dt = new DateTime(2017, 3, 31, 00, 00, 00);
		DateTime a = dt.minusMonths(1);
		Assert.assertEquals(new DateTime(2017, 2, 28, 00, 00, 00).toString(), a.toString());
		DateTime b = dt.plusMonths(1);
		Assert.assertEquals(new DateTime(2017, 4, 30, 00, 00, 00).toString(), b.toString());
	}

	/**
	 * 일수만 계산하기
	 * 
	 * @author fixalot
	 */
	@Test
	public void calculatePeriodJustDays() {
		DateTime start = new DateTime("2017-01-01");
		DateTime end = new DateTime("2017-05-03");
		Assert.assertEquals(122, Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays());
		end = new DateTime("2017-01-01");
		Assert.assertEquals(0, Days.daysBetween(start, end).getDays());
		end = new DateTime("2017-01-02");
		Assert.assertEquals(1, Days.daysBetween(start, end).getDays());
		end = new DateTime("2017-01-03");
		Assert.assertEquals(2, Days.daysBetween(start, end).getDays());
		end = new DateTime("2017-01-05"); // 3박4일
		Assert.assertEquals(4, Days.daysBetween(start, end).getDays());
	}

	/**
	 * Interval로 계산하는 방법
	 * 
	 * @author fixalot
	 */
	@Test
	public void calculatePeriodWithInterval() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime start = formatter.parseDateTime("2017-01-01");
		DateTime end = formatter.parseDateTime("2017-01-03");

		Interval interval = new Interval(start.toDate().getTime(), end.toDate().getTime());
		org.joda.time.Period period = interval.toPeriod();

		logger.debug(String.format("%d years, %d months, %d days, %d hours, %d minutes, %d seconds%n", period.getYears(),
				period.getMonths(), period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds()));
	}

	@Test
	public void calculatePeriodWithInterval2() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime now = new DateTime();
		DateTime dt1 = now.withYear(2020).withMonthOfYear(2).withDayOfMonth(29);
		DateTime dt2 = now.withYear(2020).withMonthOfYear(3).withDayOfMonth(01);
		Assert.assertEquals("2020-02-29", dt1.toString(format));
		Interval interval = new Interval(dt1, dt2);
		Assert.assertEquals("2020-02-29", interval.getStart().toString(format));
	}

	/**
	 * Interval을 스킵하고 Period를 직접 사용하는 방법
	 * 
	 * @author fixalot
	 */
	@Test
	public void calculatePeriodWithPeriodFormatter() {
		DateTime start = new DateTime(2004, 12, 25, 0, 0, 0, 0);
		DateTime end = new DateTime(2006, 1, 1, 0, 0, 0, 0);

		// period of 1 year and 7 days
		Period period = new Period(start, end);

		PeriodFormatter formatter = new PeriodFormatterBuilder().appendYears().appendSuffix(" years ago\n").appendMonths()
				.appendSuffix(" months ago\n").appendWeeks().appendSuffix(" weeks ago\n").appendDays().appendSuffix(" days ago\n")
				.appendHours().appendSuffix(" hours ago\n").appendMinutes().appendSuffix(" minutes ago\n").appendSeconds()
				.appendSuffix(" seconds ago\n").printZeroNever().toFormatter();

		logger.debug(period.toString(formatter));
	}

	/**
	 * 가장 간단한 방법이지만 문자열로만 사용할 수 있는 방법
	 * 
	 * @author fixalot
	 */
	@Test
	public void calculatePeriodWithPeriod() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime start = formatter.parseDateTime("2017-01-01");
		DateTime end = formatter.parseDateTime("2017-01-03");
		Period period = new Period(start, end);
		Assert.assertEquals("2 days", PeriodFormat.getDefault().print(period));
	}

	/**
	 * 날짜 비교
	 * 
	 * @author fixalot
	 */
	@Test
	public void compare() {
		DateTime sometime = new DateTime("2017-04-18T01:00:00.000+09:00");
		DateTime oneMinuteLater = new DateTime("2017-04-18T01:01:00.000+09:00");

		Assert.assertTrue(sometime.isBefore(oneMinuteLater));
		Assert.assertTrue(oneMinuteLater.isAfter(sometime));

		Assert.assertTrue(sometime.getMillis() < oneMinuteLater.getMillis());
		Assert.assertEquals(1492444800000L, sometime.getMillis());
		Assert.assertEquals(1492444860000L, oneMinuteLater.getMillis());
		Assert.assertEquals(60000, oneMinuteLater.getMillis() - sometime.getMillis());
		Assert.assertEquals(1, (oneMinuteLater.getMillis() - sometime.getMillis()) / 1000 / 60); // 1부운 차이

		DateTime a = new DateTime("2017-01-01");
		DateTime b = new DateTime("2017-01-03");

		DateTime c = new DateTime("2017-01-02");

		Assert.assertEquals(-1, a.compareTo(c)); // -1: a는 c보다 이전
		Assert.assertEquals(0, c.compareTo(c)); // 0: c는 d와 같음
		Assert.assertEquals(1, b.compareTo(c)); // 1: b는 c보다 이후
	}

	@Test
	public void shouldEqual() {
//		Assert.assertEquals(new DateTime(), DateTime.now()); // 호출시점에 따라 몇 밀리초 차이로 같지 않을 수 있음
		Assert.assertEquals(new DateTime(2020, 2, 29, 23, 59, 59, 10), new DateTime().withYear(2020).withMonthOfYear(2).withDayOfMonth(29)
				.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(10));

		// 기본적으론 비슷하지만
		DateTime a = new DateTime("2017-01-02");
		DateTime b = new DateTime("2017-01-02");
		Assert.assertTrue(a.isEqual(b));
		Assert.assertTrue(a.equals(b));

		a = new DateTime("2017-01-02T01:00:00", DateTimeZone.UTC); // GMT/UTC
		b = new DateTime("2017-01-02T10:00:00", DateTimeZone.forID("Asia/Seoul")); // GMT/UTC보다 9시간 빠름

		// isEqual은 밀리초(GMT/UTC 기준)가 같은지 비교한다.
		logger.debug(String.valueOf(a.getMillis()));
		logger.debug(String.valueOf(b.getMillis()));
		Assert.assertTrue(a.isEqual(b)); // 영국의 01시는 한국의 10시와 같다.

		// equals는 timezone(혹은 chronology)을 적용한 시각이 같은지를 비교한다.
		Assert.assertEquals("2017-01-02T01:00:00.000Z", a.toString());
		Assert.assertEquals("2017-01-02T10:00:00.000+09:00", b.toString());
		Assert.assertFalse(a.equals(b)); // 01시와 10시를 비교
	}
}
