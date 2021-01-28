package org.ggp.base.apps.kiosk.games;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

import org.ggp.base.apps.kiosk.templates.CommonGraphics;
import org.ggp.base.apps.kiosk.templates.GameCanvas_FancyGrid;


/**
 * An implementation of Tic-Tac-Toe based on the GameCanvas_FancyGrid template.
 * Comes in at just under 50 lines of code, mostly boilerplate.
 *
 * @author Sam Schreiber
 */
public class Catch11Canvas extends GameCanvas_FancyGrid {
    public static final long serialVersionUID = 0x1;

    @Override
    public String getGameName() { return "Catch 11"; }
    @Override
    protected String getGameKey() { return "catch11"; }
    @Override
    protected int getGridHeight() { return 11; }
    @Override
    protected int getGridWidth() { return 11; }

    @Override
    protected Set<String> getFactsAboutCell(int xCell, int yCell) {
        return gameStateHasFactsMatching("\\( pos_(rabbit|fox) " + (xCell-1) + " " + (11-yCell) + " \\)");
    }

    @Override
    protected Set<String> getLegalMovesForCell(int xCell, int yCell) {
        if (gameStateHasFactsMatching("\\( pos_" + myRole + " " + (xCell-1) + " " + (11-yCell) + " \\)").size() > 0) {
            return gameStateHasLegalMovesMatching(".*");
        }
        return gameStateHasLegalMovesMatching("foobar");
    }

    @Override
    protected void renderCellBackground(Graphics g, int xCell, int yCell) {
        if (xCell-1 == 5 && yCell-1 == 5) {
            int width = g.getClipBounds().width;
            int height = g.getClipBounds().height;
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(2,2,width-2,height-2);

            //CommonGraphics.drawSelectionBox(g);

        }
    }
    @Override
    protected void renderCellContent(Graphics g, Set<String> facts) {
        for (String theFact: facts) {
            String[] cellFacts = theFact.split(" ");
            //System.out.println("theFact: " + theFact);
            if(cellFacts[1].equals("pos_rabbit")) {
                g.setColor(Color.BLACK);
                CommonGraphics.fillWithString(g, "R", 1.2);
            }
            else if(cellFacts[1].equals("pos_fox")) {
                g.setColor(Color.BLACK);
                CommonGraphics.fillWithString(g, "F", 1.2);
            }
        }
    }
}
