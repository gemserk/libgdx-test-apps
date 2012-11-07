package com.gemserk.libgdx.tests.customsprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.NumberUtils;

public class MeshSpriteSingle implements MeshSpriteInterface {

	MeshRegion region;
	float x, y;
	float width, height;
	float scaleX = 1f, scaleY = 1f;
	float rotation;
	float originX, originY;
	float[] vertices;
	boolean dirty;
	Rectangle bounds = new Rectangle();

	public MeshRegion getRegion() {
		return region;
	}

	private final Color color = new Color(1f, 1f, 1f, 1f);

	public MeshSpriteSingle(MeshRegion region) {
		setRegion(region);
		setColor(1, 1, 1, 1);
		setSize(region.getRegion().getRegionWidth(), region.getRegion().getRegionHeight());
		setOrigin(width / 2, height / 2);
	}

	public MeshSpriteSingle(MeshSpriteSingle sprite) {
		set(sprite);
	}

	public void set(MeshSpriteSingle sprite) {
		if (sprite == null)
			throw new IllegalArgumentException("sprite cannot be null.");

		setRegion(sprite.region);

		x = sprite.x;
		y = sprite.y;
		width = sprite.width;
		height = sprite.height;
		originX = sprite.originX;
		originY = sprite.originY;
		rotation = sprite.rotation;
		scaleX = sprite.scaleX;
		scaleY = sprite.scaleY;
		color.set(sprite.color);
		dirty = sprite.dirty;
	}

	@Override
	public void setBounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		dirty = true;
	}

	@Override
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;

		dirty = true;
	}

	@Override
	public void setPosition(float x, float y) {
		translate(x - this.x, y - this.y);
	}

	@Override
	public void setX(float x) {
		translateX(x - this.x);
	}

	@Override
	public void setY(float y) {
		translateY(y - this.y);
	}

	@Override
	public void translateX(float xAmount) {
		this.x += xAmount;

		if (dirty)
			return;

		final float[] vertices = this.vertices;
		for (int i = 0; i < vertices.length; i += MeshSpriteBatch.VERTEX_SIZE) {
			vertices[i] += xAmount;
		}
	}

	@Override
	public void translateY(float yAmount) {
		y += yAmount;

		if (dirty)
			return;

		final float[] vertices = this.vertices;
		for (int i = 0; i < vertices.length; i += MeshSpriteBatch.VERTEX_SIZE) {
			vertices[i + 1] += yAmount;
		}
	}

	@Override
	public void translate(float xAmount, float yAmount) {
		x += xAmount;
		y += yAmount;

		if (dirty)
			return;

		final float[] vertices = this.vertices;
		for (int i = 0; i < vertices.length; i += MeshSpriteBatch.VERTEX_SIZE) {
			vertices[i] += xAmount;
			vertices[i + 1] += yAmount;
		}
	}

	@Override
	public void setColor(Color tint) {
		float color = tint.toFloatBits();

		final float[] vertices = this.vertices;
		for (int i = 0; i < vertices.length; i += MeshSpriteBatch.VERTEX_SIZE) {
			vertices[i + 2] = color;
		}
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		int intBits = ((int) (255 * a) << 24) | ((int) (255 * b) << 16) | ((int) (255 * g) << 8) | ((int) (255 * r));
		float color = NumberUtils.intToFloatColor(intBits);
		final float[] vertices = this.vertices;
		for (int i = 0; i < vertices.length; i += MeshSpriteBatch.VERTEX_SIZE) {
			vertices[i + 2] = color;
		}
	}

	@Override
	public void setOrigin(float originX, float originY) {
		this.originX = originX;
		this.originY = originY;
		dirty = true;
	}

	@Override
	public void setRotation(float degrees) {
		this.rotation = degrees;
		dirty = true;
	}

	@Override
	public void rotate(float degrees) {
		rotation += degrees;
		dirty = true;
	}

	@Override
	public void setScale(float scaleXY) {
		this.scaleX = scaleXY;
		this.scaleY = scaleXY;
		dirty = true;
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		dirty = true;
	}

	@Override
	public void scale(float amount) {
		this.scaleX += amount;
		this.scaleY += amount;
		dirty = true;
	}

	@Override
	public float[] getVertices() {
		if (dirty) {
			dirty = false;

			final float worldOriginX = x + originX;
			final float worldOriginY = y + originY;
			float sX = width / region.getRegion().getRegionWidth();
			float sY = height / region.getRegion().getRegionHeight();
			float fx, rx;
			float fy, ry;

			float[] localVertices = region.getLocalVertices();

			final float cos = MathUtils.cosDeg(rotation);
			final float sin = MathUtils.sinDeg(rotation);

			for (int i = 0; i < localVertices.length; i += 2) {
				fx = localVertices[i] * sX;
				fy = localVertices[i + 1] * sY;

				fx -= originX;
				fy -= originY;

				if (scaleX != 1.0f || scaleY != 1.0) {
					fx *= scaleX;
					fy *= scaleY;
				}

				rx = cos * fx - sin * fy;
				ry = sin * fx + cos * fy;

				rx += worldOriginX;
				ry += worldOriginY;

				vertices[(i / 2) * 5] = rx;
				vertices[((i / 2) * 5) + 1] = ry;
			}
		}

		return vertices;
	}

	@Override
	public Rectangle getBoundingRectangle() {
		if (!dirty)
			return bounds;

		final float[] vertices = getVertices();

		float minx = vertices[0];
		float miny = vertices[1];
		float maxx = vertices[0];
		float maxy = vertices[1];

		for (int i = 0; i < vertices.length; i += 5) {
			minx = minx > vertices[i] ? vertices[i] : minx;
			maxx = maxx < vertices[i] ? vertices[i] : maxx;
			miny = miny > vertices[i + 1] ? vertices[i + 1] : miny;
			maxy = maxy < vertices[i + 1] ? vertices[i + 1] : maxy;
		}

		bounds.x = minx;
		bounds.y = miny;
		bounds.width = maxx - minx;
		bounds.height = maxy - miny;

		return bounds;
	}

	public void draw(MeshSpriteBatch spriteBatch) {
		spriteBatch.draw(getTexture(), getVertices(), 0, vertices.length);
	}

	public void draw(MeshSpriteBatch spriteBatch, float alphaModulation) {
		Color color = getColor();
		float oldAlpha = color.a;
		color.a *= alphaModulation;
		setColor(color);
		draw(spriteBatch);
		color.a = oldAlpha;
		setColor(color);
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
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getOriginX() {
		return originX;
	}

	@Override
	public float getOriginY() {
		return originY;
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public float getScaleX() {
		return scaleX;
	}

	@Override
	public float getScaleY() {
		return scaleY;
	}

	@Override
	public Color getColor() {
		int intBits = NumberUtils.floatToIntColor(vertices[2]);
		Color color = this.color;
		color.r = (intBits & 0xff) / 255f;
		color.g = ((intBits >>> 8) & 0xff) / 255f;
		color.b = ((intBits >>> 16) & 0xff) / 255f;
		color.a = ((intBits >>> 24) & 0xff) / 255f;
		return color;
	}

	public void setRegion(MeshRegion region) {
		this.region = region;

		float[] localVertices = region.getLocalVertices();
		float[] localTextureCoords = region.getTextureCoords();

		int verticesCount = localVertices.length / 2;

		vertices = new float[verticesCount * MeshSpriteBatch.VERTEX_SIZE];

		// Pack the region info into this sprite's vertices
		for (int i = 0; i < localVertices.length / 2; i++) {
			vertices[(i * MeshSpriteBatch.VERTEX_SIZE)] = localVertices[(i * 2)];
			vertices[(i * MeshSpriteBatch.VERTEX_SIZE) + 1] = localVertices[(i * 2) + 1];
			vertices[(i * MeshSpriteBatch.VERTEX_SIZE) + 2] = 0f;
			vertices[(i * MeshSpriteBatch.VERTEX_SIZE) + 3] = color.toFloatBits();
			vertices[(i * MeshSpriteBatch.VERTEX_SIZE) + 4] = localTextureCoords[(i * 2)];
			vertices[(i * MeshSpriteBatch.VERTEX_SIZE) + 5] = localTextureCoords[(i * 2) + 1];
		}
	}

	@Override
	public Texture getTexture() {
		MeshRegion meshRegion = getRegion();
		TextureRegion textureRegion = meshRegion.getRegion();
		return textureRegion == null ? null : textureRegion.getTexture();
	}
}
