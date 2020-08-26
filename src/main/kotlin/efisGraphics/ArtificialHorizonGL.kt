package efisGraphics

import com.jogamp.opengl.GLAutoDrawable
import org.anglur.joglext.jogl2d.GLGraphics2D
import java.awt.Color

class ArtificialHorizonGL: IEFISGraphic {

    private val itemOffsetY = -100
    private val itemWidth = 400
    private val itemHeight = itemWidth

    override fun draw(g: GLGraphics2D, glDrawable: GLAutoDrawable) {
        // Draw the center.
        EFISWindow.drawRoundedRect(EFISWindow.WINDOW_WIDTH / 2 - itemWidth / 2,
                EFISWindow.WINDOW_HEIGHT / 2 - itemHeight / 2 + itemOffsetY,
                itemWidth, itemHeight,
                100, 100,
                true, Color.ORANGE)
    }

    override fun update() {

    }
}