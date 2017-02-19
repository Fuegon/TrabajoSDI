package uo.sdi.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	RegisterTests.class,
	OtherTests.class,
	ChangeDataTests.class
})
public class AllTest {

}
