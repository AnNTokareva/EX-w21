package oaoa

import CartesianPlane
import oaoa.painting.FunctionPainter
import oaoa.painting.CartesianPainter
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import kotlin.math.sin

class MainFrame : JFrame(){

    val mainPanel: GraphicsPanel
    val controlPanel: JPanel

    val xMinLabel: JLabel
    val yMinLabel: JLabel
    val xMaxLabel: JLabel
    val yMaxLabel: JLabel

    val xMin: JSpinner
    val xMinM: SpinnerNumberModel
    val xMax: JSpinner
    val xMaxM: SpinnerNumberModel
    val yMin: JSpinner
    val yMinM: SpinnerNumberModel
    val yMax: JSpinner
    val yMaxM: SpinnerNumberModel

    val chPoint: JCheckBox
    val chFunk: JCheckBox

    val lPoint: JLabel
    val lFunk: JLabel

    val pointColorP: JPanel
    val funColorP: JPanel

    val k = mutableMapOf<Double, Double>()




    init {

        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(600, 600)


        controlPanel = JPanel().apply{
   //         background = Color.RED
        }

        pointColorP = JPanel().apply { background = Color.RED }
        funColorP = JPanel().apply { background = Color.GRAY }

        xMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        xMin = JSpinner(xMinM)
        xMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        xMax = JSpinner(xMaxM)
        yMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        yMin = JSpinner(yMinM)
        yMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        yMax = JSpinner(yMaxM)

        /*xMax.addChangeListener(object : ChangeListener{
            override fun stateChanged(e: ChangeEvent?) {
                TODO("Not yet implemented")
            }
        })*/



        val plane = CartesianPlane(
            xMinM.value as Double,
            xMaxM.value as Double,
            yMinM.value as Double,
            yMaxM.value as Double
        )

        val cartesianPainter = CartesianPainter(plane)
  //      val sinPainter= FfunctionPainter(plane)
        val f = FunctionPainter(plane, {x->3*x-x*x*x}).apply { funColor = funColorP.background }
        val painters=mutableListOf(cartesianPainter, /*sinPainter*/f)
        mainPanel=GraphicsPanel(painters).apply{
            background=Color.WHITE
        }


        mainPanel.addComponentListener(object: ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                plane.pixelSize = mainPanel.size
                mainPanel.repaint()
            }
        })

        xMin.addChangeListener{
            xMaxM.minimum = (xMinM.value as Double + 0.1)
            plane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        xMax.addChangeListener{
            xMinM.maximum = (xMaxM.value as Double - 0.1)
            plane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        yMin.addChangeListener{
            yMaxM.minimum = (yMinM.value as Double + 0.1)
            plane.ySegment = Pair(yMin.value as Double,yMaxM.value as Double)
            mainPanel.repaint()
        }
        yMax.addChangeListener{
            yMinM.maximum = (yMaxM.value as Double - 0.1)
            plane.ySegment = Pair(yMin.value as Double,yMaxM.value as Double)
            mainPanel.repaint()
        }

        layout = GroupLayout(contentPane).apply{
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(4)
            )

            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(4)
                    .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(4)
            )
        }


        xMin.addChangeListener{ xMaxM.minimum = xMin.value as Double + 0.1 }
        xMax.addChangeListener{ xMinM.maximum = xMax.value as Double - 0.1 }
        yMin.addChangeListener{ yMaxM.minimum = yMin.value as Double + 0.1 }
        yMax.addChangeListener{ yMinM.maximum = yMax.value as Double - 0.1 }

        xMinLabel = JLabel().apply { text = "xMin" }
        yMinLabel = JLabel().apply { text = "yMin" }
        xMaxLabel = JLabel().apply { text = "xMax" }
        yMaxLabel = JLabel().apply { text = "yMax" }

        chPoint = JCheckBox().apply { isSelected = true }
        chFunk = JCheckBox().apply { isSelected = true }

        chPoint.addItemListener{
            if(chPoint.isSelected == false)
            if(chPoint.isSelected == true)

            mainPanel.repaint()
        }

        chFunk.addItemListener {
            if (chFunk.isSelected == false)
                painters.removeIf { it == f }
            if (chFunk.isSelected == true){
                painters.add(f)
                if(chPoint.isSelected == true) {
                }
            }
            mainPanel.repaint()
        }



        lPoint = JLabel().apply { text = "point" }
        lFunk = JLabel().apply { text = "fun" }



        pointColorP.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                e?.let{
                    var color = JColorChooser.showDialog(null,"",pointColorP.background)
                    pointColorP.background = color

                    mainPanel.repaint()
                }
            }
        })

        funColorP.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                e?.let {
                    var color = JColorChooser.showDialog(null, "", funColorP.background)
                    funColorP.background = color
                    f.funColor = color
                    mainPanel.repaint()
                }
            }
        })


                    controlPanel.layout = GroupLayout(controlPanel).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGroup(
                        createParallelGroup()
                            .addGroup(
                                createSequentialGroup()
                                    .addComponent(xMinLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                                    .addComponent(xMin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            )
                            .addGroup(
                                createSequentialGroup()
                                    .addComponent(yMinLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                                    .addComponent(yMin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            )
                    )
                    .addGroup(
                        createParallelGroup()
                            .addGroup(
                                createSequentialGroup()
                                    .addComponent(xMaxLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                                    .addComponent(xMax, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            )
                            .addGroup(
                                createSequentialGroup()
                                    .addComponent(yMaxLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                                    .addComponent(yMax, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            )
                    )
                    .addGroup(
                        createParallelGroup()
                            .addComponent(chPoint, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addGap(4)
                            .addComponent(chFunk, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addGap(4)
                    )
                    .addGroup(
                        createParallelGroup()
                            .addComponent(lPoint,  GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(lFunk, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                    )
                    .addGroup(
                        createParallelGroup()
                            .addComponent(pointColorP, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addComponent(funColorP, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)


                    )
            )
            setVerticalGroup(createParallelGroup()
                .addGroup(
                createSequentialGroup()
                    .addGroup(
                        createParallelGroup()
                            .addComponent(xMinLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addComponent(xMin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addComponent(xMaxLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addComponent(xMax, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    )
                    .addGroup(
                        createParallelGroup()
                            .addComponent(yMinLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addComponent(yMin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addComponent(yMaxLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addComponent(yMax, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    )
            )
                    .addGroup(
                        createSequentialGroup()
                            .addComponent(chPoint,  GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addComponent(chFunk, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)


                    )
                .addGroup(
                    createSequentialGroup()
                        .addComponent(lPoint,  GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(lFunk, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                        .addGap(5)

                )
                .addGroup(
                    createSequentialGroup()
                        .addComponent(pointColorP, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                        .addComponent(funColorP, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)

                )

            )
        }
        pack()
        plane.width = mainPanel.width
        plane.height = mainPanel.height
    }
}