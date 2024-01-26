package pl.gda.pg.eti.kask.sa.migration.behaviours

import jade.content.onto.basic.Result
import jade.core.Location
import jade.core.behaviours.Behaviour
import jade.lang.acl.MessageTemplate
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent

class ReceiveContainersListBehaviour(agent: MigratingAgent, private val conversationId: String) : Behaviour(agent) {
    private var done = false

    private var mt: MessageTemplate? = null

    override fun onStart() {
        super.onStart()
        mt = MessageTemplate.MatchConversationId(conversationId)
    }

    override fun action() {
        val msg = myAgent.receive(mt)
        if (msg != null) {
            done = true
            try {
                val ce = myAgent.contentManager.extractContent(msg)
                val items = (ce as Result).items
                val locations: MutableList<Location> = ArrayList()
                items.iterator().forEachRemaining { i: Any -> locations.add(i as Location) }

                (myAgent as MigratingAgent).locations = locations
                myAgent.addBehaviour(MigratingBehaviour(myAgent as MigratingAgent))
            } catch (ex: Exception) {
                //ReceiveContainersListBehaviour.log.log(Level.SEVERE, null, ex)
            }
        }
    }

    override fun done(): Boolean {
        return done
    }
}