package efisGraphics

import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.util.gl2.GLUT
import org.anglur.joglext.jogl2d.GLGraphics2D
import java.awt.Color
import kotlin.math.ceil

class AltitudeTapeGL: IEFISGraphic {

    var currentAltitude = 250
    override fun draw(g: GLGraphics2D, glDrawable: GLAutoDrawable) {
        EFISWindow.drawRect(EFISWindow.WINDOW_WIDTH - 175 - 20, 100, 175, 500, true)

        val middleY = 100 + 250

        EFISWindow.drawText((currentAltitude).toString(), (EFISWindow.WINDOW_WIDTH - 175).toDouble() + 65,
                (middleY).toDouble(), glDrawable, GLUT.BITMAP_HELVETICA_18, Color.WHITE)


        val diff = ((currentAltitude + 99) / 100) * 100 - currentAltitude


        EFISWindow.drawText((diff + currentAltitude).toString(), (EFISWindow.WINDOW_WIDTH - 175).toDouble(),
                ((middleY - diff/2)).toDouble(), glDrawable, GLUT.BITMAP_HELVETICA_18, Color.WHITE)

        // Draw upper portion of tape
        repeat(3) {
            EFISWindow.drawText((diff + currentAltitude + 100 * (it + 1)).toString(), (EFISWindow.WINDOW_WIDTH - 175).toDouble(),
                    ((middleY - diff/2) - 50 * (it + 1)).toDouble(), glDrawable, GLUT.BITMAP_HELVETICA_18, Color.WHITE)
        }

        // Draw lower portion of tape
        repeat(4) {
            EFISWindow.drawText((diff + currentAltitude - 100 * (it + 1)).toString(), (EFISWindow.WINDOW_WIDTH - 175).toDouble(),
                    ((middleY - diff/2) + 50 * (it + 1)).toDouble(), glDrawable, GLUT.BITMAP_HELVETICA_18, Color.WHITE)
        }


    }

    var count = 0
    override fun update() {
        count += 1
        if (count == 10000 && currentAltitude != 5000) {
            currentAltitude += 1
            count = 0
        }
    }
}