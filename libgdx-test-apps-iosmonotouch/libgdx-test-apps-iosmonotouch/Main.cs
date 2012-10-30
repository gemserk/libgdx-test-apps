using System;
using System.Collections.Generic;
using System.Linq;

using MonoTouch.Foundation;
using MonoTouch.UIKit;
using com.badlogic.gdx.backends.ios;

using java.io;
using com.badlogic.gdx.files;

using java.text;

using System.IO;
using System.Json;

using System.Text;

using com.gemserk.libgdx.tests.ios;

namespace libgdxtestapps.iosmonotouch
{
	public class Application
	{
		[Register ("AppDelegate")]
		public partial class AppDelegate : IOSApplication
		{
			public AppDelegate (): base(getGame(), getConfig())
			{

			}
			
			internal static IOSApplicationConfiguration getConfig ()
			{
				IOSApplicationConfiguration config = new IOSApplicationConfiguration ();
				config.orientationLandscape = true;
				config.orientationPortrait = false;
				config.useAccelerometer = true;
				return config;
			}

			internal static com.badlogic.gdx.ApplicationListener getGame ()
			{
				return GameProvider.createGame();
			}
		}
		
		static void Main (string[] args)
		{
			UIApplication.Main (args, null, "AppDelegate");
		}
	}

}
