package org.grails.plugins.gravity.formula

/**
 * This class is inspired in the Formula class found in homebrew project
 * https://github.com/mxcl/homebrew
 *
 * The formula has 3 main methods:
 *
 * <ul>
 *	<li>install(): This method downloads the javascript library, creates where the js tests are going to be located...etc</li>
 *	<li>report(): This method should scrap the js test result and show it in the Grails console </li>
 *  <li>uninstall(): This method cleans up all the resources installed</li>
 * </ul>
 * 
 *
**/
class Formula{

	def name // = "jasmine"
	def home // = "http://somejavascriptlibrary.com"
	def url  // = "http://somejavascriptlibrary.github.com/downloads/release-1.1.0.js"
	def sha  // = "hsiufh9229uf29hf92"

	def install(env){}

	def executeTests(testEnv){
		// Here we should use Geb to scrap the result page and show the results in Grails console

		/*
			def url = runtimeInfo.urlToTest
			Driver.browse{
				go url

				runtimeInfo.howManyTest.each{
					try{
						evaluateAssert waitFor()
						runtimeInfo.grailsConsole.addStatus()
					} catch (e){
						
					}
				}

			}

		*/
	}

	def uninstall(env){
		// Delete testing directory ??? Maybe not, don´t want to lose already created tests
	}

}
