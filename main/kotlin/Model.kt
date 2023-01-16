import java.util.*
import javafx.application.Platform
import javafx.scene.Group
import javafx.scene.Scene
import org.jetbrains.annotations.Nullable
import javafx.scene.image.ImageView
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.stage.FileChooser


class Model{
    private val views = ArrayList<IView>()
    private var photos = 0
    private var choice = 0
//    private var current_img  = 0
    private var current_view  = 0
    private var selected = false
    private var cascade = true

    fun addPhoto(){
        photos += 1
        val filechooser = FileChooser()
        val addscene = Scene(HBox())
        val own = addscene.window
        filechooser.title = "Add photo"
        val selectedfile = filechooser.showOpenDialog(own)
        println(selectedfile)
        val img = Image(selectedfile.inputStream())
        val imgv1 = ImageView(img)
        val imgv2 = ImageView(img)
        imgv1.setPreserveRatio(true)
        imgv2.setPreserveRatio(true)
        for (view in views){
            if (view is CanvasView){
                view.addImageView(imgv1,true)
                view.addImageView(imgv2,false)
            }
            if (view is StatusView){
                view.update()
            }
        }
    }
    fun cascade():Boolean{
        return cascade
    }
    fun change_mode(cas:Boolean){
        if (cas != cascade) {
            cascade = cas
            selected = false
            notifyView()
        }
    }
    fun load_num():Int{
        return photos
    }
    fun choice():Int{
        return choice
    }
    fun act(i:Int){
        choice = i
        if (i >0){
            this.notifyView()
        }
        choice = 0
    }
    fun selected():Boolean{
        return selected
    }
    fun select_img(b : Int){
        current_view = b
        selected = true
        notifyView()
        println("${b}")
    }
    fun selected_img():Int{
        return current_view
    }
    fun clear_select(){
        selected = false
        notifyView()
    }
    fun delete_img(){
        if (selected){
            photos -= 1
            for (view in views){
            if (view is CanvasView){
                view.delImageView(current_view)
            }
        }}
    }

    fun addView(view: IView) {
        views.add(view)
    }
    fun removeView(view: IView) {
        views.remove(view)
    }

    fun notifyView() {
        for (view in views) {
            view.update()
        }
    }
}

class newImageView