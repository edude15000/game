package miniRPGtest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import miniRPG.Play;
import miniRPG.User;

import org.junit.Before;
import org.junit.Test;

public class Play_Test {

	User user;

	@Before
	public void setup() {
		user = Play.startUser("test", "Barbarian", false);
	}

	@Test
	public void userLoadNotNull_Test() {
		assertTrue(!user.equals(null));
	}

	@Test
	public void userSaveChangesNotEqual_Test() throws IOException {
		@SuppressWarnings("resource")
		String content = new Scanner(new File("Output.json")).useDelimiter(
				"\\Z").next();
		user.setMoney(user.getMoney() + 1);
		Play.saveData(user);
		@SuppressWarnings("resource")
		String content2 = new Scanner(new File("Output.json")).useDelimiter(
				"\\Z").next();
		assertTrue(!content.equals(content2));
	}
}
