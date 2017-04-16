package observatory

import java.time.LocalDate

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import observatory.Extraction._

@RunWith(classOf[JUnitRunner])
class ExtractionTest extends FunSuite {

  test("Fahrenheits should be converted to Celsius correctly.") {
    val cel0 = fahrenheitToCelsius(32)
    val cel1 = fahrenheitToCelsius(33.8)
    val cel2 = fahrenheitToCelsius(35.6)
    val cel50 = fahrenheitToCelsius(122)
    val cel100 = fahrenheitToCelsius(212)

    assert(cel0 === 0.0)
    assert(cel1 === 1.0)
    assert(cel2 === 2.0)
    assert(cel50 === 50.0)
    assert(cel100 === 100.0)
  }

  test("Locations and temperatures should be calculated correctly.") {
    val actualResult = locateTemperatures(2015, "/testStation.csv", "/testDate.csv").toSet
    val expectedResult = Set(
      (LocalDate.of(2015, 8, 11), Location(37.35, -78.433), 27.3),
      (LocalDate.of(2015, 12, 6), Location(37.358, -78.438), 0.0),
      (LocalDate.of(2015, 1, 29), Location(37.358, -78.438), 2.0)
    )

    assert(actualResult === expectedResult)
  }

  test("Location average temps should be calculated correctly.") {
    val input = locateTemperatures(2015, "/testStation.csv", "/testDate.csv")
    val actualResult = locationYearlyAverageRecords(input).toSet
    val expectedResult = Set(
      (Location(37.35, -78.433), 27.3),
      (Location(37.358, -78.438), 1.0)
    )

    assert(actualResult === expectedResult)
  }
}