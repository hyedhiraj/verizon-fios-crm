'use strict';

var gulp = require('gulp');
var path = require('path')
var $    = require('gulp-load-plugins')();
var browserSync = require('browser-sync');
var rename = require('gulp-rename');
var autoprefixer = require('gulp-autoprefixer');
var sourcemaps = require('gulp-sourcemaps');
// var compass = require('gulp-compass'); // changed by sass
var sass = require('gulp-sass');
var gutil = require('gutil');
var plumber = require('gulp-plumber');

// images
var imagemin = require('gulp-imagemin');
// js
var browserify = require('gulp-browserify');
var uglify = require('gulp-uglify');
// static site
var assemble = require('assemble');
var app = assemble();
// Statics paths
var paths = {
    src: 'src/',
    build: 'build/',
    images: 'src/assets/images/**/*',
    videos: 'src/assets/videos/**/*',
    icons: 'src/assets/ico/*',
    pdf: 'src/assets/pdf/*'
};
// Gulp default task
// Original version of gulp task that compiles the bootstrap just support to V3 and this project uses v4
// for this reason jus import the task that copy the css to the build
gulp.task('default', ['images','icons','videos','pdf','fonts','css',/*'compass'*/'sass','js', 'assemble'], function() {
// gulp.task('default', ['images','icons','videos','pdf','fonts','css','js', 'assemble'], function() {
    browserSync.init({
        server: "./build"
    });

    gulp.watch(paths.src+'assets/scss/**/*.scss', [/*'compass'*/ 'sass']);
    gulp.watch(paths.src+'assets/css/**/*.css', ['css-watch']);
    gulp.watch(paths.src+'assets/js/**/*.js', ['js-watch']);
    gulp.watch(paths.images, ['images-watch']);
    gulp.watch(paths.src+'views/**/*.hbs', ['assemble-watch']);

});

// Create tasks that ensures the given task is complete before reloading
// js watch
gulp.task('js-watch', ['js'], browserSync.reload);
// css watch
gulp.task('css-watch', ['css'], browserSync.reload);
// html watch
gulp.task('assemble-watch', ['assemble'], browserSync.reload);
// images watch
gulp.task('images-watch', ['images'], browserSync.reload);
// Build Task
gulp.task('build', ['clean'], function() {
    gulp.run('default');
});
//Clean Task
gulp.task('clean', function(cb) {
    var del = require('del');

    return del([paths.build+'**/*'], cb);
});
// ==================================================================
// Copy all static images
gulp.task('images', function() {
    return gulp.src(paths.images)
        .pipe(imagemin())
        .pipe(gulp.dest(paths.build+'assets/images'));
});
// Copy all videos
gulp.task('videos', function() {
    return gulp.src(paths.videos)
        .pipe(gulp.dest(paths.build+'assets/videos'));
});
// Copy all icons
gulp.task('icons', function() {
    return gulp.src(paths.icons)
        .pipe(gulp.dest(paths.build+'assets/ico'));
});
// Copy all pdf files
gulp.task('pdf', function() {
    return gulp.src(paths.pdf)
        .pipe(gulp.dest(paths.build+'assets/pdf'));
});
// Copy all fonts
gulp.task('fonts', function() {
    return gulp.src(paths.src+'assets/fonts/**/*')
        .pipe(gulp.dest(paths.build+'assets/fonts'));
});
// Copy all Static CSS
gulp.task('css', function() {
    return gulp.src(paths.src+'assets/css/**/*')
        .pipe(gulp.dest(paths.build+'assets/css'));
});
// Compile scss
/*
* Israel: comented beacuse compass conflicts instead use gulp-sass
*
gulp.task('compass', function() {
    return gulp.src(paths.src+'assets/scss/styles.scss')
        .pipe(compass({
            config_file: 'config.rb',
            css: process.env.PWD+'/'+paths.src+'assets/css',
            sass: process.env.PWD+'/'+paths.src+'assets/scss'
            //debug: true
        }))
        .pipe($.autoprefixer({
            browsers: ['last 8 versions', 'ie >= 9']
        }))
        .pipe(gulp.dest(paths.build+'assets/css'))
        .pipe(browserSync.reload({stream:true}));
});
*/

// compile SASS
gulp.task('sass', function () {
    return gulp.src(paths.src+'assets/scss/styles.scss')
      .pipe(plumber())
      .pipe(sass({
          outputStyle: 'expanded' // nested, expanded, compact, compressed
        })).on('error', sass.logError)
      .pipe(gulp.dest(paths.build+'assets/css'))
      .pipe(browserSync.reload({stream:true}));
  });
// ==================================================================

// Generate JS with browserify with source maps
gulp.task('js', function() {
    gulp.src(paths.src+'assets/js/libs/**/*.js')
        .pipe(gulp.dest(paths.build+'assets/js/libs'));
    gulp.src(paths.src+'assets/js/partials/**/*.js')
        .pipe(gulp.dest(paths.build+'assets/js/partials'));
    gulp.src(paths.src+'assets/js/data/**/*')
        .pipe(gulp.dest(paths.build+'assets/js/data'));
    gulp.src(paths.src+'assets/js/main.js')
        .pipe(browserify({
            debug : false
        }))
        .on('error', function (err) {
            gutil.log('ERROR: '+err.message);
            this.emit('end');
        })
        .pipe(uglify())
        //.pipe(rename('min.js'))
        .pipe(gulp.dest(paths.build+'assets/js'))
});

// Compress js
gulp.task('compressjs', ['js'], function() {
    gulp.src(paths.build+'assets/js/**/*.js')
        .pipe(uglify())
        .pipe(rename('min.js'))
        .pipe(gulp.dest(paths.build+'assets/js'))
});

// ==================================================================

// Assemble
app.helpers(paths.src+'helpers/**/*.js');
//app.partials(paths.src+'views/partials/**/*.hbs');
//app.layouts(paths.src+'views/layouts/**/*.hbs');

gulp.task('assemble', function(cb) {
    app.build(['views'], function(err) {
        if (err) return cb(err);
        console.log('Pages were build!');
        cb();
    });
});

app.task('views', function() {
    app.partials(paths.src+'views/partials/**/*.hbs');
    app.layouts(paths.src+'views/layouts/**/*.hbs');
    app.pages(paths.src+'views/pages/**/*.hbs');
    app.option('layout', paths.src+'views/layouts/default.hbs');
    app.option('page', paths.src+'views/pages/**/*.hbs');
    app.option('partial', paths.src+'views/partials/**/*.hbs');

    return app.toStream('pages')
        .pipe(app.toStream('partials'))
        .pipe(app.renderFile())
        .pipe(rename(function (path) {
            path.extname = ".html"
        }))
        .pipe(app.dest(paths.build))
        .pipe(browserSync.stream());
});

module.exports = app;