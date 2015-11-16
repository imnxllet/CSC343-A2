import java.util.ArrayList;
import java.sql.*;
import java.util.Collections;

class Assignment2 {

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

		String queryString;
		Statement pStatement;
		ResultSet rs;
        ArrayList<String> answer = new ArrayList<>(); 

		try{
			String set_path = "SET search_path TO artistdb;";
			queryString = "select name from Artist, Album, Genre where Artist.artist_id = Album.artist_id and Album.genre_id = Genre.genre_id and Genre.genre =  '" + genre + "';";
		    pStatement = connection.createStatement();
            pStatement.execute(set_path);
		    rs = pStatement.executeQuery(queryString);
            
			int numcols = rs.getMetaData().getColumnCount();
		    
		    while (rs.next()) {
		        int i = 1;
		        while (i <= numcols) {  // don't skip the last column, use <=
		            answer.add(rs.getString(i++));
		        }
		    }

		    
		}catch (SQLException se){
            System.err.println("SQL Exception." + "<Message>: " + se.getMessage());    
        } 

        return answer;   
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
	public ArrayList<String> findCollaborators(String artist) {
		String queryString;
		Statement ps;
		ResultSet rs;
		ArrayList<String> answer = new ArrayList<>();
		String name;
        try{
        	String set_path = "SET search_path TO artistdb;";
			queryString = "SELECT a1.name FROM Collaboration c, Artist a1, Artist a2 WHERE a1.artist_id = c.artist1 AND a2.artist_id = c.artist2 AND (a1.name = '" + artist + "' OR a2.name = '" + artist+ "');";
			ps = connection.createStatement();
			ps.execute(set_path);
			rs = ps.executeQuery(queryString);

			int numcols = rs.getMetaData().getColumnCount();
		    
		    while (rs.next()) {
		        int i = 1;
		        while (i <= numcols) {  // don't skip the last column, use <=
		            answer.add(rs.getString(i++));
		        }
		    }

			Collections.sort(answer);
			

	    } catch (SQLException se){
	    	System.err.println("SQL Exception." + "<Message>: " + se.getMessage());
	    }
	    return answer;	
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

		String queryString;
		Statement pStatement;
		ResultSet rs;
		ArrayList<String> answer = new ArrayList<>(); 
		int numcols;
        
        try{
        	String set_path = "SET search_path TO artistdb;";
			queryString = "select name from Artist, (select songwriter_id from Song, BelongsToAlbum, Album, Artist where Song.song_id = BelongsToAlbum.song_id and BelongsToAlbum.album_id = Album.album_id and Album.artist_id = Artist.artist_id and Song.songwriter_id != Album.artist_id and Artist.name = '" + artist + "') AS writer where Artist.artist_id = writer.songwriter_id;";
		    pStatement = connection.createStatement();
		    pStatement.execute(set_path);
		    rs = pStatement.executeQuery(queryString);

			numcols = rs.getMetaData().getColumnCount();
		    
		    while (rs.next()) {
		        int i = 1;
		        while (i <= numcols) {  // don't skip the last column, use <=
		        	answer.add(rs.getString(i++));
		        }	
		    }

		    
		}catch (SQLException se){
	    	System.err.println("SQL Exception." + "<Message>: " + se.getMessage());
	    }
	    return answer;
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
	    ArrayList<String> answer = new ArrayList<>();
		try{	
            String set_path = "SET search_path TO artistdb;";
			String q1 = "CREATE VIEW CollabA1 AS SELECT a1.name AS name1, a2.name AS name2 FROM Collaboration c, Artist a1, Artist a2 WHERE a1.artist_id = c.artist1 AND a2.artist_id = c.artist2 AND (a1.name = '" + artist1 +  "' OR a2.name = '"+ artist1 + "');";
	        String q2 = "CREATE VIEW WriterA1 AS SELECT a2.name FROM Artist a1, Artist a2, Album m, Song s, BelongsToAlbum b WHERE a1.name = '" + artist1 + "' AND a1.artist_id = m.artist_id AND m.album_id = b.album_id AND b.song_id = s.song_id AND a2.artist_id = s.songwriter_id;";
			String q3 = "CREATE VIEW AcqA1 AS (SELECT c.name1 AS name FROM CollabA1 c WHERE c.name1 <> '" + artist1 + "') UNION (SELECT c.name2 AS name FROM CollabA1 c WHERE c.name2 <> '"+ artist1 +"') UNION (SELECT * FROM WriterA1);";

			String q4 = "CREATE VIEW CollabA2 AS SELECT a1.name AS name1, a2.name AS name2 FROM Collaboration c, Artist a1, Artist a2 WHERE a1.artist_id = c.artist1 AND a2.artist_id = c.artist2 AND (a1.name = '" + artist2 +  "' OR a2.name = '" + artist2 + "');" ;
			String q5 = "CREATE VIEW WriterA2 AS SELECT a2.name FROM Artist a1, Artist a2, Album m, Song s, BelongsToAlbum b WHERE a1.name = '" + artist2 + "' AND a1.artist_id = m.artist_id AND m.album_id = b.album_id AND b.song_id = s.song_id AND a2.artist_id = s.songwriter_id;";
			String q6 = "CREATE VIEW AcqA2 AS (SELECT c.name1 AS name FROM CollabA1 c WHERE c.name1 <> '"+ artist2 + "') UNION (SELECT c.name2 AS name FROM CollabA1 c WHERE c.name2 <> '"+ artist2 + "') UNION (SELECT * FROM WriterA1);";
			String q7 = "(SELECT * FROM AcqA1) INTERSECT (SELECT * FROM AcqA2);";


			Statement st = connection.createStatement();
			st.execute(set_path);	
			st.executeUpdate(q1);
			st.executeUpdate(q2);
			st.executeUpdate(q3);
			st.executeUpdate(q4);
			st.executeUpdate(q5);
			st.executeUpdate(q6);
			ResultSet rs = st.executeQuery(q7);

			while (rs.next()) {
				String name = rs.getString("name");
				answer.add(name);
			}
			
			Collections.sort(answer);
			return answer;

	    }catch (SQLException se){
	    	System.err.println("SQL Exception." + "<Message>: " + se.getMessage());
	    }
	    return answer;
	}
	
	
	public static void main(String[] args) {
		
		Assignment2 a2 = new Assignment2();
		
		/* TODO: Change the database name and username to your own here. */
		a2.connectDB("jdbc:postgresql://localhost:5432/csc343h-c3luzhen",
		             "c3luzhen",
		             "242418LZ");
		
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
