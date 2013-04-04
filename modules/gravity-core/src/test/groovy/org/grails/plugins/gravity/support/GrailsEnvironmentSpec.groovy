package org.grails.plugins.gravity.support

import org.grails.plugins.gravity.formula.Formula

import spock.lang.Unroll
import spock.lang.Shared
import spock.lang.Specification

import org.apache.commons.io.FileUtils
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * This the GrailsEnvironment behavior
**/
class GrailsEnvironmentSpec extends Specification{

	@Shared
	def remoteResourceUrl

	/**
	 * We are going to user the same remote resource for all tests
	**/
	def setupSpec(){
		remoteResourceUrl = GrailsEnvironment.class.getResource("/jasmine-standalone-1.3.1.zip")
	}
	
	def "Downloading a given file"(){
		setup: "Setting up a grails environment"
			def formula = new Formula(name:"jasmine",url: remoteResourceUrl)
			def grailsEnvironment = new GrailsEnvironment()
		and: "Mocking"
			grailsEnvironment.metaClass.getWorkingDir = {->
				FileUtils.getTempDirectoryPath()
			}
		when: "Setting the formula to inform the environment where to put the resources"
			grailsEnvironment.formula = formula
		and: "Downloading the resources"
			def urlToDownload = formula.url
				assert urlToDownload
			def downloadedResourcesDir = grailsEnvironment.download urlToDownload
		then: "The trash environment should have the downloaded resources"
			downloadedResourcesDir.list().length > 0
	}

	def "Telling Gravity to use a certain files"(){
		setup: "Setting up a grails environment"
			def formula = new Formula(name:"jasmine",url: remoteResourceUrl)
			def grailsEnvironment = new GrailsEnvironment()
		and: "Mocking"
			grailsEnvironment.metaClass.getWorkingDir = {->
				FileUtils.getTempDirectoryPath()
			}
		when: "Setting the formula to inform the environment where to put the resources"
			grailsEnvironment.formula = formula
		and: "Downloading the resources"
			def urlToDownload = formula.url
				assert urlToDownload
			def downloadedResourcesDir = grailsEnvironment.download urlToDownload
			def usedFiles = grailsEnvironment.use [
				'jasmine.js',
				'jasmine.css',
				'jasmine-html.js'
			]
		then: "Checking"
			usedFiles.every{f-> f instanceof File}
			usedFiles.every{f-> f.exists() }
			usedFiles.size() == 3
	}

	@Unroll
	def "Trying to uncompress #fileName"(){
		when: "When trying to decompress a given file"
			def fileUrl = GrailsEnvironment.class.getResource("/${fileName}")
			def file = new File(fileUrl.file)
				assert file.exists()
			def fileOrDir = 
				new GrailsEnvironment().uncompressFileIfNecessary(file)
		then: "We check whether the file or dir exists or not"
			fileOrDir.exists()
			fileOrDir.isDirectory() == mustBecameDir
		where: "The possible file cases are"
				fileName		|	mustBecameDir
			"compressed.zip"	|		true
			"compressed.tar"	|		true
			"compressed.tar.gz"	|		true
			"compressed.js"		|		false
	}

}
