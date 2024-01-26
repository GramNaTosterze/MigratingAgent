package pl.gda.pg.eti.kask.sa.migration.agents

import jade.content.lang.sl.SLCodec
import jade.core.Agent
import jade.core.Location
import jade.domain.mobility.MobilityOntology
import pl.gda.pg.eti.kask.sa.migration.behaviours.RequestContainersListBehaviour
import javax.swing.JOptionPane

class MigratingAgent: Agent() {

    var locations: MutableList<Location> = emptyList<Location>().toMutableList()
    private var successfulMove: Boolean = false

    override fun setup() {
        super.setup()
        successfulMove = true
        val cm = contentManager
        cm.registerLanguage(SLCodec())
        cm.registerOntology(MobilityOntology.getInstance())
        this.addBehaviour(RequestContainersListBehaviour(this))
    }
    override fun afterMove() {
        super.afterMove()
        //restore state
        //resume threads
        successfulMove = true
    }
    override fun beforeMove() {
        JOptionPane.showMessageDialog(null, "Odchodzę!")


        //stop threads
        //save state
        super.beforeMove()
    }
    override fun restoreBufferedState() {
        super.restoreBufferedState()

        if (successfulMove) {
            successfulMove = false
        } else {
            successfulMove = true
            JOptionPane.showMessageDialog(null, "Brak Kontenera")
            reloadLocationList()
        }


        val cm = contentManager
        cm.registerLanguage(SLCodec())
        cm.registerOntology(MobilityOntology.getInstance())
        JOptionPane.showMessageDialog(null, "Przybywam!")
        if (locations.isEmpty()) reloadLocationList()
    }
    private fun reloadLocationList() {
        JOptionPane.showMessageDialog(null, "Pobrano nową listę")
        addBehaviour(RequestContainersListBehaviour(this))
    }
}