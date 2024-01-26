package pl.gda.pg.eti.kask.sa.migration.agents;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.mobility.MobilityOntology;

import java.util.List;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.Setter;
import pl.gda.pg.eti.kask.sa.migration.behaviours.RequestContainersListBehaviour;

public class MigratingAgent extends Agent {

    @Setter
    @Getter
    private List<Location> locations;
    private Boolean successfullMove;

    public MigratingAgent() {
    }

    @Override
    protected void setup() {
        super.setup();
        successfullMove = true;
        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());
        this.addBehaviour(new RequestContainersListBehaviour(this));
    }

    @Override
    protected void afterMove() {
        super.afterMove();
        //restore state
        //resume threads
        successfullMove = true;
    }

    @Override
    protected void beforeMove() {
        JOptionPane.showMessageDialog(null, "Odchodzę!");
        //stop threads
        //save state
        super.beforeMove();
    }

    @Override
    public void restoreBufferedState () {
        super.restoreBufferedState();

        if (successfullMove) { successfullMove = false; }
        else {
            successfullMove = true;
            JOptionPane.showMessageDialog(null, "Brak Kontenera");
            reloadLocationList();
        }


        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());
        JOptionPane.showMessageDialog(null, "Przybywam!");
        if (getLocations().isEmpty())
            reloadLocationList();
    }

    public void reloadLocationList() {
        JOptionPane.showMessageDialog(null, "Pobrano nową listę");
        addBehaviour(new RequestContainersListBehaviour(this));
    }
}
