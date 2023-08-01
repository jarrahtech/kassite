/*
package com.jarrahtechnology.kassite.hex

import scala.scalajs.js.annotation._
import com.jarrahtechnology.util.Vector2
import com.jarrahtechnology.hex.*

@JSExportAll
final case class RectangularHexGridDisplay[Model, View, C <: CoordSystem, G <: RectangularHexGrid[(Model, View), C]](val grid: G, val hexRadius: Double, position: Vector2) {
    
  def fromPixel(pos: Vector2) = grid.coords.fromRadii((pos subtractPiecewise pixelCenter).divide(hexRadius))
  def hexFromPixel(pos: Vector2) = {
    val coord = fromPixel(pos)
    (coord, grid.hexAt(coord))
  }
  def toPixel(coord: Coord) = grid.coords.toRadii(coord).multiply(hexRadius) addPiecewise pixelCenter

  val pixelCenter = (gridCentreToOriginRadii multiply hexRadius) addPiecewise position
  def gridCentreToOriginRadii = { // shift from the center of the grid to the center of the (0,0) hex
      def shift = 
        if (coords.isHorizontal && coords.isEven && numRows>1) {
          Vector2(coords.hexRadiiWidth, 0)
        } else if (!coords.isHorizontal && coords.isEven && numColumns>1) {
          Vector2(0, coords.hexRadiiHeight)
        } else Vector2.zero

      (shift addPiecewise coords.hexRadiiDimensions subtractPiecewise coords.rectangularGridRadiiDimensions(numColumns, numRows)) divide 2f
    }
}

@JSExportAll
object RectangularHexGridDisplay {

}
*/