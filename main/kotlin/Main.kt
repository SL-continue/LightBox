import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.effect.DropShadow
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*
import javafx.scene.input.*
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color

class Main : Application(){
    override fun start(stage: Stage?){
        val model = Model()
        val root = BorderPane()


        val toolbar = ToolbarView(model)
        val displays = CanvasView(model)
        val status = StatusView(model)

        val scene = Scene(root,800.0,600.0)
        model.addView(toolbar)
        model.addView(displays)
        model.addView(status)
        root.top = toolbar
        root.center = displays
//        displays.toBack()
        root.bottom = status
        stage?.title = "LightBox 2021 Junyi Liu"
        stage?.isResizable = true
        stage?.scene = scene
        stage?.maxHeight=1200.0
        stage?.maxWidth=1600.0
        stage?.sizeToScene()
        stage?.show()
    }
}

