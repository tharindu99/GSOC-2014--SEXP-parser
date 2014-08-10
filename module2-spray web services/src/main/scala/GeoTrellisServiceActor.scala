package tutorial

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


   //localhost:8999/
  val rootRoute = {
      path("add") {
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
      
      pathPrefix("raster" / Segment){ slug =>  // string write to ceperate file 
          //Construct an object with instructions to fetch the raster
          //val expression: String = slug
          val r1: RasterSource = RasterSource("SBN_farm_mkt")
          val r2: RasterSource = RasterSource("SBN_farm_mkt")

          path("added") {
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
          }  
        }
  }

}
