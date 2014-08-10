package spray_webServices_module

import akka.actor._
import spray.routing.{ExceptionHandler, HttpService}
import geotrellis.source.{ValueSource, RasterSource}
import geotrellis.render.ColorRamps
import geotrellis.process.{Error, Complete}
import spray.http.MediaTypes
import spray.http.StatusCodes.InternalServerError
import spray.util.LoggingContext
import geotrellis._
import geotrellis.process.Error
import geotrellis.process.Complete
import geotrellis.statistics.Histogram

class GeoTrellisServiceActor extends GeoTrellisService with Actor {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  def receive = runRoute(rootRoute)
}


trait GeoTrellisService extends HttpService {

  implicit def myExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: Exception =>
        requestUri { uri =>
          complete(InternalServerError, s"Exception: ${e.getMessage}" )
        }
    }

  def rasterAsPng(name: String) = {
    val raster = RasterSource(name)

    raster.renderPng(ColorRamps.BlueToRed).run match {
      case Complete(img, hist) => img
      case Error(msg, trace) => throw new RuntimeException(msg)
    }
  }

  val rootRoute = {
    path("check") {
      get { complete("Works..") }
    } ~
    path("operation1") {
        get{
            respondWithMediaType(MediaTypes.`image/png`) {
              complete{
                val r1: RasterSource = RasterSource("SBN_farm_mkt")
                val r2: RasterSource = RasterSource("SBN_farm_mkt")
                val added  = (r1*5) + (r2*2)
                val weightedOverlay = added / (5+2)
                //val rendered = weightedOverlay.renderPng(ColorRamps.BlueToRed).get

                weightedOverlay.renderPng(ColorRamps.BlueToRed).get
              }
            }
        }
      } ~
    pathPrefix("raster" / Segment) { slug =>
      //Construct an object with instructions to fetch the raster
      val raster: RasterSource = RasterSource(slug)
      val r1: RasterSource = RasterSource("SBN_farm_mkt")
      val r2: RasterSource = RasterSource("SBN_farm_mkt")

      path("operation") {
        get{
          parameter('cutoff.as[Int]) { cutoff =>
            respondWithMediaType(MediaTypes.`image/png`) {
              complete{
                val mask = raster.localMap{ x => if (x > cutoff) 1 else NODATA }
                mask.renderPng(ColorRamps.BlueToRed).get
              }
            }
          }
        }
      } ~
      path("check_operation") {
        get {
          respondWithMediaType(MediaTypes.`application/json`) {
            complete {
              val histogramSource: ValueSource[Histogram] = raster.histogram()
              //No processing has been done yet
              val histogram = histogramSource.get
              val stats = histogram.generateStatistics()
              s"{mean: ${stats.mean}, histogram: ${histogram.toJSON} }"
            }
          }
        }
      }
    } 

  }

}
