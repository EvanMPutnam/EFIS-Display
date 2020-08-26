package efisGraphics

import com.jogamp.opengl.GLAutoDrawable
import org.anglur.joglext.jogl2d.GLGraphics2D

interface IEFISGraphic {
    // Function used to draw this graphic components.
    fun draw(g: GLGraphics2D, glDrawable: GLAutoDrawable)

    // This calls the xPlane API to fetch value/values to update the flight model.
    fun update()
}