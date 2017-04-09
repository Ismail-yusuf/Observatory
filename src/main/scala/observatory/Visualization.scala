package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import observatory.Helpers._

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Double)], location: Location): Double = {
    val distances = temperatures.map { case (loc, temp) => (distanceOfPoints(loc, location), temp) }
    distances.toMap.getOrElse(0.0, spatialInterpolateTemp(distances).toDouble)
  }

  def spatialInterpolateTemp(distances: Iterable[(Double, Double)]) = {
    val (temps, dists) = distances.map { case(dist, temp) => (dec(1 / dist * temp), dec(1 / dist)) }.unzip
    temps.sum / dists.sum
  }

  def distanceOfPoints(p1: Location, p2: Location) = math.sqrt(math.pow(p1.lat - p2.lat, 2) + math.pow(p1.lon - p2.lon, 2))

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Double, Color)], value: Double): Color = {
    points
    ???
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Double)], colors: Iterable[(Double, Color)]): Image = {
    ???
  }

}

