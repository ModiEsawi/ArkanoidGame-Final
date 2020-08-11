package sprites;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {
    private int blocksDefualtHeight;
    private int blocksDefualtWidth;
    private int blocksDefaultHitPoints;
    private Map<String, Integer> spacerWidth;
    private Map<String, BlockCreator> blockCreators;
    private String defaultFill = null;
    private Color defaultStroke = null;

    /**
     * Instantiates a new Blocks definition reader.
     */
    public BlocksDefinitionReader() {
        this.blockCreators = new TreeMap<>();
        this.spacerWidth = new TreeMap<>();
    }

    /**
     * Blocks details blocks from symbols factory.
     * reading each detail about the blocks in the file.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     * @throws Exception the exception
     */
    public BlocksFromSymbolsFactory blocksDetails(java.io.Reader reader) throws Exception {
        BufferedReader bufferedReader = (BufferedReader) reader;
        String currentLine;
        int endIndex , heightInfo = -1, widthInfo = -1, hitPointsInfo = -1;
        Color strokeInfo = null;
        char blockSymbol;
        try {
            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.startsWith("#") || currentLine.equals("")) { // ignore empty lines or comments
                    continue;
                }
                if (currentLine.startsWith("sdef")) { // the spacers
                    int spacerKeyIndex = 12, spacerKeyStartValue = spacerKeyIndex + 8;
                    this.spacerWidth.put(String.valueOf(currentLine.charAt(spacerKeyIndex)),
                            Integer.parseInt(currentLine.substring(spacerKeyStartValue)));
                }
                if (currentLine.startsWith("default")) { // getting the default block details
                    this.setDefaultValues(currentLine);
                }
                if (currentLine.startsWith("bdef")) { // getting each blocks details.
                    blockSymbol = currentLine.charAt(12);
                    List<Integer> spacesInLine = new ArrayList<>();
                    for (int i = 0; i < currentLine.length(); i++) {
                        char charAt = currentLine.charAt(i);
                        if (charAt == ' ') {
                            spacesInLine.add(i);
                        }
                    }
                    spacesInLine.add(currentLine.length());
                    if (!currentLine.contains("height")) {
                        heightInfo = this.blocksDefualtHeight;
                    } else if (currentLine.contains("height")) {
                        endIndex = currentLine.length();
                        for (Integer integer : spacesInLine) {
                            if (integer > currentLine.indexOf("height")) {
                                endIndex = integer;
                                break;
                            }
                        }
                        heightInfo = Integer.parseInt(currentLine.substring(currentLine.indexOf("height")
                                + "height".length() + 1, endIndex));
                    }
                    if (!currentLine.contains("width")) {
                        widthInfo = this.blocksDefualtWidth;
                    } else if (currentLine.contains("width")) {
                        endIndex = currentLine.length();
                        for (Integer integer : spacesInLine) {
                            if (integer > currentLine.indexOf("width")) {
                                endIndex = integer;
                                break;
                            }
                        }
                        widthInfo = Integer.parseInt(currentLine.substring(currentLine.indexOf("width")
                                + "width".length() + 1, endIndex));
                    }
                    if (!currentLine.contains("hit_points")) {
                        hitPointsInfo = this.blocksDefaultHitPoints;
                    } else if (currentLine.contains("hit_points")) {
                        endIndex = currentLine.length();
                        for (Integer integer : spacesInLine) {
                            if (integer > currentLine.indexOf("hit_points")) {
                                endIndex = integer;
                                break;
                            }
                        }
                        try {
                            String hit = currentLine.substring(currentLine.indexOf("hit_points") + "hit_points".length()
                                    + 1, endIndex);
                            hitPointsInfo = Integer.parseInt(hit);
                        } catch (Exception e) {
                            throw new Exception("invalid hit_points value of block with the symbol " + blockSymbol);
                        }
                    }
                    if (!currentLine.contains("stroke")) {
                        strokeInfo = this.defaultStroke;
                    } else if (currentLine.contains("stroke")) {
                        endIndex = currentLine.length();
                        for (Integer integer : spacesInLine) {
                            if (integer > currentLine.indexOf("stroke")) {
                                endIndex = integer;
                                break;
                            }
                        }
                        String temp = (currentLine.substring(currentLine.indexOf("stroke")
                                + "stroke".length() + 1, endIndex));
                        if (temp.contains("color")) {
                            String secTemp = currentLine.substring(currentLine.indexOf("stroke:") + "stroke:".length()
                                    + 6, endIndex - 1);
                            strokeInfo = ColorsParser.colorFromString(secTemp);
                        }
                    }
                    this.blockCreators.put(blockSymbol + "", this.creatorOfBlocks(this.getFills(currentLine,
                            spacesInLine, blockSymbol), strokeInfo,
                            heightInfo, widthInfo, hitPointsInfo));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new Exception("closing the block definition file failed!");
                }
            }
        }
        return new BlocksFromSymbolsFactory(spacerWidth, blockCreators);
    }

    /**
     * creating a "CreatorOfBlocks" object.
     *
     * @param fillMap   : blocks fill map according to HP.
     * @param stroke    : blocks stroke.
     * @param height    : blocks height.
     * @param width     : blocks width.
     * @param hitPoints : blocks hit points.
     * @return a creator of blocks object.
     */
    private CreatorOfBlocks creatorOfBlocks(Map<Integer, String> fillMap, Color stroke, int height, int width,
                                            int hitPoints) {
        return new CreatorOfBlocks(fillMap, stroke, height, width, hitPoints);
    }

    /**
     * getting the color of the block after each hit.
     *
     * @param currentLine  : the line that is now being red.
     * @param spacesInLine : the spaces in the current line.
     * @param blockSymbol  : the symbol of the block.
     * @return : the map that hold the fills.
     * @throws Exception : throws exception.
     */
    private Map<Integer, String> getFills(String currentLine, List<Integer> spacesInLine, char blockSymbol)
            throws Exception {
        Map<Integer, String> fillMap = new TreeMap<>();
        int endIndex = -1;
        if (currentLine.contains("fill:")) {
            for (Integer integer : spacesInLine) {
                if (integer > currentLine.indexOf("fill:")) {
                    endIndex = integer;
                    break;
                }
            }
            try {
                String fill = currentLine.substring(currentLine.indexOf("fill:") + "fill:".length() + 6,
                        endIndex - 1);
                fillMap.put(0, fill);
            } catch (Exception e) {
                throw new Exception("invalid fill value of block with the symbol " + blockSymbol);
            }
        }
        if (currentLine.contains("fill-")) {
            int temp;
            String newSpacesInLine = currentLine + "";
            while (true) {
                spacesInLine.clear();
                if (newSpacesInLine.contains("fill-")) {
                    temp = Integer.parseInt(newSpacesInLine.charAt(newSpacesInLine.indexOf("fill-") + 5)
                            + "");
                    for (int j = 0; j < newSpacesInLine.length(); j++) {
                        if (newSpacesInLine.charAt(j) == ' ') {
                            spacesInLine.add(j);
                        }
                    }
                    spacesInLine.add(newSpacesInLine.length());
                    for (Integer integer : spacesInLine) {
                        if (integer > newSpacesInLine.indexOf("fill-")) {
                            endIndex = integer;
                            break;
                        }
                    }
                    try {
                        String status = newSpacesInLine.substring(newSpacesInLine.indexOf("fill-") + "fill-"
                                .length() + 8, endIndex - 1);
                        fillMap.put(temp, status);
                    } catch (Exception e) {
                        throw new Exception("invalid fill-" + temp + " value of block with symbol " + blockSymbol);
                    }
                }
                try {
                    newSpacesInLine = newSpacesInLine.substring(newSpacesInLine.indexOf("fill-") + 6);
                } catch (Exception e) {
                    break;
                }
            }
        } else if (!currentLine.contains("fill:")) {
            fillMap.put(0, defaultFill);
        }
        return fillMap;
    }

    /**
     * getting each default value of the block.
     *
     * @param currentLine : the line that is now being red.
     */
    private void setDefaultValues(String currentLine) {
        int endIndex = -1, heightInfo, widthInfo, hitPointsInfo;
        String fillInfo;
        List<Integer> spacesInLine = new ArrayList<>();
        for (int i = 0; i < currentLine.length(); i++) {
            if (currentLine.charAt(i) == ' ') {
                spacesInLine.add(i);
            }
        }
        spacesInLine.add(currentLine.length());
        if (currentLine.contains("height")) {
            endIndex = currentLine.length();
            for (Integer integer : spacesInLine) {
                if (currentLine.indexOf("height") < integer) {
                    endIndex = integer;
                    break;
                }
            }
            heightInfo = Integer.parseInt(currentLine.substring("height".length() + 1
                    + currentLine.indexOf("height"), endIndex));
            this.blocksDefualtHeight = (heightInfo);
        }
        spacesInLine.add(currentLine.length());
        if (currentLine.contains("width")) {
            for (Integer integer : spacesInLine) {
                endIndex = currentLine.length();
                if (currentLine.indexOf("width") < integer) {
                    endIndex = integer;
                    break;
                }
            }
            widthInfo = Integer.parseInt(currentLine.substring("width".length() + 1
                    + currentLine.indexOf("width"), endIndex));
            this.blocksDefualtWidth = (widthInfo);
        }
        spacesInLine.add(currentLine.length());
        if (currentLine.contains("hit_points")) {
            for (int j = 0; j < currentLine.length(); j++) {
                endIndex = currentLine.length();
                if (currentLine.indexOf("hit_points") < spacesInLine.get(j)) {
                    endIndex = spacesInLine.get(j);
                    break;
                }
            }
            hitPointsInfo = Integer.parseInt(currentLine.substring("hit_points".length() + 1
                    + currentLine.indexOf("hit_points"), endIndex));
            this.blocksDefaultHitPoints = (hitPointsInfo);
        }
        if (currentLine.contains("fill")) {
            endIndex = currentLine.length();
            for (Integer integer : spacesInLine) {
                if (currentLine.indexOf("fill") < integer) {
                    endIndex = integer;
                    break;
                }
            }
            fillInfo = (currentLine.substring("fill".length() + 6
                    + currentLine.indexOf("fill"), endIndex - 1));
            this.defaultFill = (fillInfo);
        }
        if (currentLine.contains("stroke")) {
            endIndex = currentLine.length();
            for (Integer integer : spacesInLine) {
                if (currentLine.indexOf("stroke") < integer) {
                    endIndex = integer;
                    break;
                }
            }
            String temp = (currentLine.substring("stroke".length()
                    + currentLine.indexOf("stroke"), endIndex));
            if (temp.contains("color")) {
                String substring = currentLine.substring(currentLine.indexOf("stroke:") + "stroke:".length()
                        + 6, endIndex - 1);
                this.defaultStroke = ColorsParser.colorFromString(substring);
            }
        }
    }
}

