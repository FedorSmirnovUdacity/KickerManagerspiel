package IO;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import optimization.Player;
import optimization.Positions;

public class IOHelper {

	
	public static List<Player> loadPlayersFromCSV(String fileName) {
		List<Player> players = null; 
		try (Stream<String> lines = Files.lines(Paths.get(fileName),
				Charset.forName("Cp1252"))) {
			players = lines.map(
					line -> {
						List<String> lineList = Arrays.asList(line.replace(',','.').split(";"));

						Player player = new Player(lineList.get(0), lineList
								.get(1), lineList.get(2), Float
								.parseFloat(lineList.get(3)));
						if (lineList.size() > 5) {
							player.setPoints1516(Integer.parseInt(lineList
									.get(4)));
							player.setRating1516(Float.parseFloat(lineList
									.get(5)));
						}

						return player;
					}).collect(Collectors.toList());
			//players.forEach(player -> System.out.println(player));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return players;
	}

	public static Map<String, Collection<Player>>splitlistByPositions(Collection<Player> list){
		 Map<String, Collection<Player>> map = new HashMap<String, Collection<Player>>();
		 for (String pos : Positions.POSITIONSARRAY){
			 map.put(pos, new ArrayList<Player>());
		 }
		 for (Player player : list){
			 map.get(player.getPosition()).add(player);
		 }
		 return map;
	}
	
	public static void main(String[] args) {
		List<Player> list = loadPlayersFromCSV("spieler_zweiteliga.csv");
		Map<String, Collection<Player>> map = splitlistByPositions(list);
		System.out.println(map.get(Positions.TOR));
	}

}