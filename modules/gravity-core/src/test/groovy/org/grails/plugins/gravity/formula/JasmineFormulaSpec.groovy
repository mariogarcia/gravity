package org.grails.plugins.gravity.formula

import spock.lang.Specification
import org.grails.plugins.gravity.support.GrailsEnvironment

/**
 * Testing the Jasmine formula
**/
class JasmineFormulaSpec extends Specification{

	def "Install Jasmine formula"(){
		setup: "Create a new instance of the formula and a new environment"
			def formula = new JasmineFormula()
			def env = new GrailsEnvironment(formula:formula)
		when: "Executing the installation with the environment"
			formula.install(env)
		then: "There is no installation errors"
			env.installationErrors.isEmpty()
		and: "We now have three directories"
			env.testDirectory
			env.trashDirectory
			env.currentDirectory
		and: "We should have the required files in the current dir"
			env.currentDir.files.size() == formula.requiredFiles.size()
	}

}
