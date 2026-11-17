# VOICE X PRO — prototype acceptance tests
Download voice-x-pro-debug from GitHub Actions after a successful Build Android APK run, unzip and install app-debug.apk.
1. Launch on Android 8 or newer. Grant microphone permission; tap Record / stop again.
2. Record speech, stop, play original and adjusted. Adjust pitch in SETTINGS and compare.
3. Grant overlay permission; return to app and launch Free Fire or Free Fire MAX. Tap the right edge strip; open settings, hide and close the panel.
4. Verify recording stops when leaving the app; delete recording in settings.
5. Report device model, Android version, errors and build run URL.
## Limitations
Pitch-only recording preview; not a natural female AI engine. Game microphone is unchanged. No network inference, root changes or game modifications. APK compilation and device tests are not yet verified.
