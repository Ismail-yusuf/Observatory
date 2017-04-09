package observatory


import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers

@RunWith(classOf[JUnitRunner])
class VisualizationTest extends FunSuite with Checkers {

  test("Distance should be calculated correctly.") {
    val p1 = Location(2.0, 2.0)
    val p2 = Location(-2.0, 5.0)
    val p3 = Location(6.0, -1.0)
    val p4 = Location(10.0, 8.0)

    val dist1 = Visualization.distanceOfPoints(p1, p2)
    val dist2 = Visualization.distanceOfPoints(p3, p1)
    val dist3 = Visualization.distanceOfPoints(p1, p4)

    assert(dist1 === 5.0)
    assert(dist2 === 5.0)
    assert(dist3 === 10.0)
  }

  test("Weighed temp average should be calculated correctly") {
    val distances = (5.0, 25.0) :: (5.0, 5.0) :: (10.0, 20.0) :: Nil

    val result = Visualization.spatialInterpolateTemp(distances)

    assert(result === 16.0)
  }

  test("Temperature should be predicted correctly.") {
    val loc1 = Location(-2.0, 5.0)
    val loc2 = Location(6.0, -1.0)
    val loc3 = Location(10.0, 8.0)

    val locations = (loc1, 25.0) :: (loc2, 5.0) :: (loc3, 20.0) :: Nil

    val locToCalculate1 = Location(2.0, 2.0)
    val locToCalculate2 = Location(6.0, -1.0)

    val res1 = Visualization.predictTemperature(locations, locToCalculate1)
    val res2 = Visualization.predictTemperature(locations, locToCalculate2)

    assert(res1 === 16.0)
    assert(res2 === 5.0)
  }
}
