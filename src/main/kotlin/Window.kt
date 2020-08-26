import efisGraphics.*

import com.jogamp.newt.event.*
import com.jogamp.newt.opengl.*
import com.jogamp.opengl.*
import com.jogamp.opengl.util.*
import com.jogamp.opengl.util.gl2.GLUT
import org.anglur.joglext.jogl2d.GLGraphics2D
import org.anglur.joglext.jogl2d.impl.gl2.GL2StringDrawer

import java.awt.Color

import kotlin.system.exitProcess

import flightModel.FlightModel



object EFISWindow : GLEventListener {

    var isActive = true
        private set

    const val WINDOW_WIDTH = 1000
    const val WINDOW_HEIGHT = 800
    private const val FPS = 60

    private val flightModel = FlightModel()

    private val window = GLWindow.create(GLCapabilities(null))

    private val aircraftGLComponents = ArrayList<IEFISGraphic>()

    init {
        GLProfile.initSingleton()
    }

    private fun initAircraftComponents() {
        // Add the components in the order that they need to be drawn.
        aircraftGLComponents.add(HSICompassGL())
        aircraftGLComponents.add(AirspeedTapeGL())
        aircraftGLComponents.add(AltitudeTapeGL())
        aircraftGLComponents.add(ArtificialHorizonGL())
    }

    fun open(width: Int = WINDOW_WIDTH, height: Int = WINDOW_HEIGHT, x: Int = 500, y: Int = 500) {
        val animator = FPSAnimator(window, FPS, true)


        window.addWindowListener(object: WindowAdapter() {
            override fun windowDestroyNotify(e: WindowEvent) {
                isActive = false
                if (animator.isStarted)
                    animator.stop()
                exitProcess(0)
            }
        })

        // Create instances of all of the components we will be using.
        initAircraftComponents()

        // Create background thread to update the components.
        val backgroundThread = Thread(UpdateThread(flightModel, aircraftGLComponents))
        backgroundThread.start()

        window.addGLEventListener(this)
        window.setSize(width, height)
        window.setPosition(x, y)
        window.isResizable = false
        window.title = "Hello world"
        window.isVisible = true
        animator.start()
        println("Finished Initialization.")
    }

    private val g = GLGraphics2D() //Create GL2D wrapper

    fun drawText(text: String, x: Double, y: Double, glDrawable: GLAutoDrawable,
                         fontType: Int = GLUT.BITMAP_HELVETICA_18, color: Color = Color.RED) {
        val gl2 = glDrawable.gl.gL2
        g.color = color
        gl2.glRasterPos2d(x, y)
        (g.stringHelper as GL2StringDrawer).glutBitmapString(fontType, text)
    }


    fun drawRect(x: Int, y: Int, width: Int, height: Int, fill: Boolean, color: Color = Color.GRAY) {
        g.color = color
        if (fill) {
            g.fillRect(x, y, width, height)
        } else {
            g.drawRect(x, y, width, height)
        }
    }

    fun drawRoundedRect(x: Int, y: Int, width: Int, height: Int, arcWidth: Int, arcHeight: Int, fill: Boolean, color: Color = Color.GRAY) {
        g.color = color
        if (fill) {
            g.fillRoundRect(x, y, width, height, arcWidth, arcHeight)
        } else {
            g.drawRoundRect(x, y, width, height, arcWidth, arcHeight)
        }
    }

    override fun display(gLDrawable: GLAutoDrawable) {
        val gl2 = gLDrawable.gl.gL2

        // Clear the screen.
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT)

        // Updated wrapper to latest glContext
        g.prePaint(gLDrawable.context)

        // Draw the sub components.
        aircraftGLComponents.forEach {
            synchronized(it) {
                it.draw(g, gLDrawable)
            }
        }
    }

    override fun init(glDrawable: GLAutoDrawable) {
    }

    override fun reshape(gLDrawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
        val gl = gLDrawable.gl.gL2
        gl.glViewport(0, 0, width, height)
    }

    override fun dispose(gLDrawable: GLAutoDrawable) {
        g.glDispose()
    }

}