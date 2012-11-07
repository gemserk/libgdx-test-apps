package com.gemserk.libgdx.tests.customsprite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class PolygonDefinition {

	private float[] texCoords;
	private float[] vertices;

	public PolygonDefinition(float[] vertices, float[] texCoords) {
		this.vertices = vertices;
		this.texCoords = texCoords;
	}

	public static PolygonDefinition loadPolygonDefinition(FileHandle file, TextureRegion region) {
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()), 64);

		try {
			float[] vertices = null;
			float[] texCoords = null;

			while (true) {
				line = reader.readLine();

				if (line == null)
					break;
				else if (line.startsWith("v")) {
					// read in vertices
					String[] verticesValues = line.substring(1).trim().split(",");
					vertices = new float[verticesValues.length];
					for (int i = 0; i < verticesValues.length; i += 2) {
						vertices[i] = Float.parseFloat(verticesValues[i]);
						vertices[i + 1] = Float.parseFloat(verticesValues[i + 1]);
					}
				} else if (line.startsWith("u")) {
					// read in uvs
					String[] texCoordsValues = line.substring(1).trim().split(",");
					texCoords = new float[texCoordsValues.length];
					for (int i = 0; i < texCoordsValues.length; i += 2) {
						texCoords[i] = Float.parseFloat(texCoordsValues[i]);
						texCoords[i + 1] = Float.parseFloat(texCoordsValues[i + 1]);
					}

					transformToRegionCoordinates(texCoords, region);
				}
			}

			return new PolygonDefinition(vertices, texCoords);
		} catch (IOException ex) {
			throw new GdxRuntimeException("Error reading polygon shape file: " + file);
		} finally {
			try {
				reader.close();
			} catch (IOException ignored) {
			}
		}
	}

	private static void transformToRegionCoordinates(float[] texCoords, TextureRegion region) {
		float uvWidth = region.getU2() - region.getU();
		float uvHeight = region.getV2() - region.getV();

		for (int i = 0; i < texCoords.length; i += 2) {
			texCoords[i] = region.getU() + texCoords[i] * uvWidth;
			texCoords[i + 1] = region.getV() + texCoords[i + 1] * uvHeight;
		}
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoordinates() {
		return texCoords;
	}

}
