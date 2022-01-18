package oaoa.painting


import CartesianPlane
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import kotlin.math.sin

class FunctionPainter(
    private val plane:CartesianPlane,
    var function: (Double) -> Double = ::sin//Math::sin
): Painter{

    var funColor: Color = Color.BLUE


    override fun paint(g:Graphics){
        with(plane) {
            (g as Graphics2D).apply {
                color = funColor
                stroke = BasicStroke(2F)
                for (x in 0 until width) {

                    drawLine(x, yCrt2Src(function(xSct2Crt(x))),
                        x + 1, yCrt2Src(function(xSct2Crt(x + 1))))
                }
            }
        }
    }
}


