/*global module:false*/
module.exports = function(grunt) {
    'use strict';

    // Project configuration.
    grunt.initConfig({
        // Task configuration.
        jshint: {
            options: {
                jshintrc: '.jshintrc'
            },
            gruntfile: {
                src: 'Gruntfile.js'
            },
            libTest: {
                src: ['lib/*.js', 'test/*.js']
            }
        },
        qunit: {
            files: ['test/*.html']
        },
        watch: {
            gruntfile: {
                files: 'Gruntfile.js',
                tasks: ['jshint:gruntfile']
            },
            libTest: {
                files: ['lib/*.js', 'test/*.js'],
                tasks: ['jshint:libTest', 'jshint', 'qunit']
            }
        }
    });

    // These plugins provide necessary tasks.
    grunt.loadNpmTasks('grunt-contrib-qunit');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-watch');
    
    // Default task.
    grunt.registerTask('default', ['jshint', 'qunit']);
};
