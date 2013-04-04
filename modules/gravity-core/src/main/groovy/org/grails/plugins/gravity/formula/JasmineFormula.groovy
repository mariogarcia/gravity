package org.grails.plugins.gravity.formula

/**
 * This class installs http://pivotal.github.com/jasmine/ infraestructure in 
 * our Grails application in order to be able to create Jasmine tests and
 * execute them through Gravity.
 *
 * @author Mario Garcia
 *
**/
class JasmineFormula extends Formula{

	def name = "jasmine" 
	def url  = "https://github.com/downloads/pivotal/jasmine/jasmine-standalone-1.3.1.zip"
	
	/**
	 * This method is called and wrapped in other method so there is no
	 * need to try/catch because env.download throws a DownloadException and
	 * env.use throws UseException,
	**/
	def install(env){
	 /* This downloads the url file in the trash and if it is 
		a zip or a tar.gz file it will try to uncompress it */
		env.console?.updateStatus "Downloading ${name} from --> ${url}"
		env.download new URL(url)
	 /* This method looks for some files recursively in the downloaded 
		directory/file */
		env.console?.updateStatus "Looking for framework files"
		env.use [
			'jasmine.js'.
			'jasmine-reporter.js'
		]
	 /* Tell the user the installation has finished */
		env.console?.updateStatus "${name} installed!!"
	}



}
