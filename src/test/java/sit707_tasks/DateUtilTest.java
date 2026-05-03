package sit707_tasks;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * Comprehensive test suite for DateUtil class demonstrating both
 * Boundary Value Analysis (BVA) and Equivalence Class Testing (ECT).
 * 
 * @author Karunjot Singh
 * @studentId 225616844
 * @version 2.0
 * @date April 2026
 */
public class DateUtilTest {
	
	// ============================================================
	// STUDENT IDENTITY TESTS
	// ============================================================
	
	@Test
	public void testStudentIdentity() {
		String studentId = "225616844";
		Assert.assertNotNull("Student ID is 225616844", studentId);
	}
	
	@Test
	public void testStudentName() {
		String studentName = "Karunjot Singh";
		Assert.assertNotNull("Student name is Karunjot Singh", studentName);
	}

	// ============================================================
	// ORIGINAL FUNCTIONALITY TESTS (3.1P/3.2C)
	// Increment/Decrement with BVA and ECT
	// ============================================================
	
	@Test
	public void testMaxJanuary31ShouldIncrementToFebruary1() {
		// BVA: Maximum day value in month (boundary: day 31 → month boundary)
		DateUtil date = new DateUtil(31, 1, 2024);
		System.out.println("BVA: january31ShouldIncrementToFebruary1 > " + date);
		date.increment();
		System.out.println("Result: " + date);
		Assert.assertEquals(2, date.getMonth());
		Assert.assertEquals(1, date.getDay());
	}
	
	@Test
	public void testMaxJanuary31ShouldDecrementToJanuary30() {
		// BVA: Maximum day value in month (boundary: day 31 - 1)
		DateUtil date = new DateUtil(31, 1, 2024);
		System.out.println("BVA: january31ShouldDecrementToJanuary30 > " + date);
		date.decrement();
		System.out.println("Result: " + date);
		Assert.assertEquals(30, date.getDay());
		Assert.assertEquals(1, date.getMonth());
	}
	
	@Test
	public void testMinJanuary1ShouldIncrementToJanuary2() {
		// BVA: Minimum day value in month (boundary: day 1 + 1)
		DateUtil date = new DateUtil(1, 1, 2024);
		System.out.println("BVA: testMinJanuary1ShouldIncrementToJanuary2 > " + date);
		date.increment();
		System.out.println("Result: " + date);
		Assert.assertEquals(2, date.getDay());
		Assert.assertEquals(1, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}
	
	@Test
	public void testNominalJanuary() {
		// ECT: Nominal/typical day in middle of month (equivalence class: normal date)
		int rand_day_1_to_31 = 1 + new Random().nextInt(31);
		DateUtil date = new DateUtil(rand_day_1_to_31, 1, 2024);
		System.out.println("ECT: testJanuaryNominal > " + date);
		date.increment();
		System.out.println("Result: " + date);
	}
	
	@Test
	public void testJune1ShouldIncrementToJune2() {
		// ECT: Nominal day (1) in 30-day month
		DateUtil date = new DateUtil(1, 6, 1994);
		date.increment();
		Assert.assertEquals(2, date.getDay());
		Assert.assertEquals(6, date.getMonth());
		Assert.assertEquals(1994, date.getYear());
	}
	
	@Test
	public void testJune30ShouldIncrementToJuly1() {
		// BVA: Last day of 30-day month (boundary: month transition)
		DateUtil date = new DateUtil(30, 6, 1994);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(7, date.getMonth());
		Assert.assertEquals(1994, date.getYear());
	}
	
	@Test
	public void testJune1ShouldDecrementToMay31() {
		// BVA: First day of month (boundary: previous month transition)
		DateUtil date = new DateUtil(1, 6, 1994);
		date.decrement();
		Assert.assertEquals(31, date.getDay());
		Assert.assertEquals(5, date.getMonth());
		Assert.assertEquals(1994, date.getYear());
	}
	
	@Test
	public void testJune30ShouldDecrementToJune29() {
		// ECT: Nominal decrement (within same month)
		DateUtil date = new DateUtil(30, 6, 1994);
		date.decrement();
		Assert.assertEquals(29, date.getDay());
		Assert.assertEquals(6, date.getMonth());
		Assert.assertEquals(1994, date.getYear());
	}
	
	@Test(expected = RuntimeException.class)
	public void testJune31ShouldBeInvalid() {
		// ECT: Invalid equivalence class (non-existent date in 30-day month)
		new DateUtil(31, 6, 1994);
	}
	
	@Test
	public void testLeapYearFeb28ToFeb29() {
		// BVA: Leap year boundary (day 28 → 29 in leap year)
		DateUtil date = new DateUtil(28, 2, 2024);
		date.increment();
		Assert.assertEquals(29, date.getDay());
		Assert.assertEquals(2, date.getMonth());
	}
	
	@Test
	public void testLeapYearFeb29ToMarch1() {
		// BVA: Leap year maximum (day 29 → month boundary in leap year)
		DateUtil date = new DateUtil(29, 2, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(3, date.getMonth());
	}
	
	@Test
	public void testNonLeapYearFeb28ToMarch1() {
		// BVA: Non-leap year boundary (day 28 → month boundary in non-leap year)
		DateUtil date = new DateUtil(28, 2, 2023);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(3, date.getMonth());
	}
	
	// ============================================================
	// NEW FUNCTIONALITY TESTS: getDateAfterDays()
	// Boundary Value Analysis and Equivalence Class Testing
	// ============================================================
	
	@Test
	public void testGetDateAfterDaysZero() {
		// BVA & ECT: Boundary value - Zero days (same date)
		DateUtil date = new DateUtil(15, 6, 2024);
		DateUtil result = date.getDateAfterDays(0);
		Assert.assertEquals(15, result.getDay());
		Assert.assertEquals(6, result.getMonth());
		Assert.assertEquals(2024, result.getYear());
	}
	
	@Test
	public void testGetDateAfterDaysOne() {
		// BVA: Boundary value - One day forward (minimum positive)
		DateUtil date = new DateUtil(15, 6, 2024);
		DateUtil result = date.getDateAfterDays(1);
		Assert.assertEquals(16, result.getDay());
		Assert.assertEquals(6, result.getMonth());
		Assert.assertEquals(2024, result.getYear());
	}
	
	@Test
	public void testGetDateAfterDaysNegativeOne() {
		// BVA: Boundary value - One day backward (maximum negative)
		DateUtil date = new DateUtil(15, 6, 2024);
		DateUtil result = date.getDateAfterDays(-1);
		Assert.assertEquals(14, result.getDay());
		Assert.assertEquals(6, result.getMonth());
		Assert.assertEquals(2024, result.getYear());
	}
	
	@Test
	public void testGetDateAfterDaysPositive30() {
		// ECT: Equivalence class - Positive days, within-month (1 to 30 days)
		DateUtil date = new DateUtil(1, 6, 2024);
		DateUtil result = date.getDateAfterDays(29);
		Assert.assertEquals(30, result.getDay());
		Assert.assertEquals(6, result.getMonth());
		Assert.assertEquals(2024, result.getYear());
	}
	
	@Test
	public void testGetDateAfterDaysMonthTransition() {
		// BVA: Month boundary - Crossing month boundary (30 to 31 days forward)
		DateUtil date = new DateUtil(1, 6, 2024);
		DateUtil result = date.getDateAfterDays(30);
		Assert.assertEquals(1, result.getDay());
		Assert.assertEquals(7, result.getMonth());
		Assert.assertEquals(2024, result.getYear());
	}
	
	@Test
	public void testGetDateAfterDaysYearTransition() {
		// BVA: Year boundary - Crossing year boundary (366+ days for leap year)
		DateUtil date = new DateUtil(1, 1, 2024);
		DateUtil result = date.getDateAfterDays(366); // 2024 is leap year, so 366 days
		Assert.assertEquals(1, result.getDay());
		Assert.assertEquals(1, result.getMonth());
		Assert.assertEquals(2025, result.getYear());
	}
	
	@Test
	public void testGetDateAfterDaysLeapYearCrossing() {
		// BVA: Leap year special case (crossing Feb 29 in leap year)
		DateUtil date = new DateUtil(28, 2, 2024);
		DateUtil result = date.getDateAfterDays(1);
		Assert.assertEquals(29, result.getDay());
		Assert.assertEquals(2, result.getMonth());
		Assert.assertEquals(2024, result.getYear());
	}
	
	@Test
	public void testGetDateAfterDaysLargePositive() {
		// ECT: Equivalence class - Large positive days (stay within valid year range)
		DateUtil date = new DateUtil(15, 6, 2020);
		DateUtil result = date.getDateAfterDays(200); // 200 days forward
		Assert.assertTrue("Year should be in valid range", result.getYear() <= 2024);
	}
	
	@Test
	public void testGetDateAfterDaysLargeNegative() {
		// ECT: Equivalence class - Large negative days (stay within valid year range)
		DateUtil date = new DateUtil(15, 6, 2024);
		DateUtil result = date.getDateAfterDays(-200); // 200 days backward
		Assert.assertTrue("Year should be in valid range", result.getYear() >= 1700);
	}
	
	@Test
	public void testGetDateAfterDaysNegativeMonthTransition() {
		// BVA: Month boundary (backward) - Crossing month boundary (negative days)
		DateUtil date = new DateUtil(1, 7, 2024);
		DateUtil result = date.getDateAfterDays(-1);
		Assert.assertEquals(30, result.getDay());
		Assert.assertEquals(6, result.getMonth());
		Assert.assertEquals(2024, result.getYear());
	}
	
	@Test
	public void testGetDateAfterDaysNegativeLeapYearCrossing() {
		// BVA: Leap year boundary (backward) - Crossing Feb 29 boundary
		DateUtil date = new DateUtil(1, 3, 2024);
		DateUtil result = date.getDateAfterDays(-1);
		Assert.assertEquals(29, result.getDay());
		Assert.assertEquals(2, result.getMonth());
		Assert.assertEquals(2024, result.getYear());
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetDateAfterDaysExceedsMaximumRange() {
		// BVA: Out-of-range boundary - Exceeds maximum valid range
		DateUtil date = new DateUtil(15, 6, 2024);
		date.getDateAfterDays(36501); // exceeds +36500 limit
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetDateAfterDaysExceedsMinimumRange() {
		// BVA: Out-of-range boundary - Exceeds minimum valid range
		DateUtil date = new DateUtil(15, 6, 2024);
		date.getDateAfterDays(-36501); // exceeds -36500 limit
	}
	
	// ============================================================
	// NEW FUNCTIONALITY TESTS: getDaysBetween()
	// Boundary Value Analysis and Equivalence Class Testing
	// ============================================================
	
	@Test
	public void testGetDaysBetweenSameDate() {
		// BVA & ECT: Boundary value - Same date (zero difference)
		DateUtil date1 = new DateUtil(15, 6, 2024);
		DateUtil date2 = new DateUtil(15, 6, 2024);
		int daysBetween = date1.getDaysBetween(date2);
		Assert.assertEquals(0, daysBetween);
	}
	
	@Test
	public void testGetDaysBetweenOneDay() {
		// BVA: Boundary value - One day difference (minimum positive)
		DateUtil date1 = new DateUtil(15, 6, 2024);
		DateUtil date2 = new DateUtil(16, 6, 2024);
		int daysBetween = date1.getDaysBetween(date2);
		Assert.assertEquals(1, daysBetween);
	}
	
	@Test
	public void testGetDaysBetweenNegativeOneDay() {
		// BVA: Boundary value - One day difference backward (maximum negative)
		DateUtil date1 = new DateUtil(16, 6, 2024);
		DateUtil date2 = new DateUtil(15, 6, 2024);
		int daysBetween = date1.getDaysBetween(date2);
		Assert.assertEquals(-1, daysBetween);
	}
	
	@Test
	public void testGetDaysBetweenSameMonth() {
		// ECT: Equivalence class - Dates in same month
		DateUtil date1 = new DateUtil(1, 6, 2024);
		DateUtil date2 = new DateUtil(15, 6, 2024);
		int daysBetween = date1.getDaysBetween(date2);
		Assert.assertEquals(14, daysBetween);
	}
	
	@Test
	public void testGetDaysBetweenDifferentMonths() {
		// ECT: Equivalence class - Dates spanning different months
		DateUtil date1 = new DateUtil(30, 6, 2024);
		DateUtil date2 = new DateUtil(1, 7, 2024);
		int daysBetween = date1.getDaysBetween(date2);
		Assert.assertEquals(1, daysBetween);
	}
	
	@Test
	public void testGetDaysBetweenDifferentYears() {
		// ECT: Equivalence class - Dates spanning different years
		DateUtil date1 = new DateUtil(15, 6, 2023);
		DateUtil date2 = new DateUtil(15, 6, 2024);
		int daysBetween = date1.getDaysBetween(date2);
		Assert.assertEquals(366, daysBetween); // 2024 is leap year
	}
	
	@Test
	public void testGetDaysBetweenLeapYearCrossing() {
		// BVA: Leap year special case - Crossing Feb 29
		DateUtil date1 = new DateUtil(28, 2, 2024);
		DateUtil date2 = new DateUtil(1, 3, 2024);
		int daysBetween = date1.getDaysBetween(date2);
		Assert.assertEquals(2, daysBetween); // Feb 28 → Feb 29 → Mar 1
	}
	
	@Test
	public void testGetDaysBetweenReversedLeapYearCrossing() {
		// BVA: Leap year special case (backward) - Crossing Feb 29 backward
		DateUtil date1 = new DateUtil(1, 3, 2024);
		DateUtil date2 = new DateUtil(28, 2, 2024);
		int daysBetween = date1.getDaysBetween(date2);
		Assert.assertEquals(-2, daysBetween); // Mar 1 → Feb 29 → Feb 28
	}
	
	@Test
	public void testGetDaysBetweenLargeDifference() {
		// ECT: Equivalence class - Large date difference (multi-year)
		DateUtil date1 = new DateUtil(15, 6, 2020);
		DateUtil date2 = new DateUtil(15, 6, 2024);
		int daysBetween = date1.getDaysBetween(date2);
		Assert.assertTrue("Days between should be approximately 1461 for 4 years", daysBetween > 1400);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetDaysBetweenNullDate() {
		// ECT: Invalid equivalence class - Null parameter
		DateUtil date1 = new DateUtil(15, 6, 2024);
		date1.getDaysBetween(null);
	}
	
	// ============================================================
	// IMMUTABILITY VERIFICATION TESTS
	// Verify that getDateAfterDays() doesn't modify original date
	// ============================================================
	
	@Test
	public void testGetDateAfterDaysDoesNotModifyOriginal() {
		// Verify immutability: Original date should remain unchanged
		DateUtil originalDate = new DateUtil(15, 6, 2024);
		DateUtil result = originalDate.getDateAfterDays(30);
		
		// Original should remain unchanged
		Assert.assertEquals(15, originalDate.getDay());
		Assert.assertEquals(6, originalDate.getMonth());
		Assert.assertEquals(2024, originalDate.getYear());
		
		// Result should be modified
		Assert.assertEquals(15, result.getDay());
		Assert.assertEquals(7, result.getMonth());
		Assert.assertEquals(2024, result.getYear());
	}
}