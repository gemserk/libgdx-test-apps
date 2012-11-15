package com.gemserk.libgdx.utils.gl;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.GLU;

public class BasicGraphicsWrapper implements Graphics{
	Graphics original;

	public BasicGraphicsWrapper(Graphics graphics) {
		original = graphics;
	}

	public boolean isGL11Available() {
		return original.isGL11Available();
	}

	public boolean isGL20Available() {
		return original.isGL20Available();
	}

	public GLCommon getGLCommon() {
		return original.getGLCommon();
	}

	public GL10 getGL10() {
		return original.getGL10();
	}

	public GL11 getGL11() {
		return original.getGL11();
	}

	public GL20 getGL20() {
		return original.getGL20();
	}

	public GLU getGLU() {
		return original.getGLU();
	}

	public int getWidth() {
		return original.getWidth();
	}

	public int getHeight() {
		return original.getHeight();
	}

	public float getDeltaTime() {
		return original.getDeltaTime();
	}

	public float getRawDeltaTime() {
		return original.getRawDeltaTime();
	}

	public int getFramesPerSecond() {
		return original.getFramesPerSecond();
	}

	public GraphicsType getType() {
		return original.getType();
	}

	public float getPpiX() {
		return original.getPpiX();
	}

	public float getPpiY() {
		return original.getPpiY();
	}

	public float getPpcX() {
		return original.getPpcX();
	}

	public float getPpcY() {
		return original.getPpcY();
	}

	public float getDensity() {
		return original.getDensity();
	}

	public boolean supportsDisplayModeChange() {
		return original.supportsDisplayModeChange();
	}

	public DisplayMode[] getDisplayModes() {
		return original.getDisplayModes();
	}

	public DisplayMode getDesktopDisplayMode() {
		return original.getDesktopDisplayMode();
	}

	public boolean setDisplayMode(DisplayMode displayMode) {
		return original.setDisplayMode(displayMode);
	}

	public boolean setDisplayMode(int width, int height, boolean fullscreen) {
		return original.setDisplayMode(width, height, fullscreen);
	}

	public void setTitle(String title) {
		original.setTitle(title);
	}

	public void setVSync(boolean vsync) {
		original.setVSync(vsync);
	}

	public BufferFormat getBufferFormat() {
		return original.getBufferFormat();
	}

	public boolean supportsExtension(String extension) {
		return original.supportsExtension(extension);
	}

	public void setContinuousRendering(boolean isContinuous) {
		original.setContinuousRendering(isContinuous);
	}

	public boolean isContinuousRendering() {
		return original.isContinuousRendering();
	}

	public void requestRendering() {
		original.requestRendering();
	}

	public boolean isFullscreen() {
		return original.isFullscreen();
	}
	
	
}
