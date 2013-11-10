/*
 * Copyright (c) 2008 - 2013 ivonet
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package nl.ivonet.rss;

import nl.ivonet.logical_expression.ExpressionEvaluator;
import nl.ivonet.logical_expression.interpreter.IllegalExpressionException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the {@link TitleInterpreter} class.
 *
 * @author Ivo Woltring
 */
public final class TitleInterpreterTest {
    private static final String SENTENCE = "\"SAMPLE_Street.Fighter.IV.The.Ties.That.Bind.2009.1080p.BluRay.DTS"
                                           + ".x264-PerfectionHD.mkv\" yEnc (1/231)";

    private List<String> titles;
    TitleInterpreter interpreter;

    @Before
    public void before() {
        interpreter = new TitleInterpreter();
        titles = new ArrayList<String>();
        titles.add("Law.And.Order.UK.S01E06.720p.HDTV.x264-BiA");
        titles.add("Rules.of.Engagement.S03E05.720p.HDTV.X264-DIMENSION");
        titles.add("CSI.Miami.S07E20.720p.HDTV.X264-DIMENSION");
        titles.add("CSI.Miami.S07E20.HDTV.XviD-LOL");
        titles.add("Castle.2009.S01E04.720p.HDTV.x264-CTU");
        titles.add("One.Tree.Hill.S06E19.720p.HDTV.x264-CTU");
        titles.add("Castle.2009.S01E04.HDTV.XviD-XOR");
        titles.add("Medium.S05E08.HDTV.XviD-0TV");
        titles.add("Medium.S05E08.720p.HDTV.x264-CTU");
        titles.add("Saving.Grace.S02E12.HDTV.XviD-0TV");
        titles.add("The.Big.Bang.Theory.S02E19.HDTV.XviD-XOR");
        titles.add("Heroes.S03E21.720p.HDTV.X264-DIMENSION");
        titles.add("24.S07E16.720p.HDTV.X264-DIMENSION");
        titles.add("How.I.Met.Your.Mother.S04E19.720p.HDTV.X264-DIMENSION");
        titles.add("How.I.Met.Your.Mother.S04E19.HDTV.XviD-LOL");
        titles.add("One.Tree.Hill.S06E19.HDTV.XviD-NoTV");
        titles.add("My.Boys.S03E01.HDTV.XviD-2HD");
        titles.add("Gossip.Girl.S02E20.HDTV.XviD-XOR");
        titles.add("Gossip.Girl.S02E20.720p.HDTV.x264-CTU");
        titles.add("24.S07E16.HDTV.XviD-LOL");
        titles.add("Heroes.S03E21.HDTV.XviD-LOL");
        titles.add("Two.and.a.Half.Men.S06E19.720p.HDTV.x264-CTU");
        titles.add("House.S05E19.HDTV.XviD-LOL");
        titles.add("Chuck.S02E18.HDTV.XviD-XOR");
        titles.add("GREEK.S02E11.HDTV.XviD-0TV");
        titles.add("House.S05E19.720p.HDTV.X264-DIMENSION");
        titles.add("Two.and.a.Half.Men.S06E19.HDTV.XviD-NoTV");
        titles.add("Chuck.S02E18.720p.HDTV.x264-CTU");
        titles.add("Eli.Stone.S02E12.720p.HDTV.x264-BiA");
        titles.add("The.Big.Bang.Theory.S02E19.720p.HDTV.x264-CTU");
        titles.add("Stewart.Lees.Comedy.Vehicle.S01E03.WS.PDTV.XviD-RiVER");
        titles.add("Eli.Stone.S02E12.HDTV.XviD-BiA");
        titles.add("Extreme.Fishing.With.Robson.Green.S02E05.WS.PDTV.XviD-FTP");
        titles.add("Tonight.How.Safe.Is.Your.Hospital.WS.PDTV.XviD-REMAX");
        titles.add("The.Gadget.Show.S11E04.WS.PDTV.XviD-FTP");
        titles.add("Panorama.S57E13.WS.PDTV.XviD-REMAX");
        titles.add("Law.And.Order.UK.S01E06.WS.PDTV.XviD-RiVER");
        titles.add("Dispatches.The.Trouble.With.Boris.WS.PDTV.XviD-REMAX");
        titles.add("United.States.of.Tara.S01E11.720p.HDTV.X264-DIMENSION");
        titles.add("Watchdog.S24E23.WS.PDTV.XviD-REMAX");
        titles.add("Hannah.Montana.S03E12.DSR.XviD-ETACH");
        titles.add("Larry.The.Lawnmower.2009.03.24.720p.HDTV.x264-HDCP");
        titles.add("Underbelly.S02E09.720p.HDTV.x264-BiA");
        titles.add("Man.v.Food.S01E15.720p.HDTV.x264-SYS");
        titles.add("Keeping.Up.With.The.Kardashians.S03E04.HDTV.XviD-SAiNTS");
        titles.add("My.Classic.Car.S13E06.WS.DSR.XviD-WaLMaRT");
        titles.add("Verminators.S01E12.WS.PDTV.XviD-aAF");
        titles.add("Sun.Sea.And.AE.S01E08.WS.PDTV.XviD-aAF");
        titles.add("Celebrity.Juice.S02E05.WS.PDTV.XviD-aAF");
        titles.add("Underbelly.A.Tale.Of.Two.Cities.S02E09.Judas.Kiss.HDTV.XviD-FoV");
    }

    @Test
    public void testMatch() throws IllegalExpressionException {
        final ExpressionEvaluator evaluator = new TitleInterpreter();
        final String expression = "2009 & IV & fighter & !(lol|HDTV)";
        assertTrue(evaluator.evaluate(expression, SENTENCE));

        assertFalse("House wives", evaluator.evaluate(expression, "Desperate.house.wives.S08E66"));
    }

    @Test
    public void testTiles() throws IllegalExpressionException {
        assertTrue(find("Chuck & HDTV & 720p"));

    }

    private boolean find(final String expr) throws IllegalExpressionException {
        for (final String title : titles) {
            if (interpreter.evaluate(expr, title)) {
                System.out.println("title = " + title);
                return true;
            }
        }
        return false;
    }
}
