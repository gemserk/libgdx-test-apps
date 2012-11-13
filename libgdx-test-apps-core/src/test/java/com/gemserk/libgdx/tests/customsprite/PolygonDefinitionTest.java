package com.gemserk.libgdx.tests.customsprite;

import static org.junit.Assert.*;

import org.junit.Test;

public class PolygonDefinitionTest {

	@Test
	public void testRemoveDuplicatesWhenInMiddle() {
		float[] vertices = { 10f, 5f, 30f, 25f, 55f, 60f, //
				10f, 5f, 39f, 22f, 51f, 65f, //
		};

		float[] texCoords = { 0f, 0f, 0.1f, 0.1f, 0.2f, 0.2f, //
				0f, 0f, 0.3f, 0.3f, 0.5f, 0.5f //
		};

		short[] indices = { 0, 1, 2, 3, 4, 5 };
		
		// TEST WITH ONE VERTEX TWICE!!

		PolygonDefinition.cleanIndices(vertices, indices);
		PolygonDefinition.removeDuplicatedIndices(vertices, texCoords, indices);

		assertArrayEquals(new short[] { 0, 1, 2, 0, 3, 4 }, indices);
		assertArrayEquals(new float[] { 10f, 5f, 30f, 25f, 55f, 60f, 39f, 22f, 51f, 65f, 51f, 65f }, vertices, 0.01f);
		assertArrayEquals(new float[] { 0f, 0f, 0.1f, 0.1f, 0.2f, 0.2f, 0.3f, 0.3f, 0.5f, 0.5f, 0.5f, 0.5f }, texCoords, 0.01f);
	}
	
	@Test
	public void testRemoveDuplicatesWhenInEnd() {
		float[] vertices = { 10f, 5f, 30f, 25f, 55f, 60f, //
				51f, 65f, 39f, 22f, 10f, 5f, //
		};

		float[] texCoords = { 0f, 0f, 0.1f, 0.1f, 0.2f, 0.2f, //
				0.5f, 0.5f, 0.3f, 0.3f, 0f, 0f //
		};

		short[] indices = { 0, 1, 2, 3, 4, 5 };

		PolygonDefinition.cleanIndices(vertices, indices);
		PolygonDefinition.removeDuplicatedIndices(vertices, texCoords, indices);

		assertArrayEquals(new short[] { 0, 1, 2, 3, 4, 0 }, indices);
		assertArrayEquals(new float[] { 10f, 5f, 30f, 25f, 55f, 60f, 51f, 65f, 39f, 22f, 10f, 5f }, vertices, 0.01f);
		assertArrayEquals(new float[] { 0f, 0f, 0.1f, 0.1f, 0.2f, 0.2f, 0.5f, 0.5f, 0.3f, 0.3f, 0f, 0f }, texCoords, 0.01f);
	}
}
