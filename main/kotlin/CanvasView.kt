import javafx.beans.value.ObservableValue
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.effect.DropShadow
import javafx.scene.image.ImageView
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import java.beans.EventHandler


class CanvasView(private val model : Model) : IView, ScrollPane(){
    private val ds = DropShadow(20.0, Color.BLACK)
    private val img_views_f = ArrayList<Dragger>()
    private val img_views_p = ArrayList<Dragger>()
    private val flow = FlowPane()
    private val pane = Pane()

    class Dragger(val imgv : ImageView, val model : Model){
        private var x = 0.0
        private var y = 0.0
        init {
            imgv.setOnMousePressed{ event ->
                x = event.sceneX;
                y = event.sceneY
                imgv.requestFocus()
            }
            imgv.setOnMouseDragged { event ->
                if (model.cascade()) {
                    var x_change = event.sceneX - x
                    var y_change = event.sceneY - y
                    imgv.x = imgv.x + x_change
                    imgv.y = imgv.y + y_change
                    x = event.sceneX
                    y = event.sceneY
                }else{
                    model.change_mode(true)
                }
            }
        }
        fun contains(x:Double,y:Double):Boolean{
            return (imgv.contains(x,y))
        }
    }
    init {
        this.content = pane
        flow.vgap = 8.0
        flow.hgap = 4.0
        flow.prefWrapLength = 800.0
        this.focusedProperty()
            .addListener { observable: ObservableValue<out Boolean?>?, oldValue: Boolean?, newValue: Boolean ->
                if (newValue) {
                    model.clear_select()
                    if (model.cascade()){
                        for (imgv in img_views_p){
                            imgv.imgv.setEffect(null)
                        }
                    }else{
                        for (imgv in img_views_f){
                            imgv.imgv.setEffect(null)
                        }
                    }
                }}
    /*    pane.setOnMouseClicked { event ->
            if (model.selected()){
                var contains = false
                if (model.cascade()){
                    for (drag_p in img_views_p){
                        if (drag_p.contains(event.sceneX, event.sceneY)) {
                            contains = true
                        }else{
                            drag_p.imgv.setEffect(null)
                        }
                    }
                }else{
                    for (drag_f in img_views_f){
                        if (drag_f.contains(event.sceneX, event.sceneY)) {
                            contains = true
                        }else{
                            drag_f.imgv.setEffect(null)
                        }
                    }
                }
                if (!contains) {
                    model.clear_select()
                }
            }
            /*if (model.selected()) {
                var contains = false
                for (img_v in img_views) {
                    img_v.imgv.setEffect(null)
                    if (img_v.contains(event.sceneX, event.sceneY)) {
                        contains = true
                        img_v.imgv.setEffect(ds)
                    }
                }
                if (!contains) {
                    model.clear_select()
                }
            }*/
        }*/
    }
    fun addImageView(img :ImageView, cas:Boolean) {
        val drag = Dragger(img,model)
        if (cas){
            pane.children.add(img)
            img_views_p.add(drag)
        }else {
            flow.children.add(img)
            img_views_f.add(drag)
        }
        img.setOnMouseClicked { event ->
            img.requestFocus()
        }
        img.focusedProperty()
            .addListener { observable: ObservableValue<out Boolean?>?, oldValue: Boolean?, newValue: Boolean ->
                if (newValue) {
                    img.setEffect(ds)
                    if (cas) {
                        img.toFront()
                        model.select_img(img_views_p.indexOf(drag))
                    }else{
                        model.select_img(img_views_f.indexOf(drag))
                    }
                } //else {
//                    img.setEffect(null)
//                }
            }
    }

    fun delImageView(imgvi : Int){
        pane.children.remove(img_views_p[imgvi].imgv)
        flow.children.remove(img_views_f[imgvi].imgv)
        img_views_p.removeAt(imgvi)
        img_views_f.removeAt(imgvi)
    }

    fun rotate_right(img: Int){
        val img_view = img_views_p[img].imgv
        img_view.rotate = img_view.rotate + 10.0
    }

    fun rotate_left(img: Int){
        val img_view = img_views_p[img].imgv
        img_view.rotate = img_view.rotate - 10.0
    }

    fun zoom_in(img: Int) {
        val img_view = img_views_p[img].imgv
        img_view.scaleX = img_view.scaleX * 1.25
        img_view.scaleY = img_view.scaleY * 1.25
    }
    fun zoom_out(img: Int) {
        val img_view = img_views_p[img].imgv
        img_view.scaleX = img_view.scaleX * 0.75
        img_view.scaleY = img_view.scaleY * 0.75
    }
    fun reset(img: Int){
        val img_view = img_views_p[img].imgv
        img_view.scaleX = 1.0
        img_view.scaleY = 1.0
        img_view.rotate = 0.0
    }
    override fun update() {
//        TODO("Not yet implemented")
        val choice = model.choice()
        if (model.cascade()){
            this.content = pane
        }else{
            this.content = flow
        }
        if (model.selected()) {
            when (choice) {
                1 -> {
                    this.rotate_right(model.selected_img())
                }
                2->{
                    this.rotate_left(model.selected_img())
                }
                3->{
                    this.zoom_in(model.selected_img())
                }
                4->{
                    this.zoom_out(model.selected_img())
                }
                5->{
                    this.reset(model.selected_img())
                }
        }

    }
    }
}

