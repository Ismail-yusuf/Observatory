package observatory


import observatory.Visualization._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers
import org.scalatest.{FunSuite, ShouldMatchers}
import math._

@RunWith(classOf[JUnitRunner])
class VisualizationTest extends FunSuite with Checkers with ShouldMatchers {

  test("Temperature should be closer if location is closer to station.") {
    val loc1 = Location(0,0)
    val temp1 = 10d
    val loc2 = Location(-100,-100)
    val temp2 = 50d
    val list = (loc1, temp1) :: (loc2, temp2) :: Nil

    val result1 = predictTemperature(list, Location(-40, -40))
    val result2 = predictTemperature(list, Location(-60, -60))
    assert(abs(temp1 - result1) < abs(temp2 - result1))
    assert(abs(temp1 - result2) > abs(temp2 - result2))
  }

  test("Color should be predicted correctly.") {
    val col1 = Color(0, 0, 0)
    val col2 = Color(33, 0, 107)
    val col3 = Color(255, 0, 255)
    val col4 = Color(0, 0, 255)
    val col5 = Color(0, 255, 255)
    val col6 = Color(255, 255, 0)
    val col7 = Color(255, 0, 0)
    val col8 = Color(255, 255, 255)

    val tempColors = (-27d, col3) :: (-60d, col1) :: (60d, col8) :: (32d, col7) :: (0d, col5) :: (-50d, col2) :: (-15d, col4) :: (12d, col6) :: Nil

    val res1 = interpolateColor(tempColors, -100)
    val res2 = interpolateColor(tempColors, 70)
    val res3 = interpolateColor(tempColors, 32)
    val res4 = interpolateColor(tempColors, 6)
    val res5 = interpolateColor(tempColors, -23)
    val res6 = interpolateColor(tempColors, 39)

    assert(res1 === col1)
    assert(res2 === col8)
    assert(res3 === col7)
    assert(res4 === Color(128, 255, 128))
    assert(res5 === Color(170, 0, 255))
    assert(res6 === Color(255, 64, 64))
  }

  test("Color should be closer if temperature is closer") {
    val col1 = Color(255, 0, 0)
    val col2 = Color(0, 0, 255)
    val tempColors = (0d, col1) :: (100d, col2) :: Nil

    val result1 = interpolateColor(tempColors, 10)
    val result2 = interpolateColor(tempColors, 90)

    assert((col1 - result1).absolute < (col2 - result1).absolute)
    assert((col1 - result2).absolute > (col2 - result2).absolute)
  }

  test("Color interpolation should work with only one color.") {
    val col = Color(0, 0, 255)
    val colorList = (0.0, col) :: Nil

    assert(interpolateColor(colorList, -0.5) == col)
    assert(interpolateColor(colorList, 0.5) == col)
    assert(interpolateColor(colorList, 0.0) == col)
  }

  test("Visualize should return correctly sized image") {
    val img = visualize((Location(0.0, 0.0), 0.0) :: Nil, (0.0, Color(0, 0, 0)) :: Nil)

    assert(img.width === 360)
    assert(img.height === 180)
  }
}
