package org.ggp.base.apps.kiosk.games;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;
import java.util.HashSet;

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
        int x = xCell - 1;
        int y = 11 - yCell;
        if (gameStateHasFactsMatching("\\( pos_"+myRole+" "+(x-1)+" "+y+" \\)").size() > 0 &&
            gameStateHasLegalMovesMatching("e").size() > 0) {
            return gameStateHasLegalMovesMatching("e");
        }
        else if (gameStateHasFactsMatching("\\( pos_"+myRole+" "+(x+1)+" "+y+" \\)").size() > 0 &&
            gameStateHasLegalMovesMatching("w").size() > 0) {
            return gameStateHasLegalMovesMatching("w");
        }
        else if (gameStateHasFactsMatching("\\( pos_"+myRole+" "+x+" "+(y-1)+" \\)").size() > 0 &&
            gameStateHasLegalMovesMatching("n").size() > 0) {
            return gameStateHasLegalMovesMatching("n");
        }
        else if (gameStateHasFactsMatching("\\( pos_"+myRole+" "+x+" "+(y+1)+" \\)").size() > 0 &&
            gameStateHasLegalMovesMatching("s").size() > 0) {
            return gameStateHasLegalMovesMatching("s");
        }
        else if (gameStateHasFactsMatching("\\( pos_"+myRole+" "+x+" "+y+" \\)").size() > 0 &&
            gameStateHasLegalMovesMatching("noop").size() > 0) {
            return gameStateHasLegalMovesMatching("noop");
        }
        return new HashSet<String>();
    }

    @Override
    protected void renderCellBackground(Graphics g, int xCell, int yCell) {
        if (xCell-1 == 5 && yCell-1 == 5) {
            int width = g.getClipBounds().width;
            int height = g.getClipBounds().height;
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(2,2,width-2,height-2);
        }
    }

    @Override
    protected void renderCellContent(Graphics g, int xCell, int yCell, Set<String> facts) {
        Set<String> moves = getLegalMovesForCell(xCell,yCell);
        if (moves.size() > 0) { CommonGraphics.drawSelectionBox(g); }

        for (String theFact: facts) {
            String[] cellFact = theFact.split(" ");
            if(cellFact[1].equals("pos_rabbit")) {
                g.setColor(Color.BLACK);
                CommonGraphics.fillWithString(g, "R", 1.2);
            }
            else if(cellFact[1].equals("pos_fox")) {
                g.setColor(Color.BLACK);
                CommonGraphics.fillWithString(g, "F", 1.2);
            }
        }
    }
}
