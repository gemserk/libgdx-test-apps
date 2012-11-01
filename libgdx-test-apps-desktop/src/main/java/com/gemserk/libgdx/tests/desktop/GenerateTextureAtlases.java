package com.gemserk.libgdx.tests.desktop;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class GenerateTextureAtlases {

	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.alias = true;
		settings.stripWhitespaceX = true;
		settings.stripWhitespaceY = true;
		settings.alphaThreshold = 0;
		settings.filterMag = TextureFilter.Linear;
		settings.filterMin = TextureFilter.Linear;
		settings.format = Format.RGBA8888;
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		settings.edgePadding = true;
		settings.duplicatePadding = true;
		settings.paddingX = 2;
		settings.paddingY = 2;
		settings.pot = true;
		settings.rotation = true;
		TexturePacker2.process(settings, "../assets/images", "src/main/resources/data/images", "images");
		
		settings.paddingX = 0;
		settings.paddingY = 0;
		TexturePacker2.process(settings, "../assets/small", "src/main/resources/data/images", "small");
		
		settings.paddingX = 2;
		settings.paddingY = 2;
		TexturePacker2.process(settings, "../assets/polygon", "src/main/resources/data/images", "polygon");
	}

}
