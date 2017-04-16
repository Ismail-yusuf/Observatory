package observatory

import scala.math._
import Helpers._

case class Location(lat: Double, lon: Double) {
  def distanceFrom (that: Location) = {
    val la1 = toRadians(this.lat)
    val la2 = toRadians(that.lat)
    val lo1 = toRadians(this.lon)
    val lo2 = toRadians(that.lon)

    val deltaLat = abs(la1 - la2)
    val deltaLon = abs(lo1 - lo2)

    val fraction = pow(sin(deltaLat / 2), 2) + cos(la1) * cos(la2) * pow(sin(deltaLon / 2), 2)

    2 * asin(sqrt(fraction)) * earthRadius
  }
}

case class Color(red: Int, green: Int, blue: Int) {
  def + (that: Color) = Color(this.red + that.red, this.green + that.green, this.blue + that.blue)
  def - (that: Color) = Color(this.red - that.red, this.green - that.green, this.blue - that.blue)
  def * (double: Double) = Color(round(this.red * double).toInt, round(this.green * double).toInt, round(this.blue * double).toInt)
  def / (double: Double) = Color(round(this.red / double).toInt, round(this.green / double).toInt, round(this.blue / double).toInt)
  def == (that: Color) = this.red == that.red && this.green == that.green && this.blue == that.blue
  def < (that: Color) = this.red + this.blue + this.green < that.red + that.blue + that.green
  def > (that: Color) = this.red + this.blue + this.green > that.red + that.blue + that.green
  def absolute = Color(abs(red), abs(green), abs(blue))
}

