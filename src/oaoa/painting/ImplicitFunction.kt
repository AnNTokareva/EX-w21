package oaoa.painting

import CartesianPlane
import java.awt.*

open class ImplicitFunctionPainter(private val plane: CartesianPlane, var t_min: Double, var t_max: Double) : Painter {

    var funColor: Color = Color.MAGENTA
    var f_x: (Double) -> Double = { t -> t }
    var f_y: (Double) -> Double = { t -> t }
    val st = 1000

    override fun paint(g: Graphics) {
        with(g as Graphics2D) {
            color = funColor
            stroke = BasicStroke(2F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
            val st1 = (t_max - t_min) / st
            with(plane) {
                for (i in 0 until st) {
                    val t = t_min + i * st1
                    drawLine(
                        xCrt2Scr(f_x(t)),
                        yCrt2Scr(f_y(t)),
                        xCrt2Scr(f_x(t + st1)),
                        yCrt2Scr(f_y(t + st1)),
                    )
                }
            }
        }
    }
}