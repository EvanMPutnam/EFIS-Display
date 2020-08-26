import efisGraphics.IEFISGraphic
import flightModel.FlightModel

class UpdateThread(efisModel: FlightModel, components: ArrayList<IEFISGraphic>) : Runnable{
    private val flightModel = efisModel
    private val flightViews = components

    override fun  run() {
        while (EFISWindow.isActive) {
            flightViews.forEach {
                synchronized(it) {
                    it.update()
                }
            }
        }
    }

}