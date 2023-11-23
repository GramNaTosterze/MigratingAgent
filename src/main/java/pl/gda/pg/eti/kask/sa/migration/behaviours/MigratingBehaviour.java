package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.Location;
import jade.core.behaviours.Behaviour;
import jade.domain.mobility.MobilityOntology;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

public class MigratingBehaviour extends Behaviour {

    protected final MigratingAgent myAgent;
    
    public MigratingBehaviour(MigratingAgent agent) {
        super(agent);
        myAgent = agent;
    }

    @Override
    public void action() {
        Location location = myAgent.getLocations().get(0);
        myAgent.getLocations().remove(location);
        myAgent.doMove(location);
    }

    @Override
    public boolean done() {
        return myAgent.getLocations().isEmpty();
    }

}
