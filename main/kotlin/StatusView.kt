import javafx.scene.layout.HBox
import javafx.scene.control.Label

class StatusView(private val model : Model) : IView, HBox() {
    private val label1 = Label()
    private val label2 = Label()
    init{
        this.height = 12.0
        this.children.add(label1)
        this.children.add(label2)
        label1.text = "0 photos loaded  "
        label2.text = "cascade mode"
    }

    override fun update() {
//        TODO("Not yet implemented")
        label1.text = "${model.load_num()} photos loaded   "
        if (model.cascade()){
            label2.text = "cascade mode"
        }else{
            label2.text = "tile mode"
        }
    }
}