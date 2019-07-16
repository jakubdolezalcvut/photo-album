# Infinite Scrolling

My implementation of albums and photos in RecyclerView written in Kotlin. The most significiant dependencies are enumerated below:

- Gradle 5 + Kotlin DSL for build
- Architecture driven by Android Jetpack including ViewModels and LiveData
- Toothpick for Dependency Injection
- RxJava for performing IO on background and failing gracefully
- Retrofit for loading data
- Glide for loading images
- [FlexboxLayout](https://github.com/google/flexbox-layout) for showing pictures and adapting to various screen sizes
- [Stetho](http://facebook.github.io/stetho/) for debugging of network calls
- [MaterialProgressBar](https://github.com/DreaminginCodeZH/MaterialProgressBar) which offers no-padding progress bar embeddable to Toolbar.