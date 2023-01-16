import javafx.scene.control.Button
import javafx.scene.control.ToolBar
import javafx.scene.image.ImageView
import javafx.scene.image.Image
import java.io.File
import java.lang.StringBuilder
class ToolbarView(private val model : Model) : IView, ToolBar() {
    private val buttons = ArrayList<Button>()
    init {
        val path = System.getProperty("user.dir")+"\\src\\main\\resources"
        val addgraph = File("${path}\\add.png")
        val addview = ImageView(Image(addgraph.inputStream()))
        addview.isPreserveRatio = true
        addview.fitHeight = 12.0
        val add = Button("Add Image",addview)

        val delgraph = File("${path}\\del.png")
        val delview = ImageView(Image(delgraph.inputStream()))
        delview.isPreserveRatio = true
        delview.fitHeight = 12.0
        val del = Button("Del Image",delview)

        val leftgraph = File("${path}\\rotate_left.png")
        val leftview = ImageView(Image(leftgraph.inputStream()))
        leftview.isPreserveRatio = true
        leftview.fitHeight = 12.0
        val left = Button("Rotate Left",leftview)

        val rightgraph = File("${path}\\rotate_right.png")
        val rightview = ImageView(Image(rightgraph.inputStream()))
        rightview.isPreserveRatio = true
        rightview.fitHeight = 12.0
        val right = Button("Rotate Right",rightview)

        val ingraph = File("${path}\\zoom_in.png")
        val inview = ImageView(Image(ingraph.inputStream()))
        inview.isPreserveRatio = true
        inview.fitHeight = 12.0
        val zoom_in = Button("Zoom in",inview)

        val outgraph = File("${path}\\zoom_out.png")
        val outview = ImageView(Image(outgraph.inputStream()))
        outview.isPreserveRatio = true
        outview.fitHeight = 12.0
        val zoom_out = Button("Zoom out",outview)

        val resetgraph = File("${path}\\reset.png")
        val resetview = ImageView(Image(resetgraph.inputStream()))
        resetview.isPreserveRatio = true
        resetview.fitHeight = 12.0
        val reset = Button("Reset",resetview)

        val cascadegraph = File("${path}\\cascade.png")
        val cascadeview = ImageView(Image(cascadegraph.inputStream()))
        cascadeview.isPreserveRatio = true
        cascadeview.fitHeight = 12.0
        val cascade = Button("Cascade",cascadeview)

        val tilegraph = File("${path}\\tile.png")
        val tileview = ImageView(Image(tilegraph.inputStream()))
        tileview.isPreserveRatio = true
        tileview.fitHeight = 12.0
        val tile = Button("Tile",tileview)
        buttons.add(add)
        buttons.add(del)
        buttons.add(left)
        buttons.add(right)
        buttons.add(zoom_in)
        buttons.add(zoom_out)
        buttons.add(reset)
        buttons.add(cascade)
        buttons.add(tile)
        println(buttons.indexOf(del))
        for(button in buttons){
            this.items.add(button)
        }

        add.setOnAction {
            model.addPhoto()
        }
        del.setOnAction {
            model.delete_img()
        }
        del.setDisable(true)
        left.setOnAction {
            model.act(2)
        }
        right.setOnAction {
            model.act(1)
        }
        zoom_in.setOnAction {
            model.act(3)
        }
        zoom_out.setOnAction {
            model.act(4)
        }
        reset.setOnAction {
            model.act(5)
        }
        cascade.setOnAction {
            model.change_mode(true)
        }
        tile.setOnAction {
            model.change_mode(false)
        }
    }

    override fun update() {
//        TODO("Not yet implemented")
        if (!model.selected()){
            buttons[1].setDisable(true)
        }else{
            buttons[1].setDisable(false)
        }
        if (!model.cascade()){
            buttons[2].setDisable(true)
            buttons[3].setDisable(true)
            buttons[4].setDisable(true)
            buttons[5].setDisable(true)
            buttons[6].setDisable(true)
        }else{
            buttons[2].setDisable(false)
            buttons[3].setDisable(false)
            buttons[4].setDisable(false)
            buttons[5].setDisable(false)
            buttons[6].setDisable(false)
        }
    }
}