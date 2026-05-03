package sit707_tasks;

/**
 * Enhanced DateUtil class with extended functionality for better 
 * Boundary Value Analysis and Equivalence Class Testing.
 * 
 * @author Karunjot Singh
 * @version 2.0
 * @date April 2026
 */
public class DateUtil {

	// Months in order 0-11 maps to January-December.
	private static String[] MONTHS = new String[] {
			"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"
	};

	private int day, month, year;

	/**
	 * Constructs object from given day, month and year.
	 * 
	 * @param day   - day of month (1-31)
	 * @param month - month of year (1-12)
	 * @param year  - year (1700-2024)
	 * @throws RuntimeException if date is invalid
	 */
	public DateUtil(int day, int month, int year) {
		// Is supplied day/month/year a valid date?
		if (day < 1 || day > 31)
			throw new RuntimeException("Invalid day: " + day + ", expected range 1-31");
		if (month < 1 || month > 12)
			throw new RuntimeException("Invalid month: " + month + ", expected range 1-12");
		if (year < 1700 || year > 2024)
			throw new RuntimeException("Invalid year: " + year + ", expected range 1700-2024");
		if (day > monthDuration(month, year))
			throw new RuntimeException("Invalid day: " + day + ", max day: " + monthDuration(month, year));
		
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	/**
	 * Increment one day.
	 */
	public void increment() {
		if (day < monthDuration(month, year)) {
			// At least 1 day remaining in current month of year.
			day++;
		} else if (month < 12) {
			// Last day of a month AND month is less than December, so +1d is first day of next month.
			day = 1;
			month++;
		} else {
			// Month is December, so +1d is 1st January next year.
			day = 1;
			month = 1;
			year++;
		}
	}

	/**
	 * Decrement one day from current date.
	 */
	public void decrement() {
		if (day > 1) {
			day--;
		} else if (month > 1) {
			month--;
			day = monthDuration(month, year);
		} else {
			month = 12;
			year--;
			day = monthDuration(month, year);
		}
	}

	/**
	 * Calculate duration of current month of year.
	 * 
	 * @param month - month (1-12)
	 * @param year  - year
	 * @return number of days in the given month
	 */
	public static int monthDuration(int month, int year) {
		if (month == 2 && year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
			// February leap year (corrected leap year logic)
			return 29;
		} else if (month == 2) {
			// normal 28 days February
			return 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			// 30 days' months
			return 30;
		}
		return 31; // rest are 31 days' months.
	}

	/**
	 * Returns a new DateUtil object representing the date after adding the specified number of days.
	 * 
	 * This method creates a new DateUtil object rather than modifying the current one (immutable approach).
	 * 
	 * Equivalence Classes:
	 * - Class 1: days < -36500 (out of range, throws exception)
	 * - Class 2: days = -36500 to -1 (past dates)
	 * - Class 3: days = 0 (same date)
	 * - Class 4: days = 1 to +36500 (future dates)
	 * - Class 5: days > 36500 (out of range, throws exception)
	 * 
	 * Boundary Values:
	 * - -36500 (minimum valid range)
	 * - -1 (one day before)
	 * - 0 (same date)
	 * - 1 (one day after)
	 * - 36500 (maximum valid range)
	 * 
	 * @param days - number of days to add (range: -36500 to +36500, approx 100 years)
	 * @return new DateUtil object with the calculated date
	 * @throws RuntimeException if days is outside valid range or resulting date is invalid
	 */
	public DateUtil getDateAfterDays(int days) {
		// Validate input range
		if (days < -36500 || days > 36500) {
			throw new RuntimeException("Invalid days: " + days + ", expected range -36500 to +36500");
		}

		// Create a working copy to manipulate
		DateUtil result = new DateUtil(this.day, this.month, this.year);

		// Handle positive days (increment)
		if (days > 0) {
			for (int i = 0; i < days; i++) {
				result.increment();
			}
		} 
		// Handle negative days (decrement)
		else if (days < 0) {
			for (int i = 0; i < Math.abs(days); i++) {
				result.decrement();
			}
		}
		// days == 0: result is same as current date

		return result;
	}

	/**
	 * Calculates the number of days between this date and another date.
	 * 
	 * Positive result means 'other' date is in the future.
	 * Negative result means 'other' date is in the past.
	 * Zero result means dates are the same.
	 * 
	 * Equivalence Classes:
	 * - Class 1: other date is before this date (negative result)
	 * - Class 2: other date is same as this date (zero result)
	 * - Class 3: other date is after this date (positive result)
	 * - Class 4: dates spanning different years
	 * - Class 5: dates spanning different months
	 * - Class 6: dates in same month
	 * 
	 * @param other - another DateUtil object
	 * @return number of days between this date and other date
	 * @throws RuntimeException if other is null
	 */
	public int getDaysBetween(DateUtil other) {
		if (other == null) {
			throw new RuntimeException("Other date cannot be null");
		}

		// Create copies to work with
		DateUtil current = new DateUtil(this.day, this.month, this.year);
		DateUtil target = new DateUtil(other.day, other.month, other.year);

		int daysDifference = 0;

		// If current date is before target date, increment current
		if (isBeforeDate(current, target)) {
			while (!datesAreEqual(current, target)) {
				current.increment();
				daysDifference++;
			}
		} 
		// If current date is after target date, decrement current
		else if (isBeforeDate(target, current)) {
			while (!datesAreEqual(current, target)) {
				current.decrement();
				daysDifference--;
			}
		}
		// If dates are equal, daysDifference remains 0

		return daysDifference;
	}

	/**
	 * Helper method to check if date1 is before date2.
	 * 
	 * @param date1 - first date
	 * @param date2 - second date
	 * @return true if date1 is before date2, false otherwise
	 */
	private static boolean isBeforeDate(DateUtil date1, DateUtil date2) {
		if (date1.year != date2.year) {
			return date1.year < date2.year;
		}
		if (date1.month != date2.month) {
			return date1.month < date2.month;
		}
		return date1.day < date2.day;
	}

	/**
	 * Helper method to check if two dates are equal.
	 * 
	 * @param date1 - first date
	 * @param date2 - second date
	 * @return true if dates are equal, false otherwise
	 */
	private static boolean datesAreEqual(DateUtil date1, DateUtil date2) {
		return date1.day == date2.day && date1.month == date2.month && date1.year == date2.year;
	}

	/**
	 * User friendly output.
	 */
	public String toString() {
		return day + " " + MONTHS[month - 1] + " " + year;
	}

}