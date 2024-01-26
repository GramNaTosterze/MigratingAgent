package pl.gda.pg.eti.kask.sa.migration.behaviours

import jade.content.lang.sl.SLCodec
import jade.content.onto.basic.Action
import jade.core.behaviours.OneShotBehaviour
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction
import jade.domain.mobility.MobilityOntology
import jade.lang.acl.ACLMessage
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent
import java.util.*

class RequestContainersListBehaviour(agent: MigratingAgent) : OneShotBehaviour(agent) {

    override fun action() {
        val query = QueryPlatformLocationsAction()
        val action = Action(myAgent.ams, query)

        val conversationId = UUID.randomUUID().toString()

        val request = ACLMessage(ACLMessage.REQUEST)
        request.language = SLCodec().name
        request.ontology = MobilityOntology.getInstance().name
        request.addReceiver(myAgent.ams)
        request.conversationId = conversationId

        try {
            myAgent.contentManager.fillContent(request, action)
            myAgent.send(request)
            myAgent.addBehaviour(ReceiveContainersListBehaviour(myAgent as MigratingAgent, conversationId))
        } catch (ex: Exception) {
            //RequestContainersListBehaviour.log.log(Level.WARNING, ex.message, ex)
        }
    }
}