package observatory

import com.sksamuel.scrimage.{Image, Pixel}

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Double)], location: Location, smoothingFactor: Int = 2): Double = {
    val distances = temperatures.map { case (loc, temp) => (loc distanceFrom location, temp) }

    distances.find(_._1 == 0.0).map(_._2).getOrElse {

      val (temps, dists) = distances.map { case (dist, temp) =>
        (temp / math.pow(dist, smoothingFactor), 1 / math.pow(dist, smoothingFactor))
      }.unzip

      temps.sum / dists.sum
    }
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Double, Color)], value: Double): Color = {
    points.find(_._1 == value).map(_._2).getOrElse {

      val (lower, higher) = points.toList.sortBy(p => p._1).partition(p => p._1 < value)
      val bottom = lower.reverse.headOption
      val top = higher.headOption

      (bottom, top) match {
        case (Some((_, topColor)), None) => topColor

        case (None, Some((_, bottomColor))) => bottomColor

        case (Some((bottomValue, bottomColor)), Some((topValue, topColor))) =>
          (topColor - bottomColor) * ((value - bottomValue) / (topValue - bottomValue)) + bottomColor

        case _ => Color(0, 0, 0)
      }
    }
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Double)], colors: Iterable[(Double, Color)]): Image = {
    val locations = (for {
      lat <- 90 until -90 by -1
      lon <- -180 until 180 by 1
    } yield Location(lat, lon)).par

    val locationTemperatures = locations.map(l => predictTemperature(temperatures, l))
    val locationColors = locationTemperatures.map(t => interpolateColor(colors, t))
    val pixels = locationColors.map(c => Pixel(c.red, c.green, c.blue, 255)).toArray

    Image(360, 180, pixels)
  }
}

