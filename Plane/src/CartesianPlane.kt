import java.awt.Dimension
import kotlin.math.max

class CartesianPlane(
    xMin: Double,
    xMax: Double,
    yMin: Double,
    yMax: Double
) {

    var xMin: Double = 0.0
        private set
    var xMax: Double = 0.0
        private set
    var yMin: Double = 0.0
        private set
    var yMax: Double = 0.0
        private set

    var pixelSize: Dimension = Dimension(1,1)
        set(size){
            field = Dimension(max(1,size.width), max(1, size.height))
        }

    private var xSize: Int
        get() = pixelSize.width
        set(w) {pixelSize.width = w}

    private var ySize: Int
        get() = pixelSize.height
        set(h) {pixelSize.height = h}

    var xSegment:Pair<Double,Double>
        get() = Pair(xMin, xMax)
        set(value) {
            val k = if (value.first == value.second) 0.1 else 0.0
            xMin = value.first - k
            xMax = value.second + k
            if (xMin > xMax) xMin = xMax.also { xMax = xMin }
        }

    var ySegment:Pair<Double,Double>
        get() = Pair(yMin, yMax)
        set(value) {
            val k = if (value.first == value.second) 0.1 else 0.0
            yMin = value.first - k
            yMax = value.second + k
            if (yMin > yMax) yMin = yMax.also { yMax = yMin }
        }

    var width: Int
        get() = xSize -1
        set(value){
            xSize = max(1, value)
        }
    var height: Int
        get() = ySize - 1
        set(value){
            ySize = max(1,value)
        }

    val xDen : Double
        get() = width/(xMax-xMin)

    val yDen : Double
        get() = width/(yMax-yMin)

    init{
        xSegment = Pair(xMin,xMax)
        ySegment = Pair(yMin,yMax)
    }

    fun xCrt2Src(x: Double): Int{//декарт в пиксели
        val c = (width.toDouble() * (x - xMin) / (xMax - xMin)).toInt()
        return c
    }

    fun yCrt2Src(x: Double): Int{
        val c = -(height.toDouble() * (x - yMax) / (yMax - yMin)).toInt()
        return c
    }

    fun xSct2Crt(x: Int): Double{//обратно
        val c = x*(xMax - xMin) / width + xMin
        return c
    }

    fun ySct2Crt(x: Int): Double{
        val c = -x*(yMax - yMin) / height.toDouble() + yMax
        return c
    }
}