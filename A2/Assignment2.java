import java.util.ArrayList;
import java.sql.*;
import java.util.Collections;

public class Assignment2 {

	/* A connection to the database */
	private Connection connection;

	/**
	 * Empty constructor. There is no need to modify this. 
	 */
	public Assignment2() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Failed to find the JDBC driver");
		}
	}

	/**
	* Establishes a connection to be used for this session, assigning it to
	* the instance variable 'connection'.
	*
	* @param  url       the url to the database
	* @param  username  the username to connect to the database
	* @param  password  the password to connect to the database
	* @return           true if the connection is successful, false otherwise
	*/
	public boolean connectDB(String url, String username, String password) {
		try {
			this.connection = DriverManager.getConnection(url, username, password);
			return true;
		} catch (SQLException se) {
			System.err.println("SQL Exception. <Message>: " + se.getMessage());
			return false;
		}
	}

	/**
	* Closes the database connection.
	*
	* @return true if the closing was successful, false otherwise
	*/
	public boolean disconnectDB() {
		try {
			this.connection.close();
		return true;
		} catch (SQLException se) {
			System.err.println("SQL Exception. <Message>: " + se.getMessage());
			return false;
		}
	}

	/**
	 * Returns a sorted list of the names of all musicians and bands 
	 * who released at least one album in a given genre. 
	 *
	 * Returns an empty list if no such genre exists or no artist matches.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param genre  the genre to find artists for
	 * @return       a sorted list of artist names
	 */
	public ArrayList<String> findArtistsInGenre(String genre) {

		return null;
	}

	/**
	 * Returns a sorted list of the names of all collaborators
	 * (either as a main artist or guest) for a given artist.  
	 *
	 * Returns an empty list if no such artist exists or the artist 
	 * has no collaborators.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param artist  the name of the artist to find collaborators for
	 * @return        a sorted list of artist names
	 */
	queryString = "Sth we need to work on";
    pStatement = conn.prepareStatement(queryString);
    rs = pStatement.executeQuery();

	int numcols = rs.getMetaData().getColumnCount();
    List<String> answer = new ArrayList<>(numcols); 
    
    while (rs.next()) {
    int i = 1;
    while (i <= numcols) {  // don't skip the last column, use <=
        answer.add(rs.getString(i++));
    }

    return answer
}
	

	public ArrayList<String> findCollaborators(String artist) {
		
		return null;
	}


	/**
	 * Returns a sorted list of the names of all songwriters
	 * who wrote songs for a given artist (the given artist is excluded).  
	 *
	 * Returns an empty list if no such artist exists or the artist 
	 * has no other songwriters other than themself.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param artist  the name of the artist to find the songwriters for
	 * @return        a sorted list of songwriter names
	 */
	public ArrayList<String> findSongwriters(String artist) {

		return null;
	}

	/**
	 * Returns a sorted list of the names of all acquaintances
	 * for a given pair of artists.  
	 *
	 * Returns an empty list if either of the artists does not exist, 
	 * or they have no acquaintances.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param artist1  the name of the first artist to find acquaintances for
	 * @param artist2  the name of the second artist to find acquaintances for
	 * @return         a sorted list of artist names
	 */
	public ArrayList<String> findAcquaintances(String artist1, String artist2) {

		return null;
	}
	
	
	public static void main(String[] args) {
		
		Assignment2 a2 = new Assignment2();
		
		/* TODO: Change the database name and username to your own here. */
		a2.connectDB("jdbc:postgresql://localhost:5432/csc343h-bogdan",
		             "bogdan",
		             "");
		
                System.err.println("\n----- ArtistsInGenre -----");
                ArrayList<String> res = a2.findArtistsInGenre("Rock");
                for (String s : res) {
                  System.err.println(s);
                }

		System.err.println("\n----- Collaborators -----");
		res = a2.findCollaborators("Michael Jackson");
		for (String s : res) {
		  System.err.println(s);
		}
		
		System.err.println("\n----- Songwriters -----");
	        res = a2.findSongwriters("Justin Bieber");
		for (String s : res) {
		  System.err.println(s);
		}
		
		System.err.println("\n----- Acquaintances -----");
		res = a2.findAcquaintances("Jaden Smith", "Miley Cyrus");
		for (String s : res) {
		  System.err.println(s);
		}

		
		a2.disconnectDB();
	}
}

