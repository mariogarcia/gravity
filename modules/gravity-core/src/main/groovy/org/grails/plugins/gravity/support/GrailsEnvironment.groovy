package org.grails.plugins.gravity.support

import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils

import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.build.logging.GrailsConsole

/**
 * This class will help the user to do all tasks related to download, install,
 * an uninstall a given formula
**/
class GrailsEnvironment{

	static final DIR_GRAVITY_HOME = ".gravity"
	static final DIR_GRAVITY_TRASH = "trash"
	static final DIR_GRAVITY_CURRENT = "current"

	static final EXTENSION_TAR = "tar"
	static final EXTENSION_TAR_GZIP = "tar.gz"
	static final EXTENSION_ZIP = "zip"

	def grailsApplication
	def formula

	/**
	 * This method downloads distribution files to the Gravity's temporary
	 * directory
	**/
	def download(url){
	 /* Setting all directories */
		def formulaDir = new File(gravityDir,formula.name)
		def downloadDir = new File(formulaDir,DIR_GRAVITY_TRASH)
		def currentDir = new File(formulaDir,DIR_GRAVITY_CURRENT)
		def formulaFile = new File(downloadDir,url.toString().tokenize("/")[-1])
	 /* Checking if some of them should be created */
		def dirs = [downloadDir,formulaDir,currentDir]
	 /* And */
		checkIfExistsAndCreate(dirs)
	 /* Downloading the file */
		def file = new FileOutputStream(formulaFile)
    	def out = new BufferedOutputStream(file)
	 /* Copying and closing */
    	out << url.openStream()
    	out.close()
	 /* Uncompress if needed */
		uncompressFileIfNecessary(formulaFile)			
	 /* Returning the downloads directory */	
		downloadDir	
	}

	/**
	 * This method tells Gravity what files from the framework
	 * we are installing are we going to use in testing.
	 * 
	 * @param filesToUse The files we want to take from the testing
	 * framework donwloaded to use them in our js tests
	**/
	def use(filesToUse){
		// TMF
	}

	/**
	 * This method uncompresses the file passed as parameter
	 * if it was compressed. 
	 * 
	 * @param file The file that maybe we need to uncompress
	**/
	def uncompressFileIfNecessary(File file){
	 /* If the file is not compressed we return the file */
		def result = file
	 /* Getting filename's extension */
		def extension = FilenameUtils.getExtension(file.name)
		def util = new CompressingUtils()
	 /* Depending on the extension */
		switch(extension){
			case EXTENSION_ZIP:
				result = util.uncompressZip(file)	
			break
			case EXTENSION_TAR:

			break
			case EXTENSION_TAR_GZIP:

			break	
			default:

			break
		}
	 /* Returning the file or the uncompressed directory */
		result
	}

	/**
	 * Better mocking support if wrapped in a getter
	**/
	def getConsole(){
		GrailsConsole.getInstance()
	}

	/**
	 * If the directory doesn´t exist this method will
	 * create the directory
	**/
	def checkIfExistsAndCreate(dirs){
		dirs.each{d->
			if (!d.exists()){
				d.mkdirs()
			}
		}
	}
	
	/**
	 * Getting the home of the Gravity installations
	**/
	def getGravityDir(){
		def userHome = FileUtils.getUserDirectory() 
		def gravityHome = new File(userHome,DIR_GRAVITY_HOME)
	 /* Returning the Gravity home directory */	
		gravityHome
	}
	
	/**
	 * We need to know where the testing artifacts are going to be
	 * created
	**/
	def getWorkingDir(){
		grailsApplication.WORKING_DIR
	}

}
