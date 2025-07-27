# Serbian Words

.PHONY: run
run: build
	@ adb install -r .github/android/app/build/outputs/apk/debug/app-debug.apk
	@ adb shell am start -S -n im.y2k.serbian_word/app.vendor.android_app.index\\\$$MainActivity

.PHONY: test
test: build_clj
	@ rm -f .github/android/app/build/reports/tests/testDebugUnitTest/index.html
	@ docker run --rm \
		-v ${PWD}/.github/temp/android:/root/.android \
		-v ${PWD}/.github/temp/gradle:/root/.gradle \
		-v ${PWD}/.github/android:/target \
		y2khub/cljdroid test || open ".github/android/app/build/reports/tests/testDebugUnitTest/index.html"

.PHONY: build
build: build_clj
	@ docker run --rm \
		-v ${PWD}/.github/temp/android:/root/.android \
		-v ${PWD}/.github/temp/gradle:/root/.gradle \
		-v ${PWD}/.github/android:/target \
		y2khub/cljdroid build

.PHONY: restore
restore: generate_makefile
	@ $(MAKE) -f .github/Makefile restore

.PHONY: build_clj
build_clj: generate_makefile
	@ rm -f .github/android/app/src/main/AndroidManifest.xml && \
		mkdir -p .github/android/app/src/main/java/y2k && \
		ly2k generate -target java > .github/android/app/src/main/java/y2k/RT.java
	@ $(MAKE) -f .github/Makefile

.PHONY: generate_makefile
generate_makefile:
	@ ly2k compile -target eval -src build.clj > .github/Makefile

.PHONY: clean
clean:
	@ rm -rf .github/android/app/src/main/AndroidManifest.xml
	@ rm -rf .github/android/app/src/main/java
