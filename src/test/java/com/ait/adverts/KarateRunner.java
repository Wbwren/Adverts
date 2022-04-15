package com.ait.adverts;

import com.intuit.karate.junit5.Karate;
/**
*	Karate runner
* 
*/
class KarateRunner {
	/**
	* 	Karate test for user authentication
	*/
    @Karate.Test
    /* default */ Karate testSample() {
        return Karate.run("Search").relativeTo(getClass());
    }
}