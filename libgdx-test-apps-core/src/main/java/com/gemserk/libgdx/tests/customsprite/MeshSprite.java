package com.gemserk.libgdx.tests.customsprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MeshSprite implements MeshSpriteInterface {

	private static final int POSITION_SIZE = 3;
	private static final int COLOR_SIZE = 1;
	private static final int TEXTURE_COORDINATE_SIZE = 2;

	private static final int VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE + TEXTURE_COORDINATE_SIZE;

	public Texture texture;

	/**
	 * a float[] with 3 floats per vertex specifying x, y, z.
	 */
	public float[] vertices;

	/**
	 * a float[] with 2 floats per vertex specifying the texture coordinates u, v.
	 */
	public float[] texCoords;

	/**
	 * a float[] with the processed vertices located after transforming the sprite.
	 */
	public float[] finalVertices;

	public Rectangle bounds;

	public float ox, oy;
	public float x, y;
	public float angle;
	public float sx, sy;

	public Color color;

	private boolean dirty;

	private int verticesCount;

	public MeshSprite(float[] vertices, float[] texCoords, Texture texture) {
		this.vertices = vertices;
		this.texCoords = texCoords;
		this.texture = texture;
		this.bounds = new Rectangle();
		this.color = new Color();

		// calculate mesh center

		setPosition(0, 0);
		setScale(1, 1);
		setRotation(0);

		verticesCount = vertices.length / POSITION_SIZE;

		finalVertices = new float[verticesCount * VERTEX_SIZE];
	}

	@Override
	public void setBounds(float x, float y, float width, float height) {
		float boundsWidth = bounds.getWidth();
		float boundsHeight = bounds.getHeight();

		if (boundsWidth == width && boundsHeight == height)
			return;

		setScale(width / boundsWidth, height / boundsHeight);
	}

	@Override
	public void setSize(float width, float height) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	@Override
	public void setX(float x) {
		if (Float.compare(this.x, x) == 0)
			return;
		this.x = x;
		dirty = true;
	}

	@Override
	public void setY(float y) {
		if (Float.compare(this.y, y) == 0)
			return;
		this.y = y;
		dirty = true;
	}

	@Override
	public void translateX(float xAmount) {
		if (Float.compare(xAmount, 0f) == 0)
			return;
		this.x += xAmount;
		dirty = true;
	}

	@Override
	public void translateY(float yAmount) {
		if (Float.compare(yAmount, 0f) == 0)
			return;
		this.y += yAmount;
		dirty = true;
	}

	@Override
	public void translate(float xAmount, float yAmount) {
		translateX(xAmount);
		translateY(yAmount);
	}

	@Override
	public void setColor(Color tint) {
		if (color.equals(tint))
			return;
		color.set(tint);
		dirty = true;
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		if (Float.compare(color.r, r) == 0 //
				&& Float.compare(color.g, g) == 0 //
				&& Float.compare(color.b, b) == 0 //
				&& Float.compare(color.a, a) == 0)
			return;
		color.set(r, g, b, a);
		dirty = true;
	}

	@Override
	public void setOrigin(float originX, float originY) {
		if (Float.compare(ox, originX) == 0 && Float.compare(oy, originY) == 0)
			return;
		this.ox = originX;
		this.oy = originY;
		dirty = true;
	}

	@Override
	public void setRotation(float degrees) {
		if (Float.compare(angle, degrees) == 0)
			return;
		this.angle = degrees;
		dirty = true;
	}

	@Override
	public void rotate(float degrees) {
		if (Float.compare(0f, degrees) == 0)
			return;
		this.angle += degrees;
		dirty = true;
	}

	@Override
	public void setScale(float scaleXY) {
		setScale(scaleXY, scaleXY);
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		if (Float.compare(scaleX, sx) == 0 || Float.compare(scaleY, sy) == 0)
			return;
		this.sx = scaleX;
		this.sy = scaleY;
		dirty = true;
	}

	@Override
	public void scale(float amount) {
		if (Float.compare(amount, 1f) == 0)
			return;
		setScale(sx * amount, sy * amount);
	}

	final Matrix4 matrix4 = new Matrix4();
	final Vector3 vector3 = new Vector3();

	@Override
	public float[] getVertices() {
		if (!dirty)
			return finalVertices;

		int vertexIndex = 0;
		int textureIndex = 0;

		matrix4.idt();
		matrix4.translate(-ox, -oy, 0f);
		matrix4.rotate(Vector3.Z, angle);
		matrix4.scale(sx, sy, 1f);
		matrix4.translate(x, y, 0f);

		for (int i = 0; i < finalVertices.length; i++) {
			int current = i * VERTEX_SIZE;

			vector3.set(vertices[vertexIndex], vertices[vertexIndex + 1], vertices[vertexIndex + 2]);
			vector3.mul(matrix4);

			finalVertices[current + 0] = vector3.x;
			finalVertices[current + 1] = vector3.y;
			finalVertices[current + 2] = vector3.z;

			finalVertices[current + POSITION_SIZE] = color.toFloatBits();
			finalVertices[current + POSITION_SIZE + COLOR_SIZE] = texCoords[textureIndex];
			finalVertices[current + POSITION_SIZE + COLOR_SIZE + 1] = texCoords[textureIndex + 1];

			vertexIndex += 3;
			textureIndex += 2;
		}

		dirty = false;
		return finalVertices;
	}

	@Override
	public Rectangle getBoundingRectangle() {
		return bounds;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getWidth() {
		return bounds.width;
	}

	@Override
	public float getHeight() {
		return bounds.height;
	}

	@Override
	public float getOriginX() {
		return ox;
	}

	@Override
	public float getOriginY() {
		return oy;
	}

	@Override
	public float getRotation() {
		return angle;
	}

	@Override
	public float getScaleX() {
		return sx;
	}

	@Override
	public float getScaleY() {
		return sy;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Texture getTexture() {
		return texture;
	}

}
