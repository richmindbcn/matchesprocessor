package cat.richmind.matchprocessor.utils;

public class Constants {
	public static class Seasons {
		public static final Integer PRESEASON3 = 0 ;	
		public static final Integer SEASON_3 = 1;
		public static final Integer PRESEASON_2014 = 2;
		public static final Integer SEASON_2014 = 3;
	 	public static final Integer PRESEASON_2015 = 4;
		public static final Integer SEASON_2015 = 5;
	 	public static final Integer PRESEASON_2016 = 6;
		public static final Integer SEASON_2016 = 7;
	 	public static final Integer PRESEASON_2017 = 8;
	 	public static final Integer SEASON_2017 = 9;
	 	public static final Integer PRESEASON_2018 = 10;
	 	public static final Integer SEASON_2018 = 11;
	}
	
	/**
	 * <ul>
	 * <li>SR: Summoner's Rift</li>
	 * <li>CS: Crystal Scar</li>
	 * <li>TT: Twisted Treeline</li>
	 * <li>HA: Howling abyss</li>
	 * <li>BB: Butcher's bridge</li>
	 * </ul>
	 */
	public static class Queues {
		/** Custom games code */
		public static final Integer CUSTOM_GAMES = 0;
		
		/* TRAINING */
		/** Twisted Treeline - Co-op vs. AI Intermediate Bot games */
		public static final Integer TT_VS_AI_INTERMEDIATE = 800;
		/** Twisted Treeline - Co-op vs. AI Intro Bot games */
		public static final Integer TT_VS_AI_INTRO = 810;
		/** Twisted Treeline - Co-op vs. AI Beginner Bot games */
		public static final Integer TT_VS_AI_BEGINNER = 820;
		/** Summoner's Rift - Co-op vs. AI Intro Bot games */
		public static final Integer SR_VS_AI_INTRO = 830;
		/** Summoner's Rift - Co-op vs. AI Beginner Bot games */
		public static final Integer SR_VS_AI_BEGINNER = 840;
		/** Summoner's Rift - Co-op vs. AI Intermediate Bot games */
		public static final Integer SR_VS_AI_INTERMEDIATE = 850;
		
		/* SPECIAL MODES: PVE */
		/** Summoner's Rift - Doom Bots Voting games */
		public static final Integer SR_DOOMBOTS_VOTING = 950;
		/** Summoner's Rift - Doom Bots Standard games */
		public static final Integer SR_DOOMBOTS_STANDARD = 960;
		/** Valoran City Park - Star Guardian Invasion: Normal games */
		public static final Integer STAR_GUARDIAN_NORMAL = 980;
		/** Valoran City Park - Star Guardian Invasion: Onslaught games */
		public static final Integer STAR_GUARDIAN_ONSLAUGHT = 990;
		/** Crash Site - Odyssey Extraction: Intro games */
		public static final Integer ODDYSSEY_INTRO = 1030;
		/** Crash Site - Odyssey Extraction: Cadet games */
		public static final Integer ODDYSSEY_CADET = 1040;
		/** Crash Site - Odyssey Extraction: Crewmember games */
		public static final Integer ODDYSSEY_CREWMENBER = 1050;
		/** Crash Site - Odyssey Extraction: Captain games */
		public static final Integer ODDYSSEY_CAPTAIN = 1060;
		/** Crash Site - Odyssey Extraction: Onslaught games */
		public static final Integer ODDYSSEY_ONSLAUGHT = 1070;
		
		/* SPECIAL MODES: PVP */
		/** Howling Abyss - 1v1 Snowdown Showdown games */
		public static final Integer HA_1VS1 = 72;
		/** Howling Abyss - 2v2 Snowdown Showdown games */
		public static final Integer HA_2VS2 = 73;
		/** Howling Abyss - One For All: Mirror Mode games */
		public static final Integer HA_ONE4ALL_MIRROR = 78;
		/** Howling Abyss - Legend of the Poro King games */
		public static final Integer HA_PORO_KING = 920;
		/** Butcher's Bridge - 5v5 ARAM games */
		public static final Integer BB_5VS5_ARAM = 100;
		/** Twisted Treeline - 6v6 Hexakill games */
		public static final Integer TT_HEXAKILL = 98;
		
		/** Summoner's Rift - 6v6 Hexakill games */
		public static final Integer SR_HEXAKILL = 75;
		/** Summoner's Rift - Ultra Rapid Fire games */
		public static final Integer SR_URF = 76;
		/** Summoner's Rift - Co-op vs AI Ultra Rapid Fire games */
		public static final Integer SR_URF_VS_AI = 83;
		/** Summoner's Rift - Nemesis games */
		public static final Integer SR_NEMESIS = 310;
		/** Summoner's Rift - Black Market Brawlers games */
		public static final Integer SR_BLACK_MATKET_BRAWLERS = 313;
		/** Summoner's Rift - Blood Hunt Assassin games */
		public static final Integer SR_BLOOD_HUNT = 600;
		/** Summoner's Rift - Nexus Siege games */
		public static final Integer SR_NEXUS_SIEGE = 940;
		/** Summoner's Rift - ARURF games */
		public static final Integer SR_ARURF = 900;
		/** Summoner's Rift - Snow ARURF games */
		public static final Integer SR_SNOW_ARURF = 1010;
		/** Summoner's Rift - One for All games */
		public static final Integer SR_ONE4ALL = 1020;
		
		/** Crystal Scar - Definitely Not Dominion games */
		public static final Integer CS_DEFO_NOT_DOMINION = 317;
		/** Crystal Scar - Ascension games */
		public static final Integer CS_ASCENSION = 910;
		
		/** Cosmic Ruins - Dark Star: Singularity games */
		public static final Integer DARK_STAR = 610;
		/** Overcharge - PROJECT: Hunters games */
		public static final Integer PROJECT = 1000;
		/** Nexus Blitz - Nexus Blitz games */
		public static final Integer NEXUS_BLITZ = 1200;
		
		/** Summoner's Rift - All Random games */
		public static final Integer SR_ALL_RANDOM_GAMES = 325;
		/** Summoner's Rift - 5v5 Draft Pick games */
		public static final Integer SR_5V5_DRAFT = 400;
		/** Summoner's Rift - 5v5 Ranked Dynamic games 	(Game mode deprecated in patch 6.22) */
		public static final Integer SR_5V5_DYNAMIC_RANKED = 410;
		/** Summoner's Rift - 5v5 Ranked Solo games */
		public static final Integer SR_5V5_SOLOQ_RANKED = 420;
		/** Summoner's Rift - 5v5 Blind Pick games */
		public static final Integer SR_5V5_BLIND = 430;
		/** Summoner's Rift - 5v5 Ranked Flex games */
		public static final Integer SR_5V5_FLEX_RANKED = 440;
		/** Howling Abyss - 5v5 ARAM games */
		public static final Integer HA_ARAM = 450;
		/** Twisted Treeline - 3v3 Blind Pick games */
		public static final Integer TT_3V3_BLIND = 460;
		/** Twisted Treeline - 3v3 Ranked Flex games */
		public static final Integer TT_3V3_FLEX_RANKED = 470;
		/** Summoner's Rift - Clash games */
		public static final Integer SR_CLASH = 700;
	}
}