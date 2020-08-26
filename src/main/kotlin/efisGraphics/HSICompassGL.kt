package efisGraphics

import com.jogamp.opengl.GLAutoDrawable
import org.anglur.joglext.jogl2d.GLGraphics2D
import java.awt.Image
import java.awt.image.ImageObserver
import javax.imageio.ImageIO

class HSICompassGL: IEFISGraphic {

    private val compassImg: Image = ImageIO.read(this.javaClass.classLoader.getResource("compass.png"))
    private val width = compassImg.getHeight(null)
    private val height = compassImg.getWidth(null)
    private val offsetY = 600
    private val blankImageObserver = ImageObserver { _, _, _, _, _, _ ->  false}

    var degreeRotation = 0.0

    override fun draw(g: GLGraphics2D, glDrawable: GLAutoDrawable) {
        // Rotate back to start.
        val radsFromDegrees = degreeRotation * Math.PI / 180

        g.rotate(radsFromDegrees, (EFISWindow.WINDOW_WIDTH / 2).toDouble(),
                (EFISWindow.WINDOW_HEIGHT / 2 + offsetY).toDouble())

        g.drawImage(compassImg, EFISWindow.WINDOW_WIDTH / 2 - width / 2,
                EFISWindow.WINDOW_HEIGHT / 2 - height / 2 + offsetY, blankImageObserver)

        g.rotate(-radsFromDegrees, (EFISWindow.WINDOW_WIDTH / 2).toDouble(),
                (EFISWindow.WINDOW_HEIGHT / 2 + offsetY).toDouble())
    }

    override fun update() {
        this.degreeRotation += 0.001
    }
}