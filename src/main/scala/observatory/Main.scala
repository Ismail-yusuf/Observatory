package observatory

import java.io.File

import observatory.Extraction._
import observatory.Visualization._

object Main extends App {
  val year = args(0).toInt

  val col1 = Color(0, 0, 0)
  val col2 = Color(33, 0, 107)
  val col3 = Color(255, 0, 255)
  val col4 = Color(0, 0, 255)
  val col5 = Color(0, 255, 255)
  val col6 = Color(255, 255, 0)
  val col7 = Color(255, 0, 0)
  val col8 = Color(255, 255, 255)

  val colors = (-27.toDouble, col3) :: (-60.toDouble, col1) :: (60.toDouble, col8) :: (32.toDouble, col7) ::
    (0.toDouble, col5) :: (-50.toDouble, col2) :: (-15.toDouble, col4) :: (12.toDouble, col6) :: Nil

  val temperatures = locationYearlyAverageRecords(locateTemperatures(year, s"/$year.csv", "/stations.csv"))

  val image = visualize(temperatures, colors)
  image.output(new File(s"/output/${year}_temps.png"))
}
