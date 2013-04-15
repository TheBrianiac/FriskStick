package friskstick.cops.testingSources;

/**
 * Defines an in game player specific to this plugin
 */
public class PlayerClass {

	private String playerName;
	private boolean friskRequest;

	/**
	 * Constructs a player
	 */
	public PlayerClass(String name, boolean friskReq) {

		playerName = name; //Name of the player
		friskRequest = friskReq; //Has the player been requested to be frisked

	}

}
