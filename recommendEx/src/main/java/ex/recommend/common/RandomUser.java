/*
 * 
 * Random data 생성 
 * User 
 * 
 * @Author
 * 
 */

package ex.recommend.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomUser {

	private static final Logger logger = LoggerFactory.getLogger(RandomUser.class);
	
	public List<String[]> groupList; 
	
	public RandomUser() {
		String[] group1 = {"fmuytjens", "garethpon", "adamsenatori","samhorine","mikekus","hirozzzz","macenzo","residentgp_homewares","lavicvic","alexprager","seoul_nightview"};
		String[] group2 = {"adamfergusonphoto","therealpeterlindbergh","amberulmer","natnacphotos","edkashi","cassandragiraldo","yellowbirdvisuals","ivankphoto","goop","mimi_brune","coffeenclothes"};
		String[] group3 = {"janske","benlowy","othellonine","chrysti","shgbaker","kevinruss","skwii","jeffonline","brahmino","katieorlinsky","chrisburkard"};
		String[] group4 = {"natalieoffduty","targetstyle","garypeppergirl","susiebubble","elainewelteroth","jacquiealexander","reallykindofamazing","bleubird","alyssainthecity","charliemay","inezandvinoodh"};
		String[] group5 = {"joshualott","marcusbleasdale","lynseyaddario","sarafarid","andrewquilty","damirsagolj","andreeacampeanu","michaelchristopherbrown","glennagordon","wissamgaza","stevemccurryofficial"};
		String[] group6 = {"heysp","lacma","artbasel","saatchi_gallery","tate","palchenkov","ronhaviv_vii","bkstreetart","pauloctavious","victrver","artnet"};
		String[] group7 = {"philmoorephoto","danrubin","donaldweber","kirstenalana","edouphoto","tarahkreutz","robertclarkphoto","thiswildidea","andrewknapp","macieknabrdalik","danielberehulak"};
		
		groupList = new ArrayList<String[]>();
		groupList.add(group1);
		groupList.add(group2);
		groupList.add(group3);
		groupList.add(group4);
		groupList.add(group5);
		groupList.add(group6);
		groupList.add(group7);
		
	}
	
	public Integer createRandomUserData() {
		Integer count = 0;
		
		
		
		
		
		return count; 
	}
	
	public String[] getMyGroup(int groupInd) {
		
		return groupList.get(groupInd);
	}
	
}


/*

String[] group1 = {"fmuytjens", "garethpon", "adamsenatori","samhorine","mikekus","hirozzzz","macenzo","residentgp_homewares","lavicvic","alexprager","seoul_nightview"};

String[] group2 = {"adamfergusonphoto","therealpeterlindbergh","amberulmer","natnacphotos","edkashi","cassandragiraldo","yellowbirdvisuals","ivankphoto","goop","mimi_brune","coffeenclothes"};

String[] group3 = {"janske","benlowy","othellonine","chrysti","shgbaker","kevinruss","skwii","jeffonline","brahmino","katieorlinsky","chrisburkard"};

String[] group4 = {"natalieoffduty","targetstyle","garypeppergirl","susiebubble","elainewelteroth","jacquiealexander","reallykindofamazing","bleubird","alyssainthecity","charliemay","inezandvinoodh"}

String[] group5 = {"joshualott","marcusbleasdale","lynseyaddario","sarafarid","andrewquilty","damirsagolj","andreeacampeanu","michaelchristopherbrown","glennagordon","wissamgaza","stevemccurryofficial"}

String[] group6 = {"dguttenfelder","marcusbleasdale","lynseyaddario","sarafarid","andrewquilty","damirsagolj","andreeacampeanu","michaelchristopherbrown","glennagordon","wissamgaza","stevemccurryofficial"}

String[] group7 = {"philmoorephoto","danrubin","donaldweber","kirstenalana","edouphoto","tarahkreutz","robertclarkphoto","thiswildidea","andrewknapp","macieknabrdalik","danielberehulak"}
*/