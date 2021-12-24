package com.vsu.cgcourse.objreader;

import com.vsu.cgcourse.model.Polygon;
import com.vsu.cgcourse.vectormath.Vector2f;
import com.vsu.cgcourse.vectormath.Vector3f;
import java.util.ArrayList;

public class Parser {
    protected static Vector3f parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        return parseToVector3f(wordsInLineWithoutToken, lineInd);
    }

    protected static Vector3f parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        return parseToVector3f(wordsInLineWithoutToken, lineInd);

    }

    protected static Vector2f parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            return new Vector2f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)));

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
        }
    }

    private static Vector3f parseToVector3f(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.size() > 3) {
            throw new ObjReaderException("Too much arguments", lineInd);
        }

        try {
            return new Vector3f(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few arguments.", lineInd);
        }
    }

    protected static Polygon parsePolygon(
            ArrayList<String> wordsInLineWithoutToken,
            int lineInd,
            int amountOfVertices,
            int amountOfTextureVertices,
            int amountOfNormals) {
        if (wordsInLineWithoutToken.size() < 3) {
            throw new ObjReaderException("Too few arguments.", lineInd);
        }

        ArrayList<Integer> onePolygonVertexIndexes = new ArrayList<Integer>();
        ArrayList<Integer> onePolygonTextureVertexIndexes = new ArrayList<Integer>();
        ArrayList<Integer> onePolygonNormalIndexes = new ArrayList<Integer>();

        for (String s : wordsInLineWithoutToken) {
            parsePolygonWord(s, onePolygonVertexIndexes, onePolygonTextureVertexIndexes, onePolygonNormalIndexes,
                    amountOfVertices, amountOfTextureVertices, amountOfNormals, lineInd);
        }

        return new Polygon(onePolygonVertexIndexes, onePolygonTextureVertexIndexes, onePolygonNormalIndexes);
    }

    private static void parsePolygonWord(
            String wordInLine,
            ArrayList<Integer> onePolygonVertexIndexes,
            ArrayList<Integer> onePolygonTextureVertexIndexes,
            ArrayList<Integer> onePolygonNormalIndexes,
            int amountOfVertices,
            int amountOfTextureVertices,
            int amountOfNormals,
            int lineInd) {
        if (amountOfVertices == 0) {
            throw new ObjReaderException("No vertex at all in this file", lineInd);
        }

        try {
            String[] wordIndexes = wordInLine.split("/");
            switch (wordIndexes.length) {
                case 1 -> {
                    int vertexIndex = Integer.parseInt(wordIndexes[0]) - 1; //Why -1?

                    if (vertexIndex > amountOfVertices || vertexIndex < 0) {
                        throw new ObjReaderException("No such vertex index: " + vertexIndex
                                + ", max: " + amountOfVertices, lineInd);
                    }

                    onePolygonVertexIndexes.add(vertexIndex);
                }
                case 2 -> {
                    if (amountOfVertices == 0) {
                        throw new ObjReaderException("No texture vertex at all in this file.", lineInd);
                    }

                    int vertexIndex = Integer.parseInt(wordIndexes[0]) - 1;
                    int textureVertexIndex = Integer.parseInt(wordIndexes[1]) - 1;

                    if (vertexIndex > amountOfVertices || vertexIndex < 0) {
                        throw new ObjReaderException("No such vertex index: " + vertexIndex
                                + ", max: " + amountOfVertices, lineInd);
                    }

                    if (textureVertexIndex > amountOfTextureVertices || textureVertexIndex < 0) {
                        throw new ObjReaderException("No such texture vertex index: " + textureVertexIndex
                                + ", max: " + amountOfTextureVertices, lineInd);
                    }

                    onePolygonVertexIndexes.add(vertexIndex);
                    onePolygonTextureVertexIndexes.add(textureVertexIndex);
                }
                case 3 -> {
                    if (amountOfNormals == 0) {
                        throw new ObjReaderException("No normals at all in this file.", lineInd);
                    }

                    int vertexIndex = Integer.parseInt(wordIndexes[0]) - 1;
                    int normalIndex = Integer.parseInt(wordIndexes[2]) - 1;

                    if (vertexIndex > amountOfVertices || vertexIndex < 0) {
                        throw new ObjReaderException("No such vertex index: " + vertexIndex
                                + ", max: " + amountOfVertices, lineInd);
                    }

                    if (normalIndex > amountOfNormals || normalIndex < 0) {
                        throw new ObjReaderException("No such texture vertex index: " + normalIndex
                                + ", max: " + amountOfNormals, lineInd);
                    }

                    onePolygonVertexIndexes.add(vertexIndex);
                    onePolygonNormalIndexes.add(normalIndex);

                    if (!wordIndexes[1].equals("")) {
                        if (amountOfTextureVertices == 0) {
                            throw new ObjReaderException("No texture vertex at all in this file.", lineInd);
                        }

                        int textureVertexIndex = Integer.parseInt(wordIndexes[1]) - 1;

                        if (textureVertexIndex > amountOfTextureVertices || textureVertexIndex < 0) {
                            throw new ObjReaderException("No such texture vertex index: " + textureVertexIndex
                                    + ", max: " + amountOfTextureVertices, lineInd);
                        }

                        onePolygonTextureVertexIndexes.add(textureVertexIndex);
                    }
                }
                default -> {
                    throw new ObjReaderException("Invalid element size.", lineInd);
                }
            }

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse int value.", lineInd);

        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few arguments.", lineInd);
        }
    }
}