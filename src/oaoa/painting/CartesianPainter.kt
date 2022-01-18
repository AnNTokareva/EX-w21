package oaoa.painting

import CartesianPlane
import java.awt.*
import kotlin.math.abs

class CartesianPainter(private val plane: CartesianPlane): Painter {
    //нарисовать две оси, если видно, то проходят через 0, если не проходят, то по бокам расположены
    override fun paint(g: Graphics) {
        paintAxes(g)
    }

    var maxTickColor: Color= Color.RED
    val mainFont: Font=Font("Cambria", Font.BOLD, 16)
    var tSize = 8

    var axesColor: Color = Color.GRAY
    private fun paintAxes(g: Graphics){
        with(plane){
            (g as Graphics2D).apply {
                stroke = BasicStroke(3F)
                color = axesColor
                if (yMin<= 0 && yMax>=0){
                    drawLine(0,yCrt2Src(0.0),width,yCrt2Src(0.0))
                } else{
                    drawLine(0,0,width,0)
                    drawLine(0,height,width,height)
                }

                if( xMin<=0 && xMax>=0 ){
                    drawLine(xCrt2Src(0.0),0,xCrt2Src(0.0), height)
                } else{
                    drawLine(0,0,0, height)
                    drawLine(width,0, width, height)
                }

       //         drawXLabels(g)
                drawMarkup(g)
            }
        }
    }

    private fun drawXLabels(g: Graphics,i:Int){
        with(g as Graphics2D){
            stroke = BasicStroke(2F)
            color = maxTickColor
            font = mainFont
            var tickValue = i/10.0
            with(plane){
                drawLine(xCrt2Src(tickValue), yCrt2Src(0.0) - tSize, xCrt2Src(tickValue), yCrt2Src(0.0)+tSize)
                val (tW, tH) = with(fontMetrics.getStringBounds(tickValue.toString(), g)){
                    Pair(width.toInt(), height.toInt())
                }
                if(i%10 == 0)
                drawString(tickValue.toString(), xCrt2Src(tickValue)-tW/2, yCrt2Src(0.0) + tSize + tH)
            }
        }
    }
    private fun drawYLabels(g: Graphics,i:Int){
        with(g as Graphics2D){
            stroke = BasicStroke(2F)
            color = maxTickColor
            font = mainFont
            var tickValue = i/10.0
            with(plane){
                drawLine(xCrt2Src(0.0)-tSize, yCrt2Src(tickValue), xCrt2Src(0.0)+tSize, yCrt2Src(tickValue))
                val (tW, tH) = with(fontMetrics.getStringBounds(tickValue.toString(), g)){
                    Pair(width.toInt(), height.toInt())
                }
                if( i%10 == 0)
                drawString(tickValue.toString(), xCrt2Src(0.0)-tSize - tW*5/4, yCrt2Src(tickValue) + tSize + tH*(1/2))
            }
        }
    }

    private fun drawMarkup(g:Graphics){
        with(plane) {
            (g as Graphics2D).apply {
                for (i in xMin.toInt()*10-20..xMax.toInt()*10+20) {
                    if(abs(i)%10 == 0) {
                        maxTickColor = Color.BLUE
                        tSize = 8
                    }
                    else if (abs(i)%10==5) {
                        maxTickColor = Color.RED
                        tSize = 5
                    }
                    else {
                        maxTickColor = Color.GRAY
                        tSize = 3
                    }
                    if(i!=0)
                        drawXLabels(g,i)
                    drawYLabels(g,i)
                }
            }
        }
    }
}