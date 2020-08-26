package efisGraphics

import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.util.gl2.GLUT
import org.anglur.joglext.jogl2d.GLGraphics2D
import java.awt.Color

class AirspeedTapeGL: IEFISGraphic {

    var currentAirspeed = 45

    override fun draw(g: GLGraphics2D, glDrawable: GLAutoDrawable) {
        EFISWindow.drawRect(20, 100, 175, 500, true)


        val middleY = 100 + 250

        // Draw current speed.
        EFISWindow.drawText((currentAirspeed).toString(), (20 + 65).toDouble(),
                (middleY).toDouble(), glDrawable, GLUT.BITMAP_HELVETICA_18, Color.WHITE)

        // Get the diff between the current and the highest 20.
        val diff = ((currentAirspeed + 24) / 25) * 25 - currentAirspeed


        EFISWindow.drawText((diff + currentAirspeed).toString(), (40).toDouble(),
                ((middleY - diff * 2)).toDouble(), glDrawable, GLUT.BITMAP_HELVETICA_18, Color.WHITE)

        // Draw upper portion of tape
        repeat(3) {
            EFISWindow.drawText((diff + currentAirspeed + 25 * (it + 1)).toString(), (40).toDouble(),
                    ((middleY - diff * 2) - 50 * (it + 1)).toDouble(), glDrawable, GLUT.BITMAP_HELVETICA_18, Color.WHITE)
        }

        // Draw lower portion of tape
        repeat(4) {
            if (diff + currentAirspeed - 25 * (it + 1) >= 0 ) {
                EFISWindow.drawText((diff + currentAirspeed - 25 * (it + 1)).toString(), (40).toDouble(),
                        ((middleY - diff * 2) + 50 * (it + 1)).toDouble(), glDrawable, GLUT.BITMAP_HELVETICA_18, Color.WHITE)
            }
        }


    }

    var count = 0
    override fun update() {
        count += 1
        if (count == 1000000 && currentAirspeed != 400) {
            currentAirspeed += 1
            count = 0
        }
    }
}