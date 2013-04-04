package org.grails.plugins.gravity.support

import spock.lang.Specification

class CompressingUtilsSpec extends Specification{

	def "Uncompressing a zip file"(){
		setup: "Getting the zip"
			def instance= new CompressingUtils()
			def zipFile = CompressingUtilsSpec.class.getResource("/compress.zip")
		when:
			def zipDir = instance.uncompressZip(zipFile) 
		then:
			zipDir.exists()
			zipDir.listFiles().length > 0
	}
}

