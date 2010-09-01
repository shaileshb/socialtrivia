
This folder contains external components. Download and extract the Android SDK and app engine to create the following structure:

$ROOT/tools
	android-sdk
	appengine-java-sdk


android-sdk setup
-----------------

$ jar xvf /home/shailesh/Downloads/appengine-java-sdk-1.3.7.zip 
$ mv appengine-java-sdk-1.3.7 appengine-java-sdk

$ tar xvzf ~/Downloads/android-sdk_r06-linux_86.tgz
$ mv android-sdk-linux_86/ android-sdk

Run the android SDK and AVD manager (android-sdk/tools/android) and install the android SDK version 1.5.

Create an AVD for testing.

