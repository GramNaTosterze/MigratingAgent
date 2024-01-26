package pl.gda.pg.eti.kask.sa.migration.behaviours

import jade.core.Location
import jade.core.behaviours.Behaviour
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent

open class MigratingBehaviour(agent: MigratingAgent) : Behaviour(agent) {
    override fun action() {
        if ((myAgent as MigratingAgent).locations.isEmpty()) return
        val location: Location = (myAgent as MigratingAgent).locations[0]
        (myAgent as MigratingAgent).locations.remove(location)
        (myAgent as MigratingAgent).doMove(location)
    }

    override fun done(): Boolean {
        return (myAgent as MigratingAgent).locations.isEmpty()
    }

}